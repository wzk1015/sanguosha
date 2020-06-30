package sanguosha.cards.strategy;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.Strategy;
import sanguosha.people.Person;

public class GuoHeChaiQiao extends Strategy {
    public GuoHeChaiQiao(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        if (!gotWuXie(getTarget())) {
            Card c = getSource().chooseTargetCards(getTarget(), true);
            getTarget().loseCard(c, true);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "过河拆桥";
    }

    @Override
    public boolean needChooseTarget() {
        return true;
    }

    @Override
    public boolean violateAdditionalRequirement(Person user, Person p) {
        if (p.getCardsAndEquipments().isEmpty() && p.getJudgeCards().isEmpty()) {
            getSource().println("you can't choose a person with no cards");
            return true;
        }
        return false;
    }

    @Override
    public String details() {
        return "出牌阶段，对区域里有牌的一名其他角色使用。你弃置其区域里的一张牌。";
    }
}
