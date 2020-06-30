package sanguosha.people.god;

import sanguosha.cards.Card;
import sanguosha.cards.EquipType;
import sanguosha.cards.equipments.Shield;
import sanguosha.manager.GameManager;
import sanguosha.manager.Utils;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class ShenLvBu extends God {
    private int baoNuMark = 2;
    private boolean isWuQian = false;

    public ShenLvBu() {
        super(5, null);
    }

    @ForcesSkill("暴怒")
    @Override
    public void gotHurt(ArrayList<Card> cards, Person p, int num) {
        println(this + " got " + num + " 暴怒mark");
        baoNuMark += num;
        println(this + " now has " + baoNuMark + " 暴怒 marks");
    }

    @Override
    public void hurtOther(Person p, int num) {
        println(this + " got " + num + " 暴怒mark");
        baoNuMark += num;
        println(this + " now has " + baoNuMark + " 暴怒 marks");
    }

    @ForcesSkill("无谋")
    @Override
    public void useStrategy() {
        if (baoNuMark == 0 || chooseNoNull("lose 1 暴怒mark", "lose 1HP").equals("lose 1HP")) {
            loseHP(1);
        }
        else {
            println(this + " lost 1 暴怒 mark");
            println(this + " now has " + baoNuMark + " 暴怒 marks");
            baoNuMark--;
        }
    }

    @ForcesSkill("无双")
    @Override
    public boolean hasWuShuang() {
        return isWuQian;
    }

    @Skill("无前")
    public void wuQian() {
        Person p = selectPlayer();
        if (p == null) {
            return;
        }
        if (p.hasEquipment(EquipType.shield, null)) {
            ((Shield) p.getEquipments().get(EquipType.shield)).setValid(false);
        }
        isWuQian = true;
        println(this + " lost 2 暴怒 mark");
        baoNuMark -= 2;
        println(this + " now has " + baoNuMark + " 暴怒 marks");
        println(this + " uses 无前 towards " + p);
    }

    @Skill("神愤")
    public void shenFen() {
        println(this + " uses 神愤");
        println(this + " lost 6 暴怒 mark");
        println(this + " now has " + baoNuMark + " 暴怒 marks");
        baoNuMark -= 6;
        for (Person p: GameManager.getPlayers()) {
            if (p == this) {
                continue;
            }
            p.hurt((Card) null, this, 1);
            if (p.isDead()) {
                continue;
            }
            p.loseCard(new ArrayList<>(p.getEquipments().values()));
            if (p.getCards().size() <= 4) {
                p.loseCard(p.getCards());
            } else {
                p.loseCard(p.chooseCards(4, p.getCards()));
            }
        }
        turnover();
    }

    @Override
    public boolean useSkillInUsePhase(String order) {
        if (baoNuMark >= 2 && order.equals("无前")) {
            wuQian();
            return true;
        }
        if (baoNuMark >= 6 && order.equals("神愤") && hasNotUsedSkill1()) {
            shenFen();
            setHasUsedSkill1(true);
            return true;
        }
        return false;
    }

    @Override
    public void endPhase() {
        Utils.assertTrue(baoNuMark >= 0, "invalid 暴怒 mark: " + baoNuMark);
        isWuQian = false;
        for (Person p: GameManager.getPlayers()) {
            if (p.hasEquipment(EquipType.shield, null)) {
                ((Shield) p.getEquipments().get(EquipType.shield)).setValid(true);
            }
        }
    }

    @Override
    public String getExtraInfo() {
        return baoNuMark + " 暴怒 marks";
    }

    @Override
    public String name() {
        return "神吕布";
    }

    @Override
    public String skillsDescription() {
        return "狂暴：锁定技，游戏开始时，你获得2枚“暴怒”标记；当你造成或受到1点伤害后，你获得1枚“暴怒”标记。\n" +
                "无谋：锁定技，当你使用普通锦囊牌时，你弃1枚“暴怒”标记或失去1点体力。\n" +
                "无前：出牌阶段，你可以弃2枚“暴怒”标记并选择一名其他角色，然后直到回合结束，你获得“无双”且该角色的防具失效。\n" +
                "无双：锁定技，你使用的【杀】需两张【闪】才能抵消；与你进行【决斗】的角色每次需打出两张【杀】。\n" +
                "神愤：出牌阶段限一次，你可以弃6枚“暴怒”标记，然后对所有其他角色各造成1点伤害，这些角色先各弃置装备区里的所有牌，再弃置四张手牌，最后你翻面。";
    }
}
