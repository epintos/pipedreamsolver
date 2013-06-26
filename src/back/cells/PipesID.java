package back.cells;

/**
 * Enumerative with a pipe name and it's ID
 *
 */
public enum PipesID {
	LEFT_UP(0), RIGHT_UP(1), DOWN_RIGHT(2), LEFT_DOWN(3), VERTICAL(4), HORIZONTAL(5),
		CROSS(6);

	private int ID;

	PipesID(int ID) {
		this.ID = ID;
	}
	
	public int getID(){
		return ID;
	}
}
