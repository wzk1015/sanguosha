package sanguosha.people.wei;

import sanguosha.cards.Card;
import sanguosha.cardsheap.CardsHeap;

import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class GuoJia extends Person {
    public GuoJia() {
        super(3, Nation.WEI);
    }

    @Skill("天妒")
    @Override
    public void receiveJudge() {
        if (launchSkill("天妒")) {
            Card c = CardsHeap.getJudgeCard();
            addCard(c);
        }
    }

    @Skill("遗计")
    @Override
    public void gotHurt(ArrayList<Card> cards, Person source, int num) {
        if (launchSkill("遗计")) {
            drawCards(num * 2);
        }
    }

    @Override
    public String name() {
        return "郭嘉";
    }

    @Override
    public String skillsDescription() {
        return "天妒：当你的判定牌生效后，你可以获得此牌。\n" +
                "遗计：当你受到1点伤害后，你可以观看牌堆顶的两张牌，然后将这些牌交给任意角色。";
    }
}
