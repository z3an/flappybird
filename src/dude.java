import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;


public class dude {
int dx = 0,dy = 0;
//Image still;
public Rectangle bird;
public static Rectangle PlayGame, Pause;
public dude(boolean load_from_file, int bird_x_gameplay, int bird_y_gameplay, int bird_width_gameplay, int bird_height_gameplay){
	//ImageIcon i = new ImageIcon("/home/zepengan/workplace/cs349/z3an/A1/Design/char.png");
	//still = i.getImage();
	if(!load_from_file){
		bird = new Rectangle(390,390,20,20);
	}
	else{
		bird = new Rectangle(bird_x_gameplay, bird_y_gameplay, bird_width_gameplay, bird_height_gameplay);
	}
	//PlayGame = new Rectangle(500,10,150,50);
	//Pause = new Rectangle(300,10,150,50);
}

public void move(){
	bird.x = bird.x+dx;
	bird.y = bird.y+dy;
//	System.out.println("6666666666666666666");
}
public int getX(){
	return bird.x;
}

public int getY(){
	return bird.y;
}
public void setY(int y_dis){
	bird.y = y_dis;
}


public void repaint(Graphics g, boolean load_from_file, int world_width_gameplay, int world_height_gameplay, board board){
	if(!load_from_file){
	g.setColor(Color.cyan);
	g.fillRect(0,100,board.getWidth(),board.getHeight());
	//g.setColor(Color.orange);
	//g.fillRect(0,680,800,120);
	g.setColor(Color.red);
	g.fillRect(bird.x,bird.y,bird.width,bird.height);
	//g.setColor(Color.green);
	//g.fillRect(0,680,800,20);
	}
	else{
		g.setColor(Color.cyan);
		g.fillRect(0,world_height_gameplay/8,world_width_gameplay,world_height_gameplay*7/8);
		g.setColor(Color.red);
		g.fillRect(bird.x,bird.y,bird.width,bird.height);
	}
}

public void keyPressed(KeyEvent e){
	int key = e.getKeyCode();
	//System.out.println("6666666666666666666");
	if(key == KeyEvent.VK_D)
	{
		dx = 10;
		System.out.println("right");
	}
	if(key == KeyEvent.VK_A)
	{
		dx = -10;
		System.out.println("left");
	}
	if(key == KeyEvent.VK_W){
		if(bird.y >=10){
			dy = -10;
		}
		else if(bird.y < 10 && bird.y >=0){
			dy = -bird.y;
		}
		else{
			dy = 0;
		}
		System.out.println("up");
	}
	if(key == KeyEvent.VK_S){
		if(bird.y <=670 ){
			dy = 10;
		}
		else if(bird.y <680){
			dy = 680-bird.y;
		}
		else{
			dy=0;
		}
		System.out.println("down");
	}
	
}
public void keyReleased(KeyEvent e){
	int key = e.getKeyCode();
	if(key == KeyEvent.VK_D)
	{
		dx = 0;
	}
	if(key == KeyEvent.VK_A)
	{
		dx = 0;
	}
	if(key == KeyEvent.VK_W){
		dy = 0;
	}
	if(key == KeyEvent.VK_S){
		dy = 0;
	}
}
}
