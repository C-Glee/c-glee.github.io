public class Pet {
	//Initialize private variables
	//petType is either Dog or Cat and determines subclass
	private String petType;
	private String petName;
	//petAge is the pet's age in years
	private int petAge;
	private int dogSpace;
	private int catSpace;
	//How long the pet is staying in days
	private int daysStay;
	//The amount that the owner will owe for services rendered
	private double amountDue;
	
	//Create default constructor for the class
	public Pet() {
		this.petType = "None";
		this.petName = "None";
		this.petAge = -1;
		this.dogSpace = -1;
		this.catSpace = -1;
		this.daysStay = 0;
		this.amountDue = 0.0;
	}
	//Create a constructor for creating an object with specified values
	public Pet(String petType, String petName, int petAge, int dogSpace, int catSpace, int daysStay, double amountDue) {
		this.petType = petType;
		this.petName = petName;
		this.petAge = petAge;
		this.dogSpace = dogSpace;
		this.catSpace = catSpace;
		this.daysStay = daysStay;
		this.amountDue = amountDue;
	}
	//Getters for each attribute
	public String getPetType() {
		return this.petType;
	}
	public String getPetName() {
		return this.petName;
	}
	public int getPetAge() {
		return this.petAge;
	}
	//Because only one value can be returned, if-else branch to determine which space is being used and return said value
	public int getPetSpace() {
		if (dogSpace != -1) {
			return dogSpace;
		} else {
			return catSpace;
		}
	}
	public int getDaysStay() {
		return this.daysStay;
	}
	public double getAmountDue() {
		return this.amountDue;
	}
	
	//Setters for each attribute
	public void setPetType(String petType) {
		this.petType = petType;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}
	public void setPetAge(int petAge) {
		this.petAge = petAge;
	}
	//Determines which type of space should be used based on the petType of the current object
	public void setPetSpace(int petSpace) {
		if (this.petType.equalsIgnoreCase("cat")) {
			this.catSpace = petSpace;
		} else {
			this.dogSpace = petSpace;
		}
	}
	public void setDaysStay(int daysStay) {
		this.daysStay = daysStay;
	}
	public void setAmountDue(double amountDue) {
		this.amountDue = amountDue;
	}
	
}
