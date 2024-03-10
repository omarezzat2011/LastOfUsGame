package model.characters;
import engine.Game;
import exceptions.GameActionException;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.world.Cell;
import model.world.CharacterCell;
import model.collectibles.*;
public class Fighter extends Hero{

	
	public Fighter(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
	}
	
	/*
	public void attack() throws InvalidTargetException, NoAvailableResourcesException, NotEnoughActionsException {
		if(getTarget()==null)
			throw new InvalidTargetException("There is no target");
		Fighter f=new Fighter( getName(), getActionsAvailable(), getAttackDmg(), getMaxActions());
		CharacterCell a = new CharacterCell(this,true);
		int row1 = this.getLocation().x;
		int column1 = this.getLocation().y;
		CharacterCell b = new CharacterCell(this.getTarget(),true);
		Game.map[row1][column1]=a;

		int row2 = this.getTarget().getLocation().x;
		int column2 = this.getTarget().getLocation().y;
		Game.map[row2][column2]=b;
		
	    if (getTarget() != null && Cell.isAdjacent( a,b) && this.isSpecialAction()&&getTarget() instanceof Zombie){
	    	Supply supply = new Supply();
				supply.use(f);
	        int attackDamage = getAttackDmg();
	        	        
	            Zombie zombie = (Zombie) getTarget();
	             zombie.decreaseHealth((int)attackDamage);
	            
	    }
	        
	   	        else if (getTarget() != null && Cell.isAdjacent( a,b) && !this.isSpecialAction()&&getTarget() instanceof Zombie&&getActionsAvailable()>0){
	   		        int attackDamage = getAttackDmg();
	   		        
	   		            Zombie zombie = (Zombie) getTarget();
	   		             zombie.decreaseHealth((int)attackDamage);
	   		          setActionsAvailable(getActionsAvailable()-1);
	   		      
	   		    }
	   	        else if(getActionsAvailable()==0) {
	   	        	throw new NotEnoughActionsException("No enough actions");
	   	        }
	   	     else if(!(getTarget() instanceof Zombie)) {
	   	  	throw new InvalidTargetException("Target a zombie!");
	   	  }
	   	  

	    else if (!Cell.isAdjacent( a,b))
	    	throw new InvalidTargetException("Invalid target!");
	}


*/
	public void attack() throws InvalidTargetException, NotEnoughActionsException {
		super.attack();
		if(!isSpecialAction())
		   	setActionsAvailable(getActionsAvailable()-1);

			
	}

	



	@Override
	public void useSpecial() throws GameActionException {
		/*if (getSupplyInventory().isEmpty())
	        throw new NoAvailableResourcesException("There is no supplies left");
		else {
		*/
		super.useSpecial();
	    setSpecialAction(true);
		this.getSupplyInventory().get(getSupplyInventory().size()-1).use(this);
		
		//s.use(this);

	}
		}

	

	

	
	
	

