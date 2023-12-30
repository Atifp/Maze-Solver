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
import maze.visualisation.*;
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


/**MazeApplication class which extends application ,consisting of
* methods to help with the visualisation process, with the use of javafx
* @author Atif
* @version 28th April 2021
*/
public class MazeApplication extends Application {
	private Maze maze;
	private RouteFinder rt;

	/**Start method of the application 
	* @param stage: This takes the mane stage for the visualisation to take place
	* @throws IOExeption if the file is not found
	* @throws InvalidMazeException covers multiple exceptions that arise when forming the maze
	* @throws NoRouteFoundException this occurs in step method when there is no possible route
	* @throws StreamCorruptedException if the file is invalid
	* @throws NullPointerException if the file is null
	* @throws ClassNotFoundException if the class is not found
	*/
	@Override
	 public void start (Stage stage)throws IOException, InvalidMazeException, NoRouteFoundException,
	  StreamCorruptedException, NullPointerException,ClassNotFoundException{

	 	MazeVisualise vis = new MazeVisualise();
	 	GridPane pane = new GridPane();

	 	HBox hbox = new HBox();
	 	HBox hbox1 = new HBox();
	 	HBox top = new HBox();

	 	VBox vbox = new VBox();
	 	VBox vbox2 = new VBox();
	 	VBox vbox3 = new VBox();

	 	BorderPane root = new BorderPane();
	 	BorderPane root2 = new BorderPane();
	 	root.setStyle("-fx-background-color: Blue");
	 	root2.setId("background");


	 	//scene
	 	Scene scene1= new Scene(root, 900, 600);
	 	scene1.getStylesheets().add("maze/visualisation/styles.css");
 	

	 	//Label
	 	Label label1 = vis.newLabel("Welcome to Maze Solver !!");
	 	Label label2 = vis.newLabel("MAZE SOLVED!!!");
	 	label1.setStyle("-fx-text-fill: White");
	 	label2.setAlignment(Pos.CENTER);
	 	label2.setStyle("-fx-text-fill: Green");
	    

	 	//load map buton
	 	Button loadMap = vis.newButton("Load Map");
	 	loadMap.setId("orange");
	 	loadMap.setOnAction(e -> {
	 		vis.loadMapAct(pane,stage);
    	});

	 	//exit button
	 	Button exitButton = vis.newButton("Exit");
	 	exitButton.setId("red");
	 	exitButton.setOnAction(e -> {
	         Platform.exit();
    	});

    	Button exitButton2 = vis.newButton("Exit Maze");
	 	exitButton2.setId("exit");
	 	exitButton2.setOnAction(e -> {
	         Platform.exit();
    	});


	 	//Step button
	 	Button step = vis.newButton("Step");
	 	step.setId("step");
	 	step.setOnAction(e -> {
        	vis.stepAct(vbox,vbox2,vbox3,pane,stage,root2,label2,exitButton2);

		});
	 	

	 	//load route button
	 	Button loadRoute = vis.newButton("Load Route");
	 	loadRoute.setId("red");
	 	loadRoute.setOnAction(e -> {
	 		vis.loadRouteAct(pane,stage);
		        
    	});

	 	//save route button
	 	Button saveRoute = vis.newButton("Save Route");
	 	saveRoute.setId("orange");
	 	saveRoute.setOnAction(e -> {
	 		vis.saveRouteAct(stage);
    	});
	 	

	 	hbox1.getChildren().addAll(step, label1);
	 	hbox1.setSpacing(140);
	 	root.setBottom(hbox1);

	 	//top hbox stuff
	 	top.setAlignment(Pos.CENTER);
	 	top.setSpacing(10);
	 	top.getChildren().addAll(saveRoute,loadRoute,loadMap,exitButton);
	 	root.setTop(top);

	   	pane.setAlignment(Pos.CENTER);
	    root.setCenter(pane);
    	stage.setScene(scene1);   
    	stage.show();

	}

	/**Main method which is used to launch the application
	* @param args[] Takes in a string of argumetns as the parameter
	*/
	public static void main(String args[]){
	 	launch(args);
	}
}

