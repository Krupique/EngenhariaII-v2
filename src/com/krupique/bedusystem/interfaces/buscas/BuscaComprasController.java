/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.interfaces.buscas;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.controladoras.CtrCompra;
import com.krupique.bedusystem.interfaces.fundamentais.RegistrarCompraController;
import com.krupique.bedusystem.utilidades.CorSistema;
import com.krupique.bedusystem.utilidades.Objeto;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Henrique K. Secchi
 */
public class BuscaComprasController implements Initializable {

    @FXML
    private BorderPane paneprincipal;
    @FXML
    private HBox paneBotoes;
    @FXML
    private JFXButton btSelecionar;
    @FXML
    private JFXButton btVoltar;
    @FXML
    private JFXRadioButton rdProduto;
    @FXML
    private JFXRadioButton rdFornecedor;
    @FXML
    private JFXRadioButton rdVenceNoDia;
    @FXML
    private JFXRadioButton rdVenceAte;
    @FXML
    private JFXTextField txtBusca;
    @FXML
    private JFXButton btBuscar;
    @FXML
    private JFXCheckBox ckExibirContasPagas;
    @FXML
    private JFXDatePicker dtBusca;
    @FXML
    private HBox hboxCompas;
    @FXML
    private HBox hboxParcelas;
    @FXML
    private TableColumn<String, String> colCompFunc;
    @FXML
    private TableColumn<String, String> colCompFornec;
    @FXML
    private TableColumn<String, String> colCompQtdParcelas;
    @FXML
    private TableColumn<String, String> colCompValorTotal;
    @FXML
    private TableColumn<String, String> colParNum;
    @FXML
    private TableColumn<String, String> colParStatus;
    @FXML
    private TableColumn<String, String> colParDtVenc;
    @FXML
    private TableColumn<String, String> colParcDtPagamento;
    @FXML
    private TableColumn<String, String> colParVlrPago;
    @FXML
    private TableColumn<String, String> colDtCompra;
    @FXML
    private TableView<Objeto> tbvCompras;
    @FXML
    private TableView<Objeto> tbvParcelas;

    
    CtrCompra ctrcompra;
    ArrayList<Object[]> lista;
    private static int flag = 0;
    private static Object[] retorno;
    private int codigo_compra;
    private int pos;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializaEstilo();
        iniciarColunas();
        setRadioButtons(true, false, false, false);
        ckExibirContasPagas.setSelected(false);
        dtBusca.setVisible(false);
        
        ctrcompra = new CtrCompra();
        lista = ctrcompra.buscar("produto.prod_nome ilike '%%'");
        
