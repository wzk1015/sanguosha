package cards.strategy;

import cards.Card;
import cards.Color;
import cards.Strategy;

import java.util.ArrayList;

public class GuoHeChaiQiao extends Strategy {
    public GuoHeChaiQiao(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        if (!gotWuXie()) {
            getTarget().printAllCards();
            String option;
            if (!getTarget().getEquipments().isEmpty()
                    && !getTarget().getRealJudgeCards().isEmpty()) {
                option = getSource().chooseFromProvided("hand cards", "equipments", "judge cards");
            } else if (!getTarget().getEquipments().isEmpty()) {
                option = getSource().chooseFromProvided("hand cards", "equipments");
            } else if (!getTarget().getRealJudgeCards().isEmpty()) {
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
                c = getSource().chooseCard(new ArrayList<>(getTarget().getRealJudgeCards()));
            }
            getTarget().loseCard(c, true);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "过河拆桥";
    }

    @Override
    public boolean needChooseTarget() {
        return true;
    }
}
