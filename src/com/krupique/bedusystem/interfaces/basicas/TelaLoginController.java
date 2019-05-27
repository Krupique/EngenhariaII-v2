/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.interfaces.basicas;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.entidades.Usuario;
import com.krupique.bedusystem.inicio.TelaInicialController;
import com.krupique.bedusystem.utilidades.CorSistema;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Henrique K. Secchi
 */
public class TelaLoginController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private ImageView imageview;
    @FXML
    private JFXTextField txtuser;
    @FXML
    private JFXPasswordField txtsenha;
    @FXML
    private JFXButton btentrar;
    @FXML
    private JFXButton btsair;
    @FXML
    private Label lb_erro;
    private final Tooltip t = new Tooltip();
    private static boolean flag = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializaEstilo();
    }    
    
    //################################# PARTE DE ESTILO DA TELA #################################//

    private void inicializaEstilo() {
        Image img = new Image("com/krupique/bedusystem/utilidades/recursos/login.png");
        imageview.setImage(img);
        btentrar.setStyle("-fx-background-color: " + CorSistema.getCorHex());
        btsair.setStyle("-fx-background-color: " + CorSistema.getCorHex());
        
        txtuser.setFocusColor(CorSistema.hex2Rgb(CorSistema.getCorHex()));
        txtsenha.setFocusColor(CorSistema.hex2Rgb(CorSistema.getCorHex()));
    }
    
    //################################# PARTE DE FUNCIONALIDADES DA TELA #################################//
    
    @FXML
    private void evtEntrar(ActionEvent event) {
        
        lb_erro.setText("");
        if(!txtuser.getText().equals(""))
            if(!txtsenha.getText().equals(""))
            {
                Usuario user = new Usuario();
                if(user.busca(txtuser.getText()))
                {
                    if(user.getSenha().equals(txtsenha.getText()))
                    {
                        TelaInicialController.nivel = user.getNivel();
                        //Platform.exit();
                        flag = true;
                        abrirTelaPrincipal();
                    }
                    else
                    {
                        lb_erro.setText("Senha incorreta, por favor digite novamente");
                        txtsenha.clear();
                        t.setText("Senha incorreta");
                        txtsenha.setTooltip(t);
                    }
                }
                else
                {
                    lb_erro.setText("Usuario não encontrado, por favor digite novamente");
                    txtuser.clear();
                    txtsenha.clear();
                    t.setText("Usuario não encontrado");
                    txtuser.setTooltip(t);
                }
            }
            else
            {
                lb_erro.setText("Digite uma senha");
                t.setText("Digite o campo da senha corretamente");
                txtsenha.setTooltip(t);
            }
        else
        {
            lb_erro.setText("Digite um login");
            t.setText("Digite o campo do login corretamente");
            txtuser.setTooltip(t);
        }
        
    }

    @FXML
    private void evtSair(ActionEvent event) {
        Platform.exit();
    }

    private void abrirTelaPrincipal() {
        try
        {
            
            
            Stage stage = (Stage)pane.getScene().getWindow();
            stage.setWidth(1080);
            stage.setHeight(680 + 30);
            stage.setResizable(false);
            
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension dime = toolkit.getScreenSize();
            stage.setX(dime.getWidth()/2 - 1080 / 2);
            stage.setY(dime.getHeight()/2 - (680 + 60) / 2);
            
            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/inicio/TelaInicial.fxml"));
            pane.getChildren().clear();
            pane.getChildren().add(root);
            
        }catch(Exception er){
        }
    }

    @FXML
    private void evtEnter(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER) && !flag)
            evtEntrar(new ActionEvent());
    }

    
    
}
