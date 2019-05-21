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
import com.krupique.bedusystem.controladoras.CtrCompra;
import com.krupique.bedusystem.controladoras.CtrFornecedor;
import com.krupique.bedusystem.controladoras.CtrProdutos;
import com.krupique.bedusystem.interfaces.basicas.CadFornecedorController;
import com.krupique.bedusystem.interfaces.basicas.CadProdutosController;
import com.krupique.bedusystem.interfaces.buscas.BuscaComprasController;
import com.krupique.bedusystem.interfaces.buscas.BuscaFornecedorController;
import com.krupique.bedusystem.interfaces.buscas.BuscaProdutoController;
import com.krupique.bedusystem.utilidades.CorSistema;
import com.krupique.bedusystem.utilidades.MaskFieldUtil;
import com.krupique.bedusystem.utilidades.Objeto;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
    /*@FXML
    private JFXButton btAddFornec;
    @FXML
    private JFXButton btCancelForne;*/
    @FXML
    private HBox hbox;
    @FXML
    private Label lblProds;
    @FXML
    private JFXRadioButton rdUnidade;
    @FXML
    private JFXRadioButton rdTotal;
    @FXML
    private TableColumn<String, String> colProduto;
    @FXML
    private TableColumn<String, String> colPreco;
    @FXML
    private TableColumn<String, String> colQuantidade;
    @FXML
    private TableColumn<String, String> colValorTotal;
    @FXML
    private JFXButton btExcluir;

    
    //Variáveis para controlar a funcionalidade do sistema.
    private static int flagVolta = 0;
    private ArrayList<Object[]> listProds;
    private ArrayList<Object[]> listProdsCompra;
    private ArrayList<Object[]> listFornec;
    private ArrayList<Object[]> listCompra;
    private ArrayList<Objeto> table;
    private CtrProdutos ctrProds;
    private CtrFornecedor ctrFornecedor;
    
    private Object[] objFornec;
    private Object[] objProd;
    private double valorTotal;
    private Object[] retorno;
    private int flagAlter;
    @FXML
    private JFXButton btAlterar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        flagVolta = 1;
        CadProdutosController.setFlagVolta(0);
        CadFornecedorController.setFlagVolta(0);
        
        inicializaEstilo();
        inicializaCombobox();
        iniciarColunas();
        
        if(BuscaFornecedorController.getFlag() == 1) //Vindo da busca de fornecedores
        {
            objFornec = BuscaFornecedorController.getRetorno();
            cbFornec.getSelectionModel().select((String)objFornec[2]);
            habilitarBotoes(false, true, false, false, true, true, true);
        }
        else if(BuscaProdutoController.getFlag() == 1) //Vindo da busca de produto
        {
            objProd = BuscaProdutoController.getRetorno();
            cbProdutos.getSelectionModel().select((String)objProd[1]);
            habilitarBotoes(false, true, false, false, true, true, true);
        }
        else if(BuscaComprasController.getFlag() == 1) //Vindo da busca de compras
        {
            limparCampos();
            habilitarBotoes(false, false, true, true, true, true, true);
            retorno = BuscaComprasController.getRetorno();
            setarCampos(retorno);
        }
        else //Inicio
        {
            habilitarBotoes(true, false, false, false, false, true, true);
            limparCampos();
            habilitarCampos(false);
            setRadioButton(true, false);
            setRdPreco(true, false);

            listCompra = new ArrayList<>();
            listProdsCompra = new ArrayList<>();
            table = new ArrayList<>();
        }
        
    }
    
    private void setarCampos(Object[] obj)
    {
        CtrCompra ctrcompra = new CtrCompra();
        ArrayList<Object[]>  temp = ctrcompra.buscar_itens_compra((int)obj[0]);
        Object[] aux_obj = new Object[5];
        listProdsCompra = new ArrayList<>();
        
        //Parte para adicionar no ArrayList principal
        for (int i = 0; i < temp.size(); i++) {
            aux_obj = new Object[5];
            aux_obj[0] = temp.get(i)[0];
            aux_obj[1] = temp.get(i)[1];
            aux_obj[2] = temp.get(i)[2];
            aux_obj[3] = temp.get(i)[3];
            aux_obj[4] = (double)((int)temp.get(i)[3] * (double)temp.get(i)[2]);
            
            listProdsCompra.add(aux_obj);
        }
        
        //Parte para exibir no ArrayList.
        ArrayList<Objeto> obs_list = new ArrayList<>();
        Objeto obs_obj;    
        valorTotal = 0;
        for (int i = 0; i < listProdsCompra.size(); i++) {
                valorTotal += (double)listProdsCompra.get(i)[4];
                obs_obj = new Objeto((String)listProdsCompra.get(i)[1], 
                        String.valueOf(listProdsCompra.get(i)[2]), 
                        String.valueOf(listProdsCompra.get(i)[3]), 
                        String.valueOf(listProdsCompra.get(i)[4]));
                obs_list.add(obs_obj);
            }
        
        tableview.setItems(FXCollections.observableArrayList(obs_list)); //Adicione observable list na tabela.
    }
    
    private void iniciarColunas() {
        
        colProduto.setCellValueFactory(new PropertyValueFactory<>("param1"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("param2"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("param3"));
        colValorTotal.setCellValueFactory(new PropertyValueFactory<>("param4"));
    }
    
    private void setRadioButton(boolean v1, boolean v2){
        rdAVista.setSelected(v1);
        rdParcelado.setSelected(v2);
        
        txtQtdParcelas.setVisible(v2);
        txtJuros.setVisible(v2);
        txtDateVenc.setVisible(v2);
    }
    
    private void setRdPreco(boolean v1, boolean v2){
        rdUnidade.setSelected(v1);
        rdTotal.setSelected(v2);
    }
    
    private void inicializaEstilo(){
        String cor = CorSistema.getCorHex();
        btNovo.setStyle("-fx-background-color: " + cor);
        btSalvar.setStyle("-fx-background-color: " + cor);
        btAlterar.setStyle("-fx-background-color: " + cor);
        btExcluir.setStyle("-fx-background-color: " + cor);
        btCancelar.setStyle("-fx-background-color: " + cor);
        btBuscar.setStyle("-fx-background-color: " + cor);
        btVoltar.setStyle("-fx-background-color: " + cor);
        
        btBuscarProd.setStyle("-fx-background-color: " + cor);
        btAddProd.setStyle("-fx-background-color: " + cor);
        btRemoverProd.setStyle("-fx-background-color: " + cor);
        btBuscarFornec.setStyle("-fx-background-color: " + cor);
        rdUnidade.setStyle("-jfx-selected-color: "+ cor);
        rdTotal.setStyle("-jfx-selected-color: "+ cor);
        //btAddFornec.setStyle("-fx-background-color: " + cor);
        //btCancelForne.setStyle("-fx-background-color: " + cor);
        
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

        cbProdutos.getSelectionModel().select(-1);
        cbFornec.getSelectionModel().select(-1);
        
        listProdsCompra = new ArrayList<>();
        ArrayList<Objeto> aux = new ArrayList<>();
        tableview.setItems(FXCollections.observableArrayList(aux)); //Adicione observable list na tabela.
    }
    
    private void habilitarCampos(boolean value){
        value = !value;
        cbProdutos.setDisable(value);
        txtQuantidade.setDisable(value);
        txtValorPago.setDisable(value);
        rdUnidade.setDisable(value);
        rdTotal.setDisable(value);
        
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
        //btAddFornec.setDisable(value);
        //btCancelForne.setDisable(value);
    }
    
    private void habilitarBotoes(boolean novo, boolean salvar, boolean alterar, boolean excluir, boolean cancelar, boolean buscar, boolean voltar){
        btNovo.setDisable(!novo);
        btSalvar.setDisable(!salvar);
        btAlterar.setDisable(!alterar);
        btExcluir.setDisable(!excluir);
        btCancelar.setDisable(!cancelar);
        btBuscar.setDisable(!buscar);
        btVoltar.setDisable(!voltar);
    }

    private void inicializaCombobox(){
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
        {
            String aux = String.valueOf((double)listProds.get(cbProdutos.getSelectionModel().getSelectedIndex())[2]);
            aux = aux.replace(".", ",");
            txtValorPago.setText(aux);
        }
    }

    @FXML
    private void evtRdUnidade(ActionEvent event) {
        setRdPreco(true, false);
    }

    @FXML
    private void evtRdTotal(ActionEvent event) {
        setRdPreco(false, true);
    }
    
    @FXML
    private void evtAddProd(ActionEvent event) {
        try{
            
            ArrayList<Objeto> obsList = new ArrayList<>();
            Objeto obj;
            Object[] prod = new Object[5]; //cod, nome, preco, quantidade, precoTotal
            int index = cbProdutos.getSelectionModel().getSelectedIndex();
            prod[0] = (int)listProds.get(index)[0]; //Cod produto
            prod[1] = (String)listProds.get(index)[1]; //Nome produto
            prod[3] = Integer.parseInt(txtQuantidade.getText()); //Quantidade de produtos
            if(rdUnidade.isSelected())
            {
                prod[2] = Double.parseDouble(txtValorPago.getText().replace(".", "").replace(",", ".")); //Valor pago
                prod[4] = (double)((double)prod[2] * (int)prod[3]); //Valor total
            }
            else
            {
                prod[4] = Double.parseDouble(txtValorPago.getText().replace(",", ".")); //Valor total
                prod[2] = (double)((double)prod[4] / (int)prod[3]); //Valor pago
            }
            
            
            listProdsCompra.add(prod);
            valorTotal = 0;
            
            for (int i = 0; i < listProdsCompra.size(); i++) {
                valorTotal += (double)listProdsCompra.get(i)[4];
                obj = new Objeto((String)listProdsCompra.get(i)[1], 
                        String.valueOf(listProdsCompra.get(i)[2]), 
                        String.valueOf(listProdsCompra.get(i)[3]), 
                        String.valueOf(listProdsCompra.get(i)[4]));
                obsList.add(obj);
            }
            
            tableview.setItems(FXCollections.observableArrayList(obsList)); //Adicione observable list na tabela.
            
        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao inserir dados na tabela!\nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void evtRemovProd(ActionEvent event) {
        int index = tableview.getSelectionModel().getSelectedIndex();
        ArrayList<Objeto> obsList = new ArrayList<>();
        Objeto obj;
        if(index != -1)
        {
           listProdsCompra.remove(index);
           
           for (int i = 0; i < listProdsCompra.size(); i++) {
                valorTotal += (double)listProdsCompra.get(i)[4];
                obj = new Objeto((String)listProdsCompra.get(i)[1], 
                        String.valueOf(listProdsCompra.get(i)[2]), 
                        String.valueOf(listProdsCompra.get(i)[3]), 
                        String.valueOf(listProdsCompra.get(i)[4]));
                obsList.add(obj);
            }
           
           tableview.setItems(FXCollections.observableArrayList(obsList)); //Adicione observable list na tabela.
        }
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

    /*
    @FXML
    private void evtAddFornec(ActionEvent event) {
    }

    @FXML
    private void evtCancelFornec(ActionEvent event) {
    }*/
    
    @FXML
    private void evtNovo(ActionEvent event) {
        habilitarBotoes(false, true, false, false, true, true, true);
        habilitarCampos(true);
        cbProdutos.requestFocus();
        flagAlter = 0;
    }
    
    @FXML
    private void evtAlterar(ActionEvent event) {
        habilitarBotoes(false, true, true, true, true, true, true);
        habilitarCampos(true);
        cbProdutos.requestFocus();
        flagAlter = 1;
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        habilitarCampos(false);
        limparCampos();
        habilitarBotoes(true, false, false, false, false, true, true);
        setRadioButton(true, false);
    }
    
    @FXML
    private void evtSalvar(ActionEvent event) {
        Object[] objCompra = new Object[7];
        Object[] objParcela = new Object[7];
        try{
            //if ValidarErros() -- faz tudo pra baixo. //Erros: campos nulos, datas e valores negativos, etc.
            
            objCompra[0] = (int)-1; //Cod compra
            objCompra[1] = (int)listFornec.get(cbFornec.getSelectionModel().getSelectedIndex())[0]; //Cod fornecedor
            objCompra[2] = (int)1; //BUSCAR DA FUNÇÃO DO ZACARIAS; //Cod funcionario
            
            if(rdAVista.isSelected()){
                objCompra[3] = 1;
                objCompra[4] = (double)0;
            }
            else{
                objCompra[3] = Integer.parseInt(txtQtdParcelas.getText()); //Quantidade de parcelas.
                objCompra[4] = Double.parseDouble(txtJuros.getText().replace(".", "").replace(",", ".")); //Valor juros.
            }
            objCompra[5] = (double)(valorTotal * ((double)objCompra[4]/100 + 1)); //Valor total
            objCompra[6] = (LocalDate)LocalDate.now(); //Data da compra
            
            objParcela[0] = (int)-1;//Cod parc
            if(rdAVista.isSelected()){
                objParcela[1] = 1; //Status paga
                objParcela[2] = (LocalDate)LocalDate.now(); //Data do primeiro vencimento
            }
            else{
                objParcela[1] = (int)0; //Status nao paga.
                objParcela[2] = (LocalDate)txtDateVenc.getValue(); //Data do primeiro vencimento
            }
            objParcela[3] = (int)1; //Número da parcela.
            objParcela[4] = (LocalDate)null;//Data do pagamento.
            objParcela[5] = (double)-1;//Valor pago na parcela.
            objParcela[6] = (int) -1;//Cod compra
            
            CtrCompra ctrCompra = new CtrCompra();
            if(flagAlter == 1)
            {
                //ctrCompra.excluir();
            }
            if(ctrCompra.salvar(objCompra, objParcela, listProdsCompra))
            {
                limparCampos();
                habilitarBotoes(true, false, false, false, false, true, true);
                habilitarCampos(false);
                tableview = new TableView<>();
                listProdsCompra = new ArrayList<>();
                
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Compra realizada com sucesso!", ButtonType.OK);
                a.showAndWait();
            }
            
            
        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao registrar compra!\nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }


    @FXML
    private void evtBuscar(ActionEvent event) {
        try
        {
            Stage stage = (Stage)paneprincipal.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/buscas/BuscaCompras.fxml"));
            paneprincipal.getChildren().clear();
            paneprincipal.getChildren().add(root);

        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Busca de Contas à Pagar! \nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
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
