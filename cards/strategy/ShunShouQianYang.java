package cards.strategy;

import cards.Card;
import cards.Color;
import cards.Strategy;

import java.util.ArrayList;

public class ShunShouQianYang extends Strategy {

    public ShunShouQianYang(Color color, int number) {
        super(color, number, 1);
    }

    @Override
    public Object use() {
        if (!gotWuXie()) {
            getTarget().printAllCards();
            String option;
            if (!getTarget().getEquipments().isEmpty() && !getTarget().getJudgeCards().isEmpty()) {
                option = getSource().chooseFromProvided("hand cards", "equipments", "judge cards");
            } else if (!getTarget().getEquipments().isEmpty()) {
                option = getSource().chooseFromProvided("hand cards", "equipments");
            } else if (!getTarget().getJudgeCards().isEmpty()) {
                option = getSource().chooseFromProvided("hand cards", "judge cards");
            } else {
                option = "hand cards";
            }
            Card c;
            if (option.equals("hand cards")) {
                c = getSource().chooseAnonymousCard(getTarget().getCards());
            } else if (option.equals("equipments")) {
                c = getSource().chooseCard(new ArrayList<>(getTarget().getEquipments().values()));
            } else {
                c = getSource().chooseCard(new ArrayList<>(getTarget().getJudgeCards()));
            }
            getTarget().loseCard(c, false);
            getSource().addCard(c);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "顺手牵羊";
    }

    @Override
    public boolean needChooseTarget() {
        return true;
    }
}
