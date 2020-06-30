package sanguosha.cards.strategy.judgecards;

import sanguosha.cards.Color;
import sanguosha.cards.JudgeCard;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.people.Person;

public class LeBuSiShu extends JudgeCard {
    public LeBuSiShu(Color color, int number) {
        super(color, number);
    }

    @Override
    public String use() {
        if (!gotWuXie(getTarget())) {
            if (CardsHeap.judge(getTarget()).color() != Color.HEART) {
                return "skip use";
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "乐不思蜀";
    }

    @Override
    public boolean needChooseTarget() {
        return true;
    }

    @Override
    public boolean violateAdditionalRequirement(Person user, Person p) {
        if (p.hasQianXun()) {
            getSource().printlnToIO("can't use that because of 谦逊");
        }
        return p.hasQianXun();
    }

    @Override
    public String details() {
        return "出牌阶段，对一名其他角色使用。若判定结果不为红桃，跳过其出牌阶段。";
    }
}
