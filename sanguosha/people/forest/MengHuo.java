package sanguosha.people.forest;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.IO;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.Skill;

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
            ArrayList<Card> cs = new ArrayList<>(CardsHeap.getDrawCards(getMaxHP() - getHP())
                    .subList(0, getMaxHP() - getHP()));
            CardsHeap.getDrawCards(0).removeAll(cs);
            println("再起 cards:");
            printCardsPublic(cs);
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
