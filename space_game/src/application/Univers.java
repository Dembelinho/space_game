package application;

import javafx.scene.paint.Color;

//environement
public class Univers {
    int posX,posY;
    // different elements of our universe
    private  int r,g,b; //rgb
    private  int   h,w; //height  width
    private double opacity;
    public Univers() {
        posX=spaceGame.RAND.nextInt(spaceGame.Width);
        posY=0;
        //random opacity nd size
        w=spaceGame.RAND.nextInt(5)+1;
        h=spaceGame.RAND.nextInt(5)+1;
        r=spaceGame.RAND.nextInt(100)+150;
        g=spaceGame.RAND.nextInt(100)+150;
        b=spaceGame.RAND.nextInt(100)+150;
        opacity=spaceGame.RAND.nextFloat();
        if(opacity<0) opacity*=-1;
        if(opacity>0.5) opacity=0.5;
    }
    public void draw(){
            if(opacity<0.1) opacity+=0.01;
            if(opacity>0.8) opacity-=0.01;
            spaceGame.gC.setFill(Color.rgb(r,g,b,opacity));
            spaceGame.gC.fillOval(posX,posY,w,h);
            posY+=20;
    }

}
