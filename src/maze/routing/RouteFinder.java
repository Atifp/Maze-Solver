package maze.routing;

import maze.*;
import java.util.List;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.*;

/**RouteFinder class consisting of methods to help solve a maze
* @author Atif
* @version 28th April 2021
*/
public class RouteFinder implements Serializable{

    private Maze maze;
    private Stack<Tile> route;
    private boolean finished = false;;

    /**Contructor of RouteFinder which assigns the parameter to the maze attribute 
    * and instantiates a stack called route
    * @param m this is object of the type maze
    */
    public RouteFinder(Maze m) {
    	this.maze = m;
    	route = new Stack<Tile>();
    }
    
    /** A method to get the maze
    * @return returns an object of Maze
    */
    public Maze getMaze() {
        return maze;
    }

    /** A method which gets the route from the stack
    * @return returns a list of tiles which is equivalent to the current stack
    */
    public List<Tile> getRoute() {
        List<Tile> list = new ArrayList<Tile>();
        while(!route.isEmpty()){
        	list.add(route.pop());

        }
        Collections.reverse(list);
        route.addAll(list);
        return list;
    }
    
    /** A method which checks if the maze has been solved
    * @return returns the boolean variable isFinished
    */
    public boolean isFinished() {
        return finished;
    }
    
    /** A method to load the current state of the maze
    * @throws IOException if the file is invalid
    * @throws ClassNotFoundException if the class is not found
    * @throws StreamCorruptedException if the file is invalid type
    * @param mazeName this is the name of the maze file that needs to be loaded 
    */
    public static RouteFinder load(String mazeName)throws IOException, ClassNotFoundException,StreamCorruptedException{
    	try
        {   
	        // Reading the object from a file
	        FileInputStream mazeFile = new FileInputStream(mazeName);
	        ObjectInputStream input = new ObjectInputStream(mazeFile);
	          
	        // Method for deserialization of object
	       RouteFinder object1 = (RouteFinder)input.readObject();
	       
	        input.close();
	        mazeFile.close();
	        return object1;
              
        }
        catch(StreamCorruptedException ex){
            System.out.println("Invalid file read");
        } 
        catch(IOException ex)
        {
            
            ex.printStackTrace();
        }
          
        catch(ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    /** A method to save the current state of the maze
    * @throws IOException if the file is invalid
    * @throws NullPointerException if the file being saved is null
    * @param mazeName this is the name of the file in which the maze needs to be saved
    */
    public void save(String mazeName) throws IOException, NullPointerException{
    	
    	RouteFinder rt = new RouteFinder(this.getMaze());
    	rt.route = route;
    	try
        {   
            //Saving of object in a file
            FileOutputStream mazeFile = new FileOutputStream(mazeName);
            ObjectOutputStream output = new ObjectOutputStream(mazeFile);
              
            // Method for serialization of object
            output.writeObject(this);
              
            output.close();
            mazeFile.close();
              
            System.out.println("Maze has been saved");
  
        }
        catch(NullPointerException ex)
        {
            System.out.println("NullPointerException is caught");
            ex.printStackTrace();
        } 
        catch(IOException ex)
        {
            System.out.println("IOException is caught");
            ex.printStackTrace();
        }

	}

	/** A method which steps through the maze until it is solved with the use of a stack
    * @throws NoRouteFoundException if there is no possible route to the exit then the exception is thrown
    * @return the method will return true when the exit tile is found
    * the method will return false for all other outcomes 
    */
    public boolean step()throws NoRouteFoundException{

    	Tile exit = maze.getExit();
        // checks if stack is empty
    	if(route.empty() == true){
            // if stack is empty push the entrance tile
    		Tile entrance = maze.getEntrance();
    		entrance.setVisited();
    		route.push(entrance);
    		return false;
    	}

        // if stack is not empty
    	if(route.empty() != true){
            // get the tile at the top of the stack
    		Tile currentTile = route.peek();
            // get the tiles in all directions
    		Tile north = maze.getAdjacentTile(currentTile, Maze.Direction.NORTH);
    		Tile east = maze.getAdjacentTile(currentTile, Maze.Direction.EAST);
    		Tile south = maze.getAdjacentTile(currentTile, Maze.Direction.SOUTH);
    		Tile west = maze.getAdjacentTile(currentTile, Maze.Direction.WEST);

            // check if exit is found
    		if(isFinished() == true){
    			return false;
    		}
    		else{
                // check whether it is null and if its already been visited
	    		if(south != null && south.getVisited() == false){
                    // check if its an exit type
	    			if(south.getType() == Tile.Type.EXIT){
                        // push to the stack
	    				route.push(south);
                        // getVisited returns true
	    				south.setVisited();
                        // is finished is true 
	    				finished = true;
                        //step returns true
	    				return true;
	    			}
	    			
                    // if its not exit, check if its a corridor
	    			else if(south.getType() == Tile.Type.CORRIDOR){
                        // push to stack
	    				route.push(south);
                        // getVisited returns true
	    				south.setVisited();
                        // return false
	    				return false;
	    			}
	    		}
	    		
	    		if(east != null && east.getVisited()==false){
	    			if(east.getType() == Tile.Type.EXIT){
	    				route.push(east);
	    				east.setVisited();
	    				finished = true;
	    				return true;
	    			}
	    			
	    			else if(east.getType() == Tile.Type.CORRIDOR){
	    				route.push(east);
	    				east.setVisited();
	    				return false;
	    			}
	    			
	    		}
	    		
	    		if(north != null &&  north.getVisited() == false){
	    			if(north.getType() == Tile.Type.EXIT){
	    				route.push(north);
	    				north.setVisited();
	    				finished = true;
	    				return true;
	    			}
	    			
	    			else if(north.getType() == Tile.Type.CORRIDOR){
	    				route.push(north);
	    				north.setVisited();
	    				return false;
	    			}
	    		}
	    		
	    		if(west != null &&  west.getVisited()== false){
	    			if(west.getType() == Tile.Type.EXIT){
	    				route.push(west);
	    				west.setVisited();
	    				finished = true;
	    				return true;
	    			}
	    			
	    			else if(west.getType() == Tile.Type.CORRIDOR){
	    				route.push(west);
	    				west.setVisited();
	    				return false;
	    			}
	    		}
                // if none of the if statements satisfied and route does not contain exit
	    		if(route.contains(exit) !=true){
                    // pop the top of the stack
	    			route.pop();
	    		}
	    		
                // if the route is empty, throw no route found exception
	    		if(route.empty() == true){
	    			throw new NoRouteFoundException("No route found");
	    		}
	    	}
	    }
        return false;
    }

    /** This method takes the maze and forms a string in a specific format
    * @return a string will be returned with the current maze and the steps taken
    */
    public String toString() {
        String mazeTxt = "";
        int tileSize = this.getMaze().getTiles().size() -1 ;
       	for(int i=0; i<= tileSize;i++){
            // - for the path visited 
       		for(int j=0; j<this.getMaze().getTiles().get(i).size(); j++){
       			if(route.contains(this.getMaze().getTiles().get(tileSize-i).get(j)) == false && this.getMaze().getTiles().get(tileSize-i).get(j).getVisited() == true ){
       				mazeTxt = mazeTxt + "- " ;
       			}
                // * for the path that is in the route 
       			else if(route.contains(this.getMaze().getTiles().get(tileSize-i).get(j)) == true){
       				mazeTxt = mazeTxt + "* " ;
       			}
                // symbol of tile for anything else
       			else{
       				mazeTxt = mazeTxt + this.getMaze().getTiles().get(tileSize-i).get(j).toString() + " ";
       			}
       		}
            // at the end of the row do a line break
       		mazeTxt = mazeTxt + "\n";
       	}
       	return mazeTxt;
    }
}