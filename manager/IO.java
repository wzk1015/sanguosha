package manager;

import cards.Card;
import people.Person;

import java.util.ArrayList;
import java.util.Scanner;

public class IO {
    private static Scanner sn = new Scanner(System.in);

    public static String input(String s) {
        print(s + ":    ");
        return sn.nextLine();
    }

    public static String input() {
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

    public static Person inputPlayer() {
        //TODO: forbid select dead person
        return null;
    }

    public static Card requestCard(String type, Person p) {
        if (type != null) {
            println("choose a " + type + ", 'q' to ignore");
        } else {
            println("choose a card");
        }
        printCards(p.getCards());
        String order = IO.input();
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

    public static String chooseFromProvided(String... choices) {
        int i = 0;
        for (String choice : choices) {
            print(i++ + ": " + choice);
        }
        try {
            int option = Integer.parseInt(input("make a choice"));
            return choices[option];
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            println("wrong choice");
            return chooseFromProvided(choices);
        }
    }

    public static ArrayList<String> chooseManyFromProvided(int num, String... choices) {
        int i = 0;
        for (String choice : choices) {
            print(i++ + ": " + choice);
        }
        try {
            String input = input("choose " + num);
            String[] split = input.split(" ");
            if (split.length != num && num != 0) {
                println("wrong number of choices");
                return chooseManyFromProvided(num, choices);
            }
            ArrayList<String> ans = new ArrayList<>();
            for (String s: split) {
                int option = Integer.parseInt(s);
                ans.add(choices[option]);
            }
            return ans;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            println("wrong choices");
            return chooseManyFromProvided(num, choices);
        }
    }

    public static Card chooseCard(ArrayList<Card> cs) {
        String[] choices = new String[cs.size()];
        for (int i = 0; i < cs.size(); i++) {
            choices[i] = cs.get(i).info() + cs.get(i).toString();
        }
        String option = chooseFromProvided(choices);
        for (Card c: cs) {
            if ((c.toString() + c.toString()).equals(option)) {
                return c;
            }
        }
        return null;
    }

    public static ArrayList<Card> chooseCards(int num, ArrayList<Card> cs) {
        String[] choices = new String[cs.size()];
        for (int i = 0; i < cs.size(); i++) {
            choices[i] = cs.get(i).info() + cs.get(i).toString();
        }
        ArrayList<String> options = chooseManyFromProvided(num,choices);
        ArrayList<Card> ans = new ArrayList<>();
        for (Card c: cs) {
            for (String option: options) {
                if ((c.toString() + c.toString()).equals(option)) {
                    ans.add(c);
                }
            }
        }
        return ans;
    }
}
