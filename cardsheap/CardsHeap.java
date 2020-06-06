package cardsheap;

import cards.Card;
import cards.Color;
import cards.basic.HurtType;
import cards.basic.Jiu;
import cards.basic.Sha;
import cards.basic.Shan;
import cards.basic.Tao;
import manager.GameManager;
import manager.IO;

import java.util.ArrayList;
import java.util.Collections;

import static cards.Color.CLUB;
import static cards.Color.DIAMOND;
import static cards.Color.HEART;
import static cards.Color.SPADE;

public class CardsHeap {
    private static ArrayList<Card> drawCards = new ArrayList<>();
    private static ArrayList<Card> usedCards = new ArrayList<>();

    public static void addCard(Class<? extends Card> cls, Color color, int num) {
        try {
            Card c = cls.getConstructor(Color.class, int.class).newInstance(color, num);
            IO.debug("color: " + c.color() + " number: " + c.number());
            drawCards.add(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addCard(Class<? extends Card> cls, Color color, int num, HurtType type) {
        try {
            Card c = cls.getConstructor(
                    Color.class, int.class, HurtType.class).newInstance(color, num, type);
            IO.debug("color: " + c.color() + " number: " + c.number());
            drawCards.add(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addCards(Class<? extends Card> cls, Color color, int... numbers) {
        for (int value: numbers) {
            addCard(cls, color, value);
        }
    }

    public static void addCards(Class<? extends Card> cls,HurtType type, Color color, int... numbers) {
        for (int value: numbers) {
            addCard(cls, color, value);
        }
    }

    public static void init() {
        addCards(Sha.class, HEART, 10, 10, 11);
        addCards(Sha.class, DIAMOND, 6, 7, 8, 9, 10, 13);
        addCards(Sha.class, SPADE, 7, 8, 8, 9, 9, 10, 10);
        addCards(Sha.class, CLUB, 2, 3, 4, 5, 6, 7, 8, 8, 9, 9, 10, 10, 11, 11);

        addCards(Shan.class, DIAMOND, 2, 2, 3, 4, 5, 6, 6, 7, 8, 8, 9, 10, 10, 11, 11, 11);
        addCards(Shan.class, HEART, 2, 2, 8, 9, 10, 11, 12, 13);

        addCards(Tao.class, HEART,  3, 4, 5, 6, 7, 8, 9, 12);
        addCards(Tao.class, DIAMOND,  2, 2, 3, 12);

        addCards(Jiu.class, CLUB, 3, 9);
        addCards(Jiu.class, SPADE, 3, 9);
        addCard(Jiu.class, DIAMOND, 9);

        addCards(Sha.class, HurtType.thunder, SPADE, 4, 5, 6, 7, 8);
        addCards(Sha.class, HurtType.thunder, CLUB, 5, 6, 7, 8);
        addCards(Sha.class, HurtType.fire, DIAMOND, 4, 5);
        addCards(Sha.class, HurtType.fire, HEART, 4, 7, 10);

        Collections.shuffle(drawCards);
    }

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
