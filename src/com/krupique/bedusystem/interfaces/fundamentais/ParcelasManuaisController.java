/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.interfaces.fundamentais;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.utilidades.MaskFieldUtil;
import com.krupique.bedusystem.utilidades.Objeto;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Henrique K. Secchi
 */
public class ParcelasManuaisController implements Initializable {

    @FXML
    private JFXTextField txtValor;
    @FXML
    private JFXButton btAddParc;
    @FXML
    private JFXButton btRemoverProd;
    @FXML
    private JFXDatePicker dtVencimento;
    @FXML
    private JFXButton btSalvar;
    @FXML
    private JFXButton btVoltar;
    @FXML
    private HBox hbox;
    @FXML
    private Label lblProds;
    @FXML
    private TableView<Objeto> tableview;
    @FXML
    private TableColumn<String, String> colNum;
    @FXML
    private TableColumn<String, String> colPreco;
    @FXML
    private TableColumn<String, String> colDtVencimento;
    @FXML
    private AnchorPane paneprincipal;
    @FXML
    private Label lbltotal;

    /**
     * Initializes the controller class.
     */
    private static int flag_retorno = 0;
    private static ArrayList<Object[]> lista;
    private ArrayList<Objeto> list_tab;
    private double total;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        flag_retorno = 0;
        lista = new ArrayList<>();
        list_tab = new ArrayList<>();
        total = 0;
        
        colNum.setCellValueFactory(new PropertyValueFactory<>("param3"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("param1"));
        colDtVencimento.setCellValueFactory(new PropertyValueFactory<>("param2"));
    }    

    @FXML
    private void evtAddParc(ActionEvent event) {
        try
        {
            Objeto temp_obj;
            Object[] temp;
            double valor = Double.parseDouble(txtValor.getText().replace(",", "."));
            LocalDate data = dtVencimento.getValue();
            total += valor;
            temp_obj = new Objeto(String.valueOf(valor), data + "");
            temp = new Object[2];
            temp[0] = valor;
            temp[1] = data;
            
            lista.add(temp);
            list_tab.add(temp_obj);
            int cont = 1;
            for (int i = 0; i < list_tab.size(); i++) {
                list_tab.get(i).setParam3("" + cont++);
            }
            
            tableview.setItems(FXCollections.observableArrayList(list_tab));
            String str = String.format("%.2f", total);
            lbltotal.setText("Total: R$ " + str);
            
        }catch(Exception er){
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao adicionar parcela! Tente novamente!", ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void evtRemovProd(ActionEvent event) {
        try
        {
            int index = tableview.getSelectionModel().getSelectedIndex();
            if(index != -1){
                
                double aux = (double)lista.get(index)[0];
                lista.remove(index);
                list_tab.remove(index);
                
                int cont = 1;
                for (int i = 0; i < list_tab.size(); i++) {
                    list_tab.get(i).setParam3("" + cont++);
                }
                

                tableview.setItems(FXCollections.observableArrayList(list_tab));
                total-= aux;
            }
            
        }catch(Exception er)
        {
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao remover parcela! Tente novamente!", ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void evtSalvar(ActionEvent event) {
        flag_retorno = 1;
        Stage stage = (Stage)paneprincipal.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void evtVoltar(ActionEvent event) {
        flag_retorno = 0;
        Stage stage = (Stage)paneprincipal.getScene().getWindow();
        stage.close();
    }
    
    public static int getFlag_retorno() {
        return flag_retorno;
    }

    public static ArrayList<Object[]> getLista() {
        return lista;
    }

    @FXML
    private void evtKey(KeyEvent event) {
        MaskFieldUtil.monetaryField(txtValor);
    }
}
