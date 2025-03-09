
public class ShapeManager {

	protected int numOfManagers;
	protected double managerPrice;
	protected double anglesPerSecond;

	ShapeManager() {
		numOfManagers = 0;
		managerPrice = 50;
		anglesPerSecond = 1;

	}

	/**
	 * 
	 * @param numAngles
	 * @return
	 */
	public int buyManager() {
		int increasePerSecond = 0;

		numOfManagers++;
		managerPrice += (managerPrice *2); 
		increasePerSecond = 1;
		anglesPerSecond++; 
		
		if (numOfManagers % 20 == 0) {
			increasePerSecond *= 1;
			anglesPerSecond *= 1;
		}

		return increasePerSecond;
	}

	public boolean canAfford(double numAngles) {

		if (numAngles >= managerPrice)
			return true;

		return false;
	}

	/**
	 * 
	 * @return the number of managers the player has purchased
	 */
	public int getNumOfManagers() {
		return numOfManagers;
	}

	/**
	 * 
	 * @return the price required to buy another manager
	 */
	public double getCost() {
		return managerPrice;
	}

	/**
	 * 
	 * @return the number of angles all the managers produce per second
	 */
	public double getAnglesPerSecond() {
		return anglesPerSecond;
	}
}
