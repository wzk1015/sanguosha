package people.wind;

import cards.Card;
import cardsheap.CardsHeap;
import people.Nation;
import people.Person;
import skills.Skill;

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
                    Card c = CardsHeap.judge(this);
                    buQuNumbers.add(c.number());
                    buQuCards.add(c);
                }
                println(this + " now has " + num + " 不屈 cards");
                while (buQuDuplicated()) {
                    dying();
                }
                if (!isDead()) {
                    println(this + " now has " + num + " 不屈 cards");
                }
            } else {
                dying();
            }
        }
    }

    @Override
    public int getHP() {
        return Math.max(super.getHP(), 0);
    }

    @Override
    public void recover(int num) {
        if (getHP() == 0) {
            println("choose 不屈 cards that you want to remove");
            ArrayList<Card> cs = chooseCards(num, buQuCards);
            buQuCards.removeAll(cs);
            for (Card c: cs) {
                buQuNumbers.remove(c.number());
            }
            println(this + " now has " + num + " 不屈 cards");
        }
        super.recover(num);
    }

    @Override
    public String toString() {
        return "周泰";
    }
}
