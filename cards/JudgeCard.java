package cards;

public abstract class JudgeCard extends Strategy {
    private Card thisCard = this;

    public JudgeCard(Color color, int number, int distance) {
        super(color, number, distance);
    }

    public JudgeCard(Color color, int number) {
        super(color, number);
    }

    public void setThisCard(Card thisCard) {
        this.thisCard = thisCard;
    }

    public Card getThisCard() {
        return thisCard;
    }

    @Override
    public abstract String use();

}
