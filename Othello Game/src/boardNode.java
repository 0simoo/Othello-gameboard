
public class boardNode {

	private boardNode next = null;
	private int[][] boardStatus;
	
	public boardNode(int[][] b) {
		boardStatus = b;
	}
	
	public boardNode getNext() {
		return next;
	}
	
	public int[][] getData(){
		return boardStatus;
	}
	
	public void setNext(boardNode obj) {
		next = obj;
	}
	
	public void setData(int[][] a) {
		boardStatus = a;
	}

}
