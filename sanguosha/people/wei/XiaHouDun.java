package sanguosha.people.wei;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cardsheap.CardsHeap;

import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class XiaHouDun extends Person {
    public XiaHouDun() {
        super(4, Nation.WEI);
    }

    @Skill("刚烈")
    @Override
    public void gotHurt(ArrayList<Card> cards, Person p, int num) {
        if (launchSkill("刚烈")) {
            if (CardsHeap.judge(this).color() != Color.HEART) {
                String option;
                if (p.getCards().size() < 2) {
                    option = "lost 1 HP";
                } else {
                    option = p.chooseNoNull("throw 2 cards", "lost 1 HP");
                }

                if (option.equals("lost 1 HP")) {
                    p.hurt((Card) null, this, 1);
                }
                else {
                    p.loseCard(p.chooseCards(2, p.getCards()));
                }
            }
        }
    }

    @Override
    public String toString() {
        return "夏侯惇";
    }
}
