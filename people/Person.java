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
import manager.IO;
import manager.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import static cards.EquipType.shield;
import static cards.EquipType.weapon;

public abstract class Person extends PersonAttributes implements SkillLauncher {
    private int maxHP;
    private int currentHP;
    private int shaCount = 1;
    private ArrayList<Card> cards = new ArrayList<>();
    private HashMap<EquipType, Equipment> equipments = new HashMap<>();
    private ArrayList<JudgeCard> judgeCards = new ArrayList<>();

    public Person(int maxHP, String sex) {
        Utils.assertTrue(sex.equals("male") || sex.equals("female"), "invalid sex");
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.setSex(sex);
    }

    public Person(int maxHP) {
        this(maxHP, "male");
    }

    public void run() {
        if (isTurnedOver()) {
            turnover();
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
        if (isDead()) {
            return;
        }
        throwPhase();
        endPhase();
    }

    public void beginPhase() {

    }

    public ArrayList<String> judgePhase() {
        ArrayList<String> states = new ArrayList<>();
        for (JudgeCard jc : judgeCards) {
            states.add(jc.use());
        }
        return states;
    }

    public void drawPhase() {
        cards.addAll(CardsHeap.draw(2));
    }

    public void parseOrder(String order) {
        Card card;
        if (useSkillInUsePhase(order)) {
            return;
        }
        else if (order.equals("丈八蛇矛") && cards.size() >= 2) {
            ArrayList<Card> cs = IO.chooseCards(this, 2, cards);
            throwCard(cs);
            if (cs.get(0).isRed() && cs.get(1).isRed()) {
                card = new Sha(Color.DIAMOND, 0);
            } else if (cs.get(1).isBlack() && cs.get(1).isBlack()) {
                card = new Sha(Color.CLUB, 0);
            } else {
                card = new Sha(Color.NOCOLOR, 0);
            }
        }
        else {
            try {
                card = cards.get(Integer.parseInt(order));
            } catch (NumberFormatException e) {
                IO.println("Wrong input");
                return;
            }
        }

        if (!GameManager.askTarget(card, this)) {
            return;
        }

        useCard(card);
    }

    public void useCard(Card card) {
        if (card instanceof Sha) {
            if (shaCount != 0 || hasEquipment(weapon, "诸葛连弩")) {
                shaCount--;
            } else {
                IO.println("You can't 杀 anymore");
                return;
            }
        }
        if ((card instanceof Tao && currentHP == maxHP) || card instanceof Shan ||
                card instanceof WuXieKeJi || (card instanceof JieDaoShaRen &&
                !card.getTarget().hasEquipment(weapon, null))) {
            IO.println("You can't use that");
            return;
        }
        if (card instanceof Equipment) {
            if (this.equipments.get(((Equipment) card).getEquipType()) != null) {
                lostEquipment();
            }
            this.equipments.put(((Equipment) card).getEquipType(), (Equipment) card);
            throwCard(card);
            return;
        }
        if (card instanceof TieSuoLianHuan) {
            if (IO.chooseFromProvided(this,"throw", "use").equals("throw")) {
                throwCard(card);
                drawCard();
                return;
            }
        }
        throwCard(card);
        if (card instanceof Strategy && !(card instanceof JudgeCard)) {
            useStrategy();
        }

        card.use();
        IO.println(toString() + " uses " + card);
    }

    public void usePhase() {
        IO.println("Your current cards: ");
        IO.printCards(cards);

        while (true) {
            String order = IO.input("Number for using card, 'q' for ending phase");
            if (order.equals("q")) {
                break;
            }
            parseOrder(order);
        }
        shaCount = 1;
    }

    public void throwPhase() {
        int num = cards.size() - currentHP;
        if (num > 0) {
            IO.println(String.format("You need to throw %d cards", num));
            IO.printCards(cards);
            throwCard(IO.chooseCards(this, num, cards));
        }
    }

    public void endPhase() {

    }

    public void addCard(Card c) {
        cards.add(c);
    }

    public void addCard(ArrayList<Card> cs) {
        for (Card c: cs) {
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

    public void loseCard(Card c) {
        if (c instanceof JudgeCard && judgeCards.contains((Card) c)) {
            judgeCards.remove(c);
            CardsHeap.discard(c);
        } else if (c instanceof Equipment && equipments.containsValue((Equipment) c)) {
            equipments.remove(((Equipment) c).getEquipType());
            CardsHeap.discard(c);
            if (c.toString().equals("白银狮子")) {
                recover(1);
            }
        } else {
            throwCard(c);
        }
    }

    public void throwCard(ArrayList<Card> cs) {
        for (Card c : cs) {
            throwCard(c);
        }
    }

    public void throwCard(Card c) {
        if (c instanceof Equipment && equipments.containsValue(c)) {
            equipments.remove(((Equipment) c).getEquipType(), c);
            CardsHeap.discard(c);
            lostEquipment();
        } else {
            cards.remove(c);
            CardsHeap.discard(c);
            lostCard();
        }
    }

    public int hurt(Person source, int num) {
        return hurt(source, num, HurtType.normal);
    }

    public int hurt(Person source, int num, HurtType type) {
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
        if (type != HurtType.normal && isLinked()) {
            link();
        }
        if (currentHP < 0) {
            dying();
        }
        gotHurt(realNum);
        return realNum;
    }

    public void recover(int num) {
        if (currentHP < maxHP) {
            currentHP += num;
        }
    }

    public int getHP() {
        return currentHP;
    }

    public boolean requestColor(Color color) {
        IO.println("choose a " + color + ", 'q' to ignore");
        IO.printCards(cards);
        String order = IO.input(this);
        if (order.equals("q")) {
            return false;
        }

        try {
            Card c = cards.get(Integer.parseInt(order));
            if (c.color() != color) {
                IO.println("Wrong color");
                return requestColor(color);
            }
            throwCard(c);
            return true;
        } catch (NumberFormatException e) {
            IO.println("Wrong input");
            return requestColor(color);
        }

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
        return IO.requestCard("闪", this) != null;
    }

    public Sha requestSha() {
        if (hasEquipment(weapon, "丈八蛇矛") && cards.size() >= 2) {
            if (IO.chooseFromProvided(this, "throw two cards to sha", "pass").equals(
                    "throw two cards to sha")) {
                ArrayList<Card> cs = IO.chooseCards(this, 2, cards);
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
        return (Sha) IO.requestCard("杀", this);
    }

    public boolean requestWuXie() {
        if (skillWuxie()) {
            return true;
        }
        return IO.requestCard("无懈可击", this) != null;
    }

    public boolean requestTao() {
        return IO.requestCard("桃", this) != null;
    }

    public void dying() {
        int needTao = 1 - currentHP;
        if (needTao <= 0) {
            return;
        }
        for (int i = 0; i < needTao; i++) {
            if (!GameManager.askTao()) {
                die();
            }
            currentHP++;
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
        return ((Weapon) equipments.get(weapon)).getDistance();
    }

    public boolean hasEquipment(EquipType type, String name) {
        if (name == null) {
            return equipments.get(type) != null;
        }
        return equipments.get(type).toString().equals(name);
    }

}
