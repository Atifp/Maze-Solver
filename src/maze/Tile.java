package maze;
import java.io.Serializable;
/**Maze class consisting of methods to form the maze
* @author Atif
* @version 28th April 2021
*/
public class Tile implements Serializable{
	private Type type;
	private boolean beenThere = false;


	/**Type enum Class consisting of the constants for each Type
	* @author Atif
	*/
	public enum Type {
		CORRIDOR,ENTRANCE,EXIT,WALL;
	}

	/**Private tile constructor assigning the type 
	* @param typeIn : type that the tile represents
	*/
	private Tile(Type typeIn){
		this.type = typeIn;
	}

	/**protected class Converts the character to a Tile 
	* @param tileIn : character that the tile represents
	* @return returns a tile with a type corresponding to the character input or null
	*/
	protected static Tile fromChar(char tileIn){
		Tile tile;
		if(tileIn ==('x')){
			tile = new Tile(Type.EXIT);
			return tile;
		}
		else if(tileIn == ('e')){
			tile = new Tile(Type.ENTRANCE);
			return tile;
		}
		else if(tileIn == ('#')){
			tile = new Tile(Type.WALL);
			return tile;
		}
		else if(tileIn == ('.')){
			tile = new Tile(Type.CORRIDOR);
			return tile;
		}
		else{
			return null;
		}

	}

	/**Gets the type of a tile
	* @return returns the type of tile
	*/
	public Type getType(){
		return type;
	}

	/**Checks if the type is wall
	* @return returns false if the type is a wall else return true
	*/
	public boolean isNavigable(){
		if (this.type != Type.WALL){
			return true;
		}
		return false;
	}

	/**gets the value of beenThere for the specific tile
	* @return returns the variable beenThere
	*/
	public boolean getVisited(){
		return beenThere;
	}

	/**sets the value of beenThere to true for the specific file
	* @return returns the variable beenThere
	*/
	public boolean setVisited(){
		beenThere = true;
		return beenThere;
	}

	/**Converts Tiles to string
	* @return returns the String which corresponds to the tile type
	* or returns null
	*/
	public String toString(){
		if (type == Type.EXIT){
			return "x";
		}
		else if(type == Type.ENTRANCE){
			return "e";
		}
		else if(type == Type.WALL){
			return "#";
		}
		else if(type == Type.CORRIDOR){
			return ".";
		}
		else{
			return null;
		}
	}

}