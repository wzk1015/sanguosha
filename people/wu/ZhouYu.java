package people.wu;

import cards.Card;
import cards.Color;
import people.Nation;
import people.Person;
import skills.Skill;

public class ZhouYu extends Person {
    public ZhouYu() {
        super(3, Nation.WU);
    }

    @Skill("英姿")
    @Override
    public void drawPhase() {
        if (launchSkill("英姿")) {
            drawCards(3);
        }
        else {
            super.drawPhase();
        }
    }

    @Skill("反间")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (getCards().size() > 0 && order.equals("反间") && hasNotUsedSkill1()) {
            println(this + " uses 反间");
            Card c = chooseCard(getCards());
            Person p = selectPlayer();
            if (p == null) {
                return true;
            }
            setHasUsedSkill1(true);
            Color clr = p.chooseFromProvided(Color.SPADE, Color.CLUB, Color.HEART, Color.DIAMOND);
            p.addCard(c);
            if (c.color() != clr) {
                p.hurt((Card) null, this, 1);
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "周瑜";
    }
}
