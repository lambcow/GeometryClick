
public class HexagonManager extends ShapeManager {

	HexagonManager() {
		super();

		managerPrice = 400;
	}

	/**
	 * If the player has enough angles to buy a new manager, it increases the
	 * 
	 * @param numAngles
	 *            the currency of the game, the amount of angles the player
	 *            currently has
	 * @return true or false if the user has enough angles to purchase the manager
	 */
	public int buyManager() {

		int increasePerSecond = 0;

		numOfManagers++;
		managerPrice *= 1.2; //Increases the price by 20%
		increasePerSecond = 6;
		anglesPerSecond += 6;

		if (numOfManagers % 20 == 0) {
			increasePerSecond = 6;
			anglesPerSecond *= 6;
		}

		return increasePerSecond;
	}

	
	
}
