package people;

public class BlankPerson extends Person {
    public BlankPerson() {
        super(4,  "male", Nation.QUN);
    }

    @Override
    public String toString() {
        return "白板";
    }
}