        exibir_tableview(lista, 0);
        btVoltar.setText("Voltar");
    }    
    
    //################################# PARTE DE ESTILO DA TELA #################################//
    
    private void inicializaEstilo()
    {
        String cor = CorSistema.getCorHex();
        btSelecionar.setStyle("-fx-background-color: " + cor);
        btBuscar.setStyle("-fx-background-color: " + cor);
        btVoltar.setStyle("-fx-background-color: " + cor);
        hboxCompas.setStyle("-fx-background-color: " + cor);
        hboxParcelas.setStyle("-fx-background-color: " + cor);
        txtBusca.setFocusColor(CorSistema.hex2Rgb(CorSistema.getCorHex()));
        dtBusca.setDefaultColor(CorSistema.hex2Rgb(CorSistema.getCorHex()));
        
        rdProduto.setStyle("-jfx-selected-color: " + cor);
        rdFornecedor.setStyle("-jfx-selected-color: " + cor);
        rdVenceAte.setStyle("-jfx-selected-color: " + cor);
        rdVenceNoDia.setStyle("-jfx-selected-color: " + cor);
        
        ckExibirContasPagas.setStyle("-jfx-checked-color: " + cor);
    }
    
    private void setRadioButtons(boolean v1, boolean v2, boolean v3, boolean v4)
    {
        rdProduto.setSelected(v1);
        rdFornecedor.setSelected(v2);
        rdVenceNoDia.setSelected(v3);
        rdVenceAte.setSelected(v4);
    }
    
    //################################# PARTE DE FUNCIONALIDADES DA TELA #################################//
    
    private void iniciarColunas() {
        
        colCompFunc.setCellValueFactory(new PropertyValueFactory<>("param1"));
        colCompFornec.setCellValueFactory(new PropertyValueFactory<>("param2"));
        colCompQtdParcelas.setCellValueFactory(new PropertyValueFactory<>("param3"));
        colCompValorTotal.setCellValueFactory(new PropertyValueFactory<>("param4"));
        colDtCompra.setCellValueFactory(new PropertyValueFactory<>("param5"));
        
        colParNum.setCellValueFactory(new PropertyValueFactory<>("param1"));
        colParStatus.setCellValueFactory(new PropertyValueFactory<>("param2"));
        colParDtVenc.setCellValueFactory(new PropertyValueFactory<>("param3"));
        colParcDtPagamento.setCellValueFactory(new PropertyValueFactory<>("param4"));
        colParVlrPago.setCellValueFactory(new PropertyValueFactory<>("param5"));
    }
    
    
    @FXML
    private void evtSelecionar(ActionEvent event) {
        try
        {if(tbvCompras.getSelectionModel().getSelectedIndex() != -1)
            {   
                flag = 1;
                int index = tbvCompras.getSelectionModel().getSelectedIndex();
                retorno = lista.get(index);
                
                int i = 0;
                ArrayList<Object[]> temp_lista = (ArrayList<Object[]>)retorno[6];
                while(i < temp_lista.size() && (int)temp_lista.get(i)[1] != 1)
                {
                    i++;
                }
                if(i < temp_lista.size() && (int)temp_lista.get(i)[1] == 1)
                {
                    Alert a = new Alert(Alert.AlertType.WARNING, "Esta conta já possui parcela paga!\nNão é possível alterá-la ou exluí-la!", ButtonType.OK);
                    a.showAndWait();
                }
                else
                {
                    Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/fundamentais/RegistrarCompra.fxml"));

                    paneprincipal.getChildren().clear();
                    paneprincipal.getChildren().add(root);
                }
                
            }
            else
            {
                Alert a = new Alert(Alert.AlertType.ERROR, "Selecione uma linha da tabela!", ButtonType.OK);
                a.showAndWait();
            }
                    
        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Registro de compras!", ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void evtVoltar(ActionEvent event) {
        if(btVoltar.getText().equals("Voltar"))
        {
            try{
                flag = 0;
                Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/fundamentais/RegistrarCompra.fxml"));
                      
                paneprincipal.getChildren().clear();
                paneprincipal.getChildren().add(root);

            }catch(Exception er){
                Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Registrar Compra!", ButtonType.OK);
                a.showAndWait();
            }
        }
        else if(btVoltar.getText().equals("Cancelar"))
        {
            setRadioButtons(true, false, false, false);
            dtBusca.setVisible(false);
            txtBusca.setVisible(true);
            
            txtBusca.requestFocus();
            btVoltar.setText("Voltar");
            
            ArrayList<Objeto> obsList = new ArrayList<>();
            tbvCompras.setItems(FXCollections.observableArrayList(obsList));
            tbvParcelas.setItems(FXCollections.observableArrayList(obsList));
        }
    }

    @FXML
    private void evtBusca(ActionEvent event) {
        String nome = "";
        if(rdProduto.isSelected())
            nome = "produto.prod_nome ilike '%" + txtBusca.getText() + "%'";
        else if(rdFornecedor.isSelected())
            nome = "fornecedor.forn_nome ilike '%" + txtBusca.getText() + "%'";
        else if(rdVenceNoDia.isSelected())
            nome = " = '" + dtBusca.getValue() + "'";
        else //rdVenceAteDia.isSelected()
            nome = " < '" + dtBusca.getValue() + "'";
        
        if(nome == null)
            nome = "";
        
        if(rdProduto.isSelected() || rdFornecedor.isSelected())
            lista = ctrcompra.buscar(nome);
        else
        {
            lista = ctrcompra.buscarPeriodo(nome);
        }
        exibir_tableview(lista, 0);
    }

    @FXML
    private void evtExibirContasPagas(ActionEvent event) {
        if(ckExibirContasPagas.isSelected())
            exibir_tableview(lista, 1);
        else
            exibir_tableview(lista, 0);
    }

    
    
    @FXML
    private void evtRdProduto(ActionEvent event) {
        setRadioButtons(true, false, false, false);
        txtBusca.setVisible(true);
        dtBusca.setVisible(false);
    }

    @FXML
    private void evtRdFornecedor(ActionEvent event) {
        setRadioButtons(false, true, false, false);
        txtBusca.setVisible(true);
        dtBusca.setVisible(false);
    }
    
    @FXML
    private void evtRdVenceNoDia(ActionEvent event) {
        setRadioButtons(false, false, true, false);
        dtBusca.setVisible(true);
        txtBusca.setVisible(false);
    }

    @FXML
    private void evtRdVenceAte(ActionEvent event) {
        setRadioButtons(false, false, false, true);
        dtBusca.setVisible(true);
        txtBusca.setVisible(false);
    }

    private void exibir_tableview(ArrayList<Object[]> lista, int flag) {
        Objeto obj_comp, obj_parc;
        Object[] temp, temp2;
        ArrayList<Objeto> obslist_comp = new ArrayList<>();
        ArrayList<Objeto> obslist_parc = new ArrayList<>();
        ArrayList<Object[]> arry_parc;
        String aux;
        double temp_valor;
        
        for (int i = 0; i < lista.size(); i++) {
            temp = new Object[6];
            temp[0] = lista.get(i)[0]; //Codigo compra
            temp[1] = lista.get(i)[1]; //Funcionário
            temp[2] = lista.get(i)[2]; //Fornecedor
            temp[3] = lista.get(i)[3]; //Qtd. Parcelas
            temp_valor = (double)lista.get(i)[4]; //Valor total
            aux = String.format("%.2f", temp_valor);
            temp[4] = aux; //Valor total
            temp[5] = lista.get(i)[5]; //Data - tá errado
            obj_comp = new Objeto((String)temp[1], (String)temp[2], 
                    String.valueOf((int)temp[3]), (String)temp[4], (String)temp[5] + "");
            
            obslist_comp.add(obj_comp);
        }
        
        tbvCompras.setItems(FXCollections.observableArrayList(obslist_comp));
    }
    
    private void exibirParcelas(int index, int flag)
    {
        System.out.println("I: " + index);
        Objeto obj_parc;
        Object[] temp, temp2;
        ArrayList<Objeto> obslist_parc = new ArrayList<>();
        ArrayList<Object[]> arry_parc;
        String aux;
        
        arry_parc = new ArrayList<>();
        arry_parc = (ArrayList<Object[]>)lista.get(index)[6];
        codigo_compra = (int)lista.get(index)[0];
        pos = index;
        for (int j = 0; j < arry_parc.size(); j++) {
            if(((int)arry_parc.get(j)[1] == 0 && flag == 0) || flag == 1)
            {
                temp2 = new Object[5];
                temp2[0] = arry_parc.get(j)[0];
                aux = String.valueOf((int)arry_parc.get(j)[1]);
                aux = aux.equals("0") ? "em haver" : "paga";
                temp2[1] = aux;
                temp2[2] = arry_parc.get(j)[2];
                temp2[3] = arry_parc.get(j)[3];
                temp2[4] = arry_parc.get(j)[4];

                obj_parc = new Objeto(String.valueOf((int)temp2[0]), (String)temp2[1], 
                        (String)temp2[2], (String)temp2[3], String.valueOf((double)temp2[4]));

                obslist_parc.add(obj_parc);
            }
        }
        tbvParcelas.setItems(FXCollections.observableArrayList(obslist_parc));
    }

    @FXML
    private void evtSelecionarTbv(MouseEvent event) {
        int index = tbvCompras.getSelectionModel().getSelectedIndex();
        if(index != -1)
        {
            if(ckExibirContasPagas.isSelected())
                exibirParcelas(index, 1);
            else
                exibirParcelas(index, 0);
        }
    }

    public static int getFlag() {
        return flag;
    }

    public static Object[] getRetorno() {
        return retorno;
    }
}
