package sanguosha.people.god;

import sanguosha.cards.Card;
import sanguosha.manager.GameManager;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class ShenCaoCao extends God {
    public ShenCaoCao() {
        super(3, null);
    }

    @Skill("归心")
    @Override
    public void gotHurt(ArrayList<Card> cards, Person p, int num) {
        if (launchSkill("归心")) {
            for (Person p2: GameManager.getPlayers()) {
                if (p2 != this &&
                        !(p2.getCardsAndEquipments().isEmpty() && p2.getJudgeCards().isEmpty())) {
                    Card c = chooseTargetCards(p2, false);
                    p2.loseCard(c, false);
                    addCard(c);
                }
            }
            turnover();
        }
    }

    @Skill("飞影")
    @Override
    public boolean hasFeiYing() {
        return true;
    }

    @Override
    public String name() {
        return "神曹操";
    }

    @Override
    public String skillsDescription() {
        return "归心：当你受到1点伤害后，你可以获得每名其他角色区域里的一张牌，然后你翻面。\n" +
                "飞影：锁定技，其他角色计算与你的距离+1。";
    }
}
