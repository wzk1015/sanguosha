package people;

import cards.Card;
import cards.Color;
import cards.EquipType;
import cards.Equipment;
import cards.JudgeCard;
import cards.strategy.JieDaoShaRen;
import cards.strategy.NanManRuQin;
import cards.strategy.TaoYuanJieYi;
import cards.strategy.TieSuoLianHuan;
import cards.strategy.WanJianQiFa;
import cards.strategy.WuGuFengDeng;
import manager.IO;
import manager.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public interface PlayerIO {
    Scanner sn = IO.getScanner();

    default void debug(String s) {
        //println(s);
    }

    default String input(String s) {
        String ans = "";
        while (ans.isEmpty()) {
            print(">>>" + s + ": ");
            ans = sn.nextLine();
        }
        return ans;
    }

    default String input() {
        print(">>> ");
        return sn.nextLine();
    }

    default void println(String s) {
        System.out.println(s);
    }

    default void print(String s) {
        System.out.print(s);
    }

    default void showUsingCard(Card c) {
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

    default void printCards(ArrayList<Card> cards) {
        for (int i = 1; i <= cards.size(); i++) {
            println("【" + i + "】 " + cards.get(i - 1).info() + cards.get(i - 1) + " ");
        }
    }

    default void printCard(Card card) {
        println(card.info() + card);
    }

    default Card requestRedBlack(String color) {
        Utils.assertTrue(color.equals("red") || color.equals("black"), "invalid color");
        println("choose a " + color + " card, 'q' to ignore");
        printCards(getCards());
        String order = input();
        if (order.equals("q")) {
            return null;
        }

        try {
            Card c = getCards().get(Integer.parseInt(order) - 1);
            if ((color.equals("red") && c.isBlack()) || (color.equals("black") && c.isRed())) {
                println("Wrong color");
                return requestRedBlack(color);
            }
            throwCard(c);
            return c;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            println("Wrong input");
            return requestRedBlack(color);
        }
    }

    default Card requestCard(String type) {
        if (getCards().isEmpty()) {
            println(toString() + "has no cards to choose");
            return null;
        }
        if (type != null) {
            println(toString() + " choose a " + type + ", 'q' to ignore");
        } else {
            println(toString() + " choose a card");
        }
        printCards(getCards());
        if (this instanceof AI) {
            for (Card c : getCards()) {
                if (c.toString().equals(type)) {
                    println("AI uses " + type);
                    throwCard(c);
                    return c;
                }
            }
            println("AI passes");
            return null;
        }
        String order = input();
        if (order.equals("q")) {
            return null;
        }

        try {
            Card c = getCards().get(Integer.parseInt(order) - 1);
            if (type != null && !c.toString().equals(type) &&
                    !(type.equals("杀") && (c.toString().equals("火杀")
                            || c.toString().equals("雷杀")))) {
                println("Wrong type");
                return requestCard(type);
            }
            throwCard(c);
            return c;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            println("Wrong input");
            return requestCard(type);
        }

    }

    default void printAllCards() {
        println(toString() + " have " + getCards().size() + " hand card(s)");
        if (!getEquipments().isEmpty()) {
            println("equipments: ");
            printCards(new ArrayList<>(getEquipments().values()));
        }
        if (!getJudgeCards().isEmpty()) {
            println("judge cards: ");
            printCards(new ArrayList<>(getJudgeCards()));
        }
    }

    default <E> E chooseFromProvided(E... choices) {
        ArrayList<E> options = new ArrayList<>(Arrays.asList(choices));
        return chooseFromProvided(options);
    }

    default <E> E chooseFromProvided(ArrayList<E> choices) {
        if (choices.isEmpty()) {
            return null;
        }

        int i = 1;
        for (E choice : choices) {
            print("【" + i++ + "】" + choice.toString() + "  ");
        }
        if (this instanceof AI) {
            int option = Utils.randint(0, choices.size() - 1);
            println("AI chooses option " + option);
            return choices.get(option);
        }
        try {
            String in = input(toString() + "make a choice");
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

    default <E> ArrayList<E> chooseManyFromProvided(int num, E... choices) {
        ArrayList<E> options = new ArrayList<>(Arrays.asList(choices));
        return chooseManyFromProvided(num, options);
    }

    default <E> ArrayList<E> chooseManyFromProvided(int num, ArrayList<E> choices) {
        if (choices.isEmpty()) {
            return null;
        }

        int i = 1;
        for (E choice : choices) {
            print("【" + i++ + "】 " + choice.toString() + " ");
        }

        if (this instanceof AI) {
            println("AI chooses options 0 - " + num);
            return new ArrayList<>(choices.subList(0, num));
        }
        try {
            String input = input(" choose " + num + " options");
            String[] split = input.split(" ");
            if (split.length != num && num != 0) {
                println("wrong number of choices: " + split.length);
                return chooseManyFromProvided(num, choices);
            }
            ArrayList<E> ans = new ArrayList<>();
            for (String s: split) {
                int option = Integer.parseInt(s) - 1;
                if (ans.contains(choices.get(option))) {
                    println("can't choose the same option: " + choices.get(option));
                }
                ans.add(choices.get(option));
            }
            return ans;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            println("wrong choices");
            return chooseManyFromProvided(num, choices);
        }
    }

    default Card chooseCard(ArrayList<Card> cs) {
        return chooseFromProvided(cs);
    }

    default ArrayList<Card> chooseCards(int num, ArrayList<Card> cs) {
        return chooseManyFromProvided(num, cs);
    }

    default Card chooseAnonymousCard(ArrayList<Card> cs) {
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
            opt = chooseFromProvided(options);
        }
        Collections.shuffle(cs);
        return cs.get(Integer.parseInt(opt.replace("card","")) - 1);
    }

    default boolean launchSkill(String skillName) {
        if (chooseFromProvided(skillName, "pass").equals(skillName)) {
            println(this + " uses " + skillName);
            return true;
        }
        return false;
    }

    default boolean requestColor(Color color) {
        println("choose a " + color + " card, 'q' to ignore");
        printCards(getCards());
        String order = input();
        if (order.equals("q")) {
            return false;
        }

        try {
            Card c = getCards().get(Integer.parseInt(order) - 1);
            if (c.color() != color) {
                println("Wrong color");
                return requestColor(color);
            }
            throwCard(c);
            return true;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            println("Wrong input");
            return requestColor(color);
        }

    }
    
    ArrayList<Card> getCards();

    HashMap<EquipType, Equipment> getEquipments();

    ArrayList<JudgeCard> getJudgeCards();
    
    void throwCard(Card c);
}