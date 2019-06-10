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
import com.krupique.bedusystem.controladoras.CtrPagamento;
import com.krupique.bedusystem.controladoras.CtrProdutos;
import com.krupique.bedusystem.interfaces.basicas.CadFornecedorController;
import com.krupique.bedusystem.interfaces.basicas.CadProdutosController;
import com.krupique.bedusystem.interfaces.buscas.BuscaComprasController;
import com.krupique.bedusystem.interfaces.buscas.BuscaFornecedorController;
import com.krupique.bedusystem.interfaces.buscas.BuscaProdutoController;
import com.krupique.bedusystem.utilidades.ComboBoxAutoComplete;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
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
    private JFXButton btAddProd;
    @FXML
    private JFXButton btRemoverProd;
    @FXML
    private TableView<Objeto> tableview;
    @FXML
    private JFXComboBox<String> cbFornec;
    
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
    @FXML
    private JFXButton btAlterar;

    
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
    private int codCompra;
    private boolean parc_manual;
    private ArrayList<Object[]> parc_manuais;
    @FXML
    private Label lblTotalCompra;
    @FXML
    private Label lblParcManuais;
    @FXML
    private JFXButton btParcManuais;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        flagVolta = 1;
        CadProdutosController.setFlagVolta(0);
        CadFornecedorController.setFlagVolta(0);
        parc_manual = false;
        inicializaEstilo();
        inicializaCombobox();
        iniciarColunas();
        limparCampos();
        habilitarCampos(false);
        setRadioButton(true, false);
        setRdPreco(true, false);
        
        setarBotaoManual();
        if(BuscaComprasController.getFlag() == 1) //Vindo da busca de compras
        {
            lblParcManuais.setDisable(false);
            btParcManuais.setDisable(false);
            
            habilitarBotoes(false, false, true, true, true, true, true);   
            retorno = BuscaComprasController.getRetorno();
            setarCampos(retorno);
        }
        else //Inicio
        {
            lblParcManuais.setDisable(true);
            btParcManuais.setDisable(true);
            
            habilitarBotoes(true, false, false, false, false, true, true);
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
        
        lblTotalCompra.setVisible(true);
        lblTotalCompra.setText(String.format("Total da Compra: R$ %.2f", valorTotal));
        tableview.setItems(FXCollections.observableArrayList(obs_list)); //Adicione observable list na tabela.
        codCompra = (int)obj[0];
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
        
        btAddProd.setStyle("-fx-background-color: " + cor);
        btRemoverProd.setStyle("-fx-background-color: " + cor);
        rdUnidade.setStyle("-jfx-selected-color: "+ cor);
        rdTotal.setStyle("-jfx-selected-color: "+ cor);
        //btAddFornec.setStyle("-fx-background-color: " + cor);
        //btCancelForne.setStyle("-fx-background-color: " + cor);
        
        rdAVista.setStyle("-jfx-selected-color: " + cor);
        rdParcelado.setStyle("-jfx-selected-color: " + cor);
        
        txtDateVenc.setDefaultColor(CorSistema.hex2Rgb(CorSistema.getCorHex()));
        hbox.setStyle("-fx-background-color: " + cor);
        btParcManuais.setStyle("-fx-background-color: "+ cor);
    }
    
    public void limparCampos(){
        lblTotalCompra.setText("Total da Compra: R$");
        lblTotalCompra.setVisible(false);
        
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
        
        btAddProd.setDisable(value);
        btRemoverProd.setDisable(value);
        
        tableview.setDisable(value);
        
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
        
        ArrayList<String> listStrProds = new ArrayList<>();
        ArrayList<String> listStrFornecs = new ArrayList<>();
        for (int i = 0; i < listProds.size(); i++)
            listStrProds.add((String)listProds.get(i)[1]);
        cbProdutos.setTooltip(new Tooltip());
        cbProdutos.setEditable(true);
        cbProdutos.getItems().addAll(listStrProds);
        new ComboBoxAutoComplete<String>(cbProdutos, 200, 100);
        
        for (int i = 0; i < listFornec.size(); i++)
            listStrFornecs.add((String)listFornec.get(i)[2]);
        
        cbFornec.setTooltip(new Tooltip());
        cbFornec.setEditable(true);
        cbFornec.getItems().addAll(listStrFornecs);
        new ComboBoxAutoComplete<String>(cbFornec, 200, 520);
        
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
            
            
            if(listProdsCompra.size() > 0)
            {
                
                int j = 0;
                String aux = (String)listProdsCompra.get(j)[1];
                String auxj = (String)prod[1];
                while(j < listProdsCompra.size() && !aux.equals(auxj)){
                    aux = (String)listProdsCompra.get(j)[1];
                    j++;
                }
            
                j = j == 0 ? 0 : j - 1;
                if(j < listProdsCompra.size() && aux.equals(auxj))
                {
                    int temp_qt = (int)prod[3] + (int)listProdsCompra.get(j)[3];
                    double temp_total = (double)prod[4] + (double)listProdsCompra.get(j)[4];
                    double temp_valor = temp_total / temp_qt;

                    listProdsCompra.get(j)[2] = temp_valor;
                    listProdsCompra.get(j)[3] = temp_qt;
                    listProdsCompra.get(j)[4] = temp_total;
                }
                else
                    listProdsCompra.add(prod);
            }
            else
                listProdsCompra.add(prod);
            
            
            valorTotal = 0;
            double temp;
            String total_temp;
            String prec_temp;
            for (int i = 0; i < listProdsCompra.size(); i++) {
                valorTotal += (double)listProdsCompra.get(i)[4];
                temp = (double)listProdsCompra.get(i)[4];
                total_temp = String.format("%.2f", temp).replace(",", ".");
                temp = (double)listProdsCompra.get(i)[2];
                prec_temp = String.format("%.2f", temp).replace(",", ".");
                
                obj = new Objeto((String)listProdsCompra.get(i)[1], 
                        prec_temp, 
                        String.valueOf(listProdsCompra.get(i)[3]), 
                        total_temp);
                obsList.add(obj);
            }
            
            lblTotalCompra.setVisible(true);
            lblTotalCompra.setText(String.format("Total da Compra: R$ %.2f", valorTotal));
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
            valorTotal = 0;
            double temp;
            String total_temp;
            String prec_temp;
            for (int i = 0; i < listProdsCompra.size(); i++) {
                valorTotal += (double)listProdsCompra.get(i)[4];
                temp = (double)listProdsCompra.get(i)[4];
                total_temp = String.format("%.2f", temp).replace(",", ".");
                temp = (double)listProdsCompra.get(i)[2];
                prec_temp = String.format("%.2f", temp).replace(",", ".");

                obj = new Objeto((String)listProdsCompra.get(i)[1], 
                        prec_temp, 
                        String.valueOf(listProdsCompra.get(i)[3]), 
                        total_temp);
                obsList.add(obj);
             }
           
            lblTotalCompra.setVisible(true);
            lblTotalCompra.setText(String.format("Total da Compra: R$ %.2f", valorTotal));
           
           tableview.setItems(FXCollections.observableArrayList(obsList)); //Adicione observable list na tabela.
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
        lblParcManuais.setDisable(false);
        btParcManuais.setDisable(false);
        setarBotaoManual();
        cbProdutos.requestFocus();
        flagAlter = 0;
    }
    
    @FXML
    private void evtAlterar(ActionEvent event) {
        habilitarBotoes(false, true, false, false, true, true, true);
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
        lblParcManuais.setDisable(true);
        btParcManuais.setDisable(true);
        setarBotaoManual();
    }
    
    @FXML
    private void evtSalvar(ActionEvent event) {
        Object[] objCompra = new Object[7];
        Object[] objParcela = new Object[7];
        int aux_atu = 0;
        String temp_str = "";
        int log = 1;
        try{
            if (ValidarErros())// -- faz tudo pra baixo. //Erros: campos nulos, datas e valores negativos, etc.
            {
                
                objCompra[0] = (int)-1; //Cod compra
                objCompra[1] = (int)listFornec.get(cbFornec.getSelectionModel().getSelectedIndex())[0]; //Cod fornecedor
                objCompra[2] = (int)1; //BUSCAR DA FUNÇÃO DO ZACARIAS; //Cod funcionario

                if(parc_manual)
                {
                    objCompra[3] = parc_manuais.size();
                    objCompra[4] = (double)0;
                    double temp_valor = 0;
                    for (int i = 0; i < parc_manuais.size(); i++) {
                        temp_valor += (double)parc_manuais.get(i)[0];
                    }
                    temp_valor /= parc_manuais.size();
                    objCompra[5] = temp_valor;
                }
                else
                {   
                    if(rdAVista.isSelected()){
                        objCompra[3] = 1;
                        objCompra[4] = (double)0;
                    }
                    else{
                        objCompra[3] = Integer.parseInt(txtQtdParcelas.getText()); //Quantidade de parcelas.
                        objCompra[4] = Double.parseDouble(txtJuros.getText().replace(".", "").replace(",", ".")); //Valor juros.
                    }
                    objCompra[5] = (double)(valorTotal * ((double)objCompra[4]/100 + 1)); //Valor total
                }
                objCompra[6] = (LocalDate)LocalDate.now(); //Data da compra

                if(!parc_manual)
                {
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
                }
                else
                {
                    
                    objParcela[0] = (int)-1;
                    objParcela[1] = 0;
                    objParcela[2] = parc_manuais.get(0)[1];
                    objParcela[3] = (int)1;
                    objParcela[4] = (LocalDate)null;
                    objParcela[5] = (double)-1;
                    objParcela[6] = (int) -1;
                }

                CtrCompra ctrCompra = new CtrCompra();
                aux_atu = 0;
                if(rdAVista.isSelected())
                {
                    CtrPagamento ctrPagamento = new CtrPagamento();
                    log = ctrPagamento.validar_caixa();
                }
                
                if(flagAlter == 1)
                {
                    aux_atu = 1;
                    if(!excluir(codCompra))
                    {
                        aux_atu = -1;
                        Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao excluir compra!", ButtonType.OK);
                        a.showAndWait();
                    }
                }
                
                if(log > 0) //Validador de caixa 0=caixa nao aberto ainda, -1 = caixa fechado, >0 caixa disponivel.
                {
                    if(ctrCompra.salvar(objCompra, objParcela, listProdsCompra, parc_manual, parc_manuais) && aux_atu != -1)
                    {
                        limparCampos();
                        habilitarBotoes(true, false, false, false, false, true, true);
                        lblParcManuais.setDisable(true);
                        btParcManuais.setDisable(true);
                        setarBotaoManual();
                        habilitarCampos(false);
                        tableview = new TableView<>();
                        listProdsCompra = new ArrayList<>();

                        if(rdAVista.isSelected())
                        {
                            int aux_cod = ctrCompra.getMaxPK();
                            CtrPagamento pagamento = new CtrPagamento();
                            Object[] obj = new Object[5];
                            obj[0] = 1;
                            obj[1] = aux_cod;
                            obj[2] = 1; //Codigo funcionario
                            obj[3] = valorTotal;

                            log = pagamento.pagar(obj);
                            temp_str = "e paga";
                        }
                        
                        Alert a;
                        if(aux_atu == 0) 
                            a = new Alert(Alert.AlertType.INFORMATION, "Compra realizada " + temp_str + " com sucesso!", ButtonType.OK);
                        else //aux_atu == 1
                            a = new Alert(Alert.AlertType.INFORMATION, "Compra alterada " + temp_str + " com sucesso!", ButtonType.OK);
                        a.showAndWait();
                        
                    }
                }
                if(log == 0)
                {
                    Alert a = new Alert(Alert.AlertType.WARNING, "O Caixa não foi aberto ainda, portanto a conta não pode ser paga!", ButtonType.OK);
                    a.showAndWait();
                }
                else if(log == -1)
                {
                    Alert a = new Alert(Alert.AlertType.WARNING, "O Caixa já foi fechado, portanto a conta não pode ser paga!", ButtonType.OK);
                    a.showAndWait();
                }
            }
            
        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao registrar compra!\nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }

    public boolean ValidarErros(){
        boolean flag = true;
        int[] erro = new int[]{0,0,0,0,0};
        
        return true;
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
    
    private boolean excluir(int cod)
    {
        CtrCompra compra = new CtrCompra();
        return compra.excluir(cod);
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Deseja mesmo excluir esta compra?", ButtonType.YES, ButtonType.NO);
        a.showAndWait();
        if(a.getResult() == ButtonType.YES)
        {
            if(excluir(codCompra))
            {
                a = new Alert(Alert.AlertType.INFORMATION, "Compra excluída com sucesso!", ButtonType.OK);
                limparCampos();
                habilitarCampos(false);
                habilitarBotoes(true, false, false, false, false, true, true);
                lblParcManuais.setDisable(true);
                btParcManuais.setDisable(true);
                setarBotaoManual();
            }
            else
            {
                a = new Alert(Alert.AlertType.ERROR, "Erro ao excluir compra!", ButtonType.OK);
            }
            a.showAndWait();
        }
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

    public void setarBotaoManual()
    {
        btParcManuais.setText("V");
        String cor = CorSistema.getCorHex();
        btParcManuais.setStyle("-fx-background-color: " + cor);
        lblParcManuais.setText("Gerar Parcelas manualmente");
        parc_manual = false;
        parc_manuais = null;
    }
    
    @FXML
    private void evtBtParcManuais(ActionEvent event) {
        if(btParcManuais.getText().equals("X"))
        {
            habilitarCampos(true);
            btParcManuais.setText("V");
            String cor = CorSistema.getCorHex();
            btParcManuais.setStyle("-fx-background-color: " + cor);
            lblParcManuais.setText("Gerar Parcelas manualmente");
            parc_manual = false;
            parc_manuais = null;
            setRadioButton(true, false);
        }
        else
        {
            try
            {
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParcelasManuais.fxml")));
                stage.setScene(scene);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                if(ParcelasManuaisController.getFlag_retorno() == 1)
                {
                    lblParcManuais.setText("Cancelar parcelas manuais");
                    btParcManuais.setText("X");
                    btParcManuais.setStyle("-fx-background-color: #A8010C");

                    habilitarCampos(false);
                    cbFornec.setDisable(false);
                    parc_manual = true;
                    parc_manuais = ParcelasManuaisController.getLista();
                    setRadioButton(false, true);
                }

            }catch(Exception er){
                Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de cadastro de parcelas manuais! " + er.getMessage(), ButtonType.OK);
                a.showAndWait();
            }
        }
    }

    
    
}
