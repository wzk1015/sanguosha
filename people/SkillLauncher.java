package people;

import cards.Card;

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

    default void gotHurt(Person p, int num) {

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

    default Card changeJudge() {
        return null;
    }

    default boolean useSkillInUsePhase(String order) {
        return false;
    }
}
