package manager;

import cards.Card;
import people.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class IO {
    private static Scanner sn = new Scanner(System.in);

    public static String input(String s) {
        print(s + ":    ");
        return sn.nextLine();
    }

    public static String input(Person p) {
        return sn.nextLine();
    }

    public static void println(String s) {
        System.out.println(s);
    }

    public static void print(String s) {
        System.out.print(s);
    }

    public static void printCards(ArrayList<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            println(i + ": " + cards.get(i).info() + cards.get(i) + " ");
        }
    }

    public static void printCard(Card card) {
        println(card.info() + card);
    }

    public static Card requestCard(String type, Person p) {
        if (type != null) {
            println("choose a " + type + ", 'q' to ignore");
        } else {
            println("choose a card");
        }
        printCards(p.getCards());
        String order = IO.input(p);
        if (order.equals("q")) {
            return null;
        }

        try {
            Card c = p.getCards().get(Integer.parseInt(order));
            if (type != null && !c.toString().equals(type)) {
                println("Wrong type");
                return requestCard(type, p);
            }
            p.throwCard(c);
            return c;
        } catch (NumberFormatException e) {
            println("Wrong input");
            return requestCard(type, p);
        }

    }

    public static ArrayList<Card> printAllCards(Person p) {
        println(p + "'s cards: ");
        printCards(p.getCards());
        println("equipments: ");
        printCards(new ArrayList<>(p.getEquipments().values()));
        println("judge cards: ");
        printCards(new ArrayList<>(p.getJudgeCards()));

        ArrayList<Card> ans = new ArrayList<>();
        ans.addAll(p.getCards());
        ans.addAll(new ArrayList<>(p.getEquipments().values()));
        ans.addAll(p.getJudgeCards());
        return ans;
    }

    @SafeVarargs
    public static <E> E chooseFromProvided(Person p, E... choices) {
        ArrayList<E> options = new ArrayList<>(Arrays.asList(choices));
        return chooseFromProvided(p, options);
    }

    public static <E> E chooseFromProvided(Person p, ArrayList<E> choices) {
        if (choices.isEmpty()) {
            return null;
        }

        int i = 0;
        for (E choice : choices) {
            print(i++ + ": " + choice.toString());
        }
        try {
            int option = Integer.parseInt(input("make a choice"));
            return choices.get(option);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            println("wrong choice");
            return chooseFromProvided(p, choices);
        }
    }

    @SafeVarargs
    public static <E> ArrayList<E> chooseManyFromProvided(Person p, int num, E... choices) {
        ArrayList<E> options = new ArrayList<>(Arrays.asList(choices));
        return chooseManyFromProvided(p, num, options);
    }

    public static <E> ArrayList<E> chooseManyFromProvided(Person p, int num, ArrayList<E> choices) {
        if (choices.isEmpty()) {
            return null;
        }

        int i = 0;
        for (E choice : choices) {
            print(i++ + ": " + choice.toString());
        }
        try {
            String input = input("choose " + num);
            String[] split = input.split(" ");
            if (split.length != num && num != 0) {
                println("wrong number of choices");
                return chooseManyFromProvided(p, num, choices);
            }
            ArrayList<E> ans = new ArrayList<>();
            for (String s: split) {
                int option = Integer.parseInt(s);
                ans.add(choices.get(option));
            }
            return ans;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            println("wrong choices");
            return chooseManyFromProvided(p, num, choices);
        }
    }

    public static Card chooseCard(Person p, ArrayList<Card> cs) {
        return chooseFromProvided(p, cs);
    }

    public static ArrayList<Card> chooseCards(Person p, int num, ArrayList<Card> cs) {
        return chooseManyFromProvided(p, num, cs);
    }
}
