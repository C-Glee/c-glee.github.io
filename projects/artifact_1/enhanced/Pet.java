/*
 * Author: Carson Lee
 * Date: 11/14/2025
 * Description: The Pet class belonging to the Pet BAG program. Used to store pet information and for checking pets in/out.
 */
public class Pet {
// Initialize variables
    private int petId; // Unique Identifier
    private String petName;
    private String petType; // "Dog" or "Cat"
    private int petAge;
    private int petSpace;   // assigned space number (1..12 for dogs or 1..10 for cats)
    private int daysStay;
    private double petWeight;  // weight in pounds pounds
    private Boolean petGrooming;
    private double amountDue;


	// Default constructor for the class
	public Pet() {
		this.petId = 0;
		this.petType = "None";
		this.petName = "None";
		this.petAge = 0;
		this.petSpace = 0;
		this.daysStay = 0;
		this.petWeight = 0.0;
		this.amountDue = 0.0;
	}

	// Constructor with parameters
	public Pet(int petId, String petType, String petName, int petAge, int petSpace, int daysStay, double petWeight, Boolean petGrooming, double amountDue) {
		this.petType = petType;
		this.petName = petName;
		this.petAge = petAge;
		this.petSpace = petSpace;
		this.daysStay = daysStay;
		this.amountDue = amountDue;
	}


	// Getters
	public int getPetId() {
		return this.petId;
	}
	public String getPetType() {
		return this.petType;
	}
	public String getPetName() {
		return this.petName;
	}
	public int getPetAge() {
		return this.petAge;
	}
	public int getPetSpace() {
		return this.petSpace;
	}
	public int getDaysStay() {
		return this.daysStay;
	}
	public double getPetWeight() {
		return this.petWeight;
	}
	public boolean getPetGrooming() {
		return this.petGrooming;
	}
	/*
	 * Getter must calculate amount due based on pet info
     * Pricing logic:
     * - Dogs: base $15/day. If weight > 20 lb then $20/day, if >50 lb then $25/day.
     * - Cats: base $15/day. Weight not relevant.
     * - Grooming: Flat fee is + $10. If weight > 20 lb then + $20, if weight > 50 then + $30.
     **/
    public double calculateAmountDue() {
        double perDay = 15.0;
        double groomingCost = 0.0;
        // Adjust perDay on weight
        if (this.petWeight <= 20) {
        	perDay = 20.0;
        } else if (this.petWeight > 20 && this.petWeight <= 50) {
        	perDay = 25.0;
        } else {
        	perDay = 30.0;
        }
        // Determine grooming cost
        if (this.getPetGrooming()){
        	groomingCost = 10.0;
        	if (this.petWeight > 20 && this.petWeight <= 50) {
        		groomingCost = 20.0;
        	} else {
        		groomingCost = 30.0;
        	}
        }
        //Update pet info and return total cost;
        double totalCost = (perDay * this.daysStay) + groomingCost;
        this.amountDue = totalCost;

        return totalCost;
    }
	
	// Setters
	public void setPetId(int petId) {
		this.petId = petId;
	}
	public void setPetType(String petType) {
		this.petType = petType;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}
	public void setPetAge(int petAge) {
		this.petAge = petAge;
	}
	public void setPetSpace(int petSpace) {
		this.petSpace = petSpace;
	}
	public void setDaysStay(int daysStay) {
		this.daysStay = daysStay;
	}
	public void setPetWeight(double weight) {
		this.petWeight = weight;
	}
	public void setPetGrooming(Boolean petGrooming) {
		this.petGrooming = petGrooming;
	}
	public void setAmountDue(double amountDue) {
		this.amountDue = amountDue;
	}
	
	@Override
	public String toString() {
		return String.format("ID: %d | Name: %s | Type: %s | Age: %d | Space: %d | Weight: %.1f lb(s). | Days: %d | Groomed: %b |Due: $%.2f",
                petId, petName, petType, petAge, petSpace, petWeight, daysStay, petGrooming, amountDue);
	}
}
