package model.characters;

import engine.Game;
import exceptions.GameActionException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.Cell;
import model.world.CharacterCell;

public class Medic extends Hero {
	

	public Medic(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
		
	}
	@Override
	public void useSpecial() throws GameActionException {
		if (getTarget() instanceof Zombie) 
		    throw new InvalidTargetException("Cannot heal a zombie");
		
		if(!(Cell.isAdjacent(getCell(), getTarget().getCell()))) {
			for (int i = getLocation().x-1; i <=getLocation().x+1; i++) {
				for (int j = getLocation().y-1; j <=getLocation().y+1; j++) {
					if(i>=0 && i<=14 && j<=14 && j>=0) {
						if(Game.map[i][j] instanceof CharacterCell 
								&&((CharacterCell)Game.map[i][j] ).getCharacter() instanceof Hero)
						{
							setTarget(((CharacterCell)Game.map[i][j] ).getCharacter() );
						}
					}
				}
			}
		}
		if(!((Cell.isAdjacent(getCell(), getTarget().getCell())) ||  (getLocation().equals(getTarget().getLocation()))))
		    throw new InvalidTargetException("Cannot heal a not adjacent");
		
		super.useSpecial();
		getSupplyInventory().get(getSupplyInventory().size() - 1).use(this);
		this.getTarget().setCurrentHp(this.getTarget().getMaxHp());

			
		
	}
	public void attack ()throws InvalidTargetException, NotEnoughActionsException{
		super.attack();
	   	setActionsAvailable(getActionsAvailable()-1);

	}
	
	
	


}
