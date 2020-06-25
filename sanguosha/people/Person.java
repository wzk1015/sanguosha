package sanguosha.people;

import sanguosha.cards.Card;
import sanguosha.cards.Equipment;
import sanguosha.cards.JudgeCard;
import sanguosha.cards.Strategy;
import sanguosha.cards.basic.Jiu;
import sanguosha.cards.basic.Sha;
import sanguosha.cards.basic.Shan;
import sanguosha.cards.basic.Tao;
import sanguosha.cards.equipments.weapons.FangTianHuaJi;
import sanguosha.cards.strategy.TieSuoLianHuan;
import sanguosha.cards.strategy.WuXieKeJi;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.cardsheap.PeoplePool;
import sanguosha.manager.GameManager;

import sanguosha.manager.Utils;

import java.io.Serializable;
import java.util.ArrayList;

import static sanguosha.cards.EquipType.weapon;

public abstract class Person extends Attributes implements Serializable {

    public Person(int maxHP, String sex, Nation nation) {
        Utils.assertTrue(sex.equals("male") || sex.equals("female"), "invalid sex");
        this.setMaxHP(maxHP);
        this.setCurrentHP(maxHP);
        this.setSex(sex);
        this.setNation(nation);
    }

    public Person(int maxHP, Nation nation) {
        this(maxHP, "male", nation);
    }

    public void run() {
        println("----------" + this + "'s round begins" + "----------");
        if (isTurnedOver()) {
            turnover();
            println(this + "turns over");
            return;
        }
        setHasUsedSkill1(false);
        setMyRound(true);
        beginPhase();
        if (isDead()) {
            return;
        }
        ArrayList<String> states = new ArrayList<>();
        if (!skipJudge()) {
            states = judgePhase();
        }
        if (isDead()) {
            return;
        }
        if (!states.contains("skip draw") && !skipDraw()) {
            drawPhase();
        }
        setShaCount(getMaxShaCount());
        if (!states.contains("skip use") && !skipUse()) {
            for (Person p2: GameManager.getPlayers()) {
                p2.otherPersonUsePhase(this);
            }
            usePhase();
        }
        setDrunk(false);
        if (isDead()) {
            return;
        }
        if (!skipThrow()) {
            throwPhase();
        }
        setMyRound(false);
        endPhase();
        Utils.assertTrue(getHP() <= getMaxHP(), "currentHP exceeds maxHP");
        println("----------" + this + "'s round ends" + "----------");
    }

    public void beginPhase() {

    }

    public ArrayList<String> judgePhase() {
        Utils.assertTrue(getJudgeCards().size() <= 3,
                "too many judgecards: " + getJudgeCards().size());
        ArrayList<String> states = new ArrayList<>();
        for (JudgeCard jc : getJudgeCards()) {
            println("judging " + jc);
            String state = jc.use();
            if (jc.isNotTaken()) {
                CardsHeap.discard(jc.getThisCard());
            } else {
                jc.setTaken(false);
            }
            if (state != null) {
                println(state);
                states.add(state);
            } else {
                println("judgecard failed");
            }
        }
        getJudgeCards().clear();
        return states;
    }

    public void drawPhase() {
        println(this + " draws 2 cards from cards heap");
        drawCards(2);
    }

    public boolean useSha(Card card) {
        if (isMyRound()) {
            if (getShaCount() != 0 || hasEquipment(weapon, "诸葛连弩")) {
                setShaCount(getShaCount() - 1);
            } else {
                println("You can't 杀 anymore");
                return false;
            }
        }
        if (getCards().isEmpty() && hasEquipment(weapon, "方天画戟")) {
            ((FangTianHuaJi) getEquipments().get(weapon)).setsha(card);
            getEquipments().get(weapon).setSource(this);
            getEquipments().get(weapon).use();
        }
        return true;
    }

