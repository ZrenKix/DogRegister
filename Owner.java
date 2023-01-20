/*
 *  @author William Jakobsson, wija4116
 *  Date: 2023-01-12
 */

public class Owner {
    private String name;
    private DogList ownedDogs;

    public Owner(String name) {
        this.name = name;
        this.ownedDogs = new DogList();
    }

    public String getName() {
        return name;
    }

    public void addDog(Dog dog) {
        if (dog.getOwner() == this || dog.getOwner() != null && dog.getOwner() != this)
            return;
        ownedDogs.addDogToList(dog);
        if (dog.getOwner() != this)
            dog.setOwner(this);
    }

    public void removeDogOwnership(Dog dog) {
        if (!this.ownsDog(dog) || dog.getOwner() == null)
            return;
        ownedDogs.removeDogFromList(dog);
        if (dog.getOwner() == this)
            dog.removeOwner();
    }

    public DogList getOwnedDogs() {
        return ownedDogs;
    }

    public boolean ownsDog(Dog dog) {
        return ownedDogs.isDogInList(dog);
    }

    @Override
    public String toString() {
        return String.format("* %s [%s]".formatted(name, ownedDogs));
    }
}