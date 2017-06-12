/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evgeniymh.simplehopfieldnetwork;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author evgeniy
 */
public class Main extends Application {


    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/main.fxml"));
        AnchorPane rootOverview = (AnchorPane) loader.load();
        
        MainController mc = loader.getController();
        mc.initMatrix();

        Scene scene = new Scene(rootOverview,450,450);
        stage.setTitle("Simple Hopfield Network");
        stage.setScene(scene);
        stage.show();
        
        mc.Draw();
    }

}
