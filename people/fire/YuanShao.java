package people.fire;

import cards.Card;
import cards.strategy.WanJianQiFa;
import manager.GameManager;
import people.Identity;
import people.Nation;
import people.Person;
import skills.ForcesSkill;
import skills.KingSkill;
import skills.Skill;

import java.util.ArrayList;

public class YuanShao extends Person {

    public YuanShao() {
        super(4, Nation.QUN);
    }

    @Skill("乱击")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("乱击")) {
            println(this + " uses 乱击");
            Card c1;
            Card c2;
            do {
                println("choose two cards of same color");
                c1 = chooseCard(getCards());
                c2 = chooseCard(getCards());
            } while (c1 != null && c2 != null && c1.color() != c2.color());
            if (c1 == null || c2 == null) {
                return true;
            }
            ArrayList<Card> thisCard = new ArrayList<>();
            thisCard.add(c1);
            thisCard.add(c2);
            WanJianQiFa wan = new WanJianQiFa(c1.color(), 0);
            wan.setThisCard(thisCard);
            wan.use();
        }
        return false;
    }

    @ForcesSkill("血裔")
    @KingSkill("血裔")
    @Override
    public void throwPhase() {
        if (getIdentity() == Identity.KING) {
            int extra = 2 * GameManager.peoplefromNation(Nation.QUN).size();
            int num = getCards().size() - getHP() - extra;
            if (num > 0) {
                println(String.format("You need to throw %d cards", num));
                ArrayList<Card> cs = chooseCards(num, getCards());
                loseCard(cs);
                for (Person p: GameManager.getPlayers()) {
                    p.otherPersonThrowPhase(this, cs);
                }
            }
        }
        else {
            super.throwPhase();
        }
    }

    @Override
    public String toString() {
        return "袁绍";
    }
}
