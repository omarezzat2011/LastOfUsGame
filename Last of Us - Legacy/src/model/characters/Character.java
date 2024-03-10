package model.characters;

import java.awt.Point;
import model.world.*;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;

public abstract class Character {
	private String name;
	private Point location;
	private int maxHp;
	private int currentHp;
	private int attackDmg;
	private Character target;
	public Character() {
	}
	

	public Character(String name, int maxHp, int attackDmg) {
		this.name=name;
		this.maxHp = maxHp;
		this.currentHp = maxHp;
		this.attackDmg = attackDmg;
	}
		
	public Character getTarget() {
		return target;
	}

	public void setTarget(Character target){
		this.target = target;
	}
	
	public String getName() {
		return name;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		//if(Game.map[location.x][location.y]==null
		//||(((CharacterCell) Game.map[location.x][location.y]).getCharacter()==null) )
		this.location = location;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		if(currentHp <= 0) { 
			this.currentHp = 0;
			onCharacterDeath();

		}
		else if(currentHp > maxHp) 
			this.currentHp = maxHp;
		else 
			this.currentHp = currentHp;
	}

	public int getAttackDmg() {
		return attackDmg;
	}
	public abstract void onCharacterDeath();
	public Cell getCell() {

		int row1 = this.getLocation().x;
		int column1 = this.getLocation().y;
		 return Game.map[row1][column1];

	}
	public void decreaseHealth(int attackDamage) {
		setCurrentHp(getCurrentHp()-attackDamage);

	}
	public void decreaseHealthByHalf(int attackDamage) {
		setCurrentHp(getCurrentHp()-(attackDamage/2));
	}
	public void defend(Character c) throws InvalidTargetException {
			this.setTarget(c);
			c.decreaseHealthByHalf(this.getAttackDmg());
	}
	public static void updateLocation(Character c) {
	    for (int i = 0; i < Game.map.length; i++) {
	        for (int j = 0; j < Game.map[0].length; j++) {
	            if ((Game.map[i][j] instanceof CharacterCell
	            		&&(((CharacterCell) Game.map[i][j]).getCharacter() != null))
	            		&&((CharacterCell) Game.map[i][j]).getCharacter().getName().equals(c.getName())) {
	            	Point p = new Point(i, j);
	            	c.setLocation(p);
	            }
	        }
	    }
	}
	public static void updateLocation(Character c,Cell cc) {
		for (int i = 0; i < Game.map.length; i++) {
	        for (int j = 0; j < Game.map[0].length; j++) {
	            if ((Game.map[i][j] instanceof CharacterCell
	            		&&(((CharacterCell) Game.map[i][j]).getCharacter() != null))
	            		&&(((CharacterCell) Game.map[i][j])==cc)) {
	            	Point p = new Point(i, j);
	            	c.setLocation(p);
	            }
	        }
	    }

	}
	public static boolean isAdjacent(Point p1, Point p2) {
	    int xDiff = Math.abs(p1.x - p2.x);
	    int yDiff = Math.abs(p1.y - p2.y);
	    return (xDiff == 1 && yDiff == 0) || (xDiff == 0 && yDiff == 1);
	}
	public abstract  void attack() throws InvalidTargetException, NotEnoughActionsException;
	


}
