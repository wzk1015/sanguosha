package sanguosha.cards;

import sanguosha.manager.GameManager;
import sanguosha.manager.IO;
import sanguosha.people.Person;

public abstract class Strategy extends Card {
    private int distance;

    public Strategy(Color color, int number, int distance) {
        super(color, number);
        this.distance = distance;
    }

    public Strategy(Color color, int number) {
        super(color, number);
        this.distance = 100;
    }

    public static boolean noWuXie() {
        boolean hasWuXie = false;
        for (Person p : GameManager.getPlayers()) {
            if (p.getSkills().contains("看破") || p.getSkills().contains("龙魂")) {
                hasWuXie = true;
                break;
            }
            for (Card c : p.getCards()) {
                if (c.toString().equals("无懈可击")) {
                    hasWuXie = true;
                    break;
                }
            }
        }
        return !hasWuXie;
    }

    public boolean gotWuXie(Person target) {
        if (noWuXie()) {
            return false;
        }
        if (target != null) {
            IO.println("requesting 无懈 for " + this + " towards " + target);
        }
        boolean ans = false;
        while (true) {
            boolean mark = false;
            for (Person p : GameManager.getPlayers()) {
                if (p.requestWuXie()) {
                    mark = true;
                    ans = !ans;
                    if (noWuXie()) {
                        return ans;
                    }
                    break;
                }
            }
            if (mark) {
                IO.println("requesting 无懈 for 无懈可击");
                continue;
            }
            return ans;
        }
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public void setSource(Person source) {
        super.setSource(source);
        if (source.hasQiCai()) {
            distance = 100;
        }
    }

    public boolean violateAdditionalRequirement(Person user, Person p) {
        return false;
    }

    @Override
    public boolean askTarget(Person user) {
        setSource(user);
        if (!needChooseTarget()) {
            setTarget(user);
            return true;
        }

        while (true) {
            Person p = super.selectTarget(user);
            if (p == null) {
                return false;
            }
            if (GameManager.calDistance(user, p) > getDistance()) {
                user.printlnToIO("distance unreachable");
                continue;
            }
            if (this.isBlack() && p.hasWeiMu()) {
                user.printlnToIO("can't use that because of 帷幕");
                continue;
            }
            if (violateAdditionalRequirement(user, p)) {
                continue;
            }
            return true;
        }
    }

    public abstract String details();

    @Override
    public String help() {
        return details() + "\n\n锦囊牌代表了可以使用的各种“锦囊妙计”，每张锦囊上会标有【锦囊】字样。" +
                "锦囊分为两类，延时类锦囊和非延时类锦囊" +
                "使用延时类锦囊牌只需将它置入目标角色的判定区即可，不会立即进行使用结算，而是要到目标角色下回合的判定阶段进行。" +
                "判定区里有延时类锦囊牌的角色不能再次被选择为同名的延时类锦囊牌的目标。" +
                "除此之外的锦囊为“非延时类锦囊”。";
    }
}
