package maze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.*;
import java.io.Serializable;

/**Maze class consisting of methods to form the maze
* @author Atif
* @version 28th April 2021
*/
public class Maze implements Serializable{
	// attributes
	private Tile entrance;
	private Tile exit;
	/**Object of tile class used in various methods */
	public Tile t;
	//list of arraylists consisting of tiles
	private List<List<Tile>> tiles = new ArrayList<List<Tile>>();
	
	/**Constructor for maze that is used to call other methods and assigns the entrance and exit*/
	private Maze(){
		this.entrance = entrance;
		this.exit = exit;
	}

	/**Coordinate class consisting of methods for coordinates
	* @author Atif
	*/
	public class Coordinate{
		private int x;
		private int y;

		/**Constructor which assigns the x and y objects
		* @param inX input of the x coordinate
		* @param inY input of the y coordinate
		*/
		public Coordinate(int inX, int inY){
			this.x = inX;
			this.y = inY;
		}

		/**Gets the x value
		* @return returns the object x
		*/
		public int getX(){
			return x;
		}

		/**Gets the y value
		* @return returns the object y
		*/
		public int getY(){
			return y;
		}

		/**This method returns the coords in a certain format
		* @return returns the coords x and y in a certain format as a String
		*/
		public String toString(){
			String coordString = "";
			int x = getX();
			int y = getY();
			coordString = "(" + String.valueOf(x) + ", " + String.valueOf(y) + ")";
			return coordString;
		}
	}

	/**Direction enum Class consisting of the constants for each direction
	* @author Atif
	*/
	public enum Direction{
		NORTH,EAST,SOUTH,WEST;
	}

	/**Reads a file character by character and appends to the tiles variable
	* sets the entrance and exit tiles and uses the tile class to convert from characters to tiles
	* returning a maze
	* @param mazeName name of the maze file
	* @throws IOExeption if the file is not found
	* @throws InvalidMazeException covers multiple exceptions that arise when forming the maze 
	*such as no entrance,no exit and multiple entrance or exits
	* @return returns an object of the maze
	*/
	public static Maze fromTxt(String mazeName)throws IOException, InvalidMazeException{
		// counters
		int row = 0;
		int rowi = 0;
		int enter = 0;
		int leave = 0;
		// new maze object
		Maze maze = new Maze();
		// open and read file
		try(
			BufferedReader br = new BufferedReader(new FileReader(mazeName));
			BufferedReader br2 = new BufferedReader(new FileReader(mazeName));
		)
		{
			// read line by line
			String line2 = br2.readLine();
			// read until file ends
			while(line2 != null){
				row ++;
				line2 = br2.readLine();
			}
			// create empy lists for number of rows
			for(int j=0; j< row;j++){
				maze.tiles.add(new ArrayList<Tile>());
			}
			String line = br.readLine();
			// read until file ends
			rowi = row -1;
			while(line != null){
        		// for each character in the file
        		for(int j=0;j<line.length();j++){
        			// get each character
        			char character = line.charAt(j);
        			// check what they are
	        		if(character == ('e')){
	        			enter +=1;
						Tile newidk = Tile.fromChar(character);
						if(newidk != null){
							maze.tiles.get(rowi).add(newidk);
							maze.setEntrance(newidk);
						}
						
					}
					else if(character == ('#')){
						Tile newidk = Tile.fromChar(character);
						if(newidk != null){
							maze.tiles.get(rowi).add(newidk);
						}	
					}
					else if(character == ('.')){
						Tile newidk = Tile.fromChar(character);
						if(newidk != null){
							maze.tiles.get(rowi).add(newidk);
						}
						
					}
					else if(character == ('x')){
						leave +=1;
						Tile newidk = Tile.fromChar(character);
						if(newidk != null){
							maze.tiles.get(rowi).add(newidk);
							maze.setExit(newidk);
						}	
							
					}
					else{
						// throw exception if the character is invalid
						throw new InvalidMazeException("Invalid Tile");
					}
					

        		}
        		// when u reach end of line
        		// decrement counter
        		rowi -=1;
        		
        		// go to the next line
        		line = br.readLine();
        	}
        	// if the maze does not meet the requirements, throw an exception
			if(maze.tiles.get(0).size() <=2){
				throw new InvalidMazeException("Invalid Maze given");
			}
			boolean checkEntrance = false;
			for(int i=0; i<maze.tiles.size(); i++){
				for(int j=0; j< maze.tiles.get(i).size(); j++){
					if(maze.tiles.get(i).get(j).getType() == Tile.Type.ENTRANCE){
						checkEntrance = true;
					}
				}
			}
			// if the entrance tile hasnt been found, throw exception
			if(checkEntrance == false){
				throw new NoEntranceException("No entrance found");
			}
			
			boolean checkExit = false;
			for(int i=0; i<maze.tiles.size(); i++){
				for(int j=0; j< maze.tiles.get(i).size(); j++){
					if(maze.tiles.get(i).get(j).getType() == Tile.Type.EXIT){
						checkExit = true;
					}
				}
			}
			// if the exit tile hasnt been found, throw exception
			if(checkExit == false){
				throw new NoExitException("No Exit found");
			}
	
			// if the rows of the mazes arent equal throw an exception
			for(int i=0; i<maze.tiles.size()-1;i++){
				if(maze.tiles.get(i).size() != maze.tiles.get(i+1).size()){

					throw new RaggedMazeException("Invalid Maze layout");
				}
			}

        } catch (FileNotFoundException e) {
             System.out.println("Error: Could not open " + mazeName);
             e.printStackTrace();
            

        } catch (IOException e) {
             System.out.println("Error: IOException when reading "+ mazeName);
             e.printStackTrace();
            
        }
        return maze;
    }

