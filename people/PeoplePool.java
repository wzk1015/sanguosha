package people;

import manager.Utils;

import java.util.ArrayList;
import java.util.Collections;

public class PeoplePool {
    private static final ArrayList<Person> people = new ArrayList<>();
    private static final ArrayList<Identity> identities = new ArrayList<>();
    private static final int optionsPerPerson = 1;
    private static int peopleIndex = 0;
    private static int identityIndex = 0;

    public static void init() {
        people.add(new BlankPerson());
        //people.add(new AI());
        people.add(new BlankPerson2());
        Collections.shuffle(people);

        identities.add(Identity.KING);
        identities.add(Identity.MINISTER);
        identities.add(Identity.MINISTER);
        identities.add(Identity.TRAITOR);
        identities.add(Identity.REBEL);
        identities.add(Identity.REBEL);
        identities.add(Identity.REBEL);
        identities.add(Identity.REBEL);
    }

    public static ArrayList<Person> allocPeople() {
        peopleIndex += optionsPerPerson;
        Utils.assertTrue(peopleIndex <= people.size(), "No people available");
        return new ArrayList<>(people.subList(peopleIndex - optionsPerPerson, peopleIndex));
    }

    public static Identity allocIdentity() {
        return identities.get(identityIndex++);
    }
}
