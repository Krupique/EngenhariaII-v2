/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.inicio;

import com.krupique.bedusystem.controladoras.CtrParametrizacao;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Henrique K. Secchi
 */
public class TelaInicialController implements Initializable {

    @FXML
    private ImageView imageview;
    @FXML
    private Label lblnome;
    @FXML
    private Label lblemail;
    @FXML
    private Circle circle;
    @FXML
    private AnchorPane panedash;
    @FXML
    private Label lbldash;
    @FXML
    private ImageView imgdashboard;
    @FXML
    private ImageView imgcadastros;
    @FXML
    private AnchorPane panerelatorios;
    @FXML
    private Label lblrelatorios;
    @FXML
    private ImageView imgrelatorio;
    @FXML
    private AnchorPane panesair;
    @FXML
    private Label lblsair;
    @FXML
    private ImageView imgsair;
    @FXML
    private Pane panemid;
    @FXML
    private AnchorPane panecadastros;
    @FXML
    private Label lblcadastros;
    @FXML
    private AnchorPane paneservicos;
    @FXML
    private Label lblservicos;
    @FXML
    private ImageView imgservicos;

    
    public static int nivel = -1;
    private String corPane;
    public Image[] img;
    @FXML
    private Pane panePrincipal;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializaEstilo();
        //Acho que isso é do Armando
        /*
        try {
           para = Controladoras.CtrParametrizacao.instancia().carrega();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        imgLogoGrande.setImage(SwingFXUtils.toFXImage(para.getLogoPequeno(), null));
        */
        
