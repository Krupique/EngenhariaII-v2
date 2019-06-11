
package com.krupique.bedusystem.inicio;

import com.krupique.bedusystem.controladoras.CtrParametrizacao;
import com.krupique.bedusystem.utilidades.Banco;
import com.krupique.bedusystem.utilidades.CorSistema;
import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Main extends Application{
    
    private static Stage stage;
    
    @Override
    public void start(Stage stage) throws Exception {
        String janela;
        Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/inicio/TelaInicial.fxml"));
        stage.setWidth(880);
        stage.setHeight(700);
         
       if(CtrParametrizacao.instancia().inicia())
        {
                stage.setWidth(380);
                stage.setHeight(430);
                stage.setResizable(false);

                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Dimension dime = toolkit.getScreenSize();
                stage.setX(dime.getWidth()/2 - 380 / 2);
                stage.setY(dime.getHeight()/2 - 460 / 2);
            janela = "/com/krupique/bedusystem/interfaces/basicas/TelaLogin.fxml";

            CorSistema.setCorHex(CtrParametrizacao.instancia().corParamatrizacao());
        }
        else
        {
            janela = "/com/krupique/bedusystem/interfaces/basicas/TelaParametrizacao.fxml";
        }
        janela = "/com/krupique/bedusystem/interfaces/basicas/TelaLogin.fxml";
        root = FXMLLoader.load(getClass().getResource(janela));
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
