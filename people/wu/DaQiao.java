package people.wu;

import cards.Card;
import cards.Color;
import cards.JudgeCard;
import cards.basic.Sha;
import cards.strategy.judgecards.LeBuSiShu;
import cardsheap.CardsHeap;
import manager.GameManager;
import people.Nation;
import people.Person;
import skills.Skill;

public class DaQiao extends Person {
    public DaQiao() {
        super(3, "female", Nation.WU);
    }

    @Skill("国色")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("国色")) {
            println(this + " uses 国色");
            Person p = GameManager.selectPlayer(this);
            Card c = requestColor(Color.DIAMOND);
            if (c == null) {
                return true;
            }
            LeBuSiShu le  = new LeBuSiShu(c.color(), c.number());
            le.setThisCard(c);
            if (GameManager.askTarget(c, this) && c.getTarget().addJudgeCard((JudgeCard) c)) {
                showUsingCard(c);
                c.setTaken(true);
            } else {
                CardsHeap.retrive(c);
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
                p = GameManager.selectPlayer(this);
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