package cards.strategy;

import cards.Card;
import cards.Color;
import cards.EquipType;
import cards.Strategy;
import manager.GameManager;
import people.Person;

import java.util.ArrayList;
import java.util.Iterator;

public class WanJianQiFa extends Strategy {
    private ArrayList<Card> thisCard = new ArrayList<>();

    public WanJianQiFa(Color color, int number) {
        super(color, number);
        thisCard.add(this);
    }

    public void setThisCard(ArrayList<Card> thisCard) {
        this.thisCard = thisCard;
    }

    public void setThisCard(Card thisCard) {
        ArrayList<Card> cs = new ArrayList<>();
        cs.add(thisCard);
        this.thisCard = cs;
    }

    @Override
    public Object use() {
        Iterator<Person> it = GameManager.getPlayers().iterator();
        while (it.hasNext()) {
            Person p = it.next();
            if (p != getSource() && !gotWuXie(p) && !p.requestShan()
                    && !p.hasEquipment(EquipType.shield, "藤甲")) {
                p.hurt(thisCard, getSource(), 1);
            }
            if (p.isDead()) {
                it.remove();
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "万箭齐发";
    }
}
