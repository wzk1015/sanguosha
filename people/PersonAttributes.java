package people;

import manager.GameManager;

public abstract class PersonAttributes {
    private boolean isTurnedOver = false;
    private boolean isLinked = false;
    private boolean isDrunk = false;
    private boolean isDead = false;
    private String sex = "male";

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
        if (GameManager.gameIsEnd()) {
            GameManager.endGame();
        }
    }

    public boolean isTurnedOver() {
        return isTurnedOver;
    }
}
