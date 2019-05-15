/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.inicio;

import com.krupique.bedusystem.controladoras.CtrParametrizacao;
import com.krupique.bedusystem.utilidades.Banco;
import com.krupique.bedusystem.utilidades.CorSistema;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 *
 * @author Henrique K. Secchi
 */
public class Main extends Application{
    
    private static Stage stage;
    
    @Override
    public void start(Stage stage) throws Exception {
        String janela;
        /*Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/inicio/TelaInicial.fxml"));
        stage.setWidth(1080);
        stage.setHeight(680);*/
         
        /*if(CtrParametrizacao.instancia().inicia())
        {*/
            janela = "/com/krupique/bedusystem/interfaces/basicas/TelaLogin.fxml";
           /* CorSistema.setCorHex(CtrParametrizacao.instancia().corParamatrizacao());
        }
        else
        {
            janela = "/com/krupique/bedusystem/interfaces/basicas/TelaParametrizacao.fxml";
        }*/
        
        Parent root = FXMLLoader.load(getClass().getResource(janela));
        Scene scene = new Scene(root);
        
        
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        try
        {
            if(Banco.conectar())
                launch(args);
            else
            {
                Alert a = new Alert(Alert.AlertType.ERROR, "Erro: Impossível se conectar ao banco de dados!", ButtonType.OK);
                a.showAndWait();
                Platform.exit();
            }
        }catch(Exception er){
            System.out.println("Erro: " + er.getMessage());
            Platform.exit();
        }
    }

    public static Stage getStage() {
        return stage;
    }
}
