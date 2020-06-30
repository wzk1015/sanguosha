package sanguosha.people;

import gui.GraphicRunner;
import sanguosha.GameLauncher;
import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.EquipType;
import sanguosha.cards.Equipment;
import sanguosha.cards.JudgeCard;
import sanguosha.cards.basic.Sha;
import sanguosha.manager.GameManager;
import sanguosha.manager.IO;
import sanguosha.manager.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public interface PlayerIO {
    Scanner sn = IO.getScanner();

    default String input(String s) {
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

    default String input() {
        return input("");
    }

    default void print(String s) {
        System.out.print(s);
    }

    default void println(String s) {
        System.out.println(s);
    }

    default void printToIO(String s) {
        if (GameLauncher.isGraphic()) {
            GameManager.addCurrentIOrequest(s);
        } else {
            print(s);
        }
    }

    default void printlnToIO(String s) {
        if (GameLauncher.isGraphic()) {
            GameManager.addCurrentIOrequest(s + "\n");
        } else {
            println(s);
        }
    }

    default void printCards(ArrayList<Card> cards) {
        for (int i = 1; i <= cards.size(); i++) {
            printlnToIO("【" + i + "】 " + cards.get(i - 1).info() + cards.get(i - 1) + " ");
        }
    }

    default void printCard(Card card) {
        printlnToIO(card.info() + card);
    }

    default Card requestRedBlack(String color, boolean fromEquipments) {
        Utils.assertTrue(color.equals("red") || color.equals("black"), "invalid color");
        Card c;
        if (fromEquipments && chooseNoNull("from hand cards", "from equipments")
                .equals("from equipments")) {
            c = chooseCard(new ArrayList<>(getEquipments().values()), true);
            while (c != null && ((color.equals("red") && c.isBlack())
                    || (color.equals("black") && c.isRed()))) {
                printlnToIO("you can't choose black card");
                c = chooseCard(new ArrayList<>(getEquipments().values()), true);
            }
            if (c != null) {
                loseCard(c);
            }
        } else {
            printlnToIO("choose a " + color + " card, 'q' to ignore");
            c = requestCard(null);
            while (c != null && ((color.equals("red") && c.isBlack())
                    || (color.equals("black") && c.isRed()))) {
                addCard(c, false);
                printlnToIO("wrong color");
                c = requestCard(null);
            }
        }
        return c;
    }

    default Card requestRedBlack(String color) {
        return requestRedBlack(color, false);
    }

    default Card requestColor(Color color, boolean fromEquipments) {
        Card c;
        if (fromEquipments && chooseNoNull("from hand cards", "from equipments")
                .equals("from equipments")) {
            c = chooseCard(new ArrayList<>(getEquipments().values()), true);
            while (c != null && c.color() != color) {
                printlnToIO("you can't choose black card");
                c = chooseCard(new ArrayList<>(getEquipments().values()), true);
            }
            if (c != null) {
                loseCard(c);
            }
        } else {
            printlnToIO("choose a " + color + " card, 'q' to ignore");
            c = requestCard(null);
            while (c != null && c.color() != color) {
                addCard(c, false);
                printlnToIO("wrong color");
                c = requestCard(null);
            }
        }
        return c;
    }

    default Card requestColor(Color color) {
        return requestColor(color, false);
    }

    default Card requestCard(String type) {
        if (getCards().isEmpty()) {
            printlnToIO(toString() + "has no cards to choose");
            return null;
        }
        if (type != null) {
            printlnToIO(toString() + " choose a " + type + ", 'q' to ignore");
        } else {
            printlnToIO(toString() + " choose a card");
        }
        printCards(getCards());
        if (this instanceof AI) {
            for (Card c : getCards()) {
                if (c.toString().equals(type)) {
                    println("AI uses " + type);
                    loseCard(c);
                    return c;
                }
            }
            println("AI passes");
            return null;
        }
        String order = input(); // doesn't use chooseFromProvided because need info()
        if (order.equals("q")) {
            return null;
        }
        try {
            Card c = getCards().get(Integer.parseInt(order) - 1);
            if (type != null && !c.toString().equals(type) &&
                    !(type.equals("杀") && c instanceof Sha)) {
                printlnToIO("Wrong type");
                return requestCard(type);
            }
            loseCard(c);
            return c;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            printlnToIO("Wrong input");
            return requestCard(type);
        }
    }

    default String showAllCards() {
        return getPlayerStatus(false, true);
    }

    default <E> E chooseFromProvided(E... choices) {
        ArrayList<E> options = new ArrayList<>(Arrays.asList(choices));
        return chooseFromProvided(options);
    }

    default <E> E chooseFromProvided(ArrayList<E> choices) {
        if (choices.isEmpty()) {
            printlnToIO(this + " has nothing to choose");
            return null;
        }

        int i = 1;
        for (E choice : choices) {
            printToIO("【" + i++ + "】" + choice.toString() + "  ");
        }
        printlnToIO("");
        if (this instanceof AI) {
            int option = Utils.randint(0, choices.size() - 1);
            println("AI chooses option " + option);
            return choices.get(option);
        }
        try {
            String in = input(toString() + " make a choice");
            if (in.equals("q")) {
                return null;
            }
            int option = Integer.parseInt(in) - 1;
            return choices.get(option);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            printlnToIO("wrong choice");
            return chooseFromProvided(choices);
        }
    }

    default <E> E chooseNoNull(E... choices) {
        E ans = null;
        do {
            ans = chooseFromProvided(choices);
        } while (ans == null);
        return ans;
    }

    default <E> E chooseNoNull(ArrayList<E> choices) {
        E ans = null;
        do {
            ans = chooseFromProvided(choices);
        } while (ans == null);
        return ans;
    }

    default <E> ArrayList<E> chooseManyFromProvided(int num, List<E> choices) {
        if (choices.isEmpty() || (num != 0 && num > choices.size())) {
            printlnToIO(this + " has not enough options to choose");
            return new ArrayList<>();
        }

        int i = 1;
        for (E choice : choices) {
            printToIO("【" + i++ + "】 " + choice.toString() + " ");
        }
        printlnToIO("");

        if (this instanceof AI) {
            println("AI chooses options 0 - " + num);
            return new ArrayList<>(choices.subList(0, num));
        }
        try {
            String input = input(this + " choose " +
                    (num == 0 ? "several" : num)  + " options, or q");
            String[] split = input.split(" ");
            if (split.length != num && num != 0) {
                printlnToIO("wrong number of choices: " + split.length);
                return chooseManyFromProvided(num, choices);
            }
            ArrayList<E> ans = new ArrayList<>();
            if (input.equals("q") && num == 0) {
                return ans;
            }
            for (String s: split) {
                int option = Integer.parseInt(s) - 1;
                if (ans.contains(choices.get(option))) {
                    printlnToIO("can't choose the same option: " + s);
                    return chooseManyFromProvided(num, choices);
                }
                ans.add(choices.get(option));
            }
            return ans;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            printlnToIO("wrong choices");
            return chooseManyFromProvided(num, choices);
        }
    }

    default Card chooseCard(ArrayList<Card> cs) {
        return chooseCard(cs, false);
    }

    default Card chooseCard(ArrayList<Card> cs, boolean allowNull) {
        if (allowNull) {
            return chooseFromProvided(cs);
        }
        return chooseNoNull(cs);
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
        String opt = chooseNoNull(options);
        Collections.shuffle(cs);
        return cs.get(Integer.parseInt(opt.replace("card","")) - 1);
    }

    default boolean launchSkill(String skillName) {
        String choice = chooseFromProvided(skillName, "pass");
        if (choice != null && choice.equals(skillName)) {
            println(this + " uses " + skillName);
            return true;
        }
        return false;
    }

    default Card chooseTargetCards(Person target, boolean includeJudge) {
        printlnToIO("choose a card from " + target);
        printlnToIO(target.showAllCards());
        String option;
        if (includeJudge && !target.getEquipments().isEmpty()
                && !target.getRealJudgeCards().isEmpty()) {
            option = chooseNoNull("hand cards", "equipments", "judge cards");
        } else if (!target.getEquipments().isEmpty()) {
            option = chooseNoNull("hand cards", "equipments");
        } else if (includeJudge && !target.getRealJudgeCards().isEmpty()) {
            option = chooseNoNull("hand cards", "judge cards");
        } else {
            option = "hand cards";
        }
        Card c = null;
        while (c == null) {
            if (option.equals("hand cards")) {
                c = chooseAnonymousCard(target.getCards());
            } else if (option.equals("equipments")) {
                c = chooseCard(new ArrayList<>(target.getEquipments().values()), false);
            } else {
                c = chooseCard(new ArrayList<>(target.getRealJudgeCards()), false);
            }
        }
        return c;
    }

    default Card chooseTargetCards(Person target) {
        return chooseTargetCards(target, false);
    }

    default Person selectPlayer(List<Person> people, boolean chooseSelf) {
        ArrayList<String> options = new ArrayList<>();
        for (Person p1 : people) {
            options.add(p1.toString());
        }
        if (!chooseSelf) {
            options.remove(toString());
        }
        printlnToIO("choose a player:");
        String option = chooseFromProvided(options);
        for (Person p1 : people) {
            if (p1.toString().equals(option)) {
                return p1;
            }
        }
        return null;
    }

    default Person selectPlayer(List<Person> people) {
        return selectPlayer(people, false);
    }

    default Person selectPlayer() {
        return selectPlayer(GameManager.getPlayers());
    }

    default Person selectPlayer(boolean chooseSelf) {
        return selectPlayer(GameManager.getPlayers(), chooseSelf);
    }

    ArrayList<Card> getCards();

    HashMap<EquipType, Equipment> getEquipments();

    ArrayList<JudgeCard> getJudgeCards();

    ArrayList<Card> getRealJudgeCards();

    Identity getIdentity();

    int getHP();

    int getMaxHP();

    void loseCard(Card c);

    boolean hasWakenUp();

    boolean hasWuShuang();

    boolean isDrunk();

    boolean isDaWu();

    boolean isKuangFeng();

    boolean isTurnedOver();

    boolean isLinked();

    String getPlayerStatus(boolean privateAccess, boolean onlyCards);

    HashSet<String> getSkills();

    void addCard(Card c,boolean print);
}
