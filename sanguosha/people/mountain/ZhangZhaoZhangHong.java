package sanguosha.people.mountain;

import sanguosha.cards.Card;
import sanguosha.cards.Equipment;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.Utils;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class ZhangZhaoZhangHong extends Person {
    public ZhangZhaoZhangHong() {
        super(3, Nation.WU);
    }

    @Skill("直谏")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("直谏")) {
            println(this + " uses 直谏");
            Card c = chooseCard(getCards(), true);
            while (c != null && !(c instanceof Equipment)) {
                printlnToIO("you should choose a weapon");
                c = chooseCard(getCards(), true);
            }
            if (c == null) {
                return true;
            }
            Person p = selectPlayer();
            while (p != null && p.hasEquipment(((Equipment) c).getEquipType(), null)) {
                printlnToIO("you should choose someone without that kind of equipment");
                p = selectPlayer();
            }
            if (p == null) {
                return true;
            }
            println(p + " puts on " + c);
            p.getEquipments().put(((Equipment) c).getEquipType(), (Equipment) c);
            drawCard();
        }
        return false;
    }

    @Skill("固政")
    @Override
    public void otherPersonThrowPhase(Person p, ArrayList<Card> cards) {
        Utils.assertTrue(!cards.isEmpty(), "throw cards are empty");
        if (launchSkill("固政")) {
            printlnToIO("choose a card to give back to " + p);
            Card c = chooseCard(cards, true);
            if (c == null) {
                return;
            }
            CardsHeap.retrieve(cards);
            p.addCard(c);
            cards.remove(c);
            addCard(cards);
        }
    }

    @Override
    public String name() {
        return "张昭张纮";
    }

    @Override
    public String skillsDescription() {
        return "直谏：出牌阶段，你可以将手牌中的一张装备牌置于其他角色的装备区里，然后摸一张牌。\n" +
                "固政：其他角色的弃牌阶段结束时，你可以将此阶段中的一张弃牌返还给该角色，然后你获得其余的弃牌。";
    }
}
