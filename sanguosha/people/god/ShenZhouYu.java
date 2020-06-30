package sanguosha.people.god;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.basic.HurtType;
import sanguosha.manager.GameManager;
import sanguosha.people.Person;
import sanguosha.skills.RestrictedSkill;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class ShenZhouYu extends God {
    private boolean hasYeYan = false;

    public ShenZhouYu() {
        super(4, null);
    }

    @Skill("琴音")
    @Override
    public void throwPhase() {
        int num = getCards().size() - getHP();
        if (num > 0) {
            printlnToIO(String.format("You need to throw %d cards", num));
            ArrayList<Card> cs = chooseCards(num, getCards());
            loseCard(cs);
            for (Person p: GameManager.getPlayers()) {
                p.otherPersonThrowPhase(this, cs);
            }
        }
        if (num >= 2) {
            if (launchSkill("琴音")) {
                if (chooseNoNull("everyone lose 1HP", "everyone recover 1HP")
                        .equals("everyone lose 1HP")) {
                    for (Person p: GameManager.getPlayers()) {
                        p.loseHP(1);
                    }
                }
                else {
                    for (Person p: GameManager.getPlayers()) {
                        p.recover(1);
                    }
                }
            }
        }
    }

    @RestrictedSkill("业炎")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("业炎") && !hasYeYan) {
            println(this + " uses 业炎");
            printlnToIO("choose your 业炎 strategy");
            String option = chooseFromProvided("1","1+1","2","1+1+1","1+2","3");
            if (option == null) {
                return true;
            }
            Person p1 = null;
            Person p2 = null;
            Person p3;
            int realNum;
            if (option.equals("2") || option.equals("1+2") || option.equals("3")) {
                printlnToIO("throw 4 cards:");
                Color[] colors = {Color.SPADE, Color.CLUB, Color.HEART, Color.DIAMOND};
                for (Color color: colors) {
                    if (requestColor(color) == null) {
                        return false;
                    }
                }
            } if (option.equals("1") || option.equals("1+1") || option.equals("1+1+1")
                    || option.equals("1+2") || option.equals("2") || option.equals("3")) {
                p1 = selectPlayer();
                if (option.equals("3")) {
                    loseHP(3);
                    realNum = p1.hurt((Card) null, this, 3, HurtType.fire);
                } else if (option.equals("2")) {
                    loseHP(3);
                    realNum = p1.hurt((Card) null, this, 2, HurtType.fire);
                } else {
                    realNum = p1.hurt((Card) null, this, 1, HurtType.fire);
                }
                GameManager.linkHurt(null, this, realNum, HurtType.fire);
            } if (option.equals("1+1") || option.equals("1+1+1") || option.equals("1+2")) {
                do {
                    printlnToIO("choose another person");
                    p2 = selectPlayer();
                } while (p2 == p1);
                if (option.equals("1+2")) {
                    loseHP(3);
                    realNum = p2.hurt((Card) null, this, 2, HurtType.fire);
                } else {
                    realNum = p2.hurt((Card) null, this, 1, HurtType.fire);
                }
                GameManager.linkHurt(null, this, realNum, HurtType.fire);
            } if (option.equals("1+1+1")) {
                do {
                    printlnToIO("choose another person");
                    p3 = selectPlayer();
                } while (p3 == p1 || p3 == p2);
                realNum = p3.hurt((Card) null, this, 1, HurtType.fire);
                GameManager.linkHurt(null, this, realNum, HurtType.fire);
            }

            hasYeYan = true;
            return true;
        }
        return false;
    }

    @Override
    public String name() {
        return "神周瑜";
    }

    @Override
    public String skillsDescription() {
        return "琴音：弃牌阶段结束时，若你于此阶段内弃置过你的至少两张手牌，则你可以选择一项：" +
                "1.令所有角色各回复1点体力；2.令所有角色各失去1点体力。\n" +
                "业炎：限定技，出牌阶段，你可以选择至多三名角色，对这些角色造成共计至多3点火焰伤害。\n" +
                "若你将对一名角色分配2点或更多火焰伤害，你须先弃置四张花色各不相同的手牌并失去3点体力。";
    }
}
