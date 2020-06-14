package people.wind;

import cards.Card;
import cards.Color;
import cards.basic.Sha;
import manager.GameManager;
import people.Nation;
import people.Person;
import skills.Skill;

import java.util.ArrayList;

public class YuJi extends Person {
    public YuJi() {
        super(3, Nation.QUN);
    }

    @Skill("蛊惑")
    @Override
    public Card requestCard(String type) {
        while (launchSkill("蛊惑")) {
            Card c = requestCard(null);
            ArrayList<Person> questioners = new ArrayList<>();
            if (c != null) {
                for (Person p: GameManager.getPlayers()) {
                    if (p == this) {
                        continue;
                    }
                    if (p.chooseFromProvided("believe", "question").equals("question")) {
                        questioners.add(p);
                    }
                }
                if (c.toString().equals(type) || (type.equals("杀") && c instanceof Sha)) {
                    println("蛊惑 card is real");
                    for (Person p: questioners) {
                        p.loseHP(1);
                    }
                    if (c.color() == Color.HEART) {
                        return c;
                    }
                } else {
                    println("蛊惑 card is fake");
                    for (Person p: questioners) {
                        p.drawCard();
                    }
                }
                continue;
            }
            break;
        }
        return super.requestCard(type);
    }

    @Override
    public String toString() {
        return "于吉";
    }
}
