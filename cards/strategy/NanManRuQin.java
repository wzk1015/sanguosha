package cards.strategy;

import cards.Color;
import cards.EquipType;
import cards.Strategy;
import manager.GameManager;
import people.Person;

import java.util.Iterator;

public class NanManRuQin extends Strategy {
    public NanManRuQin(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        Person realSource = getSource();
        for (Person p: GameManager.getPlayers()) {
            if (p.hasHuoShou()) {
                realSource = p;
                break;
            }
        }
        Iterator<Person> it = GameManager.getPlayers().iterator();
        while (it.hasNext()) {
            Person p = it.next();
            if (!p.hasHuoShou() && !p.hasJuXiang() && p != getSource()
                    && !p.hasEquipment(EquipType.shield, "藤甲")
                    && !gotWuXie(p) && p.requestSha() == null) {
                p.hurt(this, realSource, 1);
            }
            if (p.isDead()) {
                it.remove();
            }
        }
        if (this.isNotTaken()) {
            for (Person p: GameManager.getPlayers()) {
                if (p.hasJuXiang()) {
                    p.addCard(this);
                    this.setTaken(true);
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "南蛮入侵";
    }
}
