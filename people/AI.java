package people;

public class AI extends Person {
    public AI() {
        super(4, Nation.QUN);
    }

    @Override
    public String toString() {
        return "AI";
    }

    public void usePhase() {
        int index = 1;
        while (true) {
            println(this + "'s current hand cards: ");
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
            }
            else {
                break;
            }
        }
    }
}
