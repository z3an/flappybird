import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;






public class board extends JPanel implements ActionListener, MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	dude p;
	Timer time;
	public Random rand;
	public boolean gameover, started=true,whether_win;
	public ArrayList<Rectangle> column;
	public int score = 0;
	public int left_boundary = 0;
	public int right_boundary = 800;
	public boolean editor_check = false;
	public GameState copy_state;
	private JSlider speed = new JSlider(0,20);
	public int speed_int = 10;
	public StringBuilder file_content = new StringBuilder("");
	public int world_width_gameplay;
	public int world_height_gameplay;
	public int bird_width_gameplay;
	public int bird_height_gameplay;
	public int bird_x_gameplay;
	public int bird_y_gameplay;
	public int column_num_gameplay;
	public int column_width_gameplay;
	public int column_height_gameplay;
	public int column_x_gameplay;
	public int column_y_gameplay;
	public int FPS = 30;
	public JSlider fps_slider = new JSlider(10,60,FPS);
	public boolean load_from_file = false;
	public frame copy_frame;
	
	public board(frame frame, StringBuilder content){
		copy_frame = frame;
		column = new ArrayList<Rectangle>();
		file_content = content;
		load_from_file = false;
		String value = file_content.toString();
		if(!value.equals("")){
			load_from_file = true;
			//String pattern = Pattern.quote("\\" + "n");
			String lines[] = value.split("\\r?\\n");
			//System.out.println("lines length " + lines.length);
			//for(int i =0 ; i<lines.length;i++){
			//	String element = lines[i];
			//	System.out.println("value in array " + element);
				
			//}
			world_width_gameplay = Integer.valueOf(lines[0]);
			world_height_gameplay = Integer.valueOf(lines[1]);
			bird_x_gameplay = Integer.valueOf(lines[2]);
			bird_y_gameplay = Integer.valueOf(lines[3]);
			bird_width_gameplay = Integer.valueOf(lines[4]);
			bird_height_gameplay = Integer.valueOf(lines[5]);
			column_num_gameplay = Integer.valueOf(lines[6]);
			for(int i =1 ; i<=4*(column_num_gameplay);){
					//String element = lines[i+6];
					column_x_gameplay = Integer.valueOf(lines[i+6]);
					column_y_gameplay = Integer.valueOf(lines[i+7]);
					column_width_gameplay = Integer.valueOf(lines[i+8]);
					column_height_gameplay = Integer.valueOf(lines[i+9]);
					i = i + 4;
					column.add(new Rectangle(column_x_gameplay,column_y_gameplay,column_width_gameplay,column_height_gameplay));
					//System.out.println("value in array " + element);
					
			}
			//System.out.println("columns size " + column.size());
		}
		JButton play = new JButton("Play"), pause = new JButton("Pause"), giveup = new JButton("Give up");
        this.add(play);
        play.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				copy_state = GameState.GAME;
				board.this.requestFocusInWindow();
			}
        	
        });
        this.add(pause);
        pause.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				copy_state = GameState.PAUSE;
				board.this.requestFocusInWindow();
			}
        	
        });
        
        this.add(giveup);
        giveup.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				copy_frame.removeBoard(board.this);
//				board.this.requestFocusInWindow();
			}
        	
        });
        
        
        
        
		copy_state = frame.state;
		p = new dude(load_from_file,bird_x_gameplay,bird_y_gameplay,bird_width_gameplay,bird_height_gameplay);
		
		addKeyListener(new AL());
		addMouseListener(this);
		setFocusable(true);
		rand = new Random();
		time = new Timer(1000/FPS,this);
		time.start();
		this.layoutView();
		this.registerControllers();
	}
	private void registerControllers() {
		// TODO Auto-generated method stub
		this.speed.addChangeListener(new BaseController());
	}
	private class BaseController implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			speed_int = speed.getValue();
