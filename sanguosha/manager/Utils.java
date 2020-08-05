package sanguosha.manager;

import sanguosha.cardsheap.CardsHeap;
import sanguosha.people.Person;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {
    public static void assertTrue(boolean bool, String s) {
        if (!bool) {
            GameManager.panic("assertion failed: " + s);
        }
    }

    public static <T> ArrayList<T> deepCopy(List<T> src) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);

            return new ArrayList<>((List<T>) in.readObject());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public static int randint(int num1, int num2) {
        Random r = new Random();
        return num1 + r.nextInt(num2 - num1 + 1);
    }

    public static <E> E choice(List<E> options) {
        return options.get(randint(0, options.size() - 1));
    }
    
    public static void checkCardsNum() {
        int ans = CardsHeap.getDrawCards(0).size() + CardsHeap.getUsedCards().size();
        for (Person p : GameManager.getPlayers()) {
            ans += p.getCards().size();
            ans += p.getEquipments().size();
            ans += p.getJudgeCards().size();
            ans += p.getExtraCards() == null ? 0 : p.getExtraCards().size();
        }
        if (ans != CardsHeap.getNumCards()) {
            IO.println("card number not consistent");
            for (Person p : GameManager.getPlayers()) {
                IO.println(p.showAllCards());
            }
            GameManager.panic("current number of cards: " + ans
                    + ", expected: " + CardsHeap.getNumCards());
        }
    }
}
