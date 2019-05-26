/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.interfaces.basicas;

import com.krupique.bedusystem.interfaces.buscas.BuscaFuncionarioController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.controladoras.ctrFuncionarios;
import com.krupique.bedusystem.utilidades.Banco;
import com.krupique.bedusystem.utilidades.CorSistema;
import com.krupique.bedusystem.utilidades.MaskFieldUtil;
import com.krupique.bedusystem.utilidades.Objeto;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Henrique K. Secchi
 */
public class CadFuncionarioController implements Initializable
{
    public static Objeto funcionario;
    final ToggleGroup group = new ToggleGroup();
    public static int cod;
    public static int acao;

    @FXML
    private HBox paneBotoes;
    @FXML
    private JFXButton btNovo;
    @FXML
    private JFXButton btSalvar;
    @FXML
    private JFXButton btAlterar;
    @FXML
    private JFXButton btExcluir;
    @FXML
    private JFXButton btCancelar;
    @FXML
    private JFXButton btBuscar;
    @FXML
    private JFXButton btVoltar;
    @FXML
    private BorderPane paneprincipal;
    @FXML
    private JFXTextField tf_nome;
    @FXML
    private JFXTextField tf_funcao;
    @FXML
    private JFXTextField tf_telefone;
    @FXML
    private JFXTextField tf_celular;
    @FXML
    private JFXTextField tf_email;
    @FXML
    private JFXTextField tf_rua;
    @FXML
    private JFXTextField tf_bairro;
    @FXML
    private JFXTextField tf_numero;
    @FXML
    private JFXTextField tf_cidade;
    @FXML
    private JFXTextField tf_cep;
    @FXML
    private JFXButton btn_cep;
    @FXML
    private JFXRadioButton rb_masculino;
    @FXML
    private JFXRadioButton rb_feminino;
    @FXML
    private JFXDatePicker dp_data;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        inicializaEstilo();
        inicializa();
        rb_feminino.setToggleGroup(group);
        rb_masculino.setToggleGroup(group);
        rb_masculino.setSelected(true);
        cod = -1;
        acao = 0;
        
