package people.wei;

import cards.Card;
import cardsheap.CardsHeap;
import manager.IO;
import people.Nation;
import people.Person;
import skills.Skill;

public class GuoJia extends Person {
    public GuoJia() {
        super(3, Nation.WEI);
    }

    @Skill("天妒")
    @Override
    public void receiveJudge() {
        if (IO.launchSkill(this, "天妒")) {
            IO.println(this + "uses 天妒");
            addCard(CardsHeap.getJudgeCard());
        }
    }

    @Skill("遗计")
    @Override
    public void gotHurt(Card card, Person source, int num) {
        if (IO.launchSkill(this, "遗计")) {
            IO.println(this + "uses 遗计");
            drawCards(num * 2);
        }
    }

    @Override
    public String toString() {
        return "郭嘉";
    }
}
