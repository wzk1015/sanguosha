package sanguosha.cards.strategy;

import sanguosha.cards.Color;
import sanguosha.cards.EquipType;
import sanguosha.cards.Strategy;
import sanguosha.manager.GameManager;
import sanguosha.people.Person;

import java.util.Iterator;

public class WanJianQiFa extends Strategy {

    public WanJianQiFa(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        Iterator<Person> it = GameManager.getPlayers().iterator();
        while (it.hasNext()) {
            Person p = it.next();
            if (p != getSource() && !gotWuXie(p) && !p.requestShan()
                    && !p.hasEquipment(EquipType.shield, "藤甲")) {
                p.hurt(getThisCard(), getSource(), 1);
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

    @Override
    public String details() {
        return "出牌阶段，对所有其他角色使用。每名目标角色需打出一张【闪】，否则受到1点伤害。";
    }
}
