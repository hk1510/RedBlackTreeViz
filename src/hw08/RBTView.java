package hw08;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class RBTView <E extends Comparable<E>> extends Pane{
	private RedBlackTree<E> tree = new RedBlackTree<>();
	private double radius = 30;
	private double vGap = 60;
	
	RBTView (RedBlackTree<E> tree) {
		this.tree = tree;
		setStatus("Tree is empty");
	}
	
	public void setStatus(String msg) {
		getChildren().add(new Text(20, 20, msg));
	}
	
	public void displayTree() {
		this.getChildren().clear();
		if (tree.root != null) {
			displayTree(tree.root, (getWidth() / 2) , vGap, (getWidth() / 4) * 0.6);
		}
	}
	
	private void displayTree(BSTNode<E> root, double x, double y , double hGap) {
		if (root.left != null) {
			getChildren().add(new Line(x - hGap, y + vGap, x, y));
			displayTree(root.left, x - hGap, y + vGap, hGap / 2);
		}
		if (root.right != null) {
			getChildren().add(new Line(x + hGap, y + vGap, x, y));
			displayTree(root.right, x + hGap, y + vGap, hGap / 2);
		}
		Shape node;
		if (root == tree.NIL) {
			node = new Rectangle(radius*2, radius);
		}
		else {
			node = new Circle (radius);
		}
		
		if (root.getColor() == 'R') {
			node.setFill(Color.RED);
		}
		else if (root.getColor() == 'B') {
			node.setFill(Color.BLACK);
		}
		else if (root.getColor() == 'N') {
			node.setFill(Color.WHITE);
		}
		node.setStroke(Color.BLACK);
		node.setStrokeWidth(2);
		Text text;
		if (root == tree.NIL) {
			text = new Text("NIL");
		}
		else {
			text = new Text(root.getData() + "");
		}
		text.setBoundsType(TextBoundsType.VISUAL);
		text.setStroke(Color.WHITE);
		StackPane stack = new StackPane();
		stack.setLayoutX(x - radius);
		if (root == tree.NIL) {
			stack.setLayoutY(y);
		}
		else {
			stack.setLayoutY(y - radius);
		}
		stack.getChildren().addAll(node, text);
		getChildren().addAll(stack);
	}
}
