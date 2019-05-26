/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.inicio;

import com.krupique.bedusystem.utilidades.CorSistema;
import com.krupique.bedusystem.utilidades.estilo.Estilo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Henrique K. Secchi
 */
public class TelaDashboardController implements Initializable {

    @FXML
    private AnchorPane panePrincipal;
    @FXML
    private Pane folderParam;
    @FXML
    private Pane paneParam;
    @FXML
    private ImageView imgParam;

    private String corPaneEnter;
    private String corPaneExit;
    private String corFolderEnter;
    private String corFolderExit;
    @FXML
    private Pane folderCtPagar;
    @FXML
    private Pane paneCtPagar;
    @FXML
    private ImageView imgCtPagar;
    @FXML
    private Pane folderCtReceber;
    @FXML
    private Pane paneCtReceber;
    @FXML
    private ImageView imgCtReceber;
    @FXML
    private Pane pane_para;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializaEstilo();
        if(TelaInicialController.nivel != 0)
          pane_para.setDisable(true);
    }    
    //################################# PARTE DE ESTILO DA TELA #################################//
    private void inicializaEstilo()
    {
        carregarIcones();
        corFolderEnter = "#CCCCCC";
        corFolderExit = "#FFFFFF";
        
        //Por enquanto está assim, mas depois eu do um grau nisso.
        corPaneEnter = CorSistema.getCorSec();
        corPaneExit = CorSistema.getCorHex();
        
        Estilo.setEstiloPane(folderParam, paneParam, corFolderExit, corPaneExit);
        Estilo.setEstiloPane(folderCtPagar, paneCtPagar, corFolderExit, corPaneExit);
        Estilo.setEstiloPane(folderCtReceber, paneCtReceber, corFolderExit, corPaneExit);
    }
    
    
    private void carregarIcones()
    {
        String caminho = "/com/krupique/bedusystem/utilidades/recursos/";
        Image img;
        img = new Image(caminho + "param.png");
        imgParam.setImage(img);
        img = new Image(caminho + "pag.png");
        imgCtPagar.setImage(img);
        img = new Image(caminho + "receb.png");
        imgCtReceber.setImage(img);
        
    }
    
    @FXML
    private void exitParam(MouseEvent event) {
        Estilo.setEstiloPane(folderParam, paneParam, corFolderExit, corPaneExit);
    }

    @FXML
    private void enterParam(MouseEvent event) {
        Estilo.setEstiloPane(folderParam, paneParam, corFolderEnter, corPaneEnter);
    }
    
    @FXML
    private void exitCtPagar(MouseEvent event) {
        Estilo.setEstiloPane(folderCtPagar, paneCtPagar, corFolderExit, corPaneExit);
    }

    @FXML
    private void enterCtPagar(MouseEvent event) {
        Estilo.setEstiloPane(folderCtPagar, paneCtPagar, corFolderEnter, corPaneEnter);
    }

    @FXML
    private void exitCtReceber(MouseEvent event) {
        Estilo.setEstiloPane(folderCtReceber, paneCtReceber, corFolderExit, corPaneExit);
    }

    @FXML
    private void enterCtReceber(MouseEvent event) {
        Estilo.setEstiloPane(folderCtReceber, paneCtReceber, corFolderEnter, corPaneEnter);
    }
    
    //################################# PARTE DE FUNCIONALIDADES DA TELA #################################//
    @FXML
    private void clickParam(MouseEvent event) {
        try
        {
            Stage stage = (Stage)panePrincipal.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/basicas/TelaParametrizacao.fxml"));
            panePrincipal.getChildren().clear();
            panePrincipal.getChildren().add(root);

        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Parametrização! \nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }
    
    @FXML
    private void clickCtPagar(MouseEvent event) {
        try
        {
            Stage stage = (Stage)panePrincipal.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/fundamentais/QuitarContasPagar.fxml"));
            panePrincipal.getChildren().clear();
            panePrincipal.getChildren().add(root);

        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Quitar Contas à Pagar! \nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void clickCtReceber(MouseEvent event) {
        try
        {
            Stage stage = (Stage)panePrincipal.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/fundamentais/QuitarContasReceber.fxml"));
            panePrincipal.getChildren().clear();
            panePrincipal.getChildren().add(root);

        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Quitar Contas à Receber! \nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }
    //################################# PARTES DO SISTEMA QUE NÃO SEI SE AINDA É ÚTIL #################################//
}
