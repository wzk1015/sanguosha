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
            println(String.format("You need to throw %d sanguosha.cards", num));
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
                Card c = requestColor(Color.DIAMOND);
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
                c = requestColor(Color.DIAMOND);
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
                c = requestColor(Color.CLUB);
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
                c = requestColor(Color.SPADE);
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
                c = requestColor(Color.HEART);
                if (c == null) {
                    return super.requestTao();
                }
            }
            return true;
        }
        return super.requestTao();
    }

    @Override
    public String toString() {
        return "神赵云";
    }
}
