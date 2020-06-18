package people;

import cards.Card;
import cards.basic.Sha;

import java.util.ArrayList;

public interface SkillLauncher {
    default void shaGotShan(Person p) {

    }

    default void shaSuccess(Person p) {

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

    default void gotShaBegin(Sha sha) {

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

    default void jueDouBegin() {

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

    default Card changeJudge(Card d) {
        return null;
    }

    default int numOfTian() {
        return 0;
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

    default boolean hasQianXun() {
        return false;
    }

    default boolean hasHongYan() {
        return false;
    }

    default boolean hasHuoShou() {
        return false;
    }

    default boolean hasDuanLiang() {
        return false;
    }

    default boolean hasJuXiang() {
        return false;
    }

    default boolean hasBaZhen() {
        return false;
    }

    default boolean hasFeiYing() {
        return false;
    }

    default void setBaZhen(boolean bool) {

    }

    default boolean hasWuShuang() {
        return false;
    }

    default boolean hasRouLin() {
        return false;
    }

    default boolean hasWeiMu() {
        return false;
    }

    default boolean hasWanSha() {
        return false;
    }

    default boolean usesXingShang() {
        return false;
    }

    default String usesYingYang() {
        return "";
    }

    default boolean skipJudge() {
        return false;
    }

    default boolean skipDraw() {
        return false;
    }

    default boolean skipUse() {
        return false;
    }

    default boolean skipThrow() {
        return false;
    }

    default void hurtOther(Person p) {

    }

    default void otherPersonUsePhase(Person p) {

    }

    default void otherPersonThrowPhase(Person p, ArrayList<Card> cards) {

    }

    default void otherPersonGetJudge(Person p) {

    }

    default void otherPersonMakeHurt(Person p) {

    }

    default void otherPersonHurtBySha(Person source, Person target) {

    }

    default void initialize() {

    }

    default void clearCards() {

    }
}
