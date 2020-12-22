package hw08;

public class BSTNode<E extends Comparable<E>> {
	protected BSTNode<E> parent;
	protected BSTNode<E> left;
	protected BSTNode<E> right;
	private E data;
	private char color = 'N';
	
	//Normal Constructor
	public BSTNode (E value) {
		this.data = value;
	}
	
	//NIL constructor
	public BSTNode() {
		this.color = 'B';
	}
	
	public void setData (E data) {
		this.data = data;
	}
	
	public E getData () {
		return this.data;
	}
	
	public void setColor(char c) {
		if (c == 'R' || c == 'B' || c == 'D' || c == 'N') {
			this.color = c;
		}
		else if (c == 'r' || c == 'b' || c == 'd' || c == 'n') {
			this.color = Character.toUpperCase(c);
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	public char getColor() {
		return this.color;
	}
}
