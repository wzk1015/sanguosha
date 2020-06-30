package sanguosha.cards.basic;

import sanguosha.cards.BasicCard;
import sanguosha.cards.Color;
import sanguosha.cards.equipments.Shield;
import sanguosha.manager.GameManager;
import sanguosha.people.Person;

import static sanguosha.cards.EquipType.shield;
import static sanguosha.cards.EquipType.weapon;

public class Sha extends BasicCard {
    private final HurtType type;
    private boolean useZhuQueYuShan = false;

    public Sha(Color color, int number, HurtType type) {
        super(color, number);
        this.type = type;
    }

    public Sha(Color color, int number) {
        this(color, number, HurtType.normal);
    }

    public boolean useWeapon(String s) {
        if (getSource().hasEquipment(weapon, s)) {
            if (s.equals("青釭剑") || getSource().chooseNoNull("use " + s, "pass")
                    .equals("use " + s)) {
                getSource().getEquipments().get(weapon).setSource(getSource());
                getSource().getEquipments().get(weapon).setTarget(getTarget());
                getSource().getEquipments().get(weapon).use();
            }
        }
        return false;
    }

    public void shaHit() {
        int numHurt = 1;
        if (getSource().isDrunk()) {
            numHurt++;
        }
        if (getSource().hasEquipment(weapon, "古锭刀") && getTarget().getCards().isEmpty()) {
            numHurt++;
        }
        if (getSource().isNaked()) {
            numHurt++;
        }
        HurtType hurtType = useZhuQueYuShan ? HurtType.fire : type;
        int realNum = getTarget().hurt(getThisCard(), getSource(), numHurt, hurtType);
        if (type != HurtType.normal) {
            GameManager.linkHurt(getThisCard(), getSource(), realNum, hurtType);
        }
        getSource().shaSuccess(getTarget());
        if (!getTarget().isDead()) {
            for (Person p : GameManager.getPlayers()) {
                p.otherPersonHurtBySha(getSource(), getTarget());
            }
        }
    }

    @Override
    public Object use() {

        getSource().shaBegin();
        getTarget().gotShaBegin(this);

        if (type == HurtType.normal && useWeapon("朱雀羽扇")) {
            useZhuQueYuShan = true;
        }
        useWeapon("雌雄双股剑");
        useWeapon("青釭剑");

        if (getTarget().canNotBeSha(this, getSource())) {
            getSource().println("invalid sha");
            return false;
        }

        boolean needDouble = (getSource().hasRouLin() && getTarget().getSex().equals("female"))
                || (getTarget().hasRouLin() && getSource().getSex().equals("female"))
                || getSource().hasWuShuang();

        if (getSource().shaCanBeShan(getTarget()) && ((needDouble && getTarget().requestShan()
                && getTarget().requestShan()) || (!needDouble && getTarget().requestShan()))) {
            if (useWeapon("贯石斧")) {
                shaHit();
            } else {
                getSource().shaGotShan(getTarget());
                useWeapon("青龙偃月刀");
            }
        } else if (!useWeapon("寒冰剑")) {
            useWeapon("麒麟弓");
            shaHit();
            useWeapon("三尖两刃刀");
        }

        if (getTarget().hasEquipment(shield, null)) {
            ((Shield) getTarget().getEquipments().get(shield)).setValid(true);
        }
        if (getTarget().hasBaZhen()) {
            getTarget().setBaZhen(true);
        }
        if (getSource().isDrunk()) {
            getSource().setDrunk(false);
        }
        useZhuQueYuShan = false;
        return true;
    }

    @Override
    public String toString() {
        if (type == HurtType.normal) {
            return "杀";
        } else if (type == HurtType.fire) {
            return "火杀";
        } else {
            return "雷杀";
        }
    }

    public HurtType getType() {
        return type;
    }

    @Override
    public boolean needChooseTarget() {
        return true;
    }

    @Override
    public boolean askTarget(Person user) {
        while (true) {
            Person p = super.selectTarget(user);
            if (p == null) {
                return false;
            }
            if (GameManager.calDistance(user, p) > user.getShaDistance()) {
                user.printlnToIO("distance unreachable");
                continue;
            } else if (p.hasKongCheng() && p.getCards().isEmpty()) {
                user.printlnToIO("can't sha because of 空城");
                continue;
            }
            return true;
        }
    }

    @Override
    public String help() {
        return "使用时机：出牌阶段限一次。\n" +
                "使用目标：你攻击范围内的一名角色。\n" +
                "作用效果：你对目标角色造成1点" +
                (type == HurtType.fire ? "火焰" : type == HurtType.thunder ? "雷电" : "") + "伤害。";
    }
}
