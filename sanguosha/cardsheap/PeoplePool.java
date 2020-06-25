package sanguosha.cardsheap;

import sanguosha.manager.Utils;
import sanguosha.people.Identity;
import sanguosha.people.Person;
import sanguosha.people.fire.DianWei;
import sanguosha.people.fire.PangDe;
import sanguosha.people.fire.PangTong;
import sanguosha.people.fire.TaiShiCi;
import sanguosha.people.fire.WoLong;
import sanguosha.people.fire.YanLiangWenChou;
import sanguosha.people.fire.YuanShao;
import sanguosha.people.forest.CaoPi;
import sanguosha.people.forest.DongZhuo;
import sanguosha.people.forest.JiaXu;
import sanguosha.people.forest.LuSu;
import sanguosha.people.forest.MengHuo;
import sanguosha.people.forest.SunJian;
import sanguosha.people.forest.XuHuang;
import sanguosha.people.forest.ZhuRong;
import sanguosha.people.god.ShenCaoCao;
import sanguosha.people.god.ShenGuanYu;
import sanguosha.people.god.ShenLvBu;
import sanguosha.people.god.ShenLvMeng;
import sanguosha.people.god.ShenSiMaYi;
import sanguosha.people.god.ShenZhaoYun;
import sanguosha.people.god.ShenZhouYu;
import sanguosha.people.god.ShenZhuGeLiang;
import sanguosha.people.mountain.CaiWenJi;
import sanguosha.people.mountain.DengAi;
import sanguosha.people.mountain.JiangWei;
import sanguosha.people.mountain.LiuChan;
import sanguosha.people.mountain.SunCe;
import sanguosha.people.mountain.ZhangHe;
import sanguosha.people.mountain.ZhangZhaoZhangHong;
import sanguosha.people.mountain.ZuoCi;
import sanguosha.people.qun.DiaoChan;
import sanguosha.people.qun.HuaTuo;
import sanguosha.people.qun.LvBu;
import sanguosha.people.qun.YuanShu;
import sanguosha.people.shu.GuanYu;
import sanguosha.people.shu.HuangYueYing;
import sanguosha.people.wind.CaoRen;
import sanguosha.people.wind.HuangZhong;
import sanguosha.people.shu.LiuBei;
import sanguosha.people.shu.MaChao;
import sanguosha.people.shu.ZhangFei;
import sanguosha.people.shu.ZhaoYun;
import sanguosha.people.shu.ZhuGeLiang;
import sanguosha.people.wei.CaoCao;
import sanguosha.people.wei.GuoJia;
import sanguosha.people.wei.SiMaYi;
import sanguosha.people.wei.XiaHouDun;
import sanguosha.people.fire.XunYu;
import sanguosha.people.wei.ZhangLiao;
import sanguosha.people.wei.ZhenJi;
import sanguosha.people.wind.WeiYan;
import sanguosha.people.wind.XiaHouYuan;
import sanguosha.people.wind.XiaoQiao;
import sanguosha.people.wind.YuJi;
import sanguosha.people.wind.ZhangJiao;
import sanguosha.people.wind.ZhouTai;
import sanguosha.people.wu.DaQiao;
import sanguosha.people.wu.GanNing;
import sanguosha.people.wu.HuangGai;
import sanguosha.people.wu.LuXun;
import sanguosha.people.wu.LvMeng;
import sanguosha.people.wu.SunQuan;
import sanguosha.people.wu.SunShangXiang;
import sanguosha.people.wu.ZhouYu;

import java.util.ArrayList;
import java.util.Collections;

public class PeoplePool {
    private static final ArrayList<Person> people = new ArrayList<>();
    private static final ArrayList<Identity> identities = new ArrayList<>();
    private static final int optionsPerPerson = 2;
    private static int peopleIndex = 0;
    private static int identityIndex = 0;

    public static void addStandard() {
        //蜀国
        people.add(new GuanYu());
        people.add(new HuangYueYing());
        people.add(new LiuBei());
        people.add(new MaChao());
        people.add(new ZhangFei());
        people.add(new ZhaoYun());
        people.add(new ZhuGeLiang());

        //魏国
        people.add(new CaoCao());
        people.add(new GuoJia());
        people.add(new SiMaYi());
        people.add(new XiaHouDun());
        people.add(new XunYu());
        people.add(new ZhangLiao());
        people.add(new ZhenJi());

        //吴国
        people.add(new DaQiao());
        people.add(new GanNing());
        people.add(new HuangGai());
        people.add(new LuXun());
        people.add(new LvMeng());
        people.add(new SunQuan());
        people.add(new SunShangXiang());
        people.add(new ZhouYu());

        //群雄
        people.add(new HuaTuo());
        people.add(new LvBu());
        people.add(new DiaoChan());
        people.add(new YuanShu());
    }

    public static void addFeng() {
        //风包
        people.add(new CaoRen());
        people.add(new HuangZhong());
        people.add(new WeiYan());
        people.add(new XiaHouYuan());
        people.add(new XiaoQiao());
        people.add(new YuJi());
        people.add(new ZhangJiao());
        people.add(new ZhouTai());
    }

    public static void addHuo() {
        //火包
        people.add(new DianWei());
        people.add(new PangDe());
        people.add(new PangTong());
        people.add(new TaiShiCi());
        people.add(new WoLong());
        people.add(new XunYu());
        people.add(new YanLiangWenChou());
        people.add(new YuanShao());
    }

    public static void addLin() {
        //林包
        people.add(new CaoPi());
        people.add(new DongZhuo());
        people.add(new JiaXu());
        people.add(new LuSu());
        people.add(new MengHuo());
        people.add(new SunJian());
        people.add(new XuHuang());
        people.add(new ZhuRong());
    }

    public static void addShan() {
        //山包
        people.add(new CaiWenJi());
        people.add(new DengAi());
        people.add(new JiangWei());
        people.add(new LiuChan());
        people.add(new SunCe());
        people.add(new ZhangHe());
        people.add(new ZhangZhaoZhangHong());
        people.add(new ZuoCi());
    }

    public static void addGod() {
        //神将
        people.add(new ShenCaoCao());
        people.add(new ShenGuanYu());
        people.add(new ShenLvBu());
        people.add(new ShenLvMeng());
        people.add(new ShenSiMaYi());
        people.add(new ShenZhaoYun());
        people.add(new ShenZhouYu());
        people.add(new ShenZhuGeLiang());
    }

    public static void init() {
        //sanguosha.people.add(new BlankPerson());
        //sanguosha.people.add(new BlankPerson2());
        //sanguosha.people.add(new AI());
        //addStandard();
        //addFeng();
        //addHuo();
        //addLin();
        //addShan();
        addGod();

        Collections.shuffle(people);

        identities.add(Identity.KING);
        identities.add(Identity.TRAITOR);
        identities.add(Identity.REBEL);
        Collections.shuffle(identities);
    }

    public static ArrayList<Person> allocPeople() {
        peopleIndex += optionsPerPerson;
        Utils.assertTrue(peopleIndex <= people.size(), "No people available");
        return new ArrayList<>(people.subList(peopleIndex - optionsPerPerson, peopleIndex));
    }

    public static Person allocOnePerson() {
        peopleIndex += 1;
        Utils.assertTrue(peopleIndex <= people.size(), "No people available");
        return people.get(peopleIndex - 1);
    }

    public static Identity allocIdentity() {
        Utils.assertTrue(identityIndex < identities.size(), "No identity available");
        return identities.get(identityIndex++);
    }

    public static ArrayList<Person> getPeople() {
        return people;
    }
}
