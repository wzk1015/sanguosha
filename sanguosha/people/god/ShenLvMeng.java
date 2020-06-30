package sanguosha.people.god;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.IO;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

import java.util.ArrayList;
import java.util.HashSet;

public class ShenLvMeng extends God {
    public ShenLvMeng() {
        super(3, null);
    }

    @Skill("涉猎")
    @Override
    public void drawPhase() {
        if (launchSkill("涉猎")) {
            ArrayList<Card> cards = new ArrayList<>(CardsHeap.getDrawCards(5).subList(0, 5));
            CardsHeap.getDrawCards(0).removeAll(cards);
            ArrayList<Card> selected = new ArrayList<>();
            IO.printCardsPublic(cards);
            while (true) {
                printlnToIO("choose cards of different colors");
                selected = chooseCards(0, cards);
                HashSet<Color> existingColors = new HashSet<>();
                boolean colorDuplicated = false;
                for (Card c: selected) {
                    if (existingColors.contains(c.color())) {
                        colorDuplicated = true;
                        break;
                    }
                    existingColors.add(c.color());
                }
                if (colorDuplicated) {
                    continue;
                }
                addCard(selected);
                cards.removeAll(selected);
                CardsHeap.discard(cards);
                return;
            }
        }
        super.drawPhase();
    }

    @Skill("攻心")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("攻心")) {
            Person p = selectPlayer();
            if (p != null) {
                printCards(p.getCards());
                printlnToIO("choose a HEART card, or q to pass");
                Card c = chooseCard(p.getCards(), true);
                if (c != null) {
                    IO.printCardPublic(c);
                    if (chooseNoNull("throw", "put on top of cards heap").equals("throw")) {
                        p.loseCard(c);
                    } else {
                        p.loseCard(c, false);
                        CardsHeap.getDrawCards(0).add(0, c);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String name() {
        return "神吕蒙";
    }

    @Override
    public String skillsDescription() {
        return "涉猎：摸牌阶段，你可以改为亮出牌堆顶的五张牌，然后获得其中每种花色的牌各一张。\n" +
                "攻心：出牌阶段限一次，你可以观看一名其他角色的手牌，然后你可以展示其中一张红桃牌，选择一项：1.弃置此牌；2.将此牌置于牌堆顶。";
    }
}
