package sanguosha.people.fire;

import sanguosha.cards.Card;
import sanguosha.manager.GameManager;

import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class XunYu extends Person {
    public XunYu() {
        super(3, Nation.WEI);
    }

    @Skill("驱虎")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("驱虎") && hasNotUsedSkill1()) {
            Person p = selectPlayer();
            if (p == null) {
                return true;
            }
            while (p.getCards().isEmpty() || p.getHP() <= getHP()) {
                if (p.getCards().isEmpty()) {
                    printlnToIO("target has no hand cards");
                } else {
                    printlnToIO("target's HP > your HP");
                }
                p = selectPlayer();
                if (p == null) {
                    return true;
                }
            }
            Person p2 = selectPlayer();
            if (p2 == null) {
                return true;
            }
            while (p2 == p || p2 == this ||
                    !GameManager.reachablePeople(p, p.getShaDistance()).contains(p2)) {
                if (p2 == p || p2 == this) {
                    printlnToIO("you can't choose yourself or himself");
                } else {
                    printlnToIO("can't reach this person");
                }
                p2 = selectPlayer();
                if (p2 == null) {
                    return true;
                }
            }
            int n = GameManager.pinDian(this, p) ?
                    p2.hurt((Card) null, p, 1) : hurt((Card) null, p,1);
            setHasUsedSkill1(true);
            return true;
        }
        return false;
    }

    @Skill("节命")
    @Override
    public void gotHurt(ArrayList<Card> cs, Person source, int num) {
        if (launchSkill("节命")) {
            Person p = selectPlayer(true);
            if (p.getMaxHP() > p.getHP()) {
                p.drawCards(p.getMaxHP() - p.getHP());
            }

        }
    }

    @Override
    public String name() {
        return "荀彧";
    }

    @Override
    public String skillsDescription() {
        return "驱虎：出牌阶段限一次，你可以与体力值大于你的一名角色拼点：" +
                "若你赢，你令该角色对其攻击范围内的另一名角色造成1点伤害；若你没赢，其对你造成1点伤害。\n" +
                "节命：当你受到1点伤害后，你可以令一名角色将手牌摸至X张（X为其体力上限且最多为5）。";
    }
}