//			System.out.printf("dexp: %f\n", base);
			board.this.requestFocusInWindow();
		}
	}
	private void layoutView() {
		// TODO Auto-generated method stub
//		this.setLayout(new FormLayout());
		JLabel label = new JLabel("Speed: ");
		//label.setHorizontalAlignment(20);
		this.add(label);
		this.add(this.speed);
		this.speed.setMajorTickSpacing(1);
		this.speed.setPaintTicks(true);

		
		JLabel jlFPS = new JLabel("FPS:");
        this.add(jlFPS);
        this.add(this.fps_slider);
        //fps_slider = new JSlider(10,60, FPS);
        fps_slider.setMajorTickSpacing(50);
        //fps_slider.setMinorTickSpacing(10);
        fps_slider.setPaintTicks(true);
        //fps_slider.setPaintLabels(true);
        //this.add(fps_slider);
        fps_slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                FPS = fps_slider.getValue();
                time.setDelay(1000/FPS);
                board.this.requestFocusInWindow();
            }
        });
		
		
		
	}
	public void restart(){
		if(gameover)
		{
			p = new dude(load_from_file,bird_x_gameplay,bird_y_gameplay,bird_width_gameplay,bird_height_gameplay);
			column.clear();
			score = 0;
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
			gameover = false;
			whether_win = false;
		}
		if(!started)
		{
			started = true;
		}
	}
	public void actionPerformed(ActionEvent e){
		
		
		if(copy_state == GameState.GAME){
			if(!load_from_file){
				addColumn(true);
				addColumn(true);
				addColumn(true);
				addColumn(true);
			}
		p.move();
		int speed = speed_int;
		if(started){
			//System.out.println("6666666666666666666");
		for(int i = 0; i < column.size();i++){
			Rectangle columns = column.get(i);
			columns.x -= (speed * 20) / FPS;
		}
		for(int i = 0; i < column.size();i++){
			Rectangle columns = column.get(i);
			if(columns.x+columns.width <0){
				column.remove(columns);
				if(columns.y == 100)
				{	if(!load_from_file){
						addColumn(false);
					}
				}
			}
		}
		for(Rectangle columns : column){
			if(columns.y == 100 && p.bird.x + p.bird.width / 2 > columns.x+ columns.width /2 -10 && p.bird.x+p.bird.width /2 < columns.x +columns.width/2+10){
				score++;
			}
			if(p.getX() <  left_boundary){
				gameover = true;
			}
			if(p.getX() > right_boundary){
				whether_win = true;
				gameover = true;
			}
			if (columns.intersects(p.bird)){
				gameover = true;
				p.bird.x = columns.x- p.bird.width;
			}
		}
		}
		repaint();
		}
		
		
	}
	
	public void addColumn(boolean start){
		int space = 300;
		int width = 100;
		int height = 50 + rand.nextInt(300);
		if(start){
			column.add(new Rectangle(900+column.size()*300,800-height,width,height));
			column.add(new Rectangle(900+(column.size()-1)*300,100,width,700-height-space));
		}
		else{
			column.add(new Rectangle(column.get(column.size()-1).x+600,800-height,width,height));
			column.add(new Rectangle(column.get(column.size()-1).x,100,width,700-height-space));
		}
	}
	public void paintColumn(Graphics g, Rectangle column){
		g.setColor(Color.green.darker());
		g.fillRect(column.x,column.y,column.width,column.height);
	}
	
	public void paint_game(Graphics g){
		
		p.repaint(g,load_from_file,world_width_gameplay,world_height_gameplay,this);
		for(Rectangle columns : column){
			paintColumn(g,columns);
		}
		g.setColor(Color.white);
		g.setFont(new Font("Arial",1,100));
		if(!started){
			g.drawString("Click to Start!",75,350);
		}
		if(gameover){
			if(whether_win){
				g.drawString("You Won",75,350);
				g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
				g.drawString("Score", 5, 130);
				g.drawString(String.valueOf(score), 100, 130);
			}
			else{
				g.drawString("Game Over", 75, 350);
				g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
				g.drawString("Click to Restart", 75, 400);
				g.drawString("Score", 5, 130);
				g.drawString(String.valueOf(score), 100, 130);
			}
		}
		if(!gameover && started){
			g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
			g.drawString("Score", 5, 130);
			g.drawString(String.valueOf(score), 100, 130);
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		switch(copy_state){
		case GAME:
				paint_game(g);
			break;
		case MENU:
			//menu.render(g);
			break;
		case OPTIONS:
			if(!editor_check)
			//editor.create();
//			editor.render(g);
			editor_check = true;
			break;
		case PAUSE:
			paint_game(g);
			break;
		default:
			g.setColor(Color.RED);
			g.drawString("UNKNOWN GAMESTATE",150,150);
			break;
			
		}
	}

	
	
	private class AL extends KeyAdapter{
		public void keyReleased(KeyEvent e){
			p.keyReleased(e);
		}
		public void keyPressed(KeyEvent e){
			p.keyPressed(e);
		}
	}



	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(gameover);
//		/System.out.println("6666666666666666666");
		restart();
	}
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}


