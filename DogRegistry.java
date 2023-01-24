/*
 *  @author William Jakobsson, wija4116
 */

import java.util.ArrayList;
import java.util.Collections;

public class DogRegistry {
    // variables for the program
    private ArrayList<Dog> dogList = new ArrayList<>();
    private ArrayList<Owner> ownerList = new ArrayList<>();
    private ScannerWithPrompt input = new ScannerWithPrompt();

    // initial program
    public static void main(String[] args) {
        new DogRegistry().run();
    }

    // main Owner interface methods
    private void run() {
        startup();
        loopCommands();
        shutdown();
    }

    private void startup() {
        printMenu();
    }

    private void printMenu() {
        System.out.println(
                "Here's the commands you can use:\n" +
                        "* register new dog\n" +
                        "* list dogs\n" +
                        "* increase age\n" +
                        "* remove dog\n" +
                        "* register new owner\n" +
                        "* give dog\n" +
                        "* list owners\n" +
                        "* remove owned dog\n" +
                        "* remove owner\n" +
                        "* help\n" +
                        "* exit");
    }

    private void loopCommands() {
        String command;
        do {
            command = input.readText("Command").toLowerCase();
            handleCommand(command);
        } while (!command.equals("exit"));
    }

    private void handleCommand(String command) {
        switch (command) {
            case "register new dog" -> registerNewDog();
            case "list dogs" -> listDogs();
            case "increase age" -> increaseDogAge();
            case "remove dog" -> removeDog();
            case "register new owner" -> registerNewOwner();
            case "give dog" -> giveDogToOwner();
            case "list owners" -> listOwner();
            case "remove owned dog" -> removeDogFromOwner();
            case "remove owner" -> deleteOwner();
            case "help" -> printMenu();
            case "exit" -> {}
            default -> System.out.println("Error: Command doesn't exist");
        }
    }

    // commands for the program
    private void registerNewDog() {
        String name = repeatString("Name");
        String breed = repeatString("Breed");

        int age = repeatInteger("Age");
        int weight = repeatInteger("Weight");

        Dog dog = new Dog(name, breed, age, weight);
        dogList.add(dog);
        System.out.println(dog.getName() + " added to the register");
    }

    private void listDogs() {
        sortDogList();
        if (dogList == null || dogList.isEmpty()) {
            System.out.println("Error: no dogs in register");
            return;
        }
        double tailLength = input.readDouble("Smallest tail length to display");
        ArrayList<Dog> dogsWithSpecifiedLength = new ArrayList<>();
        for (Dog dog : dogList) {
            if (dog.getTailLength() >= tailLength) {
                dogsWithSpecifiedLength.add(dog);
            }
        }
        if (dogsWithSpecifiedLength.isEmpty()) {
            System.out.println("Error: no dog have a tail that long");
        } else {
            System.out.println("The following dogs has such a large tail:");
            for (Dog dog : dogsWithSpecifiedLength) {
                System.out.println("* " + dog);
            }
        }
    }

    private void increaseDogAge() {
        String name = repeatString("Enter the name of the dog");
        if (findDog(name) == null) {
            System.out.println("Error: no such dog");
        } else {
            findDog(name).increaseAge(1);
            System.out.println(findDog(name).getName() + " is now one year older");
        }
    }

    private void removeDog() {
        String name = repeatString("Enter the name of the dog");
        Dog foundDog = findDog(name);
        if (foundDog == null) {
            System.out.println("Error: no such dog");
        } else {
            foundDog.removeOwner();
            dogList.remove(foundDog);
            System.out.println(foundDog.getName() + " is removed from the register");
        }
    }

    private void registerNewOwner() {
        String name = repeatString("Name");
        Owner owner = new Owner(name);
        ownerList.add(owner);
        System.out.println(owner.getName() + " added to the register");
    }

    private void giveDogToOwner() {
        String dogName = getAvailableDogName();
        if (dogName == null) return;

        String ownerName = getOwnerName();
        if (ownerName == null) return;

        Dog dog = findDog(dogName);
        if (dog.getOwner() != null) {
            System.out.println("Error: dog is already owned by " + dog.getOwner().getName());
            return;
        }
        Owner owner = findOwner(ownerName);
        owner.addDog(dog);
        System.out.println(owner.getName() + " now owns " + dog.getName());
    }

