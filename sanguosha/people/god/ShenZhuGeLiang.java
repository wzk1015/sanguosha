package sanguosha.people.god;

import sanguosha.cards.Card;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.GameManager;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class ShenZhuGeLiang extends God {
    private final ArrayList<Card> stars = new ArrayList<>();

    public ShenZhuGeLiang() {
        super(3, null);
    }

    @Skill("七星")
    public void qiXing() {
        ArrayList<Card> handCards;
        printlnToIO("current stars: ");
        printCards(stars);
        do {
            printlnToIO("choose cards to exchange with stars");
            handCards = chooseCards(0, getCards());
        } while (handCards.size() > stars.size());
        int num = handCards.size();
        if (num != 0) {
            printlnToIO("choose stars to exchange");
            ArrayList<Card> starCards = chooseCards(num, stars);
            getCards().removeAll(handCards);
            stars.removeAll(starCards);
            getCards().addAll(starCards);
            stars.addAll(handCards);
        }
    }

    @Skill("狂风")
    public void kuangFeng() {
        Person p = selectPlayer(true);
        if (p == null) {
            return;
        }
        Card c = chooseCard(stars, true);
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
            printlnToIO("choose players");
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
        if (launchSkill("狂风") && stars.size() > 0) {
            kuangFeng();
        }
        if (launchSkill("大雾") && stars.size() > 0) {
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
    public ArrayList<Card> getExtraCards() {
        return stars;
    }

    @Override
    public String getExtraInfo() {
        return stars.size() + " stars";
    }

    @Override
    public String name() {
        return "神诸葛亮";
    }

    @Override
    public String skillsDescription() {
        return "七星：游戏开始时，你将牌堆顶的七张牌扣置于你的武将牌上，称为“星”，然后你可以用任意张手牌替换等量的“星”；" +
                "摸牌阶段结束时，你可以用任意张手牌替换等量的“星”。\n" +
                "狂风：结束阶段，你可以移去一张“星”并选择一名角色，然后直到你的下回合开始之前，当该角色受到火焰伤害时，此伤害+1。\n" +
                "大雾：结束阶段，你可以移去任意张“星”并选择等量的角色，然后直到你的下回合开始之前，当这些角色受到非雷电伤害时，防止此伤害。";
    }
}