        if(funcionario != null)
            preenche_campos();
    }

    //################################# PARTE DE ESTILO DA TELA #################################//
    private void inicializaEstilo()
    {
        String cor = CorSistema.getCorHex();
        btNovo.setStyle("-fx-background-color: " + cor);
        btSalvar.setStyle("-fx-background-color: " + cor);
        btAlterar.setStyle("-fx-background-color: " + cor);
        btExcluir.setStyle("-fx-background-color: " + cor);
        btCancelar.setStyle("-fx-background-color: " + cor);
        btBuscar.setStyle("-fx-background-color: " + cor);
        btVoltar.setStyle("-fx-background-color: " + cor);
    }
    
    private void limpar()
    {
        tf_nome.clear(); tf_funcao.clear(); tf_telefone.clear(); tf_celular.clear(); tf_bairro.clear(); tf_cep.clear();
        tf_cidade.clear(); tf_email.clear(); tf_rua.clear(); dp_data.setValue(LocalDate.now()); tf_numero.clear();
    }
    
    private void altera_campos(boolean b1,boolean b2,boolean b3,boolean b4,boolean b5)
    {
        tf_nome.setDisable(b1);
        tf_funcao.setDisable(b1);
        tf_telefone.setDisable(b1);
        tf_celular.setDisable(b1);
        tf_email.setDisable(b1);
        tf_cep.setDisable(b1);
        btn_cep.setDisable(b1);
        rb_feminino.setDisable(b1);
        rb_masculino.setDisable(b1);
        tf_rua.setDisable(b1);
        tf_bairro.setDisable(b1);
        tf_numero.setDisable(b1);
        tf_cidade.setDisable(b1);
        dp_data.setDisable(b1);
        btSalvar.setDisable(b2);
        btNovo.setDisable(b3);
        btAlterar.setDisable(b4);
        btExcluir.setDisable(b5);
    }

    private void inicializa()
    {
        acao = 0;
        limpar();
        altera_campos(true, true, false, true, true);
    }
    
    private String valida_campos()
    {
        String erro = "";
        if(tf_nome.getText().equals(""))
            erro += "Nome invalido\n";
         if(tf_funcao.getText().equals(""))
             erro += "Função inválida\n";
         if(tf_rua.getText().equals(""))
             erro += "Rua inválida\n";
         if(tf_bairro.getText().equals(""))
             erro += "Bairro inválido\n";
         if(tf_cidade.getText().equals(""))
             erro += "Cidade inválida\n";
         if(dp_data.getValue().compareTo(LocalDate.now()) > 0)
             erro += "Data inválida\n";
         return erro;
    }
    
    private void preenche_campos()
    {
        tf_nome.setText(funcionario.getParam2());
        tf_funcao.setText(funcionario.getParam3());
        if(funcionario.getParam4() != null)
            tf_telefone.setText(funcionario.getParam4());
        else
            tf_telefone.setText("");
        if(funcionario.getParam5() != null)
            tf_celular.setText(funcionario.getParam5());
        else
            tf_celular.setText("");
        if(funcionario.getParam6() != null)
            tf_email.setText(funcionario.getParam6());
        else
            tf_email.setText("");
        String aux = funcionario.getParam7();
        if(aux.contains(","))
        {
            tf_rua.setText(aux.substring(0, aux.indexOf (",")));
            tf_numero.setText(aux.substring(aux.indexOf(",") + 1));
        }
        else
            tf_rua.setText(aux);
        tf_bairro.setText(funcionario.getParam8());
        tf_cidade.setText(funcionario.getParam9());
        if(funcionario.getParam11().equals(true))
            rb_masculino.setSelected(true);
        else 
            rb_feminino.setSelected(true);
        cod = Integer.parseInt(funcionario.getParam1());
        
        altera_campos(true, true, true, false, false);
    }
    
    //################################# PARTE DE FUNCIONALIDADES DA TELA #################################//
    @FXML
    private void clickNovo(ActionEvent event)
    {
        acao = 1;
        altera_campos(false, false, true, true, true);
    }

    @FXML
    private void clickSalvar(ActionEvent event)
    {
        JFXTextField aux = new JFXTextField(tf_rua.getText() + ", " + tf_numero.getText());
        if(acao == 1)
        {
            String erro = valida_campos();
            Alert alert;
            if(valida_campos().equals(""))
            {
                if(new ctrFuncionarios().gravar(tf_nome, tf_funcao, tf_telefone, tf_celular, tf_email, 
                        rb_feminino.isSelected()?rb_feminino:rb_masculino,aux, tf_bairro, tf_cidade, dp_data))
                {
                    alert = new Alert(Alert.AlertType.INFORMATION, "Funcionário cadastrado com sucesso", ButtonType.OK);
                    inicializa();
                }
                else
                    alert = new Alert(Alert.AlertType.ERROR, "Erro no cadastramento do funcionário: " + Banco.getCon().getMensagemErro(), ButtonType.OK);
            }
            else
               alert = new Alert(Alert.AlertType.ERROR, erro, ButtonType.OK); 
            alert.showAndWait();
        }
        else if(acao == 2)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Deseja alterar " + tf_nome.getText() + "?");
            alert.setTitle("Alteração");

            ButtonType btn_yes = new ButtonType("Yes");
            ButtonType btn_no = new ButtonType("No");

            alert.getButtonTypes().setAll(btn_yes,btn_no);

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == btn_yes)
            {
                String erro = valida_campos();
                alert = new Alert(Alert.AlertType.INFORMATION);
                if(erro.equals("") && new ctrFuncionarios().alterar(tf_nome, tf_funcao, tf_telefone, tf_celular, tf_email, 
                        rb_feminino.isSelected()?rb_feminino:rb_masculino,aux, tf_bairro, tf_cidade, dp_data,cod))
                {
                    alert.setContentText("Alteração concluida com sucesso");
                    clickCancelar(event);
                    inicializa();
                    btAlterar.setText("Alterar");
                }
                else if(!erro.equals(""))
                    alert = new Alert(Alert.AlertType.ERROR, erro, ButtonType.NO);
                else
                    alert.setContentText("Erro na alteração do usuário, por favor corrige os dados e tente novamente");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void clickAlterar(ActionEvent event)
    {
        acao = 2;
        altera_campos(false, false, true, true, true);
    }

    @FXML
    private void clickExcluir(ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Deseja remover " + tf_nome.getText() + "?");
        alert.setTitle("Remoção");
        
        ButtonType btn_yes = new ButtonType("Yes");
        ButtonType btn_no = new ButtonType("No");
        
        alert.getButtonTypes().setAll(btn_yes,btn_no);
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == btn_yes)
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            if(cod != -1 && new ctrFuncionarios().remover(cod))
            {
                alert.setContentText("Exclusão concluida com sucesso");
                clickCancelar(event);
                inicializa();
            }
            else
                alert.setContentText("Erro na exclusão do usuário");
            alert.showAndWait();
        }   
    }

    @FXML
    private void clickCancelar(ActionEvent event)
    {
        inicializa();
    }

    @FXML
    private void clickBuscar(ActionEvent event)
    {
        try
        {
            Stage stage = (Stage) paneprincipal.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/buscas/BuscaFuncionario.fxml"));
            paneprincipal.getChildren().clear();
            paneprincipal.getChildren().add(root);

        } catch (Exception er)
        {
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Busca de Funcionários! \nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
        
    }

    @FXML
    private void clickVoltar(ActionEvent event)
    {
    }

    @FXML
    private void evtMaskNum(KeyEvent event)
    {
        MaskFieldUtil.numericField(tf_numero);
    }

    @FXML
    private void evtMaskCep(KeyEvent event)
    {
        MaskFieldUtil.cepField(tf_cep);
    }

    @FXML
    private void clickCep(ActionEvent event)
    {
        tf_rua.setText("");
        String res = consultaCep();
    }

    @FXML
    private void eventMaskTel(KeyEvent event)
    {
        MaskFieldUtil.foneField(tf_telefone);
    }

    @FXML
    private void eventMaskCel(KeyEvent event)
    {
        MaskFieldUtil.foneField(tf_celular);
    }

    public String consultaCep()
    { 
        String urlString = "http://cep.republicavirtual.com.br/web_cep.php";
        urlString += "?cep=" + tf_cep.getText() + "&formato= xml";
        try 
        { 
            URL url = new URL(urlString);
            // cria o objeto httpurlconnection
            HttpURLConnection con=(HttpURLConnection) url.openConnection();
            // seta para modo GET
            con.setRequestProperty("Request-Method", "GET");
            // conecta com a url destino
            con.connect();
            // abre a conexão para leitura
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            // le ate o final dos dados
            StringBuffer dados = new StringBuffer();
            String s = "";
            while (null != (s = br.readLine())) 
            {
                dados.append(s);
                s = s.replace("<", "").replace(">", "").replace("/", "");
                if(s.contains("bairro"))
                {
                    s = s.replace("bairro", "");
                    tf_bairro.setText(s);
                }
                if(s.contains("cidade"))
                {
                    s = s.replace("cidade", "");
                    tf_cidade.setText(s);
                }
                if(s.contains("tipo_logradouro") || s.contains("logradouro"))
                {
                    s = s.replace("tipo_logradouro", "").replace("logradouro", "");
                    tf_rua.setText(tf_rua.getText() + " " + s); 
                }
            } 
            
            br.close();
            return dados.toString();
        } 
        catch (Exception e) 
        {  
            return "Erro: " + e.getMessage();
        }
    }
}
