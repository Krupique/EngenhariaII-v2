/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.interfaces.basicas;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.controladoras.CtrParametrizacao;
import com.krupique.bedusystem.entidades.Parametrizacao;
import com.krupique.bedusystem.inicio.Main;
import com.krupique.bedusystem.utilidades.CorSistema;
import com.krupique.bedusystem.utilidades.MaskFieldUtil;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author Henrique K. Secchi
 */
public class TelaParametrizacaoController implements Initializable {

    @FXML
    private HBox paneBotoes;
    @FXML
    private JFXButton btSalvar;
    @FXML
    private JFXButton btVoltar;
    @FXML
    private JFXTextField tfNomeFantasia;
    @FXML
    private JFXTextField tfRazaoSocial;
    @FXML
    private JFXTextField tfEndereco;
    @FXML
    private JFXColorPicker tfCorFundo;
    @FXML
    private JFXTextField tfCep;
    @FXML
    private JFXButton btConsultar;
    @FXML
    private JFXComboBox<String> cbcidade;
    @FXML
    private JFXComboBox<String> cbbairro;
    @FXML
    private JFXTextField tfNumero;
    @FXML
    private JFXTextField tfSite;
    @FXML
    private JFXTextField tfEmail;
    @FXML
    private JFXTextField tfFone;
    @FXML
    private ImageView imageview;
    @FXML
    private ImageView imageview2;
    private FileInputStream f;
    private FileInputStream f2;
    private String caminho = null;
    private String caminho2 = null;
    private Parametrizacao para; //Ta fora do MVC cara de égua
    @FXML
    private AnchorPane paneprincipal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializaEstilo();
        
        try
        {
            if (CtrParametrizacao.instancia().inicia())
            {
                try
                {
                    //Carrega a tebela
                   para = CtrParametrizacao.instancia().carrega();
                    
                    tfNomeFantasia.setText(para.getNome());
                    tfRazaoSocial.setText(para.getRazaoSocial());
                    tfFone.setText(para.getTelefone());
                    tfEmail.setText(para.getEmail());
                    tfEndereco.setText(para.getRua());
                    cbbairro.getSelectionModel().select(para.getBairro());
                    cbcidade.getSelectionModel().select(para.getCidade());
                    tfCep.setText(para.getCep());
                    evtConsultar();
                    tfCorFundo.setValue(hex2Rgb(para.getCor()));
                    tfSite.setText(para.getSite());

                    imageview.setImage(SwingFXUtils.toFXImage(para.getLogoGrande(), null));
                    imageview2.setImage(SwingFXUtils.toFXImage(para.getLogoPequeno(), null));

                } catch (SQLException | IOException ex)
                {
                    System.out.println(ex.getMessage());
                }

            } else
            {
                // primeira vez do programa
                System.out.println("TESTE TESTE");
                setarBotoes(false);
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(TelaParametrizacaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }   
    //################################# PARTE DE ESTILO DA TELA #################################//
    private void inicializaEstilo()
    {
        pintarBotoes();
        Image img = new Image("/com/krupique/bedusystem/utilidades/recursos/add-image.png");
        imageview.setImage(img);
        imageview2.setImage(img);
    }
    
    private void pintarBotoes()
    {
        String cor = CorSistema.getCorHex();
        
        btConsultar.setStyle("-fx-background-color: " + cor);
        btSalvar.setStyle("-fx-background-color: " + cor);
        btVoltar.setStyle("-fx-background-color: " + cor);
    }
    
    public String toRGBCode( Color color )
    {
        return String.format( "#%02X%02X%02X",
            (int)( color.getRed() * 255 ),
            (int)( color.getGreen() * 255 ),
            (int)( color.getBlue() * 255 ) );
    }
    
    public Color hex2Rgb(String colorStr){
        
        return new Color(
            (double)Integer.valueOf( colorStr.substring( 1, 3 ), 16 ) / 255,
            (double)Integer.valueOf( colorStr.substring( 3, 5 ), 16 ) / 255,
            (double)Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) / 255, 1);
    }
    
    //################################# PARTE DE FUNCIONALIDADES DA TELA #################################//
    
    public void setarBotoes(boolean value)
    {
        btVoltar.setText("Sair");
        
    }
    
    public String consultaCep(String cep, String formato)
    {
        String username = "seu login";
        String password = "sua senha";
        String proxyHost = "177.131.35.1";
        String proxyPort = "3128";

        StringBuffer dados = new StringBuffer();
        try
        {
           URL url = new URL("http://apps.widenet.com.br/busca-cep/api/cep." + formato + "?code=" + cep);
            URLConnection con = url.openConnection();
            con.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            InputStream in = con.getInputStream();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
                String s = "";
                while (null != (s = br.readLine()))
                {
                    dados.append(s);
                }
            }
        } catch (IOException ex)
        {
            System.out.println(ex);

        }
        return dados.toString();
    }
    
   
    
    private void habilitarBotoes(boolean salvar, boolean alterar, boolean cancelar, boolean buscar, boolean voltar)
    {
        btSalvar.setDisable(salvar);
        btVoltar.setDisable(voltar);
    }
  
    @FXML
    private void maskCep(KeyEvent event) {
        MaskFieldUtil.cepField(tfCep);
    }

    @FXML
    private void MaskNum(KeyEvent event) {
        MaskFieldUtil.numericField(tfNumero);
    }

    @FXML
    private void MaskFone(KeyEvent event) {
        MaskFieldUtil.foneField(tfFone);
    }
    
