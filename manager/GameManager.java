package manager;

import cards.Card;
import cards.EquipType;
import cards.Strategy;
import cards.basic.HurtType;
import cards.basic.Sha;
import cards.strategy.HuoGong;
import cards.strategy.JieDaoShaRen;
import cards.strategy.TieSuoLianHuan;
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
        while (!endGame()) {
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
        //TODO
        return false;
    }

    public static Card askChangeJudge() {
        return null;
    }

    public static boolean askTao() {
        return false;
    }

    public static void linkHurt(int num, HurtType type) {
        for (Person p : players) {
            if (p.isLinked()) {
                p.hurt(num, type);
                p.link();
            }
        }
    }

    public boolean endGame() {
        return false;
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
        if (!card.needChooseTarget()) {
            card.setTarget(currentPerson);
            return true;
        }

        card.setSource(currentPerson);

        while (true) {

            if (card instanceof TieSuoLianHuan || card instanceof JieDaoShaRen) {
                Person p1 = selectPlayer(players);
                Person p2 = selectPlayer(players);
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
            Person p = selectPlayer(players);
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

    public static Person selectPlayer(ArrayList<Person> people) {
        return IO.chooseFromProvided(people);
    }
}
