package people.forest;

import cards.Card;
import cards.Color;
import cardsheap.CardsHeap;
import people.Nation;
import people.Person;
import skills.ForcesSkill;
import skills.Skill;

import java.util.ArrayList;

public class MengHuo extends Person {
    public MengHuo() {
        super(4, Nation.SHU);
    }

    @ForcesSkill("祸首")
    @Override
    public boolean hasHuoShou() {
        println(this + " uses 祸首");
        return true;
    }

    @Skill("再起")
    @Override
    public void drawPhase() {
        if (launchSkill("再起")) {
            ArrayList<Card> cs = CardsHeap.getDrawCards(getMaxHP() - getHP());
            CardsHeap.getDrawCards(0).removeAll(cs);
            println("再起 cards:");
            printCards(cs);
            for (Card c: cs) {
                if (c.color() == Color.HEART) {
                    recover(1);
                    CardsHeap.discard(c);
                } else {
                    addCard(c);
                }
            }
        }

        else {
            super.drawPhase();
        }
    }

    @Override
    public String toString() {
        return "孟获";
    }
}
