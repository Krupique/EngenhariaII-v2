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
public class TelaServicosController implements Initializable {

    @FXML
    private AnchorPane paneprincipal;
    @FXML
    private Pane folderCompra;
    @FXML
    private Pane paneCompra;
    @FXML
    private ImageView imgCompra;
    @FXML
    private Pane folderOS;
    @FXML
    private Pane paneOS;
    @FXML
    private ImageView imgOS;
    @FXML
    private Pane folderOrc;
    @FXML
    private Pane paneOrc;
    @FXML
    private ImageView imgOrc;

    
    private String corPaneEnter;
    private String corPaneExit;
    private String corFolderEnter;
    private String corFolderExit;
    @FXML
    private Pane paneAprovar;
    @FXML
    private ImageView imgAprovar;
    @FXML
    private Pane folderAprovar;
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
        carregarIcones();
        
        corFolderEnter = "#CCCCCC";
        corFolderExit = "#FFFFFF";
        
        //Por enquanto está assim, mas depois eu do um grau nisso.
        corPaneEnter =CorSistema.getCorSec();
        corPaneExit = CorSistema.getCorHex();
        
        Estilo.setEstiloPane(folderCompra, paneCompra, corFolderExit, corPaneExit);
        Estilo.setEstiloPane(folderOS, paneOS, corFolderExit, corPaneExit);
        Estilo.setEstiloPane(folderOrc, paneOrc, corFolderExit, corPaneExit);
    }
    
    private void carregarIcones()
    {
        String caminho = "/com/krupique/bedusystem/utilidades/recursos/";
        Image img;
        img = new Image(caminho + "compra.png");
        imgCompra.setImage(img);
        img = new Image(caminho + "os.png");
        imgOS.setImage(img);
        img = new Image(caminho + "orcamento.png");
        imgOrc.setImage(img);
        img = new Image(caminho + "aprovar_orcamento.png");
        imgAprovar.setImage(img);
    }

    @FXML
    private void exitCompra(MouseEvent event) {
        Estilo.setEstiloPane(folderCompra, paneCompra, corFolderExit, corPaneExit);
    }

    @FXML
    private void enterCompra(MouseEvent event) {
        Estilo.setEstiloPane(folderCompra, paneCompra, corFolderEnter, corPaneEnter);
    }

    @FXML
    private void exitOS(MouseEvent event) {
        Estilo.setEstiloPane(folderOS, paneOS, corFolderExit, corPaneExit);
    }

    @FXML
    private void enterOS(MouseEvent event) {
        Estilo.setEstiloPane(folderOS, paneOS, corFolderEnter, corPaneEnter);
    }
    
    @FXML
    private void exitOrc(MouseEvent event) {
        Estilo.setEstiloPane(folderOrc, paneOrc, corFolderExit, corPaneExit);
    }

    @FXML
    private void enterOrc(MouseEvent event) {
        Estilo.setEstiloPane(folderOrc, paneOrc, corFolderEnter, corPaneEnter);
    }
     @FXML
    private void exitAprovar(MouseEvent event)
    {
        Estilo.setEstiloPane(folderAprovar, paneAprovar, corFolderExit, corPaneExit);
    }

    @FXML
    private void enterAprovar(MouseEvent event)
    {
        Estilo.setEstiloPane(folderAprovar, paneAprovar, corFolderEnter, corPaneEnter);
    }
    
    //################################# PARTE DE FUNCIONALIDADES DA TELA #################################//
    
    @FXML
    private void clickCompra(MouseEvent event) {
        try
        {
            Stage stage = (Stage)paneprincipal.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/fundamentais/RegistrarCompra.fxml"));
            paneprincipal.getChildren().clear();
            paneprincipal.getChildren().add(root);

        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Compras! \nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void clickOS(MouseEvent event) {
        try
        {
            Stage stage = (Stage)paneprincipal.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/fundamentais/OS.fxml"));
            paneprincipal.getChildren().clear();
            paneprincipal.getChildren().add(root);

        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Ordem de Serviço! \nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }
    
    @FXML
    private void clickOrc(MouseEvent event) {
        try
        {
            Stage stage = (Stage)paneprincipal.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/fundamentais/RealizarOrcamento.fxml"));
            paneprincipal.getChildren().clear();
            paneprincipal.getChildren().add(root);

        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Orçamentos! \nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }
    
    @FXML
    private void clickAprovar(MouseEvent event)
    {
        try
        {
            Stage stage = (Stage)paneprincipal.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/fundamentais/AprovarOrcamento.fxml"));
            paneprincipal.getChildren().clear();
            paneprincipal.getChildren().add(root);

        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Compras! \nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }
    //################################# PARTES DO SISTEMA QUE NÃO SEI SE AINDA É ÚTIL #################################//
}
