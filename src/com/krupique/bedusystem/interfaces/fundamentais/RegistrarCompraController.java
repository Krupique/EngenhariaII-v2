/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.interfaces.fundamentais;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.controladoras.CtrFornecedor;
import com.krupique.bedusystem.controladoras.CtrProdutos;
import com.krupique.bedusystem.interfaces.basicas.CadFornecedorController;
import com.krupique.bedusystem.interfaces.basicas.CadProdutosController;
import com.krupique.bedusystem.interfaces.buscas.BuscaFornecedorController;
import com.krupique.bedusystem.interfaces.buscas.BuscaProdutoController;
import com.krupique.bedusystem.utilidades.CorSistema;
import com.krupique.bedusystem.utilidades.MaskFieldUtil;
import com.krupique.bedusystem.utilidades.Objeto;
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
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Henrique K. Secchi
 */
public class RegistrarCompraController implements Initializable {

    @FXML
    private BorderPane paneprincipal;
    @FXML
    private HBox paneBotoes;
    @FXML
    private JFXButton btNovo;
    @FXML
    private JFXButton btSalvar;
    @FXML
    private JFXButton btCancelar;
    @FXML
    private JFXButton btBuscar;
    @FXML
    private JFXButton btVoltar;
    @FXML
    private JFXComboBox<String> cbProdutos;
    @FXML
    private JFXTextField txtQuantidade;
    @FXML
    private JFXTextField txtValorPago;
    @FXML
    private JFXRadioButton rdAVista;
    @FXML
    private JFXRadioButton rdParcelado;
    @FXML
    private JFXTextField txtQtdParcelas;
    @FXML
    private JFXTextField txtJuros;
    @FXML
    private JFXDatePicker txtDateVenc;
    @FXML
    private JFXButton btBuscarProd;
    @FXML
    private JFXButton btAddProd;
    @FXML
    private JFXButton btRemoverProd;
    @FXML
    private TableView<Objeto> tableview;
    @FXML
    private JFXComboBox<String> cbFornec;
    @FXML
    private JFXButton btBuscarFornec;
    @FXML
    private JFXButton btAddFornec;
    @FXML
    private JFXButton btCancelForne;
    @FXML
    private HBox hbox;
    @FXML
    private Label lblProds;

    
    //Variáveis para controlar a funcionalidade do sistema.
    private static int flagVolta = 0;
    private ArrayList<Object[]> listProds;
    private ArrayList<Object[]> listFornec;
    private ArrayList<Object[]> listCompra;
    private ArrayList<Objeto> table;
    private CtrProdutos ctrProds;
    private CtrFornecedor ctrFornecedor;
    
    private Object[] objFornec;
    private Object[] objProd;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        flagVolta = 1;
        CadProdutosController.setFlagVolta(0);
        CadFornecedorController.setFlagVolta(0);
        
        inicializaEstilo();
        inicializaCombobox();
        
