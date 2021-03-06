package sanguosha.cards.strategy.judgecards;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.JudgeCard;
import sanguosha.cards.basic.HurtType;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.GameManager;
import sanguosha.manager.Utils;

import java.util.ArrayList;

public class ShanDian extends JudgeCard {
    public ShanDian(Color color, int number) {
        super(color, number, 100);
    }

    @Override
    public String use() {
        if (!gotWuXie(getTarget())) {
            Card judge = CardsHeap.judge(getTarget());
            if (judge.color() == Color.SPADE && judge.number() >= 2 && judge.number() <= 9) {
                int realNum = getTarget().hurt((Card) null, null, 3, HurtType.thunder);
                ArrayList<Card> cs = new ArrayList<>();
                cs.add(this);
                GameManager.linkHurt(cs, null, realNum, HurtType.thunder);
            } else {
                //getTarget().removeJudgeCard(getThisCard().get(0));
                int numPlayers = GameManager.getNumPlayers();
                int index = GameManager.getPlayers().indexOf(getTarget());
                Utils.assertTrue(index != -1, "shandian target not found");
                do {
                    index = (index + 1 == numPlayers) ? 0 : index + 1;
                } while (!GameManager.getPlayers().get(index).addJudgeCard(this));
                setTarget(GameManager.getPlayers().get(index));
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "闪电";
    }

    @Override
    public String details() {
        return "出牌阶段，对你（你是第一个目标，之后可能会不断改变）使用。" +
                "若判定结果为黑桃2~9，则目标角色受到3点雷电伤害。若判定不为黑桃2~9，将之移动到其下家的判定区里。";
    }
}
