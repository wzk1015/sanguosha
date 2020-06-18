package manager;

import cards.Card;
import cards.EquipType;
import cards.basic.HurtType;
import cardsheap.CardsHeap;
import people.Identity;
import people.Nation;
import cardsheap.PeoplePool;
import people.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GameManager {
    private static final ArrayList<Person> players = new ArrayList<>();
    private static int numPlayers = 2;
    private static final HashMap<Identity, ArrayList<Person>> idMap = new HashMap<>();
    private static final ArrayList<Person> winners = new ArrayList<>();
    private static Iterator<Person> it;

    public static void startGame() {
        IO.println("wzk's sanguosha begins!");

        PeoplePool.init();
        CardsHeap.init();
        idMap.put(Identity.KING, new ArrayList<>());
        idMap.put(Identity.MINISTER, new ArrayList<>());
        idMap.put(Identity.TRAITOR, new ArrayList<>());
        idMap.put(Identity.REBEL, new ArrayList<>());

        for (int i = 0; i < numPlayers; i++) {
            Identity identity = PeoplePool.allocIdentity();
            ArrayList<Person> options = PeoplePool.allocPeople();
            IO.println("player " + (i + 1) + ", your identity is " + identity.toString() +
                    "\nchoose your person");
            Person selected = IO.initialChoosePerson(options);
            selected.setIdentity(identity);
            players.add(selected);
            idMap.get(identity).add(selected);
        }

        for (Person p : players) {
            p.addCard(CardsHeap.draw(4));
        }

    }

    public static void runGame(int num) {
        GameManager.numPlayers = num;
        startGame();

        if (num > 4) {
            Person king = idMap.get(Identity.KING).get(0);
            king.setMaxHP(king.getMaxHP() + 1);
            king.setCurrentHP(king.getMaxHP());
        }

        for (Person p: players) {
            p.initialize();
        }

        while (!gameIsEnd()) {
            it = players.iterator();
            while (it.hasNext()) {
                Person p = it.next();
                p.run();
                checkCardsNum();
            }
        }
        endGame();
    }

    public static boolean gameIsEnd() {
        Utils.assertTrue(winners.isEmpty(), "winners are not empty");
        if (isExtinct(Identity.KING) && numPlayers > 1) {
            winners.addAll(idMap.get(Identity.REBEL));
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
            winners.addAll(idMap.get(Identity.MINISTER));
            winners.addAll(idMap.get(Identity.KING));
            for (Person p : winners) {
                IO.print(p.toString());
            }
            IO.println("");
            return true;
        }
        return false;
    }

    public static void endGame() {
        System.exit(0);
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

        Card c1 = null;
        Card c2 = null;
        while (c1 == null || c2 == null) {
            c1 = source.requestCard(null);
            c2 = target.requestCard(null);
        }
        return c1.number() > c2.number();
    }

    public static void die(Person p) {
        Person cp = null;
        for (Person p2: players) {
            if (p2.usesXingShang()) {
                cp = p2;
                break;
            }
        }
        if (cp == null) {
            p.loseCard(p.getCards());
            p.loseCard(new ArrayList<>(p.getRealJudgeCards()));
            p.loseCard(new ArrayList<>(p.getEquipments().values()));
        }
        else {
            p.loseCard(p.getCards(), false);
            p.loseCard(new ArrayList<>(p.getRealJudgeCards()),false);
            p.loseCard(new ArrayList<>(p.getEquipments().values()), false);
            cp.addCard(p.getCards());
            cp.addCard(new ArrayList<>(p.getRealJudgeCards()));
            cp.addCard(new ArrayList<>(p.getEquipments().values()));
        }
        it.remove();
        numPlayers--;
        idMap.get(p.getIdentity()).remove(p);
        IO.println(p + " dead, identity: " + p.getIdentity());
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
            if (calDistance(p1, p) < distance) {
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

    public static void callItEven() {
        IO.println("call it even");
        System.exit(0);
    }

    public static void endWithError(String s) {
        IO.println(s);
        System.exit(1);
    }

    public static int getNumPlayers() {
        return numPlayers;
    }

    public static ArrayList<Person> getPlayers() {
        return players;
    }

    public static void checkCardsNum() {
        int ans = CardsHeap.getDrawCards(0).size() + CardsHeap.getUsedCards().size();
        for (Person p : players) {
            ans += p.getCards().size();
            ans += p.getEquipments().size();
            ans += p.getJudgeCards().size();
        }
        if (ans != CardsHeap.getNumCards()) {
            IO.println("card number not consistent");
            for (Person p : players) {
                p.printAllCards();
            }
            endWithError("current number of cards: " + ans +
                    ", expected: " + CardsHeap.getNumCards());
        }
    }
}
