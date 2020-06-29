package sanguosha.people.mountain;

import sanguosha.cards.Card;
import sanguosha.cards.Equipment;
import sanguosha.cards.JudgeCard;
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
            Card c = chooseCard(getCardsAndEquipments());
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
                    } else if (source.getCardsAndEquipments().size() >= 3) {
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
            for (Person p : GameManager.getPlayers()) {
                if (p == source) {
                    p = new BlankPerson(source.getMaxHP());
                    p.setSex(source.getSex());
                    p.setCurrentHP(source.getHP());
                    p.setDaWu(source.isDaWu());
                    p.setKuangFeng(source.isKuangFeng());
                    p.setIdentity(source.getIdentity());
                    p.setNation(source.getNation());
                    p.setDrunk(source.isDrunk());
                    p.setShaCount(source.getShaCount());
                    if (source.isLinked()) {
                        p.link();
                    }
                    if (source.isTurnedOver()) {
                        p.turnover();
                    }
                    p.addCard(source.getCards());
                    for (Equipment equipment : source.getEquipments().values()) {
                        p.putOnEquipment(equipment);
                    }
                    for (JudgeCard judgeCard : source.getJudgeCards()) {
                        p.addJudgeCard(judgeCard);
                    }
                    CardsHeap.discard(source.getExtraCards());
                    source.loseCard(p.getCards(), false, false);
                    source.loseCard(new ArrayList<>(p.getRealJudgeCards()), false, false);
                    source.loseCard(new ArrayList<>(p.getEquipments().values()), false, false);
                    break;
                }
            }
        }
        return realNum;
    }

    @Override
    public String toString() {
        return "蔡文姬";
    }
}
