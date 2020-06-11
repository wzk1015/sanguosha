package people;

import cards.Card;
import cards.Color;
import cards.EquipType;
import cards.Equipment;
import cards.JudgeCard;
import cards.Strategy;
import cards.basic.HurtType;
import cards.basic.Sha;
import cards.basic.Shan;
import cards.basic.Tao;
import cards.equipments.Shield;
import cards.equipments.Weapon;
import cards.strategy.JieDaoShaRen;
import cards.strategy.TieSuoLianHuan;
import cards.strategy.WuXieKeJi;
import cardsheap.CardsHeap;
import manager.GameManager;

import manager.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import static cards.EquipType.shield;
import static cards.EquipType.weapon;

public abstract class Person extends Attributes implements SkillLauncher,
        PlayerIO, Serializable {
    private final int maxHP;
    private int currentHP;
    private int shaCount = getMaxShaCount();
    private final ArrayList<Card> cards = new ArrayList<>();
    private final HashMap<EquipType, Equipment> equipments = new HashMap<>();
    private final ArrayList<JudgeCard> judgeCards = new ArrayList<>();

    public Person(int maxHP, String sex, Nation nation) {
        Utils.assertTrue(sex.equals("male") || sex.equals("female"), "invalid sex");
        this.maxHP = maxHP;
        this.currentHP = maxHP;
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
        beginPhase();
        if (isDead()) {
            return;
        }
        ArrayList<String> states = judgePhase();
        if (isDead()) {
            return;
        }
        if (!states.contains("skip draw")) {
            drawPhase();
        }
        if (!states.contains("skip use")) {
            usePhase();
        }
        shaCount = getMaxShaCount();
        setDrunk(false);
        if (isDead()) {
            return;
        }
        throwPhase();
        endPhase();
        println("----------" + this + "'s round ends" + "----------");
    }

    public void beginPhase() {

    }

    public ArrayList<String> judgePhase() {
        ArrayList<String> states = new ArrayList<>();
        for (JudgeCard jc : judgeCards) {
            println("judging " + jc);
            String state = jc.use();
            if (jc.isNotTaken()) {
                CardsHeap.discard(jc);
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
        judgeCards.clear();
        return states;
    }

    public void drawPhase() {
        println(this + " draw 2 cards from cards heap");
        drawCards(2);
    }

    public boolean parseOrder(String order) {
        Card card;
        if (useSkillInUsePhase(order)) {
            return true;
        } else if (order.equals("丈八蛇矛") && cards.size() >= 2) {
            ArrayList<Card> cs = this.chooseCards(2, cards);
            throwCard(cs);
            if (cs.get(0).isRed() && cs.get(1).isRed()) {
                card = new Sha(Color.DIAMOND, 0);
            } else if (cs.get(1).isBlack() && cs.get(1).isBlack()) {
                card = new Sha(Color.CLUB, 0);
            } else {
                card = new Sha(Color.NOCOLOR, 0);
            }
        } else {
            try {
                card = cards.get(Integer.parseInt(order) - 1);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                println("Wrong input");
                return false;
            }
        }

        if (card instanceof TieSuoLianHuan) {
            if (chooseFromProvided("throw", "use").equals("throw")) {
                throwCard(card);
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
        if (shaCount != 0 || hasEquipment(weapon, "诸葛连弩")) {
            shaCount--;
        } else {
            println("You can't 杀 anymore");
            return false;
        }
        if (cards.isEmpty() && hasEquipment(weapon, "方天画戟")) {
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
        if (this.equipments.get(((Equipment) card).getEquipType()) != null) {
            throwCard(equipments.get(((Equipment) card).getEquipType()));
            lostEquipment();
        }
        println(this + " puts on equipment " + card);
        throwCard(card);
        this.equipments.put(((Equipment) card).getEquipType(), (Equipment) card);
    }

    public boolean useCard(Card card) {
        if (card instanceof Sha) {
            if (!useSha(card)) {
                return false;
            }
        }
        if ((card instanceof Tao && currentHP == maxHP) || card instanceof Shan ||
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
            card.getTarget().getJudgeCards().add((JudgeCard) card);
            throwCard(card);
            showUsingCard(card);
            return true;
        }
        if (card instanceof Strategy) {
            useStrategy();
        }

        showUsingCard(card);
        card.use();
        return true;
    }

    public void usePhase() {
        println("identity: " + getIdentity());
        println("current HP: " + currentHP + "/" + maxHP);
        printAllCards();
        while (true) {
            println(this + "'s current hand cards: ");
            printCards(getCards());
            if (hasEquipment(weapon, "丈八蛇矛")) {
                println("【丈八蛇矛】");
            }
            String order = input("Number for using card, " +
                    "Name for using skill or weapon, 'q' for ending phase");
            if (order.equals("q")) {
                break;
            }
            parseOrder(order);
        }
    }

    public void throwPhase() {
        int num = cards.size() - currentHP;
        if (num > 0) {
            println(String.format("You need to throw %d cards", num));
            ArrayList<Card> cs = chooseCards(num, cards);
            print(this + " throws ");
            printCards(cs);
            throwCard(cs);
        }
    }

    public void endPhase() {

    }

    public void addCard(Card c) {
        cards.add(c);
        print(this + " got card: ");
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

    public void loseCard(Card c, boolean throwAway) {
        print(this + " lost card: ");
        printCard(c);
        if (c instanceof JudgeCard && judgeCards.contains(c)) {
            judgeCards.remove(c);
            CardsHeap.discard(c);
        } else if (c instanceof Equipment && equipments.containsValue(c)) {
            equipments.remove(((Equipment) c).getEquipType());
            CardsHeap.discard(c);
            if (c.toString().equals("白银狮子")) {
                recover(1);
            }
            if (!isDead()) {
                lostEquipment();
            }
        } else if (cards.contains(c)) {
            cards.remove(c);
            if (!isDead()) {
                lostCard();
            }
        } else {
            GameManager.endWithError("lose card not belong to " + this);
        }
        if (throwAway) {
            CardsHeap.discard(c);
            lostCard();
        }
    }

    public void throwCard(ArrayList<Card> cs) {
        for (Card c : cs) {
            throwCard(c);
        }
    }

    public void throwCard(Card c) {
        loseCard(c, true);
    }

    public int hurt(Card card, Person source, int num) {
        return hurt(card, source, num, HurtType.normal);
    }

    public int hurt(Card card, Person source, int num, HurtType type) {
        int realNum = num;

        if (hasEquipment(shield, "藤甲") && ((Shield) equipments.get(shield)).isValid()) {
            if (type == HurtType.fire) {
                realNum++;
            }
        }
        if (hasEquipment(shield, "白银狮子") && ((Shield) equipments.get(shield)).isValid()) {
            if (num > 1) {
                realNum--;
            }
        }

        currentHP -= realNum;
        println(this + " lost " + realNum + " HP, current HP: " + currentHP + "/" + maxHP);
        if (currentHP <= 0) {
            dying();
        }
        if (type != HurtType.normal && isLinked()) {
            link();
        }
        gotHurt(card, source, realNum);
        return realNum;
    }

    public void recover(int num) {
        if (currentHP < maxHP) {
            currentHP += num;
            println(this + " recover " + num + " HP, current HP: " + currentHP + "/" + maxHP);
        }
    }

    public int getHP() {
        return currentHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public boolean requestShan() {
        if (hasEquipment(shield, "八卦阵") && ((Shield) equipments.get(shield)).isValid()) {
            if ((boolean) equipments.get(shield).use()) {
                return true;
            }
        }
        if (skillShan()) {
            return true;
        }
        return requestCard("闪") != null;
    }

    public Sha requestSha() {
        if (hasEquipment(weapon, "丈八蛇矛") && cards.size() >= 2) {
            if (chooseFromProvided("throw two cards to sha", "pass").equals(
                    "throw two cards to sha")) {
                ArrayList<Card> cs = chooseCards(2, cards);
                throwCard(cs);
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
        int needTao = 1 - currentHP;
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
        if (hasEquipment(shield, "藤甲") && ((Shield) equipments.get(shield)).isValid()) {
            if (sha.getType() == HurtType.normal) {
                return false;
            }
        }
        if (hasEquipment(shield, "仁王盾") && ((Shield) equipments.get(shield)).isValid()) {
            return sha.isRed();
        }
        return true;
    }

    public abstract String toString();

    public ArrayList<Card> getCards() {
        return cards;
    }

    public HashMap<EquipType, Equipment> getEquipments() {
        return equipments;
    }

    public ArrayList<JudgeCard> getJudgeCards() {
        return judgeCards;
    }

    public ArrayList<Card> getCardsAndEquipments() {
        ArrayList<Card> ans = new ArrayList<>(cards);
        ans.addAll(equipments.values());
        return ans;
    }

    public int getShaDistance() {
        if (equipments.get(weapon) != null) {
            return ((Weapon) equipments.get(weapon)).getDistance();
        }
        return 1;
    }

    public boolean hasEquipment(EquipType type, String name) {
        if (name == null) {
            return equipments.get(type) != null;
        }
        if (equipments.get(type) == null) {
            return false;
        }
        return equipments.get(type).toString().equals(name);
    }

}
