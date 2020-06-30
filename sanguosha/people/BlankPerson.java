package sanguosha.people;

public class BlankPerson extends Person {
    public BlankPerson() {
        super(4,  "male", Nation.QUN);
    }

    public BlankPerson(int maxHP) {
        super(maxHP,  "male", Nation.QUN);
    }

    @Override
    public String name() {
        return "白板";
    }

    @Override
    public String skillsDescription() {
        return "我是快乐的小白板";
    }
}
