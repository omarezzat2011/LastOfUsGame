package model.world;

public class Collapse extends Disaster {
	private int damagedCells=3;
	public Collapse() {
		super("Collapse ");
	}
	public int getDamagedCells() {
		return damagedCells;
	}

}
