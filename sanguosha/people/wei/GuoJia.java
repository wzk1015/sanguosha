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
    public String toString() {
        return "郭嘉";
    }
}
