import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;

import javax.swing.*;


public class runner {

	
	public runner() {
		
	}

	public static void main(String[] args) {
		JFrame window = new JFrame("Othello");
		boardPanel board = new boardPanel(window.getWidth(), window.getHeight());
	    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    window.setSize( 800,850);
	   
	    //board.setSize(window.MAXIMIZED_HORIZ/2, window.MAXIMIZED_HORIZ/2);
		
		board.setPreferredSize(new Dimension(window.getWidth(), window.getHeight()));
		
		window.setVisible( true );
		window.add(board);
		
		
	}
	
}