package sanguosha.people.mountain;

import sanguosha.cards.Card;
import sanguosha.cards.basic.Sha;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.AfterWakeSkill;
import sanguosha.skills.Skill;
import sanguosha.skills.WakeUpSkill;

public class SunCe extends Person {
    public SunCe() {
        super(4, Nation.WU);
    }

    @Skill("激昂")
    public void jiAng(Card c) {
        if (c instanceof Sha && c.isRed() && launchSkill("激昂")) {
            drawCard();
        }
    }

    @Override
    public void gotShaBegin(Sha sha) {
        jiAng(sha);
    }

    @Override
    public boolean useSha(Card card) {
        if (super.useSha(card)) {
            jiAng(card);
            return true;
        }
        return false;
    }

    @Override
    public void jueDouBegin() {
        if (launchSkill("激昂")) {
            drawCard();
        }
    }

    @Skill("鹰扬")
    @Override
    public String usesYingYang() {
        if (launchSkill("鹰扬")) {
            String option = chooseFromProvided("+3", "-3");
            if (option != null) {
                return option;
            }
        }
        return "";
    }

    @WakeUpSkill("魂姿")
    public void hunZi() {
        setMaxHP(getMaxHP() - 1);
        wakeUp();
    }

    @Override
    public void beginPhase() {
        if (!hasWakenUp() && getHP() == 1) {
            hunZi();
        }
        if (hasWakenUp()) {
            yingHun();
        }
    }

    @AfterWakeSkill("英姿")
    @Override
    public void drawPhase() {
        if (hasWakenUp() && launchSkill("英姿")) {
            drawCards(3);
        }
        else {
            super.drawPhase();
        }
    }

    @AfterWakeSkill("英魂")
    public void yingHun() {
        if (getHP() < getMaxHP() && launchSkill("英魂")) {
            int x = getMaxHP() - getHP();
            String op1 = "draw " + x + ", throw 1";
            String op2 = "draw 1, throw " + x;
            Person p = selectPlayer();
            if (p != null) {
                if (chooseNoNull(op1, op2).equals(op1)) {
                    p.drawCards(x);
                    p.loseCard(p.chooseCards(1, p.getCardsAndEquipments()));
                }
                else {
                    p.drawCard();
                    int num = p.getCardsAndEquipments().size();
                    if (num <= x) {
                        p.loseCard(p.getCardsAndEquipments());
                    } else {
                        p.loseCard(p.chooseCards(x, p.getCardsAndEquipments()));
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "孙策";
    }
}
