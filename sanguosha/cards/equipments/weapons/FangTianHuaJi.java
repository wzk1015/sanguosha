package sanguosha.cards.equipments.weapons;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.basic.Sha;
import sanguosha.cards.equipments.Weapon;
import sanguosha.manager.Utils;
import sanguosha.people.Person;

public class FangTianHuaJi extends Weapon {
    private Card sha;

    public FangTianHuaJi(Color color, int number) {
        super(color, number, 4);
    }

    @Override
    public Object use() {
        String option = getSource().chooseNoNull("1target", "2targets", "3targets");
        Person target3 = null;
        if (option.equals("3targets")) {
            Sha s3 = new Sha(sha.color(), sha.number(), ((Sha) sha).getType());
            if (s3.askTarget(getSource()) && s3.getTarget() != sha.getTarget()) {
                target3 = s3.getTarget();
                Utils.assertTrue(target3 != null, "sha3 target is null");
                s3.use();
            }
        }
        if (option.equals("3targets") || option.equals("2targets")) {
            Sha s2 = new Sha(sha.color(), sha.number(), ((Sha) sha).getType());
            if (s2.askTarget(getSource()) && s2.getTarget() != sha.getTarget()
                    && s2.getTarget() != target3) {
                Utils.assertTrue(s2.getTarget() != null, "sha2 target is null");
                s2.use();
            }
        }
        return null;
    }

    public void setsha(Card sha) {
        this.sha = sha;
    }

    @Override
    public String toString() {
        return "方天画戟";
    }
}
