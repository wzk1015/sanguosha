package sanguosha.people.fire;

import sanguosha.cards.Card;
import sanguosha.cards.strategy.WanJianQiFa;
import sanguosha.manager.GameManager;
import sanguosha.people.Identity;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.KingSkill;
import sanguosha.skills.Skill;

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
                printlnToIO("choose two cards of same color");
                c1 = chooseCard(getCards(), true);
                c2 = chooseCard(getCards(), true);
            } while (c1 != null && c2 != null && c1.color() != c2.color());
            if (c1 == null || c2 == null) {
                return true;
            }
            ArrayList<Card> thisCard = new ArrayList<>();
            thisCard.add(c1);
            thisCard.add(c2);
            WanJianQiFa wan = new WanJianQiFa(c1.color(), 0);
            wan.setThisCard(thisCard);
            wan.setSource(this);
            wan.use();
        }
        return false;
    }

    @ForcesSkill("血裔")
    @KingSkill("血裔")
    @Override
    public void throwPhase() {
        if (getIdentity() == Identity.KING) {
            int num = getCards().size() - getHP()
                    - 2 * GameManager.peoplefromNation(Nation.QUN).size();
            if (num > 0) {
                printlnToIO(String.format("You need to throw %d cards", num));
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
    public String name() {
        return "袁绍";
    }

    @Override
    public String skillsDescription() {
        return "乱击：你可以将两张花色相同的手牌当【万箭齐发】使用。\n" +
                "血裔：主公技，锁定技，你的手牌上限+X（X为其他群势力角色数的两倍）。";
    }
}
