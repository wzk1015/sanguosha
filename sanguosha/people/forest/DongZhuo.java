package sanguosha.people.forest;

import sanguosha.cards.Color;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.GameManager;
import sanguosha.people.Identity;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.KingSkill;
import sanguosha.skills.Skill;

public class DongZhuo extends Person {
    public DongZhuo() {
        super(8, Nation.QUN);
    }

    @Skill("酒池")
    public boolean jiuChi() {
        return requestColor(Color.SPADE) != null;
    }

    @ForcesSkill("肉林")
    @Override
    public boolean hasRouLin() {
        println(this + " uses 肉林");
        return true;
    }

    @Override
    public boolean requestJiu() {
        if (launchSkill("酒池")) {
            if (jiuChi()) {
                return true;
            }
        }
        return super.requestJiu();
    }

    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("酒池") && !isDrunk()) {
            println(this + " uses 酒池");
            if (jiuChi()) {
                setDrunk(true);
            }
            return true;
        }
        return false;
    }

    @ForcesSkill("崩坏")
    @Override
    public void endPhase() {
        boolean isLowest = true;
        for (Person p: GameManager.getPlayers()) {
            if (p.getHP() < getHP()) {
                isLowest = false;
                break;
            }
        }
        if (!isLowest) {
            println(this + " uses 崩坏");
            if (chooseNoNull("lose 1 HP", "lose 1 maxHP").equals("lose 1 HP")) {
                loseHP(1);
            } else {
                setMaxHP(getMaxHP() - 1);
            }
        }
    }

    @KingSkill("暴虐")
    @Override
    public void otherPersonMakeHurt(Person p) {
        if (getHP() < getMaxHP() && getIdentity() == Identity.KING && p.getNation() == Nation.QUN
                && p.launchSkill("暴虐")) {
            if (CardsHeap.judge(p).color() == Color.SPADE) {
                recover(1);
            }
        }
    }

    @Override
    public String name() {
        return "董卓";
    }

    @Override
    public String skillsDescription() {
        return "酒池：你可以将一张黑桃手牌当【酒】使用。\n" +
                "肉林：锁定技，你对女性角色使用的【杀】和女性角色对你使用的【杀】均需使用两张【闪】才能抵消。\n" +
                "崩坏：锁定技，结束阶段，若你不是体力值最小的角色，你失去1点体力或减1点体力上限。\n" +
                "暴虐：主公技，当其他群势力角色造成伤害后，其可以进行判定，若结果为黑桃，你回复1点体力。";
    }
}
