import java.io.*;
import java.util.*;

public class PetDataManager {

    private final String filePath;

    public PetDataManager(String filePath) {
        this.filePath = filePath;
    }

    // Loads all boarded pets from 'pets.txt'
    public List<Pet> loadPets(boolean[] dogOccupied, boolean[] catOccupied, int[] nextPetIdHolder) {
        List<Pet> pets = new ArrayList<>();
        File file = new File(filePath);

        System.out.println("Attempting to load pet data.");

        if (!file.exists()) {
            System.out.println("No saved pets found, starting fresh.");
            return pets;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {
                Pet p = parsePet(line);

                if (p != null) {
                    pets.add(p);

                    // Mark spaces
                    if (p.getPetType().equalsIgnoreCase("Dog")) {
                        if (p.getPetSpace() > 0 && p.getPetSpace() < dogOccupied.length)
                            dogOccupied[p.getPetSpace()] = true;
                    } else {
                        if (p.getPetSpace() > 0 && p.getPetSpace() < catOccupied.length)
                            catOccupied[p.getPetSpace()] = true;
                    }

                    // Track ID counter
                    nextPetIdHolder[0] = Math.max(nextPetIdHolder[0], p.getPetId() + 1);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading pets file: " + e.getMessage());
        }

        System.out.println("Pet data loaded.");
        return pets;
    }

    // Writes all boarded pet data to file.
    public void savePets(List<Pet> pets) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
            for (Pet p : pets) {
                out.println(petToCSV(p));
            }
        } catch (IOException e) {
            System.out.println("Error saving pets: " + e.getMessage());
        }
    }

    // Helpers

    private Pet parsePet(String line) {
        try {
            String[] parts = line.split(",");

            if (parts.length < 8) {
                System.out.println("Skipping malformed line: " + line);
                return null;
            }

            Pet p = new Pet();
            p.setPetId(Integer.parseInt(parts[0]));
            p.setPetName(parts[1]);
            p.setPetType(parts[2]);
            p.setPetAge(Integer.parseInt(parts[3]));
            p.setPetWeight(Double.parseDouble(parts[4]));
            p.setDaysStay(Integer.parseInt(parts[5]));
            p.setPetSpace(Integer.parseInt(parts[6]));
            p.setPetGrooming(Boolean.parseBoolean(parts[7]));
            p.calculateAmountDue();

            return p;
        } catch (Exception ex) {
            System.out.println("Error parsing line: " + line);
            return null;
        }
    }

    private String petToCSV(Pet p) {
        return String.join(",",
            String.valueOf(p.getPetId()),
            p.getPetName(),
            p.getPetType(),
            String.valueOf(p.getPetAge()),
            String.valueOf(p.getPetWeight()),
            String.valueOf(p.getDaysStay()),
            String.valueOf(p.getPetSpace()),
            String.valueOf(p.getPetGrooming())
        );
    }
}
