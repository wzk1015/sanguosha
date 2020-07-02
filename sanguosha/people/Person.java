package sanguosha.people;

import sanguosha.GameLauncher;
import sanguosha.cards.Card;
import sanguosha.cards.Equipment;
import sanguosha.cards.JudgeCard;
import sanguosha.cards.Strategy;
import sanguosha.cards.basic.Jiu;
import sanguosha.cards.basic.Sha;
import sanguosha.cards.basic.Shan;
import sanguosha.cards.basic.Tao;
import sanguosha.cards.equipments.weapons.FangTianHuaJi;
import sanguosha.cards.strategy.TieSuoLianHuan;
import sanguosha.cards.strategy.WuXieKeJi;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.GameManager;

import sanguosha.manager.IO;
import sanguosha.manager.Utils;
import sanguosha.skills.AfterWakeSkill;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.KingSkill;
import sanguosha.skills.RestrictedSkill;
import sanguosha.skills.Skill;
import sanguosha.skills.WakeUpSkill;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;

import static sanguosha.cards.EquipType.weapon;
import static sanguosha.manager.IO.showUsingCard;

public abstract class Person extends Attributes implements Serializable {
    private boolean isZuoCi = false;
    private ArrayList<Person> huaShen = new ArrayList<>();
    private Person selected = null;

    public Person(int maxHP, String sex, Nation nation) {
        Utils.assertTrue(sex.equals("male") || sex.equals("female"), "invalid sex");
        this.setMaxHP(maxHP);
        this.setCurrentHP(maxHP);
        this.setSex(sex);
        this.setNation(nation);
    }

    public Person(int maxHP, Nation nation) {
        this(maxHP, "male", nation);
    }

    public void run() {
        println("----------" + this + "'s round begins" + "----------");
        if (isTurnedOver()) {
            turnover();
            return;
        }
        setHasUsedSkill1(false);
        setMyRound(true);
        if (huaShen(true)) {
            return;
        }
        beginPhase();
        if (isDead()) {
            return;
        }
        ArrayList<String> states = new ArrayList<>();
        if (!skipJudge()) {
            states = judgePhase();
        }
        if (isDead()) {
            return;
        }
        if (!states.contains("skip draw") && !skipDraw()) {
            drawPhase();
        }
        setShaCount(getMaxShaCount());
        if (!states.contains("skip use") && !skipUse()) {
            for (Person p2: GameManager.getPlayers()) {
                p2.otherPersonUsePhase(this);
            }
            usePhase();
        }
        if (isDead()) {
            return;
        }
        if (!skipThrow()) {
            throwPhase();
        }
        setMyRound(false);
        endPhase();
        huaShen(false);
        Utils.assertTrue(getHP() <= getMaxHP(), "currentHP exceeds maxHP");
        println("----------" + this + "'s round ends" + "----------");
    }

    public void beginPhase() {

    }

    public ArrayList<String> judgePhase() {
        Utils.assertTrue(getJudgeCards().size() <= 3,
                "too many judgecards: " + getJudgeCards().size());
        ArrayList<String> states = new ArrayList<>();
        for (JudgeCard jc : getJudgeCards()) {
            println("judging " + jc);
            String state = jc.use();
            if (jc.isNotTaken()) {
                CardsHeap.discard(jc.getThisCard());
            } else {
                jc.setTaken(false);
            }
            if (state != null) {
                println(state);
                states.add(state);
            } else {
                println("judgecard failed");
            }
        }
        getJudgeCards().clear();
        return states;
    }

    public void drawPhase() {
        println(this + " draws 2 cards from cards heap");
        drawCards(2);
    }

    public boolean useSha(Card card) {
        if (isMyRound()) {
            if (getShaCount() != 0 || hasEquipment(weapon, "诸葛连弩")) {
                setShaCount(getShaCount() - 1);
            } else {
                printlnToIO("You can't 杀 anymore");
                return false;
            }
        }
        if (getCards().isEmpty() && hasEquipment(weapon, "方天画戟")) {
            ((FangTianHuaJi) getEquipments().get(weapon)).setsha(card);
            getEquipments().get(weapon).setSource(this);
            getEquipments().get(weapon).use();
        }
        return true;
    }

