package sanguosha.people.mountain;

import sanguosha.cards.Card;
import sanguosha.cards.basic.Sha;
import sanguosha.manager.GameManager;
import sanguosha.people.Identity;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.AfterWakeSkill;
import sanguosha.skills.KingSkill;
import sanguosha.skills.Skill;
import sanguosha.skills.WakeUpSkill;

public class SunCe extends Person {
    private boolean zhiBa = false;

    public SunCe() {
        super(4, Nation.WU);
    }

    @Skill("激昂")
    public void jiAng(Card c) {
        if (c instanceof Sha && c.isRed() && launchSkill("激昂")) {
            drawCard();
        }
    }

    @Override
    public void gotShaBegin(Sha sha) {
        jiAng(sha);
    }

    @Override
    public boolean useSha(Card card) {
        if (super.useSha(card)) {
            jiAng(card);
            return true;
        }
        return false;
    }

    @Override
    public void jueDouBegin() {
        if (launchSkill("激昂")) {
            drawCard();
        }
    }

    @Skill("鹰扬")
    @Override
    public String usesYingYang() {
        if (launchSkill("鹰扬")) {
            String option = chooseFromProvided("+3", "-3");
            if (option != null) {
                return option;
            }
        }
        return "";
    }

    @WakeUpSkill("魂姿")
    public void hunZi() {
        setMaxHP(getMaxHP() - 1);
        wakeUp();
    }

    @KingSkill("制霸")
    @Override
    public void otherPersonUsePhase(Person p) {
        if (getIdentity() == Identity.KING && p.getNation() == Nation.WU &&
                !p.getCards().isEmpty() && !getCards().isEmpty() && p.launchSkill("制霸")) {
            GameManager.pinDian(p, this);
        }
    }

    @Override
    public boolean usesZhiBa() {
        return zhiBa;
    }

    @Override
    public void beginPhase() {
        if (!hasWakenUp() && getHP() == 1) {
            hunZi();
        }
        if (hasWakenUp()) {
            yingHun();
        }
    }

    @AfterWakeSkill("英姿")
    @Override
    public void drawPhase() {
        if (hasWakenUp() && launchSkill("英姿")) {
            drawCards(3);
        }
        else {
            super.drawPhase();
        }
    }

    @AfterWakeSkill("英魂")
    public void yingHun() {
        if (getHP() < getMaxHP() && launchSkill("英魂")) {
            int x = getMaxHP() - getHP();
            String op1 = "draw " + x + ", throw 1";
            String op2 = "draw 1, throw " + x;
            Person p = selectPlayer();
            if (p != null) {
                if (chooseNoNull(op1, op2).equals(op1)) {
                    p.drawCards(x);
                    p.loseCard(p.chooseCards(1, p.getCardsAndEquipments()));
                }
                else {
                    p.drawCard();
                    int num = p.getCardsAndEquipments().size();
                    if (num <= x) {
                        p.loseCard(p.getCardsAndEquipments());
                    } else {
                        p.loseCard(p.chooseCards(x, p.getCardsAndEquipments()));
                    }
                }
            }
        }
    }

    @Override
    public String name() {
        return "孙策";
    }

    @Override
    public String skillsDescription() {
        return "激昂：当你使用【决斗】或红色【杀】指定目标后，或成为【决斗】或红色【杀】的目标后，你可以摸一张牌。\n" +
                "魂姿：觉醒技，准备阶段，若你的体力值为1，你减1点体力上限，然后获得\"英姿\"和\"英魂\"。\n" +
                "制霸：主公技，其他吴势力角色的出牌阶段限一次，该角色可以与你拼点（若你已觉醒，你可以拒绝此拼点），若其没赢，你可以获得拼点的两张牌。\n" +
                (hasWakenUp() ? "英姿：锁定技，摸牌阶段，你多摸一张牌；你的手牌上限等于X（X为你的体力上限）。\n" +
                "英魂：准备阶段，若你已受伤，你可以选择一名其他角色并选择一项：" +
                        "1.令其摸X张牌，然后弃置一张牌；2.令其摸一张牌，然后弃置X张牌。（X为你已损失的体力值）。" : "");
    }
}
