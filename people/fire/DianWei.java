package people.fire;

import cards.Card;
import cards.equipments.Weapon;
import people.Nation;
import people.Person;
import skills.Skill;

public class DianWei extends Person {
    public DianWei() {
        super(4, Nation.WEI);
    }

    @Skill("强袭")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("强袭") && hasNotUsedSkill1()) {
            println(this + " uses 强袭");
            Person p = selectPlayer();
            if (p == null) {
                return true;
            }
            if (chooseFromProvided("throw a weapon", "lose 1 HP").equals("lost 1 HP")) {
                loseHP(1);
                if (isDead()) {
                    p.hurt((Card) null, null, 1);
                } else {
                    p.hurt((Card) null, this, 1);
                }
            }
            else {
                Card c;
                do {
                    println("choose a weapon");
                    c = chooseCard(getCardsAndEquipments());
                } while (!(c instanceof Weapon));
                loseCard(c);
                p.hurt((Card) null, this, 1);
            }
            setHasUsedSkill1(true);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "典韦";
    }
}
