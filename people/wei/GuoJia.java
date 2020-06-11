package people.wei;

import cards.Card;
import cardsheap.CardsHeap;

import people.Nation;
import people.Person;
import skills.Skill;

import java.util.ArrayList;

public class GuoJia extends Person {
    public GuoJia() {
        super(3, Nation.WEI);
    }

    @Skill("天妒")
    @Override
    public void receiveJudge() {
        if (launchSkill("天妒")) {
            addCard(CardsHeap.getJudgeCard());
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
