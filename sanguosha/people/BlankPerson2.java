package sanguosha.people;

public class BlankPerson2 extends Person {
    public BlankPerson2() {
        super(4, "male", Nation.QUN);
    }

    @Override
    public String name() {
        return "黑板";
    }

    @Override
    public String skillsDescription() {
        return "我是快乐的小黑板";
    }
}
