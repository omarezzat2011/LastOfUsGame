package model.collectibles;

import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import model.characters.*;
import model.characters.Character;
import model.world.CharacterCell;
import engine.Game;
public class Vaccine implements Collectible {

	public Vaccine() {
	}

	@Override
	public void pickUp(Hero h) {
		h.getVaccineInventory().add((this));
		
	}

	@Override
	

	public void use(Hero h) throws NoAvailableResourcesException, InvalidTargetException {
		if(h.getVaccineInventory().isEmpty()) 
			throw new NoAvailableResourcesException("NO Available Vaccines in your inventory");
		h.getVaccineInventory().remove(this);

		Hero newh = Game.availableHeroes.remove((Game.availableHeroes.size())-1);
		Game.zombies.remove(h.getTarget());
		Game.heroes.add(newh);
		CharacterCell hc = (CharacterCell) h.getTarget().getCell();
		hc.setCharacter(newh);
		Character.updateLocation(newh,hc);
		 
		newh.setTarget(null);	
	}

}
