package sanguosha.cards.equipments.weapons;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.equipments.Weapon;
import sanguosha.manager.GameManager;
import sanguosha.people.Person;

import java.util.ArrayList;

public class SanJianLiangRenDao extends Weapon {
    public SanJianLiangRenDao(Color color, int number) {
        super(color, number, 3);
    }

    @Override
    public Object use() {
        ArrayList<Person> nearbyPerson = GameManager.reachablePeople(getSource(), 1);
        if (!nearbyPerson.isEmpty()) {
            Person p = getSource().selectPlayer(nearbyPerson);
            if (p != null) {
                p.hurt((Card) null, getSource(), 1);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "三尖两刃刀";
    }

    @Override
    public String details() {
        return "你使用【杀】对目标角色造成伤害后，可弃置一张手牌并对该角色距离1的另一名角色造成1点伤害。";
    }
}