    public void putOnEquipment(Card card) {
        if (this.getEquipments().get(((Equipment) card).getEquipType()) != null) {
            loseCard(getEquipments().get(((Equipment) card).getEquipType()));
        }
        println(this + " puts on equipment " + card);
        getCards().remove(card);
        card.setTaken(true);
        this.getEquipments().put(((Equipment) card).getEquipType(), (Equipment) card);
    }

    public boolean useCard(Card card) {
        if (card instanceof Sha) {
            if (!useSha(card)) {
                return false;
            }
        }
        if ((card instanceof Tao && getHP() == getMaxHP()) || card instanceof Shan ||
                card instanceof WuXieKeJi || (card instanceof Jiu && isDrunk())) {
            printlnToIO("You can't use that");
            return false;
        }
        if (card instanceof Equipment) {
            putOnEquipment(card);
            return true;
        }

        if (card instanceof JudgeCard) {
            if (card.getTarget().addJudgeCard((JudgeCard) card)) {
                getCards().remove(card);
                showUsingCard(card);
                card.setTaken(true);
                return true;
            }
            return false;
        }
        if (card instanceof Strategy) {
            useStrategy();
            throwCard(card);
            card.setTaken(true);
        }
        getCards().remove(card);
        if (!isDead()) {
            showUsingCard(card);
            card.use();
        }
        return true;
    }

    public boolean parseOrder(String order) {
        Card card;

        if (useSkillInUsePhase(order)) {
            return true;
        }
        else if (order.equals("丈八蛇矛") && hasEquipment(weapon, "丈八蛇矛") && getCards().size() >= 2) {
            getEquipments().get(weapon).setSource(this);
            card = (Card) getEquipments().get(weapon).use();
        }
        else if (order.contains("help")) {
            IO.showHelp("[use phase]: input number: use card, " +
                    "name: skill or weapon (e.g. '制衡'), 'q':end phase");
            return true;
        } else {
            try {
                card = getCards().get(Integer.parseInt(order) - 1);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                printlnToIO("Wrong input");
                return false;
            }
        }

        if (card instanceof TieSuoLianHuan) {
            if (chooseNoNull("throw", "use").equals("throw")) {
                loseCard(card);
                drawCard();
                return true;
            }
        }

        if (!card.askTarget(this)) {
            return false;
        }

        boolean used = useCard(card);

        if (card.isNotTaken() && used) {
            throwCard(card);
        } else if (!card.isNotTaken()) {
            card.setTaken(false);
        }
        return used;
    }

    public void usePhase() {
        if (GameLauncher.isCommandLine()) {
            printlnToIO(getPlayerStatus(true, false));
        }
        while (!isDead()) {
            if (hasEquipment(weapon, "丈八蛇矛")) {
                printlnToIO("【丈八蛇矛】");
            }
            String order = input("input an order, 'help' for help");
            if (order.equals("q")) {
                break;
            }
            parseOrder(order);
            printlnToIO(this + "'s current hand cards: ");
            printCards(getCards());
        }
    }

    public void throwPhase() {
        int num = getCards().size() - getHP();
        if (num > 0) {
            printlnToIO(String.format("You need to throw %d cards", num));
            ArrayList<Card> cs = chooseCards(num, getCards());
            loseCard(cs);
            for (Person p: GameManager.getPlayers()) {
                p.otherPersonThrowPhase(this, cs);
            }
        }

    }

    public void endPhase() {

    }

    public final String help() {
        return (isZuoCi ? "化身：游戏开始时，你随机获得两张武将牌作为\"化身\"牌，然后亮出其中一张，" +
                "获得该\"化身\"牌的一个技能。回合开始时或结束后，你可以更改亮出的\"化身\"牌。\n" +
                "新生：当你受到1点伤害后，你可以获得一张新的\"化身\"牌。\n\n" : "") + skillsDescription();
    }

    public abstract String skillsDescription();

    public final String toString() {
        if (isZuoCi) {
            return "左慈[" + name() + "]";
        }
        return name();
    }

