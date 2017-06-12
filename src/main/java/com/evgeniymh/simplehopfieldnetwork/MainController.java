/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evgeniymh.simplehopfieldnetwork;

import com.evgeniymh.simplehopfieldnetwork.neural.HopfieldNetwork;
import com.evgeniymh.simplehopfieldnetwork.neural.NeuralNetworkError;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author evgeniy
 */
public class MainController {
    
    private int matrM;
    private int matrN;
    private Rectangle[][] drawMatrix;
    private boolean[][] boolMatrix;
    private HopfieldNetwork mHopfieldNetwork;
    private Group drawContainer = new Group();

    @FXML
    AnchorPane DrawSpace;
    @FXML
    Button clearButton;
    @FXML
    Button trainButton;
    @FXML
    Button goButton;
    @FXML
    Spinner<Integer> DrawSpaceMSpinner;
    @FXML
    Spinner<Integer> DrawSpaceNSpinner;
    
    public void initMatrix(){
        this.matrM = DrawSpaceMSpinner.getValue();
        this.matrN = DrawSpaceNSpinner.getValue();

        drawMatrix = new Rectangle[matrM][matrN];
        boolMatrix = new boolean[matrM][matrN];
        mHopfieldNetwork = new HopfieldNetwork(matrN * matrM);
    }

    @FXML
    public void initialize() {  
        
        DrawSpace.getChildren().add(drawContainer);
        
        SpinnerValueFactory<Integer> valueFactoryM=new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 15, 6);
        SpinnerValueFactory<Integer> valueFactoryN=new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 15, 6);
        DrawSpaceMSpinner.setValueFactory(valueFactoryM);
        DrawSpaceNSpinner.setValueFactory( valueFactoryN);
        
        ChangeListener<Integer> spinnerListener=(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {            
            drawContainer.getChildren().clear();
            initMatrix();
            Draw();
        };
        DrawSpaceMSpinner.valueProperty().addListener(spinnerListener);
        DrawSpaceNSpinner.valueProperty().addListener(spinnerListener);
        
        trainButton.setOnAction((event) -> {
            try {
                mHopfieldNetwork.train(getPattern());
            } catch (NeuralNetworkError ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        goButton.setOnAction((event) -> {
            boolean[] outputPattern = mHopfieldNetwork.present(getPattern());
            showPattern(outputPattern);
        });
        
        clearButton.setOnAction((event) -> {
            for (int i = 0; i < matrM; i++) {
                for (int j = 0; j < matrN; j++) {
                    drawMatrix[i][j].setFill(Color.LIGHTSTEELBLUE);
                    boolMatrix[i][j] = false;
                    mHopfieldNetwork.getWeightMatrix().set(0.0);
                }
            }
        });
    }    

    public void Draw() {     
        drawMatrix(drawContainer, matrM, matrN);

    }
    
    private void showPattern(boolean[] pattern){
        for (int i = 0, ind = 0; i < matrM; i++) {
            for (int j = 0; j < matrN; j++) {
                boolMatrix[i][j]=pattern[ind];
                drawMatrix[i][j].setFill(pattern[ind]? Color.BLACK : Color.LIGHTSTEELBLUE);
                
                ind++;
            }
        }
    }
    
    private boolean[] getPattern() {
        boolean[] pattern = new boolean[matrM * matrN];
        for (int i = 0, ind = 0; i < matrM; i++) {
            for (int j = 0; j < matrN; j++) {
                pattern[ind++] = boolMatrix[i][j];
            }
        }
        return pattern;
    }

    private void drawMatrix(Group gr, int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                drawMatrix[i][j] = new Rectangle(40, 40);
                drawMatrix[i][j].setArcHeight(20);
                drawMatrix[i][j].setArcWidth(20);
                drawMatrix[i][j].setX(i * 50);
                drawMatrix[i][j].setY(j * 50);
                drawMatrix[i][j].setFill(Color.LIGHTSTEELBLUE);
                boolMatrix[i][j] = false;

                final int fi = i;
                final int fj = j;
                drawMatrix[i][j].setOnMouseClicked((MouseEvent event) -> {
                    Paint p = drawMatrix[fi][fj].getFill();
                    if (p.toString().equals(Color.LIGHTSTEELBLUE.toString())) {
                        drawMatrix[fi][fj].setFill(Color.BLACK);
                        boolMatrix[fi][fj] = true;
                    } else {
                        drawMatrix[fi][fj].setFill(Color.LIGHTSTEELBLUE);
                        boolMatrix[fi][fj] = false;
                    }
                });                
                
                gr.getChildren().add(drawMatrix[i][j]);
            }
        }

    }
}
