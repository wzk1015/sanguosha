package sanguosha.cards;

public abstract class JudgeCard extends Strategy {

    public JudgeCard(Color color, int number, int distance) {
        super(color, number, distance);
    }

    public JudgeCard(Color color, int number) {
        super(color, number);
    }

    @Override
    public abstract String use();

}
