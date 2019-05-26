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
public class TelaCadastroController implements Initializable {

    @FXML
    private AnchorPane paneprincipal;
    @FXML
    private Pane folderFuncs;
    @FXML
    private Pane paneFuncs;
    @FXML
    private ImageView imgFuncs;
    @FXML
    private Pane folderUsers;
    @FXML
    private Pane paneUsers;
    @FXML
    private ImageView imgUsers;
    @FXML
    private Pane folderClientes;
    @FXML
    private Pane paneClientes;
    @FXML
    private ImageView imgClientes;
    @FXML
    private Pane folderFornecs;
    @FXML
    private Pane paneFornecs;
    @FXML
    private ImageView imgFornecs;
    @FXML
    private Pane folderProds;
    @FXML
    private Pane paneProds;
    @FXML
    private ImageView imgProds;
    @FXML
    private Pane folderServs;
    @FXML
    private Pane paneServs;
    @FXML
    private ImageView imgServs;
    @FXML
    private Pane folderOrcs;
    @FXML
    private Pane paneOrcs;
    @FXML
    private ImageView imgOrcs;

    private String corPaneEnter;
    private String corPaneExit;
    private String corFolderEnter;
    private String corFolderExit;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializaEstilo();
        if(TelaInicialController.nivel == 1)
        {
            paneUsers.setDisable(true);
        }
    }    

    //################################# PARTE DE ESTILO DA TELA #################################//
    private void inicializaEstilo()
    {
        carregarIcones();
        
        corFolderEnter = "#CCCCCC";
        corFolderExit = "#FFFFFF";
        
        //Por enquanto está assim, mas depois eu do um grau nisso.
        corPaneEnter =CorSistema.getCorSec();
        //corPaneEnter = "#204f3f";
        corPaneExit = CorSistema.getCorHex();
        //corPaneExit = "#347E65";
        Estilo.setEstiloPane(folderFuncs, paneFuncs, corFolderExit, corPaneExit);
        Estilo.setEstiloPane(folderUsers, paneUsers, corFolderExit, corPaneExit);
        Estilo.setEstiloPane(folderClientes, paneClientes, corFolderExit, corPaneExit);
        Estilo.setEstiloPane(folderFornecs, paneFornecs, corFolderExit, corPaneExit);
        Estilo.setEstiloPane(folderProds, paneProds, corFolderExit, corPaneExit);
        Estilo.setEstiloPane(folderServs, paneServs, corFolderExit, corPaneExit);
        Estilo.setEstiloPane(folderOrcs, paneOrcs, corFolderExit, corPaneExit);
    }
    
    private void carregarIcones()
    {
        String caminho = "/com/krupique/bedusystem/utilidades/recursos/";
        Image img;
        img = new Image(caminho + "client.png");
        imgClientes.setImage(img);
        img = new Image(caminho + "fornec.png");
        imgFornecs.setImage(img);
        img = new Image(caminho + "func.png");
        imgFuncs.setImage(img);
        img = new Image(caminho + "orcs.png");
        imgOrcs.setImage(img);
        img = new Image(caminho + "prod.png");
        imgProds.setImage(img);
        img = new Image(caminho + "servicos.png");
        imgServs.setImage(img);
        img = new Image(caminho + "user.png");
        imgUsers.setImage(img);
    }
    
    @FXML
    private void exitFuncs(MouseEvent event) {
        Estilo.setEstiloPane(folderFuncs, paneFuncs, corFolderExit, corPaneExit);
    }

    @FXML
    private void enterFuncs(MouseEvent event) {
        Estilo.setEstiloPane(folderFuncs, paneFuncs, corFolderEnter, corPaneEnter);
    }

    @FXML
    private void exitUsers(MouseEvent event) {
        Estilo.setEstiloPane(folderUsers, paneUsers, corFolderExit, corPaneExit);
    }

    @FXML
    private void enterUsers(MouseEvent event) {
        Estilo.setEstiloPane(folderUsers, paneUsers, corFolderEnter, corPaneEnter);
    }

    @FXML
    private void exitClientes(MouseEvent event) {
        Estilo.setEstiloPane(folderClientes, paneClientes, corFolderExit, corPaneExit);
    }

    @FXML
    private void enterClientes(MouseEvent event) {
        Estilo.setEstiloPane(folderClientes, paneClientes, corFolderEnter, corPaneEnter);
    }

    @FXML
    private void exitFornecs(MouseEvent event) {
        Estilo.setEstiloPane(folderFornecs, paneFornecs, corFolderExit, corPaneExit);
    }
    
    @FXML
    private void enterFornecs(MouseEvent event) {
        Estilo.setEstiloPane(folderFornecs, paneFornecs, corFolderEnter, corPaneEnter);
    }

    @FXML
    private void exitProds(MouseEvent event){
        Estilo.setEstiloPane(folderProds, paneProds, corFolderExit, corPaneExit);
    }
    
    @FXML
    private void enterProds(MouseEvent event) {
        Estilo.setEstiloPane(folderProds, paneProds, corFolderEnter, corPaneEnter);
    }

    @FXML
    private void exitServs(MouseEvent event) {
        Estilo.setEstiloPane(folderServs, paneServs, corFolderExit, corPaneExit);
    }

    @FXML
    private void enterServs(MouseEvent event) {
        Estilo.setEstiloPane(folderServs, paneServs, corFolderEnter, corPaneEnter);
    }

    @FXML
    private void exitOrcs(MouseEvent event) {
        Estilo.setEstiloPane(folderOrcs, paneOrcs, corFolderExit, corPaneExit);
    }

    @FXML
    private void EnterOrcs(MouseEvent event) {
        Estilo.setEstiloPane(folderOrcs, paneOrcs, corFolderEnter, corPaneEnter);
    }
    
    //################################# PARTE DE FUNCIONALIDADES DA TELA #################################//
    
    @FXML
    private void clicFuncs(MouseEvent event) {
        try
        {
            Stage stage = (Stage)paneprincipal.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/basicas/CadFuncionario.fxml"));
            paneprincipal.getChildren().clear();
            paneprincipal.getChildren().add(root);

        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Funcionários! \nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }
    
    @FXML
    private void clickUsers(MouseEvent event) {
        try
        {
            Stage stage = (Stage)paneprincipal.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/basicas/CadUsuario.fxml"));
            paneprincipal.getChildren().clear();
            paneprincipal.getChildren().add(root);

        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Usuários! \nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }
    
    @FXML
    private void clickClientes(MouseEvent event) {
        try
        {
            Stage stage = (Stage)paneprincipal.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/basicas/CadCliente.fxml"));
            paneprincipal.getChildren().clear();
            paneprincipal.getChildren().add(root);

        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Clientes! \nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }
    
    @FXML
    private void clickFornecs(MouseEvent event) {
        try
        {
            Stage stage = (Stage)paneprincipal.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/basicas/CadFornecedor.fxml"));
            paneprincipal.getChildren().clear();
            paneprincipal.getChildren().add(root);

        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Fornecedores! \nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }
    
    @FXML
    private void clickProds(MouseEvent event) {
        try
        {
            Stage stage = (Stage)paneprincipal.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/basicas/CadProdutos.fxml"));
            paneprincipal.getChildren().clear();
            paneprincipal.getChildren().add(root);

        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Produtos! \nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }
    
    @FXML
    private void clickServs(MouseEvent event) {
    }
    
    @FXML
    private void clickOrcs(MouseEvent event) {
    }
    
    //################################# PARTES DO SISTEMA QUE NÃO SEI SE AINDA É ÚTIL #################################//

    
    
}
