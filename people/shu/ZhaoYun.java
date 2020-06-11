package people.shu;

import cards.Card;
import cards.basic.Sha;

import manager.Utils;
import people.Nation;
import people.Person;
import skills.Skill;

public class ZhaoYun extends Person {
    public ZhaoYun() {
        super(4, Nation.SHU);
    }

    @Skill("龙胆")
    public Card longDan(String type) {
        Utils.assertTrue(type.equals("杀") || type.equals("闪"), "illegal 龙胆 type:" + type);
        if (type.equals("闪")) {
            Card c = requestCard("杀");
            if (c != null) {
                println(this + " uses 龙胆");
            }
            return c;
        }
        else {
            return requestCard("闪");
        }
    }

    @Override
    public boolean skillShan() {
        if (launchSkill("龙胆")) {
            return longDan("闪") != null;
        }
        return false;
    }

    @Override
    public boolean skillSha() {
        if (launchSkill("龙胆")) {
            return longDan("杀") != null;
        }
        return false;
    }

    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("龙胆")) {
            Card c = longDan("杀");
            if (c != null) {
                Sha sha = new Sha(c.color(), c.number());
                sha.setThisCard(c);
                useCard(sha);
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "赵云";
    }
}
