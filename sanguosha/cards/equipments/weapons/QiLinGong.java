package sanguosha.cards.equipments.weapons;

import sanguosha.cards.Color;
import sanguosha.cards.equipments.Weapon;

import static sanguosha.cards.EquipType.minusOneHorse;
import static sanguosha.cards.EquipType.plusOneHorse;

public class QiLinGong extends Weapon {

    public QiLinGong(Color color, int number) {
        super(color, number, 5);
    }

    @Override
    public Object use() {
        if (getTarget().hasEquipment(plusOneHorse, null) &&
                getTarget().hasEquipment(minusOneHorse, null)) {
            String choice = getSource().chooseNoNull("shoot down plusonehorse",
                    "shoot down minusonehorse", "pass");
            if (choice.equals("shoot down minusonehorse")) {
                getTarget().getEquipments().put(minusOneHorse, null);
            } else if (choice.equals("shoot down plusonehorse")) {
                getTarget().getEquipments().put(plusOneHorse, null);
            }
        } else if (getTarget().hasEquipment(plusOneHorse, null) ||
                getTarget().hasEquipment(minusOneHorse, null)) {
            String choice = getSource().chooseNoNull("shoot down horse", "pass");
            if (choice.equals("shoot down horse")) {
                if (getTarget().hasEquipment(plusOneHorse, null)) {
                    getTarget().getEquipments().put(plusOneHorse, null);
                } else {
                    getTarget().getEquipments().put(minusOneHorse, null);
                }
            }
        } else {
            getSource().printlnToIO("target has no horse");
        }
        return null;
    }

    @Override
    public String toString() {
        return "麒麟弓";
    }

    @Override
    public String details() {
        return "每当你使用【杀】对目标角色造成伤害时，你可以弃置其装备区里的一张坐骑牌。";
    }
}
