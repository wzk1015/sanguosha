package sanguosha.people.wei;

import sanguosha.cards.Card;

import sanguosha.cardsheap.CardsHeap;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class SiMaYi extends Person {
    public SiMaYi() {
        super(3, Nation.WEI);
    }

    @Skill("鬼才")
    @Override
    public Card changeJudge(Card d) {
        if (launchSkill("鬼才")) {
            Card c = requestCard(null);
            if (c != null) {
                CardsHeap.retrieve(c);
                CardsHeap.discard(d);
                return c;
            }
        }
        return null;
    }

    @Skill("反馈")
    @Override
    public void gotHurt(ArrayList<Card> cards, Person p, int num) {
        if (p != null && !p.getCardsAndEquipments().isEmpty()
                && launchSkill("反馈")) {
            Card c  = chooseTargetCards(p);
            p.loseCard(c, false);
            addCard(c);
        }
    }

    @Override
    public String name() {
        return "司马懿";
    }

    @Override
    public String skillsDescription() {
        return "反馈：当你受到伤害后，你可以获得伤害来源的一张牌。\n" +
                "鬼才：当一名角色的判定牌生效前，你可以打出一张手牌代替之。";
    }
}
