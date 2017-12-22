import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;





public class View extends JPanel implements IView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Model model;
	public JTextField world_height = new JTextField(10);
	public JTextField world_width = new JTextField(10);
	public JTextField obstacle_width = new JTextField(10);
	public JTextField obstacle_height = new JTextField(10);
	public JPanel innerpanel;
	public JScrollPane scrollPane;
	public ArrayList<Rectangle> columns_view;
	public DefaultTableModel newModel;
	public int help_for_table = -1;
	
	
	public View(Model aModel) {
		
		
		super();
		this.model = aModel;
		columns_view = model.columns;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); 

		// Add a this view as a listener to the model
		//this.model.addView(this);
		
		
		JPanel innerPanel = new JPanel();
		String[] columnNames = {"Name", "X","Y","Width","Height"};
		DefaultTableModel D_model = new DefaultTableModel(columnNames, 0 );
		JTable t = new JTable(D_model);
		innerPanel.setLayout(new BorderLayout());
		
		
		newModel = (DefaultTableModel)t.getModel();
		
		newModel.addTableModelListener(new TableModelListener(){

			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				int row = e.getFirstRow();
				int column = e.getColumn();
				if(column == -1){
					return;
				}
				String value_in_table = String.valueOf(newModel.getValueAt(row, column));
				//System.out.println(value_in_table);
				if(column == 1){
					columns_view.get(row).x = Integer.valueOf(value_in_table);
				}
				else if(column ==2){
					columns_view.get(row).y = Integer.valueOf(value_in_table);
				}
				else if(column ==3){
					columns_view.get(row).width = Integer.valueOf(value_in_table);
				}
				else if(column ==4){
					columns_view.get(row).height = Integer.valueOf(value_in_table);
				}
				else{
					System.out.println("out of column range");
				}
				//System.out.println(columns_view.size());
				(model.views).get(1).updateView();
			}
			
		});
		//newModel.addRow(new Object[]{"1","2","3","4"});
		
		
		
		
		JScrollPane scroll = new JScrollPane(t);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//scroll.setBounds( 0, 20, 150, 100 );
		innerPanel.add(scroll);
		this.add(innerPanel);
		//System.out.println("7777");
	}
		
	
	public void updateView() {
		// TODO Auto-generated method stub
		//updatetable();
		//model add view 1 called updateview once it will call this first time
		//then add view2 called updateview twice it will call this second time
		if(columns_view != null){
			int rowCount = newModel.getRowCount();
			for (int i = rowCount - 1; i >= 0; i--) {
			    newModel.removeRow(i);
			}
			
			int columns_size = columns_view.size();
			for(int i =0 ; i < columns_size;i++){
				String index = Integer.toString(i);
				String name = "Obs " + index;
				if(columns_view.get(i) == model.bird){
					name = "Bird";
				}
				String obstacle_x = Integer.toString(columns_view.get(i).x);
				String obstacle_y = Integer.toString(columns_view.get(i).y);
				String obstacle_width = Integer.toString(columns_view.get(i).width);
				String obstacle_height = Integer.toString(columns_view.get(i).height);
				newModel.addRow(new Object[]{name,obstacle_x,obstacle_y,obstacle_width,obstacle_height});
			}
			//System.out.println("this is view1 updateview");
			//System.out.println("columns size" + columns_view.size());
		}
		
	}
	public void addview(View2 view2) {
		// TODO Auto-generated method stub

        //Create the scroll pane and add the table to it.
        scrollPane = new JScrollPane(view2);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //Add the scroll pane to this panel.
        this.add(scrollPane);
		//this.add(view2);
	}
	public void dropview() {
		// TODO Auto-generated method stub
		this.remove(scrollPane);
	}


	public void addcolumns(ArrayList<Rectangle> columns) {
		// TODO Auto-generated method stub
		columns_view = columns;
	}
	
}
