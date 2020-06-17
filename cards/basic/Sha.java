package cards.basic;

import cards.BasicCard;
import cards.Card;
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
    private ArrayList<Card> thisCard = new ArrayList<>();

    public Sha(Color color, int number, HurtType type) {
        super(color, number);
        this.type = type;
        thisCard.add(this);
    }

    public Sha(Color color, int number) {
        this(color, number, HurtType.normal);
    }

    public void setThisCard(ArrayList<Card> thisCard) {
        this.thisCard = thisCard;
    }

    public void setThisCard(Card thisCard) {
        ArrayList<Card> cs = new ArrayList<>();
        cs.add(thisCard);
        this.thisCard = cs;
    }

    public boolean useWeapon(String s) {
        if (getSource().hasEquipment(weapon, s)) {
            return getSource().chooseFromProvided("use " + s, "pass").equals("use " + s);
        }
        return false;
    }

    public void sha(int num) {
        getTarget().hurtBySha();
        int realNum = getTarget().hurt(thisCard, getSource(), num, type);
        if (type != HurtType.normal) {
            GameManager.linkHurt(thisCard, getSource(), realNum, type);
        }
    }

    public void beforeSha() {
        if (useWeapon("朱雀羽扇")) {
            this.type = HurtType.fire;
        }
        if (useWeapon("雌雄双股剑")) {
            if (!getSource().getSex().equals(getTarget().getSex())) {
                String choice = getTarget().chooseFromProvided(
                        "you throw a card", "he draws a card");
                if (choice.equals("you throw a card")) {
                    getTarget().requestCard(null);
                } else {
                    getSource().drawCard();
                }
            }
        }
        if (getSource().hasEquipment(weapon, "青釭剑")) {
            if (getTarget().hasEquipment(shield, null)) {
                ((Shield) getTarget().getEquipments().get(shield)).setValid(false);
            }
            else if (getTarget().hasBaZhen()) {
                getTarget().setBaZhen(false);
            }
        }
    }

    public void shaHit() {
        getTarget().beforeHurt();
        if (useWeapon("麒麟弓")) {
            if (getTarget().hasEquipment(plusOneHorse, null) &&
                    getTarget().hasEquipment(minusOneHorse, null)) {
                String choice = getSource().chooseFromProvided("shoot down plusonehorse",
                        "shoot down minusonehorse", "pass");
                if (choice.equals("shoot down minusonehorse")) {
                    getTarget().getEquipments().put(minusOneHorse, null);
                } else if (choice.equals("shoot down plusonehorse")) {
                    getTarget().getEquipments().put(plusOneHorse, null);
                }
            } else if (getTarget().hasEquipment(plusOneHorse, null) ||
                    getTarget().hasEquipment(minusOneHorse, null)) {
                String choice = getSource().chooseFromProvided("shoot down horse", "pass");
                if (choice.equals("shoot down horse")) {
                    if (getTarget().hasEquipment(plusOneHorse, null)) {
                        getTarget().getEquipments().put(plusOneHorse, null);
                    } else {
                        getTarget().getEquipments().put(minusOneHorse, null);
                    }
                }
            }
        }

        int numHurt = 1;
        if (getSource().isDrunk()) {
            numHurt++;
        }
        if (getSource().hasEquipment(weapon, "古锭刀") && getTarget().getCards().isEmpty()) {
            numHurt++;
        }
        if (getSource().isNaked()) {
            numHurt++;
        }
        sha(numHurt);
        getSource().shaSuccess(getTarget());
        if (useWeapon("三尖两刃刀")) {
            ArrayList<Person> nearbyPerson = GameManager.reachablePeople(getSource(), 1);
            if (!nearbyPerson.isEmpty()) {
                Person p = getSource().selectPlayer(nearbyPerson);
                p.hurt((Card) null, getSource(), 1);
            }
        }
    }

    public void shanSuccess() {
        if (useWeapon("贯石斧")) {
            String option = getSource().chooseFromProvided(
                    "throw two cards and hurt", "pass");
            if (option.equals("throw two cards and hurt")) {
                getSource().throwCard(getSource().chooseCards(2,
                        getTarget().getCardsAndEquipments()));
                shaHit();
            } else {
                getSource().shaGotShan(getTarget());
            }
        } else {
            getSource().shaGotShan(getTarget());
            if (useWeapon("青龙偃月刀")) {
                Sha s = getSource().requestSha();
                if (s != null) {
                    s.setTarget(getTarget());
                    s.setSource(getSource());
                    s.use();
                }
            }
        }
    }

    @Override
    public Object use() {

        getSource().shaBegin();
        getTarget().gotShaBegin(this);
        beforeSha();

        if (getTarget().canNotBeSha(this, getSource())) {
            IO.println("invalid sha");
            return false;
        }

        boolean needDouble = (getSource().hasRouLin() && getTarget().getSex().equals("female"))
                || (getTarget().hasRouLin() && getSource().getSex().equals("female"))
                || getSource().hasWuShuang();

        if (getSource().shaCanBeShan(getTarget()) && ((needDouble && getTarget().requestShan()
                && getTarget().requestShan()) || (!needDouble && getTarget().requestShan()))) {
            shanSuccess();
        }

        else {
            if (!getTarget().getCardsAndEquipments().isEmpty() && useWeapon("寒冰剑")) {
                Card c = getSource().chooseTargetCardsAndEquipments(getTarget());
                getTarget().loseCard(c);
                if (!getTarget().getCardsAndEquipments().isEmpty()) {
                    c = getSource().chooseTargetCardsAndEquipments(getTarget());
                    getTarget().loseCard(c);
                }
            } else {
                shaHit();
            }
        }

        if (getTarget().hasEquipment(shield, null)) {
            ((Shield) getTarget().getEquipments().get(shield)).setValid(true);
        }
        if (getTarget().hasBaZhen()) {
            getTarget().setBaZhen(true);
        }
        return true;
    }

    @Override
    public String toString() {
        if (type == HurtType.normal) {
            return "杀";
        } else if (type == HurtType.fire) {
            return "火杀";
        } else {
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
