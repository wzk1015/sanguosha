package sanguosha.manager;

import gui.GUI;
import sanguosha.CLILauncher;
import sanguosha.cards.Card;
import sanguosha.people.Person;

import java.util.ArrayList;
import java.util.Scanner;

public class IO {
    private static final boolean gui = CLILauncher.isGUI();
    private static final Scanner sn = new Scanner(System.in);

    public static void debug(String s) {
        //println(s);
    }

    public static Scanner getScanner() {
        return sn;
    }

    public static String input(String s) {
        if (gui) {
            printToIO(">>>" + s + ": \n");
            return GUI.getInput();
        }
        String ans = "";
        while (ans.isEmpty()) {
            printToIO(">>>" + s + ": ");
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

    public static void printCard(Card card) {
        printlnToIO(card.info() + card);
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
            int option = Integer.parseInt(in) - 1;
            return choices.get(option);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            printlnToIO("wrong choice");
            return chooseFromProvided(choices);
        }
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
        GameManager.endWithError("end of initialChoosePlayer reached");
        return people.get(0);
    }
    
    public static void printToIO(String s) {
        if (CLILauncher.isGUI()) {
            GameManager.addCurrentIOrequest(s);
        } else {
            print(s);
        }
    }
    
    public static void printlnToIO(String s) {
        if (CLILauncher.isGUI()) {
            GameManager.addCurrentIOrequest(s + "\n");
        } else {
            println(s);
        }
    }
}
