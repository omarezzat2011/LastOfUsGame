package model.collectibles;

import engine.Game;
import exceptions.NoAvailableResourcesException;
import model.characters.Hero;

public class Supply implements Collectible  {

	

	
	public Supply() {
		//Game.suppLeft++;
		
	}

	@Override
	public void pickUp(Hero h) {
		h.getSupplyInventory().add((this));
		
	}

	@Override
	public void use(Hero h) throws NoAvailableResourcesException {
		if(h.getSupplyInventory().isEmpty())
			throw new NoAvailableResourcesException("NO Available Supplies in your inventory");

			
		h.getSupplyInventory().remove(this);
		h.setSpecialAction(true);
	}



	
		
		

}
