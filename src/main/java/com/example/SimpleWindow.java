package com.example;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.control.Alert.AlertType.WARNING;

public class SimpleWindow extends Application {

    private Matrix MainMatrix;
    private Matrix SecondaryMatrix;

    @Override
    public void start(Stage primaryStage) {
        // Create the menu bar and its items
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem generateMenuItem = new MenuItem("Generate Matrix");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem loadMenuItem = new MenuItem("Load");
        MenuItem aboutMenuItem = new MenuItem("About");

        fileMenu.getItems().addAll(generateMenuItem, saveMenuItem, loadMenuItem, aboutMenuItem);
        menuBar.getMenus().addAll(fileMenu);

        // Create the status bar
        Label statusBar = new Label("Status: Ready");

        // Create the text areas
        TextArea leftTextArea = new TextArea();
        leftTextArea.setEditable(false);
        TextArea rightTextArea = new TextArea();
        rightTextArea.setEditable(false);

        // Create the buttons
        Button button1 = new Button("MULTIPLY");
        button1.setMinSize(100,25);
        Button button2 = new Button("POWER");
        button2.setMinSize(100,25);
        Button button3 = new Button("DISTANCE");
        button3.setMinSize(100,25);
        Button button4 = new Button("ECCENTRICITY");
        button4.setMinSize(100,25);
        Button button5 = new Button("RADIUS");
        button5.setMinSize(100,25);
        Button button6 = new Button("DIAMETER");
        button6.setMinSize(100,25);
        Button button7 = new Button("CENTER");
        button7.setMinSize(100,25);
        Button buttonComponents = new Button("COMPONENTS");
        buttonComponents.setMinSize(100,25);
        Button buttonArticulations = new Button("ARTICULATION");
        buttonArticulations.setMinSize(100,25);
        Button buttonSWAP = new Button("SWAP MATRIX");
        buttonSWAP.setMinSize(100,25);
        Button buttonBridge = new Button("BRIGDES");
        buttonBridge.setMinSize(100,25);

        // Arrange the buttons in a vertical box
        VBox buttonBox = new VBox(10, button1, button2, button3, button4, button5,button6,button7,buttonComponents,buttonArticulations,buttonBridge,buttonSWAP);
        buttonBox.setMinWidth(100);
        buttonBox.setAlignment(Pos.BASELINE_CENTER);

        // Create the center layout with the text areas and button box
        HBox centerLayout = new HBox(10, leftTextArea, buttonBox, rightTextArea);
        HBox.setHgrow(leftTextArea, Priority.ALWAYS);
        HBox.setHgrow(rightTextArea, Priority.ALWAYS);
        HBox.setHgrow(buttonBox, Priority.ALWAYS);

        //ADD EH, yeah yeah Spaghetti I know
        //Personally I hate this, but at the same time I just hate GUI design, so ya know

        //Menus
        generateMenuItem.addEventHandler(ActionEvent.ANY, event -> {
            MainMatrix = new Matrix(30,30,true);
            leftTextArea.setText(MainMatrix.toString());
        });
        saveMenuItem.addEventHandler(ActionEvent.ANY, event -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialFileName(MainMatrix.getName()+".csv");
                File file = fileChooser.showSaveDialog(primaryStage);
                MainMatrix.saveCsv(file.getAbsolutePath());
            } catch (Exception e) {
                new Alert(WARNING, "File not Valid").show();
            }
        });
        loadMenuItem.addEventHandler(ActionEvent.ANY, event -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(primaryStage);
            try {
                MainMatrix = Matrix.loadCsv(file.getAbsolutePath());
                leftTextArea.setText(MainMatrix.toString());
            } catch (Exception e) {
                new Alert(WARNING, "File not Valid").show();
            }
        });
        aboutMenuItem.addEventHandler(ActionEvent.ANY, actionEvent -> {
            new Alert(INFORMATION, "This Tool was created as a learning Project by Ptak Simon in the HTBLM Spengergasse").show();
        });

        //Buttons
        button1.addEventHandler(ActionEvent.ANY,event -> {
            File file = new FileChooser().showOpenDialog(primaryStage);
            try {
                SecondaryMatrix = Matrix.multiply(MainMatrix, Matrix.loadCsv(file.getAbsolutePath()));
                rightTextArea.setText(SecondaryMatrix.toString());
            } catch (Exception e) {
                new Alert(WARNING, "File not Valid").show();
            }
        });
        button2.addEventHandler(ActionEvent.ANY,actionEvent -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Input Dialog");
            dialog.setHeaderText("Please enter the Power you want to achieve:");
            dialog.setContentText("Power");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(choice -> {
                try {
                    SecondaryMatrix = MainMatrix.power(Integer.parseInt(choice));
                    rightTextArea.setText(SecondaryMatrix.toString());
                } catch (Exception e) {
                    new Alert(WARNING, "Power not Valid").show();
                }
            });

        });
        button3.addEventHandler(ActionEvent.ANY,actionEvent -> {
            try {
                SecondaryMatrix = MainMatrix.distance();
                rightTextArea.setText(SecondaryMatrix.toString());
            } catch (Exception e) {
                new Alert(WARNING, "Error").show();
            }
        });
        button4.addEventHandler(ActionEvent.ANY,actionEvent -> {
            try {
                SecondaryMatrix = MainMatrix.eccentricity();
                rightTextArea.setText(SecondaryMatrix.toString());
            } catch (Exception e) {
                new Alert(WARNING, "Error").show();
            }
        });
        button5.addEventHandler(ActionEvent.ANY,actionEvent -> {
            try {
                SecondaryMatrix = MainMatrix.radius();
                rightTextArea.setText(SecondaryMatrix.toString());
            } catch (Exception e) {
                new Alert(WARNING, "Error").show();
            }
        });
        button6.addEventHandler(ActionEvent.ANY,actionEvent -> {
            try {
                SecondaryMatrix = MainMatrix.diameter();
                rightTextArea.setText(SecondaryMatrix.toString());
            } catch (Exception e) {
                new Alert(WARNING, "Error").show();
            }
        });
        button7.addEventHandler(ActionEvent.ANY,actionEvent -> {
            try {
                SecondaryMatrix = MainMatrix.center();
                rightTextArea.setText(SecondaryMatrix.toString());
            } catch (Exception e) {
                new Alert(WARNING, "Error").show();
            }
        });
        buttonComponents.addEventHandler(ActionEvent.ANY,actionEvent -> {
            try {
                SecondaryMatrix = MainMatrix.components();
                rightTextArea.setText(SecondaryMatrix.toString());
            } catch (Exception e) {
                new Alert(WARNING, "Error").show();
            }
        });
        buttonArticulations.addEventHandler(ActionEvent.ANY,actionEvent -> {
            try {
                SecondaryMatrix = MainMatrix.articulationPoints();
                rightTextArea.setText(SecondaryMatrix.toString());
            } catch (Exception e) {
                new Alert(WARNING, "Error").show();
            }
        });

        buttonBridge.addEventHandler(ActionEvent.ANY,actionEvent -> {
            try {
                SecondaryMatrix = MainMatrix.bridges();
                rightTextArea.setText(SecondaryMatrix.toString());
            } catch (Exception e) {
                new Alert(WARNING, "Error" + e.getMessage()).show();
            }
        });

        buttonSWAP.addEventHandler(ActionEvent.ANY,actionEvent -> {
            try {
                Matrix tempMatrix = MainMatrix;
                MainMatrix = SecondaryMatrix;
                SecondaryMatrix = tempMatrix;
                leftTextArea.setText(MainMatrix.toString());
                rightTextArea.setText(SecondaryMatrix.toString());
            } catch (Exception e) {
                new Alert(WARNING, "Error").show();
            }
        });

        // Create the root layout
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(centerLayout);
        root.setBottom(statusBar);
        BorderPane.setMargin(centerLayout, new Insets(10));

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Simple Window");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
