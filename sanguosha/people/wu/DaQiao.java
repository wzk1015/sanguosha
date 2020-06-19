package sanguosha.people.wu;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.JudgeCard;
import sanguosha.cards.basic.Sha;
import sanguosha.cards.strategy.judgecards.LeBuSiShu;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.GameManager;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

public class DaQiao extends Person {
    public DaQiao() {
        super(3, "female", Nation.WU);
    }

    @Skill("国色")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("国色")) {
            println(this + " uses 国色");
            Card c = requestColor(Color.DIAMOND);
            if (c == null) {
                return true;
            }
            LeBuSiShu le  = new LeBuSiShu(c.color(), c.number());
            le.setThisCard(c);
            if (le.askTarget(this) && le.getTarget().addJudgeCard((JudgeCard) le)) {
                useCard(le);
            } else {
                addCard(CardsHeap.retrieve(c));
            }
            return true;
        }
        return false;
    }

    @Skill("流离")
    @Override
    public void gotShaBegin(Sha sha) {
        if (launchSkill("流离")) {
            Person p;
            while (true) {
                p = selectPlayer();
                if (p == null) {
                    return;
                }
                if (!GameManager.reachablePeople(this, getShaDistance()).contains(p)) {
                    println("unreachable person");
                    continue;
                }
                if (p == sha.getSource()) {
                    println("you can't select source player of the 杀");
                    continue;
                }
                break;
            }
            sha.setTarget(p);
        }
    }

    @Override
    public String toString() {
        return "大乔";
    }
}
