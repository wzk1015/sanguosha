package sanguosha.manager;

import sanguosha.GameLauncher;
import sanguosha.cards.Card;
import sanguosha.cards.EquipType;
import sanguosha.cards.basic.HurtType;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.people.Identity;
import sanguosha.people.Nation;
import sanguosha.cardsheap.PeoplePool;
import sanguosha.people.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameManager {
    private static final CopyOnWriteArrayList<Person> players = new CopyOnWriteArrayList<>();
    private static int numPlayers = 3;
    private static final HashMap<Identity, ArrayList<Person>> idMap = new HashMap<>();
    private static final HashMap<Identity, ArrayList<Person>> idMapAll = new HashMap<>();
    private static final ArrayList<Person> winners = new ArrayList<>();
    private static final ArrayList<Person> dead = new ArrayList<>();
    private static Person currentPerson = null;
    private static String currentIOrequest = "";
    private static Status gameStatus = Status.preparing;
    private static String panic = "";
    private static int round = 1;

    public static void startGame() {
        IO.println("wzk's sanguosha begins!");
        if (GameLauncher.isCommandLine()) {
            IO.printlnToIO("choose number of players");
            setNumPlayers(IO.chooseNumber(2, 10));
        }

        PeoplePool.init();
        CardsHeap.init();
        idMap.put(Identity.KING, new ArrayList<>());
        idMap.put(Identity.MINISTER, new ArrayList<>());
        idMap.put(Identity.TRAITOR, new ArrayList<>());
        idMap.put(Identity.REBEL, new ArrayList<>());
        idMapAll.put(Identity.KING, new ArrayList<>());
        idMapAll.put(Identity.MINISTER, new ArrayList<>());
        idMapAll.put(Identity.TRAITOR, new ArrayList<>());
        idMapAll.put(Identity.REBEL, new ArrayList<>());

        IO.printlnToIO("player 1, your identity is KING\nchoose your person");
        Person selected = IO.initialChoosePerson(PeoplePool.allocPeopleForKing());
        selected.setIdentity(PeoplePool.allocIdentityForKing());
        players.add(selected);
        idMap.get(Identity.KING).add(selected);
        idMapAll.get(Identity.KING).add(selected);
        if (numPlayers > 4) {
            selected.setMaxHP(selected.getMaxHP() + 1);
        }

        for (int i = 2; i <= numPlayers; i++) {
            Identity identity = PeoplePool.allocIdentity();
            IO.printlnToIO("player " + i + ", your identity is " + identity.toString() +
                    "\nchoose your person");
            selected = IO.initialChoosePerson(PeoplePool.allocPeople());
            selected.setIdentity(identity);
            players.add(selected);
            idMap.get(identity).add(selected);
            idMapAll.get(identity).add(selected);
        }

        for (Person p : players) {
            p.drawCards(4, false);
        }

        for (Person p : players) {
            p.initialize();
        }

        gameStatus = Status.running;
    }

    public static void runGame() {
        try {
            startGame();

            while (!gameIsEnd()) {
                IO.println("round " + round++);
                for (Person p : players) {
                    currentPerson = p;
                    p.run();
                    Utils.checkCardsNum();
                    currentIOrequest = "";
                }
            }
            endGame();

        } catch (NoSuchElementException e) {
            panic("\nno line found");
        }
    }

    public static boolean gameIsEnd() {
        Utils.assertTrue(winners.isEmpty(), "winners are not empty");
        if (isExtinct(Identity.KING) && (!isExtinct(Identity.MINISTER) ||
                !isExtinct(Identity.REBEL) || idMap.get(Identity.TRAITOR).size() > 1)) {
            winners.addAll(idMapAll.get(Identity.REBEL));
            IO.print("REBEL WIN: ");
            for (Person p : winners) {
                IO.print(p.toString());
            }
            IO.println("");
            return true;
        } else if (isExtinct(Identity.KING)) {
            IO.print("TRAITOR WIN: ");
            winners.add(idMap.get(Identity.TRAITOR).get(0));
            IO.println(winners.get(0).toString());
            return true;
        } else if (isExtinct(Identity.TRAITOR) && isExtinct(Identity.REBEL)) {
            IO.print("KING AND MINISTER WIN: ");
            winners.addAll(idMapAll.get(Identity.MINISTER));
            winners.addAll(idMapAll.get(Identity.KING));
            for (Person p : winners) {
                IO.print(p.toString());
            }
            IO.println("");
            return true;
        }
        return false;
    }

    public static void endGame() {
        gameStatus = Status.end;
        if (GameLauncher.isCommandLine()) {
            System.exit(0);
        }
    }

    public static void callItEven() {
        IO.println("call it even");
        endGame();
    }

    public static void panic(String s) {
        panic += "panic at " + Thread.currentThread().getStackTrace()[1].getFileName();
        panic += " line" + Thread.currentThread().getStackTrace()[1].getLineNumber() + ": " + s;
        IO.print(panic);
        gameStatus = Status.error;
        if (GameLauncher.isCommandLine()) {
            System.exit(1);
        }
    }

    public static void linkHurt(ArrayList<Card> cards, Person source, int num, HurtType type) {
        Utils.assertTrue(type == HurtType.fire ||
                type == HurtType.thunder, "link hurt wrong type");
        int realNum = num;
        for (Person p : players) {
            if (p.isLinked()) {
                realNum = p.hurt(cards, source, realNum, type);
            }
        }
    }

    public static boolean pinDian(Person source, Person target) {
        Utils.assertTrue(source.getCards().size() >= 1, "pindian source has no cards");
        Utils.assertTrue(target.getCards().size() >= 1, "pindian target has no cards");

        IO.println(source + " launches 拼点 towards " + target);
        Card c1 = null;
        Card c2 = null;
        while (c1 == null || c2 == null) {
            c1 = source.requestCard(null);
            c2 = target.requestCard(null);
        }
        int num1 = c1.number();
        int num2 = c2.number();
        String yy1 = source.usesYingYang();
        String yy2 = target.usesYingYang();
        if (yy1.equals("+3")) {
            num1 = Math.max(num1 + 3, 13);
        } else if (yy1.equals("-3")) {
            num1 = Math.min(num1 - 3, 1);
        }
        if (yy2.equals("+3")) {
            num2 = Math.max(num2 + 3, 13);
        } else if (yy2.equals("-3")) {
            num2 = Math.min(num2 - 3, 1);
        }
        IO.println((num1 > num2 ? source : target) + " wins");
        if (num1 > num2 && target.usesZhiBa()) {
            target.addCard(c1);
            target.addCard(c2);
        }
        return num1 > num2;
    }

    public static void die(Person p) {
        Person cp = null;
        for (Person p2 : players) {
            if (!p2.isDead() && p2.usesXingShang()) {
                cp = p2;
                break;
            }
        }
        if (p.getExtraCards() != null) {
            CardsHeap.discard(p.getExtraCards());
        }
        if (cp == null) {
            p.loseCard(p.getCards());
            p.loseCard(new ArrayList<>(p.getRealJudgeCards()));
            p.loseCard(new ArrayList<>(p.getEquipments().values()));
        } else {
            p.loseCard(p.getCards(), false);
            p.loseCard(new ArrayList<>(p.getRealJudgeCards()), false);
            p.loseCard(new ArrayList<>(p.getEquipments().values()), false);
            cp.addCard(p.getCards());
            cp.addCard(new ArrayList<>(p.getRealJudgeCards()));
            cp.addCard(new ArrayList<>(p.getEquipments().values()));
        }
        players.remove(p);
        numPlayers--;
        idMap.get(p.getIdentity()).remove(p);
        dead.add(p);
        IO.println(p + " dead, identity: " + p.getIdentity());
    }

    public static void deathRewardPunish(Identity deadIdentity, Person source) {
        if (deadIdentity == Identity.REBEL) {
            source.drawCards(3);
        }
        else if (deadIdentity == Identity.MINISTER && source.getIdentity() == Identity.KING) {
            source.loseCard(source.getCards());
            source.loseCard(new ArrayList<>(source.getEquipments().values()));
        }
    }

    public static boolean isExtinct(Identity id) {
        return idMap.get(id).isEmpty();
    }

    public static int calDistance(Person p1, Person p2) {
        if (p1 == p2) {
            return 0;
        }
        int pos1 = players.indexOf(p1);
        int pos2 = players.indexOf(p2);
        int dis = Math.max(pos1 - pos2, pos2 - pos1);
        dis = Math.min(dis, numPlayers - dis);
        if (p2.hasEquipment(EquipType.plusOneHorse, null)) {
            dis++;
        }
        if (p2.hasFeiYing()) {
            dis++;
        }
        if (p1.hasEquipment(EquipType.minusOneHorse, null)) {
            dis = Math.max(dis - 1, 1);
        }
        if (p1.hasMaShu()) {
            dis = Math.max(dis - 1, 1);
        }
        dis = Math.max(dis - p1.numOfTian(), 1);
        return dis;
    }

    public static ArrayList<Person> reachablePeople(Person p1, int distance) {
        ArrayList<Person> ans = new ArrayList<>();
        for (Person p : players) {
            if (calDistance(p1, p) <= distance) {
                ans.add(p);
            }
        }
        ans.remove(p1);
        return ans;
    }

    public static ArrayList<Person> peoplefromNation(Nation n) {
        ArrayList<Person> ans = new ArrayList<>();
        for (Person p : players) {
            if (p.getNation() == n) {
                ans.add(p);
            }
        }
        return ans;
    }

    public static Person getKing() {
        return idMapAll.get(Identity.KING).get(0);
    }

    public static int getNumPlayers() {
        return numPlayers;
    }

    public static void setNumPlayers(int numPlayers) {
        GameManager.numPlayers = numPlayers;
    }

    public static List<Person> getPlayers() {
        return players;
    }

    public static String getCurrentPlayerStatus() {
        return currentPerson == null ? "" : currentPerson.getPlayerStatus(true, false);
    }

    public static String getOverallStatus() {
        switch (gameStatus) {
            case preparing: return players.isEmpty() ? "preparing" : "KING: " + players.get(0);
            case error: return panic;
            case end: return "end";
            default:
        }
        String ans = "Round " + round + "\n";
        ans += "Cards heap: " + CardsHeap.getDrawCards(1).size() + "\n\n";
        ans += dead.isEmpty() ? "" : "\ndead people: " + dead.size() + " : ";
        for (Person p: dead) {
            ans += p.toString() + "(" + p.getIdentity() + ") ";
        }
        ans += "alive people: " + numPlayers + " : ";
        for (Person p: players) {
            ans += p.toString() + " ";
        }
        ans += "\nKING:    " + idMap.get(Identity.KING).get(0) + "\n";
        ans += "MINISTER: " + idMap.get(Identity.MINISTER).size() + "\n";
        ans += "TRAITOR:  " + idMap.get(Identity.TRAITOR).size() + "\n";
        ans += "REBEL:    " + idMap.get(Identity.REBEL).size() + "\n";
        for (Person p : players) {
            ans += "\n" + p.getPlayerStatus(false, false) + "\n";
        }
        return ans;
    }

    public static String getCurrentIOrequest() {
        return currentIOrequest;
    }

    public static void addCurrentIOrequest(String currentIOrequest) {
        GameManager.currentIOrequest += currentIOrequest;
    }

    public static void clearCurrentIOrequest() {
        currentIOrequest = "";
    }
}
