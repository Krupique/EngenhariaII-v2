/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.interfaces.buscas;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.utilidades.CorSistema;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Henrique K. Secchi
 */
public class BuscaFornecedorController implements Initializable {

    @FXML
    private BorderPane paneprincipal;
    @FXML
    private HBox paneBotoes;
    @FXML
    private JFXButton btSelecionar;
    @FXML
    private JFXButton btVoltar;
    @FXML
    private JFXTextField txtPesquisa;
    @FXML
    private JFXButton btBuscar;
    @FXML
    private HBox panePintar;
    @FXML
    private TableView<?> tableview;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializaEstilo();
    }    

    //################################# PARTE DE ESTILO DA TELA #################################//
    
    private void inicializaEstilo()
    {
        String cor = CorSistema.getCorHex();
        btSelecionar.setStyle("-fx-background-color: " + cor);
        btBuscar.setStyle("-fx-background-color: " + cor);
        btVoltar.setStyle("-fx-background-color: " + cor);
        panePintar.setStyle("-fx-background-color: " + cor);
        txtPesquisa.setFocusColor(CorSistema.hex2Rgb(CorSistema.getCorHex()));
        /*
        Pintar radio buttons também
        */
    }

    //################################# PARTE DE FUNCIONALIDADES DA TELA #################################//
    
    @FXML
    private void evtSelecionar(ActionEvent event) {
    }

    @FXML
    private void evtVoltar(ActionEvent event) {
    }

    @FXML
    private void evtBuscar(ActionEvent event) {
    }
    
}
