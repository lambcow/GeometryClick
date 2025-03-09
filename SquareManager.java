/**
 * represents a manager in the geometry clicking game
 * 
 * @author laura
 *
 */
public class SquareManager extends ShapeManager {

	SquareManager() {
		super();

		managerPrice = 200;
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
		increasePerSecond = 4;
		anglesPerSecond += 4;

		if (numOfManagers % 20 == 0) {
			increasePerSecond *= 4;
			anglesPerSecond += 4;
		}

		return increasePerSecond;
	}

}
