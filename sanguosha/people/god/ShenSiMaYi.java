package sanguosha.people.god;

import sanguosha.cards.Card;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.GameManager;
import sanguosha.people.Person;
import sanguosha.skills.AfterWakeSkill;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.Skill;
import sanguosha.skills.WakeUpSkill;

import java.util.ArrayList;

public class ShenSiMaYi extends God {
    private int renMark = 0;
    private boolean hasKilled = false;

    public ShenSiMaYi() {
        super(4, null);
    }

    @ForcesSkill("忍戒")
    @Override
    public void gotHurt(ArrayList<Card> cards, Person p, int num) {
        println(this + " uses 忍戒");
        renMark += num;
        println(this + " now has " + renMark + " 忍mark");
        fangZhu();
    }

    @Override
    public void throwPhase() {
        int num = getCards().size() - getHP();
        if (num > 0) {
            println(String.format("You need to throw %d cards", num));
            ArrayList<Card> cs = chooseCards(num, getCards());
            loseCard(cs);
            for (Person p: GameManager.getPlayers()) {
                p.otherPersonThrowPhase(this, cs);
            }
            println(this + " uses 忍戒");
            renMark += num;
            println(this + " now has " + renMark + " 忍mark");
        }
    }

    @WakeUpSkill("拜印")
    @Override
    public void beginPhase() {
        if (!hasWakenUp() && renMark >= 4) {
            setMaxHP(getMaxHP() - 1);
            wakeUp();
        }
    }

    @AfterWakeSkill("极略")
    public boolean jiLue(String s) {
        if (hasWakenUp() && renMark > 0 && launchSkill(s + "(极略)")) {
            renMark--;
            println(this + " now has " + renMark + " 忍mark");
            return true;
        }
        return false;
    }

    @AfterWakeSkill("鬼才")
    @Override
    public Card changeJudge(Card d) {
        if (jiLue("鬼才")) {
            Card c = requestCard(null);
            if (c != null) {
                addCard(CardsHeap.retrieve(c));
                CardsHeap.discard(d);
                return c;
            }
        }
        return null;
    }

    @AfterWakeSkill("放逐")
    public void fangZhu() {
        if (jiLue("放逐")) {
            Person target = selectPlayer();
            if (target != null) {
                target.drawCards(getMaxHP() - getHP());
                target.turnover();
            }
        }
    }

    @AfterWakeSkill("急智")
    @Override
    public void useStrategy() {
        if (jiLue("急智")) {
            drawCard();
        }
    }

    @AfterWakeSkill("制衡")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("制衡") && jiLue("制衡")) {
            println(this + " uses 制衡");
            ArrayList<Card> cs = chooseCards(0, getCards());
            if (!cs.isEmpty()) {
                loseCard(cs);
                drawCards(cs.size());
            }
            setHasUsedSkill1(true);
            return true;
        }
        return false;
    }

    @AfterWakeSkill("完杀")
    @Override
    public boolean hasWanSha() {
        if (jiLue("完杀")) {
            println(this + " uses 完杀");
            return true;
        }
        return false;
    }

    @Override
    public void killOther() {
        hasKilled = true;
    }

    @Skill("连破")
    @Override
    public void endPhase() {
        if (hasKilled) {
            hasKilled = false;
            run();
        }
    }

    @Override
    public String toString() {
        return "神司马懿";
    }
}
