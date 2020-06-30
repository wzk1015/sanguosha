package sanguosha.people.mountain;

import sanguosha.cards.Card;
import sanguosha.cards.basic.HurtType;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.GameManager;
import sanguosha.people.BlankPerson;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class CaiWenJi extends Person {
    public CaiWenJi() {
        super(3, "female", Nation.QUN);
    }

    @Skill("悲歌")
    @Override
    public void otherPersonHurtBySha(Person source, Person target) {
        if (launchSkill("悲歌")) {
            Card c = chooseCard(getCardsAndEquipments(), true);
            if (c == null) {
                return;
            }
            loseCard(c);
            Card d = CardsHeap.judge(target);
            switch (d.color()) {
                case HEART:
                    target.recover(1);
                    break;
                case DIAMOND:
                    target.drawCards(2);
                    break;
                case CLUB:
                    if (source.getCardsAndEquipments().size() == 1) {
                        source.loseCard(source.getCardsAndEquipments().get(0));
                    } else if (source.getCardsAndEquipments().size() >= 2) {
                        source.loseCard(source.chooseCards(2, source.getCardsAndEquipments()));
                    }
                    break;
                case SPADE:
                    source.turnover();
                    break;
                default:
                    GameManager.panic("Unexpected value: " + d.color());
            }
        }
    }

    @ForcesSkill("断肠")
    @Override
    public int hurt(ArrayList<Card> cs, Person source, int num, HurtType type) {
        int realNum = super.hurt(cs, source, num, type);
        if (isDead() && source != null) {
            println(this + " uses 断肠");
            Person p = new BlankPerson(source.getMaxHP());
            p.setSex(source.getSex());
            p.setNation(source.getNation());
            source.changePerson(p);
            if (source.isMyRound() && !source.isDead()) {
                p.usePhase();
                p.throwPhase();
                p.setMyRound(false);
                p.endPhase();
                p.println("----------" + p + "'s round ends" + "----------");
            }
        }
        return realNum;
    }

    @Override
    public String name() {
        return "蔡文姬";
    }

    @Override
    public String skillsDescription() {
        return "悲歌：当一名角色受到【杀】造成的伤害后，你可以弃置一张牌，然后令其进行判定，若结果为：" +
                "红桃，其回复1点体力；方块，其摸两张牌；梅花，伤害来源弃置两张牌；黑桃，伤害来源翻面。\n" +
                "断肠：锁定技，当你死亡时，杀死你的角色失去所有武将技能。(half implemented)";
    }
}
