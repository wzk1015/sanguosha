package people;

import manager.Utils;

import java.util.ArrayList;
import java.util.Collections;

public class PeoplePool {
    private static final ArrayList<Person> people = new ArrayList<>();
    private static final int optionsPerPerson = 1;
    private static int index = 0;

    public static void init() {
        //TODO
        //for (int i = 0; i < 100; i++) {
        //    people.add(new BlankPerson());
        //}
        people.add(new BlankPerson());
        people.add(new BlankPerson2());
        Collections.shuffle(people);
    }

    public static ArrayList<Person> allocPeople(int num) {
        index += optionsPerPerson;
        Utils.assertTrue(index <= people.size(), "No people available");
        return new ArrayList<>(people.subList(index - optionsPerPerson, index));
    }
}
