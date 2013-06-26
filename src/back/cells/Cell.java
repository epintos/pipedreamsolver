package back.cells;

import java.awt.Point;

import front.graphics.CellImage;

/**
 * Class which represents a Cell with a location 
 */
public abstract class Cell {

	private Point location;
	
	public Cell(Point p) {
		location = p;
	}
	
	/**
	 * Returns the cell location
	 * @return location
	 */
	public Point getLocation() {
		return location;
	}
	
	/**
	 * Sets the cell location
	 * @param location
	 */
	public void setLocation(Point location) {
		this.location = location;
	}
	
	/**
	 * Returns true if a pipe can be placed over this cell
	 * @return
	 */
	public abstract boolean canStepOver();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		CellImage other = (CellImage) obj;
		if(other.isBlankCell())
			return false;
		if (location == null) {
			if (other.getPosition() != null)
				return false;
		} else if (!location.equals(other.getPosition()))
			return false;
		return true;
	}
}