    /**Uses the given tile and gets the tile in the direction given
    * @param t The tile that the user is currently on
    * @param way The direction the user wants to move
	* @return returns the tile that is in the place coinciding with the direction
	* if the direction is not a valid direction, current tile returned
	* if the tile is out of bounds, then return null
	*/

	public Tile getAdjacentTile(Tile t,Direction way){

		// gets the coordinate of the current tile
		Coordinate moveTile = getTileLocation(t);
		// getsthe array of tiles
		List<List<Tile>> tiles = getTiles();
		// gets the x and y coords
		int x = moveTile.getX();
		int y = moveTile.getY();
		int rowSize = tiles.size() -1;
		int columnSize = tiles.get(0).size() -1;
			//if statements for each direction
			if(way == Direction.NORTH){
				Coordinate north = new Coordinate(x,y+1);
				// if the coords are outside the maze,return null
				if(y+1 > rowSize){
					return null;
				}else{
					// return a tile in the direction specified
					Tile northWay = getTileAtLocation(north);
					return northWay;
				}
			}
			if(way == Direction.EAST){
				Coordinate east = new Coordinate(x+1,y);
				if(x+1 > columnSize){
					return null;
				}else{
					Tile eastWay = getTileAtLocation(east);
					return eastWay;
				}
			}
			if(way == Direction.SOUTH){
				Coordinate south = new Coordinate(x,y-1);
				if(y-1 < 0){
					return null;
				}else{
					Tile southWay = getTileAtLocation(south);
					return southWay;
				}
			}
			if(way == Direction.WEST){
				Coordinate west = new Coordinate(x-1,y);
				if(x-1< 0){
					return null;
				}else{
					Tile westWay = getTileAtLocation(west);
					return westWay;
				}
			}
		return t;
	}

    /**Gets the entrance tile of the maze
	* @return returns a tile which is the entrance
	*/
	public Tile getEntrance(){
		return entrance;
	}

	/**Gets the exit tile of a maze
	* @return returns a tile which is the exit
	*/
	public Tile getExit() {
		return exit;
	}

	/**Gets the tile given a location
	* @param coord This represents an object of Coordinate class
	* @return returns a tile on the input coord
	*/
	public Tile getTileAtLocation(Coordinate coord){
		List<List<Tile>> tiles = getTiles();
		int tileSize = tiles.size()-1;
		int y = coord.getY();
		int x = coord.getX();
		//uses the input coords to get the specified tile
		Tile coordTile = tiles.get(y).get(x);
		// returns the tile at the coords
		return coordTile;
	}

