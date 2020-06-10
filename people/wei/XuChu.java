package people.wei;

import manager.IO;
import people.Nation;
import people.Person;
import skills.Skill;

public class XuChu extends Person {
    private boolean isNaked = false;

    public XuChu() {
        super(4, Nation.WEI);
    }

    @Skill("裸衣")
    @Override
    public void drawPhase() {
        if (IO.launchSkill(this, "裸衣")) {
            IO.println(this + " uses 裸衣");
            IO.println(this + " draw 1 card from cards heap");
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
    public String toString() {
        return "许褚";
    }
}
