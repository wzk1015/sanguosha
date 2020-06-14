package people.wind;

import cards.Card;
import cards.Color;
import cards.basic.HurtType;
import cardsheap.CardsHeap;
import manager.GameManager;
import people.Identity;
import people.Nation;
import people.Person;
import skills.KingSkill;
import skills.Skill;

public class ZhangJiao extends Person {

    public ZhangJiao() {
        super(3, Nation.QUN);
    }

    @Skill("雷击")
    @Override
    public boolean requestShan() {
        if (super.requestShan()) {
            if (launchSkill("雷击")) {
                Person p = GameManager.selectPlayer(this);
                if (p != null) {
                    Card c = CardsHeap.judge(this);
                    if (c.color() == Color.SPADE) {
                        int realNum = p.hurt((Card) null, this, 2, HurtType.thunder);
                        GameManager.linkHurt(null, this, realNum, HurtType.thunder);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Skill("鬼道")
    @Override
    public Card changeJudge(Card d) {
        if (launchSkill("鬼道")) {
            Card c =  requestRedBlack("black");
            if (c != null) {
                CardsHeap.retrieve(c);
                addCard(d);
                return c;
            }
        }
        return null;
    }

    @KingSkill("黄天")
    @Override
    public void otherPersonUsePhase(Person p) {
        if (getIdentity() == Identity.KING && p.getNation() == Nation.QUN
                && p.launchSkill("黄天")) {
            String type = p.chooseFromProvided("闪", "闪电");
            if (type != null) {
                Card c = p.requestCard(type);
                if (c != null) {
                    CardsHeap.retrieve(c);
                    addCard(c);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "张角";
    }
}