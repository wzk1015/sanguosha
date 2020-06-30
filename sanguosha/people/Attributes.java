package sanguosha.people;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.EquipType;
import sanguosha.cards.Equipment;
import sanguosha.cards.JudgeCard;
import sanguosha.cards.basic.HurtType;
import sanguosha.cards.basic.Sha;
import sanguosha.cards.equipments.Shield;
import sanguosha.cards.equipments.Weapon;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.GameManager;
import sanguosha.manager.IO;

import java.util.ArrayList;
import java.util.HashMap;

import static sanguosha.cards.EquipType.shield;
import static sanguosha.cards.EquipType.weapon;

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

    public void wakeUp() {
        println(this + " wakes up!");
        hasWakenUp = true;
    }

    public boolean hasWakenUp() { return hasWakenUp; }

    public boolean isDaWu() { return isDaWu; }

    public void setDaWu(boolean daWu) { isDaWu = daWu; }

    public boolean isKuangFeng() { return isKuangFeng; }

    public void setKuangFeng(boolean kuangFeng) { isKuangFeng = kuangFeng; }

    public boolean isMyRound() { return myRound; }

    public void setMyRound(boolean myRound) { this.myRound = myRound; }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
        this.currentHP = Math.max(currentHP, maxHP);
    }

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

    public void setDrunk(boolean drunk) {
        isDrunk = drunk;
        if (isDrunk) {
            println(this + " is drunk");
        }
    }

    public boolean isLinked() { return isLinked; }

    public boolean isTurnedOver() {
        return isTurnedOver;
    }

    public void link() {
        isLinked = !isLinked;
        println(this + " is linked, now " + (isLinked ? "连环状态" : "非连环状态"));
    }

    public void turnover() {
        isTurnedOver = !isTurnedOver;
        println(this + " is turned over, now " + (isTurnedOver ? "反面朝上" : "正面朝上"));
    }

    public void die() {
        isDead = true;
        GameManager.die((Person) this);
        if (GameManager.gameIsEnd()) {
            GameManager.endGame();
        }
    }

    public boolean hasNotUsedSkill1() {
        if (hasUsedSkill1) {
            printlnToIO("you have used this skill in this phase");
            return false;
        }
        return true;
    }

    public void setHasUsedSkill1(boolean hasUsedSkill1) {
        this.hasUsedSkill1 = hasUsedSkill1;
    }

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
            ans.add(jc.getThisCard().get(0));
        }
        return ans;
    }

    public void removeJudgeCard(Card c) {
        for (JudgeCard jc: judgeCards) {
            if (jc.getThisCard().get(0) == c) {
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
        addCard(c, true);
    }

    public void addCard(Card c, boolean print) {
        getCards().add(c);
        if (print) {
            println(this + " got 1 card");
            printToIO(this + " got card: ");
            printCard(c);
        }
        c.setOwner((Person) this);
    }

    public void addCard(ArrayList<Card> cs) {
        addCard(cs, true);
    }

    public void addCard(ArrayList<Card> cs, boolean print) {
        println(this + " got " + cs.size() + " cards");
        for (Card c : cs) {
            getCards().add(c);
            if (print) {
                printToIO(this + " got card: ");
                printCards(cs);
            }
            c.setOwner((Person) this);
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
        loseCard(cs, true);
    }

    public void loseCard(ArrayList<Card> cs, boolean throwAway) {
        ArrayList<Card> newCs = new ArrayList<>(cs);
        for (Card c: newCs) {
            loseCard(c, throwAway);
        }
    }

    public void loseCard(Card c) {
        loseCard(c,  true);
    }

    public void loseCard(Card c, boolean throwAway) {
        loseCard(c, throwAway, true);
    }

    public void loseCard(ArrayList<Card> cs, boolean throwAway, boolean print) {
        ArrayList<Card> newCs = new ArrayList<>(cs);
        for (Card c: newCs) {
            loseCard(c, throwAway, print);
        }
    }

    public void loseCard(Card c, boolean throwAway, boolean print) {
        if (getRealJudgeCards().contains(c)
                || getRealJudgeCards().contains(c.getThisCard().get(0))) {
            removeJudgeCard(c);
            if (print && throwAway) {
                print(this + " lost judge card: ");
                IO.printCardPublic(c);
            }
        } else if (c instanceof Equipment && getEquipments().containsValue(c)) {
            getEquipments().remove(((Equipment) c).getEquipType());
            if (c.toString().equals("白银狮子")) {
                recover(1);
            }
            if (!isDead()) {
                lostEquipment();
            }
            if (print && throwAway) {
                print(this + " lost equipment: ");
                IO.printCardPublic(c);
            }
        } else if (getCards().contains(c) || getCards().contains(c.getThisCard().get(0))) {
            getCards().remove(c);
            if (!isDead()) {
                lostCard();
            }
            if (print && throwAway) {
                print(this + " lost hand card: ");
                IO.printCardPublic(c);
            }
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
            println(this + " uses 大雾， hurt dismissed");
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

        if (source != null) {
            println(source + " hurt " + this);
        }
        loseHP(realNum);
        if (type != HurtType.normal && isLinked()) {
            link();
        }
        if (source != null) {
            source.hurtOther((Person) this, realNum);
        }
        for (Person p: GameManager.getPlayers()) {
            p.otherPersonMakeHurt((Person) this);
        }
        if (!isDead()) {
            if (launchSkill("新生")) {
                addHuaShen();
            }
            gotHurt(cs, source, realNum);
        }
        else if (source != null) {
            source.killOther();
            GameManager.deathRewardPunish(getIdentity(), source);
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
        setCurrentHP(Math.min(getHP() + num, getMaxHP()));
        println(this + " recover " + num + "HP, current HP: " + getHP() + "/" + getMaxHP());
    }

    public boolean askTao() {
        boolean wanSha = false;
        Person wanShaPerson = null;
        for (Person p: GameManager.getPlayers()) {
            if (p.hasWanSha()) {
                wanSha = true;
                wanShaPerson = p;
                break;
            }
        }
        println("requesting tao for " + this);
        for (Person p : GameManager.getPlayers()) {
            if (p == this) {
                if (p.requestTao() || p.requestJiu()) {
                    return true;
                }
            }
            else if ((!wanSha && p.requestTao()) || (p == wanShaPerson && p.requestTao())) {
                gotSavedBy(p);
                return true;
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
            } else {
                recover(1);
            }
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
            return (Sha) getEquipments().get(weapon).use();
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
        } if (hasEquipment(shield, "仁王盾") && ((Shield) getEquipments().get(shield)).isValid()) {
            return sha.isBlack();
        }
        return false;
    }

    public abstract void addHuaShen();
}
