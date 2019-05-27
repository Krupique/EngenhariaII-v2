
package com.krupique.bedusystem.interfaces.fundamentais;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.controladoras.ctrRecebimento;
import com.krupique.bedusystem.entidades.Funcionário;
import com.krupique.bedusystem.entidades.ParcelaRecebimento;
import com.krupique.bedusystem.entidades.Recebimento;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class QuitarContasReceberController implements Initializable {

    @FXML
    private BorderPane paneprincipal;
    @FXML
    private JFXButton btPagar;
    @FXML
    private JFXButton brRemover;
    @FXML
    private JFXButton btCancelar;
    @FXML
    private JFXButton btBuscar;
    @FXML
    private JFXButton btVoltar;
    @FXML
    private JFXTextField txtBusca;
    @FXML
    private JFXDatePicker dtBusca;
    @FXML
    private TableView<Recebimento> tab_reb;
    @FXML
    private TableColumn<Recebimento, Integer> colcodigo;
    @FXML
    private TableColumn<Recebimento, String> coldescricao;
    @FXML
    private TableColumn<Recebimento, Date> coldata;
    @FXML
    private TableColumn<Recebimento, Double> colvalor;
    @FXML
    private TableColumn<Recebimento, Funcionário> colfunc;
    @FXML
    private TableView<ParcelaRecebimento> tab_par;
    @FXML
    private TableColumn<ParcelaRecebimento, Integer> colcodigop;
    @FXML
    private TableColumn<ParcelaRecebimento, Integer> colnum;
    @FXML
    private TableColumn<ParcelaRecebimento, Integer> colstatus;
    @FXML
    private TableColumn<ParcelaRecebimento, Date> colvenc;
    @FXML
    private TableColumn<ParcelaRecebimento, Date> colpagamento;
    @FXML
    private TableColumn<ParcelaRecebimento, Integer> colcliente;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        colcodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        coldescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        coldata.setCellValueFactory(new PropertyValueFactory<>("data"));
        colvalor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colfunc.setCellValueFactory(new PropertyValueFactory<Recebimento, Funcionário>("funcionario"));
        
        colcodigop.setCellValueFactory(new PropertyValueFactory<ParcelaRecebimento, Integer>("parc_receb_codigo"));
        colnum.setCellValueFactory(new PropertyValueFactory<ParcelaRecebimento, Integer>("parc_receb_numero"));
        colstatus.setCellValueFactory(new PropertyValueFactory<ParcelaRecebimento, Integer>("parc_receb_status"));
        colvenc.setCellValueFactory(new PropertyValueFactory<ParcelaRecebimento, Date>("parc_receb_dtvencimento"));
        colpagamento.setCellValueFactory(new PropertyValueFactory<ParcelaRecebimento, Date>("parc_receb_dtpagamento"));
        colcliente.setCellValueFactory(new PropertyValueFactory<ParcelaRecebimento, Integer>("cli_cod"));
        
        CarregaTabelaRecebimento("");

        


    }  
    
        private void CarregaTabelaRecebimento(String string)
    {
        tab_reb.getItems().clear();
        tab_reb.setItems(ctrRecebimento.instancia().getTodos());
    }

    @FXML
    private void evtPagar(ActionEvent event) 
    {
        
    }

    @FXML
    private void evtRemover(ActionEvent event) 
    {
        
    }

    @FXML
    private void evtCancelar(ActionEvent event) 
    {
        
    }

    @FXML
    private void evtBuscar(ActionEvent event) 
    {
        
    }

    @FXML
    private void evtVoltar(ActionEvent event) 
    {
        
    }

    @FXML
    private void evtMudaData(ActionEvent event) 
    {


    }
    
}
