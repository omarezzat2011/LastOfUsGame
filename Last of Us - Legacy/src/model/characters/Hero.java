package model.characters;

import java.awt.Point;
import java.util.ArrayList;

import engine.Game;
import exceptions.GameActionException;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

public abstract class Hero extends Character {
	

		private int actionsAvailable;
		private int maxActions;
		private ArrayList<Vaccine> vaccineInventory;
		private ArrayList<Supply> supplyInventory;
		private boolean specialAction;
	
		
		public Hero(String name,int maxHp, int attackDmg, int maxActions) {
			super(name,maxHp, attackDmg);
			this.maxActions = maxActions;
			this.actionsAvailable = maxActions;
			this.vaccineInventory = new ArrayList<Vaccine>();
			this.supplyInventory=new ArrayList<Supply>();
			this.specialAction=false;
		
		}

		
	


		public boolean isSpecialAction() {
			return specialAction;
		}



		public void setSpecialAction(boolean specialAction) {
			this.specialAction = specialAction;
		}



		public int getActionsAvailable() {
			return actionsAvailable;
		}



		public void setActionsAvailable(int actionsAvailable) {
			this.actionsAvailable = actionsAvailable;
		}



		public int getMaxActions() {
			return maxActions;
		}



		public ArrayList<Vaccine> getVaccineInventory() {
			return vaccineInventory;
		}


		public ArrayList<Supply> getSupplyInventory() {
			return supplyInventory;
		}
		
		public void reset() throws InvalidTargetException {
			this.actionsAvailable=maxActions;
			this.setTarget(null);
			this.specialAction=false;
		}	
		public static boolean isValidPoint(Point newPosition) {
		    return newPosition.x >= 0 && newPosition.x < Game.map.length 
		           && newPosition.y >= 0 && newPosition.y < Game.map[0].length ;
		}
		
		public void move(Direction direction) throws GameActionException {
		    if (actionsAvailable < 1) {
		        throw new NotEnoughActionsException("Cannot move, insufficient actions!");
		    }

		    Point currentPosition = getLocation();

		    Point newPosition = null;
		    switch (direction) {
		        case UP:
		            newPosition = new Point(currentPosition.x+1, currentPosition.y);
		            break;
		        case DOWN:
		            newPosition = new Point(currentPosition.x-1, currentPosition.y );
		            break;
		        case LEFT:
		            newPosition = new Point(currentPosition.x , currentPosition.y-1);
		            break;
		        case RIGHT:
		            newPosition = new Point(currentPosition.x , currentPosition.y+1);
		            break;
		    }

		    if (!isValidPoint(newPosition)) {
		        throw new MovementException("Cannot move, out of bounds!");
		    }
		    if (((Game.map[newPosition.x][newPosition.y] instanceof CharacterCell) &&(((CharacterCell) Game.map[newPosition.x][newPosition.y]).getCharacter() != null))) {
		    	throw new MovementException("Cannot move, cell is occupied!");
		    	}
		    if(Game.map[newPosition.x][newPosition.y] instanceof CollectibleCell) {
		    		((CollectibleCell) Game.map[newPosition.x][newPosition.y]).getCollectible().pickUp(this);
		    			
		    }
		    if(Game.map[newPosition.x][newPosition.y] instanceof TrapCell) {
	    		this.setCurrentHp(getCurrentHp()-((TrapCell) Game.map[newPosition.x][newPosition.y]).getTrapDamage());
	    			
	    }
		    	

		    setLocation(newPosition);
		    actionsAvailable--;

		    CharacterCell updatedCell = new CharacterCell(this,true);
			updatedCell.getCharacter().setLocation(newPosition);
			updatedCell.setVisible(true);
		    Game.map[newPosition.x][newPosition.y]= updatedCell;
		    //Game.map[newPosition.x][newPosition.y].setVisible(true);
		    ((CharacterCell)Game.map[currentPosition.x][currentPosition.y]).setVisible(true);

		    ((CharacterCell)Game.map[currentPosition.x][currentPosition.y]).setCharacter(null);
		    if(this.getCurrentHp()==0) {
			    ((CharacterCell)Game.map[currentPosition.x][currentPosition.y]).setVisible(false);
			    Game.map[newPosition.x][newPosition.y].setVisible(false);
			    ((CharacterCell)Game.map[newPosition.x][newPosition.y]).setVisible(false);
			    ((CharacterCell)Game.map[newPosition.x][newPosition.y]).setCharacter(null);

			    



		    }
		    
		}
		

		public  void attack() throws InvalidTargetException, NotEnoughActionsException{
			if(getTarget()==null)
				throw new InvalidTargetException("There is no target");
			if(!Cell.isAdjacent(getCell(),getTarget().getCell()))
		    	throw new InvalidTargetException("Target not Adjacent!");
			//super.attack();
			if(!(getTarget() instanceof Zombie)) 
		    	throw new InvalidTargetException("Target not a zombie!");
		    if(getActionsAvailable()==0) 
	   	        throw new NotEnoughActionsException("No enough actions");
					
		    getTarget().defend(this);
		    getTarget().decreaseHealth((int)getAttackDmg());
		   		      
		   		    
		   	        
		   	        
		   
		}
		
		public  void useSpecial() throws GameActionException {
			if (getSupplyInventory().isEmpty())
		        throw new NoAvailableResourcesException("There is no supplies left");
			//getSupplyInventory().remove(getSupplyInventory().size() - 1).use(this);

		}
		
		public static boolean isAdjacent(Point p1, Point p2) {
		    int xDiff = Math.abs(p1.x - p2.x);
		    int yDiff = Math.abs(p1.y - p2.y);
		    return (xDiff == 1 && yDiff == 0) || (xDiff == 0 && yDiff == 1);
		}
		public void cure() throws GameActionException  {
			if(this.getTarget() instanceof Hero)
				throw new InvalidTargetException("You cant cure a hero");
			if(this.getTarget() ==null)
				throw new InvalidTargetException("No Target");
			if(this.getActionsAvailable()==0)
				throw new NotEnoughActionsException("You dont have enough action points");
			if(!isAdjacent(getLocation(),getTarget().getLocation()))
				throw new	InvalidTargetException("Target not Adjacent");
			
			if(getVaccineInventory().isEmpty())
				throw new NoAvailableResourcesException("You have no Vaccines");
			setActionsAvailable(getActionsAvailable()-1);

			
			this.getVaccineInventory().get((getVaccineInventory().size()-1)).use(this);
			

		
			
			
			
		}
		public void onCharacterDeath() {
			 Game.heroes.remove(this);
			 ((CharacterCell) this.getCell()).setCharacter(null);
		}
		
	
}
