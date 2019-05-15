/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.interfaces.fundamentais;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Henrique K. Secchi
 */
public class QuitarContasPagarController implements Initializable {

    @FXML
    private BorderPane paneprincipal;
    @FXML
    private HBox paneBotoes;
    @FXML
    private JFXButton btPagar;
    @FXML
    private JFXButton brRemover;
    @FXML
    private JFXButton btCancelar;
    @FXML
    private JFXButton btBuscar;
    @FXML
    private JFXButton btVoltar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void evtPagar(ActionEvent event) {
    }

    @FXML
    private void evtRemover(ActionEvent event) {
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
    }

    @FXML
    private void evtBuscar(ActionEvent event) {
    }

    @FXML
    private void evtVoltar(ActionEvent event) {
    }
    
}
