package people;

import cards.Card;

import java.util.ArrayList;

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

    default void gotHurt(ArrayList<Card> cards, Person p, int num) {

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

    default boolean isNaked() {
        return false;
    }

    default void shaBegin() {

    }

    default void beforeHurt() {

    }

    default void receiveJudge() {

    }

    default void gotSavedBy(Person p) {

    }

    default boolean shaCanBeShan(Person p) {
        return true;
    }

    default Card changeJudge() {
        return null;
    }

    default boolean useSkillInUsePhase(String order) {
        return false;
    }

    default boolean hasMaShu() {
        return false;
    }

    default boolean hasQiCai() {
        return false;
    }

    default boolean hasKongCheng() {
        return false;
    }
}
