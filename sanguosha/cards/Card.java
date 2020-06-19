package sanguosha.cards;

import sanguosha.cards.basic.Sha;
import sanguosha.cards.strategy.GuoHeChaiQiao;
import sanguosha.cards.strategy.HuoGong;
import sanguosha.cards.strategy.JieDaoShaRen;
import sanguosha.cards.strategy.ShunShouQianYang;
import sanguosha.cards.strategy.TieSuoLianHuan;
import sanguosha.cards.strategy.judgecards.LeBuSiShu;
import sanguosha.manager.GameManager;
import sanguosha.manager.IO;
import sanguosha.manager.Utils;
import sanguosha.people.Person;

import java.io.Serializable;
import java.util.ArrayList;

import static sanguosha.cards.EquipType.weapon;

public abstract class Card implements Serializable {
    private final Color color;
    private final int number;
    private Person target;
    private Person source;
    private boolean isTaken = false;
    private Person owner = null;
    private ArrayList<Card> thisCard = new ArrayList<>();

    public void setThisCard(ArrayList<Card> thisCard) {
        this.thisCard = thisCard;
    }

    public void setThisCard(Card thisCard) {
        ArrayList<Card> cs = new ArrayList<>();
        cs.add(thisCard);
        this.thisCard = cs;
    }

    public ArrayList<Card> getThisCard() {
        return thisCard;
    }

    public Card(Color color, int number) {
        this.color = color;
        this.number = number;
        this.thisCard.add(this);
    }

    public Color color() {
        if (owner != null && owner.hasHongYan() && color == Color.SPADE) {
            return Color.HEART;
        }
        return color;
    }

    public int number() {
        return number;
    }

    public boolean isBlack() {
        return color() == Color.SPADE || color() == Color.CLUB;
    }

    public boolean isRed() {
        return color() == Color.HEART || color() == Color.DIAMOND;
    }

    public void setSource(Person source) {
        this.source = source;
    }

    public Person getSource() {
        return source;
    }

    public void setTarget(Person target) {
        this.target = target;
    }

    public Person getTarget() {
        return target;
    }

    public abstract Object use();

    public boolean needChooseTarget() {
        return false;
    }

    public abstract String toString();

    public String info() {
        String num;
        if (number == 0) {
            num = "-";
        } else if (number == 1) {
            num = "A";
        } else if (number <= 10 && number >= 2) {
            num = number + "";
        } else if (number == 11) {
            num = "J";
        } else if (number == 12) {
            num = "Q";
        } else {
            Utils.assertTrue(number == 13, "wrong number: " + number);
            num = "K";
        }
        String col;
        if (color() == Color.DIAMOND) {
            col = "方片";
        } else if (color() == Color.HEART) {
            col = "红桃";
        } else if (color() == Color.SPADE) {
            col = "黑桃";
        } else if (color() == Color.CLUB) {
            col = "梅花";
        } else {
            col = "无色";
        }
        return col + num + " ";
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public boolean isNotTaken() {
        return !isTaken;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Person getOwner() {
        return owner;
    }
    
    public String askMultiTargets() {
        Person p1 = source.selectPlayer(GameManager.getPlayers(), true);
        Person p2 = source.selectPlayer(GameManager.getPlayers(), true);
        if (p1 == null || p2 == null) {
            return "false";
        }
        if (p1 == p2) {
            IO.println("can't select two same sanguosha.people");
            return "continue";
        }
        if (this instanceof JieDaoShaRen && (p1 == source || p2 == source)) {
            IO.println("can't select yourself");
            return "continue";
        }
        if (!p1.hasEquipment(weapon, null)) {
            IO.println("target has no weapon");
            return "continue";
        }
        setTarget(p1);
        if (this instanceof TieSuoLianHuan) {
            ((TieSuoLianHuan) this).setTarget2(p2);
        } else if (this instanceof JieDaoShaRen) {
            ((JieDaoShaRen) this).setTarget2(p2);
        }
        return "true";
    }

    public boolean askTarget(Person user) {
        source = user;

        if (!this.needChooseTarget()) {
            setTarget(user);
            return true;
        }

        while (true) {
            if (this instanceof TieSuoLianHuan || this instanceof JieDaoShaRen) {
                String ret = askMultiTargets();
                if (ret.equals("true")) {
                    return true;
                } else if (ret.equals("false")) {
                    return false;
                } else {
                    continue;
                }
            }

            Person p;
            if (this instanceof HuoGong) {
                p = user.selectPlayer(GameManager.getPlayers(), true);
            } else {
                p = user.selectPlayer(GameManager.getPlayers());
            }

            if (p == null) {
                return false;
            }
            if (this instanceof Strategy &&
                    GameManager.calDistance(user, p) > ((Strategy) this).getDistance()) {
                IO.println("distance unreachable");
                continue;
            }
            if (this instanceof Strategy && this.isBlack() && p.hasWeiMu()) {
                IO.println("can't use that because of 帷幕");
                continue;
            }
            if (this instanceof Sha) {
                if (GameManager.calDistance(user, p) > user.getShaDistance()) {
                    IO.println("distance unreachable");
                    continue;
                } else if (p.hasKongCheng() && p.getCards().isEmpty()) {
                    IO.println("can't sha because of 空城");
                    continue;
                }
            }
            if ((this instanceof ShunShouQianYang || this instanceof LeBuSiShu) &&
                    p.hasQianXun()) {
                IO.println("can't use that because of 谦逊");
                continue;
            }
            if ((this instanceof GuoHeChaiQiao || this instanceof ShunShouQianYang) &&
                    p.getCardsAndEquipments().isEmpty()
                    && p.getJudgeCards().isEmpty()) {
                IO.println("you can't chooose a person with no sanguosha.cards");
                continue;
            }
            target = p;
            return true;
        }
    }

    public String help() {
        return "not implemented";
    }
}
