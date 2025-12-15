/*
 * Author: Carson Lee
 * Date: 11/14/2025
 * Description: The driver of the Pet BAG program. Contains methods to check-in pets, view currently boarded pets, and check-out pets.
 */
import java.io.*;
import java.util.*;

public class Driver {
    // Constants indicating the current number of the facilities max capacity.
    private static final int MAX_DOG_SPACES = 12;
    private static final int MAX_CAT_SPACES = 10;
    
    // Loading and saving data
    PetDataManager dataManager = new PetDataManager("pets.txt");
    
    // Track which dog/cat spaces are currently occupied.
    private boolean[] dogOccupied = new boolean[MAX_DOG_SPACES + 1]; // index 1-12
    private boolean[] catOccupied = new boolean[MAX_CAT_SPACES + 1]; // index 1-10
    
    // Arbitrary starting point for pet ID generation
    private int nextPetId = 1000;
	int[] nextPetIdHolder = { 1 };
	
    // Stores all currently boarded pets
    private ArrayList<Pet> boardedPets = (ArrayList<Pet>) dataManager.loadPets(dogOccupied, catOccupied, nextPetIdHolder);

    // For console input
    private Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        new Driver().run();
    }
    
    // Main loop
    private void run() {

    	boardedPets = (ArrayList<Pet>) dataManager.loadPets(dogOccupied, catOccupied, nextPetIdHolder);
    	nextPetId = nextPetIdHolder[0];
    	
        System.out.println("Welcome to Pet Boarding And Grooming");
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1) Check in or update a pet");
            System.out.println("2) View boarded pets");
            System.out.println("3) Check out a pet");
            System.out.println("4) Exit");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": checkInPet(); break;
                case "2": viewBoardedPets(); break;
                case "3": checkOutPet(); break;
                case "4":
                	//savePetsToFile();
                	dataManager.savePets(boardedPets);
                    System.out.println("Exiting program. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Try 1-4.");
            }
        }
    }
    
    private void checkInPet() {
    	// Choice to update existing pet using ID
        System.out.print("Update a boarded pet? (y/n): ");
        String response = sc.nextLine().trim().toLowerCase();

        Pet pet = new Pet();

        if (response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes")) {
        // Loop to attempt ID re-entry
        while (true) {
        	
            int id = readInt("Enter the pet ID: ", 0, Integer.MAX_VALUE);
            Pet found = null;

            // Search for existing pet
            for (Pet p : boardedPets) {
                if (p.getPetId() == id) {
                    found = p;
                    break;
                }
            }
            
            if (found == null) {
                System.out.printf("No pet found with ID: %d. Check ID number and try again.\n", id);
            } else {
                System.out.println("Pet found: " + found); // Display found pet
                System.out.print("Would you like to update this pet? (y/n): "); // Give option to update or repeat loop
                String update = sc.nextLine().trim();
                if (update.equalsIgnoreCase("y") || update.equalsIgnoreCase("yes")) {
                    pet = found;  // Proceed with this pet to check-in
                    break;
                }

            	}
        	}
        }
        else {
        	System.out.println("Proceeding with check-in.");
        }
        
        // If pet is not already boarded, assign one and increment
        if (!isAlreadyBoarded(pet.getPetId())) {
        	pet.setPetId(nextPetId++);
        }
        // Pet Name
        System.out.print("What is your pet's name? ");
        String petName = sc.nextLine().trim();
        pet.setPetName(petName);
        // Pet Type
        System.out.printf("Is %s a Cat or a Dog?: ", pet.getPetName());
        String typeInput = sc.nextLine().trim();
        String type = typeInput.equalsIgnoreCase("cat") ? "Cat" : "Dog";
        pet.setPetType(type);
        // Pet Age
        int age = readInt(String.format("How old is %s (in years)?: ", pet.getPetName()), 0, 100);
        pet.setPetAge(age);
        // Pet Weight
        double wt = readDouble(String.format("How much does %s weigh (in lbs.)?: ", pet.getPetName()), 0.1, 10000.0);
        pet.setPetWeight(wt);
    
        int days = readInt(String.format("How many days is %s staying with us?: ", pet.getPetName()), 1, 365);
        pet.setDaysStay(days);

        boolean grooming = readBoolean(String.format("Would you like for %s to be groomed during their stay (y/n)? ", pet.getPetName()));
        pet.setPetGrooming(grooming);
        
        //Check for available space
        String petType = pet.getPetType();

        if (petType.equalsIgnoreCase("Dog") && !hasDogSpace()) {
            System.out.println("No dog spaces available.");
            return;
        }

        if (petType.equalsIgnoreCase("Cat") && !hasCatSpace()) {
            System.out.println("No cat spaces available.");
            return;
        }
        
        // Assign space, if space is not already assigned
        if (pet.getPetSpace() == 0) {
	        pet.setPetSpace(assignSpace(petType));
        }
        // If pet is assigned a space and is not already present, add to boardedPets
        if (pet.getPetSpace() > 0 && !isAlreadyBoarded(pet.getPetId())) {
            boardedPets.add(pet);
        }
        
        //Finalize check-in
        System.out.printf("Check-in successful! Assigned %s space #%d. Pet ID: %d. Amount due: $%.2f%n",
            petType.toLowerCase(), pet.getPetSpace(), pet.getPetId(), pet.calculateAmountDue());
}

    private void viewBoardedPets() {
        if (boardedPets.isEmpty()) {
            System.out.println("No pets are currently boarded.");
            return;
        }
        System.out.println("Currently boarded pets:");
        for (Pet p : boardedPets) {
            System.out.println(p.toString());
        }
        System.out.printf("Available dog spaces: %d / %d%n", remainingDogSpaces(), MAX_DOG_SPACES);
        System.out.printf("Available cat spaces: %d / %d%n", remainingCatSpaces(), MAX_CAT_SPACES);
    }

    private void checkOutPet() {
        // Quick check if any pets are boarded
    	if (boardedPets.isEmpty()) {
            System.out.println("No pets to check out.");
            return;
        }
    	// Find pet to check out by ID
        int id = readInt("Enter pet ID to check out: ", 0, Integer.MAX_VALUE);
        Pet found = null;
        for (Pet p : boardedPets) {
            if (p.getPetId() == id) { found = p; break; }
        }
        if (found == null) {
            System.out.println("No pet was found. Double check your ID number.");
            return;
        }
        // Display found pet and confirm check-out
        System.out.println("Found: " + found);
        System.out.print("Proceed with checkout? (y/n): ");
        String confirm = sc.nextLine().trim();
        
        if (!(confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes"))) {
            System.out.println("Checkout cancelled.");
            return;
        }

        // Release corresponding space
        if (found.getPetType().equalsIgnoreCase("Dog")) {
            int s = found.getPetSpace();
            if (s >= 1 && s <= MAX_DOG_SPACES) dogOccupied[s] = false;
        } else {
            int s = found.getPetSpace();
            if (s >= 1 && s <= MAX_CAT_SPACES) catOccupied[s] = false;
        }
        // Remove pet from boardedPets list
        boardedPets.remove(found);
        
        //Finalize check-out
        System.out.printf("Checked out %s (ID: %d).",
                found.getPetName(), found.getPetId());
    }

    // Helpers
    // Check remaining spaces
    private boolean hasDogSpace() { return remainingDogSpaces() > 0; }
    private boolean hasCatSpace() { return remainingCatSpaces() > 0; }

    private int remainingDogSpaces() {
        int c = 0;
        for (int i = 1; i <= MAX_DOG_SPACES; i++) if (!dogOccupied[i]) c++;
        return c;
    }
    private int remainingCatSpaces() {
        int c = 0;
        for (int i = 1; i <= MAX_CAT_SPACES; i++) if (!catOccupied[i]) c++;
        return c;
    }
    // Check if corresponding pet ID is already in boardedPets
    private boolean isAlreadyBoarded(int petId) {
        for (Pet p : boardedPets) if (p.getPetId() == petId) return true;
        return false;
    }
    // Return lowest-numbered available space for the corresponding type and mark it occupied
    private int assignSpace(String type) {
        if (type.equalsIgnoreCase("Dog")) {
            for (int i = 1; i <= MAX_DOG_SPACES; i++) {
                if (!dogOccupied[i]) {
                    dogOccupied[i] = true;
                    return i;
                }
            }
        } else {
            for (int i = 1; i <= MAX_CAT_SPACES; i++) {
                if (!catOccupied[i]) {
                    catOccupied[i] = true;
                    return i;
                }
            }
        }
        return -1; // Should not happen if availability already checked
    }

    // Integer input (with bounds)
    private int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                String s = sc.nextLine().trim();
                int v = Integer.parseInt(s);
                if (v < min || v > max) {
                    System.out.printf("Enter a value between %d and %d.%n", min, max);
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Try again.");
            }
        }
    }
    // Double input handler (with bounds)
    private double readDouble(String prompt, double min, double max) {
        while (true) {
            try {
                System.out.print(prompt);
                String s = sc.nextLine().trim();
                double v = Double.parseDouble(s);
                if (v < min || v > max) {
                    System.out.printf("Enter a value between %.2f and %.2f.%n", min, max);
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }
    // Boolean input handler
    private boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim().toLowerCase();

            if (s.equals("y") || s.equals("yes")) {
                return true;
            } else if (s.equals("n") || s.equals("no")) {
                return false;
            } else {
                System.out.println("Please enter 'yes' or 'no'.");
            }
        }
    }

}
