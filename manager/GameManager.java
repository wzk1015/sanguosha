package manager;

import cards.Card;
import cards.EquipType;
import cards.Strategy;
import cards.basic.HurtType;
import cards.basic.Sha;
import cards.strategy.GuoHeChaiQiao;
import cards.strategy.HuoGong;
import cards.strategy.JieDaoShaRen;
import cards.strategy.ShunShouQianYang;
import cards.strategy.TaoYuanJieYi;
import cards.strategy.TieSuoLianHuan;
import cards.strategy.WuGuFengDeng;
import cards.strategy.judgecards.ShanDian;
import cardsheap.CardsHeap;
import people.Identity;
import people.Nation;
import people.PeoplePool;
import people.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GameManager {
    private static ArrayList<Person> players = new ArrayList<>();
    private static int numPlayers = 2;
    private static HashMap<Identity, ArrayList<Person>> idMap = new HashMap<>();
    private static ArrayList<Person> winners = new ArrayList<>();
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
            Person selected = selectPlayer(null, options);
            selected.setIdentity(identity);
            players.add(selected);
            idMap.get(identity).add(selected);
        }

        for (Person p: players) {
            p.addCard(CardsHeap.draw(4));
        }

    }
    
    public static void runGame(int num) {
        GameManager.numPlayers = num;
        startGame();


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

    public static void endGame() {
        IO.println(players.get(0) + " wins!");
        System.exit(1);
    }

    public static boolean noWuXie() {
        boolean hasWuXie = false;
        for (Person p : players) {
            for (Card c : p.getCards()) {
                if (c.toString().equals("无懈可击")) {
                    hasWuXie = true;
                    break;
                }
            }
        }
        return !hasWuXie;
    }

    public static boolean askWuXie(Card c) {
        if (noWuXie()) {
            return false;
        }
        IO.println("requesting wuxie for " + c);
        boolean ans = false;
        while (true) {
            boolean mark = false;
            for (Person p : players) {
                if (p.requestWuXie()) {
                    mark = true;
                    ans = !ans;
                    if (noWuXie()) {
                        return ans;
                    }
                    break;
                }
            }
            if (mark) {
                IO.println("requesting wuxie for 无懈可击");
                continue;
            }
            return ans;
        }
    }

    public static Card askChangeJudge() {
        for (Person p: players) {
            Card c = p.changeJudge();
            if (c != null) {
                return c;
            }
        }
        return null;
    }

    public static boolean askTao(Person target) {
        IO.println("requesting tao for " + target);
        for (Person p: players) {
            if (p.requestTao()) {
                target.gotSavedBy(p);
                return true;
            } else if (p == target) {
                if (p.requestCard("酒") != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void nanManRuQin(Card card, Person source) {
        for (Person p: players) {
            if (p != source && !askWuXie(card) && p.requestSha() == null
                    && !p.hasEquipment(EquipType.shield, "藤甲")) {
                p.hurt(card, source, 1);
            }
        }
    }

    public static void wanJianQiFa(ArrayList<Card> thiscard, Card card, Person source) {
        for (Person p: players) {
            if (p != source && !askWuXie(card) && !p.requestShan()
                    && !p.hasEquipment(EquipType.shield, "藤甲")) {
                p.hurt(thiscard, source, 1);
            }
        }
    }

    public static void taoYuanJieYi(TaoYuanJieYi c) {
        for (Person p: players) {
            if (!askWuXie(c)) {
                p.recover(1);
            }
        }
    }

    public static void wuGuFengDeng(WuGuFengDeng wgfd) {
        ArrayList<Card> cards = CardsHeap.draw(numPlayers);
        for (Person p: players) {
            if (!askWuXie(wgfd)) {
                Card c;
                if (cards.size() == 1) {
                    c = cards.get(0);
                } else {
                    c = p.chooseCard(cards);
                }
                p.addCard(c);
                cards.remove(c);
            }
        }
        if (!cards.isEmpty()) {
            CardsHeap.discard(cards);
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

        Card c1 = null;
        Card c2 = null;
        while (c1 == null || c2 == null) {
            c1 = source.requestCard(null);
            c2 = target.requestCard(null);
        }
        return c1.number() > c2.number();
    }

    public static void moveShanDian(ShanDian sd, Person p) {
        int index = players.indexOf(p);
        Utils.assertTrue(index != -1, "shandian target not found");
        index = (index + 1 == numPlayers) ? 0 : index;
        while (!players.get(index + 1).getJudgeCards().add(sd)) {
            index = (index + 1 == numPlayers) ? 0 : index;
        }
    }

    public static void die(Person p) {
        p.throwCard(p.getCards());
        p.throwCard(new ArrayList<>(p.getRealJudgeCards()));
        p.throwCard(new ArrayList<>(p.getEquipments().values()));
        it.remove();
        numPlayers--;
        idMap.get(p.getIdentity()).remove(p);
        IO.println(p + " dead, identity: " + p.getIdentity());
    }

    public static boolean isExtinct(Identity id) {
        return idMap.get(id).isEmpty();
    }

    public static boolean gameIsEnd() {
        Utils.assertTrue(winners.isEmpty(), "winners are not empty");
        if (isExtinct(Identity.KING) && numPlayers > 1) {
            winners.addAll(idMap.get(Identity.REBEL));
            IO.print("REBEL WIN: ");
            for (Person p: winners) {
                IO.print(p.toString());
            }
            IO.println("");
            return true;
        } else if (isExtinct(Identity.KING)) {
            IO.print("TRAITOR WIN: ");
            winners.add(players.get(0));
            IO.println(winners.get(0).toString());
            return true;
        } else if (isExtinct(Identity.TRAITOR) && isExtinct(Identity.REBEL)) {
            IO.print("KING AND MINISTER WIN: ");
            winners.addAll(idMap.get(Identity.MINISTER));
            winners.addAll(idMap.get(Identity.KING));
            for (Person p: winners) {
                IO.print(p.toString());
            }
            IO.println("");
            return true;
        }
        return false;
    }

    public static int calDistance(Person p1, Person p2) {
        if (p1 == p2) {
            return 0;
        }
        int pos1 = players.indexOf(p1);
        int pos2 = players.indexOf(p2);
        int dis = Math.max(pos1 - pos2, pos2 - pos1);
        dis =  Math.min(dis, numPlayers - dis);
        if (p2.hasEquipment(EquipType.plusOneHorse, null)) {
            dis++;
        }
        if (p1.hasEquipment(EquipType.minusOneHorse, null)) {
            dis = Math.max(dis - 1, 1);
        }
        if (p1.hasMaShu()) {
            dis = Math.max(dis - 1, 1);
        }
        return dis;
    }

    public static ArrayList<Person> reachablePeople(Person p1, int distance) {
        ArrayList<Person> ans = new ArrayList<>();
        for (Person p: players) {
            if (calDistance(p1, p) < distance) {
                ans.add(p);
            }
        }
        ans.remove(p1);
        return ans;
    }

    public static ArrayList<Person> peoplefromNation(Nation n) {
        ArrayList<Person> ans = new ArrayList<>();
        for (Person p: players) {
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

    public static boolean askTarget(Card card, Person currentPerson) {
        card.setSource(currentPerson);

        if (!card.needChooseTarget()) {
            card.setTarget(currentPerson);
            return true;
        }

        while (true) {
            if (card instanceof TieSuoLianHuan || card instanceof JieDaoShaRen) {
                Person p1 = selectPlayer(currentPerson, players, true);
                Person p2 = selectPlayer(currentPerson, players, true);
                if (p1 == null || p2 == null) {
                    return false;
                } if (p1 == p2) {
                    IO.println("can't select two same people");
                    continue;
                }
                if (card instanceof JieDaoShaRen && (p1 == currentPerson || p2 == currentPerson)) {
                    IO.println("can't select yourself");
                    continue;
                }
                card.setTarget(p1);
                if (card instanceof TieSuoLianHuan) {
                    ((TieSuoLianHuan) card).setTarget2(p2);
                } else {
                    ((JieDaoShaRen) card).setTarget2(p2);
                }
                return true;
            }

            Person p;
            if (card instanceof HuoGong) {
                p = selectPlayer(currentPerson, players, true);
            } else {
                p = selectPlayer(currentPerson, players);
            }

            if (p == null) {
                return false;
            }
            if (card instanceof Strategy &&
                    calDistance(currentPerson, p) > ((Strategy) card).getDistance()) {
                IO.println("distance unreachable");
                continue;
            }
            if (card instanceof Sha) {
                if (calDistance(currentPerson, p) > currentPerson.getShaDistance()) {
                    IO.println("distance unreachable");
                    continue;
                } else if (p.hasKongCheng() && p.getCards().isEmpty()) {
                    IO.println("can't sha because of 空城");
                    continue;
                }
            }

            if ((card instanceof GuoHeChaiQiao || card instanceof ShunShouQianYang) &&
                p.getCardsAndEquipments().isEmpty()
                    && p.getJudgeCards().isEmpty()) {
                IO.println("you can't chooose a person with no cards");
            }
            card.setTarget(p);
            return true;
        }
    }

    public static Person selectPlayer(Person p, ArrayList<Person> people) {
        return selectPlayer(p, people, false);
    }

    public static Person selectPlayer(Person p, ArrayList<Person> people, boolean chooseSelf) {
        ArrayList<String> options = new ArrayList<>();
        for (Person p1 : people) {
            options.add(p1.toString());
        }
        if (!chooseSelf && p != null) {
            options.remove(p.toString());
        }
        IO.println("choose a player:");
        String option = p != null ? p.chooseFromProvided(options) : IO.chooseFromProvided(options);
        for (Person p1 : people) {
            if (p1.toString().equals(option)) {
                return p1;
            }
        }
        return null;
    }

    public static Person selectPlayer(Person p) {
        return selectPlayer(p, players);
    }

    public static Person selectPlayer(Person p, boolean chooseSelf) {
        return selectPlayer(p, players, chooseSelf);
    }

    public static int getNumPlayers() {
        return numPlayers;
    }

    public static void checkCardsNum() {
        int ans = CardsHeap.getDrawCards(0).size() + CardsHeap.getUsedCards().size();
        for (Person p: players) {
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
