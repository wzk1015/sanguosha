package sanguosha.people.qun;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.basic.Sha;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.GameManager;
import sanguosha.people.Identity;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;
import sanguosha.skills.SpecialSkill;

import java.util.ArrayList;
import java.util.HashSet;

public class YuanShu extends Person {
    private Person king;

    public YuanShu() {
        super(4, Nation.QUN);
    }

    @Override
    public void initialize() {
        king = getIdentity() == Identity.KING ? null : GameManager.getKing();
    }

    public boolean weiDi(String s) {
        return king != null && (king.getKingSkill().equals(s) || s.equals("激将") && hasWakenUp());
    }

    @Skill("庸肆")
    @Override
    public void drawPhase() {
        println(this + " uses 庸肆");
        int numNations = 0;
        Nation[] nations  = {Nation.WEI, Nation.SHU, Nation.WU, Nation.QUN};
        for (Nation nation: nations) {
            if (!GameManager.peoplefromNation(nation).isEmpty()) {
                numNations++;
            }
        }
        drawCards(2 + numNations);
    }

    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("激将") && weiDi("激将")) {
            Sha sha = jiJiang();
            useCard(sha);
            return true;
        }
        return false;
    }

    @SpecialSkill("血裔")
    @Override
    public void throwPhase() {
        int numNations = 0;
        Nation[] nations  = {Nation.WEI, Nation.SHU, Nation.WU, Nation.QUN};
        for (Nation nation: nations) {
            if (!GameManager.peoplefromNation(nation).isEmpty()) {
                numNations++;
            }
        }
        int num = numNations;
        if (weiDi("血裔")) {
            num -= 2 * GameManager.peoplefromNation(Nation.QUN).size();;
        }
        num = Math.min(num, getCards().size());
        if (num > 0) {
            printlnToIO(String.format("You need to throw %d cards", num));
            ArrayList<Card> cs = chooseCards(num, getCards());
            loseCard(cs);
            for (Person p: GameManager.getPlayers()) {
                p.otherPersonThrowPhase(this, cs);
            }
        }
    }

    @Skill("伪帝")
    @Override
    public HashSet<String> getSkills() {
        HashSet<String> ans = super.getSkills();
        if (king != null) {
            ans.add(king.getKingSkill());
        }
        if (weiDi("激将")) {
            ans.add("激将");
        }
        return ans;
    }

    @SpecialSkill("激将")
    public Sha jiJiang() {
        ArrayList<Person> shuPeople = GameManager.peoplefromNation(Nation.SHU);
        shuPeople.remove(this);
        if (shuPeople.isEmpty()) {
            println("no 蜀 people available");
            return null;
        }
        for (Person p : shuPeople) {
            Sha sha = p.requestSha();
            if (sha != null) {
                println(p + " answers 激将 from " + this);
                return sha;
            }
        }
        return null;
    }

    @Override
    public boolean skillSha() {
        if (weiDi("激将") && launchSkill("激将")) {
            return jiJiang() != null;
        }
        return false;
    }

    @SpecialSkill("护驾")
    @Override
    public boolean skillShan() {
        if (weiDi("护驾") && launchSkill("护驾")) {
            ArrayList<Person> weiPeople = GameManager.peoplefromNation(Nation.WEI);
            weiPeople.remove(this);
            if (weiPeople.isEmpty()) {
                println("no 魏 people available");
                return false;
            }
            for (Person p : weiPeople) {
                if (p.requestShan()) {
                    println(p + " answers 护驾 from " + this);
                    return true;
                }
            }
        }
        return false;
    }

    @SpecialSkill("救援")
    @Override
    public void gotSavedBy(Person p) {
        if (weiDi("救援") && p.getNation() == Nation.WU) {
            println(this + " uses 救援");
            recover(1);
        }
    }

    @SpecialSkill("黄天")
    public void huangTian(Person p) {
        if (weiDi("黄天") && p.getNation() == Nation.QUN && p.launchSkill("黄天")) {
            String type = p.chooseFromProvided("闪", "闪电");
            if (type != null) {
                Card c = p.requestCard(type);
                if (c != null) {
                    CardsHeap.retrieve(c);
                    addCard(c);
                }
            }
        }
    }

    @SpecialSkill("制霸")
    public void zhiBa(Person p) {
        if (weiDi("制霸") && p.getNation() == Nation.WU &&
                !p.getCards().isEmpty() && !getCards().isEmpty() && p.launchSkill("制霸")) {
            GameManager.pinDian(p, this);
        }
    }

    @Override
    public void otherPersonUsePhase(Person p) {
        huangTian(p);
        zhiBa(p);
    }

    @SpecialSkill("颂威")
    @Override
    public void otherPersonGetJudge(Person p) {
        if (weiDi("颂威") && CardsHeap.getJudgeCard().isBlack()
                && p.getNation() == Nation.WEI && p.launchSkill("颂威")) {
            drawCard();
        }
    }

    @SpecialSkill("暴虐")
    @Override
    public void otherPersonMakeHurt(Person p) {
        if (weiDi("暴虐") && getHP() < getMaxHP() && p.getNation() == Nation.QUN
                && p.launchSkill("暴虐")) {
            if (CardsHeap.judge(p).color() == Color.SPADE) {
                recover(1);
            }
        }
    }

    @SpecialSkill("若愚")
    @Override
    public void beginPhase() {
        if (weiDi("若愚") && !hasWakenUp()) {
            boolean isLowest = true;
            for (Person p: GameManager.getPlayers()) {
                if (p.getHP() < getHP()) {
                    isLowest = false;
                    break;
                }
            }
            if (isLowest) {
                println(this + " uses 若愚");
                setMaxHP(getMaxHP() + 1);
                recover(1);
                wakeUp();
            }
        }
    }

    @Override
    public String name() {
        return "袁术";
    }

    @Override
    public String skillsDescription() {
        return "庸肆：锁定技，摸牌阶段，你多摸X张牌；锁定技，弃牌阶段开始时，你弃置X张牌（X为势力数）。\n" +
                "伪帝：锁定技，你视为拥有主公的主公技。";
    }
}
