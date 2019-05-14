/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.interfaces.basicas;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.controladoras.CtrFornecedor;
import com.krupique.bedusystem.interfaces.buscas.BuscaFornecedorController;
import com.krupique.bedusystem.interfaces.fundamentais.RegistrarCompraController;
import com.krupique.bedusystem.utilidades.CorSistema;
import com.krupique.bedusystem.utilidades.MaskFieldUtil;
import com.krupique.bedusystem.utilidades.ValidadorCPFeCNPJ;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Henrique K. Secchi
 */
public class CadFornecedorController implements Initializable {

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
    private AnchorPane paneInf;
    @FXML
    private JFXTextField txtCNPJ;
    @FXML
    private JFXTextField txtNome;
    @FXML
    private JFXTextField txtCelular;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private Label lblInfBasicas;
    @FXML
    private AnchorPane paneEnd;
    @FXML
    private JFXTextField txtRua;
    @FXML
    private JFXTextField txtBairro;
    @FXML
    private JFXTextField txtNumero;
    @FXML
    private JFXTextField txtCidade;
    @FXML
    private JFXTextField txtCep;
    @FXML
    private JFXButton btBuscarCep;
    @FXML
    private Label lblInfEnd;
    @FXML
    private Label lblObs;

    
    private static int flagVolta = 0;
    private CtrFornecedor ctrforn;
    private Object[] obj;
    private int flagAlter;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializaEstilo();
        
