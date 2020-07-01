package sanguosha.cardsheap;

import sanguosha.GameLauncher;
import sanguosha.manager.GameManager;
import sanguosha.manager.IO;
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
import java.util.HashSet;

import static sanguosha.people.Identity.KING;
import static sanguosha.people.Identity.MINISTER;
import static sanguosha.people.Identity.REBEL;
import static sanguosha.people.Identity.TRAITOR;

public class PeoplePool {
    private static final ArrayList<Person> people = new ArrayList<>();
    private static final HashSet<Person> allPeople = new HashSet<>();
    private static final ArrayList<Identity> identities = new ArrayList<>();
    private static int optionsPerPerson = 2;
    private static int numPlayers;

    public static void addStandard() {
        if (allPeople.isEmpty()) {
            addFeng();
            addHuo();
            addLin();
            addShan();
            addGod();
            allPeople.addAll(people);
            addStandard();
            allPeople.addAll(people);
            people.clear();
        }
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
        people.add(new ShenCaoCao());
        people.add(new ShenGuanYu());
        people.add(new ShenLvBu());
        people.add(new ShenLvMeng());
        people.add(new ShenSiMaYi());
        people.add(new ShenZhaoYun());
        people.add(new ShenZhouYu());
        people.add(new ShenZhuGeLiang());
    }

    public static void addIdentity(Identity id, int num) {
        for (int i = 0; i < num; i++) {
            identities.add(id);
        }
    }

    public static int defaultMinister(int total) {
        return total == 10 ? 3 : total >= 6 ? 2 : total >= 4 ? 1 : 0;
    }

    public static int defaultTraitor(int total) {
        return total >= 9 ? 2 : total >= 3 ? 1 : 0;
    }

    public static int defaultRebel(int total) {
        return total >= 8 ? 4 : total >= 7 ? 3 : total >= 5 ? 2 : 1;
    }

    public static void init() {
        //people.add(new BlankPerson());
        //people.add(new BlankPerson2());
        //people.add(new AI());
        //addStandard();
        numPlayers = GameManager.getNumPlayers();

        if (GameLauncher.isCommandLine()) {
            if (IO.chooseFromProvided("default people config", "customize")
                    .equals("default people config")) {
                addStandard();
                //addFeng();
                //addHuo();
                //addLin();
                //addShan();
                //addGod();
                switch (GameManager.getNumPlayers()) {
                    case 10: addIdentity(MINISTER, 1);
                    //fallthrough
                    case 9: addIdentity(TRAITOR, 1);
                    //fallthrough
                    case 8: addIdentity(REBEL, 1);
                    //fallthrough
                    case 7: addIdentity(REBEL, 1);
                    //fallthrough
                    case 6: addIdentity(MINISTER, 1);
                    //fallthrough
                    case 5: addIdentity(REBEL, 1);
                    //fallthrough
                    case 4: addIdentity(MINISTER, 1);
                    //fallthrough
                    case 3: addIdentity(TRAITOR, 1);
                    //fallthrough
                    case 2: addIdentity(KING, 1);
                            addIdentity(REBEL, 1);
                            break;
                    default:
                        GameManager.panic("invalid players: " + GameManager.getNumPlayers());
                }
            } else {
                addStandard();
                if (IO.chooseFromProvided("add 风", "pass").equals("add 风")) {
                    addFeng();
                } if (IO.chooseFromProvided("add 火", "pass").equals("add 火")) {
                    addHuo();
                } if (IO.chooseFromProvided("add 林", "pass").equals("add 林")) {
                    addLin();
                } if (IO.chooseFromProvided("add 山", "pass").equals("add 山")) {
                    addShan();
                } if (IO.chooseFromProvided("add 神", "pass").equals("add 神")) {
                    addGod();
                }
                IO.printlnToIO("choose options per person");
                setOptionsPerPerson(IO.chooseNumber(2, people.size() / numPlayers));
                while (true) {
                    IO.printlnToIO("1 KING (required)");
                    addIdentity(KING, 1);
                    IO.printlnToIO("choose number of MINISTER");
                    addIdentity(MINISTER, IO.chooseNumber(0, 4));
                    IO.printlnToIO("choose number of TRAITOR");
                    addIdentity(TRAITOR, IO.chooseNumber(0, 4));
                    IO.printlnToIO("choose number of REBEL");
                    addIdentity(REBEL, IO.chooseNumber(1, 4));
                    if (illegalIdentityNumber()) {
                        IO.printToIO("wrong number of identites! expect: " + numPlayers +
                                " get: " + identities.size());
                        continue;
                    }
                    break;
                }
            }
        }
        Utils.assertTrue(!illegalOptionsPerPerson(), "not enough people to choose");
        Collections.shuffle(people);
        Collections.shuffle(identities);
    }

    public static void setNumPlayers(int numPlayers) {
        PeoplePool.numPlayers = numPlayers;
    }

    public static boolean illegalIdentityNumber() {
        return identities.size() != GameManager.getNumPlayers();
    }

    public static boolean illegalOptionsPerPerson() {
        return people.size() < numPlayers * optionsPerPerson;
    }

    public static void restart() {
        people.clear();
        identities.clear();
    }

    public static void addPersonToList(Class<? extends Person> cls, ArrayList<Person> kings) {
        for (Person p: people) {
            if (p.getClass() == cls) {
                kings.add(p);
                people.remove(p);
                return;
            }
        }
    }

    public static ArrayList<Person> allocPeopleForKing() {
        ArrayList<Person> options = new ArrayList<>();
        if (optionsPerPerson > 3) {
            addPersonToList(LiuBei.class, options);
            addPersonToList(CaoCao.class, options);
            addPersonToList(SunQuan.class, options);
        }
        if (optionsPerPerson > 10) {
            addPersonToList(ZhangJiao.class, options);
            addPersonToList(YuanShao.class, options);
            addPersonToList(DongZhuo.class, options);
            addPersonToList(CaoPi.class, options);
            addPersonToList(LiuChan.class, options);
            addPersonToList(SunCe.class, options);
        }
        Utils.assertTrue(people.size() >= optionsPerPerson, "No people available");
        options.addAll(people.subList(0, optionsPerPerson - options.size()));
        people.removeAll(options);
        return options;
    }

    public static ArrayList<Person> allocPeople() {
        Utils.assertTrue(people.size() >= optionsPerPerson, "No people available");
        ArrayList<Person> ans = new ArrayList<>(people.subList(0, optionsPerPerson));
        people.removeAll(ans);
        return ans;
    }

    public static Person allocOnePerson() {
        Utils.assertTrue(people.size() >= 1, "No people available");
        Person ans = people.get(0);
        people.remove(ans);
        return ans;
    }

    public static Identity allocIdentityForKing() {
        Utils.assertTrue(identities.contains(KING), "KING not in identities");
        identities.remove(KING);
        return KING;
    }

    public static Identity allocIdentity() {
        Utils.assertTrue(identities.size() >= 1, "No identity available");
        Identity ans = identities.get(0);
        identities.remove(ans);
        return ans;
    }

    public static ArrayList<Person> getPeople() {
        return people;
    }

    public static HashSet<Person> getAllPeople() {
        return allPeople;
    }

    public static void setOptionsPerPerson(int optionsPerPerson) {
        PeoplePool.optionsPerPerson = optionsPerPerson;
    }
}
