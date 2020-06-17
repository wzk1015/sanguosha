package people.mountain;

import cards.Card;
import cards.Color;
import cards.strategy.ShunShouQianYang;
import cardsheap.CardsHeap;
import manager.GameManager;
import people.Nation;
import people.Person;
import skills.AfterWakeSkill;
import skills.Skill;
import skills.WakeUpSkill;

import java.util.ArrayList;

public class DengAi extends Person {
    private ArrayList<Card> tian = new ArrayList<>();

    public DengAi() {
        super(4, Nation.WEI);
    }

    @Skill("屯田")
    public void tunTian() {
        if (launchSkill("屯田")) {
            Card c = CardsHeap.judge(this);
            if (c.color() != Color.HEART) {
                CardsHeap.getJudgeCard();
                tian.add(c);
            }
        }
    }

    @Override
    public void lostCard() {
        tunTian();
    }

    @Override
    public void lostEquipment() {
        tunTian();
    }

    @Override
    public int numOfTian() {
        return tian.size();
    }

    @WakeUpSkill("凿险")
    @Override
    public void beginPhase() {
        if (!hasWakenUp() && tian.size() >= 3) {
            println(this + " uses 凿险");
            setMaxHP(getMaxHP() - 1);
            if (getHP() > getMaxHP()) {
                setCurrentHP(getMaxHP());
            }
            wakeUp();
        }
    }

    @AfterWakeSkill("急袭")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("急袭") && hasWakenUp()) {
            println(this + " uses 急袭");
            Card c = chooseCard(tian);
            if (c == null) {
                return true;
            }
            ShunShouQianYang shun = new ShunShouQianYang(c.color(), c.number());
            if (GameManager.askTarget(shun, this)) {
                tian.remove(c);
                useCard(shun);
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "邓艾";
    }
}