        flagVolta = 1;
        RegistrarCompraController.setFlagVolta(0);

        
        habilitarBotoes(true, false, false, false, false, true, true, false); //Oficial
        //habilitarBotoes(true, true, true, true, true, true); //Teste
        limparCampos();
        habilitarCampos(false);
        flagAlter = 0;
        if(BuscaFornecedorController.getFlag() == 1)
        {
            obj = BuscaFornecedorController.getRetorno();
            setarCampos(obj);
            habilitarBotoes(false, false, true, true, true, true, true, false);
        }
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
        btBuscarCep.setStyle("-fx-background-color: " + cor);
    }
    
    private void habilitarBotoes(boolean novo, boolean salvar, boolean alterar, boolean excluir, boolean cancelar, boolean buscar, boolean voltar, boolean buscarCEP)
    {
        btNovo.setDisable(!novo);
        btSalvar.setDisable(!salvar);
        btAlterar.setDisable(!alterar);
        btExcluir.setDisable(!excluir);
        btCancelar.setDisable(!cancelar);
        btBuscar.setDisable(!buscar);
        btVoltar.setDisable(!voltar);
        btBuscarCep.setDisable(!buscarCEP);
    }
    
    private void limparCampos()
    {
        txtCNPJ.setText("");
        txtNome.setText("");
        txtCelular.setText("");
        txtEmail.setText("");
        
        txtRua.setText("");
        txtBairro.setText("");
        txtNumero.setText("");
        txtCidade.setText("");
        txtCep.setText("");
    }
    
    //################################# PARTE DE FUNCIONALIDADES DA TELA #################################//

    private void habilitarCampos(boolean value)
    {
        value = !value;
        lblInfBasicas.setDisable(value);
        lblInfEnd.setDisable(value);
        lblObs.setDisable(value);
        
        txtCNPJ.setDisable(value);
        txtNome.setDisable(value);
        txtCelular.setDisable(value);
        txtEmail.setDisable(value);
        
        txtRua.setDisable(value);
        txtBairro.setDisable(value);
        txtNumero.setDisable(value);
        txtCidade.setDisable(value);
        txtCep.setDisable(value);
        
        btBuscarCep.setDisable(value);
    }
    
    @FXML
    private void evtNovo(ActionEvent event) {
        flagAlter = 0;
        habilitarCampos(true);
        habilitarBotoes(false, true, false, false, true, true, true, true);
    }

    @FXML
    private void evtSalvar(ActionEvent event) {
        System.out.println("Confirm");
            
        Object[] obj = new Object[10];
        try
        {
            obj[0] = flagAlter == 0 ? -1 : this.obj[0];
            obj[1] = txtCNPJ.getText();//.replace(".", "").replace("/", "").replace("-", "");
            obj[2] = txtNome.getText();
            obj[3] = txtCelular.getText();//.replace("(", "").replace(")", "").replace("-", "");
            obj[4] = txtEmail.getText();

            obj[5] = txtRua.getText();
            obj[6] = txtBairro.getText();
            if(!txtNumero.getText().equals(""))
                obj[7] = Integer.parseInt(txtNumero.getText());
            obj[8] = txtCidade.getText();
            obj[9] = txtCep.getText();//.replace("-", "");

            if(validarErros())
            {
                //Continua para gravar no banco.
                preencherNulos(obj);

                ctrforn = new CtrFornecedor();

                Alert a;

                int saida = ctrforn.salvar(obj, flagAlter);
                if(saida == 1)
                {
                    String msg;
                    if(flagAlter == 1)
                        msg = "Fornecedor alterado com sucesso!";
                    else
                        msg = "Fornecedor cadastrado com sucesso!";

                    a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
                    limparCampos();
                    habilitarCampos(false);
                    habilitarBotoes(true, false, false, false, false, true, true, false);
                }
                else if(saida == 0)
                    a = new Alert(Alert.AlertType.ERROR, "Erro ao gravar dados no banco!", ButtonType.OK);
                else
                    a = new Alert(Alert.AlertType.ERROR, "Erro ao gravar dados no banco! Este CPF/CNPJ já foi cadastrado!", ButtonType.OK);

                a.showAndWait();
            }
            //else - tem erros

        }catch(Exception er)
        {
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao tentar gravar os dados no banco!\nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void evtAlterar(ActionEvent event) {
        flagAlter = 1;
        habilitarCampos(true);
        habilitarBotoes(false, true, false, true, true, true, true, true);
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        System.out.println("Excluir");
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja excluir este fornecedor?", ButtonType.YES, ButtonType.NO);
        a.showAndWait();

        if(a.getResult() == ButtonType.YES)
        {
            ctrforn = new CtrFornecedor();
            if(ctrforn.excluir((int)obj[0]))
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Fornecedor excluído com sucesso!", ButtonType.OK);
                alert.showAndWait();

                limparCampos();
                habilitarCampos(false);
                habilitarBotoes(true, false, false, false, false, true, true, false);
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao tentar excluir este fornecedor!", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        System.out.println("Cancel");
        habilitarCampos(false);
        limparCampos();
        habilitarBotoes(true, false, false, false, false, true, true, false);
    }

    @FXML
    private void evtBuscar(ActionEvent event) {
        try
        {
            Stage stage = (Stage)paneprincipal.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/buscas/BuscaFornecedor.fxml"));
            paneprincipal.getChildren().clear();
            paneprincipal.getChildren().add(root);

        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Busca de Fornecedores! \nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void evtVoltar(ActionEvent event) {
    }

    @FXML
    private void evtMaskCPF(KeyEvent event) {
        MaskFieldUtil.cpfCnpjField(txtCNPJ);
    }

    @FXML
    private void evtMaskFone(KeyEvent event) {
        MaskFieldUtil.foneField(txtCelular);
    }

    @FXML
    private void evtMaskNum(KeyEvent event) {
        MaskFieldUtil.numericField(txtNumero);
    }

    @FXML
    private void evtMaskCep(KeyEvent event) {
        MaskFieldUtil.cepField(txtCep);
    }

    @FXML
    private void evtBuscarCep(ActionEvent event) {
        consultaCep();
    }
    
    private boolean validarErros() {
        boolean flag = true;
        int[] erro = new int[]{0,0,0,0,0};
        
        int aux = ValidadorCPFeCNPJ.isValidCPForCNPJ(txtCNPJ.getText());
        if(aux == -1) //CPF ou CNPJ inválido.
        {
            erro[0] = 1;
            flag = false;
        }

        if(txtCNPJ.getText().equals("")) //Não foi digitado nenhum cpf ou cnpj.
        {
            erro[1] = 1;
            flag = false;
        }

        if(txtNome.getText().equals("")) //Não foi digitado nenhum nome.
        {
            erro[2] = 1;
            flag = false;
        }

        String auxstr = txtCelular.getText();
        if(auxstr.equals("") || auxstr.length() < 13) //Validar se o celular foi digitado corretamente.
        {
            erro[3] = 1;
            flag = false;
        }
        
        if(!txtCep.getText().equals("") && txtCep.getText().length() != 9)
        {
            erro[4] = 1;
            flag = false;
        }
        
        if(!flag)
        {
            String str = "";
            if(erro[1] == 1)
                str += "Não foi digitado o CPF/CNPJ.\n";
            else if(erro[0] == 1)
                str += "CPF/CNPJ inválido.\n";
            if(erro[2] == 1)
                str += "Não foi digitado o Nome do fornecedor.\n";
            if(erro[3] == 1)
                str += "Não foi digitado o número de celular ou número inválido.\n";
            if(erro[4] == 1)
                str += "CEP inválido.";
            
            Alert a = new Alert(Alert.AlertType.ERROR, str, ButtonType.OK);
            a.showAndWait();
            
            if(erro[0] == 1 || erro[1] == 1)
               txtCNPJ.requestFocus();
            else if(erro[2] == 1)
                txtNome.requestFocus();
            else if(erro[3] == 1)
                txtCelular.requestFocus();
            else if(erro[4] == 1)
                txtCep.requestFocus();
        }
        return flag;
    }
     
    private void preencherNulos(Object[] obj) {
        if(obj[7] == null)
            obj[7] = -1;
        
        for (int i = 0; i < obj.length; i++)
            if(obj[i] == null)
                obj[i] = "";
    }
    
    private void setarCampos(Object[] obj) {
        txtCNPJ.setText((String)obj[1]);
        txtNome.setText((String)obj[2]);
        txtCelular.setText((String)obj[3]);
        txtEmail.setText((String)obj[4]);
        txtRua.setText((String)obj[5]);
        txtBairro.setText((String)obj[6]);
        if((int)obj[7] != -1)
            txtNumero.setText("" + (int)obj[7]);
        txtCidade.setText((String)obj[8]);
        txtCep.setText((String)obj[9]);
    }
    
    public String consultaCep()
    { 
        String urlString = "http://cep.republicavirtual.com.br/web_cep.php";
        urlString += "?cep=" + txtCep.getText() + "&formato= xml";
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
            limparEnd();
            while (null != (s = br.readLine())) 
            {
                dados.append(s);
                s = s.replace("<", "").replace(">", "").replace("/", "");
                if(s.contains("bairro"))
                {
                    s = s.replace("bairro", "");
                    txtBairro.setText(s);
                }
                if(s.contains("cidade"))
                {
                    s = s.replace("cidade", "");
                    txtCidade.setText(s);
                }
                if(s.contains("tipo_logradouro") || s.contains("logradouro"))
                {
                    s = s.replace("tipo_logradouro", "").replace("logradouro", "");
                    txtRua.setText(txtRua.getText() + " " + s);
                }
            } 
            
            if(txtRua.getText().equals("  "))
                txtRua.setText("");
            
            br.close();
            return dados.toString();
        } 
        catch (Exception e) 
        {  
            return "Erro: " + e.getMessage();
        }
    }
    
    private void limparEnd() {
        txtRua.setText("");
        txtBairro.setText("");
        txtRua.setText("");
    }

    public static int getFlagVolta() {
        return flagVolta;
    }

    public static void setFlagVolta(int flagVolta) {
        CadFornecedorController.flagVolta = flagVolta;
    }
    
}
