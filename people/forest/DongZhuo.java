package people.forest;

import cards.Color;
import cardsheap.CardsHeap;
import manager.GameManager;
import people.Identity;
import people.Nation;
import people.Person;
import skills.ForcesSkill;
import skills.KingSkill;
import skills.Skill;

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
            if (chooseFromProvided("lose 1 HP", "lose 1 maxHP").equals("lose 1 HP")) {
                loseHP(1);
            } else {
                setMaxHP(getMaxHP() - 1);
                setCurrentHP(Math.max(getHP(), getMaxHP()));
            }
        }
    }

    @KingSkill("暴虐")
    @Override
    public void otherPersonMakeHurt(Person p) {
        if (getHP() < getMaxHP() && getIdentity() == Identity.KING && p.launchSkill("暴虐")) {
            if (CardsHeap.judge(p).color() == Color.SPADE) {
                recover(1);
            }
        }
    }

    @Override
    public String toString() {
        return "董卓";
    }
}
