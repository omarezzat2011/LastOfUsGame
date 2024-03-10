package model.world;

import java.awt.Point;
import java.util.List;

import engine.Game;
import model.world.*;
import model.characters.Character;
import model.characters.Hero;
import model.collectibles.*;
public abstract class Cell {


	private boolean isVisible;
	
	public Cell() {
	
		isVisible = false;
	
	}
	public boolean isVisible() {
		
		return isVisible;
	
	}
	
	public void setVisible(boolean isVisible) {
	
		this.isVisible = isVisible;
	
	}
	public  Point getLocation() {
		for (int i = 0; i < Game.map.length; i++) {
	        for (int j = 0; j < Game.map[0].length; j++) {
	            if (this.equals(Game.map[i][j])) {
	            	Point p = new Point(i, j);
	            	return p;
	            }
	        }
	    }
	    //return null;
		return null;
	}
	
	public static boolean isAdjacent(Cell cell1, Cell cell2) {
	    int row1 = -1, col1 = -1, row2 = -1, col2 = -1;
	    int height = Game.map.length;
	    int width = Game.map[0].length;

	    for (int i = 0; i < height; i++) {
	        for (int j = 0; j < width; j++) {
	            if (Game.map[i][j] == cell1) {
	                row1 = i;
	                col1 = j;
	            }
	            if (Game.map[i][j] == cell2) {
	                row2 = i;
	                col2 = j;
	            }
	        }
	    }

	    if (row1 == row2 && Math.abs(col1 - col2) == 1) {
	        return true; // Cells are adjacent in the same row
	    } else if (col1 == col2 && Math.abs(row1 - row2) == 1) {
	        return true; // Cells are adjacent in the same column
	    } else if (Math.abs(row1 - row2) == 1 && Math.abs(col1 - col2) == 1) {
	        return true; // Cells are diagonally adjacent
	    }// else if (row1 == row2 && col1 == col2) {
	       // return true;
	    //}
	    return false;
	}
	public static boolean isValidPoint(Point newPosition) {
	    return newPosition.x >= 0 && newPosition.x < Game.map.length 
	           && newPosition.y >= 0 && newPosition.y < Game.map[0].length ;
	}
	public static void setAllAdjacentCellsVisible(Cell cellLocation) {
		int row = -1, col = -1;
	    int height = Game.map.length;
	    int width = Game.map[0].length;

	    // Find the row and column of the cell
	    for (int i = 0; i < height; i++) {
	        for (int j = 0; j < width; j++) {
	            if (Game.map[i][j] == cellLocation) {
	                row = i;
	                col = j;
	                break;
	            }
	        }
	    }
	    for (int i = row - 1; i <= row + 1; i++) {
	        for (int j = col - 1; j <= col + 1; j++) {
	        	Point p = new Point(i,j);
	            if (isValidPoint(p) && Game.map[i][j]!=null) {
	                Game.map[i][j].setVisible(true);
	            }
	        }
	    }
	}
	public static void main(String[] args) {
		Supply x = new Supply();
		CollectibleCell a = new CollectibleCell(x);
		Supply y = new Supply();
		CollectibleCell b = new CollectibleCell(x);
		Game.map[0][0]=a;
		Game.map[1][1]=b;
		System.out.println(isAdjacent(a, b));
		
		
	}
	public static void setAllCellsNotVisible() {
	    for (int i = 0; i < Game.map.length; i++) {
	        for (int j = 0; j < Game.map[i].length; j++) {
	        	if(Game.map[i][j]!=null)
	        		Game.map[i][j].setVisible(false);
	        }
	    }
	    }

	public static void setAllCellsVisible() {
	    for (int i = 0; i < Game.map.length; i++) {
	        for (int j = 0; j < Game.map[0].length; j++) {
	        		Game.map[i][j].setVisible(true);
	        }
	    }


	


	}
	}


