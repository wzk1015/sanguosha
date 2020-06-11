package manager;

import cards.Card;

import java.util.ArrayList;
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
        String ans = "";
        while (ans.isEmpty()) {
            print(">>>" + s + ": ");
            ans = sn.nextLine();
        }
        return ans;
    }

    public static String input() {
        print(">>> ");
        return sn.nextLine();
    }

    public static void println(String s) {
        System.out.println(s);
    }

    public static void print(String s) {
        System.out.print(s);
    }

    public static void printCard(Card card) {
        println(card.info() + card);
    }

    public static <E> E chooseFromProvided(ArrayList<E> choices) {
        if (choices.isEmpty()) {
            return null;
        }

        int i = 1;
        for (E choice : choices) {
            print("【" + i++ + "】" + choice.toString() + "  ");
        }
        try {
            String in = input("make a choice");
            if (in.equals("q")) {
                return null;
            }
            int option = Integer.parseInt(in) - 1;
            return choices.get(option);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            println("wrong choice");
            return chooseFromProvided(choices);
        }
    }
}
