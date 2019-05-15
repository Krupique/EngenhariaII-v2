/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.interfaces.buscas;

import com.krupique.bedusystem.interfaces.basicas.CadFuncionarioController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.controladoras.ctrFuncionarios;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Henrique K. Secchi
 */
public class BuscaFuncionarioController implements Initializable
{

    final ToggleGroup group = new ToggleGroup();
    
    @FXML
    private BorderPane paneprincipal;
    @FXML
    private HBox paneBotoes;
    @FXML
    private JFXButton btSelecionar;
    @FXML
    private JFXButton btVoltar;
    @FXML
    private JFXButton btBuscar;
    @FXML
    private HBox panePintar;
    @FXML
    private TableView<Objeto> tableview;
    @FXML
    private JFXTextField tf_pesquisa;
    @FXML
    private JFXRadioButton rb_nome;
    @FXML
    private JFXRadioButton rb_funcao;
    @FXML
    private JFXDatePicker dp_data;
    @FXML
    private TableColumn<Object, String> tc_nome;
    @FXML
    private TableColumn<Object, String> tc_funca;
    @FXML
    private TableColumn<Object, String> tc_contato;
    @FXML
    private TableColumn<Object, String> tc_email;
    @FXML
    private TableColumn<Object, String> tc_endereco;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        //inicializaEstilo();
        tc_contato.setCellValueFactory(new PropertyValueFactory<>("param5"));
        tc_endereco.setCellValueFactory(new PropertyValueFactory<>("param13"));
        tc_funca.setCellValueFactory(new PropertyValueFactory<>("param3"));
        tc_nome.setCellValueFactory(new PropertyValueFactory<>("param2"));
        tc_email.setCellValueFactory(new PropertyValueFactory<>("param6"));
        
        rb_funcao.setToggleGroup(group);
        rb_nome.setToggleGroup(group);
        rb_nome.setSelected(true);
        
        CadFuncionarioController.funcionario = null;
    }

    //################################# PARTE DE ESTILO DA TELA #################################//
    private void inicializaEstilo()
    {
        String cor = CorSistema.getCorHex();
        btSelecionar.setStyle("-fx-background-color: " + cor);
        btBuscar.setStyle("-fx-background-color: " + cor);
        btVoltar.setStyle("-fx-background-color: " + cor);
        panePintar.setStyle("-fx-background-color: " + cor);
        //txtPesquisa.setFocusColor(CorSistema.hex2Rgb(CorSistema.getCorHex()));
        /*
        Pintar radio buttons também
         */
    }

    //################################# PARTE DE FUNCIONALIDADES DA TELA #################################//
    @FXML
    private void evtSelecionar(ActionEvent event)
    {
        CadFuncionarioController.funcionario = tableview.getSelectionModel().getSelectedItem();
        evtVoltar(event);
    }

    @FXML
    private void evtVoltar(ActionEvent event)
    {
        try
        {
            Stage stage = (Stage) paneprincipal.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/basicas/CadFuncionario.fxml"));
            paneprincipal.getChildren().clear();
            paneprincipal.getChildren().add(root);

        } catch (Exception er)
        {
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de Busca de Funcionários! \nErro: " + er.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void evtBuscar(ActionEvent event)
    {
        ArrayList<Object[]> list;
        ArrayList<Objeto> obsList = new ArrayList<>();
        Objeto obj;
        JFXRadioButton rb = rb_nome.isSelected()?rb_nome: (rb_funcao.isSelected()?rb_funcao:null);
        
        list = new ctrFuncionarios().procurarTodos(tf_pesquisa,rb,dp_data);
        for (int i = 0; i < list.size(); i++) 
        { 
            
            obj = new Objeto(String.valueOf(list.get(i)[0]), (String)list.get(i)[1], (String)list.get(i)[2],
                    (String)list.get(i)[3],(String)list.get(i)[4],(String)list.get(i)[5],(String)list.get(i)[6],
                    (String)list.get(i)[7],(String)list.get(i)[8],String.valueOf(list.get(i)[9]),String.valueOf(list.get(i)[10]),
                    String.valueOf(list.get(i)[11]),(String)list.get(i)[12]);
            obsList.add(obj);    
        }
       
        tableview.setItems(FXCollections.observableArrayList(obsList));
    }

    @FXML
    private void clickTabela(MouseEvent event)
    {
        if(event.getClickCount() == 2 && tableview.getSelectionModel().getSelectedItem() != null)
            evtSelecionar(new ActionEvent());
    }

}
