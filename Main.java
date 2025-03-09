/**
 * The driver class for Geometry Click, the game of cookie clicker with my flavor of geometric shapes
 * 
 * @author Laura Brown
 * @version 1.0
 * 
 */

import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {

	Stage stage; 
	Scene mainMenu, gameScreen;
	HBox layoutGameScreen, sqrMgrBox, triMgrBox, pgnMgrBox;
	VBox shapeManagerContainer, rightPane;
	Pane canvas;
	GridPane achievementsGridView;
	
	TriangleManager triMgr;
	Label triMgrCostLabel;
	Label numTriMgrs;
	
	SquareManager sqrMgr;
	Label sqrMgrCostLabel;
	Label numSqrMgrs;
	
	PentagonManager pgnMgr;
	Label pgnMgrCostLabel;
	Label numPgnMgrs;
	
	Label numAnglesLabel;
	Label cpsLabel; //clicks per second
	int made = 0; //angles per second label has been created 0 or 1 
	double numAngles = 0; //initialized to 0
	double addAnglesPerSecond = 0;
	
	
	final int GAME_HEIGHT = 500, GAME_WIDTH = 800; 
	Random rand = new Random();
	int time = 0;
	int triTime = 25;
	int sqrTime = 25;
	int pgnTime = 25;
	
	int numManagerTypes = 0;
	int totalMgrs = 0;
	
	public static void main(String[] args) {
		launch(args);
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		/*
		 * Creating gameScreen
		 */
		layoutGameScreen = new HBox(10);
		
		//Lefthand pannel on the game where information about buying and updating managers is stored.
		shapeManagerContainer = new VBox(5);
		shapeManagerContainer.setPrefWidth((GAME_WIDTH/3)-10);

		//Middle pannel on the game where you can click and shapes appear for angles created increases
		canvas = new Pane();
		canvas.setStyle("-fx-background-color: black" );
		canvas.setPrefSize((GAME_WIDTH/3)-5, GAME_HEIGHT);
		
		
		//Righthand pannel of the game where more information is stored about number of angles, achievements etc.
		rightPane = new VBox(10);

		Label clickMessage = new Label("Click the middle section to increase your angles!");

		numAnglesLabel = new Label("Number of angles: "+ numAngles);
		
		achievementsGridView = new GridPane(); 
		
		rightPane.getChildren().addAll(clickMessage, numAnglesLabel, achievementsGridView);
		
		
		//Add all components into the game screen and create 
		layoutGameScreen.getChildren().addAll(shapeManagerContainer, canvas, rightPane);
		gameScreen = new Scene(layoutGameScreen, GAME_WIDTH, GAME_HEIGHT);
		
		
		//Mouse action for adding shapes and earning angles in the game
		canvas.setOnMousePressed(e -> {
			addShape(newTriangle());
			numAngles += 3;
			//anglesToDate += 3;
			numAnglesLabel.setText("Number of angles: "+ (int)numAngles);

		});
		
		/*
		 * Manager click controls
		 */
		makeManagers();

				
		triMgrBox.setOnMouseClicked(ev -> {
			if(triMgr.canAfford(numAngles)) {
				
				numAngles -= triMgr.getCost();
				addAnglesPerSecond += triMgr.buyManager();
				numTriMgrs.setText(""+triMgr.getNumOfManagers());
				triMgrCostLabel.setText("Cost: "+ (int)triMgr.getCost() + " angles");
				totalMgrs++;
				
				if(triTime > 1) {
					triTime--;
				}
			}
		});
		
		//SQUARE MANAGER
		
		sqrMgrBox.setOnMouseClicked(ev -> {
			if(sqrMgr.canAfford(numAngles)) {
				numAngles -= sqrMgr.getCost();
				addAnglesPerSecond += sqrMgr.buyManager();
				numSqrMgrs.setText(""+sqrMgr.getNumOfManagers());
				sqrMgrCostLabel.setText("Cost: "+ (int)sqrMgr.getCost() + " angles");
				totalMgrs++;
				
				if(sqrTime > 1) {
					sqrTime--;
				}
			}
		});
		
		//PENTAGON MANAGER
		
		pgnMgrBox.setOnMouseClicked(ev -> {
			if(pgnMgr.canAfford(numAngles)) {
				numAngles -= pgnMgr.getCost();
				addAnglesPerSecond += pgnMgr.buyManager();
				numPgnMgrs.setText(""+pgnMgr.getNumOfManagers());
				pgnMgrCostLabel.setText("Cost: "+ (int)pgnMgr.getCost() + " angles");
				totalMgrs++;
				
				if(pgnTime > 1) {
					pgnTime--;
				}
			}
		});
		

		
		managerClickControl();
	
		cpsLabel = new Label();
		
		
		/*
		 * Creating gameMenu scene 
		 */
		VBox layout1 = new VBox(20);
		layout1.setAlignment(Pos.CENTER);
		
		Label title1 = new Label("Geometry Click");
		title1.setOnMouseEntered(e -> {
			title1.setTextFill(randomColor());
			title1.setStyle("-fx-stroke-width: 5;-fx-stroke: black; -fx-font: 80 ubuntu");
		});
		title1.setOnMouseExited(e-> {
			title1.setTextFill(Color.BLACK);

		});
		
		title1.setStyle("-fx-font: 80 ubuntu");
		Label title2 = new Label("");
		Label authorLabel = new Label("By Marva Lamb");
		authorLabel.setStyle("-fx-font: 10 cursive");
		Label versionLabel = new Label("Version 1.0");
		versionLabel.setStyle("-fx-font: 10 cursive");
		
		
		
		Button startButton = new Button("Start");
		startButton.setOnAction(e -> {
			stage.setScene(gameScreen);
			startService(); //begins timer for game
		});
		
		
		Button achievementsButton = new Button("Achievements");
		
		HBox menuButtons = new HBox(20);
		menuButtons.setAlignment(Pos.CENTER);
		menuButtons.getChildren().addAll(startButton, achievementsButton);
		
		layout1.getChildren().addAll(title1,title2,menuButtons,authorLabel, versionLabel);
	
		
		
		mainMenu = new Scene(layout1, GAME_WIDTH, GAME_HEIGHT);
		stage.setScene(mainMenu);
		stage.setTitle("Geometry Click");
		stage.setResizable(false);
		stage.show();
		
		/*
		 *Starts checking every few seconds for conditions of the game/updates number of angles and 
		 *brings in more managers etc, etc, etc, as time goes on.
		 */
		
		
		
	}
	/**
	 * 
	 * @return a random rgb-value color
	 */
	public Color randomColor() {
		return Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
	}
	
	public void managerClickControl() {
		//TRIANGLE MANAGER
	}
	
	
	public void makeManagers() {
		//Miss Triangle
		triMgr = new TriangleManager();
		VBox middle1 = new  VBox(2);
		Label managerLabel1 = new Label("Miss Triangle");
		triMgrCostLabel = new Label("Cost: "+ triMgr.getCost() + " angles");
		numTriMgrs = new Label(""+ triMgr.getNumOfManagers());
		
		middle1.getChildren().addAll(managerLabel1, triMgrCostLabel);
		triMgrBox = new HBox(3);

		triMgrBox.getChildren().addAll(middle1, numTriMgrs);
		shapeManagerContainer.getChildren().addAll(triMgrBox);
		
	
	//Square Manager Unlocked
		sqrMgr = new SquareManager();
		VBox middle2 = new VBox(2);
		Label managerLabel2 = new Label("Square Guy");
		sqrMgrCostLabel = new Label("Cost: "+ sqrMgr.getCost() + " angles");
		numSqrMgrs = new Label(""+ sqrMgr.getNumOfManagers());
		
		middle2.getChildren().addAll(managerLabel2, sqrMgrCostLabel);
		sqrMgrBox = new HBox(3);
		
		sqrMgrBox.getChildren().addAll(middle2, numSqrMgrs);
		shapeManagerContainer.getChildren().addAll(sqrMgrBox);
		
	
	
	//Pentagon Manager Unlocked
		pgnMgr = new PentagonManager();
		VBox middle3 = new VBox(2);
		Label managerLabel3 = new Label("Mr. Pentagon");
		pgnMgrCostLabel = new Label("Cost: " + pgnMgr.getCost() + " angles");
		numPgnMgrs = new Label(""+ pgnMgr.getNumOfManagers());
		
		middle3.getChildren().addAll(managerLabel3, pgnMgrCostLabel);
		pgnMgrBox = new HBox(3);
		
		pgnMgrBox.getChildren().addAll(middle3, numPgnMgrs);
		shapeManagerContainer.getChildren().addAll(pgnMgrBox);
		
	}
	
	
	
	/**
	 * Timer service used to update the game every 100ms with new shapes and increased angle count!
	 */
	public void startService() {
		
		//UPDATE things every tenth of a second
		
	    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
	    	
	    	
	    	if(time == 1000) 
	    		time = 0;
	    		    	
	    	time++;
	    	
	    	if(time%triTime == 0 && numManagerTypes > 0 ) {
	    		if(triMgr.getNumOfManagers() > 0) 
	    			addShape(newTriangle());
	    		    		
	    	}
	    	
	    	if(time%sqrTime == 0 && numManagerTypes > 1) {
	    		if(sqrMgr.getNumOfManagers() > 0)
	    			addShape(newSquare());
	    	}
	    	
	    	/*
	    	 * Update number of Angles
	    	 */
	    	numAngles += (addAnglesPerSecond/10); 
	    	
	    	numAnglesLabel.setText("Number of angles: " +  (int) numAngles);
	    	
	    	if(totalMgrs == 1 && made < 1) {
				//this is the first manager ever created! Report the cps now!
	    		cpsLabel.setText("Angles per second: " + addAnglesPerSecond);
	    		made++;
				rightPane.getChildren().addAll(cpsLabel);
				
			} else if (totalMgrs > 1) {
				cpsLabel.setText("Angles per second: " + (int) addAnglesPerSecond);
			}
	    	
	}));
	    timeline.setCycleCount(Animation.INDEFINITE);
	    timeline.play();
	}
	
	
	public void startServiceDeleteShape() {
	    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
	    	
	    }));
	    timeline.setCycleCount(Animation.INDEFINITE);
	    timeline.play();
	}
	
	
	/*
	 * Creates a Polygon object that is an equilateral triangle designated to a random spot
	 * on the black board in the middle of the game display
	 */
	public Polygon newTriangle() {
		double x = rand.nextInt(GAME_WIDTH/3-10);
		double y = rand.nextInt(GAME_HEIGHT-5);
		
		double size = 3 + rand.nextInt(5);
		
		Polygon triangle = new Polygon();
		triangle.getPoints().addAll(new Double[] {
								x+size, y+size,
								x-size, y+size,
								x, y-size });
		return triangle;
	}
	
	public Polygon newSquare() {
		double x = rand.nextInt(GAME_WIDTH/3-10);
		double y = rand.nextInt(GAME_HEIGHT-5);
		
		double size = 3+ 2*rand.nextInt(5);
		
		Polygon square = new Polygon();
		square.getPoints().addAll(new Double[] {
								x, y,
								x+size, y,
								x+size, y+size,
								x, y+size});

		return square;
	}
		
	/*
	 * Adds a given shape to the black board in the middle of the display
	 */
	public void addShape(Polygon shape) {//int angles, int size) {
		
		double x = rand.nextInt(GAME_WIDTH/3-10);
		double y = rand.nextInt(GAME_HEIGHT-5);
		
		double size = 3 + rand.nextInt(5);
		
//		
//		Polygon shape = new Polygon();
//		for(int i = 0; i < angles; i++) {
//			shape.addPoint((int)(size*Math.cos(i*2*Math.PI/2)), 
//					(int)(size*Math.sin(i*2*Math.PI/2)));
		//}
		
		
		canvas.getChildren().add(shape);
		
		shape.setFill(randomColor());
		shape.setStroke(Color.WHITE);		
	}
}
