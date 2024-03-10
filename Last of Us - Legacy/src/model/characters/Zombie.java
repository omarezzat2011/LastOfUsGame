package model.characters;
import exceptions.*;
import model.world.*;

import engine.Game;


public class Zombie extends Character {
	static int ZOMBIES_COUNT = 1;

	public Zombie() {
		super("Zombie " + ZOMBIES_COUNT, 40, 10);
		ZOMBIES_COUNT++;
	}
	public void resetHp() {
		this.setCurrentHp(40);
	}
	public void setTarget(Character target) {
		super.setTarget(target);
	}


	public void attack()throws InvalidTargetException, NotEnoughActionsException{
		if(getTarget()==null) {
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

		if(getTarget()!=null ){//&& Hero.isValidPoint(getTarget().getLocation())) {
			getTarget().defend(this);
			getTarget().decreaseHealth(getAttackDmg());
		}

	}
	@Override
	public void onCharacterDeath() {
		Game.zombies.remove(this);
		((CharacterCell) this.getCell()).setCharacter(null);

		int x,y;
		do {
			x = ((int) (Math.random() * Game.map.length));
			y = ((int) (Math.random() * Game.map[x].length));
		} while (Game.map[x][y] != null
				&& ((Game.map[x][y]instanceof CharacterCell) &&(((CharacterCell) Game.map[x][y]).getCharacter() != null))
				|| (Game.map[x][y]instanceof CollectibleCell)
				|| (Game.map[x][y] instanceof TrapCell));
		Zombie z = new Zombie();
		CharacterCell zc = new CharacterCell(z,true);
		Game.map[x][y]=zc;
		Game.zombies.add(z);
		Character.updateLocation(z);
	}




}




