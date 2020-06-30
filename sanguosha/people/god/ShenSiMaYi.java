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
                CardsHeap.retrieve(c);
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
    public String getExtraInfo() {
        return renMark + " 忍戒 marks";
    }

    @Override
    public String name() {
        return "神司马懿";
    }

    @Override
    public String skillsDescription() {
        return "忍戒：锁定技，当你受到伤害后，或于弃牌阶段内弃置手牌后，你获得X枚“忍”标记（X为伤害值或弃置的手牌数）。\n" +
                "连破：当你杀死一名角色后，你可于此回合结束后获得一个额外回合。\n" +
                "拜印：觉醒技，准备阶段开始时，若“忍”标记的数量不小于4，你减1点体力上限，然后获得“极略”。\n" +
                (hasWakenUp() ? "极略：你可以弃置1枚“忍”标记，发动下列一项技能：“鬼才”、“放逐”、“集智”、“制衡”或“完杀”。\n" +
                "鬼才：当一名角色的判定牌生效前，你可以打出一张手牌代替之。\n" +
                "放逐：当你受到伤害后，你可以令一名其他角色翻面，然后该角色摸X张牌（X为你已损失的体力值）。\n" +
                "集智：当你使用普通锦囊牌时，你可以摸一张牌。\n" +
                "制衡：出牌阶段限一次，你可以弃置任意张牌，然后摸等量的牌。\n" +
                "完杀：锁定技，你的回合内，只有你和处于濒死状态的角色才能使用【桃】。" : "");
    }
}