    public void putOnEquipment(Card card) {
        if (this.getEquipments().get(((Equipment) card).getEquipType()) != null) {
            loseCard(getEquipments().get(((Equipment) card).getEquipType()));
            lostEquipment();
        }
        println(this + " puts on equipment " + card);
        getCards().remove(card);
        card.setTaken(true);
        this.getEquipments().put(((Equipment) card).getEquipType(), (Equipment) card);
    }

    public boolean useCard(Card card) {
        if (card instanceof Sha) {
            if (!useSha(card)) {
                return false;
            }
        }
        if ((card instanceof Tao && getHP() == getMaxHP()) || card instanceof Shan ||
                card instanceof WuXieKeJi || (card instanceof Jiu && isDrunk())) {
            println("You can't use that");
            return false;
        }
        if (card instanceof Equipment) {
            putOnEquipment(card);
            return true;
        }

        if (card instanceof JudgeCard) {
            if (card.getTarget().addJudgeCard((JudgeCard) card)) {
                getCards().remove(card);
                showUsingCard(card);
                card.setTaken(true);
                return true;
            }
            return false;
        }
        if (card instanceof Strategy) {
            useStrategy();
            throwCard(card);
            card.setTaken(true);
        }
        getCards().remove(card);
        if (!isDead()) {
            showUsingCard(card);
            card.use();
        }
        return true;
    }

    public boolean parseOrder(String order) {
        Card card;

        if (useSkillInUsePhase(order)) {
            return true;
        }
        else if (order.equals("丈八蛇矛") && hasEquipment(weapon, "丈八蛇矛") && getCards().size() >= 2) {
            getEquipments().get(weapon).setSource(this);
            card = (Card) getEquipments().get(weapon).use();
        }
        else if (order.contains("help")) {
            showHelp();
            return true;
        } else {
            try {
                card = getCards().get(Integer.parseInt(order) - 1);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                println("Wrong input");
                return false;
            }
        }

        if (card instanceof TieSuoLianHuan) {
            if (chooseFromProvided("throw", "use").equals("throw")) {
                loseCard(card);
                drawCard();
                return true;
            }
        }

        if (!card.askTarget(this)) {
            return false;
        }

        boolean used = useCard(card);

        if (card.isNotTaken() && used) {
            throwCard(card);
        } else if (!card.isNotTaken()) {
            card.setTaken(false);
        }
        return used;
    }

    public void usePhase() {
        printSkills();
        println("identity: " + getIdentity());
        println("current HP: " + getHP() + "/" + getMaxHP());
        showExtraInfo();
        printAllCards();
        while (!isDead()) {
            println(this + "'s current hand cards: ");
            printCards(getCards());
            if (hasEquipment(weapon, "丈八蛇矛")) {
                println("【丈八蛇矛】");
            }
            String order = input("input an order, 'help' for help");
            if (order.equals("q")) {
                break;
            }
            parseOrder(order);
        }
    }

    public void throwPhase() {
        int num = getCards().size() - getHP();
        if (num > 0) {
            println(String.format("You need to throw %d cards", num));
            ArrayList<Card> cs = chooseCards(num, getCards());
            loseCard(cs);
            for (Person p: GameManager.getPlayers()) {
                p.otherPersonThrowPhase(this, cs);
            }
        }

    }

    public void endPhase() {

    }

    public void showHelp() {
        println("Number: use card, Name: skill or weapon, 'q':end phase");
        String type = input("input name of card or person to get information, 'q' to quit");
        if (type.equals("q")) {
            return;
        }
        if (type.equals("")) {
            println(this + "'s help:\n" + help());
            return;
        }
        for (Card c: CardsHeap.getAllCards()) {
            if (c.toString().equals(type)) {
                println(c + "'s help:\n" + c.help());
                return;
            }
        }
        for (Person p: PeoplePool.getPeople()) {
            if (p.toString().equals(type)) {
                println(p + "'s help:\n" + p.help());
                return;
            }
        }
        println("unknown help type: " + type);
    }

    public String help() {
        return "not implemented";
    }
}
