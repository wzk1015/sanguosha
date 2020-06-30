package sanguosha.people.god;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.basic.HurtType;
import sanguosha.cards.basic.Sha;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.GameManager;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class ShenZhaoYun extends God {
    public ShenZhaoYun() {
        super(2, null);
    }

    @ForcesSkill("绝境")
    @Override
    public void drawPhase() {
        println(this + " uses 绝境");
        drawCards(2 + getMaxHP() - getHP());
    }

    @Override
    public void throwPhase() {
        println(this + " uses 绝境");
        int num = getCards().size() - getHP() - 2;
        if (num > 0) {
            println(String.format("You need to throw %d cards", num));
            ArrayList<Card> cs = chooseCards(num, getCards());
            loseCard(cs);
            for (Person p: GameManager.getPlayers()) {
                p.otherPersonThrowPhase(this, cs);
            }
        }
    }

    @Skill("龙魂")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("龙魂")) {
            println(this + " uses 龙魂");
            ArrayList<Card> cs = new ArrayList<>();
            for (int i = 0; i < Math.max(getHP(), 1); i++) {
                Card c = requestColor(Color.DIAMOND, true);
                if (c == null) {
                    return true;
                }
                cs.add(c);
            }
            Sha sha = new Sha(Color.DIAMOND, 0, HurtType.fire);
            sha.setThisCard(cs);
            if (sha.askTarget(this)) {
                useCard(sha);
            } else {
                CardsHeap.retrieve(cs);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean skillSha() {
        if (launchSkill("龙魂")) {
            Card c;
            for (int i = 0; i < Math.max(getHP(), 1); i++) {
                c = requestColor(Color.DIAMOND, true);
                if (c == null) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean skillShan() {
        if (launchSkill("龙魂")) {
            Card c;
            for (int i = 0; i < Math.max(getHP(), 1); i++) {
                c = requestColor(Color.CLUB, true);
                if (c == null) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean skillWuxie() {
        if (launchSkill("龙魂")) {
            Card c;
            for (int i = 0; i < Math.max(getHP(), 1); i++) {
                c = requestColor(Color.SPADE, true);
                if (c == null) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean requestTao() {
        if (launchSkill("龙魂")) {
            Card c;
            for (int i = 0; i < Math.max(getHP(), 1); i++) {
                c = requestColor(Color.HEART, true);
                if (c == null) {
                    return super.requestTao();
                }
            }
            return true;
        }
        return super.requestTao();
    }

    @Override
    public String name() {
        return "神赵云";
    }

    @Override
    public String skillsDescription() {
        return "绝境：锁定技，摸牌阶段，你多摸等同于你已损失的体力值数的牌；你的手牌上限+2。\n" +
                "龙魂：你可以将花色相同的X张牌按下列规则使用或打出：红桃当【桃】；方块当火【杀】；梅花当【闪】；黑桃当【无懈可击】（X为你的体力值且最少为1）。";
    }
}
