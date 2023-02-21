package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
public class spaceGame extends Application {
    //variables
    static Random RAND = new Random();
    static int Width = 700;
    static int Height = 600;
    static int player_Size = 60;
    static final Image player_Img = new Image("images/player.png");
    static final Image explosion_Img = new Image("images/explosion.png");
    static int explosion_Height = 128;
    static int explosion_Width = 128;
    static int explosion_Rows = 3;
    static int explosion_Column = 3;
    static int explosion_Steps = 15;
    static final Image Enemies[] = {
            new Image("images/1.png"),
            new Image("images/2.png"),
            new Image("images/3.png"),
            new Image("images/4.png"),
            new Image("images/5.png"),
            new Image("images/6.png")
    };
    private final int Max_Enemies = 10, Max_Shots = Max_Enemies * 2;
    boolean gameOver = false;
    static GraphicsContext gC;
    Rocket player;
    List<Shot> shots;
    List<Univers> univ;
    List<Bomb> bombs;
    private double mouseX;
    static int score;

    @Override
    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas(Width, Height);
        gC = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gC)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        canvas.setCursor(Cursor.MOVE);
        //get the mouse X position
        canvas.setOnMouseMoved(e -> mouseX = e.getX());
        //click = shot
        canvas.setOnMouseClicked(e -> {
            if(shots.size() < Max_Shots) shots.add(player.shoot());
            if(gameOver) {
                gameOver = false;
                setup();
            }
        });
        setup();
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setTitle("Space Invaders");
        stage.show();
    }

    // generete ennemies bombs
    Bomb newBomb(){
        return new Bomb(50+ RAND.nextInt(Width-100),
                0,player_Size, Enemies[RAND.nextInt(Enemies.length)]);
    }

    //calculate the distance between 2 objects
    public static int distance(int x1,int y1,int x2,int y2){
        return (int) Math.sqrt(Math.pow((x1 - x2),2) + Math.pow((y1 - y2),2));
    }

    //setup the game
    private void setup(){
        univ=new ArrayList<>();
        shots=new ArrayList<>();
        bombs=new ArrayList<>();
        player=new Rocket(Width/2, Height-player_Size, player_Size, player_Img);
        score=0;
        IntStream.range(0,Max_Enemies).mapToObj(i ->this.newBomb()).forEach(bombs::add);
    }
    //run graphics
    private void run(GraphicsContext gc){
        gc.setFill(Color.grayRgb(20));
        // rectangle interface
        gc.fillRect(0,0,Width,Height);
        gc.setTextAlign(TextAlignment.CENTER);
        // show score
        gc.setFont(Font.font(20));
        gc.setFill(Color.WHITE);
        gc.fillText("Score: "+score,60,20);
        if(gameOver){
            gc.setFont(Font.font(35));
            gc.setFill(Color.YELLOW);
            gc.fillText("Game Over \n Your Score is: " + score
                    + " \n Click to play again", Width / 2, Height /2.5);
            //	return ;
        }
        univ.forEach(Univers::draw);
        player.update();
        player.draw();
        // player location = mouse location
        player.posX = (int) mouseX;

        bombs.stream().peek(Rocket::update).peek(Rocket::draw).forEach(e -> {
            if(player.colide(e) && !player.exploding) {
                player.explode();
            }
        });
// Missing a shot
        for (int i = shots.size() - 1; i >=0 ; i--) {
            Shot shot = shots.get(i);
            if(shot.posY < 0 || shot.toRemove)  {
                shots.remove(i);
                continue;
            }
            shot.update();
            shot.draw();
            //If the target bomb is hit
            for (Bomb bomb : bombs) {
                if(shot.colide(bomb) && !bomb.exploding) {
                    score++;
                    bomb.explode();
                    shot.toRemove = true;
                }
            }
        }

        for (int i = bombs.size() - 1; i >= 0; i--){
            if(bombs.get(i).destroyed)  {
                bombs.set(i, newBomb());
            }
        }
        gameOver = player.destroyed;
        if(RAND.nextInt(10) > 2) {
            univ.add(new Univers());
        }
        for (int i = 0; i < univ.size(); i++) {
            if(univ.get(i).posY > Height)
                univ.remove(i);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}






