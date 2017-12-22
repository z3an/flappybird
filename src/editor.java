import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;







public class editor extends JPanel implements Observer{ 
	public static Rectangle File;
	public static frame frame_value;
	public Model editor_model = new Model();
	public static StringBuilder content = new StringBuilder("");
	public JTextField world_height = new JTextField(10);;
    public JTextField world_width = new JTextField(10);;
    public JTextField column_height = new JTextField(10);;
    public JTextField column_width = new JTextField(10);;
    public JButton add_button = new JButton("Add");
    public View2 tmp_view;
    public JDialog column_dialog;
    public JMenuItem undoMenuItem;
    public JMenuItem redoMenuItem;
	
	public editor(final frame frame2) {
		
		frame_value = frame2;
	
		this.setLayout(new BorderLayout()); 
		
		editor_model.UM.addObserver(this); 
		
		JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem exitMenuItem = new JMenuItem("Back");
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        //JMenuItem cutMenuItem = new JMenuItem("Cut");
        //JMenuItem copyMenuItem = new JMenuItem("Copy");
        //JMenuItem pasteMenuItem = new JMenuItem("Paste");
        undoMenuItem = new JMenuItem("Undo"); 
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        //cutMenuItem.setEnabled(false);
        //copyMenuItem.setEnabled(false);
        //pasteMenuItem.setEnabled(false);
        // TODO: add undo
        redoMenuItem = new JMenuItem("Redo"); 
        redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        //editMenu.add(cutMenuItem);
        //editMenu.add(copyMenuItem);
        editMenu.add(redoMenuItem);
        editMenu.add(undoMenuItem);
        menuBar.add(editMenu);


        JMenu addMenu = new JMenu("Add");
        JMenuItem add_new_column = new JMenuItem("New Column");
        addMenu.add(add_new_column);
        
        
        
        JMenuItem add_new_world = new JMenuItem("New World");
        addMenu.add(add_new_world);
        
        
        add_new_world.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        add_new_world.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                JDialog column_dialog = new JDialog(frame2,"World Configuration",true);
                
                
                
                column_dialog.setPreferredSize(new Dimension(300,300));
                column_dialog.setMinimumSize(new Dimension(300,300));
                
                JPanel innerPanel = new JPanel();
        		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS)); 
        		innerPanel.add(Box.createVerticalStrut(50));
        		innerPanel.add(Box.createHorizontalStrut(50));
        		innerPanel.add(new JLabel("World H:"));
        		//world_height = new JTextField(10);
        		//world_width = new JTextField(10);
        		innerPanel.add(world_height);
        		world_height.setPreferredSize(new Dimension(100,20));
        		world_height.setMaximumSize(world_height.getPreferredSize());
        		world_height.setAlignmentX(JTextField.LEFT_ALIGNMENT);
        		innerPanel.add(Box.createVerticalStrut(50));
        		innerPanel.add(new JLabel("World W:"));
        		innerPanel.add(world_width);
        		world_width.setPreferredSize(new Dimension(100,20));
        		world_width.setMaximumSize(world_width.getPreferredSize());
        		world_width.setAlignmentX(JTextField.LEFT_ALIGNMENT);
        		innerPanel.add(Box.createVerticalStrut(50));
        		column_dialog.add(innerPanel);
        		
        		column_dialog.setVisible(true);
        		
        		
        		
        		
            }
        });
        
        
        world_height.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				double ht = Double.parseDouble(world_height.getText());
				editor_model.set_world_Height(ht);
				//System.out.println("world height adction works");
			}
		});
		world_width.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				double wd = Double.parseDouble(world_width.getText());
				editor_model.set_world_Width(wd);
				//System.out.println("world width action works");
			}
		});
		
		add_new_column.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		add_new_column.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
            	
            	column_dialog = new JDialog(frame2,"Column Configuration",true);

                column_dialog.setPreferredSize(new Dimension(300,300));
                column_dialog.setMinimumSize(new Dimension(300,300));
                
                JPanel innerPanel = new JPanel();
        		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS)); 
        		innerPanel.add(Box.createVerticalStrut(50));
        		innerPanel.add(Box.createHorizontalStrut(50));
        		innerPanel.add(new JLabel("Column H:"));
        		//world_height = new JTextField(10);
        		//world_width = new JTextField(10);
        		innerPanel.add(column_height);
        		column_height.setPreferredSize(new Dimension(100,20));
        		column_height.setMaximumSize(world_height.getPreferredSize());
        		column_height.setAlignmentX(JTextField.LEFT_ALIGNMENT);
        		innerPanel.add(Box.createVerticalStrut(50));
        		innerPanel.add(new JLabel("Column W:"));
        		innerPanel.add(column_width);
        		column_width.setPreferredSize(new Dimension(100,20));
        		column_width.setMaximumSize(world_width.getPreferredSize());
        		column_width.setAlignmentX(JTextField.LEFT_ALIGNMENT);
        		
        		innerPanel.add(Box.createVerticalStrut(50));
        		
     
        		innerPanel.add(add_button);
        		
        		
        		innerPanel.add(Box.createVerticalStrut(50));
        		column_dialog.add(innerPanel);
        		
        		column_dialog.setVisible(true);
        		
            }
        });
        
		add_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
            	int cw = Integer.valueOf(column_width.getText());
            	int ch = Integer.valueOf(column_height.getText());
            	editor_model.addNewColumn(cw, ch);
            	column_dialog.dispose();
            }
		});

       
		//Undo.setEnabled(true);
		undoMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//System.out.println("this is in Undo");
				editor_model.UM.undo();
				//				View2.this.repaint();
			}
		});
		
		redoMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//System.out.println("this is in Undo");
				editor_model.UM.redo();
				//				View2.this.repaint();
			}
		});
		
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//System.out.println("this is in Undo");
				frame_value.removeEditor(editor.this);
				//				View2.this.repaint();
			}
		});
		
		
        menuBar.add(addMenu);

        this.add(menuBar, BorderLayout.NORTH);
		
		
		
		
		
        saveMenuItem.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//String sb = "TEST CONTENT";
				JFileChooser saveFile = new JFileChooser();
				//saveFile.setCurrentDirectory(new File("/home/Desktop"));
                int retrival = saveFile.showSaveDialog(null);
                if(retrival == JFileChooser.APPROVE_OPTION){
                	try{
                		content = tmp_view.Change_Content();
                		FileWriter fw = new FileWriter(saveFile.getSelectedFile()+".txt");
                		fw.write(content.toString());
                		fw.close();
                	}
                	catch(Exception ex){ex.printStackTrace();}
                }
			}
        	
        });
        
        openMenuItem.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser openFile = new JFileChooser();
				int retrival = openFile.showOpenDialog(null);
				if(retrival == JFileChooser.APPROVE_OPTION){
					try{
						content.delete(0, content.length());
						FileReader reader = null;
						File file=openFile.getSelectedFile();
						reader = new FileReader(file);
						int i=1;
			            while(i!=-1)
			            {
			                i=reader.read();
			                char ch=(char) i;
			                content.append(ch);

			            }
			            reader.close();
			            
			            frame_value.change_from_menu_to_game2(content);
			    	
			            //System.out.println("content in file" + content.toString());
					}
					catch(Exception ex){ex.printStackTrace();}
				}
			}
        	
        });
        //System.out.println("view size" + editor_model.views.size());  //size 0
        final View view1 = new View(editor_model);
        //System.out.println("view size" + editor_model.views.size());  size 1  
        editor_model.addView(view1);
        //System.out.println("view size" + editor_model.views.size()); 	size 2
        this.add(view1);
        //System.out.println("view size" + editor_model.views.size());  //size 2
        tmp_view = new View2(editor_model,content);
        editor_model.addView(tmp_view);
        //System.out.println("view size" + editor_model.views.size());   size 3
        editor_model.addColumnsList(tmp_view.columns);
		
        view1.addcolumns(editor_model.columns);
        view1.addview(tmp_view);
        //System.out.println("view size" + editor_model.views.size());   //size 3
		editor_model.UM.updateViews();
		
        
        
		// TODO Auto-generated constructor stub
	}

    
    public void update(Observable o, Object arg) {
        undoMenuItem.setEnabled(editor_model.UM.canUndo());
    }
}