    private void listOwner() {
        if (ownerList == null || ownerList.isEmpty()) {
            System.out.println("Error: no owners in register");
            return;
        }
        for (Owner owner : ownerList) {
            System.out.println(owner);
        }
    }

    private void removeDogFromOwner() {
        String dogName = repeatString("Enter the name of the dog");
        Dog foundDog = findDog(dogName);
        if (foundDog == null) {
            System.out.println("Error: no such dog");
            return;
        }
        if (foundDog.getOwner() == null) {
            System.out.println("Error: " + foundDog.getName() + " is not owned by anyone");
            return;
        }
        if (foundDog.getOwner() != null) {
            foundDog.removeOwner();
        }
    }

    private void deleteOwner() {
        String name = repeatString("Enter the name of the owner");
        if (findOwner(name) == null) {
            System.out.println("Error: no such owner");
            return;
        }

        for (int i = dogList.size() - 1; i >= 0; i--) {
            Owner dogOwner = dogList.get(i).getOwner();
            if (dogOwner != null && dogOwner.equals(findOwner(name))) {
                dogList.remove(i);
            }
        }

        for (int i = 0; i < ownerList.size(); i++) {
            if (ownerList.get(i).equals(findOwner(name))) {
                ownerList.remove(i);
                break;
            }
        }
    }

    private void shutdown() {
        System.out.println("Cya :)");
        System.exit(0);
    }

    // methods for the commands
    private String repeatString(String prompt) {
        String name = input.readText(prompt);
        while (name.isBlank() || name.isEmpty()) {
            System.out.println("Error: the input can't be empty");
            name = input.readText(prompt);
        }
        return name;
    }

    private int repeatInteger(String prompt) {
        int value = input.readInteger(prompt);
        while (value < 0) {
            System.out.println("Error: The entered value can't be lower than 0");
            value = input.readInteger(prompt);
        }
        return value;
    }

    private String getOwnerName() {
        String ownerName = repeatString("Enter the name of the new owner");
        if (findOwner(ownerName) == null) {
            System.out.println("Error: no owner with that name");
            return null;
        }
        return ownerName;
    }

    private String getAvailableDogName() {
        String dogName = repeatString("Enter the name of the dog");
        if (findDog(dogName) == null) {
            System.out.println("Error: no dog with that name");
            return null;
        }
        if (findDog(dogName).getOwner() != null) {
            System.out.println("Error: the dog already has an owner");
            return null;
        }
        return dogName;
    }

    private Owner findOwner(String name) {
        for (Owner owner : ownerList)
            if (owner.getName().equalsIgnoreCase(name)) return owner;
        return null;
    }

    private Dog findDog(String name) {
        for (Dog dog : dogList)
            if (dog.getName().equalsIgnoreCase(name)) return dog;
        return null;
    }

    private void swapDogPosition(int firstDog, int secondDog) {
        Collections.swap(dogList, firstDog, secondDog);
    }

    private int findSmallestDog(int pointer) {
        int smallestDogFound = pointer;
        for (; pointer < dogList.size(); pointer++) {
            if (getTailLength(pointer) < getTailLength(smallestDogFound))
                smallestDogFound = pointer;

            if (getTailLength(pointer) == getTailLength(smallestDogFound)) {
                if (getName(pointer).compareToIgnoreCase(getName(smallestDogFound)) < 0)
                    smallestDogFound = pointer;
            }
        }
        return smallestDogFound;
    }

    private double getTailLength(int i) {
        return dogList.get(i).getTailLength();
    }

    private String getName(int i) {
        return dogList.get(i).getName();
    }

    private void sortDogList() {
        for (int i = 0; i < dogList.size() - 1; i++) {
            int smallestDog = findSmallestDog(i);
            if (i != smallestDog)
                swapDogPosition(i, smallestDog);
        }
    }
}