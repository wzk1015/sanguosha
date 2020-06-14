package cardsheap;

import cards.Card;
import cards.Color;
import cards.basic.HurtType;
import cards.basic.Jiu;
import cards.basic.Sha;
import cards.basic.Shan;
import cards.basic.Tao;
import cards.equipments.horses.ChiTu;
import cards.equipments.horses.DaWan;
import cards.equipments.horses.DiLu;
import cards.equipments.horses.HuaLiu;
import cards.equipments.horses.JueYing;
import cards.equipments.horses.ZhuaHuangFeiDian;
import cards.equipments.horses.ZiXing;
import cards.equipments.shields.BaGuaZhen;
import cards.equipments.shields.BaiYinShiZi;
import cards.equipments.shields.RenWangDun;
import cards.equipments.shields.TengJia;
import cards.equipments.weapons.CiXiongShuangGuJian;
import cards.equipments.weapons.FangTianHuaJi;
import cards.equipments.weapons.GuDingDao;
import cards.equipments.weapons.GuanShiFu;
import cards.equipments.weapons.HanBingJian;
import cards.equipments.weapons.QiLinGong;
import cards.equipments.weapons.QingGangJian;
import cards.equipments.weapons.QingLongYanYueDao;
import cards.equipments.weapons.ZhangBaSheMao;
import cards.equipments.weapons.ZhuGeLianNu;
import cards.equipments.weapons.ZhuQueYuShan;
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
import cards.strategy.judgecards.BingLiangCunDuan;
import cards.strategy.judgecards.LeBuSiShu;
import cards.strategy.judgecards.ShanDian;
import manager.GameManager;
import manager.IO;
import manager.Utils;
import people.Person;

import java.util.ArrayList;
import java.util.Collections;

import static cards.Color.CLUB;
import static cards.Color.DIAMOND;
import static cards.Color.HEART;
import static cards.Color.SPADE;

public class CardsHeap {
    private static ArrayList<Card> drawCards = new ArrayList<>();
    private static final ArrayList<Card> usedCards = new ArrayList<>();
    private static int remainingShuffleTimes = 5;
    private static int numCards;
    private static Card judgeCard = null;

