package sanguosha.cards.strategy;

import sanguosha.cards.Color;
import sanguosha.cards.EquipType;
import sanguosha.cards.Strategy;
import sanguosha.cards.basic.Sha;
import sanguosha.manager.Utils;
import sanguosha.people.Person;

import static sanguosha.cards.EquipType.weapon;

public class JieDaoShaRen extends Strategy {
    private Person target2;

    public JieDaoShaRen(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        if (!gotWuXie(getTarget())) {
            Utils.assertTrue(getTarget().hasEquipment(EquipType.weapon, null),
                    "target has no weapon");
            Sha sha = getTarget().requestSha();
            if (sha != null) {
                sha.setSource(getTarget());
                sha.setTarget(getTarget2());
                sha.use();
            } else {
                getSource().addCard(getTarget().getEquipments().get(EquipType.weapon));
                getTarget().getEquipments().put(EquipType.weapon, null);
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "借刀杀人";
    }

    public void setTarget2(Person target2) {
        this.target2 = target2;
    }

    public Person getTarget2() {
        return target2;
    }

    @Override
    public boolean needChooseTarget() {
        return true;
    }

    @Override
    public boolean askTarget(Person user) {
        setSource(user);

        while (true) {
            user.printlnToIO("choose target 1");
            Person p1 = getSource().selectPlayer(false);
            user.printlnToIO("choose target 2");
            Person p2 = getSource().selectPlayer(true);
            if (p1 == null || p2 == null) {
                return false;
            }
            if (p1 == p2) {
                getSource().printlnToIO("can't select two same people");
                continue;
            }
            if (!p1.hasEquipment(weapon, null)) {
                getSource().printlnToIO("target has no weapon");
                continue;
            }
            setTarget(p1);
            setTarget2(p2);
            return true;
        }
    }

    @Override
    public String details() {
        return "出牌阶段，对装备区里有武器牌且其攻击范围内有使用【杀】的目标的一名其他角色A使用。" +
                "（选择目标时）你选择A攻击范围内的一名角色B（与A不同）。" +
                "A需对B使用一张【杀】，否则将其装备区里的武器牌交给你。";
    }
}
