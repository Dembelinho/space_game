package application;
import javafx.scene.image.Image;

//computer player //Enemies
public class Bomb extends Rocket{
    //start with speed=2 & increase speed every 5 scores
    int SPEED =(spaceGame.score/5)+2;

    public Bomb(int posX, int posY, int size, Image img) {
        super(posX, posY, size, img);
    }
    @Override
    public void update() {
        super.update();
        // if the bomb is not destroyed: it does not stop descending vertically
        if(!exploding && !destroyed) posY+= SPEED;
        if (posY > spaceGame.Height) destroyed=true;
    }
}
