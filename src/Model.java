import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.undo.UndoManager;
import java.util.Observable;
import javax.swing.undo.*;

public class Model extends Object{
	ArrayList<IView> views = new ArrayList<IView>();
	public double world_height = 800.0;  // length of the base
	public double world_width = 800.0;  // height of the triangle
	public double obstacle_width = 50.0; 
	public double obstacle_height = 300.0; 
	private UndoManager undoManager;
	public UndoModel UM = new UndoModel();
	public ArrayList<Rectangle> columns;
	public Rectangle bird = new Rectangle();
	
	class UndoModel extends Observable{
		public Double obstacle_width_d = new Double(obstacle_width);
		public int obstacle_width_i = obstacle_width_d.intValue();
		public Double obstacle_height_d = new Double(obstacle_height);
		public int obstacle_height_i = obstacle_height_d.intValue();
		public int obstacle_undo_x = 0;
		public int obstacle_undo_y = 0;
		public Rectangle cur; 

		UndoModel(){
			undoManager = new UndoManager();
		}
		public void updateViews() {
			setChanged();
			notifyObservers();
		}
		public void undo() {
			if (canUndo())
				undoManager.undo();
		}

		public void redo() {
			if (canRedo())
				undoManager.redo();
		}

		public boolean canUndo() {
			return undoManager.canUndo();
		}

		public boolean canRedo() {
			return undoManager.canRedo();
		}
		public void change_location(final Rectangle rec) {
			UndoableEdit undoableEdit = new AbstractUndoableEdit() {
				/**
				 * 
				 */
				//private static final long serialVersionUID = 1L;
				// capture variables for closure
				final int oldValue_obstacle_undo_x = obstacle_undo_x;
				final int oldValue_obstacle_undo_y = obstacle_undo_y;
				
				int newValue_obstacle_undo_x = rec.x;
				int newValue_obstacle_undo_y = rec.y;

				// Method that is called when we must redo the undone action
				
				public void redo() throws CannotRedoException {
					super.redo();
//					/System.out.println("in the redo");				
					rec.x = newValue_obstacle_undo_x; 						
					rec.y = newValue_obstacle_undo_y;
					setChanged();
					Model.this.updateAllViews();
					notifyObservers();
				}
				
				public void undo() throws CannotUndoException {
					super.undo(); 					
					rec.x = oldValue_obstacle_undo_x; 								
					rec.y = oldValue_obstacle_undo_y;
					setChanged();
					Model.this.updateAllViews();
					notifyObservers();
				}
			};

			// Add this undoable edit to the undo manager
			undoManager.addEdit(undoableEdit);
		
			setChanged();
			notifyObservers();
		}
		public void create(Rectangle column) {
			// TODO Auto-generated method stub
			obstacle_undo_x = column.x;
			obstacle_undo_y = column.y;
			//System.out.println("create obstacle_undo_x "+obstacle_undo_x);
			//System.out.println("create obstacle_undo_y "+obstacle_undo_y);
		}
		public void shape(Rectangle column) {
			// TODO Auto-generated method stub
			obstacle_height_i = column.height;
			obstacle_width_i =  column.width;
		}
		public void change_shape(final Rectangle rec_location_right) {
			// TODO Auto-generated method stub
			UndoableEdit undoableEdit = new AbstractUndoableEdit() {
				/**
				 * 
				 */
				//private static final long serialVersionUID = 1L;
				// capture variables for closure
				final int oldValue_obstacle_undo_height = obstacle_height_i;
				final int oldValue_obstacle_undo_width = obstacle_width_i;
				
				int newValue_obstacle_undo_height = rec_location_right.height;
				int newValue_obstacle_undo_width = rec_location_right.width;

				// Method that is called when we must redo the undone action
				
				public void redo() throws CannotRedoException {
					super.redo();
//					/System.out.println("in the redo");				
					rec_location_right.height = newValue_obstacle_undo_height; 						
					rec_location_right.width = newValue_obstacle_undo_width;
					setChanged();
					Model.this.updateAllViews();
					notifyObservers();
				}
				
				public void undo() throws CannotUndoException {
					super.undo(); 					
					rec_location_right.height = oldValue_obstacle_undo_height; 								
					rec_location_right.width = oldValue_obstacle_undo_width;
					setChanged();
					Model.this.updateAllViews();
					notifyObservers();
				}
			};

			// Add this undoable edit to the undo manager
			undoManager.addEdit(undoableEdit);
		
			setChanged();
			notifyObservers();
		}
		public void delete(final Rectangle copy_obstacle) {
			// TODO Auto-generated method stub
			UndoableEdit undoableEdit = new AbstractUndoableEdit() {
				/**
				 * 
				 */
				//private static final long serialVersionUID = 1L;
				// capture variables for closure
				
				// Method that is called when we must redo the undone action
				
				public void redo() throws CannotRedoException {
					super.redo();
//					/System.out.println("in the redo");				
					Model.this.columns.remove(copy_obstacle);
					setChanged();
					Model.this.updateAllViews();
					notifyObservers();
				}
				
				public void undo() throws CannotUndoException {
					super.undo(); 					
					Model.this.columns.add(copy_obstacle);
					setChanged();
					Model.this.updateAllViews();
					notifyObservers();
				}
			};

			// Add this undoable edit to the undo manager
			undoManager.addEdit(undoableEdit);
		
			setChanged();
			notifyObservers();
		}
		public void add(final Rectangle copy_obstacle) {
			// TODO Auto-generated method stub
			UndoableEdit undoableEdit = new AbstractUndoableEdit() {
				/**
				 * 
				 */
				//private static final long serialVersionUID = 1L;
				// capture variables for closure
				
				// Method that is called when we must redo the undone action
				
				public void redo() throws CannotRedoException {
					super.redo();
//					/System.out.println("in the redo");				
					Model.this.columns.add(copy_obstacle);
					setChanged();
					Model.this.updateAllViews();
					notifyObservers();
				}
				
				public void undo() throws CannotUndoException {
					super.undo(); 					
					Model.this.columns.remove(copy_obstacle);
					setChanged();
					Model.this.updateAllViews();
					notifyObservers();
				}
			};

			// Add this undoable edit to the undo manager
			undoManager.addEdit(undoableEdit);
		
			setChanged();
			notifyObservers();
		}
			
	}
	
