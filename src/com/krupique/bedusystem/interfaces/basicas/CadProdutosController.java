/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.interfaces.basicas;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.controladoras.CtrClassificacao;
import com.krupique.bedusystem.controladoras.CtrProdutos;
import com.krupique.bedusystem.interfaces.buscas.BuscaProdutoController;
import com.krupique.bedusystem.interfaces.fundamentais.RegistrarCompraController;
import com.krupique.bedusystem.utilidades.CorSistema;
import com.krupique.bedusystem.utilidades.MaskFieldUtil;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
public class CadProdutosController implements Initializable {

    @FXML
    private BorderPane paneprincipal;
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
    private AnchorPane paneInf;
    @FXML
    private JFXTextField txtCod;
    @FXML
    private JFXTextField txtNome;
    @FXML
    private JFXTextField txtPreco;
    @FXML
    private JFXTextField txtQtd;
    @FXML
    private JFXComboBox<String> cbClass;
    @FXML
    private ImageView imageview;
    @FXML
    private Label lblObs;
    @FXML
    private Label lblInf;
    
    //Variáveis para controlar a funcionalidade do sistema.
    private static int flagVolta = 0;
    private CtrProdutos ctrprod;
    private CtrClassificacao ctrclassi;
    private Object[] obj;
    private int flagAlter;
    private ArrayList<Object[]> listClassi;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializaEstilo();
        
        flagVolta = 1;
        RegistrarCompraController.setFlagVolta(0);
        
        habilitarBotoes(true, false, false, false, false, true, true);
        //habilitarBotoes(true, true, true, true, true, true); //Teste
        limparCampos();
        habilitarCampos(false);
        
        flagAlter = 0;
        ctrclassi = new CtrClassificacao();
        listClassi = ctrclassi.buscar();
        povoarCombobox();
        
        
        if(BuscaProdutoController.getFlag() == 1)
        {
            obj = BuscaProdutoController.getRetorno();
            setarCampos(obj);
            habilitarBotoes(false, false, true, true, true, true, true);
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
        
        Image img = new Image("/com/krupique/bedusystem/utilidades/recursos/products_extra.png");
        imageview.setImage(img);
    }
    
    //################################# PARTE DE FUNCIONALIDADES DA TELA #################################//
    private void setarCampos(Object[] obj)
    {
        txtCod.setText("" + obj[0]);
        txtNome.setText((String)obj[1]);
        
        String preco = String.valueOf((double)obj[2]);
        preco = preco.replace(".", ",");
        
        txtPreco.setText(preco);
        txtQtd.setText("" + obj[3]);
        for (int i = 0; i < listClassi.size(); i++) {
            if((int)listClassi.get(i)[0] == (int)obj[4])
                cbClass.getSelectionModel().select(i);
        }
    }
    
    private void povoarCombobox()
    {
        String aux;
        for (int i = 0; i < listClassi.size(); i++) {
            aux = (String)listClassi.get(i)[1];
            cbClass.getItems().add(aux);
        }
    }
    
    private void habilitarBotoes(boolean novo, boolean salvar, boolean alterar, boolean excluir, boolean cancelar, boolean buscar, boolean voltar)
    {
        btNovo.setDisable(!novo);
        btSalvar.setDisable(!salvar);
        btAlterar.setDisable(!alterar);
        btExcluir.setDisable(!excluir);
        btCancelar.setDisable(!cancelar);
        btBuscar.setDisable(!buscar);
        btVoltar.setDisable(!voltar);
    }
    
    private void habilitarCampos(boolean value)
    {
        value = !value;
        lblInf.setDisable(value);
        lblObs.setDisable(value);
        
        txtCod.setDisable(value);
        txtNome.setDisable(value);
        txtPreco.setDisable(value);
        txtQtd.setDisable(value);
        
        cbClass.setDisable(value);
        
        imageview.setDisable(value);
    }
    
    private void limparCampos()
    {
        txtCod.setText("");
        txtNome.setText("");
        txtPreco.setText("");
        txtQtd.setText("");
        cbClass.getSelectionModel().select(-1);
    }
    