	/**Gets the location of a tile
	* @param t This represents an object of the tile class
	* @return returns the coordinate of the input tile or null
	*/
	public Coordinate getTileLocation(Tile t){
		//creates a coords objects
		Coordinate tileCoord;
		List<List<Tile>> tiles = getTiles();
		//ints for the for loop
		int tilecheck = tiles.size()-1;
		int tileSize = tiles.size();
		int columnSize = tiles.get(0).size();
		// for loop matching the tile to the input tile
		for(int i=0;i<tileSize;i++){
			for(int j=0;j<columnSize;j++){
				if(t == tiles.get(i).get(j)){
					// instantiates the coords with the i and j
					tileCoord = new Coordinate(j,i);
					return tileCoord;
				}
				
			}
			
		}
		return null;
	}

	/**Gets the 2d arrayList of tiles
	* @return returns the 2d arraylist of tiles 
	*/
	public List<List<Tile>> getTiles(){
		return tiles;
	}

	/**Sets the entrance tile
	* @param e  Entrance tile
	* @throws MultipleEntranceException for multiple entrances found and no entrance found
	*/
	private void setEntrance(Tile e) throws MultipleEntranceException{
		// checks if entrance is null
		if(this.entrance == null){
			// checks if the type is what is needed
			if(e.getType() == Tile.Type.ENTRANCE){
				//checks that the tile is actually in the maze
				for(int i=0; i<getTiles().size();i++){
					for(int j=0; j<getTiles().get(i).size();j++){
						if(e == getTiles().get(i).get(j)){
							// sets entrance
							this.entrance = e;
						}
					}
				}
			}
		}else{
			throw new MultipleEntranceException("Multiple Entrances found");
		}
		
	}

	/**Sets the Exit tile
	* @param ex  Exit tile
	* @throws MultipleExitException for multiple exits found and no exits found
	*/
	private void setExit(Tile ex)throws MultipleExitException{
		// checks if exit is null
		if(this.exit == null){
			// make sure the type is of exit
			if(ex.getType() == Tile.Type.EXIT){
				boolean x = true;
				// for loop to make sure tile is in the maze
				for(int i=0; i<getTiles().size();i++){
					for(int j=0; j<getTiles().get(i).size();j++){
						if(ex == getTiles().get(i).get(j)){
							//sets exit
							this.exit = ex;
						}
					}
				}
			}
		}else{
			throw new MultipleExitException("Multiple Exits found");
		}
	}

	

	/**Converts a maze to a String
	* @return Returns a string version of the maze
	* or it will return an empty string
	*/
	public String toString(){
		String txt = "";
		List<List<Tile>> tiles = getTiles();
	 	int tileSize = tiles.size();
	 	int tileSize2 = tiles.size() -1;
	 	int columnSize = tiles.get(0).size();
	 	String mazeTxt = "";
	 	for(int j=0; j<=tileSize; j++){
	 		if(j == tileSize){
	 			mazeTxt = mazeTxt + "\n" + "\n"+ "    ";
	 			for(int l=0; l<columnSize; l++){
	 				if(l<10){
	 					mazeTxt = mazeTxt + String.valueOf(l) + "  ";
	 				}else{
	 					if(l>=10){
	 						mazeTxt = mazeTxt + String.valueOf(l) + " ";
	 					}
	 				}
	 			}
    			return mazeTxt;
	 		}
	 		mazeTxt = mazeTxt +"\n" + tiles.get(tileSize2 -j).toString().replace(",", " ")  //remove the commas
    		.replace("[[", "" + String.valueOf(tileSize -j-1) + "   ")  //remove the right bracket
    		.replace("[", "" + String.valueOf(tileSize -j-1) + "   ")  //remove the right bracket
    		.replace("]", "" + "\n")  //remove the left bracket
    		.trim();
	 	}
    	return txt;
	}

}