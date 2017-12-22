import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class View2 extends JPanel implements IView,Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Model model;
	private int world_width;
	private int world_height;
	private int obstacle_width;
	private int obstacle_height;
	public Rectangle bird;
	public ArrayList<Rectangle> columns = new ArrayList<Rectangle>();
	public Rectangle cur_obstacle;
	private Point initialClick;
	private boolean rightside_selected = false; // did the user select the triangle to
	private boolean botside_selected = false; // did the user select the triangle to
	public int change_shape_index=0;
	public boolean click_on_obstacle =  false;
	public Rectangle copy_obstacle = new Rectangle();
	public boolean whether_copy = false;
	public StringBuilder file_content = new StringBuilder("");
	public boolean obstacle_location_change = false;
	public Rectangle rec_location_drag;
	//public boolean whether_right_click = false;
	public Rectangle rec_location_shape = new Rectangle();
	//public boolean obstacle_width_change = false;
	//public Rectangle rec_location_bot = new Rectangle();
	public boolean obstacle_height_change = false;
	//public boolean check_whether_right_click = false;
	//private boolean click_obstacle = false;
	
	
	public View2(Model model_, StringBuilder content) {
		//file_content = content;
		
		model = model_;
		model.UM.addObserver(this);
		MovingAdapter ma = new MovingAdapter();
		this.addMouseListener(ma);
		this.addMouseMotionListener(ma);
		// TODO Auto-generated constructor stub
		//System.out.println("column size "+columns.size());
		Double world_width_double = new Double(model.get_world_Width());
		world_width = world_width_double.intValue();
		Double world_height_double = new Double(model.get_world_Height());
		world_height = world_height_double.intValue();
		Double obstacel_width_double = new Double(model.get_obstacle_Width());
		obstacle_width = obstacel_width_double.intValue();
		Double obstacel_height_double = new Double(model.get_obstacle_Height());
		obstacle_height = obstacel_height_double.intValue();
		this.setPreferredSize(new Dimension(world_width,world_height));
		bird = new Rectangle(world_width/3,world_height/2,world_width/40,world_height/40);
		model_.bird = this.bird;
		//columns.add(bird);
		
		JButton play = new JButton("Play"), pause = new JButton("Pause");
        this.add(play);
        this.add(pause);
        play.setEnabled(false);
		pause.setEnabled(false);
		
	}
	public StringBuilder Change_Content(){
		//System.out.println("columns size" + columns.size());
		//file_content = new StringBuilder("");
		file_content.delete(0, file_content.length());
		String S_world_width = Integer.toString(world_width)+"\n";			//store world width
		String S_world_height = Integer.toString(world_height)+"\n";		//store world height
		String bird_x = Integer.toString(bird.x)+"\n";						//store bird x
		String bird_y = Integer.toString(bird.y)+"\n";						//store bird y
		String bird_width = Integer.toString(bird.width)+"\n";				//store bird width
		String bird_height = Integer.toString(bird.height)+"\n";			//store bird height
		String columns_num = Integer.toString(columns.size()) + "\n";		//store columns number
		file_content.append(S_world_width + S_world_height +bird_x +bird_y+bird_width+bird_height+columns_num);
		for(int i = 0 ; i<columns.size(); i++){
			Rectangle column = columns.get(i);
			String column_x = Integer.toString(column.x)+"\n";				//store column x
			String column_y = Integer.toString(column.y)+"\n";				//store column y
			String column_width = Integer.toString(column.width)+"\n";		//store column width
			String column_height = Integer.toString(column.height)+"\n";	//store column height
			file_content.append(column_x + column_y +column_width +column_height);
			//System.out.println("columnx columny column")
		}
		return file_content;
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.cyan);
		g.fillRect(0,world_height/8,world_width,world_height);
		g.setColor(Color.red);
		g.fillRect(bird.x,bird.y,bird.width,bird.height);
		for(int i =0 ; i< columns.size();i++){
			Rectangle column = columns.get(i);
			paintColumn(g,column);
			if(rightside_selected && i ==change_shape_index ){
				g.setColor(Color.black);
				g.fillRect(column.x+column.width - 3, column.y - 3, 6, 6);
				g.fillRect(column.x+column.width - 3, column.y+column.height - 3, 6, 6);
			}
			if(botside_selected && i ==change_shape_index ){
				g.setColor(Color.black);
				g.fillRect(column.x - 3, column.y+column.height - 3, 6, 6);
				g.fillRect(column.x+column.width - 3, column.y+column.height - 3, 6, 6);
			}
		}
		//g.setColor(Color.green);
		//g.fillRect(0,world_height*39/40,world_width,world_height/40);
		//System.out.println("grass y location"+world_height*39/40);
		//System.out.println("grass height"+world_height/40);
		//System.out.println("width"+world_width);
		//System.out.println("height"+world_height);
	}
	public void updateView() {
		// TODO Auto-generated method stub
		Double width_double = new Double(model.get_world_Width());
		world_width = width_double.intValue();
		Double height_double = new Double(model.get_world_Height());
		world_height = height_double.intValue();
		Double obstacel_width_double = new Double(model.get_obstacle_Width());
		obstacle_width = obstacel_width_double.intValue();
		Double obstacel_height_double = new Double(model.get_obstacle_Height());
		obstacle_height = obstacel_height_double.intValue();
//		genColumn();
		this.setPreferredSize(new Dimension(world_width,world_height));
		//System.out.println("this is view2 updateview");
		this.repaint();
	}
	public void genColumn(){
		//int space = 300;
		//int width = 100;
		//int height = 50 + rand.nextInt(300);
		Rectangle obstacle = new Rectangle(0,world_height/8,obstacle_width,obstacle_height);
		cur_obstacle = obstacle;
		columns.add(cur_obstacle);
		//System.out.println("generate obstacle");
		Change_Content();
	}
	public void paintColumn(Graphics g, Rectangle column){
		g.setColor(Color.green.darker());
		g.fillRect(column.x,column.y,column.width,column.height);
	}
	
	
	class PopUpDemo extends JPopupMenu {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JMenuItem Copy;
		JMenuItem Past;
		JMenuItem Cut;
		JMenuItem Delete;
		//JMenuItem Undo;
		//JMenuItem Redo;
	    public PopUpDemo(){
	    	//Undo = new JMenuItem("Undo");
	    	//Redo = new JMenuItem("Redo");
	    	//System.out.println("this is in popupdemo");
	        Copy = new JMenuItem("Copy");
	        Copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
					ActionEvent.CTRL_MASK));
	        Copy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					View2.this.Copy();
				}
			});
	        if(!click_on_obstacle){
	        	Copy.setEnabled(false);
	        }
	        add(Copy);
	        Cut = new JMenuItem("Cut");
	        add(Cut);
	        Cut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					View2.this.Cut();
				}
			});
	        if(!click_on_obstacle){
	        	Cut.setEnabled(false);
	        }
	        Delete = new JMenuItem("Delete");
	        add(Delete);
	        Delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					View2.this.Delete();
				}
			});
	        if(!click_on_obstacle){
	        	Delete.setEnabled(false);
	        }
	        Past = new JMenuItem("Paste");
	        add(Past);
	        Past.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					View2.this.Past();
				}
			});
	        if(!whether_copy){
	        	Past.setEnabled(false);
	        }
	    }
	}
	
	
	
	
	class MovingAdapter extends MouseAdapter {
		private int mouse_click_x;
		private int mouse_click_y;
		
		public boolean ifMoving = false;
		public boolean ifchanging_shape = false;
		
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		initialClick = e.getPoint();
		/**
		Rectangle rect = new Rectangle(e.getX(),e.getY(),1,1);
		if(rect.intersects(cur_obstacle)){
			click_obstacle = true;
		}
		**/
		mouse_click_x = initialClick.x;
		mouse_click_y = initialClick.y;
		click_on_obstacle = false;
		
		//check_whether_right_click = false;
		rightside_selected = onRightSide(e.getX(), e.getY());
		if(rightside_selected){
			int index = which_rec_right_side_selected(mouse_click_x,mouse_click_y);
			Rectangle column = columns.get(index);
			model.UM.shape(column);
		}
		botside_selected = onBotSide(e.getX(), e.getY());
		if(botside_selected){
			int index = which_rec_bot_side_selected(mouse_click_x,mouse_click_y);
			Rectangle column = columns.get(index);
			model.UM.shape(column);
		}
		for(int i = 0 ; i < columns.size();i++){
			Rectangle rec = columns.get(i);
			if(rec.getBounds2D().contains(mouse_click_x,mouse_click_y)){
				click_on_obstacle = true;
				copy_obstacle = rec;
				break;
			}
		}
		if (e.isPopupTrigger()){
			//whether_right_click = true;
			//check_whether_right_click = true;
            doPop(e);
		}
	}
	private void doPop(MouseEvent e) {
		// TODO Auto-generated method stub
		 PopUpDemo menu = new PopUpDemo();
	     menu.show(e.getComponent(), e.getX(), e.getY());
	}
	
	
	public void mouseDragged(MouseEvent e){
		//if(click_obstacle){
		//whether_right_click = false;
		if(!rightside_selected && !botside_selected){
			int dx = e.getX() - mouse_click_x;
			int dy = e.getY() - mouse_click_y;
			columns.add(bird);
			for(int i =0; i < columns.size();i++){
				Rectangle column = columns.get(i);
				if(column.getBounds2D().contains(mouse_click_x,mouse_click_y)){
					ifMoving = true;
					//if(whether_copy = false){
					//if(!check_whether_right_click){
					for(int j =0; j< columns.size();j++){
						if(i!=j){
							Rectangle check = columns.get(j);
							if(column.x+column.width+dx>check.x && column.x+dx < check.x && ((column.y<=check.y+check.height&&column.y+column.height>check.y+check.height)||(column.y<check.y&&column.y+column.height>check.y)) ){
								if(column.y+dy <100){
									dy = 0;
									column.y = 100;
								}
								dx = check.x - column.x-column.width;
								//column.x = 
								break;
							}
							if(column.x+dx < check.x+check.width && column.x+dx+column.width > check.x+check.width&& column.y<=check.y+check.height&&((column.y<=check.y+check.height&&column.y+column.height>check.y+check.height)||(column.y<check.y&&column.y+column.height>check.y))){
								if(column.y+dy <100){
									dy = 0;
									column.y = 100;
								}
								dx = check.x+check.width - column.x;
								break;
							}
							if(column.y+dy+column.height>check.y && column.y+dy<check.y &&((column.x<check.x&&column.x+column.width>check.x)||(column.x<check.x+check.width&&column.x+column.width>check.x+check.width))){
								dy = check.y-column.y-column.height;
								break;
							}
							if(column.y+dy<check.y+check.height && column.y+dy+column.height > check.y+check.height&& ((column.x<check.x&&column.x+column.width>check.x)||(column.x<check.x+check.width&&column.x+column.width>check.x+check.width))){
								dy = check.y+check.height-column.y;
								break;
							}
							if(column.y+dy <100){
								dy = 0;
								column.y = 100;
							}
							//if(column)
						}
						
					}
					if(!obstacle_location_change){
						model.UM.create(column);
						//System.out.println("777");
					}
					obstacle_location_change = true;
					column.x +=dx;
					column.y +=dy;
					rec_location_drag = column;
					//System.out.println("position x" + column.x);
					//System.out.println("position y" + column.y);
					//repaint();
					model.updateAllViews();
				}
			}
			bird = columns.get(columns.size()-1);
			columns.remove(columns.size()-1);
			mouse_click_x += dx;
			mouse_click_y += dy;
			//System.out.println("mousedragged works");
		}
		else if(rightside_selected){
			//System.out.println("mouserightselected works");
			ifchanging_shape = true;
			int index = which_rec_right_side_selected(mouse_click_x,mouse_click_y);
			change_shape_index = index;
			int dx = e.getX() - mouse_click_x;
			int dy = e.getY() - mouse_click_y;
			columns.add(bird);
			Rectangle column = columns.get(index);
			//if(!obstacle_width_change){
			
			//System.out.println(column.width);
			//System.out.println(column.height);
			//}
			for(int i =0; i < columns.size();i++){
				//if(i != index){
				Rectangle check = columns.get(i);
				if(column.x+column.width+dx>check.x && column.x+dx < check.x && ((column.y<=check.y+check.height&&column.y+column.height>check.y+check.height)||(column.y<check.y&&column.y+column.height>check.y)) ){
					dx = check.x - column.x-column.width;
					break;
				}	
			//}
				
			}
			//obstacle_width_change = true;
			column.width +=dx;
			rec_location_shape  = column;
			model.updateAllViews();
			bird = columns.get(columns.size()-1);
			columns.remove(columns.size()-1);
			mouse_click_x += dx;
			mouse_click_y += dy;
			//System.out.println("right redimention works");
	}
	else if(botside_selected){
		ifchanging_shape = true;
			int index = which_rec_bot_side_selected(mouse_click_x,mouse_click_y);
			change_shape_index = index;
			int dx = e.getX() - mouse_click_x;
			int dy = e.getY() - mouse_click_y;
			columns.add(bird);
			Rectangle column = columns.get(index);
			for(int i =0; i < columns.size();i++){
				//if(i != index){
				Rectangle check = columns.get(i);
				if(column.y+dy+column.height>check.y && column.y+dy<check.y &&((column.x<check.x&&column.x+column.width>check.x)||(column.x<check.x+check.width&&column.x+column.width>check.x+check.width))){
					dy = check.y-column.y-column.height;
					break;
				}	
			//}
				
			}
			column.height +=dy;
			rec_location_shape  = column;
			model.updateAllViews();
			bird = columns.get(columns.size()-1);
			columns.remove(columns.size()-1);
			mouse_click_x += dx;
			mouse_click_y += dy;
			//System.out.println("bot redimention works");
	}
	else{}
	}
	
	public void mouseReleased(MouseEvent e){
		//if(!whether_right_click){
		rightside_selected = false;
		botside_selected = false;
		if (ifMoving) {
			model.UM.change_location(rec_location_drag);
			//System.out.println("666");
		}
		if(ifchanging_shape = true){
			model.UM.change_shape(rec_location_shape);
		}
		//}
		//model.UW.
		obstacle_location_change = false;
		//obstacle_width_change = false;
		ifMoving = false;
		ifchanging_shape = false;
		
	}
	
	
	}
	
	public boolean onRightSide(int x, int y) {
		// TODO Auto-generated method stub
		boolean temp_check = false;
		for(int i =0; i< columns.size();i++){
			Rectangle changed_rec = columns.get(i);
			if(Math.abs(x - (changed_rec.x+changed_rec.width)) <= 10 && y >= changed_rec.y && y <= changed_rec.y+changed_rec.height){
				temp_check = true;
				break;
			}
		}
		return temp_check;
	}
	
	protected void Delete() {
		// TODO Auto-generated method stub
		columns.remove(copy_obstacle);
		model.UM.delete(copy_obstacle);
		Change_Content();
		model.updateAllViews();
	}
	protected void Past() {
		// TODO Auto-generated method stub
		int x_axis = copy_obstacle.x + 100;
		int y_axis = copy_obstacle.y + 100;
		Rectangle copy_one = new Rectangle(x_axis,y_axis,copy_obstacle.width,copy_obstacle.height);
		columns.add(copy_one);
		model.UM.add(copy_one);
		Change_Content();
		model.updateAllViews();
	}
	protected void Cut() {
		whether_copy = true;
		columns.remove(copy_obstacle);
		model.UM.delete(copy_obstacle);
		Change_Content();
		model.updateAllViews();
	}
	protected void Copy() {
		// TODO Auto-generated method stub
		whether_copy = true;
		repaint();
	}
	public int which_rec_right_side_selected(int x, int y){
		int index = 0;
		for(int i =0; i< columns.size();i++){
			Rectangle changed_rec = columns.get(i);
			if(Math.abs(x - (changed_rec.x+changed_rec.width)) <= 10 && y >= changed_rec.y && y <= changed_rec.y+changed_rec.height){
				index = i;
				break;
			}
		}
		return index;
	}
	
	
	public boolean onBotSide(int x, int y) {
		// TODO Auto-generated method stub
		boolean temp_check = false;
		for(int i =0 ; i<columns.size();i++){
			Rectangle changed_rec = columns.get(i);
			if(Math.abs(y - (changed_rec.y+changed_rec.height)) <=10 && x < changed_rec.x+changed_rec.width && x > changed_rec.x){
				temp_check = true;
				break;
			}
		}
		return temp_check;
	}
	public int which_rec_bot_side_selected(int x,int y){
		int index = 0;
		for(int i =0; i< columns.size();i++){
			Rectangle changed_rec = columns.get(i);
			if(Math.abs(y - (changed_rec.y+changed_rec.height)) <=10 && x < changed_rec.x+changed_rec.width && x > changed_rec.x){
				index = i;
				break;
			}
		}
		return index;
	}
	/**
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		Undo.setEnabled(model.UM.canUndo());
		Redo.setEnabled(model.UM.canRedo());	
	}
	**/
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}


