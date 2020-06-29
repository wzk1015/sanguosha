package sanguosha.people.mountain;

import sanguosha.cards.Card;
import sanguosha.cards.Equipment;
import sanguosha.cards.JudgeCard;
import sanguosha.manager.GameManager;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class ZhangHe extends Person {
    public ZhangHe() {
        super(4, Nation.WEI);
    }

    @Skill("巧变")
    public boolean qiaoBian(String s) {
        printlnToIO("skip " + s + "?");
        return launchSkill("巧变") && requestCard(null) != null;
    }

    @Override
    public boolean skipJudge() {
        return qiaoBian("判定阶段");
    }

    @Override
    public boolean skipDraw() {
        if (qiaoBian("摸牌阶段")) {
            while (true) {
                Person p1 = selectPlayer();
                Person p2 = selectPlayer();
                if (p1 == null || p2 == null) {
                    return true;
                }
                if (p1 == p2) {
                    printlnToIO("can't select same person");
                    continue;
                }
                if (p1.getCards().isEmpty() || p2.getCards().isEmpty()) {
                    printlnToIO("target has no hand cards");
                    continue;
                }
                Card c1 = chooseAnonymousCard(p1.getCards());
                Card c2 = chooseAnonymousCard(p2.getCards());
                p1.loseCard(c1, false);
                p2.loseCard(c2, false);
                addCard(c1);
                addCard(c2);
                return true;
            }
        }
        else {
            return false;
        }
    }

    @Override
    public boolean skipUse() {
        if (qiaoBian("出牌阶段")) {
            printlnToIO("choose source and target");
            Person p = selectPlayer(true);
            Person target = selectPlayer(true);
            if (p == null || target == null) {
                return true;
            }
            ArrayList<Card> options = new ArrayList<>(new ArrayList<>(p.getEquipments().values()));
            options.addAll(p.getJudgeCards());
            Card c = chooseCard(options);
            if (c == null) {
                return true;
            }
            if (c instanceof Equipment) {
                if (p.hasEquipment(((Equipment) c).getEquipType(), null)) {
                    printlnToIO("target already has that type of equipment");
                    return true;
                }
                p.getEquipments().put(((Equipment) c).getEquipType(), (Equipment) c);
            }
            else if (c instanceof JudgeCard) {
                if (!addJudgeCard((JudgeCard) c)) {
                    printlnToIO("target already has that type of judge card");
                    return true;
                }
            }
            else {
                GameManager.panic("unknown type of card in 巧变: " + c);
            }
        }
        return false;
    }

    @Override
    public boolean skipThrow() {
        return qiaoBian("弃牌阶段");
    }

    @Override
    public String toString() {
        return "张郃";
    }
}
