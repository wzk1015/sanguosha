package manager;

import cards.Card;
import cards.EquipType;
import cards.Strategy;
import cards.basic.HurtType;
import cards.basic.Sha;
import cards.strategy.HuoGong;
import cards.strategy.JieDaoShaRen;
import cards.strategy.TieSuoLianHuan;
import cardsheap.CardsHeap;
import people.Person;

import java.util.ArrayList;

public class GameManager {
    private static ArrayList<Person> players;
    private static int numPlayers;

    public void runGame() {
        players = new ArrayList<>();
        //TODO: intiallize CardsHeap and players;
        //initailize cards and identity

        numPlayers = players.size();
        while (!gameEnd()) {
            for (Person p : players) {
                p.run();
                if (p.isDead()) {
                    players.remove(p);
                    numPlayers--;
                }
            }
        }
    }

    public static boolean requestWuXie() {
        while (true) {
            boolean mark = false;
            for (Person p : players) {
                if (p.requestWuXie()) {
                    mark = true;
                    break;
                }
            }
            if (mark) {
                continue;
            }
            return false;
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

    public static boolean askTao() {
        for (Person p: players) {
            if (p.requestTao()) {
                return true;
            }
        }
        return false;
    }

    public static void nanManRuQin(Person source) {
        for (Person p: players) {
            if (!requestWuXie() && p.requestSha() == null && !p.hasEquipment(EquipType.shield, "藤甲")) {
                p.hurt(source, 1);
            }
        }
    }

    public static void wanJianQiFa(Person source) {
        for (Person p: players) {
            if (!requestWuXie() && !p.requestShan() && !p.hasEquipment(EquipType.shield, "藤甲")) {
                p.hurt(source, 1);
            }
        }
    }

    public static void taoYuanJieYi() {
        for (Person p: players) {
            if (!requestWuXie()) {
                p.recover(1);
            }
        }
    }

    public static void wuGuFengDeng() {
        ArrayList<Card> cards = CardsHeap.draw(8);
        for (Person p: players) {
            if (!requestWuXie()) {
                Card c = IO.chooseCard(p, cards);
                p.addCard(c);
                cards.remove(c);
            }
        }
        if (!cards.isEmpty()) {
            CardsHeap.discard(cards);
        }
    }

    public static void linkHurt(Person source, int num, HurtType type) {
        Utils.assertTrue(type == HurtType.fire ||
                type == HurtType.thunder, "link hurt wrong type");
        int realNum = num;
        for (Person p : players) {
            if (p.isLinked()) {
                realNum = p.hurt(source, realNum, type);
            }
        }
    }

    public boolean gameEnd() {
        //TODO
        return players.isEmpty();
    }

    public static int calDistance(Person p1, Person p2) {
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

    public static boolean askTarget(Card card, Person currentPerson) {
        card.setSource(currentPerson);

        if (!card.needChooseTarget()) {
            card.setTarget(currentPerson);
            return true;
        }

        while (true) {
            if (card instanceof TieSuoLianHuan || card instanceof JieDaoShaRen) {
                Person p1 = selectPlayer(currentPerson, players);
                Person p2 = selectPlayer(currentPerson, players);
                if (p1 == null || p2 == null) {
                    return false;
                }
                card.setTarget(p1);
                if (card instanceof TieSuoLianHuan) {
                    ((TieSuoLianHuan) card).setTarget2(p2);
                } else {
                    ((JieDaoShaRen) card).setTarget2(p2);
                }
            }
            Person p = selectPlayer(currentPerson, players);
            if (p == null) {
                return false;
            }
            if (card instanceof Strategy &&
                    calDistance(currentPerson, p) > ((Strategy) card).getDistance()) {
                IO.println("distance unreachable");
                continue;
            }
            if (card instanceof Sha
                    && calDistance(currentPerson, p) > currentPerson.getShaDistance()) {
                IO.println("distance unreachable");
                continue;
            }
            if (!(card instanceof HuoGong) && currentPerson.equals(p)) {
                IO.println("you can't select yourself");
            }
            card.setTarget(p);
            return true;
        }
    }

    public static Person selectPlayer(Person p, ArrayList<Person> people) {
        return IO.chooseFromProvided(p, people);
    }
}
