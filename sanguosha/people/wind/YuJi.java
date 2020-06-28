package sanguosha.people.wind;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.basic.HurtType;
import sanguosha.cards.basic.Jiu;
import sanguosha.cards.basic.Sha;
import sanguosha.cards.basic.Shan;
import sanguosha.cards.basic.Tao;
import sanguosha.cards.strategy.GuoHeChaiQiao;
import sanguosha.cards.strategy.HuoGong;
import sanguosha.cards.strategy.JieDaoShaRen;
import sanguosha.cards.strategy.JueDou;
import sanguosha.cards.strategy.NanManRuQin;
import sanguosha.cards.strategy.ShunShouQianYang;
import sanguosha.cards.strategy.TaoYuanJieYi;
import sanguosha.cards.strategy.TieSuoLianHuan;
import sanguosha.cards.strategy.WanJianQiFa;
import sanguosha.cards.strategy.WuGuFengDeng;
import sanguosha.cards.strategy.WuXieKeJi;
import sanguosha.cards.strategy.WuZhongShengYou;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.GameManager;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import static sanguosha.cards.Color.NOCOLOR;

public class YuJi extends Person {
    private HashMap<String, Class<? extends Card>> cardsMap = new HashMap<>();

    public YuJi() {
        super(3, Nation.QUN);
        putCard(Sha.class);
        putCard(Shan.class);
        putCard(Tao.class);
        putCard(Jiu.class);
        putCard(GuoHeChaiQiao.class);
        putCard(HuoGong.class);
        putCard(JieDaoShaRen.class);
        putCard(JueDou.class);
        putCard(NanManRuQin.class);
        putCard(ShunShouQianYang.class);
        putCard(TaoYuanJieYi.class);
        putCard(TieSuoLianHuan.class);
        putCard(WanJianQiFa.class);
        putCard(WuGuFengDeng.class);
        putCard(WuXieKeJi.class);
        putCard(WuZhongShengYou.class);
    }

    public void putCard(Class<? extends Card> cls) {
        Card c;
        try {
            c = cls.getConstructor(Color.class, int.class).newInstance(NOCOLOR, 0);
            cardsMap.put(c.toString(), cls);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Skill("蛊惑")
    public boolean guHuo(Card c, String type) {
        ArrayList<Person> questioners = new ArrayList<>();
        for (Person p: GameManager.getPlayers()) {
            if (p == this) {
                continue;
            }
            if (p.chooseNoNull("believe", "question").equals("question")) {
                questioners.add(p);
            }
        }
        if (c.toString().equals(type) || (type.equals("杀") && c instanceof Sha)) {
            println("蛊惑 card is real: " + c.info() + c.toString());
            for (Person p: questioners) {
                p.loseHP(1);
            }
            return c.color() == Color.HEART;
        } else {
            println("蛊惑 card is fake, real card: " + c.info() + c.toString());
            for (Person p: questioners) {
                p.drawCard();
            }
            return false;
        }
    }

    @Override
    public Card requestCard(String type) {
        while (launchSkill("蛊惑")) {
            Card c = requestCard(null);
            if (c != null) {
                if (guHuo(c, type)) {
                    return c;
                } else {
                    continue;
                }
            }
            break;
        }
        return super.requestCard(type);
    }

    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("蛊惑")) {
            println(this + " uses 蛊惑");
            String type = input("input a type");
            try {
                Card intend;
                if (cardsMap.containsKey(type)) {
                    intend = cardsMap.get(type).getConstructor(Color.class, int.class)
                            .newInstance(NOCOLOR, 0);

                } else if (type.equals("火杀")) {
                    intend = new Sha(NOCOLOR, 0, HurtType.fire);
                } else if (type.equals("雷杀")) {
                    intend = new Sha(NOCOLOR, 0, HurtType.thunder);
                } else {
                    printlnToIO("wrong type");
                    return true;
                }
                Card c = requestCard(null);
                intend.setThisCard(c);
                if (intend.askTarget(this)) {
                    if (guHuo(c, type)) {
                        useCard(intend);
                    }
                } else {
                    addCard(CardsHeap.retrieve(c));
                }
            } catch (InstantiationException | IllegalAccessException
                    | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }

            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "于吉";
    }
}