        if(BuscaFornecedorController.getFlag() == 1)
        {
            objFornec = BuscaFornecedorController.getRetorno();
            cbFornec.getSelectionModel().select((String)objFornec[2]);
            habilitarBotoes(false, true, true, true, true);
        }
        else if(BuscaProdutoController.getFlag() == 1)
        {
            objProd = BuscaProdutoController.getRetorno();
            cbProdutos.getSelectionModel().select((String)objProd[1]);
            habilitarBotoes(false, true, true, true, true);
        }
        else
        {
            habilitarBotoes(true, false, false, true, true);
            limparCampos();
            habilitarCampos(false);
            setRadioButton(true, false);

            listCompra = new ArrayList<>();
            table = new ArrayList<>();
        }
        
    }
    
    private void setRadioButton(boolean v1, boolean v2){
        rdAVista.setSelected(v1);
        rdParcelado.setSelected(v2);
        
        txtQtdParcelas.setVisible(v2);
        txtJuros.setVisible(v2);
        txtDateVenc.setVisible(v2);
    }
    
    private void inicializaEstilo()
    {
        String cor = CorSistema.getCorHex();
        btNovo.setStyle("-fx-background-color: " + cor);
        btSalvar.setStyle("-fx-background-color: " + cor);
        btCancelar.setStyle("-fx-background-color: " + cor);
        btBuscar.setStyle("-fx-background-color: " + cor);
        btVoltar.setStyle("-fx-background-color: " + cor);
        
        btBuscarProd.setStyle("-fx-background-color: " + cor);
        btAddProd.setStyle("-fx-background-color: " + cor);
        btRemoverProd.setStyle("-fx-background-color: " + cor);
        btBuscarFornec.setStyle("-fx-background-color: " + cor);
        btAddFornec.setStyle("-fx-background-color: " + cor);
        btCancelForne.setStyle("-fx-background-color: " + cor);
        
        rdAVista.setStyle("-jfx-selected-color: " + cor);
        rdParcelado.setStyle("-jfx-selected-color: " + cor);
        
        txtDateVenc.setDefaultColor(CorSistema.hex2Rgb(CorSistema.getCorHex()));
        hbox.setStyle("-fx-background-color: " + cor);
    }
    
    public void limparCampos(){
        cbProdutos.getSelectionModel().select(-1);
        txtQuantidade.setText("");
        txtValorPago.setText("");
        
        txtQtdParcelas.setText("");
        txtJuros.setText("");
        txtDateVenc.setValue(null); //Cuidado

        tableview.getSelectionModel().select(-1);
        
        cbFornec.getSelectionModel().select(-1);
    }
    
    private void habilitarCampos(boolean value){
        value = !value;
        cbProdutos.setDisable(value);
        txtQuantidade.setDisable(value);
        txtValorPago.setDisable(value);
        
        rdAVista.setDisable(value);
        rdParcelado.setDisable(value);
        txtQtdParcelas.setDisable(value);
        txtJuros.setDisable(value);
        txtDateVenc.setDisable(value);
        
        hbox.setDisable(value);
        lblProds.setDisable(value);
        tableview.setDisable(value);
        cbFornec.setDisable(value);
        
        btBuscarProd.setDisable(value);
        btAddProd.setDisable(value);
        btRemoverProd.setDisable(value);
        
        btBuscarFornec.setDisable(value);
        btAddFornec.setDisable(value);
        btCancelForne.setDisable(value);
    }
    
    private void habilitarBotoes(boolean novo, boolean salvar, boolean cancelar, boolean buscar, boolean voltar){
        btNovo.setDisable(!novo);
        btSalvar.setDisable(!salvar);
        btCancelar.setDisable(!cancelar);
        btBuscar.setDisable(!buscar);
        btVoltar.setDisable(!voltar);
    }
    

    private void inicializaCombobox()
    {
        ctrProds = new CtrProdutos();
        ctrFornecedor = new CtrFornecedor();
        listProds = ctrProds.buscar("prod_nome ilike '%%'");
        listFornec = ctrFornecedor.buscar("forn_nome ilike '%%'");
        String aux;
        
        for (int i = 0; i < listProds.size(); i++) {
            aux = (String)listProds.get(i)[1];
            cbProdutos.getItems().add(aux);
        }
        
        for (int i = 0; i < listFornec.size(); i++) {
            aux = (String)listFornec.get(i)[2];
            cbFornec.getItems().add(aux);
        }
    }

    @FXML
    private void evtMaskQuantidade(KeyEvent event) {
        MaskFieldUtil.numericField(txtQuantidade);
    }

    @FXML
    private void evtMaskVlrPago(KeyEvent event) {
        MaskFieldUtil.monetaryField(txtValorPago);
    }

    @FXML
    private void evtMaskQtdParcelas(KeyEvent event) {
        MaskFieldUtil.numericField(txtQtdParcelas);
    }

    @FXML
    private void evtMaskJuros(KeyEvent event) {
        MaskFieldUtil.monetaryField(txtJuros);
    }
    
    @FXML
    private void evtRdAvista(ActionEvent event) {
        setRadioButton(true, false);
    }

    @FXML
    private void evtRdParcelado(ActionEvent event) {
        setRadioButton(false, true);
    }

    @FXML
    private void evtBuscarProd(ActionEvent event) {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/buscas/BuscaProduto.fxml"));

            paneprincipal.getChildren().clear();
            paneprincipal.getChildren().add(root);
        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Busca de Produtos! " + er.getLocalizedMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }
    
    @FXML
    private void evtCbProds(ActionEvent event) {
        if(cbProdutos.getSelectionModel().getSelectedIndex() != -1)
            txtValorPago.setText("" + listProds.get(cbProdutos.getSelectionModel().getSelectedIndex())[2]);
    }

    @FXML
    private void evtAddProd(ActionEvent event) {
    }

    @FXML
    private void evtRemovProd(ActionEvent event) {
    }

    @FXML
    private void evtBuscarFornec(ActionEvent event) {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/buscas/BuscaFornecedor.fxml"));

            paneprincipal.getChildren().clear();
            paneprincipal.getChildren().add(root);
        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Busca de Fornecedores! " + er.getLocalizedMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void evtAddFornec(ActionEvent event) {
    }

    @FXML
    private void evtCancelFornec(ActionEvent event) {
    }
    
    @FXML
    private void evtNovo(ActionEvent event) {
        habilitarBotoes(false, true, true, true, true);
        habilitarCampos(true);
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        habilitarCampos(false);
        limparCampos();
        habilitarBotoes(true, false, false, true, true);
        setRadioButton(true, false);
    }
    
    @FXML
    private void evtSalvar(ActionEvent event) {
    }


    @FXML
    private void evtBuscar(ActionEvent event) {
    }

    @FXML
    private void evtVoltar(ActionEvent event) {
    }
    
    public static int getFlagVolta() {
        return flagVolta;
    }

    public static void setFlagVolta(int flagVolta) {
        RegistrarCompraController.flagVolta = flagVolta;
    }

    
}
