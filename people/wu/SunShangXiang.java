package people.wu;

import cards.Card;
import manager.GameManager;
import people.Nation;
import people.Person;
import skills.Skill;

import java.util.ArrayList;

public class SunShangXiang extends Person {
    public SunShangXiang() {
        super(3, "female", Nation.WU);
    }

    @Skill("枭姬")
    @Override
    public void lostEquipment() {
        if (launchSkill("枭姬")) {
            drawCards(2);
        }
    }

    @Skill("结姻")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (getCards().size() >= 2 && order.equals("结姻") && hasNotUsedSkill1()) {
            println(this + " uses 结姻");
            Person p = GameManager.selectPlayer(this);
            if (p == null) {
                return true;
            }
            if (p.getSex().equals("female")) {
                println("you can't choose female player");
                return true;
            }
            if (p.getHP() == p.getMaxHP()) {
                println("you can't choose player with maxHP");
            }
            ArrayList<Card> cs = chooseCards(2, getCards());
            loseCard(cs);
            this.recover(1);
            p.recover(1);
            setHasUsedSkill1(true);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "孙尚香";
    }
}