    private boolean validarErros() {
        boolean flag = true;
        int[] erro = new int[]{0,0,0,0,0};
        
        if(txtNome.getText().equals(""))
        {
            erro[0] = 1;
            flag = false;
        }
        if(txtPreco.getText().equals(""))
        {
            erro[1] = 1;
            flag = false;
        }
        if(!txtQtd.getText().equals(""))
        {
            try
            {
                int qtd = Integer.parseInt(txtQtd.getText());
                if(qtd < 0)
                {
                    erro[3] = 1;
                    flag = false;
                }
            }catch(Exception er){
                erro[2] = 1;
                flag = false;
            }
        }
        
        if(cbClass.getSelectionModel().getSelectedIndex() == -1)
        {
            erro[4] = 1;
            flag = false;
        }
        
        if(!flag)
        {
            String str = "";
            if(erro[0] == 1)
                str += "Não foi digitado o nome do Produto!\n";
            if(erro[1] == 1)
                str += "Não foi digitado o preço do Produto!\n";
            if(erro[2] == 1)
                str += "Quantidade digitada inválida! Certifique-se de digitar um valor inteiro positivo!\n";
            if(erro[3] == 1)
                str += "Quantidade digitada inválida! Certifique-se de digitar um valor positivo!\n";
            if(erro[4] == 1)
                str += "Não foi selecionado a classificação do Produto";
            
            Alert a = new Alert(Alert.AlertType.ERROR, str, ButtonType.OK);
            a.showAndWait();
            
            if(erro[0] == 1)
                txtNome.requestFocus();
            else if(erro[1] == 1)
                txtPreco.requestFocus();
            else if(erro[2] == 1 || erro[3] == 1)
                txtQtd.requestFocus();
            else if(erro[4] == 1)
                cbClass.requestFocus();
        }
        return flag;
    }
    
    @FXML
    private void evtNovo(ActionEvent event) {
         txtCod.setText("---");
        flagAlter = 0;
        habilitarCampos(true);
        habilitarBotoes(false, true, false, false, true, true, true);
        txtNome.requestFocus();
    }

    @FXML
    private void evtSalvar(ActionEvent event) {
        Object[] obj = new Object[6];
            
            try
            {
                obj[0] = flagAlter == 0 ? -1 : this.obj[0];
                obj[1] = txtNome.getText();
                obj[2] = txtPreco.getText().replace(".", "").replace(",", ".");
                obj[3] = txtQtd.getText();
                if(cbClass.getSelectionModel().getSelectedIndex() != -1)
                {
                    obj[4] = listClassi.get(cbClass.getSelectionModel().getSelectedIndex())[0]; //Pega o codigo do item escolhido no combobox
                    obj[5] = listClassi.get(cbClass.getSelectionModel().getSelectedIndex())[1]; //Pega o codigo do item escolhido no combobox
                }
                
                if(validarErros())
                {
                    //preencherNulos(obj);
                    obj[2] = Double.parseDouble((String)obj[2]);
                    obj[3] = txtQtd.getText().equals("") ? 0 : Integer.parseInt((String)obj[3]);
                    
                    ctrprod = new CtrProdutos();
                    System.out.println("Chegou aqui");
                    if(ctrprod.salvar(obj, flagAlter))
                    {
                        String msg;
                        if(flagAlter == 1)
                            msg = "Produto alterado com sucesso!";
                        else
                            msg = "Produto cadastrado com sucesso!";
                        
                        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
                        a.showAndWait();
                        limparCampos();
                        habilitarCampos(false);
                        habilitarBotoes(true, false, false, false, false, true, true);
                    }
                }
                
            }catch(Exception er){
                Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao tentar gravar os dados no banco!\nErro: " + er.getMessage(), ButtonType.OK);
                a.showAndWait();
            }
    }

    @FXML
    private void evtAlterar(ActionEvent event) {
        habilitarCampos(true);
        txtCod.setDisable(true);
        txtQtd.setDisable(true);
        habilitarBotoes(false, true, false, true, true, true, true);
        flagAlter = 1;
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja excluir este produto?", ButtonType.YES, ButtonType.NO);
            a.showAndWait();
            
            if(a.getResult() == ButtonType.YES)
            {
                ctrprod = new CtrProdutos();
                if(ctrprod.excluir((int)obj[0]))
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Produto excluído com sucesso!", ButtonType.OK);
                    alert.showAndWait();
                    
                    limparCampos();
                    habilitarCampos(false);
                    habilitarBotoes(true, false, false, false, false, true, true);
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao tentar excluir este produto!", ButtonType.OK);
                    alert.showAndWait();
                }
            }
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        habilitarCampos(false);
        limparCampos();
        habilitarBotoes(true, false, false, false, false, true, true);
    }

    @FXML
    private void evtBuscar(ActionEvent event) {
        try
        {
            Stage stage = (Stage)paneprincipal.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/buscas/BuscaProduto.fxml"));
            paneprincipal.getChildren().clear();
            paneprincipal.getChildren().add(root);

        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Busca de Produtos! \nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void evtVoltar(ActionEvent event) {
    }

    @FXML
    private void evtMaskPreco(KeyEvent event) {
        MaskFieldUtil.monetaryField(txtPreco);
    }

    @FXML
    private void evtMaskQtd(KeyEvent event) {
        MaskFieldUtil.numericField(txtQtd);
    }
    
    public static int getFlagVolta() {
        return flagVolta;
    }

    public static void setFlagVolta(int flagVolta) {
        CadProdutosController.flagVolta = flagVolta;
    }
    
}
