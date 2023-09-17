package com.example.leoringassignment2;

import com.example.leoringassignment2.controller.StudentDataController;
import com.example.leoringassignment2.view.StudentView;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

import static com.example.leoringassignment2.view.StudentView.createPopUpContent;

/**
 * Class to run the application
 * @author Leo Ring
 */
public class StudentApplication extends javafx.application.Application {
    /**
     * @param stage part of the class it extends
     * @throws NullPointerException if stylesheet can't be found
     */
    @Override
    public void start(Stage stage) throws NullPointerException {
        StudentView studentView = new StudentView();
        // get gridPane from View class and add to scene
        BorderPane root = studentView.createRoot();
        Scene scene = new Scene(root, 650, 500);
        // get styling for scene from stylesheet and set title
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("assignment1Stylesheet.css")).toExternalForm());
        stage.setTitle("MTU Student Record System");
        // attach scene to stage
        stage.setScene(scene);
        // run onQuit method when the stage is closed
        stage.setOnHidden(e -> StudentDataController.getInstance().closeConnection());
        // show stage
        stage.show();
    }

    /**
     * method to create a popup
     * @param title title of pop up
     * @param message text in pop up
     * @param decision boolean whether to ask the user to decide or not
     * @return boolean whether user chose yes or not
     */
    public static Boolean createPopUp(String title, String message, Boolean decision){
        PopUpStage popUp = new PopUpStage();
        popUp.setTitle(title);
        popUp.initModality(Modality.APPLICATION_MODAL);
        VBox popUpContent = createPopUpContent(message, decision, popUp);
        Scene popUpScene = new Scene(popUpContent, 550, 100);
        popUpScene.getStylesheets().add(popUp.getStyleSheet());
        popUp.setScene(popUpScene);
        return popUp.showPopUp();
    }
    // launch app
    public static void main(String[] args) {
        launch();
    }
}