package people.fire;

import cards.Card;
import people.Nation;
import people.Person;
import skills.ForcesSkill;
import skills.Skill;

import java.util.ArrayList;

public class PangDe extends Person {
    public PangDe() {
        super(4, Nation.QUN);
    }

    @ForcesSkill("马术")
    @Override
    public boolean hasMaShu() {
        return true;
    }

    @Skill("猛进")
    @Override
    public void shaGotShan(Person p) {
        if (p.getCardsAndEquipments().size() > 0 && launchSkill("猛进")) {
            p.printAllCards();
            String option;
            if (!p.getEquipments().isEmpty()) {
                option = chooseFromProvided("hand cards", "equipments");
            } else {
                option = "hand cards";
            }
            Card c;
            if (option.equals("hand cards")) {
                c = chooseAnonymousCard(p.getCards());
            } else {
                c = chooseCard(new ArrayList<>(p.getEquipments().values()));
            }
            p.loseCard(c);
        }
    }

    @Override
    public String toString() {
        return "庞德";
    }
}