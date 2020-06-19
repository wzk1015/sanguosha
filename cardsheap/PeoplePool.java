package cardsheap;

import manager.Utils;
import people.Identity;
import people.Person;
import people.fire.DianWei;
import people.fire.PangDe;
import people.fire.PangTong;
import people.fire.TaiShiCi;
import people.fire.WoLong;
import people.fire.YanLiangWenChou;
import people.fire.YuanShao;
import people.forest.CaoPi;
import people.forest.DongZhuo;
import people.forest.JiaXu;
import people.forest.LuSu;
import people.forest.MengHuo;
import people.forest.SunJian;
import people.forest.XuHuang;
import people.forest.ZhuRong;
import people.god.ShenCaoCao;
import people.god.ShenGuanYu;
import people.god.ShenLvBu;
import people.god.ShenLvMeng;
import people.god.ShenSiMaYi;
import people.god.ShenZhaoYun;
import people.god.ShenZhouYu;
import people.god.ShenZhuGeLiang;
import people.mountain.CaiWenJi;
import people.mountain.DengAi;
import people.mountain.JiangWei;
import people.mountain.LiuChan;
import people.mountain.SunCe;
import people.mountain.ZhangHe;
import people.mountain.ZhangZhaoZhangHong;
import people.mountain.ZuoCi;
import people.qun.DiaoChan;
import people.qun.HuaTuo;
import people.qun.LvBu;
import people.qun.YuanShu;
import people.shu.GuanYu;
import people.shu.HuangYueYing;
import people.wind.CaoRen;
import people.wind.HuangZhong;
import people.shu.LiuBei;
import people.shu.MaChao;
import people.shu.ZhangFei;
import people.shu.ZhaoYun;
import people.shu.ZhuGeLiang;
import people.wei.CaoCao;
import people.wei.GuoJia;
import people.wei.SiMaYi;
import people.wei.XiaHouDun;
import people.fire.XunYu;
import people.wei.ZhangLiao;
import people.wei.ZhenJi;
import people.wind.WeiYan;
import people.wind.XiaHouYuan;
import people.wind.XiaoQiao;
import people.wind.YuJi;
import people.wind.ZhangJiao;
import people.wind.ZhouTai;
import people.wu.DaQiao;
import people.wu.GanNing;
import people.wu.HuangGai;
import people.wu.LuXun;
import people.wu.LvMeng;
import people.wu.SunQuan;
import people.wu.SunShangXiang;
import people.wu.ZhouYu;

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
        //people.add(new BlankPerson());
        //people.add(new BlankPerson2());
        //people.add(new AI());
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
