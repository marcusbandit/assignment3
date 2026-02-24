package dk.dtu.compute.course02324.assignment3.lists.uses;

import jakarta.validation.constraints.NotNull;

public class Person implements Comparable<Person> {

    final public String name;

    final public double weight;

    Person(@NotNull String name, @NotNull double weight) {
        if (name == null || weight <= 0) {
            throw new IllegalArgumentException("\033[31mA persons must be initialized with a" +
                    "(non null) name and an age greater than 0\033[0m");
        }
        this.name = name;
        this.weight = weight;
    }

    @Override
    public int compareTo(@NotNull Person o) {
        if (o == null) {
            throw new IllegalArgumentException("\033[31mArgument of compareTo() must not be null\033[0m");
        }
        int nameCompare = this.name.compareTo(o.name);
        if (nameCompare != 0) {
            return nameCompare;
        }
        return Double.compare(this.weight, o.weight);
    }

    /**
     * Computes a simple string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        // This could be automatically generated, but this automatically
        // generated representation is a bit too verbose. Therefore, we
        // chose a simpler representation here.
        return name + ", " + weight + "kg";
    }

    /*
     * The following two methods must be implemented in order to respect the contract of objects
     * with respect to equals(), hashCode() and compareTo() methods. The details and reasons
     * as to why will be discussed much later.
     *
     * The implementations can be done fully automatically my IntelliJ (using the two attributes
     * of person).
     *
     * @param o
     * @return
     */
    /**
     * Checks whether this person is equal to the given object. Two persons
     * are considered equal if their names and weights are the same, which
     * is consistent with the {@link #compareTo(Person)} method.
     *
     * @param o the object to compare with
     * @return {@code true} iff the given object is a Person with the same name and weight
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Double.compare(person.weight, weight) == 0 &&
                name.equals(person.name);
    }

    /**
     * Returns a hash code for this person, consistent with {@link #equals(Object)}.
     * If two persons are equal, they will have the same hash code.
     *
     * @return a hash code value for this person
     */
    @Override
    public int hashCode() {
        int result = name.hashCode();
        long temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }


}