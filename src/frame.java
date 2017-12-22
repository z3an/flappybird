import javax.swing.*;

public class frame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static menu m_menu;
	public editor editor;
	private StringBuilder content =new StringBuilder("");
	public static GameState state = GameState.MENU;
	public static JFrame frame = new JFrame("2D Game");
	public void start_menu(){
		this.add(m_menu);
	}
	public frame(){
		m_menu = new menu(this);
		this.add(m_menu);
	}
	public void change_from_menu_to_game(){
		this.remove(m_menu);
		this.state = GameState.GAME;
		board m_game = new board(this,this.content);
		this.add(m_game);
		this.pack();
		this.setSize(800, 800);
		m_game.requestFocusInWindow();
//		System.out.println("77777777777");
	}
	
	public void change_from_menu_to_game2(StringBuilder content){
		this.remove(m_menu);
		this.remove(editor);
		this.state = GameState.GAME;
		board m_game = new board(this,content);
		this.add(m_game);
		this.pack();
		this.setSize(800, 800);
		m_game.requestFocusInWindow();
//		System.out.println("77777777777");
	}
	
	public void change_from_menu_to_editor(){
		this.remove(m_menu);
		this.state = GameState.OPTIONS;
		editor m_editor = new editor(this);
		editor = m_editor;
		this.add(m_editor);
		this.pack();
		this.setSize(800, 800);
//		m_editor.requestFocusInWindow();
	}
	
	public void removeBoard(board b) {
		this.remove(b);
		m_menu = new menu(this);
		this.add(m_menu);
		this.pack();
		this.setSize(800, 800);
	}
	
	public void removeEditor(editor e){
		this.remove(e);
		m_menu = new menu(this);
		this.add(m_menu);
		this.pack();
		this.setSize(800, 800);
	}
	
	/**
	public void change_pause_to_play(){
		this.state = GameState.GAME;
	}
	public void change_play_to_pause(){
		this.state = GameState.PAUSE;
	}
	**/
}
