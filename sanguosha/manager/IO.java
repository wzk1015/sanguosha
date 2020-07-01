package sanguosha.manager;

import gui.GraphicRunner;
import sanguosha.GameLauncher;
import sanguosha.cards.Card;
import sanguosha.cards.strategy.JieDaoShaRen;
import sanguosha.cards.strategy.NanManRuQin;
import sanguosha.cards.strategy.TaoYuanJieYi;
import sanguosha.cards.strategy.TieSuoLianHuan;
import sanguosha.cards.strategy.WanJianQiFa;
import sanguosha.cards.strategy.WuGuFengDeng;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.cardsheap.PeoplePool;
import sanguosha.people.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class IO {
    private static final Scanner sn = new Scanner(System.in);

    public static void debug(String s) {
        //println(s);
    }

    public static Scanner getScanner() {
        return sn;
    }

    public static String input(String s) {
        if (GameLauncher.isGraphic()) {
            printToIO(">>>" + s + " ");
            return GraphicRunner.getInput();
        }
        String ans = "";
        while (ans.isEmpty()) {
            printToIO(">>>" + s + " ");
            ans = sn.nextLine();
        }
        return ans;
    }

    public static String input() {
        return input("");
    }

    public static void println(String s) {
        System.out.println(s);
    }

    public static void print(String s) {
        System.out.print(s);
    }

    public static void printCardPublic(Card card) {
        println(card.info() + card);
    }

    public static void printCardsPublic(ArrayList<Card> cards) {
        for (int i = 1; i <= cards.size(); i++) {
            println("【" + i + "】 " + cards.get(i - 1).info() + cards.get(i - 1) + " ");
        }
    }

    public static int chooseNumber(int min, int max) {
        Utils.assertTrue(min <= max, "min > max");
        for (int i = min; i <= max; i++) {
            printToIO("【" + i + "】");
        }
        printlnToIO("");
        try {
            int num = Integer.parseInt(input("choose a number"));
            if (num < min || num > max) {
                printToIO("out of range");
                return chooseNumber(min, max);
            }
            return num;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            printlnToIO("wrong number");
            return chooseNumber(min, max);
        }
    }

    public static <E> E chooseFromProvided(ArrayList<E> choices) {
        Utils.assertTrue(!choices.isEmpty(), "choices are empty");
        int i = 1;
        for (E choice : choices) {
            printToIO("【" + i++ + "】" + choice.toString() + "  ");
        }
        printlnToIO("");
        try {
            String in = input("make a choice");
            if (in.equals("help")) {
                showHelp("[make a choice]: input number to make your choice");
                return chooseFromProvided(choices);
            }
            int option = Integer.parseInt(in) - 1;
            return choices.get(option);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            printlnToIO("wrong choice");
            return chooseFromProvided(choices);
        }
    }

    public static <E> E chooseFromProvided(E... choices) {
        ArrayList<E> options = new ArrayList<>(Arrays.asList(choices));
        return chooseFromProvided(options);
    }

    public static Person initialChoosePerson(ArrayList<Person> people) {
        Utils.assertTrue(!people.isEmpty(), "initial people are empty");
        ArrayList<String> options = new ArrayList<>();
        for (Person p1 : people) {
            options.add(p1.toString());
        }
        printlnToIO("choose a player:");
        String option = chooseFromProvided(options);
        for (Person p1 : people) {
            if (p1.toString().equals(option)) {
                return p1;
            }
        }
        GameManager.panic("end of initialChoosePlayer reached");
        return people.get(0);
    }
    
    public static void printToIO(String s) {
        if (GameLauncher.isGraphic()) {
            GameManager.addCurrentIOrequest(s);
        } else {
            print(s);
        }
    }
    
    public static void printlnToIO(String s) {
        if (GameLauncher.isGraphic()) {
            GameManager.addCurrentIOrequest(s + "\n");
        } else {
            println(s);
        }
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

    public static void showHelp(String info) {
        printlnToIO(info);
        printlnToIO("\nHow to input: press Enter/OK-button to input," +
                " or 'q'/Cancel-button to quit");
        String type = input("now input name of Card or Person to get information, " +
                "\ne.g. '关羽' / '过河拆桥', or 'q' to quit");
        if (type.equals("q")) {
            return;
        }
        for (Card c: CardsHeap.getAllCards()) {
            if (c.toString().equals(type)) {
                printlnToIO(c + "'s help:\n" + c.help() + "\n");
                return;
            }
        }
        for (Person p: PeoplePool.getAllPeople()) {
            if (p.toString().equals(type)) {
                printlnToIO(p + "'s help:\n" + p.help() + "\n");
                return;
            }
        }
        printlnToIO("unknown help type: " + type);
    }
}
