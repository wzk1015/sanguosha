package sanguosha.people.qun;

import sanguosha.cards.Card;
import sanguosha.manager.GameManager;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

import java.util.ArrayList;
import java.util.Arrays;

public class YuanShu extends Person {

    public YuanShu() {
        super(4, Nation.QUN);
    }

    @Skill("庸肆")
    @Override
    public void drawPhase() {
        println(this + " uses 庸肆");
        int numNations = 0;
        Nation[] nations  = {Nation.WEI, Nation.SHU, Nation.WU, Nation.QUN};
        for (Nation nation: nations) {
            if (!GameManager.peoplefromNation(nation).isEmpty()) {
                numNations++;
            }
        }
        drawCards(2 + numNations);
    }

    @Deprecated
    @Skill("伪帝")
    @Override
    public boolean useSkillInUsePhase(String order) {
        String[] kingSkills = {"激将", "护驾", "救援", "黄天", "血裔", "暴虐", "若愚", "制霸"};
        ArrayList<String> ks = new ArrayList<String>(Arrays.asList(kingSkills));
        if (ks.contains(order)) {
            println("you can't use 伪帝 because I don't want to implement it");
            return true;
        }
        return false;
    }

    @Override
    public void throwPhase() {
        int numNations = 0;
        Nation[] nations  = {Nation.WEI, Nation.SHU, Nation.WU, Nation.QUN};
        for (Nation nation: nations) {
            if (!GameManager.peoplefromNation(nation).isEmpty()) {
                numNations++;
            }
        }
        int num = getCards().size() - getHP() + numNations;
        num = Math.min(num, getCards().size());
        if (num > 0) {
            println(String.format("You need to throw %d sanguosha.cards", num));
            ArrayList<Card> cs = chooseCards(num, getCards());
            loseCard(cs);
            for (Person p: GameManager.getPlayers()) {
                p.otherPersonThrowPhase(this, cs);
            }
        }
    }

    @Override
    public String toString() {
        return "袁术";
    }
}
