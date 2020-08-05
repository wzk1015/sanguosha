package sanguosha.people;

import sanguosha.GameLauncher;
import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.Equipment;
import sanguosha.manager.GameManager;
import sanguosha.manager.Status;
import sanguosha.manager.Utils;

import java.util.ArrayList;
import java.util.List;

import static sanguosha.cards.EquipType.weapon;

public class AI extends Person {
    private Person primaryEnemy;
    private boolean flag;

    public AI() {
        super(4, Nation.QUN);
    }

    @Override
    public void beginPhase() {
        if (getIdentity() == Identity.REBEL) {
            primaryEnemy = GameManager.getKing();
        } else {
            do {
                primaryEnemy = Utils.choice(GameManager.getPlayers());
            } while (primaryEnemy == this);
        }
    }

    @Override
    public void gotHurt(ArrayList<Card> cards, Person p, int num) {
        primaryEnemy = p;
    }

    private void stupidUsePhase() {
        int index = 1;
        while (true) {
            printlnToIO(this + "'s current hand cards: ");
            printCards(getCards());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                break;
            }
            if (index <= getCards().size()) {
                if (!parseOrder(index + "")) {
                    index++;
                }
            } else {
                break;
            }
        }
    }

    private void use(Card c, Person target) {
        c.setSource(this);
        c.setTarget(target);
        boolean used = useCard(c);
        if (c.isNotTaken() && used) {
            throwCard(c);
        } else if (!c.isNotTaken()) {
            c.setTaken(false);
        }
        flag = true;
    }

    private boolean sleep() {
        try {
            Thread.sleep(100);
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

    private void useAI(Card c) {
        switch (c.toString()) {
            case "杀":
                if (GameManager.calDistance(this, primaryEnemy) <= this.getShaDistance()
                        && !primaryEnemy.hasKongCheng() && (getShaCount() > 0
                        || hasEquipment(weapon, "诸葛连弩"))) {
                    use(c, primaryEnemy);
                }
                break;
            case "顺手牵羊": //fallthrough
            case "兵粮寸断": //fallthrough
                if (GameManager.calDistance(this, primaryEnemy) <= 1) {
                    use(c, primaryEnemy);
                }
                break;
            case "桃":
                if (getHP() < getMaxHP()) {
                    use(c, this);
                }
                break;
            case "铁索连环":
                loseCard(c);
                drawCard();
                println(this + " 重铸 铁索连环");
                break;
            case "过河拆桥": //fallthrough
            case "火攻": //fallthrough
            case "决斗": //fallthrough
            case "乐不思蜀": //fallthrough
                use(c, primaryEnemy);
                break;
            case "酒":
            case "闪电": //fallthrough
            case "无中生有": //fallthrough
            case "南蛮入侵": //fallthrough
            case "万箭齐发": //fallthrough
            case "五谷丰登": //fallthrough
            case "桃园结义":
                use(c, this);
                break;
            default:
        }
    }

    @Override
    public void usePhase() {
        flag = true;
        while (flag) {
            flag = false;
            if (GameLauncher.isCommandLine()) {
                printlnToIO(getPlayerStatus(true, false));
            }
            if (!sleep()) {
                break;
            }
            for (Card c : getCards()) {
                ArrayList<Card> oldCards = new ArrayList<>(getCards());
                if (c instanceof Equipment) {
                    putOnEquipment(c);
                }
                useAI(c);
                if (!getCards().equals(oldCards)) {
                    break;
                }
                if (GameManager.status() == Status.end || isDead()) {
                    return;
                }
            }
        }
    }

    @Override
    public Card requestRedBlack(String color, boolean fromEquipments) {
        sleep();
        ArrayList<Card> options;
        if (fromEquipments) {
            options = getCardsAndEquipments();
        } else {
            options = getCards();
        }
        for (Card c: options) {
            if ((c.isRed() && color.equals("red")) || (c.isBlack() && color.equals("black"))) {
                println(this + " chooses " + c);
                loseCard(c);
                return c;
            }
        }
        return null;
    }

    @Override
    public Card requestColor(Color color, boolean fromEquipments) {
        sleep();
        ArrayList<Card> options;
        if (fromEquipments) {
            options = getCardsAndEquipments();
        } else {
            options = getCards();
        }
        for (Card c: options) {
            if (c.color() == color) {
                println(this + " chooses " + c);
                loseCard(c);
                return c;
            }
        }
        return null;
    }

    @Override
    public Card requestCard(String type) {
        sleep();
        if (type != null && (type.equals("无懈可击") || type.equals("桃"))) {
            println(this + " pass");
            return null;
        }
        for (Card c : getCards()) {
            if (c.toString().equals(type)) {
                println(this + " chooses " + c);
                loseCard(c);
                return c;
            }
        }
        println(this + " pass");
        return null;
    }

    @Override
    public <E> E chooseFromProvided(ArrayList<E> choices) {
        sleep();
        int option = Utils.randint(0, choices.size());
        if (option == choices.size()) {
            println(this + " pass");
            return null;
        }
        println(this + " chooses option " + option);
        return choices.get(option);
    }

    @Override
    public <E> ArrayList<E> chooseManyFromProvided(int num, List<E> choices) {
        sleep();
        println(this + " chooses options 1 - " + num);
        return new ArrayList<>(choices.subList(0, num));
    }

    @Override
    public String name() {
        return "AI";
    }

    @Override
    public String skillsDescription() {
        return "我是AI，我是辣鸡，纯瞎玩";
    }
}
