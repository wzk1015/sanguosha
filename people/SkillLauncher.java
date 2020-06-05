package people;

public interface SkillLauncher {
    default void shaGotShan() {

    }

    default void shaSuccess() {

    }

    default void hurtBySha() {

    }

    default void lostCard() {

    }

    default void lostEquipment() {

    }

    default void useStrategy() {

    }

    default void gotHurt(int num) {

    }

    default boolean skillSha() {
        return false;
    }

    default boolean skillShan() {
        return false;
    }

    default boolean skillWuxie() {
        return false;
    }

    default void shaBegin() {

    }

    default void beforeHurt() {

    }

    default boolean useSkillInUsePhase(String order) {
        return false;
    }
}
