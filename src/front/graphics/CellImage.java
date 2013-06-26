package front.graphics;

import java.awt.Image;
import java.awt.Point;

/**
 * Class which represents a Cell to print, it contains a position and an Image
 * 
 */
public class CellImage {
	private Image image;
	private Point position;
	private boolean isBlankCell;

	public CellImage(Image image, Point position, boolean isBlankCell) {
		this.image = image;
		this.position = position;
		this.isBlankCell = isBlankCell;
	}

	/**
	 * Returns the CellImage image
	 * 
	 * @return Image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Retuns the CellImage position
	 * 
	 * @return
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * Returns if a CellImage represents a Blank Cell, this method is useful
	 * because when imagesToPrint is initialized, lots of Blank Cell are added,
	 * and when a new pipe is added, the Blank Cell that was at that position is
	 * not removed, the Pipe is added to the list with the same position and is
	 * printed over the Blank Cell. So when a Pipe is removed, it can first find a Blank
	 * Cell in the point we are looking for and is not what we want to remove.
	 * 
	 * @return boolean
	 */
	public boolean isBlankCell() {
		return this.isBlankCell;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isBlankCell ? 1231 : 1237);
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CellImage other = (CellImage) obj;
		if (isBlankCell != other.isBlankCell)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}
}
