package people.qun;

import cards.Card;
import people.Nation;
import people.Person;
import skills.Skill;

public class DiaoChan extends Person {
    public DiaoChan() {
        super(3, Nation.QUN);
    }

    @Skill("闭月")
    @Override
    public void endPhase() {
        if (launchSkill("闭月")) {
            drawCard();
        }
    }

    @Skill("离间")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("离间")) {
            Person p1;
            Person p2;
            while (true) {
                p1 = selectPlayer();
                p2 = selectPlayer();
                if (p1 == null || p2 == null) {
                    return true;
                }
                if (p1.getSex().equals("female") || p2.getSex().equals("female")) {
                    println("you should choose male player");
                    continue;
                }
                break;
            }
            //决斗
            p1.jueDouBegin();
            p2.jueDouBegin();
            while (true) {
                if ((!p2.hasWuShuang() && p1.requestSha() == null) ||
                    p2.hasWuShuang() && (p1.requestSha() == null || 
                            p1.requestSha() == null)) {
                    p1.hurt((Card) null, p2,1);
                    break;
                }
                if ((!p1.hasWuShuang() && p2.requestSha() == null) ||
                    p1.hasWuShuang() && (p2.requestSha() == null || 
                            p2.requestSha() == null)) {
                    p2.hurt((Card) null, p1,1);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "貂蝉";
    }
}
