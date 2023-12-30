import maze.*;
import maze.routing.*;
import java.io.IOException;
import java.io.*;

public class MazeDriver implements Serializable{

    public static void main(String args[])throws IOException, InvalidMazeException, ClassNotFoundException, NoRouteFoundException{
    	
    	Maze maze = Maze.fromTxt("/home/csimage/comp16412-coursework-2_c44054ap/resources/mazes/maze2.txt");
    	System.out.println(maze.toString());
    	//Maze.Coordinate c = maze.new Coordinate(0,5);
    	// Tile t = maze.getTileAtLocation(c);
     //    System.out.println(t);
        RouteFinder rt = new RouteFinder(maze);

        //rt.getMaze().setExit(t);
        //maze.setExit(t);
        //Maze.Direction east = Maze.Direction.EAST;
    	//System.out.println(maze.getTileAtLocation(c));
    	//System.out.println(maze.getTileLocation(t));
     //    System.out.println(maze.getAdjacentTile(t,east));
        //RouteFinder rt = new RouteFinder(maze);
        while(rt.step() == false){
           if(rt.step() == true){
            break;
           }
        }
        //System.out.println(rt.getMaze());
        System.out.println(rt.toString());
        //System.out.println(rt.getRoute());
        //System.out.println("Deserialised object");
        //rt.save("test2.txt");
        //System.out.println(rt.load("test2.txt"));
        

    }
}
