package one.jmg.minesweeper2d.engine;

public class MineTile {

	private boolean hasMine = false;
	private int state = 0; // 0 - hidden, 1 - flagged, 2 - display
		
	private int minedMinedNeighbors = 0;
	
	public void armMine() {
		this.hasMine = true;
	}
	
	public boolean hasMine() {
		return hasMine;
	}

	public void increaseMinedNeighbors() {
		minedMinedNeighbors++;		
	}
	
	public int getMinedNeighbors() {
		return minedMinedNeighbors;
	}

	public void flag() {
		if (this.state == 0) {
			this.state = 1;
		} else if (this.state == 1) {
			this.state = 0;
		}
	}

	public void reveal() {
		this.state = 2;
	}

	public int getState() {
		return this.state;
	}
}
