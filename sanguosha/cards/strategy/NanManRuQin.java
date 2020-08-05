package sanguosha.cards.strategy;

import sanguosha.cards.Color;
import sanguosha.cards.EquipType;
import sanguosha.cards.Strategy;
import sanguosha.manager.GameManager;
import sanguosha.manager.Status;
import sanguosha.people.Person;

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
            if (GameManager.status() == Status.end) {
                return null;
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

    @Override
    public String details() {
        return "出牌阶段，对所有其他角色使用。每名目标角色需打出一张【杀】，否则受到1点伤害。";
    }
}
