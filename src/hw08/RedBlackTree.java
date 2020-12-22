package hw08;

public class RedBlackTree<E extends Comparable<E>> extends BinarySearchTree<E> {
	
	//Initialize NIL using NIL constructor (sets color to black and leaves everything else null)
	protected final BSTNode<E> NIL = new BSTNode<>();
	
	public RedBlackTree() {
		super();
	}
	public RedBlackTree(E ... values) {
		for (E value : values) {
			this.insert(value);
		}
	}
	@Override
	public void insert(E key) {
		if (key == null) {
			throw new NullPointerException();
		}
		BSTNode<E> child = new BSTNode<E>(key);
		child.setColor('R');
		if (this.root == null) {
			this.root = child;
		}
		else {
			try {
				BSTNode<E> parent = insertionPoint(key);
				if (key.compareTo(parent.getData()) < 0) {
					parent.left = child;
					child.parent = parent;
				}
				else if (key.compareTo(parent.getData()) > 0) {
					parent.right = child;
					child.parent = parent;
				}
			}
			catch (DuplicateItemException e){
				throw e;
			}
		}
		child.left = this.NIL;
		child.right = this.NIL;
		insertCleanup(child);
	}
	private void insertCleanup (BSTNode<E> node) {
		if (this.root.getColor() == 'R') {
			//Case 1
			this.root.setColor('B');
		}
		else if (node.parent.getColor() == 'B') {
			//Case 2
		}
		else if (node.parent.getColor() == 'R' && uncle(node).getColor() == 'R') {
			//Case 3
			node.parent.setColor('B');
			uncle(node).setColor('B');
			grandparent(node).setColor('R');
			insertCleanup(grandparent(node));
		}
		else if (node.parent.getColor() == 'R' && uncle(node).getColor() == 'B') {
			if (isRightChild(node) && isLeftChild(node.parent)) {
				//Case 4a
				BSTNode<E> p = node.parent;
				leftRotate(node.parent);
				node = p;
			}
			else if (isLeftChild(node) && isRightChild(node.parent)) {
				//Case 4b
				BSTNode<E> p = node.parent;
				rightRotate(node.parent);
				node = p;
			}
			if (isLeftChild(node) && isLeftChild(node.parent)) {
				//Case 5a
				node.parent.setColor('B');
				grandparent(node).setColor('R');
				rightRotate(grandparent(node));
			}
			else if(isRightChild(node) && isRightChild(node.parent)) {
				node.parent.setColor('B');
				grandparent(node).setColor('R');
				leftRotate(grandparent(node));
			}
			
		}
	}
	
