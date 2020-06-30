package sanguosha.people.wu;

import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

public class LvMeng extends Person {
    private boolean hasSha = false;

    public LvMeng() {
        super(4, Nation.WU);
    }

    @Override
    public void beginPhase() {
        hasSha = false;
    }

    @Override
    public void shaBegin() {
        hasSha = true;
    }

    @Skill("克己")
    @Override
    public void throwPhase() {
        if (!hasSha && launchSkill("克己")) {
            return;
        }
        super.throwPhase();
    }

    @Override
    public String name() {
        return "吕蒙";
    }

    @Override
    public String skillsDescription() {
        return "克己：若你未于出牌阶段内使用或打出过【杀】，你可以跳过弃牌阶段。";
    }
}
