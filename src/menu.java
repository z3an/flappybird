import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;


public class menu extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Rectangle play, options, quit;
	//private static int centerY = 800/2;
	public menu(final frame frame){
		JButton play = new JButton("Play"), options = new JButton("Options"), quit = new JButton("quit");
		setLayout((LayoutManager) new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.add(Box.createVerticalStrut(100));
        play.setFont(play.getFont().deriveFont(32.0f));
        play.setAlignmentX(CENTER_ALIGNMENT);
        play.setMaximumSize(new Dimension(450, 80));
        this.add(play);
        play.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.change_from_menu_to_game();
			}
        	
        });
        
        this.add(Box.createVerticalStrut(100));
        options.setFont(play.getFont().deriveFont(32.0f));
        options.setAlignmentX(CENTER_ALIGNMENT);
        options.setMaximumSize(new Dimension(450, 80));
        this.add(options);
        options.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.change_from_menu_to_editor();
			}
        	
        });
        
        this.add(Box.createVerticalStrut(100));
        quit.setFont(play.getFont().deriveFont(32.0f));
        quit.setAlignmentX(CENTER_ALIGNMENT);
        quit.setMaximumSize(new Dimension(450, 80));
        this.add(quit);
        quit.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
        });
	}

}
