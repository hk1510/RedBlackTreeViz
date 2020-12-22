package hw08;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Main extends Application {
	int treeType = 1;
	@Override
	public void start(Stage primaryStage) {
		RedBlackTree<Integer> intTree = new RedBlackTree<>();
		RedBlackTree<Character> charTree = new RedBlackTree<>();
		RedBlackTree<String> strTree = new RedBlackTree<>();
		
		BorderPane pane = new BorderPane();
		RBTView<Integer> intTreeView = new RBTView<>(intTree);
		RBTView<Character> charTreeView = new RBTView<>(charTree);
		RBTView<String> strTreeView = new RBTView<>(strTree);
		intTreeView.setPadding(new Insets(50));
		charTreeView.setPadding(new Insets(50));
		strTreeView.setPadding(new Insets(50));
		pane.setCenter(intTreeView);
		
		TextField tfKey = new TextField();
		tfKey.setPrefColumnCount(3);
		tfKey.setAlignment(Pos.BASELINE_RIGHT);
		Button btInsert = new Button("Insert");
		Button btDelete = new Button("Delete");
		RadioButton intb = new RadioButton("Integer Tree");
		RadioButton charb = new RadioButton("Character Tree");
		RadioButton strb = new RadioButton("String Tree");
		ToggleGroup treeTypes = new ToggleGroup();
		intb.setToggleGroup(treeTypes);
		charb.setToggleGroup(treeTypes);
		strb.setToggleGroup(treeTypes);
		treeTypes.selectToggle(intb);
		HBox hBox = new HBox(10);
		hBox.setPadding(new Insets(20));
		hBox.getChildren().addAll(new Label("Enter a key: "), tfKey, btInsert, btDelete, intb, strb, charb);
		hBox.setAlignment(Pos.CENTER);
		pane.setTop(hBox);
		
		
		intb.setOnAction(e ->  {
			pane.setCenter(intTreeView);
			treeType = 1;
		});
		
		charb.setOnAction(e -> {
			pane.setCenter(charTreeView);
			treeType = 2;
		});
		
		strb.setOnAction(e -> {
			pane.setCenter(strTreeView);
			treeType = 3;
		});
		
		btInsert.setOnAction(e -> {
			if (treeType == 1) {
				try {
					int key = Integer.parseInt(tfKey.getText());
					if (intTree.find(key)) {
						intTreeView.displayTree();
						intTreeView.setStatus(key + " is already in the tree");
					}
					else {
						intTree.insert(key);
						intTreeView.displayTree();
						intTreeView.setStatus(key + " is inserted in the tree");
					}
				}
				catch (Exception ex) {
					intTreeView.displayTree();
					intTreeView.setStatus(tfKey.getText() + " is invalid input for a tree of type Integer.");
					ex.printStackTrace();
				}
			}
			else if (treeType == 2) {
				try {
					if(tfKey.getText().length() > 1) {
						throw new IllegalArgumentException();
					}
					char key = tfKey.getText().charAt(0);
					if (charTree.find(key)) {
						charTreeView.displayTree();
						charTreeView.setStatus(key + " is already in the tree");
					}
					else {
						charTree.insert(key);
						charTreeView.displayTree();
						charTreeView.setStatus(key + " is inserted in the tree");
					}
				}
				catch (Exception ex) {
					charTreeView.displayTree();
					charTreeView.setStatus(tfKey.getText() + " is invalid input for a tree of type Character.");
					ex.printStackTrace();
				}
			}
			else if (treeType == 3) {
				try {
					String key = tfKey.getText();
					if (strTree.find(key)) {
						strTreeView.displayTree();
						strTreeView.setStatus(key + " is already in the tree");
					}
					else {
						strTree.insert(key);
						strTreeView.displayTree();
						strTreeView.setStatus(key + " is inserted in the tree");
					}
				}
				catch (Exception ex) {
					strTreeView.displayTree();
					strTreeView.setStatus(tfKey.getText() + " is invalid input for a tree of type String.");
					ex.printStackTrace();
				}
			}
			
		});
		
		btDelete.setOnAction(e -> {
			if (treeType == 1) {
				try {
					int key = Integer.parseInt(tfKey.getText());
					if (!intTree.find(key)) {
						intTreeView.displayTree();
						intTreeView.setStatus(key + " is not in the tree");
					}
					else {
						intTree.delete(key);
						intTreeView.displayTree();
						intTreeView.setStatus(key + " is deleted from the tree");
					}
				}
				catch (Exception ex) {
					intTreeView.setStatus(tfKey.getText() + "is invalid input for a tree of type Integer.");
					ex.printStackTrace();
				}
			}
			else if (treeType == 2) {
				try {
					if(tfKey.getText().length() > 1) {
						throw new IllegalArgumentException();
					}
					char key = tfKey.getText().charAt(0);
					if (!charTree.find(key)) {
						charTreeView.displayTree();
						charTreeView.setStatus(key + " is not in the tree");
					}
					else {
						charTree.delete(key);
						charTreeView.displayTree();
						charTreeView.setStatus(key + " is deleted from the tree");
					}
				}
				catch (Exception ex) {
					charTreeView.setStatus(tfKey.getText() + "is invalid input for a tree of type Character.");
					ex.printStackTrace();
				}
			}
			else if (treeType == 3) {
				try {
					String key = tfKey.getText();
					if (!strTree.find(key)) {
						strTreeView.displayTree();
						strTreeView.setStatus(key + " is not in the tree");
					}
					else {
						strTree.delete(key);
						strTreeView.displayTree();
						strTreeView.setStatus(key + " is deleted from the tree");
					}
				}
				catch (Exception ex) {
					strTreeView.setStatus(tfKey.getText() + "is invalid input for a tree of type String.");
					ex.printStackTrace();
				}
			}
		});
		
		Scene scene = new Scene(pane, 1280, 720);
		primaryStage.setTitle("RBT Animation");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
