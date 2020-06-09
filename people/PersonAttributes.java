package people;

import manager.GameManager;

public abstract class PersonAttributes {
    private boolean isTurnedOver = false;
    private boolean isLinked = false;
    private boolean isDrunk = false;
    private boolean isDead = false;
    private String sex = "male";
    private Nation nation;
    private Identity identity;
    private int maxShaCount = 1;

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

    public boolean isTurnedOver() {
        return isTurnedOver;
    }
}
