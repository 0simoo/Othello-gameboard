import java.awt.* ;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
public class boardButton extends JButton {
	
	private int status = 0;
	private ArrayList<Integer> directions = new ArrayList<Integer>(0);

	public boardButton() {
		super(new ImageIcon("button1small.png"));
		setSize(50, 50);
		
	}
	
	public int getStatus() {
		return status;
	}
	
	public Integer[] getDirection() {
		return directions.toArray(new Integer[0]);
	}
	
	public void setStatus(int n) {
		status = n;
		if(status == 1) {
			this.setIcon(new ImageIcon("blackBig.png"));
		}
		else if(status == 2) {
			this.setIcon(new ImageIcon("whiteBig.png"));
		}
		else if(status == 3) {
			this.setIcon(new ImageIcon("blackFaded.png"));
		}
		else if(status == 4) {
			this.setIcon(new ImageIcon("whiteFaded.png"));
		}
		else if(status == 0) {
			this.setIcon(new ImageIcon("emptyBig.png"));
		}
	}
	
	public void addDirection(int y, int x) {
		directions.add(y);
		directions.add(x);
	}
	
	public void wipeDirection() {
		directions.clear();
	}
	
	public boolean isEmpty() {
		if(status == 0 || status == 3 || status==4)
			return true;
		else
			return false;
	}

}