	public void set_world_Height(double ht) {
		// TODO Auto-generated method stub
		this.world_height = ht;
		this.updateAllViews();
	}

	public void updateAllViews() {
		
		// TODO Auto-generated method stub
		for (IView view : this.views) {
			//System.out.println("update all views");
			view.updateView();
		}
	}

	public double get_world_Height() {
		// TODO Auto-generated method stub
		return this.world_height;
	}

	public void addView(IView view) {
		// TODO Auto-generated method stub
		views.add(view);
		view.updateView();
	}
	public void removeview(IView view){
		views.remove(view);
	}
	
	public void set_world_Width(double wd) {
		// TODO Auto-generated method stub
		this.world_width = wd;
		this.updateAllViews();
	}

	public double get_world_Width() {
		// TODO Auto-generated method stub
		return this.world_width;
	}

	public void set_obstacle_Height(double ht) {
		// TODO Auto-generated method stub
		this.obstacle_height = ht;
		this.updateAllViews();
	}

	public void set_obstacle_Width(double wd) {
		// TODO Auto-generated method stub
		this.obstacle_width = wd;
		this.updateAllViews();
	}

	public double get_obstacle_Height() {
		// TODO Auto-generated method stub
		return this.obstacle_height;
	}

	public double get_obstacle_Width() {
		// TODO Auto-generated method stub
		return this.obstacle_width;
	}

	public void addColumnsList(ArrayList<Rectangle> columns2) {
		// TODO Auto-generated method stub
		columns = columns2;
	}

	public void addNewColumn(int cw, int ch) {
		// TODO Auto-generated method stub
		Rectangle newCol = new Rectangle(0, 100, cw, ch);
		columns.add(newCol);
		//System.out.println("called addNewColumn");
		//System.out.println("columns " + columns.size());
		this.updateAllViews();
	}
	
	
}