	@Override
	public void delete(E key) {
		delete(nodeToDelete(key));
	}
	private void delete(BSTNode<E> node) {
		if (numChildren(node) == 0) {
			if (node == this.root) {
				this.root = null;
			}
			else {
				if (isLeftChild(node)) {
					node.parent.left = this.NIL;
				}
				else if (isRightChild(node)) {
					node.parent.right = this.NIL;
				}
				this.NIL.parent = node.parent;
				if (node.getColor() == 'B') {
					this.NIL.setColor('D');
					fixDoubleBlack(this.NIL);
				}
			}
			
		}
		else if (numChildren(node) == 1) {
			BSTNode<E> child = null;
			if (node.right != NIL) {
				child = node.right;
			}
			if (node.left != NIL) {
				child = node.left;
			}
			if (node == this.root) {
				this.root = child;
				child.parent = null;
				insertCleanup(this.root);
			}
			else {
				if (isLeftChild(node)) {
					node.parent.left = child;
					child.parent = node.parent;
				}
				else if (isRightChild(node)) {
					node.parent.right = child;
					child.parent = node.parent;
				}
				
				if (child.getColor() == 'R' || node.getColor() == 'R') {
					child.setColor('B');
				}
				else if (child.getColor() == 'B' && node.getColor() == 'B') {
					child.setColor('D');
					fixDoubleBlack(child);
				}
			}
		}
		else if (numChildren(node) == 2) {
			BSTNode<E> max = maxLeftSubtree(node);
			node.setData(max.getData());
			delete(max);
		}
	}
	private void fixDoubleBlack(BSTNode<E> node) {
		BSTNode<E> p = node.parent;
		BSTNode<E> s = sibling(node);
		if (p == null) {
			node.setColor('B');
		}
		else if (s.getColor() == 'R') {
			if (isRightChild(node)) {
				s.setColor('B');
				p.setColor('R');
				rightRotate(p);
				fixDoubleBlack(node);
			}
			else if (isLeftChild(node)) {
				s.setColor('B');
				p.setColor('R');
				leftRotate(p);
				fixDoubleBlack(node);
			}
		}
		else if (checkRedChildren(s)) {
			if (isLeftChild(s)) {
				if (s.right.getColor() == 'R') {
					BSTNode<E> rc = s.right;
					leftRotate(s);
					rightRotate(p);
					rc.setColor(p.getColor());
					s.setColor('B');
					p.setColor('B');
					node.setColor('B');
				}
				else if (s.left.getColor() == 'R') {
					BSTNode<E> rc = s.left;
					rightRotate(p);
					s.setColor(p.getColor());
					rc.setColor('B');
					p.setColor('B');
					node.setColor('B');
				}
			}
			else if (isRightChild(s)) {
				if (s.left.getColor() == 'R') {
					BSTNode<E> rc = s.left;
					rightRotate(s);
					leftRotate(p);
					rc.setColor(p.getColor());
					s.setColor('B');
					p.setColor('B');
					node.setColor('B');
				}
				else if (s.right.getColor() == 'R') {
					BSTNode<E> rc = s.right;
					leftRotate(p);
					s.setColor(p.getColor());
					rc.setColor('B');
					p.setColor('B');
					node.setColor('B');
				}
			}
		}
		else if ((s.getColor() == 'B' || s.getColor() == 'D') && (s.left.getColor() == 'B' || s.left.getColor() == 'D') && (s.right.getColor() == 'B' || s.left.getColor() == 'D')) {
			if (p.getColor() == 'R') {
				s.setColor('R');
				p.setColor('B');
				node.setColor('B');
			}
			else if (p.getColor() == 'B') {
				s.setColor('R');
				p.setColor('D');
				node.setColor('B');
				fixDoubleBlack(p);
			}
		}
	}
	private boolean checkRedChildren(BSTNode<E> node) {
		if (numChildren(node) == 0) {
			return false;
		}
		else {
			if (node.left != null) {
				if (node.left.getColor() == 'R') {
					return true;
				}
			}
			if (node.right != null) {
				if (node.right.getColor() == 'R') {
					return true;
				}
			}
		}
		return false;
	}
	private void leftRotate (BSTNode<E> subTreeRoot) {
		BSTNode<E> pivot = subTreeRoot.right;
		pivot.parent = subTreeRoot.parent;
		if (subTreeRoot.parent != null) {
			if (isLeftChild(subTreeRoot)) {
				subTreeRoot.parent.left = pivot;
			}
			else if (isRightChild(subTreeRoot)) {
				subTreeRoot.parent.right = pivot;
			}
		}
		else {
			this.root = pivot;
		}
		subTreeRoot.parent = pivot;
		subTreeRoot.right = pivot.left;
		if (subTreeRoot.right != null) {
			subTreeRoot.right.parent = subTreeRoot;
		}
		pivot.left = subTreeRoot;
		
	} 
	private void rightRotate (BSTNode<E> subTreeRoot) {
		BSTNode<E> pivot = subTreeRoot.left;
		pivot.parent = subTreeRoot.parent;
		if (subTreeRoot.parent != null) {
			if (isLeftChild(subTreeRoot)) {
				subTreeRoot.parent.left = pivot;
			}
			else if (isRightChild(subTreeRoot)) {
				subTreeRoot.parent.right = pivot;
			}
		}
		else {
			this.root = pivot;
		}
		subTreeRoot.parent = pivot;
		subTreeRoot.left = pivot.right;
		if (subTreeRoot.left != null) {
			subTreeRoot.left.parent = subTreeRoot;
		}
		pivot.right = subTreeRoot;
	}
}
