package people.god;

import cards.Card;
import cards.EquipType;
import cards.equipments.Shield;
import manager.GameManager;
import manager.Utils;
import people.Person;
import skills.ForcesSkill;
import skills.Skill;

import java.util.ArrayList;
import java.util.Iterator;

public class ShenLvBu extends God {
    private int baoNuMark = 2;
    private boolean isWuQian = false;

    public ShenLvBu() {
        super(5, null);
    }

    @ForcesSkill("暴怒")
    @Override
    public void gotHurt(ArrayList<Card> cards, Person p, int num) {
        println(this + " got 1 暴怒mark");
        baoNuMark++;
    }

    @Override
    public void hurtOther(Person p) {
        println(this + " got 1 暴怒mark");
        baoNuMark++;
    }

    @ForcesSkill("无谋")
    @Override
    public void useStrategy() {
        if (baoNuMark == 0 || chooseFromProvided("lose 1 暴怒mark", "lose 1HP").equals("lose 1HP")) {
            loseHP(1);
        }
        else {
            println(this + " lost 1 暴怒mark");
            baoNuMark--;
        }
    }

    @ForcesSkill("无双")
    @Override
    public boolean hasWuShuang() {
        return isWuQian;
    }

    @Skill("无前")
    public void wuQian() {
        println(this + " uses 无前");
        Person p = selectPlayer();
        if (p == null) {
            return;
        }
        if (p.hasEquipment(EquipType.shield, null)) {
            ((Shield) p.getEquipments().get(EquipType.shield)).setValid(false);
        }
        isWuQian = true;
        println(this + "lost 2 暴怒mark");
        baoNuMark -= 2;

    }

    @Skill("神愤")
    public void shenFen() {
        println(this + " uses 神愤");
        println(this + "lost 6 暴怒mark");
        baoNuMark -= 6;
        Iterator<Person> it = GameManager.getPlayers().iterator();
        while (it.hasNext()) {
            Person p = it.next();
            p.hurt((Card) null, this, 1);
            if (p.isDead()) {
                it.remove();
                continue;
            }
            p.loseCard(new ArrayList<>(p.getEquipments().values()));
            if (p.getCards().size() <= 4) {
                p.loseCard(p.getCards());
            } else {
                p.loseCard(p.chooseCards(4, p.getCards()));
            }
        }
        turnover();
    }

    @Override
    public boolean useSkillInUsePhase(String order) {
        if (baoNuMark >= 2 && order.equals("无前")) {
            wuQian();
            return true;
        }
        if (baoNuMark >= 6 && order.equals("神愤")) {
            shenFen();
            return true;
        }
        return false;
    }

    @Override
    public void endPhase() {
        Utils.assertTrue(baoNuMark >= 0, "invalid 暴怒 mark: " + baoNuMark);
        isWuQian = false;
        for (Person p: GameManager.getPlayers()) {
            if (p.hasEquipment(EquipType.shield, null)) {
                ((Shield) p.getEquipments().get(EquipType.shield)).setValid(true);
            }
        }
    }

    @Override
    public String toString() {
        return "神吕布";
    }
}
