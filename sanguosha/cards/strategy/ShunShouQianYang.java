package sanguosha.cards.strategy;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.Strategy;
import sanguosha.people.Person;

public class ShunShouQianYang extends Strategy {

    public ShunShouQianYang(Color color, int number) {
        super(color, number, 1);
    }

    @Override
    public Object use() {
        if (!gotWuXie(getTarget())) {
            Card c = getSource().chooseTargetCards(getTarget(), true);
            getTarget().loseCard(c, false);
            getSource().addCard(c);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "顺手牵羊";
    }

    @Override
    public boolean needChooseTarget() {
        return true;
    }

    @Override
    public boolean violateAdditionalRequirement(Person user, Person p) {
        if (p.hasQianXun()) {
            getSource().println("can't use that because of 谦逊");
            return true;
        }
        if (p.getCardsAndEquipments().isEmpty() && p.getJudgeCards().isEmpty()) {
            getSource().printlnToIO("you can't choose a person with no cards");
            return true;
        }
        return false;
    }

    @Override
    public String details() {
        return "出牌阶段，对距离为1且区域里有牌的一名其他角色使用。你获得其区域里的一张牌。";
    }
}
