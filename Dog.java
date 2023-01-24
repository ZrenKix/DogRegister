/*
 *  @author William Jakobsson, wija4116
 */

import java.util.ArrayList;
import java.util.Arrays;

public class Dog {
    private static final double DACHSHUND_TAIL_LENGTH = 3.7;
    private static final ArrayList<String> DACHSHUND_NAMES_LIST = new ArrayList<>(Arrays.asList("tax", "dachshund",
            "bassotto", "gravhund", "teckel", "taksikoer", "mäyräkoira", "tekkel", "baja", "dackel"));
    private String name;
    private String breed;
    private int age;
    private int weight;
    private Owner owner;

    public Dog(String name, String breed, int age, int weight) {
        this.name = name;
        this.breed = breed.toLowerCase();
        this.age = age;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public int getAge() {
        return age;
    }

    public void increaseAge(int newAge) {
        if (newAge > 0) {
            age += newAge;
        }
    }

    public int getWeight() {
        return weight;
    }

    public double getTailLength() {
        double tailLength = 0;
        for (String s : DACHSHUND_NAMES_LIST) {
            if (s.equalsIgnoreCase(breed)) {
                tailLength = DACHSHUND_TAIL_LENGTH;
                break;
            } else {
                tailLength = age * (weight / 10d);
            }
        }
        return tailLength;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        if (owner == null)
            return;
        if (!owner.isDogOwned(this))
            owner.addDog(this);
        if (this.owner == null)
            this.owner = owner;
    }

    public void removeOwner() {
        if (owner == null)
            return;
        owner.removeDogOwnership(this);
        owner = null;
    }

    @Override
    public String toString() {
        String endOfString = owner != null ? ", owned by %s)".formatted(owner.getName()) : ", no owner)";
        return String.format("%s (%s, %d years, %d kilo, %.2f cm tail%s".formatted(name, breed, age, weight, getTailLength(), endOfString));
    }
}