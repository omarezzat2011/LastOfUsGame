package model.world;

public abstract class Disaster {
	private static int ID=1;
	private String name;
	private int timer=2;
	public Disaster(String name) {
		this.name=name+ID++;
		//ID++;
	}
	public static int getID() {
		return ID;
	}
	public String getName() {
		return name;
	}
	public int getTimer() {
		return timer;
	}
}
