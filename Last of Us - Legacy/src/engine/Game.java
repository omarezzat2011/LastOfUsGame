package engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.GameActionException;
import model.characters.*;
import model.characters.Character;
import model.world.*;
import model.collectibles.*;
public class Game {

	public static Cell [][] map =  new Cell [15][15] ;

	public static ArrayList <Hero> availableHeroes = new ArrayList<Hero>();
	public static ArrayList <Hero> heroes =  new ArrayList<Hero>();
	public static ArrayList <Zombie> zombies =  new ArrayList<Zombie>();
	public static ArrayList <Vaccine> vaccines = new ArrayList<Vaccine>();
	public static ArrayList <Vaccine> availableVaccines = new ArrayList<Vaccine>();




	public static void loadHeroes(String filePath)  throws IOException {


		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Hero hero=null;
			switch (content[1]) {
			case "FIGH":
				hero = new Fighter(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3]));
				break;
			case "MED":  
				hero = new Medic(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3])) ;
				break;
			case "EXP":  
				hero = new Explorer(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3]));
				break;
			}
			availableHeroes.add(hero);
			line = br.readLine();


		}
		br.close();



	}
	public static boolean isVaccineLeft() {
		boolean flag = false;
		for (Hero hero : heroes) {
			if(!hero.getVaccineInventory().isEmpty())
				flag=true;

		}
		for (int i = 0; i < Game.map.length; i++) {
			for (int j = 0; j < Game.map[0].length; j++) {
				if((Game.map[i][j] instanceof CollectibleCell)) {
					if(((CollectibleCell) Game.map[i][j]).getCollectible() instanceof Vaccine)
						flag=true;

				}


			}
		}




		return flag;


	}


	public static void startGame(Hero h) {
		/*map =  new Cell [15][15];
		for(int i=0; i<map.length;i++) {
			for(int j=0;j<map[i].length;j++) {
				if(map[i][j]==null) {
					map[i][j] = new CharacterCell(null);
			}
		}
		}*/
		//|| (map[x][y] instanceof TrapCell)
		int x,y;
		int countVacc=0;

		while(countVacc!=5){
			do {
				x = ((int) (Math.random() * map.length));
				y = ((int) (Math.random() * map[x].length));
			} while (map[x][y] != null
					&&(!(x==0 && y==0))
					&& (((map[x][y]instanceof CharacterCell) &&(((CharacterCell) map[x][y]).getCharacter() != null))
							|| (map[x][y]instanceof CollectibleCell&& ((CollectibleCell) map[x][y]).getCollectible() != null))
							);
			Vaccine v= new Vaccine();
			CollectibleCell vc = new CollectibleCell(v);
			map[x][y]=vc;
			countVacc++;
		}
		int countSupp=0;
		while(countSupp!=5){
			do {
				x = ((int) (Math.random() * map.length));
				y = ((int) (Math.random() * map[x].length));
			} while (map[x][y] != null
					&&(!(x==0 && y==0))
					&& (((map[x][y]instanceof CharacterCell) &&(((CharacterCell) map[x][y]).getCharacter() != null))
							|| (map[x][y]instanceof CollectibleCell && ((CollectibleCell) map[x][y]).getCollectible() != null))
							/*|| (map[x][y] instanceof TrapCell)*/);
			Supply s = new Supply();
			CollectibleCell sc = new CollectibleCell(s);
			map[x][y]=sc;
			countSupp++;
		}
		for(int i=0;i<5;i++){
			do {
				x = ((int) (Math.random() * map.length));
				y = ((int) (Math.random() * map[x].length));
			} while (map[x][y] != null
					&&(!(x==0 && y==0))
					&& (((map[x][y]instanceof CharacterCell) &&(((CharacterCell) map[x][y]).getCharacter() != null))
							|| (map[x][y]instanceof CollectibleCell&& ((CollectibleCell) map[x][y]).getCollectible() != null))
							|| (map[x][y] instanceof TrapCell));
			TrapCell tc = new TrapCell();
			map[x][y]=tc;
		}

		for(int i=0;i<10;i++){
			do {
				x = ((int) (Math.random() * map.length));
				y = ((int) (Math.random() * map[x].length));
			} while (map[x][y] != null
					&&(!(x==0 && y==0))
					&& (((map[x][y]instanceof CharacterCell) &&(((CharacterCell) map[x][y]).getCharacter() != null))
							|| (map[x][y]instanceof CollectibleCell)
							|| (map[x][y] instanceof TrapCell)));
			Zombie z = new Zombie();
			CharacterCell zc = new CharacterCell(z,true);
			map[x][y]=zc;
			zombies.add(z);
			Character.updateLocation(z);
		}
		for(int i=0; i<map.length;i++) {
			for(int j=0;j<map[i].length;j++) {
				if(map[i][j]==null) {
					map[i][j] = new CharacterCell(null);
			}
		}
		}
		if(countSupp<5) {
			do {
				x = ((int) (Math.random() * map.length));
				y = ((int) (Math.random() * map[x].length));
			} while (map[x][y] != null
					&&(!(x==0 && y==0))
					&& (((map[x][y]instanceof CharacterCell) &&(((CharacterCell) map[x][y]).getCharacter() != null))
							|| (map[x][y]instanceof CollectibleCell && ((CollectibleCell) map[x][y]).getCollectible() != null))
							|| (map[x][y] instanceof TrapCell));
			Supply s = new Supply();
			CollectibleCell sc = new CollectibleCell(s);
			map[x][y]=sc;
			
		}
		if(countVacc<5) {
			do {
				x = ((int) (Math.random() * map.length));
				y = ((int) (Math.random() * map[x].length));
			} while (map[x][y] != null
					&&(!(x==0 && y==0))
					&& (((map[x][y]instanceof CharacterCell) &&(((CharacterCell) map[x][y]).getCharacter() != null))
							|| (map[x][y]instanceof CollectibleCell&& ((CollectibleCell) map[x][y]).getCollectible() != null))
							|| (map[x][y] instanceof TrapCell));
			Vaccine v= new Vaccine();
			CollectibleCell vc = new CollectibleCell(v);
			map[x][y]=vc;
			//countVacc++;
		}

		CharacterCell hc = new CharacterCell(h,true);
		map[0][0]=hc;
		Character.updateLocation(h);
		hc.setVisible(true);

		availableHeroes.remove(h);
		heroes.add(h);
		Cell.setAllAdjacentCellsVisible(hc);

	}
	public static boolean checkWin() {	    
		if (!isVaccineLeft()&& (heroes.size() >= 5)) 
			return true; // Player has won

		return false; // Player has not won yet
	}
	public static boolean checkGameOver() {
		if ( heroes.isEmpty()) 
			return true;
		if ( availableHeroes.isEmpty()&&heroes.size()<=5) 
			return true;
		if (!isVaccineLeft() ) 
			return true;
		return false;
	}
	public static void endTurn() throws GameActionException{
		for(int i=0; i<map.length;i++) {
			for(int j=0;j<map[i].length;j++) {
				if((map[i][j] instanceof CharacterCell) 
					&&(((CharacterCell)map[i][j]).getCharacter() instanceof Zombie)
					&&((Zombie) ((CharacterCell)map[i][j]).getCharacter())!=null) {
					((Zombie) ((CharacterCell)map[i][j]).getCharacter()).attack();
			}
		}
		}
		
		for (Zombie zombie : zombies) {
		//	zombie.attack();
			zombie.setTarget(null);
		}
		
		
		if(zombies.size()<=10) {
			int x,y;
			do {
				x = ((int) (Math.random() * map.length));
				y = ((int) (Math.random() * map[x].length));
			} while (map[x][y] != null
					&& ((map[x][y]instanceof CharacterCell) &&(((CharacterCell) map[x][y]).getCharacter() != null))
					|| (map[x][y]instanceof CollectibleCell)
					|| (map[x][y] instanceof TrapCell));
			Zombie z = new Zombie();
			CharacterCell zc = new CharacterCell(z);
			map[x][y]=zc;
			zombies.add(z);
			Character.updateLocation(z);


		}
		//for (Hero hero : heroes) 
			//hero.reset();
		for(int i=0; i<map.length;i++) {
			for(int j=0;j<map[i].length;j++) {
				if(map[i][j] instanceof CharacterCell &&((CharacterCell)map[i][j]).getCharacter() instanceof Hero) {
					((Hero) ((CharacterCell)map[i][j]).getCharacter()).reset();
			}
		}
		}
		Cell.setAllCellsNotVisible();
		for (Hero hero : heroes) 
			Cell.setAllAdjacentCellsVisible(hero.getCell());

		

	}
	
}
