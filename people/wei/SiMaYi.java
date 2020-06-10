package people.wei;

import cards.Card;
import manager.IO;
import people.Nation;
import people.Person;
import skills.Skill;

import java.util.ArrayList;

public class SiMaYi extends Person {
    public SiMaYi() {
        super(3, Nation.WEI);
    }

    @Skill("鬼才")
    @Override
    public Card changeJudge() {
        if (IO.launchSkill(this, "鬼才")) {
            Card c = IO.requestCard(null, this);
            if (c != null) {
                IO.println(this + "uses 鬼才");
            }
            return c;
        }
        return null;
    }

    @Skill("反馈")
    @Override
    public void gotHurt(Card card, Person p, int num) {
        if (p != null && p.getCardsAndEquipments().size() > 0
                && IO.launchSkill(this, "反馈")) {
            IO.printAllCards(p);
            String option;
            if (!p.getEquipments().isEmpty()) {
                option = IO.chooseFromProvided(this,
                        "hand cards", "equipments");
            } else {
                option = "hand cards";
            }
            Card c;
            if (option.equals("hand cards")) {
                c = IO.chooseAnonymousCard(this, p.getCards());
            } else {
                c = IO.chooseCard(this,
                        new ArrayList<>(p.getEquipments().values()));
            }
            p.loseCard(c, false);
            addCard(c);
        }
    }

    @Override
    public String toString() {
        return "司马懿";
    }
}
