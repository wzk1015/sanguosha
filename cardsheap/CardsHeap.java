package cardsheap;

import cards.Card;
import manager.GameManager;
import manager.IO;

import java.util.ArrayList;

public class CardsHeap {
    private static ArrayList<Card> drawCards = new ArrayList<>();
    private static ArrayList<Card> usedCards = new ArrayList<>();

    public static Card draw() {
        Card c = drawCards.get(0);
        drawCards.remove(0);
        return c;
    }

    public static ArrayList<Card> draw(int num) {
        ArrayList<Card> cs = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            cs.add(draw());
        }
        return cs;
    }

    public static void discard(Card c) {
        usedCards.add(0, c);
    }

    public static void discard(ArrayList<Card> cs) {
        usedCards.addAll(0, cs);
    }

    public static Card judge() {
        Card d = draw();
        System.out.print("Judge card: ");
        IO.printCard(d);
        Card change = GameManager.askChangeJudge();
        if (change != null) {
            d = change;
        }
        return d;
    }
}
