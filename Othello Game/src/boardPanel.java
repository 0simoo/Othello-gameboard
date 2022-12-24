import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;

import javax.swing.*;

/**
 * gameboard is a JPanel that holds a grid of buttons that represent the Othello board
 * @author Simon Shin
 *
 */
public class boardPanel extends JPanel implements ActionListener, PureStack<int[][]>{
	
	private int colorStatus = 1;
	
	private boardNode top = null;
	private JPanel gameboard = new JPanel();
	private boardButton[][] buttonArray = new boardButton[8][8];
	private JLabel text = new JLabel("");

	/**
	 * Constructor
	 */
	public boardPanel(int w, int h) {
  		super();
		
  		gameboard.setPreferredSize(new Dimension(800*(w/800), 800*(h/800)));
  		
		gameboard.setLayout(new GridLayout(8, 8));
		 
		//fills the buttonArray and sets stuff up
		for(int y=0; y <= 7; y++) {
			for(int x=0; x <= 7; x++) {
				buttonArray[y][x] = new boardButton();
				buttonArray[y][x].setActionCommand(y + " " + x);
			    buttonArray[y][x].addActionListener( this );
			    buttonArray[y][x].setStatus(0);
			    
				gameboard.add(buttonArray[y][x]);
				}
			}
		
		//the four starting disks
		buttonArray[3][3].setStatus(2);
		buttonArray[3][4].setStatus(1);
		buttonArray[4][3].setStatus(1);
		buttonArray[4][4].setStatus(2);
		updateIsValid();
		
		
		gameboard.setVisible( true ); 
		gameboard.revalidate();
		
		push(buttonArray); //begins the stack
		
		JButton undoButton = new JButton("undo");
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		undoButton.setActionCommand("undo");
		undoButton.addActionListener(this);
		
		this.add(undoButton);
		this.add(gameboard);
		
		repaint();
	}
	
	public int getColorStatus() {
		return colorStatus;
	}
	
	public boardButton[][] getButtonArray(){
		return buttonArray;
	}
	
	
	/**
	 * updates all the empty disks and checks if they're valid move to play
	 * @return - if a move is available to play
	 */
	public boolean updateIsValid() {
		boolean returngameboard = false;
		
		for(int y=0; y<=7; y++) {
			for(int x=0; x<=7; x++) { //for all tiles in the board
				if(buttonArray[y][x].isEmpty()) { //If gameboard position is empty
					if(coordsValid(y-1, x)) { //if top valid
						if(lineCheck(y, x, -1, 0)) { //check top
							returngameboard = true;
							buttonArray[y][x].setStatus(colorStatus+2);
							buttonArray[y][x].addDirection(-1, 0);
						}
					}
					if(coordsValid(y-1, x+1)) { //if top right valid
						if(lineCheck(y, x, -1, 1)) { //check top right
							returngameboard = true;
							buttonArray[y][x].setStatus(colorStatus+2);
							buttonArray[y][x].addDirection(-1, 1);
						}
					}
					if(coordsValid(y, x+1)) { //if right valid
						if(lineCheck(y, x, 0, 1)) { //check right
							returngameboard = true;
							buttonArray[y][x].setStatus(colorStatus+2);
							buttonArray[y][x].addDirection(0, 1);
						}
					}
					if(coordsValid(y+1, x+1)) { //if bottom right valid
						if(lineCheck(y, x, 1, 1)) { //check bottom right
							returngameboard = true;
							buttonArray[y][x].setStatus(colorStatus+2);
							buttonArray[y][x].addDirection(1, 1);
						}
					}
					if(coordsValid(y+1, x)) { //if bottom valid
						if(lineCheck(y, x, 1, 0)) { //check bottom
							returngameboard = true;
							buttonArray[y][x].setStatus(colorStatus+2);
							buttonArray[y][x].addDirection(1, 0);
						}
					}
					if(coordsValid(y+1, x-1)) { //if bottom left valid
						if(lineCheck(y, x, 1, -1)) { //check bottom left
							returngameboard = true;
							buttonArray[y][x].setStatus(colorStatus+2);
							buttonArray[y][x].addDirection(1, -1);
						}
					}
					if(coordsValid(y, x-1)) { //if left valid
						if(lineCheck(y, x, 0, -1)) { //check left
							returngameboard = true;
							buttonArray[y][x].setStatus(colorStatus+2);
							buttonArray[y][x].addDirection(0, -1);
						}
					}
					if(coordsValid(y-1, x-1)) { //if top left valid
						if(lineCheck(y, x, -1, -1)) { //check top left
							returngameboard = true;
							buttonArray[y][x].setStatus(colorStatus+2);
							buttonArray[y][x].addDirection(-1, -1);
						}
					}
					
				}
			}
		}
		
		return returngameboard;
	}
	
	/**
	 * Checks if a tile and a direction (so a line) is valid for a move to be made on said tile
	 * @param y-coordinates of the tile we're trying to verify
	 * @param x-see above
	 * @param yModifier-which direction are we going?
	 * @param xModifier-see above
	 * @returns
	 */
	public boolean lineCheck(int y, int x, int yModifier, int xModifier) {
		y+= yModifier;
		x+= xModifier;
		if(coordsValid(y, x)) { //if coordinates are valid
			if(buttonArray[y][x].getStatus() != -colorStatus+3) { //if the disk right next to ours isn't the opposite color, then it's not a valid line for a move
			return false;
			}
		}
		y+= yModifier;
		x+= xModifier;
		
		while(coordsValid(y, x)) { //while in bounds of array
			if(buttonArray[y][x].getStatus() == colorStatus) { //if the disk we're looking at is the same color as us, the line is valid
				return true;
			}
			else if(buttonArray[y][x].isEmpty()) { //if the disk is empty, the line is not valid
				return false;
			}
			y += yModifier;
			x += xModifier;
		}
		return false;
	}
	
