package sanguosha.people.shu;

import sanguosha.cards.Card;
import sanguosha.cards.basic.Sha;

import sanguosha.manager.Utils;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

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
    public String name() {
        return "赵云";
    }

    @Override
    public String skillsDescription() {
        return "龙胆：你可以将一张【杀】当【闪】、【闪】当【杀】使用或打出。";
    }
}
