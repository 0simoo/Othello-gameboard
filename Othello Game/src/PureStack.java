/**
 * Stack interface
 * @author 0simo
 *
 */
public interface PureStack<E> {
	
	/**
	 * lets us know if the stack has any elements or not
	 * @return - a boolean that tells us if the stack is empty
	 */
	boolean isEmpty();
	
	/**
	 * adds an element onto the top of the stack
	 * @param item - the item to be added
	 */
	void push(E item);
	
	/**
	 * removes the top element
	 * @return - the element removed
	 */
	E pop();
	
	/**
	 * Tells us the top element
	 * @return - the element on the top of the stack
	 */
	E peek();
	
	
	

}//PureStack queue
