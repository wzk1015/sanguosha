package people;

import cards.Card;
import cards.Color;
import cards.Equipment;
import cards.JudgeCard;
import cards.Strategy;
import cards.basic.HurtType;
import cards.basic.Sha;
import cards.basic.Shan;
import cards.basic.Tao;
import cards.equipments.Shield;
import cards.strategy.JieDaoShaRen;
import cards.strategy.TieSuoLianHuan;
import cards.strategy.WuXieKeJi;
import cardsheap.CardsHeap;
import manager.GameManager;

import manager.Utils;

import java.io.Serializable;
import java.util.ArrayList;

import static cards.EquipType.shield;
import static cards.EquipType.weapon;

public abstract class Person extends Attributes implements SkillLauncher,
        PlayerIO, Serializable {

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
            GameManager.askOtherPeopleUsePhase(this);
            usePhase();
        }
        setDrunk(false);
        if (isDead()) {
            return;
        }
        if (!skipThrow()) {
            throwPhase();
        }
        endPhase();
        setMyRound(false);
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

    public boolean parseOrder(String order) {
        Card card;
        if (useSkillInUsePhase(order)) {
            return true;
        } else if (order.equals("丈八蛇矛") && getCards().size() >= 2) {
            ArrayList<Card> cs = this.chooseCards(2, getCards());
            loseCard(cs);
            if (cs.get(0).isRed() && cs.get(1).isRed()) {
                card = new Sha(Color.DIAMOND, 0);
            } else if (cs.get(1).isBlack() && cs.get(1).isBlack()) {
                card = new Sha(Color.CLUB, 0);
            } else {
                card = new Sha(Color.NOCOLOR, 0);
            }
            card.setTaken(true);
            ((Sha) card).setThisCard(cs);
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

        if (!GameManager.askTarget(card, this)) {
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

    public boolean useSha(Card card) {
        if (getShaCount() != 0 || hasEquipment(weapon, "诸葛连弩")) {
            setShaCount(getShaCount() - 1);
        } else {
            println("You can't 杀 anymore");
            return false;
        }
        if (getCards().isEmpty() && hasEquipment(weapon, "方天画戟")) {
            String option = chooseFromProvided("1target", "2targets", "3targets");
            Person target3 = null;
            if (option.equals("3targets")) {
                Sha s3 = new Sha(card.color(), card.number(), ((Sha) card).getType());
                if (GameManager.askTarget(s3, this) && s3.getTarget() != card.getTarget()) {
                    target3 = s3.getTarget();
                    Utils.assertTrue(target3 != null, "sha3 target is null");
                    s3.use();
                }
            }
            if (option.equals("3targets") || option.equals("2targets")) {
                Sha s2 = new Sha(card.color(), card.number(), ((Sha) card).getType());
                if (GameManager.askTarget(s2, this) && s2.getTarget() != card.getTarget()
                        && s2.getTarget() != target3) {
                    Utils.assertTrue(s2.getTarget() != null, "sha2 target is null");
                    s2.use();
                }
            }
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
                card instanceof WuXieKeJi || (card instanceof JieDaoShaRen &&
                !card.getTarget().hasEquipment(weapon, null))) {
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

        showUsingCard(card);
        card.use();
        return true;
    }

    public void usePhase() {
        printSkills();
        println("identity: " + getIdentity());
        println("current HP: " + getHP() + "/" + getMaxHP());
        printAllCards();
        while (true) {
            println(this + "'s current hand cards: ");
            printCards(getCards());
            if (hasEquipment(weapon, "丈八蛇矛")) {
                println("【丈八蛇矛】");
            }
            String order = input("Number:use card, " +
                    "Name:skill or weapon, 'q':end phase");
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
        }
    }

    public void endPhase() {

    }

    public void addCard(Card c) {
        getCards().add(c);
        print(this + " got card: ");
        c.setOwner(this);
        printCard(c);
    }

    public void addCard(ArrayList<Card> cs) {
        for (Card c : cs) {
            addCard(c);
        }
    }

    public void drawCard() {
        addCard(CardsHeap.draw());
    }

    public void drawCards(int num) {
        for (int i = 0; i < num; i++) {
            drawCard();
        }
    }

    public void loseCard(ArrayList<Card> cs) {
        for (Card c: cs) {
            loseCard(c);
        }
    }

    public void loseCard(Card c) {
        loseCard(c,  true);
    }

    public void loseCard(Card c, boolean throwAway) {
        loseCard(c, throwAway, true);
    }

    public void loseCard(Card c, boolean throwAway, boolean print) {
        if (print && throwAway) {
            print(this + " lost card: ");
            printCard(c);
        }
        if (getRealJudgeCards().contains(c)) {
            removeJudgeCard(c);
        } else if (c instanceof Equipment && getEquipments().containsValue(c)) {
            getEquipments().remove(((Equipment) c).getEquipType());
            if (c.toString().equals("白银狮子")) {
                recover(1);
            }
            if (!isDead()) {
                lostEquipment();
            }
        } else if (getCards().contains(c)) {
            getCards().remove(c);
            if (!isDead()) {
                lostCard();
            }
        } else {
            GameManager.endWithError("lose card not belong to " + this);
        }
        c.setOwner(null);
        if (throwAway) {
            CardsHeap.discard(c);
        } else {
            c.setTaken(true);
        }
    }

    public void throwCard(ArrayList<Card> cs) {
        for (Card c : cs) {
            throwCard(c);
        }
    }

    public void throwCard(Card c) {
        loseCard(c, true, false);
    }

    public void hurt(ArrayList<Card> cs, Person source, int num) {
        hurt(cs, source, num, HurtType.normal);
    }

    public int hurt(Card card, Person source, int num) {
        ArrayList<Card> cs = new ArrayList<>();
        cs.add(card);
        return hurt(cs, source, num, HurtType.normal);
    }

    public int hurt(Card card, Person source, int num, HurtType type) {
        ArrayList<Card> cs = new ArrayList<>();
        cs.add(card);
        return hurt(cs, source, num, type);
    }

    public int hurt(ArrayList<Card> cs, Person source, int num, HurtType type) {
        int realNum = num;

        if (hasEquipment(shield, "藤甲") && ((Shield) getEquipments().get(shield)).isValid()) {
            if (type == HurtType.fire) {
                realNum++;
            }
        }
        if (hasEquipment(shield, "白银狮子") && ((Shield) getEquipments().get(shield)).isValid()) {
            if (num > 1) {
                realNum--;
            }
        }

        loseHP(realNum);
        if (type != HurtType.normal && isLinked()) {
            link();
        }
        source.hurtOther(this);
        gotHurt(cs, source, realNum);
        return realNum;
    }

    public void loseHP(int num) {
        subCurrentHP(num);
        println(this + " lost " + num + "HP, current HP: " + getHP() + "/" + getMaxHP());
        if (getHP() <= 0) {
            dying();
        }
    }

    public void recover(int num) {
        if (getHP() == getMaxHP()) {
            return;
        }
        setCurrentHP(Math.max(getHP() + num, getMaxHP()));
        println(this + " recover " + num + "HP, current HP: " + getHP() + "/" + getMaxHP());
    }

    public boolean requestShan() {
        if (hasEquipment(shield, "八卦阵") && ((Shield) getEquipments().get(shield)).isValid()) {
            if ((boolean) getEquipments().get(shield).use()) {
                return true;
            }
        }
        if (skillShan()) {
            return true;
        }
        return requestCard("闪") != null;
    }

    public Sha requestSha() {
        if (hasEquipment(weapon, "丈八蛇矛") && getCards().size() >= 2) {
            if (chooseFromProvided("throw two cards to sha", "pass").equals(
                    "throw two cards to sha")) {
                ArrayList<Card> cs = chooseCards(2, getCards());
                loseCard(cs);
                if (cs.get(0).isRed() && cs.get(1).isRed()) {
                    return new Sha(Color.DIAMOND, 0);
                } else if (cs.get(1).isBlack() && cs.get(1).isBlack()) {
                    return new Sha(Color.CLUB, 0);
                }
                return new Sha(Color.NOCOLOR, 0);
            }
        }
        if (skillSha()) {
            return new Sha(Color.NOCOLOR, 0);
        }
        return (Sha) requestCard("杀");
    }

    public boolean requestWuXie() {
        if (skillWuxie()) {
            return true;
        }
        return requestCard("无懈可击") != null;
    }

    public boolean requestTao() {
        return requestCard("桃") != null;
    }

    public void dying() {
        int needTao = 1 - getHP();
        if (needTao <= 0) {
            return;
        }
        for (int i = 0; i < needTao; i++) {
            if (!GameManager.askTao(this)) {
                die();
            }
            recover(1);
        }
    }

    public boolean canBeSha(Sha sha) {
        if (hasEquipment(shield, "藤甲") && ((Shield) getEquipments().get(shield)).isValid()) {
            if (sha.getType() == HurtType.normal) {
                return false;
            }
        }
        if (hasEquipment(shield, "仁王盾") && ((Shield) getEquipments().get(shield)).isValid()) {
            return sha.isRed();
        }
        return true;
    }

}