    @FXML
    private void logoPrincipal(MouseEvent event) {
        try
        {
            Image img;
            FileChooser fc = new FileChooser();
            FileChooser.ExtensionFilter filterPNG = new FileChooser.ExtensionFilter("PNG (*.png)", "*.png");
            FileChooser.ExtensionFilter filterJPG = new FileChooser.ExtensionFilter("JPEG (*.jpg, *.jpeg)", "*.jpg", "*.jpeg");
            FileChooser.ExtensionFilter filterGIF = new FileChooser.ExtensionFilter("GIF (*.gif)", "*.gif");

            fc.getExtensionFilters().addAll(filterPNG, filterJPG, filterGIF);

            fc.setInitialDirectory(new File("C:\\"));
            File arq = fc.showOpenDialog(null);

            if(arq != null)
            {
                caminho = arq.getAbsolutePath();
                f = new FileInputStream(arq);
                img = new Image(arq.toURI().toString());
                imageview.setImage(img);
            }
        }catch(Exception er)
        {
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir imagem!\nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
            
    }

    @FXML
    private void logoSecundario(MouseEvent event) {
        try
        {
            Image img;
            FileChooser fc = new FileChooser();
            FileChooser.ExtensionFilter filterPNG = new FileChooser.ExtensionFilter("PNG (*.png)", "*.png");
            FileChooser.ExtensionFilter filterJPG = new FileChooser.ExtensionFilter("JPEG (*.jpg, *.jpeg)", "*.jpg", "*.jpeg");
            FileChooser.ExtensionFilter filterGIF = new FileChooser.ExtensionFilter("GIF (*.gif)", "*.gif");

            fc.getExtensionFilters().addAll(filterPNG, filterJPG, filterGIF);

            fc.setInitialDirectory(new File("C:\\"));
            File arq = fc.showOpenDialog(null);

            if(arq != null)
            {
                caminho2 = arq.getAbsolutePath();
                f2 = new FileInputStream(arq);
                img = new Image(arq.toURI().toString());
                imageview2.setImage(img);
            }
        }catch(Exception er)
        {
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir imagem!\nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }

    
    
    @FXML
    private void evtSalvar(ActionEvent event) {
        try
        {
            String cor = new String(toRGBCode(tfCorFundo.getValue()));
            if (!tfRazaoSocial.getText().isEmpty() && !tfNomeFantasia.getText().isEmpty() && !tfEndereco.getText().isEmpty() && !cor.isEmpty() && !tfCep.getText().isEmpty() && !tfSite.getText().isEmpty() && !tfEmail.getText().isEmpty() && !tfFone.getText().isEmpty())
            {
              //nome,fantasia,logoGrande,logoPequeno,telefone,email,razaoSocial,rua,bairro,cidade,cep,cor,site;
                if(caminho != null && caminho2 != null)
                {
                    
                       if(CtrParametrizacao.instancia().Manipular(tfNomeFantasia.getText(),
                               tfNomeFantasia.getText(), caminho, caminho2, tfFone.getText(), 
                               tfEmail.getText(),tfRazaoSocial.getText(), tfEndereco.getText(),
                               cbbairro.getValue(),cbcidade.getValue(),tfCep.getText(), cor,
                               tfSite.getText()))
                        {
                        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setContentText("Cadastrado com Sucesso!!!");
                        alerta.showAndWait();
                        }
                }
                else
                {
                    
                    if(CtrParametrizacao.instancia().alterar(tfNomeFantasia.getText(),
                            tfNomeFantasia.getText(), para.getLogoGrande(), para.getLogoPequeno(),
                            tfFone.getText(), tfEmail.getText(),tfRazaoSocial.getText(), 
                            tfEndereco.getText(),cbbairro.getValue(),cbcidade.getValue(),
                            tfCep.getText(), cor, tfSite.getText()))
                        {
                        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setContentText("Alterado com Sucesso!!!");
                        alerta.showAndWait();
                        }
                }

                //abrir tela
                CorSistema.setCorHex(cor);
                setarTela();
                
                
            } else
            {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setContentText("Campos Incorreto!!!");
                alerta.showAndWait();
            }
        }catch(Exception er)
        {
            Alert a = new Alert(Alert.AlertType.ERROR, "Um erro inesperado aconteceu!\nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }


    private void evtCancelar(ActionEvent event) {
        inicializaEstilo();
        limparCampos();
    }


    @FXML
    private void evtVoltar(ActionEvent event) {
        if(btVoltar.getText().equals("Sair")){
            Platform.exit();
        }
    }

    @FXML
    private void evtConsultar() {
        String str = consultaCep(tfCep.getText().replace("-", ""), "json");
        JSONObject my_obj = new JSONObject(str);
        cbcidade.getItems().clear();
        cbcidade.getItems().add(my_obj.getString("city"));
        cbcidade.getSelectionModel().select(my_obj.getString("city"));
        
        cbbairro.getItems().clear();
        cbbairro.getItems().add(my_obj.getString("district"));
        cbbairro.getSelectionModel().select(my_obj.getString("district"));
        
    }

    private void setarTela() {
        try
        {
            if(!CtrParametrizacao.instancia().inicia())
            {
                Stage stage = (Stage)paneprincipal.getScene().getWindow();
                stage.setWidth(380);
                stage.setHeight(400 + 30);
                stage.setResizable(false);

                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Dimension dime = toolkit.getScreenSize();
                stage.setX(dime.getWidth()/2 - 380 / 2);
                stage.setY(dime.getHeight()/2 - (400 + 60) / 2);

                Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/basicas/TelaLogin.fxml"));
                paneprincipal.getChildren().clear();
                paneprincipal.getChildren().add(root);
            }
            else
            {

                    paneprincipal.toBack();
                    paneprincipal.getChildren().clear();

            }

            
        }catch(Exception er){
        }
    }

    

    private void limparCampos(){
        //Implementa ai macaco
    }
    
}
