package sanguosha.cards.equipments.weapons;

import sanguosha.cards.Color;
import sanguosha.cards.equipments.Shield;
import sanguosha.cards.equipments.Weapon;

import static sanguosha.cards.EquipType.shield;

public class QingGangJian extends Weapon {
    public QingGangJian(Color color, int number) {
        super(color, number, 2);
    }

    @Override
    public Object use() {
        if (getTarget().hasEquipment(shield, null)) {
            ((Shield) getTarget().getEquipments().get(shield)).setValid(false);
        }
        else if (getTarget().hasBaZhen()) {
            getTarget().setBaZhen(false);
        }
        return null;
    }

    @Override
    public String toString() {
        return "青釭剑";
    }

    @Override
    public String details() {
        return "锁定技，每当你使用【杀】指定一名目标角色后，你无视其防具。";
    }
}
