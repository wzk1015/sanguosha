package people.god;

import cards.Card;
import manager.GameManager;
import people.Person;
import skills.Skill;

import java.util.ArrayList;

public class ShenCaoCao extends God {
    public ShenCaoCao() {
        super(3, null);
    }

    @Skill("归心")
    @Override
    public void gotHurt(ArrayList<Card> cards, Person p, int num) {
        if (launchSkill("归心")) {
            for (Person p2: GameManager.getPlayers()) {
                if (p2 != this) {
                    Card c = chooseTargetAllCards(p2);
                    p2.loseCard(c, false);
                    addCard(c);
                }
            }
            turnover();
        }
    }

    @Skill("飞影")
    @Override
    public boolean hasFeiYing() {
        return true;
    }

    @Override
    public String toString() {
        return "神曹操";
    }
}
