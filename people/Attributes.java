package people;

import cards.Card;
import cards.Color;
import cards.EquipType;
import cards.Equipment;
import cards.JudgeCard;
import cards.basic.HurtType;
import cards.basic.Sha;
import cards.equipments.Shield;
import cards.equipments.Weapon;
import cardsheap.CardsHeap;
import manager.GameManager;
import manager.IO;

import java.util.ArrayList;
import java.util.HashMap;

import static cards.EquipType.shield;
import static cards.EquipType.weapon;

public abstract class Attributes implements PlayerIO, SkillLauncher {
    private boolean isTurnedOver = false;
    private boolean isLinked = false;
    private boolean isDrunk = false;
    private boolean isDead = false;
    private String sex = "male";
    private Nation nation;
    private Identity identity;
    private int maxShaCount = 1;
    private boolean hasUsedSkill1 = false;
    private final ArrayList<Card> cards = new ArrayList<>();
    private final HashMap<EquipType, Equipment> equipments = new HashMap<>();
    private final ArrayList<JudgeCard> judgeCards = new ArrayList<>();
    private int maxHP = 3;
    private int currentHP;
    private int shaCount = maxShaCount;
    private boolean myRound = false;
    private boolean isKuangFeng = false;
    private boolean isDaWu = false;
    private boolean hasWakenUp = false;

    @Override
    public boolean hasWuShuang() { return false; }

    public void wakeUp() { hasWakenUp = true; }

    public boolean hasWakenUp() { return hasWakenUp; }

    public boolean isDaWu() { return isDaWu; }

    public void setDaWu(boolean daWu) { isDaWu = daWu; }

    public boolean isKuangFeng() { return isKuangFeng; }

    public void setKuangFeng(boolean kuangFeng) { isKuangFeng = kuangFeng; }

    public boolean isMyRound() { return myRound; }

    public void setMyRound(boolean myRound) { this.myRound = myRound; }

    public void setMaxHP(int maxHP) { this.maxHP = maxHP; }

    public int getMaxHP() { return maxHP; }

    public void setShaCount(int shaCount) { this.shaCount = shaCount; }

    public int getShaCount() { return shaCount; }

    public void setCurrentHP(int currentHP) { this.currentHP = currentHP; }

    public void subCurrentHP(int sub) { currentHP -= sub; }

    public int getHP() { return currentHP; }

    public int getMaxShaCount() { return maxShaCount; }

    public void setMaxShaCount(int maxShaCount) { this.maxShaCount = maxShaCount; }

    public Nation getNation() { return nation; }

    public void setNation(Nation nation) { this.nation = nation; }

    public Identity getIdentity() { return identity; }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public String getSex() { return sex; }

    public void setSex(String sex) { this.sex = sex; }

    public boolean isDead() { return isDead; }

    public boolean isDrunk() { return isDrunk; }

    public void setDrunk(boolean drunk) { isDrunk = drunk; }

    public boolean isLinked() { return isLinked; }

    public void turnover() { isTurnedOver = !isTurnedOver; }

    public void link() { isLinked = !isLinked; }

    public void die() {
        isDead = true;
        clearCards();
        GameManager.die((Person) this);
        if (GameManager.gameIsEnd()) {
            GameManager.endGame();
        }
    }

    public boolean hasNotUsedSkill1() {
        if (hasUsedSkill1) {
            println("you have used this skill in this phase");
            return false;
        }
        return true;
    }

    public void setHasUsedSkill1(boolean hasUsedSkill1) {
        this.hasUsedSkill1 = hasUsedSkill1;
    }

    public boolean isTurnedOver() {
        return isTurnedOver;
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

    public ArrayList<Card> getRealJudgeCards() {
        ArrayList<Card> ans = new ArrayList<>();
        for (JudgeCard jc: judgeCards) {
            ans.add(jc.getThisCard());
        }
        return ans;
    }

    public void removeJudgeCard(Card c) {
        for (JudgeCard jc: judgeCards) {
            if (jc.getThisCard() == c) {
                judgeCards.remove(jc);
                return;
            }
        }
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

    public boolean addJudgeCard(JudgeCard c) {
        for (JudgeCard jc: judgeCards) {
            if (jc.toString().equals(c.toString())) {
                return false;
            }
        }
        judgeCards.add(c);
        return true;
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

    public void addCard(Card c) {
        getCards().add(c);
        print(this + " got card: ");
        c.setOwner((Person) this);
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

    public void loseCard(ArrayList<Card> cs, boolean throwAway) {
        for (Card c: cs) {
            loseCard(c, throwAway);
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
        if (isDaWu() && type != HurtType.thunder) {
            return 0;
        }
        int realNum = num;

        if (hasEquipment(shield, "藤甲") && ((Shield) getEquipments().get(shield)).isValid()) {
            if (type == HurtType.fire) {
                realNum++;
            }
        }
        if (isKuangFeng() && type == HurtType.fire) {
            realNum++;
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
        source.hurtOther((Person) this);
        for (Person p: GameManager.getPlayers()) {
            p.otherPersonMakeHurt((Person) this);
        }
        if (!isDead()) {
            gotHurt(cs, source, realNum);
        }
        else if (getIdentity() == Identity.REBEL) {
            source.drawCards(3);
        }
        else if (getIdentity() == Identity.MINISTER && source.getIdentity() == Identity.KING) {
            source.loseCard(source.getCards());
            source.loseCard(new ArrayList<>(source.getEquipments().values()));
        }
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

    public boolean askTao() {
        boolean wanSha = false;
        for (Person p: GameManager.getPlayers()) {
            if (p.hasWanSha()) {
                wanSha = true;
                break;
            }
        }
        IO.println("requesting tao for " + this);
        for (Person p : GameManager.getPlayers()) {
            if ((!wanSha && p.requestTao()) || (p.hasWanSha() && p.requestTao())) {
                gotSavedBy(p);
                return true;
            } else if (p == this) {
                if (p.requestJiu()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void dying() {
        int needTao = 1 - getHP();
        if (needTao <= 0) {
            return;
        }
        for (int i = 0; i < needTao; i++) {
            if (!askTao()) {
                die();
            }
            recover(1);
        }
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

    public boolean requestTao() { return requestCard("桃") != null; }

    public boolean requestJiu() { return requestCard("酒") != null; }

    public boolean canNotBeSha(Sha sha, Person p) {
        if (hasEquipment(shield, "藤甲") && ((Shield) getEquipments().get(shield)).isValid()) {
            if (sha.getType() == HurtType.normal) {
                return true;
            }
        }
        if (hasEquipment(shield, "仁王盾") && ((Shield) getEquipments().get(shield)).isValid()) {
            return sha.isBlack();
        }
        return false;
    }
}