    public abstract String name();

    public String getPlayerStatus(boolean privateAccess, boolean onlyCards) {
        String ans = "";
        if (!onlyCards) {
            ans += this.toString() + "  ";
            for (String s : getSkills()) {
                ans += "【" + s + "】";
            }
            ans += "  HP: " + getHP() + "/" + getMaxHP() + "\n";
            if (isDrunk() || isDaWu() || isKuangFeng() || isLinked() ||
                    isTurnedOver() || hasWakenUp()) {
                ans += isDrunk() ? "[喝酒]" : "";
                ans += isTurnedOver() ? "[翻面]" : "";
                ans += isLinked() ? "[连环]" : "";
                ans += isKuangFeng() ? "[狂风]" : "";
                ans += isDaWu() ? "[大雾]" : "";
                ans += hasWakenUp() ? "[觉醒]\n" : "\n";
            }
            ans += getExtraInfo() + (getExtraInfo().isEmpty() ? "" : "\n");
        }
        if (privateAccess) {
            ans += "identity: " + getIdentity();
            ans += "\nhand cards:\n";
            for (int i = 1; i <= getCards().size(); i++) {
                ans += "【" + i + "】" + getCards().get(i - 1).info() + getCards().get(i - 1) + "\n";
            }
        }
        else {
            ans += getCards().size() + " hand cards";
        }
        ans += "\nequipments:";
        ArrayList<Card> equips = new ArrayList<>(getEquipments().values());
        for (int i = 1; i <= equips.size(); i++) {
            ans += equips.get(i - 1) + " ";
        }
        ans += "\njudge cards:";
        ArrayList<Card> judges = new ArrayList<>(getJudgeCards());
        for (int i = 1; i <= judges.size(); i++) {
            ans += judges.get(i - 1) + " ";
        }
        return ans;
    }

    public String getKingSkill() {
        if (getIdentity() != Identity.KING) {
            return "";
        }
        for (Method method : getClass().getDeclaredMethods()) {
            if (method.getAnnotation(KingSkill.class) != null) {
                return method.getAnnotation(KingSkill.class).value();
            }
        }
        return "";
    }

    public HashSet<String> getSkills() {
        HashSet<String> skills = new HashSet<>();
        for (Method method : getClass().getDeclaredMethods()) {
            if (method.getAnnotation(Skill.class) != null) {
                if (method.getAnnotation(Skill.class).value().equals("化身") ||
                    method.getAnnotation(Skill.class).value().equals("新生")) {
                    if (!isZuoCi) {
                        continue;   // for 左慈
                    }
                }
                skills.add(method.getAnnotation(Skill.class).value());
            } else if (method.getAnnotation(ForcesSkill.class) != null) {
                if (method.getAnnotation(ForcesSkill.class).value().equals("无双")) {
                    if (!hasWuShuang()) {
                        continue;   // for 神吕布
                    }
                }
                skills.add(method.getAnnotation(ForcesSkill.class).value());
            } else if (method.getAnnotation(RestrictedSkill.class) != null) {
                skills.add(method.getAnnotation(RestrictedSkill.class).value());
            } else if (method.getAnnotation(WakeUpSkill.class) != null) {
                skills.add(method.getAnnotation(WakeUpSkill.class).value());
            } else if (method.getAnnotation(KingSkill.class) != null) {
                skills.add(method.getAnnotation(KingSkill.class).value());
            } else if (method.getAnnotation(AfterWakeSkill.class) != null) {
                if (hasWakenUp()) {
                    skills.add(method.getAnnotation(AfterWakeSkill.class).value());
                }
            }
        }
        return skills;
    }

    public boolean isZuoCi() {
        return isZuoCi;
    }

    public void setZuoCi(boolean zuoCi) {
        isZuoCi = zuoCi;
    }

    public void addHuaShen(Person p) {
        huaShen.add(p);
    }

    public ArrayList<Person> getHuaShen() {
        return huaShen;
    }

    public void setHuaShen(ArrayList<Person> huaShen) {
        this.huaShen = huaShen;
    }

    public void selectHuaShen(Person p) {
        selected = p;
    }
}
