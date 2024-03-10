package model.characters;

import engine.Game;
import exceptions.GameActionException;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.collectibles.Supply;
import model.world.Cell;
import model.world.CharacterCell;

public class Explorer extends Hero {
	

	public Explorer(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
	}

	@Override
	public void useSpecial() throws GameActionException  {
		super.useSpecial();
		getSupplyInventory().get(getSupplyInventory().size() - 1).use(this);

		Cell.setAllCellsVisible();
		
	}

	public void attack ()throws InvalidTargetException, NotEnoughActionsException{
		super.attack();
	   	setActionsAvailable(getActionsAvailable()-1);

	}

	
	

	
}
