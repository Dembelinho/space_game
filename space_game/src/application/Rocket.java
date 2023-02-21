package application;
import javafx.scene.image.Image;
import static application.spaceGame.distance;
//player class
public class Rocket {
    //position along the x-axis
    int posX;
    //position along the y-axis
    int posY;
    int size;
    boolean exploding,destroyed;
    Image img;
    int explosionsStep=0;

    public Rocket(int posX, int posY, int size, Image img) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.img = img;
    }

    public Shot shoot(){
        return new Shot((posX+size - Shot.size) /2 ,posY - Shot.size);
    }
    public void update(){
        if (exploding) explosionsStep++;
        destroyed= explosionsStep > spaceGame.explosion_Steps;
    }
    public void draw(){
        if(exploding){
            spaceGame.gC.drawImage(spaceGame.explosion_Img,
                    explosionsStep % spaceGame.explosion_Column * spaceGame.explosion_Width,
                    (explosionsStep/spaceGame.explosion_Rows)*spaceGame.explosion_Height+1, spaceGame.explosion_Width,
                    spaceGame.explosion_Height,posX,posY,size,size);
        }else {
            spaceGame.gC.drawImage(img,posX,posY,size,size);
        }
    }
    //to determine if the player collided with a bomb
    public boolean colide(Rocket other){
        int d = distance(this.posX+size /2,this.posY +size/ 2 ,
                other.posX+other.size /2, other.posY+other.size/2);
        return d<other.size/2 + this.size/2;
    }
    public void explode(){
        exploding= true;
        explosionsStep= -1;
    }
}