	/**
	 * Answers if the given coordinates are within the bounds of our array (between 0 and 8, inclusive)
	 * @param y
	 * @param x
	 * @return
	 */
	public boolean coordsValid(int y, int x) {
		if(y>=0 && y<=7 && x>=0 && x<=7)
			return true;
		else
			return false;
	}
	
	/**
	 * gets rid of all the faded buttons and sets them to empty again
	 */
	public void wipeFaded() {
		for(int y=0; y<=7; y++) {
			for(int x=0; x<=7; x++) {
				if(buttonArray[y][x].getStatus() == 3 || buttonArray[y][x].getStatus() == 4) {
					buttonArray[y][x].setStatus(0);
					buttonArray[y][x].wipeDirection();
				}
			}
		}
	}
	
	/**
	 * Flips the tile appropriately given the coordinates of a tile
	 * @param y-the coords
	 * @param x-da coords
	 */
	public void flipTile(int y, int x) {
		System.out.println("flip tile at " + y + ", " + x);
		Integer[] directionArray = buttonArray[y][x].getDirection();
		buttonArray[y][x].setStatus(colorStatus);
		
		int yMod;
		int xMod;
		
		int lookingY = y;
		int lookingX = x;
		
		for(int i=0; i<directionArray.length-1; i+=2) {
			
			yMod = directionArray[i];
			xMod = directionArray[i+1];
			lookingY = y+yMod;
			lookingX = x+xMod;
			
			while(coordsValid(lookingY, lookingX) && buttonArray[lookingY][lookingX].getStatus() == -colorStatus+3) { //while in bounds and the tile we're looking at is a different color
				buttonArray[lookingY][lookingX].setStatus(colorStatus);
				lookingY += yMod;
				lookingX += xMod;
			}
		}
	}
	

	/**
	 * Perform stuff when button is clicked
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("undo")) { //undo button undoes stuff
			if(!text.getText().equals("")) {
				text.setText("");
			}
			pop();
			
			for(int y=0; y<=7; y++) {
				for(int x=0; x<=7; x++) {
					buttonArray[y][x].setStatus(top.getData()[y][x]);
				}
			}
			colorStatus = -colorStatus+3;
			if(!updateIsValid()) { //if we just undid a move into a state where the player can't do anything, we have to undo one more time
				
			}
			repaint(); 
			return;
		}
		String[] temp = e.getActionCommand().split(" ");
		int y = Integer.parseInt(temp[0]);
		int x = Integer.parseInt(temp[1]);
		if(buttonArray[y][x].getStatus() == colorStatus+2) { //If the button clicked on is faded, meaning that is a valid
			
			flipTile(y, x);
			colorStatus = -colorStatus + 3; //this equation turn 1 into 2 and turns 2 into 1			
			wipeFaded();
			push(buttonArray);
			if(!updateIsValid()) { //if no available moves to be played
				
				colorStatus = -colorStatus+3; //change colors and check for valid moves to be played again
				if(!updateIsValid()) { //if no available moves to be played again
					//tell player that there are no moves to be played and the games ends
					int black = 0;
					int white = 0;
					
					for(int i=0; i<=7; i++) {
						for(int j=0; j<=7; j++) {
							if(buttonArray[i][j].getStatus() == 1) {
								black++;
							}
							else if(buttonArray[i][j].getStatus() == 2) {
								white++;
							}
						}
					}
					
					if(black>white) {
						text.setText("NO MOVES LEFT, BLACK WINS: " + black + "-" + white);
					}
					else if(white>black) {
						text.setText("NO MOVES LEFT, WHITE WINS: " + black + "-" + white);
					}
					else {
						text.setText("NO MOVES LEFT, GAME TIE");
					}
					wipeFaded();
					System.out.println("GAME OVER");
					JFrame endWindow = new JFrame("GAME OVER");
					endWindow.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
					endWindow.setSize( 300,300 );
					endWindow.add(text);
					text.setHorizontalAlignment((int)endWindow.CENTER_ALIGNMENT);
					endWindow.setVisible(true);
				}
			}
			repaint();
		}
	}

	
	public boolean isEmpty() {
		if(top == null) 
			return true;
		else
			return false;
	}
	

	public void push(boardButton[][] item) {
		int[][] temp = new int[8][8];
		for(int i=0; i<=7; i++) {
			for(int j=0; j<=7; j++) {
				temp[i][j] = item[i][j].getStatus();
			}
		}
		boardNode newNode = new boardNode(temp);
		newNode.setNext(top);
		top = newNode;
	}

	
	public int[][] pop() {
		if(top.getNext() == null) {
			throw new NoSuchElementException("Hold on don't pop this");
		}
		int[][] poppedData = top.getData();
		top = top.getNext();
		return poppedData;
	}

	
	public int[][] peek() {
		if(isEmpty()) {
			throw new NoSuchElementException("Stack is empty");
		}
		return top.getData();
	}

	/**
	 * ok so like, I already have a push method that takes a boardButton[][] and turns it into int[][] in the method, and I like having it that way better, but Eclipse wants me to have this method too so
	 * i guess I'll just have this empty?? i feel like i'm doing something terrible but I don't wanna figure it out
	 */
	public void push(int[][] item) {
		// TODO Auto-generated method stub
		
	}
	
	

}
