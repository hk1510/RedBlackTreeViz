package hw08;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<E extends Comparable<E>> {
	
	protected BSTNode<E> root;
	
	public BinarySearchTree() {}
	public BinarySearchTree(E ... values) {
		for (E value : values) {
			this.insert(value);
		}
	}
	
	public void insert(E value) {
		if (value == null) {
			throw new NullPointerException();
		}
		BSTNode<E> child = new BSTNode<E>(value);
		if (this.root == null) {
			this.root = child;
		}
		else {
			try {
				BSTNode<E> parent = insertionPoint(value);
				if (value.compareTo(parent.getData()) < 0) {
					parent.left = child;
					child.parent = parent;
				}
				else if (value.compareTo(parent.getData()) > 0) {
					parent.right = child;
					child.parent = parent;
				}
			}
			catch (DuplicateItemException e){
				throw e;
			}
		}
	}
	protected BSTNode<E> insertionPoint(E value) {
		BSTNode<E> current = this.root;
		BSTNode<E> parent = null;
		
		while (current.getData() != null) {
			if (value.compareTo(current.getData()) == 0) {
				throw new DuplicateItemException("Item already exists in tree!");
			}
			else if (value.compareTo(current.getData()) < 0) {
				parent = current;
				current = current.left;
			}
			else if (value.compareTo(current.getData()) > 0) {
				parent = current;
				current = current.right;
			}
			if(current == null) {
				break;
			}
		}
		
		return parent;
	}
	
	public void delete(E value) {
		delete(nodeToDelete(value));
	}
	private void delete(BSTNode<E> node) {
		if (numChildren(node) == 0) {
			if (node == this.root) {
				this.root = null;
			}
			else {
				if (isLeftChild(node)) {
					node.parent.left = null;
				}
				else if (isRightChild(node)) {
					node.parent.right = null;
				}
			}
		}
		else if (numChildren(node) == 1) {
			BSTNode<E> child = null;
			if (node.right != null) {
				child = node.right;
			}
			if (node.left != null) {
				child = node.left;
			}
			if (node == this.root) {
				this.root = child;
				child.parent = null;
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
			}
		}
		else if (numChildren(node) == 2) {
			BSTNode<E> max = maxLeftSubtree(node);
			node.setData(max.getData());
			delete(max);
		}
	}
	protected BSTNode<E> nodeToDelete(E value) {
		BSTNode<E> current = root;
		while (current != null) {
			if (value.compareTo(current.getData()) == 0) {
				return current;
			}
			else if (value.compareTo(current.getData()) < 0) {
				current = current.left;
			}
			else if (value.compareTo(current.getData()) > 0) {
				current = current.right;
			}
		}
		return null;
	}
	protected int numChildren(BSTNode<E> node) {
		int count = 0;
		if (node.left != null) {
			if (node.left.getData() != null) {
				count++;
			}
		}
		if (node.right != null) {
			if (node.right.getData() != null) {
				count++;
			}
		}
		return count;
	}
	protected BSTNode<E> maxLeftSubtree(BSTNode<E> node) {
		BSTNode<E> current = node.left;
		BSTNode<E> max = null;
		while(current.getData() != null) {
			max = current;
			current = current.right;
		}
		return max;
	}
	
	public boolean find(E value) {
		BSTNode<E> current = this.root;
		if (this.root == null) {
			return false;
		}
		while (current.getData() != null) {
			if (value.compareTo(current.getData()) == 0) {
				return true;
			}
			else if (value.compareTo(current.getData()) < 0) {
				current = current.left;
			}
			else if (value.compareTo(current.getData()) > 0) {
				current = current.right;
			}
			if (current == null) {
				break;
			}
		}
		return false;
	}
	
	public boolean isEmpty() {
		if (root == null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public ArrayList<E> preorder() {
		ArrayList<E> preorderList = new ArrayList<>();
		preorder(this.root, preorderList);
		return preorderList;
		
	}
	private void preorder(BSTNode<E> node, ArrayList<E> list) {
		if (node == null) {
			return;
		}
		list.add(node.getData());
		preorder(node.left, list);
		preorder(node.right, list);
	}
	
	public ArrayList<E> inorder() {
		ArrayList<E> inorderList = new ArrayList<>();
		inorder(this.root, inorderList);
		return inorderList;
		
	}
	private void inorder(BSTNode<E> node, ArrayList<E> list) {
		if (node == null) {
			return;
		}
		inorder(node.left, list);
		list.add(node.getData());
		inorder(node.right, list);
	}
	
	public ArrayList<E> postorder() {
		ArrayList<E> postorderList = new ArrayList<>();
		postorder(this.root, postorderList);
		return postorderList;
		
	}
	private void postorder(BSTNode<E> node, ArrayList<E> list) {
		if (node == null) {
			return;
		}
		postorder(node.left, list);
		postorder(node.right, list);
		list.add(node.getData());
	}
	
	public ArrayList<E> breadthfirst() {
		ArrayList<E> breadthfirstList = new ArrayList<>();
		breadthfirst(this.root, breadthfirstList);
		return breadthfirstList;
	}
	private void breadthfirst(BSTNode<E> root, ArrayList<E> list) {
		if (root == null) {
			return;
		}
		Queue<BSTNode<E>> queue = new LinkedList<BSTNode<E>>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			BSTNode<E> node = queue.poll();
			list.add(node.getData());
			if (node.left != null) {
				queue.offer(node.left);
			}
			if (node.right != null) {
				queue.offer(node.right);
			}
		}
	}
	
	protected boolean isLeftChild(BSTNode<E> node) {
		if (node.parent.left != null) {
			return node.parent.left.getData() == node.getData();
		}
		return false;
	}
	protected boolean isRightChild(BSTNode<E> node) {
		if (node.parent.right != null) {
			return node.parent.right.getData() == node.getData();
		}
		return false;
	}
	protected boolean isLeaf(BSTNode<E> node) {
		return numChildren(node) == 0;
	}
	protected BSTNode<E> sibling(BSTNode<E> node) {
		if (node.parent == null) {
			return null;
		}
		if (isLeftChild(node)) {
			return node.parent.right;
		}
		else {
			return node.parent.left;
		}
	}
	protected BSTNode<E> uncle(BSTNode<E> node) {
		if (node.parent == null) {
			return null;
		}
		return this.sibling(node.parent);
	}
	protected BSTNode<E> grandparent(BSTNode<E> node) {
		if (node.parent == null) {
			return null;
		}
		return node.parent.parent;
	}
	
	@Override
	public String toString() {
		
		if (this.root == null) {
			return "Tree is empty.";
		}
		
		String tree = "";
		
	    if (this.root.right != null) {
	        tree += this.stringTree(this.root.right, true, "");
	    }

	    tree += stringNodeValue(this.root);

	    if (this.root.left != null) {
	        tree += this.stringTree(this.root.left, false, "");
	    }
	    
	    return tree;
	}

	private String stringTree(BSTNode<E> node, boolean isRight, String indent) {
	    
		String tree = "";
		
		if (node.right != null) {
	        tree += stringTree(node.right, true, indent + (isRight ? "        " : " |      "));
	    }

	    tree += indent;

	    if (isRight) {
	        tree += " /";
	    }
	    else {
	        tree += " \\";
	    }
	    tree += "----- ";
	    tree += stringNodeValue(node);
	    if (node.left != null) {
	        tree += stringTree(node.left, false, indent + (isRight ? " |      " : "        "));
	    }
	    return tree;
	}

	private String stringNodeValue(BSTNode<E> node) {
	    String nodeVal = "";
		if (node == null) {
	        nodeVal += "<null>";
	    }
	    else {
	        nodeVal += node.getData()  + ((node.getColor() == 'N') ? "" : " (" + node.getColor() + ")");
	    }
	    nodeVal += "\n";
	    return nodeVal;
	}
	
	public void printTree() {
		System.out.println(this);
	}
}
