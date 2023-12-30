package maze.visualisation;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.*;
import maze.*;
import maze.routing.*;
import javafx.scene.shape.*;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import java.util.*;
import java.io.*;
import javafx.scene.control.Label;
import java.awt.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.FontWeight;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;

/**MazeVisualise class consisting of methods to help woth reusable components
* @author Atif
* @version 28th April 2021
*/
public class MazeVisualise{
	private Maze maze;
	private RouteFinder rt;

	/**empty constructor to make instances of the class
	*/
	public void MazeVisualise(){

	}
	/**newButton method to create buttons with a specific name
	* @param buttonName this specifies a string which is the name of the button
	* @return returns a button with a specific size and adjusted font
	*/
	public Button newButton(String buttonName){
		Button newbtn = new Button(buttonName);

	 	if(buttonName.equals("Step")){
			newbtn.setPrefWidth(220);
	 		newbtn.setPrefHeight(60);
	 		newbtn.setFont(Font.font("Arial",FontWeight.BOLD,20));
		 	return newbtn;
	 	}

	 	else if(buttonName.equals("Exit Maze")){
	 		newbtn.setPrefWidth(200);
	 		newbtn.setPrefHeight(70);
	 		newbtn.setFont(Font.font("Arial",FontWeight.BOLD,20));
	 		return newbtn;

	 	}else {
	 		newbtn.setPrefWidth(175);
		 	newbtn.setPrefHeight(60);
		 	newbtn.setFont(Font.font("Arial",FontWeight.BOLD,16));
		 	return newbtn;
	 	}
	}

	/**newLabel method to create labels with a specific name
	* @param labelName this specifies a string which is the name of the label
	* @return returns a label with a specific font and size
	*/
	public Label newLabel(String labelName){
		Label newLabel = new Label(labelName);
		newLabel.setFont(Font.font("serif", FontWeight.BOLD,30));
		return newLabel;
	}

	/**loadMapAct method which specifies the load functionality of a button
	* @param pane this is the GridPane that will be filled with rectangles showing the maze
	* @param stage this is the stage which is used to display the gridpane, used for the FileChooser 
	* @throws IOException if the file is invalid
	* @throws InvalidMazeException covers multiple exceptions that arise when forming the maze
	*/
	public void loadMapAct(GridPane pane,Stage stage){
		FileChooser fileChooser2 = new FileChooser();
        fileChooser2.setTitle("Load Map");
        File file2 = fileChooser2.showOpenDialog(stage);
        if(file2 != null){
	        try {
 				pane.getChildren().clear();
	 			maze = Maze.fromTxt(file2.getPath());
	 			rt = new RouteFinder(maze);
	 			List<List<Tile>> tiles = maze.getTiles();
	 			int row = tiles.size() -1;
	 			int rowi = tiles.size();
	 			int column = tiles.get(0).size();
	 			for (int i=0; i<tiles.size(); i++) {
			      	for (int j=0; j<tiles.get(row-i).size(); j++) {

				        Rectangle r = new Rectangle(45,45);
				        if(tiles.get(row-i).get(j).getVisited() == true){
				        	r.setFill(Color.ORANGE);
				            pane.add(r,j,i);
				        }
				        else if (tiles.get(row-i).get(j).getType() == Tile.Type.WALL) {
				            r.setFill(Color.BLACK);
				            pane.add(r,j,i);
				        }
				        else if (tiles.get(row-i).get(j).getType() == Tile.Type.CORRIDOR){
			             r.setFill(Color.GREY);
			             pane.add(r,j,i);
				        }
				        else if(tiles.get(row-i).get(j).getType() == Tile.Type.EXIT){
				        	r.setFill(Color.GREEN);
				             pane.add(r,j,i);
				        }
				        else if(tiles.get(row-i).get(j).getType() == Tile.Type.ENTRANCE){
				             r.setFill(Color.GREY);
				             pane.add(r,j,i);
				        }
				        
			        }
	     
	    		}

	        } catch (IOException ex) {
	            Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Error,invalid save");
        		alert.setHeaderText(null);
        		alert.setContentText(ex.toString() + "\n Try Again");
        		alert.showAndWait();
	        } catch (InvalidMazeException ex) {
	            Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Error Invalid Maze");
        		alert.setHeaderText(null);
        		alert.setContentText(ex.toString() + "\n Try Again");
        		alert.showAndWait();
	        }
	    }
	}

	/**saveRouteAct method which specifies the save functionality of a button to save a RouteFinder object
	* @param stage this is the stage which is used for the FileChooser 
	* @throws IOException if the file is invalid
	* @throws NullPointerException if the file is null
	*/
	public void saveRouteAct(Stage stage){
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Route");
        File file = fileChooser.showSaveDialog(stage);
        if(file != null){
        	try {
     
           	rt.save(file.getPath());
           	

        	} catch (IOException ex) {
            	Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Error");
        		alert.setHeaderText(null);
        		alert.setContentText(ex.toString() + "\n Try Again");
        		alert.showAndWait();
        	} catch (NullPointerException ex){
        		Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Error,invalid save");
        		alert.setHeaderText(null);
        		alert.setContentText(ex.toString() + "\n Try Again");
        		alert.showAndWait();
        	}
        }   
	}
	
