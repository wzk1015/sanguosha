package people.mountain;

import cards.basic.Sha;
import manager.GameManager;
import people.Identity;
import people.Nation;
import people.Person;
import skills.AfterWakeSkill;
import skills.ForcesSkill;
import skills.KingSkill;
import skills.Skill;
import skills.WakeUpSkill;

import java.util.ArrayList;

public class LiuChan extends Person {
    private boolean fangQuan = false;

    public LiuChan() {
        super(3, Nation.SHU);
    }

    @ForcesSkill("享乐")
    @Override
    public boolean canNotBeSha(Sha sha, Person p) {
        if (super.canNotBeSha(sha, p)) {
            return true;
        }
        println(this + " uses 享乐");
        String type = p.chooseFromProvided("throw 杀", "throw 闪", "throw 桃", "throw 酒");
        if (type == null) {
            return true;
        }
        return p.requestCard(type) == null;
    }

    @Skill("放权")
    @Override
    public void usePhase() {
        if (launchSkill("放权")) {
            fangQuan = true;
            return;
        }
        super.usePhase();
    }

    @Override
    public void endPhase() {
        if (fangQuan) {
            println("choose person to 放权");
            Person p = selectPlayer();
            if (p == null) {
                return;
            }
            if (requestCard(null) == null) {
                return;
            }
            fangQuan = false;
            p.run();
        }
    }

    @KingSkill("若愚")
    @WakeUpSkill("若愚")
    @Override
    public void beginPhase() {
        if (getIdentity() == Identity.KING && !hasWakenUp()) {
            boolean isLowest = true;
            for (Person p: GameManager.getPlayers()) {
                if (p.getHP() < getHP()) {
                    isLowest = false;
                    break;
                }
            }
            if (isLowest) {
                println(this + " uses 若愚");
                setMaxHP(getMaxHP() + 1);
                recover(1);
                wakeUp();
            }
        }
    }

    @AfterWakeSkill("激将")
    public Sha jiJiang() {
        ArrayList<Person> shuPeople = GameManager.peoplefromNation(Nation.SHU);
        shuPeople.remove(this);
        if (shuPeople.isEmpty()) {
            println("no 蜀 people available");
            return null;
        }
        for (Person p : shuPeople) {
            Sha sha = p.requestSha();
            if (sha != null) {
                println(p + " answers 激将 from " + this);
                return sha;
            }
        }
        return null;
    }

    @Override
    public boolean skillSha() {
        if (getIdentity() == Identity.KING && hasWakenUp() && launchSkill("激将")) {
            return jiJiang() != null;
        }
        return false;
    }

    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("激将") && hasWakenUp() && getIdentity() == Identity.KING) {
            Sha sha = jiJiang();
            useCard(sha);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "刘禅";
    }
}
