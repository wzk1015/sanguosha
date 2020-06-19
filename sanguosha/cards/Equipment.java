package sanguosha.cards;

public abstract class Equipment extends Card {
    private EquipType type;

    public Equipment(Color color, int number, EquipType type) {
        super(color, number);
        this.type = type;
    }

    public EquipType getEquipType() {
        return type;
    }

}
