package cards.strategy;

import cards.Color;
import cards.EquipType;
import cards.Strategy;
import cards.basic.Sha;
import manager.Utils;
import people.Person;

public class JieDaoShaRen extends Strategy {
    private Person target2;

    public JieDaoShaRen(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        if (!gotWuXie(getTarget())) {
            Utils.assertTrue(getTarget().hasEquipment(EquipType.weapon, null),
                    "target has no weapon");
            Sha sha = getTarget().requestSha();
            if (sha != null) {
                sha.setSource(getTarget());
                sha.setTarget(getTarget2());
                sha.use();
            } else {
                getSource().addCard(getTarget().getEquipments().get(EquipType.weapon));
                getTarget().getEquipments().put(EquipType.weapon, null);
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "借刀杀人";
    }

    public void setTarget2(Person target2) {
        this.target2 = target2;
    }

    public Person getTarget2() {
        return target2;
    }

    @Override
    public boolean needChooseTarget() {
        return true;
    }
}
