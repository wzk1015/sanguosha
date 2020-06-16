package people.god;

import cards.Card;
import cardsheap.CardsHeap;
import manager.GameManager;
import people.Person;
import skills.Skill;

import java.util.ArrayList;

public class ShenZhuGeLiang extends Person {
    private final ArrayList<Card> stars = new ArrayList<>();

    public ShenZhuGeLiang() {
        super(3, null);
    }

    @Skill("七星")
    public void qiXing() {
        ArrayList<Card> handCards;
        do {
            println("choose cards to exchange with stars");
            handCards = chooseCards(0, getCards());
        } while (handCards.size() > stars.size());
        int num = handCards.size();
        if (num != 0) {
            println("choose stars to exchange");
            ArrayList<Card> starCards = chooseCards(num, stars);
            getCards().removeAll(handCards);
            stars.removeAll(starCards);
            getCards().addAll(starCards);
            stars.addAll(handCards);
        }
    }

    @Skill("狂风")
    public void kuangFeng() {
        Person p = GameManager.selectPlayer(this, true);
        if (p == null) {
            return;
        }
        Card c = chooseCard(stars);
        if (c == null) {
            return;
        }
        stars.remove(c);
        CardsHeap.discard(c);
        p.setKuangFeng(true);
    }

    @Skill("大雾")
    public void daWu() {
        ArrayList<Person> people;
        int num;
        do {
            println("choose players");
            people = chooseManyFromProvided(0, GameManager.getPlayers());
            num = people.size();
        } while (num > stars.size());
        if (num == 0) {
            return;
        }
        ArrayList<Card> starCards = chooseCards(num, stars);
        stars.removeAll(starCards);
        CardsHeap.discard(starCards);
        for (Person p: people) {
            p.setDaWu(true);
        }
    }

    @Override
    public void initialize() {
        super.initialize();
        println(this + " uses 七星");
        stars.addAll(CardsHeap.draw(7));
        qiXing();
    }

    @Override
    public void beginPhase() {
        for (Person p: GameManager.getPlayers()) {
            p.setKuangFeng(false);
            p.setDaWu(false);
        }
    }

    @Override
    public void drawPhase() {
        super.drawPhase();
        if (launchSkill("七星")) {
            qiXing();
        }
    }

    @Override
    public void endPhase() {
        if (launchSkill("狂风")) {
            kuangFeng();
        }
        if (launchSkill("大雾")) {
            daWu();
        }
        super.endPhase();
    }

    @Override
    public void die() {
        super.die();
        for (Person p: GameManager.getPlayers()) {
            p.setKuangFeng(false);
            p.setDaWu(false);
        }
    }

    @Override
    public String toString() {
        return "神诸葛亮";
    }
}
