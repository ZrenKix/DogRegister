/*
 *  @author William Jakobsson, wija4116
 *  Date: 2023-01-12
 */

import java.util.Arrays;

public class DogList {
    private Dog[] dogList = {};

    public String getNames() {
        String list = "";
        for (Dog dog : dogList) {
            list += (list.isEmpty() ? "" : ", ") + dog.getName();
        }
        return String.format("[%s]".formatted(list));
    }

    public boolean isDogInList(Dog search) {
        for (Dog dog : dogList)
            if (dog.equals(search))
                return true;
        return false;
    }

    public void addDogToList(Dog dog) {
        if (dog == null || isDogInList(dog))
            return;
        dogList = Arrays.copyOf(dogList, dogList.length + 1);
        dogList[dogList.length - 1] = dog;
    }

    public void removeDogFromList(Dog dog) {
        if (dog == null || !isDogInList(dog))
            return;

        Dog[] newArray = new Dog[dogList.length - 1];
        int index = 0;
        for (Dog d : dogList) {
            if (!d.equals(dog)) {
                newArray[index] = d;
                index++;
            }
        }
        dogList = Arrays.copyOf(newArray, newArray.length);
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < dogList.length; i++) {
            result += "%s".formatted(dogList[i]);
            if (i != dogList.length - 1) {
                result += ", ";
            }
        }
        return result;
    }
}
