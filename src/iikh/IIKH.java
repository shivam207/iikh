
package iikh;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author shivam207
 */
public class IIKH extends Application {
    
        
    static void writeToFile(String data, String filename){
        
        try { 
            File targetFile = new File(filename);
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }

            FileWriter fw = new FileWriter(targetFile.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.newLine();
            bw.close();
            System.out.println("File Writing :" + filename + " is done.");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public static HBox setTitle(String message){
        Text title = new Text(message);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.getChildren().add(title);
        return header;
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        Stage window = primaryStage;

        BorderPane layout = new BorderPane();
        
        
        HBox title = setTitle("Intelligent Interactive Kitchen Helper");
        layout.setTop(title);
       
        VBox verticalBox = new VBox();
        verticalBox.setPadding(new Insets(10));
        verticalBox.setSpacing(8);

        
        Label options[] = new Label[] {
        new Label("1. Add Recipe."),
        new Label("2. Browse Recipes Database."),
        new Label("3. Add meal plan."),
        new Label("4. Browse meal plan.")};

        for (int i=0; i<4; i++) {
            verticalBox.setMargin(options[i], new Insets(0, 0, 0, 10));
            verticalBox.getChildren().add(options[i]);
        }

        TextField input = new TextField();
        Label inputLabel = new Label("Enter your choice");
        
        HBox inputField = new HBox();
        inputField.setSpacing(10);
        inputField.setAlignment(Pos.CENTER);
        inputField.getChildren().addAll(inputLabel, input);
        
        verticalBox.getChildren().add(inputField);
        
        Button button1 = new Button();
        Button button2 = new Button();
        
        button1.setText("Okay");
        button2.setText("Quit");
        button1.setOnAction(e -> {
            int choice = isChoiceValid(input.getText());
            selectChoice(choice);
            
        });
        
        button2.setOnAction(e -> window.close());
        
        HBox confirmation = new HBox();
        confirmation.setAlignment(Pos.CENTER_RIGHT);
        confirmation.getChildren().addAll(button1, button2);
        
        verticalBox.getChildren().add(confirmation);
        layout.setCenter(verticalBox);
        Scene scene = new Scene(layout, 500, 250);
        
        window.setTitle("IIKH");
        window.setScene(scene);
        window.show();
    }

    public int isChoiceValid(String input){
        int choice; 
        try{
            choice = Integer.parseInt(input);
            if((choice > 0) && (choice <= 5)) 
                return choice;
            else
                return 0;
        }
        catch(NumberFormatException e){
            return 0;
    }
}
    
    public void selectChoice(int choice){
        switch(choice){
            
            case 1:
                Recipe.addRecipe();
                break;
                
            case 2:
                Recipe.browseRecipeDB(false);
                break;
                
            case 3:
                Meal.preparePlan();
                break;
                
            case 4:
                Meal.editPlan();
                break;
                
            default:
                System.out.println("No Choice");
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