	/**loadRouteAct method which specifies the load functionality of a button to load a RouteFinder object
	* @param stage this is the stage which is used to display the gridpane and is used for the FileChooser 
	* @param pane this is the GridPane that will be filled with rectangles showing the maze
	* @throws IOException if the file is invalid
	* @throws NullPointerException if the file is null
	* @throws ClassNotFoundException if the class is not found
	* @throws StreamCorruptedException if the file is invalid type
	*/
	public void loadRouteAct(GridPane pane,Stage stage){

		FileChooser fileChooser1 = new FileChooser();
        fileChooser1.setTitle("Load Route");
        File file1 = fileChooser1.showOpenDialog(stage);
        if(file1 != null){
	        try {

	        	pane.getChildren().clear();
		        rt = rt.load(file1.getPath());
		        maze = rt.getMaze();
		        List<List<Tile>> tiles = maze.getTiles();
		        int row = tiles.size() -1;
		        for (int i=0; i<tiles.size(); i++) {
			      	for (int j=0; j<tiles.get(row-i).size(); j++) {

				        Rectangle r = new Rectangle(45,45);
				        if(tiles.get(row-i).get(j).getVisited() == true){
				        	r.setFill(Color.ORANGE);
				            pane.add(r,j,i);
				        }
				        else if (tiles.get(row-i).get(j).getType() == Tile.Type.WALL) {
				            r.setFill(Color.BLACK);
				            pane.add(r,j,i);
				        }
				        else if (tiles.get(row-i).get(j).getType() == Tile.Type.CORRIDOR){
				             r.setFill(Color.GREY);
				             pane.add(r,j,i);
				        }
				        else if(tiles.get(row-i).get(j).getType() == Tile.Type.EXIT){
				        	r.setFill(Color.GREEN);
				             pane.add(r,j,i);
				        }
				        else if(tiles.get(row-i).get(j).getType() == Tile.Type.ENTRANCE){
				             r.setFill(Color.GREY);
				             pane.add(r,j,i);
				        }
				        
			        }
	     
	    		}

	        } catch (NullPointerException ex){
        		Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Error,invalid load");
        		alert.setHeaderText(null);
        		alert.setContentText(ex.toString() + "\n Try Again");
        		alert.showAndWait();
        	}
	        catch (StreamCorruptedException ex) {
	            Alert alert1 = new Alert(AlertType.INFORMATION);
        		alert1.setTitle("Error with load file");
        		alert1.setHeaderText(null);
        		alert1.setContentText(ex.toString() + "\n Try Again");
        		alert1.showAndWait();
	        } 
	        catch (IOException ex) {
	            Alert alert1 = new Alert(AlertType.INFORMATION);
        		alert1.setTitle("Error");
        		alert1.setHeaderText(null);
        		alert1.setContentText(ex.toString() + "\n Try Again");
        		alert1.showAndWait();
	        } catch (ClassNotFoundException ex) {
	            Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Error");
        		alert.setHeaderText(null);
        		alert.setContentText(ex.toString() + "\n Try Again");
        		alert.showAndWait();
	        }
	    }
	}


	/**stepAct method which specifies the step functionality of a button to step through a maze
	* @param stage this is the stage which is used to display the the second scene to show the maze is solved 
	* @param pane this is the GridPane that will be filled with rectangles showing the maze
	* @param vbox this is used to group a label and an exit button to set in the center of a BorderPane
	* @param vbox2 this is used to hold a rectangle to place on the left of the BorderPane
	* @param vbox3 this is used to hold a rectangle to place on the right of the BorderPane
	* @param root2 this is the BorderPane used to organise the layout
	* @param label2 this is the label used to tell the user that the maze is solved
	* @param exitButton2 this is the button which when clicked will allow the user to exit the program
    * @throws NoRouteFoundException if there is no possible route to the exit then the exception is thrown
	*/
	public void stepAct(VBox vbox,VBox vbox2,VBox vbox3,GridPane pane,Stage stage,
		BorderPane root2,Label label2, Button exitButton2){

		try {
	 			
        	List<List<Tile>> tiles = maze.getTiles();
        	int row = tiles.size() -1;
            if(rt.step() == true){
            	Rectangle r1 = new Rectangle(100,900,Color.BLUE);
            	Rectangle r2 = new Rectangle(100,900, Color.BLUE);
            	//Rectangle r3 = new Rectangle(900,100,Color.RED);
            	
            	vbox.getChildren().addAll(label2,exitButton2);
            	vbox.setAlignment(Pos.CENTER);
            	vbox.setSpacing(30);
            	root2.setCenter(vbox);

            	vbox2.getChildren().add(r1);
            	root2.setLeft(vbox2);

            	vbox3.getChildren().add(r2);
            	root2.setRight(vbox3);
         
            	pane.getChildren().clear();
            	Scene scene2= new Scene(root2,900, 600);
            	scene2.getStylesheets().add("maze/visualisation/styles.css");
				stage.setScene(scene2);   
				stage.show();
            }
            for (int i=0; i<tiles.size(); i++) {
		      	for (int j=0; j<tiles.get(row-i).size(); j++) {

			        Rectangle r = new Rectangle(45,45);
			        if(rt.getRoute().contains(tiles.get(row-i).get(j))){
			        	r.setFill(Color.ORANGE);
			            pane.add(r,j,i);
			        }
			        else if (rt.getRoute().contains(tiles.get(row-i).get(j)) == false && tiles.get(row-i).get(j).getVisited()) {
                        r.setFill(Color.GREY);
                        pane.add(r,j,i);
						}
			        
		        }
	    	}

        } catch (NoRouteFoundException ex) {
            Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Error, No route found");
    		alert.setHeaderText(null);
    		alert.setContentText(ex.toString() + "\n Try Again");
    		alert.showAndWait();
        }
	}

}