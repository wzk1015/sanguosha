package manager;

import cards.Card;
import cards.strategy.JieDaoShaRen;
import cards.strategy.NanManRuQin;
import cards.strategy.TaoYuanJieYi;
import cards.strategy.TieSuoLianHuan;
import cards.strategy.WanJianQiFa;
import cards.strategy.WuGuFengDeng;
import people.AI;
import people.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class IO {
    private static final Scanner sn = new Scanner(System.in);

    public static void debug(String s) {
        //println(s);
    }

    public static String input(String s) {
        String ans = "";
        while (ans.isEmpty()) {
            print(s + ": ");
            ans = sn.nextLine();
        }
        return ans;
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

    public static void showUsingCard(Card c) {
        print(c.getSource().toString() + " uses " + c + " towards ");
        if (c instanceof TieSuoLianHuan) {
            println(c.getTarget().toString() + " and " +
                    ((TieSuoLianHuan) c).getTarget2().toString());
        } else if (c instanceof JieDaoShaRen) {
            println(c.getTarget().toString() + " and " +
                    ((JieDaoShaRen) c).getTarget2().toString());
        } else if (c instanceof NanManRuQin || c instanceof WanJianQiFa
                || c instanceof TaoYuanJieYi || c instanceof WuGuFengDeng) {
            println("everyone");
        } else {
            println(c.getTarget().toString());
        }
    }

    public static void printCards(ArrayList<Card> cards) {
        for (int i = 1; i <= cards.size(); i++) {
            println("【" + i + "】 " + cards.get(i - 1).info() + cards.get(i - 1) + " ");
        }
    }

    public static void printCard(Card card) {
        println(card.info() + card);
    }

    public static Card requestRedBlack(Person p, String color) {
        Utils.assertTrue(color.equals("red") || color.equals("black"), "invalid color");
        IO.println("choose a " + color + " card, 'q' to ignore");
        IO.printCards(p.getCards());
        String order = input(p);
        if (order.equals("q")) {
            return null;
        }

        try {
            Card c = p.getCards().get(Integer.parseInt(order) - 1);
            if ((color.equals("red") && c.isBlack()) || (color.equals("black") && c.isRed())) {
                IO.println("Wrong color");
                return requestRedBlack(p, color);
            }
            p.throwCard(c);
            return c;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            IO.println("Wrong input");
            return requestRedBlack(p, color);
        }
    }

    public static Card requestCard(String type, Person p) {
        if (p.getCards().isEmpty()) {
            println(p + "has no cards to choose");
            return null;
        }
        if (type != null) {
            println(p + " choose a " + type + ", 'q' to ignore");
        } else {
            println(p + " choose a card");
        }
        printCards(p.getCards());
        if (p instanceof AI) {
            for (Card c : p.getCards()) {
                if (c.toString().equals(type)) {
                    println("AI uses " + type);
                    p.throwCard(c);
                    return c;
                }
            }
            println("AI passes");
            return null;
        }
        String order = IO.input(p);
        if (order.equals("q")) {
            return null;
        }

        try {
            Card c = p.getCards().get(Integer.parseInt(order) - 1);
            if (type != null && !c.toString().equals(type)) {
                println("Wrong type");
                return requestCard(type, p);
            }
            p.throwCard(c);
            return c;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            println("Wrong input");
            return requestCard(type, p);
        }

    }

    public static ArrayList<Card> printAllCards(Person p) {
        println(p + " have " + p.getCards().size() + " hand card(s)");
        if (!p.getEquipments().isEmpty()) {
            println("equipments: ");
            printCards(new ArrayList<>(p.getEquipments().values()));
        }
        if (!p.getJudgeCards().isEmpty()) {
            println("judge cards: ");
            printCards(new ArrayList<>(p.getJudgeCards()));
        }
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

        int i = 1;
        for (E choice : choices) {
            print("【" + i++ + "】" + choice.toString() + "  ");
        }
        if (p instanceof AI) {
            int option = Utils.randint(0, choices.size() - 1);
            println("AI chooses option " + option);
            return choices.get(option);
        }
        try {
            String in;
            if (p == null) {
                in = input("make a choice");
            } else {
                in = input(p + "make a choice");
            }
            if (in.equals("q")) {
                return null;
            }
            int option = Integer.parseInt(in) - 1;
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

        int i = 1;
        for (E choice : choices) {
            print("【" + i++ + "】 " + choice.toString() + " ");
        }

        if (p instanceof AI) {
            println("AI chooses options 0 - " + num);
            return new ArrayList<>(choices.subList(0, num));
        }
        try {
            String input = input(" choose " + num + " options");
            String[] split = input.split(" ");
            if (split.length != num && num != 0) {
                println("wrong number of choices");
                return chooseManyFromProvided(p, num, choices);
            }
            ArrayList<E> ans = new ArrayList<>();
            for (String s: split) {
                int option = Integer.parseInt(s) - 1;
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

    public static Card chooseAnonymousCard(Person p, ArrayList<Card> cs) {
        Utils.assertTrue(!cs.isEmpty(), "cards are empty");
        if (cs.size() == 1) {
            return cs.get(0);
        }
        ArrayList<String> options = new ArrayList<>();
        for (int i = 0; i < cs.size(); i++) {
            options.add("card" + (i + 1));
        }
        String opt = null;
        while (opt == null) {
            opt = chooseFromProvided(p, options);
        }
        Collections.shuffle(cs);
        return cs.get(Integer.parseInt(opt.replace("card","")) - 1);
    }

    public static boolean launchSkill(Person p, String skillName) {
        return chooseFromProvided(p, skillName, "pass").equals(skillName);
    }
}
