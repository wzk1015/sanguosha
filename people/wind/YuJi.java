package people.wind;

import cards.Card;
import cards.Color;
import cards.basic.HurtType;
import cards.basic.Jiu;
import cards.basic.Sha;
import cards.basic.Shan;
import cards.basic.Tao;
import cards.strategy.GuoHeChaiQiao;
import cards.strategy.HuoGong;
import cards.strategy.JieDaoShaRen;
import cards.strategy.JueDou;
import cards.strategy.NanManRuQin;
import cards.strategy.ShunShouQianYang;
import cards.strategy.TaoYuanJieYi;
import cards.strategy.TieSuoLianHuan;
import cards.strategy.WanJianQiFa;
import cards.strategy.WuGuFengDeng;
import cards.strategy.WuXieKeJi;
import cards.strategy.WuZhongShengYou;
import cardsheap.CardsHeap;
import manager.GameManager;
import people.Nation;
import people.Person;
import skills.Skill;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import static cards.Color.NOCOLOR;

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
            if (p.chooseFromProvided("believe", "question").equals("question")) {
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
                    println("wrong type");
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
