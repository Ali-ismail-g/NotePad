package notepad;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
public class NotePad extends Application {

    MenuBar bar;
    Menu file, edit, help;
    MenuItem fileItem1, fileItem2, fileItem3, fileItem4, editItem1, editItem2, editItem3, editItem4, editItem5, editItem6, helpItem;
    TextArea txtArea;
    FileChooser fileChooser;
    FileChooser.ExtensionFilter extFilter, extFilter2;
    File fileChoosen, fileSaved,fileSaved2,fileSaved3;
    FileInputStream currentFile;
    FileWriter fileWrite,fileWrite2,fileWrite3;
    byte[] b;
    String fileName;
    Alert alert,alert2,alert3;
    SeparatorMenuItem separator, separator2, separator3;
    public static void main(String[] args) {
        Application.launch(args);
    }
    @Override
    public void init() {
        bar = new MenuBar();
        separator = new SeparatorMenuItem();
        separator2 = new SeparatorMenuItem();
        separator3 = new SeparatorMenuItem();
        file = new Menu("File");  //creating submenu inside a menu
        fileItem1 = new MenuItem("New");
        fileItem1.setAccelerator(KeyCombination.keyCombination("Ctrl + e"));
        fileItem2 = new MenuItem("Open");
        fileItem2.setAccelerator(KeyCombination.keyCombination("Ctrl + f"));
        fileItem3 = new MenuItem("Save");
        fileItem3.setAccelerator(KeyCombination.keyCombination("Ctrl + b"));
        fileItem4 = new MenuItem("Exit");
        fileItem4.setAccelerator(KeyCombination.keyCombination("Ctrl + x"));
        file.getItems().add(fileItem1);
        file.getItems().add(fileItem2);
        file.getItems().add(fileItem3);
        file.getItems().add(separator);
        file.getItems().add(fileItem4);

        edit = new Menu("Edit");
        editItem1 = new MenuItem("Undo");
        editItem1.setAccelerator(KeyCombination.keyCombination("Ctrl + u"));
        editItem2 = new MenuItem("Cut");
        editItem2.setAccelerator(KeyCombination.keyCombination("Ctrl + z"));
        editItem3 = new MenuItem("Copy");
        editItem3.setAccelerator(KeyCombination.keyCombination("Ctrl + a"));
        editItem4 = new MenuItem("Paste");
        editItem4.setAccelerator(KeyCombination.keyCombination("Ctrl + s"));
        editItem5 = new MenuItem("Delete");
        editItem5.setAccelerator(KeyCombination.keyCombination("Ctrl + g"));
        editItem6 = new MenuItem("Select All");
        editItem6.setAccelerator(KeyCombination.keyCombination("Ctrl + q"));
        edit.getItems().add(editItem1);
        edit.getItems().add(separator2);
        edit.getItems().add(editItem2);
        edit.getItems().add(editItem3);
        edit.getItems().add(editItem4);
        edit.getItems().add(editItem5);
        edit.getItems().add(separator3);
        edit.getItems().add(editItem6);

        help = new Menu("Help");
        helpItem = new MenuItem("About NotePad");
        helpItem.setAccelerator(KeyCombination.keyCombination("Ctrl + h"));
        help.getItems().addAll(helpItem);
        txtArea = new TextArea();
        fileChooser = new FileChooser();
    }
    @Override
    public void start(Stage primaryStage) throws Exception { //new button
        fileItem1.setOnAction(new EventHandler<ActionEvent>() {
            //check if user want to save or not
            @Override
            public void handle(ActionEvent event) {
                if(txtArea.getLength()!= 0){
                alert2 = new Alert(AlertType.INFORMATION);
                alert2.setTitle("Warning");
                alert2.setHeaderText(null);
                alert2.setContentText("Do you want to save this text?");
                alert2.getButtonTypes().setAll(ButtonType.YES,ButtonType.NO);
                Optional<ButtonType>result = alert2.showAndWait();
                if(result.get() == ButtonType.YES){
                    try {
                    //set save file
                    fileSaved2 = fileChooser.showSaveDialog(primaryStage);
                    fileWrite2 = new FileWriter(fileSaved2);
                    fileWrite2.write(txtArea.getText());
                    fileWrite2.close();
                } catch (IOException ex) {
                    Logger.getLogger(NotePad.class.getName()).log(Level.SEVERE, null, ex);
                }
                }else{
                    txtArea.clear();
                }
                }else{
                    txtArea.clear();
                }
            }
        });
        fileItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //set extension filter
                extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                try {
                    //set open dialogue
                    currentFile = new FileInputStream(fileChooser.showOpenDialog(primaryStage));
                    int size = currentFile.available();
                    b = new byte[size];
                    currentFile.read(b);
                    txtArea.setText(new String(b));
                    currentFile.close();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(NotePad.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(NotePad.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        fileItem3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    //set save file
                    fileSaved = fileChooser.showSaveDialog(primaryStage);
                    fileName = fileSaved.getName();
                    fileWrite = new FileWriter(fileSaved);
                    fileWrite.write(txtArea.getText());
                    fileWrite.close();
                } catch (IOException ex) {
                    Logger.getLogger(NotePad.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        fileItem4.setOnAction(new EventHandler<ActionEvent>() { //Exit button
            @Override
          //ask user to save file or ignor if there any change
            public void handle(ActionEvent event) {
                if(txtArea.getLength()!= 0){
                alert3 = new Alert(AlertType.INFORMATION);
                alert3.setTitle("Warning");
                alert3.setHeaderText(null);
                alert3.setContentText("Do you want to save this text?");
                alert3.getButtonTypes().setAll(ButtonType.YES,ButtonType.NO);
                Optional<ButtonType>result = alert3.showAndWait();
                if(result.get() == ButtonType.YES){
                    try {
                    //set save file
                    fileSaved3 = fileChooser.showSaveDialog(primaryStage);
                    fileWrite3 = new FileWriter(fileSaved3);
                    fileWrite3.write(txtArea.getText());
                    fileWrite3.close();
                } catch (IOException ex) {
                    Logger.getLogger(NotePad.class.getName()).log(Level.SEVERE, null, ex);
                }
                }else{
                    primaryStage.close();
                }
                }else{
                    primaryStage.close();
                }
            }
        });
        editItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txtArea.undo();
            }
        });
        editItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txtArea.cut();
            }
        });
        editItem3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txtArea.copy();
            }
        });
        editItem4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txtArea.paste();
            }
        });
        editItem5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txtArea.deleteText(txtArea.getSelection());
            }
        });
        editItem6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txtArea.selectAll();
            }
        });
        helpItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("About");
                alert.setHeaderText("Info about the creator");
                alert.setContentText("Ali Ismail is the creator of the NotePad on 30Dec 2019");
                alert.showAndWait();
            }
        });
        bar.getMenus().addAll(file, edit, help);
        BorderPane pane = new BorderPane();
        pane.setTop(bar);
        pane.setCenter(txtArea);
        Scene scene = new Scene(pane, 300, 400);
        primaryStage.setTitle("FirstNotePad");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

