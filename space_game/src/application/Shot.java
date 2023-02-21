package application;

import javafx.scene.paint.Color;

import static application.spaceGame.distance;

//tir //bullets
public class Shot {
    static final int size=6;
    public boolean toRemove;
    int posX,posY, speed=10;
    public Shot(int posX,int posY) {
        this.posY = posY;
        this.posX=posX;
    }
    public void update(){
        posY-=speed;
    }
    public void draw(){
        spaceGame.gC.setFill(Color.RED);
        //if score greater than 50 --> yellow big shot
        if (spaceGame.score >= 50 ){
            spaceGame.gC.setFill(Color.YELLOW);
            speed=50;
            spaceGame.gC.fillRect(posX-5, posY-10, size+10, size+30);
        }else {
            spaceGame.gC.fillOval(posX,posY,size,size);
        }
    }

    public boolean colide(Rocket rocket){
        int d = distance(this.posX+size /2,this.posY +size/ 2 ,
                rocket.posX+rocket.size /2, rocket.posY+rocket.size/2);
        return d<rocket.size/2 + this.size/2;
    }
}
