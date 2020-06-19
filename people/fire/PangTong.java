package people.fire;

import cards.Card;
import cards.Color;
import cards.strategy.TieSuoLianHuan;
import cardsheap.CardsHeap;
import people.Nation;
import people.Person;
import skills.RestrictedSkill;
import skills.Skill;

public class PangTong extends Person {
    private boolean hasNiePan = false;

    public PangTong() {
        super(3, Nation.SHU);
    }

    @Skill("连环")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("连环")) {
            Card c = requestColor(Color.CLUB);
            if (c == null) {
                return true;
            }
            if (chooseFromProvided("throw", "use").equals("throw")) {
                drawCard();
                return true;
            } else {
                TieSuoLianHuan ts = new TieSuoLianHuan(c.color(), c.number());
                if (ts.askTarget(this)) {
                    useCard(ts);
                } else {
                    addCard(CardsHeap.retrieve(c));
                }
            }
        }
        return false;
    }

    @RestrictedSkill("涅槃")
    @Override
    public void dying() {
        if (!hasNiePan && launchSkill("涅槃")) {
            hasNiePan = true;
            loseCard(getRealJudgeCards());
            loseCard(getCardsAndEquipments());
            setCurrentHP(3);
            drawCards(3);
            if (isTurnedOver()) {
                turnover();
            }
            if (isLinked()) {
                link();
            }
            setDrunk(false);
            return;
        }
        super.dying();
    }

    @Override
    public String toString() {
        return "庞统";
    }
}
