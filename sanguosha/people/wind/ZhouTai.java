package sanguosha.people.wind;

import sanguosha.cards.Card;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.GameManager;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

import java.util.ArrayList;
import java.util.HashSet;

public class ZhouTai extends Person {
    private ArrayList<Card> buQuCards = new ArrayList<>();
    private ArrayList<Integer> buQuNumbers = new ArrayList<>();

    public ZhouTai() {
        super(4, Nation.WU);
    }

    public boolean buQuDuplicated() {
        return new HashSet<>(buQuNumbers).size() != buQuNumbers.size();
    }

    @Skill("不屈")
    @Override
    public void loseHP(int num) {
        subCurrentHP(num);
        println(this + " lost " + num + "HP, current HP: " + getHP() + "/" + getMaxHP());
        if (getHP() <= 0) {
            if (launchSkill("不屈")) {
                println(this + " now has " + num + " 不屈 cards");
                for (int i = 0; i < num; i++) {
                    Card c = CardsHeap.draw();
                    buQuNumbers.add(c.number());
                    buQuCards.add(c);
                    printCardsPublic(buQuCards);
                }
                println(this + " now has " + buQuCards.size() + " 不屈 cards");
                while (buQuDuplicated()) {
                    dying();
                }
                if (!isDead()) {
                    println(this + " now has " + buQuCards.size() + " 不屈 cards");
                }
            } else {
                dying();
            }
        }
    }

    @Override
    public void throwPhase() {
        int num = getCards().size() - Math.max(getHP(), 0);
        if (num > 0) {
            printlnToIO(String.format("You need to throw %d cards", num));
            ArrayList<Card> cs = chooseCards(num, getCards());
            loseCard(cs);
            for (Person p: GameManager.getPlayers()) {
                p.otherPersonThrowPhase(this, cs);
            }
        }
    }

    @Override
    public void recover(int num) {
        if (getHP() <= 0 && !buQuCards.isEmpty()) {
            printlnToIO("choose 不屈 cards that you want to remove");
            ArrayList<Card> cs = chooseCards(num, buQuCards);
            buQuCards.removeAll(cs);
            CardsHeap.discard(cs);
            for (Card c: cs) {
                buQuNumbers.remove(c.number());
            }
            println(this + " now has " + buQuCards.size() + " 不屈 cards");
        }
        super.recover(num);
    }

    public int numOfBuQu() {
        return buQuCards.size();
    }

    @Override
    public ArrayList<Card> getExtraCards() {
        return buQuCards;
    }

    @Override
    public String getExtraInfo() {
        return buQuCards.size() + " 不屈 cards";
    }

    @Override
    public String toString() {
        return "周泰";
    }
}
