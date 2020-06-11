package people;

import manager.Utils;
import people.shu.GuanYu;
import people.shu.HuangYueYing;
import people.shu.HuangZhong;
import people.shu.LiuBei;
import people.shu.MaChao;
import people.shu.ZhangFei;
import people.shu.ZhaoYun;
import people.shu.ZhuGeLiang;
import people.wei.CaoCao;
import people.wei.GuoJia;
import people.wei.SiMaYi;
import people.wei.XiaHouDun;
import people.wei.XuChu;
import people.wei.XunYu;
import people.wei.ZhangLiao;
import people.wei.ZhenJi;
import people.wu.SunQuan;

import java.util.ArrayList;
import java.util.Collections;

public class PeoplePool {
    private static final ArrayList<Person> people = new ArrayList<>();
    private static final ArrayList<Identity> identities = new ArrayList<>();
    private static final int optionsPerPerson = 5;
    private static int peopleIndex = 0;
    private static int identityIndex = 0;

    public static void init() {
        people.add(new BlankPerson());
        //people.add(new AI());
        people.add(new BlankPerson2());

        people.add(new GuanYu());
        people.add(new HuangYueYing());
        people.add(new LiuBei());
        people.add(new MaChao());
        people.add(new ZhangFei());
        people.add(new ZhaoYun());
        people.add(new ZhuGeLiang());

        people.add(new CaoCao());
        people.add(new GuoJia());
        people.add(new SiMaYi());
        people.add(new XiaHouDun());
        people.add(new XuChu());
        people.add(new XunYu());
        people.add(new ZhangLiao());
        people.add(new ZhenJi());

        people.add(new SunQuan());


        people.add(new HuangZhong());

        people.add(new XunYu());

        Collections.shuffle(people);

        identities.add(Identity.KING);
        //identities.add(Identity.MINISTER);
        //identities.add(Identity.MINISTER);
        identities.add(Identity.TRAITOR);
        //identities.add(Identity.REBEL);
        //identities.add(Identity.REBEL);
        //identities.add(Identity.REBEL);
        //identities.add(Identity.REBEL);
        Collections.shuffle(identities);
        Collections.shuffle(people);
    }

    public static ArrayList<Person> allocPeople() {
        peopleIndex += optionsPerPerson;
        Utils.assertTrue(peopleIndex <= people.size(), "No people available");
        return new ArrayList<>(people.subList(peopleIndex - optionsPerPerson, peopleIndex));
    }

    public static Identity allocIdentity() {
        return identities.get(identityIndex++);
    }
}
