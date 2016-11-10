package iikh;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author shivam207
 */
public class Recipe {
    
    private SimpleStringProperty name;
    private SimpleStringProperty ingredients;
    private SimpleStringProperty method;
    private SimpleIntegerProperty time;

    
    public static void addRecipe(){
    
        Stage window = new Stage();
        BorderPane layout = new BorderPane();
        HBox header = IIKH.setTitle("Add Recipe");
        layout.setTop(header);
        
        Label l1 = new Label("Name");
        Label l2 = new Label("Ingredients");
        Label l3 = new Label("Method of preparation");
        Label l4 = new Label("Time of preparation");
        
        TextField tf1 = new TextField();
        TextField tf2 = new TextField();
        TextField tf3 = new TextField();
        TextField tf4 = new TextField();
        
        HBox h1 = new HBox();
        HBox h2 = new HBox();
        HBox h3 = new HBox();
        HBox h4 = new HBox();
        h1.setSpacing(10);
        h2.setSpacing(10);
        h3.setSpacing(10);
        h4.setSpacing(10);
        h1.setPadding(new Insets(10));
        h2.setPadding(new Insets(10));
        h3.setPadding(new Insets(10));
        h4.setPadding(new Insets(10));
        h1.setAlignment(Pos.CENTER_LEFT);
        h2.setAlignment(Pos.CENTER_LEFT);
        h3.setAlignment(Pos.CENTER_LEFT);
        h4.setAlignment(Pos.CENTER_LEFT);
        
        h1.getChildren().addAll(l1, tf1);
        h2.getChildren().addAll(l2, tf2);
        h3.getChildren().addAll(l3, tf3);
        h4.getChildren().addAll(l4, tf4);
        
        Button okay = new Button("Okay");
        Button cancel = new Button("Cancel");
        okay.setOnAction(e -> {
            String nR = tf1.getText();
            String nI = tf2.getText();
            String nM = tf3.getText();
            int nT = Integer.parseInt(tf4.getText());
            String recipeData = String.join(":", nR, nI, nM, tf4.getText());
            IIKH.writeToFile(recipeData, "recipes.txt");
        });
        cancel.setOnAction(e -> window.close());
        
        HBox options = new HBox();
        options.setAlignment(Pos.CENTER_RIGHT);
        options.getChildren().addAll(okay, cancel);
        
        VBox root = new VBox();
        root.getChildren().addAll(h1, h2, h3, h4, options);
        layout.setCenter(root);
        
        Scene addrecipe = new Scene(layout, 400, 250);
        window.setTitle("Add Recipe");
        window.setScene(addrecipe);
        window.show();
        
    }
    
    public static void editRecipe(){
    
        browseRecipeDB(true);
    }

    public static void browseRecipeDB(boolean isEditable){
        ObservableList<Recipe> data = FXCollections.observableArrayList();
        
        try{
            FileReader fr = new FileReader("recipes.txt");
            BufferedReader bufr = new BufferedReader(fr); 
            String line = bufr.readLine(); 
            while(line != null){
                String[] items = line.split(":");
                Recipe newRecipe = new Recipe(items[0], items[1], items[2], Integer.parseInt(items[3]));
                data.add(newRecipe);
                line = bufr.readLine(); 
            }
            bufr.close(); 
        }
        catch(IOException e){
            System.out.println("Error while reading file.");
        }
        
        Stage window = new Stage();
        window.setTitle("IIKH");
        window.setWidth(500);
        window.setHeight(400);
        
        TableView table = new TableView();
        table.setEditable(isEditable);
        
        TableColumn<Recipe, String> recipeNameCol = new TableColumn<>("Name");
        TableColumn ingredientsCol = new TableColumn("Ingredients");
        TableColumn methodCol = new TableColumn("Method");
        TableColumn timeCol = new TableColumn("Time");
        
        recipeNameCol.setMinWidth(100);
        ingredientsCol.setMinWidth(150);
        methodCol.setMinWidth(150);
        timeCol.setMinWidth(50);
        
        recipeNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ingredientsCol.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
        methodCol.setCellValueFactory(new PropertyValueFactory<>("method"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        recipeNameCol.setOnEditCommit((CellEditEvent<Recipe, String> t) -> {
                ((Recipe) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setName(t.getNewValue());

        });

        table.setItems(data);
        table.getColumns().addAll(recipeNameCol, ingredientsCol, methodCol, timeCol);
 
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table);
 
        Scene browse = new Scene(new Group());
        ((Group) browse.getRoot()).getChildren().addAll(vbox);
     
        window.setScene(browse);
        window.show();
    }

    private Recipe(String nR, String nI, String nM, int nT) {
        this.name = new SimpleStringProperty(nR);
        this.ingredients = new SimpleStringProperty(nI);
        this.method = new SimpleStringProperty(nM);
        this.time = new SimpleIntegerProperty(nT);
    }

    public String getName() {
        return name.get();
    }
    public void setName(String rName) {
        name.set(rName);
    }
        
    public String getIngredients() {
        return ingredients.get();
    }
    public void setIngredients(String iName) {
        ingredients.set(iName);
    }
    
    public String getMethod() {
        return method.get();
    }
    public void setEmail(String mName) {
        method.set(mName);
    }
    
    public int getTime() {
        return time.get();
    }
    public void setTime(int tName) {
        time.set(tName);
    }

}