        //inicializa();
        /*if(nivel != 0)
        {
            menu_admin.setDisable(true);
            menu_usuarios.setDisable(true);
            if(nivel != 1)
            {
                menu_fornecedor.setDisable(true);
                menu_funcionario.setDisable(true);
                menu_produtos.setDisable(true);
            }
        }*/
    }    
    
    
    
    //################################# PARTE DE ESTILO DA TELA #################################//
    private void inicializaEstilo() {
        img = new Image[11];
        //Imagem de fundo
        img[0] = new Image("/com/krupique/bedusystem/utilidades/recursos/wallpaper.png");
        imageview.setImage(img[0]);
        
        //Icones
        img[1] = new Image("/com/krupique/bedusystem/utilidades/recursos/dashboard_mono.png");
        img[6] = new Image("/com/krupique/bedusystem/utilidades/recursos/dashboard_multi.png");
        imgdashboard.setImage(img[1]);
        
        img[2] = new Image("/com/krupique/bedusystem/utilidades/recursos/register_mono.png");
        img[7] = new Image("/com/krupique/bedusystem/utilidades/recursos/register_multi.png");
        imgcadastros.setImage(img[2]);
        
        img[3] = new Image("/com/krupique/bedusystem/utilidades/recursos/services_mono.png");
        img[8] = new Image("/com/krupique/bedusystem/utilidades/recursos/services_multi.png");
        imgservicos.setImage(img[3]);
        
        img[4] = new Image("/com/krupique/bedusystem/utilidades/recursos/report_mono.png");
        img[9] = new Image("/com/krupique/bedusystem/utilidades/recursos/report_multi.png");
        imgrelatorio.setImage(img[4]);
        
        img[5] = new Image("/com/krupique/bedusystem/utilidades/recursos/exit_mono.png");
        img[10] = new Image("/com/krupique/bedusystem/utilidades/recursos/exit_multi.png");
        imgsair.setImage(img[5]);
        
        BufferedImage[] imgs = CtrParametrizacao.instancia().imgParametrizacao();
        
        try{
            circle.setFill(new ImagePattern(SwingFXUtils.toFXImage(imgs[0], null)));
        }catch(Exception er){
            System.out.println("Erro ao carregar a logo: " + er.getMessage());
        }
        
    }
    
    
    @FXML
    private void dashExit(MouseEvent event) {
        panedash.setStyle("-fx-background-color: #FFFFFF");
        lbldash.setTextFill(new Color(0, 0, 0, 1));
        imgdashboard.setImage(img[1]);
    }

    @FXML
    private void dashEnter(MouseEvent event) {
        panedash.setStyle("-fx-background-color: #EEEEEE");
        lbldash.setTextFill(new Color(0.20, 0.49, 0.39, 1));
        imgdashboard.setImage(img[6]);
    }

    @FXML
    private void cadastroExit(MouseEvent event) {
        panecadastros.setStyle("-fx-background-color: #FFFFFF");
        lblcadastros.setTextFill(new Color(0, 0, 0, 1));
        imgcadastros.setImage(img[2]);
    }

    @FXML
    private void cadastroEnter(MouseEvent event) {
        panecadastros.setStyle("-fx-background-color: #EEEEEE");
        lblcadastros.setTextFill(new Color(0.20, 0.49, 0.39, 1));
        imgcadastros.setImage(img[7]);
    }
    
    @FXML
    private void servicosExit(MouseEvent event) {
        paneservicos.setStyle("-fx-background-color: #FFFFFF");
        lblservicos.setTextFill(new Color(0, 0, 0, 1));
        imgservicos.setImage(img[3]);
    }
    
    @FXML
    private void servicosEnter(MouseEvent event) {
        paneservicos.setStyle("-fx-background-color: #EEEEEE");
        lblservicos.setTextFill(new Color(0.20, 0.49, 0.39, 1));
        imgservicos.setImage(img[8]);
    }

    @FXML
    private void relaExit(MouseEvent event) {
        panerelatorios.setStyle("-fx-background-color: #FFFFFF");
        lblrelatorios.setTextFill(new Color(0, 0, 0, 1));
        imgrelatorio.setImage(img[4]);
    }

    @FXML
    private void relaEnter(MouseEvent event) {
        panerelatorios.setStyle("-fx-background-color: #EEEEEE");
        lblrelatorios.setTextFill(new Color(0.20, 0.49, 0.39, 1));
        imgrelatorio.setImage(img[9]);
    }

    @FXML
    private void sairExit(MouseEvent event) {
        panesair.setStyle("-fx-background-color: #FFFFFF");
        lblsair.setTextFill(new Color(0, 0, 0, 1));
        imgsair.setImage(img[5]);
    }

    @FXML
    private void sairEnter(MouseEvent event) {
        panesair.setStyle("-fx-background-color: #EEEEEE");
        lblsair.setTextFill(new Color(0.20, 0.49, 0.39, 1));
        imgsair.setImage(img[10]);
    }

    //################################# PARTE DE FUNCIONALIDADES DA TELA #################################//
    
    
    @FXML
    private void evtDashboard(MouseEvent event) {
        try
        {
            Stage stage = (Stage)panemid.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/inicio/TelaDashboard.fxml"));
            panemid.getChildren().clear();
            panemid.getChildren().add(root);

        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Impossível abrir tela de Dashboard!\nUm erro inesperado aconteceu!\nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }
    
    @FXML
    private void evtCadastro(MouseEvent event) {
        try
        {
            Stage stage = (Stage)panemid.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/inicio/TelaCadastro.fxml"));
            panemid.getChildren().clear();
            panemid.getChildren().add(root);

        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Impossível abrir tela de Cadastro!\nUm erro inesperado aconteceu!\nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }
    
    @FXML
    private void evtServicos(MouseEvent event) {
        try
        {
            Stage stage = (Stage)panemid.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/inicio/TelaServicos.fxml"));
            panemid.getChildren().clear();
            panemid.getChildren().add(root);

        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Impossível abrir tela de Serviços!\nUm erro inesperado aconteceu!\nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }
    
    @FXML
    private void evtRelatorios(MouseEvent event) {
    }
    
    
    @FXML
    private void evtSair(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deseja mesmo encerrar e sessão?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        
        if(alert.getResult() == ButtonType.YES)
        {
            try
            {
                Stage stage = (Stage)panePrincipal.getScene().getWindow();
                stage.setWidth(380);
                stage.setHeight(430);
                stage.setResizable(false);

                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Dimension dime = toolkit.getScreenSize();
                stage.setX(dime.getWidth()/2 - 380 / 2);
                stage.setY(dime.getHeight()/2 - 460 / 2);

                Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/basicas/TelaLogin.fxml"));
                panePrincipal.getChildren().clear();
                panePrincipal.getChildren().add(root);

            }catch(Exception er){
            }
        }
    }
    
    
   
    
    //################################# PARTES DO SISTEMA QUE NÃO SEI SE AINDA É ÚTIL #################################//
    
    //Não faço idéia do que se trata essa função. by Henrique
    @Deprecated
    public void inicializa()
    {
        Parent root;
        Stage s1 = new Stage();
        try 
        {
            if(nivel == -1)
            {
                root = FXMLLoader.load(getClass().getResource("/Interfaces/FXMLLogin.fxml"));
                Scene scene = new Scene(root);

                s1.setScene(scene);
                s1.initModality(Modality.APPLICATION_MODAL);
                s1.setTitle("Login");
                s1.showAndWait();  

                if(nivel == -1)
                    Platform.exit();
                s1.close();
            }
              
              
        } 
        catch (IOException ex) 
        {
            //Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
