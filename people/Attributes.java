package people;

import cards.Card;
import cards.EquipType;
import cards.Equipment;
import cards.JudgeCard;
import cards.equipments.Weapon;
import manager.GameManager;

import java.util.ArrayList;
import java.util.HashMap;

import static cards.EquipType.weapon;

public abstract class Attributes implements PlayerIO {
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

    public boolean isMyRound() {
        return myRound;
    }

    public void setMyRound(boolean myRound) {
        this.myRound = myRound;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setShaCount(int shaCount) {
        this.shaCount = shaCount;
    }

    public int getShaCount() {
        return shaCount;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public void subCurrentHP(int sub) {
        currentHP -= sub;
    }

    public int getHP() {
        return currentHP;
    }

    public int getMaxShaCount() {
        return maxShaCount;
    }

    public void setMaxShaCount(int maxShaCount) {
        this.maxShaCount = maxShaCount;
    }

    public Nation getNation() {
        return nation;
    }

    public void setNation(Nation nation) {
        this.nation = nation;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isDrunk() {
        return isDrunk;
    }

    public void setDrunk(boolean drunk) {
        isDrunk = drunk;
    }

    public boolean isLinked() {
        return isLinked;
    }

    public void turnover() {
        isTurnedOver = !isTurnedOver;
    }

    public void link() {
        isLinked = !isLinked;
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
}
