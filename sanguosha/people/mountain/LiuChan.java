package sanguosha.people.mountain;

import sanguosha.cards.basic.Sha;
import sanguosha.manager.GameManager;
import sanguosha.people.Identity;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.AfterWakeSkill;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.KingSkill;
import sanguosha.skills.Skill;
import sanguosha.skills.WakeUpSkill;

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
        return p.requestCard(type.replace("throw ", "")) == null;
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
            printlnToIO("choose person to 放权");
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
    public String name() {
        return "刘禅";
    }

    @Override
    public String skillsDescription() {
        return "享乐：锁定技，当你成为一名角色使用【杀】的目标后，除非该角色弃置一张基本牌，否则此【杀】对你无效。\n" +
                "放权：你可以跳过出牌阶段，然后此回合结束时，你可以弃置一张手牌并令一名其他角色获得一个额外的回合。\n" +
                "若愚：主公技，觉醒技，准备阶段，若你是体力值最小的角色，你加1点体力上限，回复1点体力，然后获得\"激将\"。\n" +
                (hasWakenUp() ? "激将：主公技，其他蜀势力角色可以在你需要时代替你使用或打出【杀】。" : "");
    }
}
