package cards.basic;

import cards.BasicCard;
import cards.Color;
import cards.equipments.Shield;
import manager.GameManager;
import manager.IO;
import people.Person;

import java.util.ArrayList;

import static cards.EquipType.minusOneHorse;
import static cards.EquipType.plusOneHorse;
import static cards.EquipType.shield;
import static cards.EquipType.weapon;

public class Sha extends BasicCard {
    private HurtType type;

    public Sha(Color color, int number, HurtType type) {
        super(color, number);
        this.type = type;
    }

    public Sha(Color color, int number) {
        this(color, number, HurtType.normal);
    }

    public void sha(int num) {
        getTarget().hurtBySha();
        int realNum = getTarget().hurt(getSource(),2, type);
        if (type != HurtType.normal) {
            GameManager.linkHurt(getSource(), realNum, type);
        }
    }

    public void beforeSha() {
        if (getSource().hasEquipment(weapon, "朱雀羽扇")) {
            this.type = HurtType.fire;
        }
        if (getSource().hasEquipment(weapon, "雌雄双股剑")) {
            if (!getSource().getSex().equals(getTarget().getSex())) {
                String choice = IO.chooseFromProvided(getTarget(),
                        "you throw a card", "he draw a card");
                if (choice.equals("you throw a card")) {
                    getTarget().throwCard(IO.chooseCard(getTarget(), getTarget().getCards()));
                } else {
                    getSource().drawCard();
                }
            }
        }
        if (getSource().hasEquipment(weapon, "青釭剑")) {
            if (getTarget().hasEquipment(shield, null)) {
                ((Shield) getTarget().getEquipments().get(shield)).setValid(false);
            }
        }
    }

    public void shaHit() {
        getTarget().beforeHurt();
        int numHurt = 1;
        if (getSource().isDrunk()) {
            numHurt++;
        }
        if (getSource().hasEquipment(weapon, "古锭刀") && getTarget().getCards().isEmpty()) {
            numHurt++;
        }
        sha(numHurt);
        getSource().shaSuccess();
        afterShaHit();
    }

    public void afterShaHit() {
        if (getSource().hasEquipment(weapon, "麒麟弓")) {
            if (getTarget().hasEquipment(plusOneHorse, null) &&
                    getTarget().hasEquipment(minusOneHorse, null)) {
                String choice = IO.chooseFromProvided(getSource(), "shoot down plusonehorse",
                        "shoot down minusonehorse", "pass");
                if (choice.equals("shoot down minusonehorse")) {
                    getTarget().getEquipments().put(minusOneHorse, null);
                } else if (choice.equals("shoot down plusonehorse")) {
                    getTarget().getEquipments().put(plusOneHorse, null);
                }
            }
            else if (getTarget().hasEquipment(plusOneHorse, null) ||
                    getTarget().hasEquipment(minusOneHorse, null)) {
                String choice = IO.chooseFromProvided(getSource(), "shoot down horse", "pass");
                if (choice.equals("shoot down horse")) {
                    if (getTarget().hasEquipment(plusOneHorse, null)) {
                        getTarget().getEquipments().put(plusOneHorse, null);
                    } else {
                        getTarget().getEquipments().put(minusOneHorse, null);
                    }
                }
            }
        }

        if (getSource().hasEquipment(weapon, "三尖两刃刀")) {
            ArrayList<Person> nearbyPerson = GameManager.reachablePeople(getSource(), 1);
            if (!nearbyPerson.isEmpty()) {
                Person p = GameManager.selectPlayer(getSource(), nearbyPerson);
                p.hurt(getSource(), 1);
            }
        }
    }

    @Override
    public Object use() {
        beforeSha();
        getSource().shaBegin();

        if (!getTarget().canBeSha(this)) {
            return false;
        }

        if (!getTarget().requestShan()) {
            if (getSource().hasEquipment(weapon, "贯石斧")) {
                String option = IO.chooseFromProvided(getSource(),
                        "throw two cards and hurt", "pass");
                if (option.equals("throw two cards")) {
                    getSource().throwCard(IO.chooseCards(getSource(), 2,
                            getTarget().getCardsAndEquipments()));
                    shaHit();
                } else {
                    getSource().shaGotShan();
                    return false;
                }
            }
            else {
                getSource().shaGotShan();
                if (getSource().hasEquipment(weapon, "青龙偃月刀")) {
                    Sha s = getSource().requestSha();
                    if (s != null) {
                        s.use();
                    }
                }
                return false;
            }
        }

        else {
            if (getSource().hasEquipment(weapon, "寒冰剑")) {
                String option = IO.chooseFromProvided(getSource(), "throw two cards", "hurt");
                if (option.equals("throw two cards")) {
                    getTarget().throwCard(IO.chooseCards(getSource(), 2,
                            getTarget().getCardsAndEquipments()));
                } else {
                    shaHit();
                }
            }
            else {
                shaHit();
            }
        }

        if (getTarget().hasEquipment(shield, null)) {
            ((Shield) getTarget().getEquipments().get(shield)).setValid(true);
        }
        return true;
    }

    @Override
    public String toString() {
        if (type == HurtType.normal) {
            return "杀";
        }
        else if (type == HurtType.fire) {
            return "火杀";
        }
        else {
            return "雷杀";
        }
    }

    public HurtType getType() {
        return type;
    }

    @Override
    public boolean needChooseTarget() {
        return true;
    }
}
