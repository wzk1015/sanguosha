package sanguosha.people.wei;

import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

public class XuChu extends Person {
    private boolean isNaked = false;

    public XuChu() {
        super(4, Nation.WEI);
    }

    @Skill("裸衣")
    @Override
    public void drawPhase() {
        if (launchSkill("裸衣")) {
            println(this + " draw 1 card from cards heap");
            drawCard();
            isNaked = true;
            return;
        }
        super.drawPhase();
    }

    @Override
    public void endPhase() {
        isNaked = false;
    }

    @Override
    public boolean isNaked() {
        return isNaked;
    }

    @Override
    public String name() {
        return "许褚";
    }

    @Override
    public String skillsDescription() {
        return "裸衣：摸牌阶段，你可以少摸一张牌，然后本回合你使用【杀】或【决斗】造成伤害时，此伤害+1。";
    }
}
