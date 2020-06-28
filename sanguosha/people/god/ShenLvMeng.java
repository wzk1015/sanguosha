package sanguosha.people.god;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.IO;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

import java.util.ArrayList;
import java.util.HashSet;

public class ShenLvMeng extends God {
    public ShenLvMeng() {
        super(3, null);
    }

    @Skill("涉猎")
    @Override
    public void drawPhase() {
        if (launchSkill("涉猎")) {
            ArrayList<Card> cards = new ArrayList<>(CardsHeap.getDrawCards(5).subList(0, 5));
            CardsHeap.getDrawCards(0).removeAll(cards);
            ArrayList<Card> selected = new ArrayList<>();
            printCardsPublic(cards);
            while (true) {
                printlnToIO("choose cards of different colors");
                selected = chooseCards(0, cards);
                HashSet<Color> existingColors = new HashSet<>();
                boolean colorDuplicated = false;
                for (Card c: selected) {
                    if (existingColors.contains(c.color())) {
                        colorDuplicated = true;
                        break;
                    }
                    existingColors.add(c.color());
                }
                if (colorDuplicated) {
                    continue;
                }
                addCard(selected);
                cards.removeAll(selected);
                CardsHeap.discard(cards);
                return;
            }
        }
        super.drawPhase();
    }

    @Skill("攻心")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("攻心")) {
            Person p = selectPlayer();
            if (p != null) {
                printCards(p.getCards());
                printlnToIO("choose a HEART card, or q to pass");
                Card c = chooseCard(p.getCards());
                if (c != null) {
                    printCardPublic(c);
                    if (chooseNoNull("throw", "put on top of cards heap").equals("throw")) {
                        p.loseCard(c);
                    } else {
                        p.loseCard(c, false);
                        CardsHeap.getDrawCards(0).add(0, c);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "神吕蒙";
    }
}
