package sanguosha.people.fire;

import sanguosha.cards.Card;
import sanguosha.cards.equipments.Weapon;
import sanguosha.manager.GameManager;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

public class DianWei extends Person {
    public DianWei() {
        super(4, Nation.WEI);
    }

    @Skill("强袭")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("强袭") && hasNotUsedSkill1()) {
            println(this + " uses 强袭");
            Person p = selectPlayer();
            if (p == null) {
                return true;
            }
            if (!GameManager.reachablePeople(this, getShaDistance()).contains(p)) {
                printlnToIO("you can't reach that person");
                return true;
            }
            if (chooseNoNull("throw a weapon", "lose 1 HP").equals("lost 1 HP")) {
                loseHP(1);
                if (isDead()) {
                    p.hurt((Card) null, null, 1);
                } else {
                    p.hurt((Card) null, this, 1);
                }
            }
            else {
                Card c;
                do {
                    printlnToIO("choose a weapon");
                    c = chooseCard(getCardsAndEquipments(), true);
                } while (!(c instanceof Weapon) && c != null);
                if (c == null) {
                    return true;
                }
                loseCard(c);
                p.hurt((Card) null, this, 1);
            }
            setHasUsedSkill1(true);
            return true;
        }
        return false;
    }

    @Override
    public String name() {
        return "典韦";
    }

    @Override
    public String skillsDescription() {
        return "强袭：出牌阶段限一次，你可以失去1点体力或弃置一张武器牌，并对你攻击范围内的一名其他角色造成1点伤害。";
    }
}