    public static void addCard(Class<? extends Card> cls, Color color, int num) {
        try {
            Card c = cls.getConstructor(Color.class, int.class).newInstance(color, num);
            IO.debug(c.info() + c.toString());
            drawCards.add(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addCard(Class<? extends Card> cls, Color color, int num, HurtType type) {
        try {
            Card c = cls.getConstructor(
                    Color.class, int.class, HurtType.class).newInstance(color, num, type);
            IO.debug(c.info() + c.toString());
            drawCards.add(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addCards(Class<? extends Card> cls, Color color, int... numbers) {
        for (int value: numbers) {
            addCard(cls, color, value);
        }
    }

    public static void addCards(Class<? extends Card> cls,HurtType type,
                                Color color, int... numbers) {
        for (int value: numbers) {
            addCard(cls, color, value, type);
        }
    }

    public static void addBasicCards() {
        addCards(Sha.class, HEART, 10, 10, 11);
        addCards(Sha.class, DIAMOND, 6, 7, 8, 9, 10, 13);
        addCards(Sha.class, SPADE, 7, 8, 8, 9, 9, 10, 10);
        addCards(Sha.class, CLUB, 2, 3, 4, 5, 6, 7, 8, 8, 9, 9, 10, 10, 11, 11);

        addCards(Shan.class, DIAMOND, 2, 2, 3, 4, 5, 6, 6, 7, 8, 8, 9, 10, 10, 11, 11, 11);
        addCards(Shan.class, HEART, 2, 2, 8, 9, 10, 11, 12, 13);

        addCards(Tao.class, HEART,  3, 4, 5, 6, 7, 8, 9, 12);
        addCards(Tao.class, DIAMOND,  2, 2, 3, 12);

        addCards(Jiu.class, CLUB, 3, 9);
        addCards(Jiu.class, SPADE, 3, 9);
        addCard(Jiu.class, DIAMOND, 9);

        addCards(Sha.class, HurtType.thunder, SPADE, 4, 5, 6, 7, 8);
        addCards(Sha.class, HurtType.thunder, CLUB, 5, 6, 7, 8);
        addCards(Sha.class, HurtType.fire, DIAMOND, 4, 5);
        addCards(Sha.class, HurtType.fire, HEART, 4, 7, 10);
    }

    public static void addStrategyCards() {
        addCards(GuoHeChaiQiao.class, SPADE, 3, 4, 12);
        addCards(GuoHeChaiQiao.class, CLUB, 3, 4);
        addCard(GuoHeChaiQiao.class, HEART, 12);

        addCards(ShunShouQianYang.class, DIAMOND, 3, 4);
        addCards(ShunShouQianYang.class, SPADE, 3, 4, 12);

        addCards(WuZhongShengYou.class, HEART, 7, 8, 9, 12);

        addCards(WuXieKeJi.class, SPADE, 11, 13);
        addCards(WuXieKeJi.class, CLUB, 12, 13);
        addCard(WuXieKeJi.class, DIAMOND, 12);
        addCards(WuXieKeJi.class, HEART, 1, 13);

        addCards(NanManRuQin.class, SPADE, 7, 13);
        addCard(NanManRuQin.class, SPADE, 7);

        addCard(WanJianQiFa.class, HEART, 1);

        addCard(JueDou.class, SPADE, 1);
        addCard(JueDou.class, DIAMOND, 1);
        addCard(JueDou.class, CLUB, 1);

        addCard(LeBuSiShu.class, HEART, 6);
        addCard(LeBuSiShu.class, SPADE, 6);
        addCard(LeBuSiShu.class, CLUB, 6);

        addCard(BingLiangCunDuan.class, CLUB, 4);
        addCard(BingLiangCunDuan.class, SPADE, 10);

        addCards(JieDaoShaRen.class, CLUB, 12, 13);
        addCards(WuGuFengDeng.class, HEART, 3, 4);
        addCard(TaoYuanJieYi.class, HEART, 1);

        addCard(ShanDian.class, SPADE, 1);
        addCard(ShanDian.class, HEART, 12);

        addCards(HuoGong.class, HEART, 2, 3, 12);

        addCards(TieSuoLianHuan.class, SPADE, 11, 12);
        addCards(TieSuoLianHuan.class, CLUB, 10, 11, 12, 13);

    }

    public static void addEquipmentCards() {
        addCard(ZhuGeLianNu.class, DIAMOND, 1);
        addCard(ZhuGeLianNu.class, CLUB, 1);

        addCard(BaGuaZhen.class, SPADE, 2);
        addCard(BaGuaZhen.class, CLUB, 2);

        addCard(TengJia.class, CLUB, 2);
        addCard(TengJia.class, SPADE, 2);

        addCard(CiXiongShuangGuJian.class, SPADE, 2);
        addCard(QingGangJian.class, SPADE, 6);
        addCard(GuanShiFu.class, DIAMOND, 5);
        addCard(QingLongYanYueDao.class, SPADE, 5);
        addCard(ZhangBaSheMao.class, SPADE, 12);
        addCard(HanBingJian.class, SPADE, 2);
        addCard(RenWangDun.class, CLUB, 2);
        addCard(GuDingDao.class, SPADE, 1);
        addCard(ZhuQueYuShan.class, DIAMOND, 1);
        addCard(BaiYinShiZi.class, CLUB, 1);
        addCard(FangTianHuaJi.class, DIAMOND, 12);

        addCard(QiLinGong.class, HEART, 5);
        addCard(ZhuaHuangFeiDian.class, HEART, 13);
        addCard(JueYing.class, SPADE, 5);
        addCard(DiLu.class, CLUB, 5);
        addCard(ChiTu.class, HEART, 5);
        addCard(ZiXing.class, DIAMOND, 13);
        addCard(DaWan.class, SPADE, 13);
        addCard(HuaLiu.class, DIAMOND, 13);
    }

    public static void init() {
        addBasicCards();
        addStrategyCards();
        addEquipmentCards();
        Collections.shuffle(drawCards);
        numCards = drawCards.size();
    }

    public static void shuffle() {
        remainingShuffleTimes--;
        if (usedCards.isEmpty() || remainingShuffleTimes == 0) {
            GameManager.callItEven();
        }
        drawCards.addAll(usedCards);
        Collections.shuffle(drawCards);
        usedCards.clear();
    }

    public static Card draw() {
        if (drawCards.isEmpty()) {
            shuffle();
        }
        Card c = drawCards.get(0);
        c.setTaken(false);
        drawCards.remove(0);
        return c;
    }

    public static ArrayList<Card> draw(int num) {
        ArrayList<Card> cs = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            cs.add(draw());
        }
        return cs;
    }

    public static void discard(Card c) {
        usedCards.add(0, c);
        c.setTaken(false);
    }

    public static void discard(ArrayList<Card> cs) {
        usedCards.addAll(0, cs);
    }

    public static Card judge(Person source) {
        judgeCard = draw();
        System.out.print("Judge card: ");
        IO.printCard(judgeCard);
        Card change = GameManager.askChangeJudge(judgeCard);
        if (change != null) {
            judgeCard = change;
        }
        source.receiveJudge();
        discard(judgeCard);
        judgeCard.setOwner(source);
        return judgeCard;
    }

    public static void retrieve(Card c) {
        Utils.assertTrue(usedCards.contains(c), "retrieving card not in usedCards");
        usedCards.remove(c);
    }

    public static Card getJudgeCard() {
        usedCards.remove(judgeCard);
        return judgeCard;
    }

    public static ArrayList<Card> getDrawCards(int num) {
        if (num > drawCards.size()) {
            shuffle();
        }
        return drawCards;
    }

    public static ArrayList<Card> getUsedCards() {
        return usedCards;
    }

    public static void setDrawCards(ArrayList<Card> drawCards) {
        CardsHeap.drawCards = drawCards;
    }

    public static int getNumCards() {
        return numCards;
    }
}
