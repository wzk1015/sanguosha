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

    @Override
    public String help() {
        return "装备牌需放置于装备区里，它们在使用后就会一直放在该区域（而不是像基本牌或锦囊牌一样结算后进入弃牌堆。）" +
                "装备牌的效果为持续性效果，即一旦使用后（装备着）则会一直有效。" +
                "若想变更装备，必须将原来同一个装备栏的装备移入弃牌堆，然后使用新的装备来替代。";
    }
}
