package sanguosha.cards.strategy;

import sanguosha.cards.Color;
import sanguosha.cards.Strategy;
import sanguosha.manager.GameManager;
import sanguosha.people.Person;

public class TaoYuanJieYi extends Strategy {
    public TaoYuanJieYi(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        for (Person p : GameManager.getPlayers()) {
            if (!gotWuXie(p)) {
                p.recover(1);
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "桃园结义";
    }

    @Override
    public String details() {
        return "出牌阶段，对所有角色使用。每名目标角色回复1点体力。";
    }
}
