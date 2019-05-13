/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.interfaces.buscas;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.controladoras.CtrFornecedor;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Henrique K. Secchi
 */
public class BuscaFornecedorController implements Initializable {

    @FXML
    private BorderPane paneprincipal;
    @FXML
    private HBox paneBotoes;
    @FXML
    private JFXButton btSelecionar;
    @FXML
    private JFXButton btVoltar;
    @FXML
    private JFXTextField txtPesquisa;
    @FXML
    private JFXButton btBuscar;
    @FXML
    private HBox panePintar;
    @FXML
    private TableView<Objeto> tableview;
    @FXML
    private JFXRadioButton rdNome;
    @FXML
    private JFXRadioButton rdCNPJ;
    @FXML
    private JFXRadioButton rdEmail;
    @FXML
    private TableColumn<String, String> colCnpj;
    @FXML
    private TableColumn<String, String> colNome;
    @FXML
    private TableColumn<String, String> colCelular;
    @FXML
    private TableColumn<String, String> colEmail;
    @FXML
    private TableColumn<String, String> colCep;
    @FXML
    private TableColumn<String, String> colCidade;

    
    //Variáveis para controlar a funcionalidade do sistema.
    private static int flag = 0;
    private static Object[] retorno;
    ArrayList<Object[]> list;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializaEstilo();
        
        habilitarBotoes(true, false, true);
        setRadioButtons(true, false, false);
        iniciarColunas();
        btVoltar.setText("Voltar");
    }    

    //################################# PARTE DE ESTILO DA TELA #################################//
    
    private void inicializaEstilo()
    {
        String cor = CorSistema.getCorHex();
        btSelecionar.setStyle("-fx-background-color: " + cor);
        btBuscar.setStyle("-fx-background-color: " + cor);
        btVoltar.setStyle("-fx-background-color: " + cor);
        panePintar.setStyle("-fx-background-color: " + cor);
        txtPesquisa.setFocusColor(CorSistema.hex2Rgb(CorSistema.getCorHex()));
        /*
        Pintar radio buttons também
        */
    }

    //################################# PARTE DE FUNCIONALIDADES DA TELA #################################//
    private void habilitarBotoes(boolean buscar, boolean selecionar, boolean voltar)
    {
        btBuscar.setDisable(!buscar);
        btSelecionar.setDisable(!selecionar);
        btVoltar.setDisable(!voltar);
    }
    
    private void iniciarColunas() {
        
        colCnpj.setCellValueFactory(new PropertyValueFactory<>("param1"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("param2"));
        colCelular.setCellValueFactory(new PropertyValueFactory<>("param3"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("param4"));
        colCep.setCellValueFactory(new PropertyValueFactory<>("param5"));
        colCidade.setCellValueFactory(new PropertyValueFactory<>("param6"));
        
    }
    
    private void setRadioButtons(boolean v1, boolean v2, boolean v3)
    {
        rdNome.setSelected(v1);
        rdCNPJ.setSelected(v2);
        rdEmail.setSelected(v3);
    }
    
    
    @FXML
    private void evtSelecionar(ActionEvent event) {
        try{
                
                if(tableview.getSelectionModel().getSelectedIndex() != -1)
                {
                    flag = 1;

                    int index = tableview.getSelectionModel().getSelectedIndex();
                    retorno = list.get(index);

                    Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/basicas/CadFornecedor.fxml"));
                    /*
                    Parent root;
                    if(RegistrarCompraController.getFlagVolta() == 1)
                        root = FXMLLoader.load(getClass().getResource("/Interfaces/henrique/RegistrarCompra.fxml"));
                    else
                        root = FXMLLoader.load(getClass().getResource("/Interfaces/henrique/CadFornecedor.fxml"));
                    */
                    paneprincipal.getChildren().clear();
                    paneprincipal.getChildren().add(root);
                }
                else
                {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Selecione uma linha da tabela!", ButtonType.OK);
                    a.showAndWait();
                }

            }catch(Exception er){
                Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de cadastro de forneceodor!", ButtonType.OK);
                a.showAndWait();
            }
    }

    @FXML
    private void evtVoltar(ActionEvent event) {
        if(btVoltar.getText().equals("Voltar"))
        {
            try{
                flag = 0;
                //Adicionar objeto de retorno aqui
                Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/interfaces/basicas/CadFornecedor.fxml"));
                /*
                Parent root;
                    if(RegistrarCompraController.getFlagVolta() == 1)
                        root = FXMLLoader.load(getClass().getResource("/Interfaces/henrique/RegistrarCompra.fxml"));
                    else
                        root = FXMLLoader.load(getClass().getResource("/Interfaces/henrique/CadFornecedor.fxml"));
                */                
                paneprincipal.getChildren().clear();
                paneprincipal.getChildren().add(root);

            }catch(Exception er){
                Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de cadastro de fornecedor!", ButtonType.OK);
                a.showAndWait();
            }
        }
        else if(btVoltar.getText().equals("Cancelar"))
        {
            habilitarBotoes(true, false, true);
            setRadioButtons(true, false, false);
            
            txtPesquisa.requestFocus();
            btVoltar.setText("Voltar");
            
            ArrayList<Objeto> obsList = new ArrayList<>();
            tableview.setItems(FXCollections.observableArrayList(obsList));
        }
    }

    @FXML
    private void evtBuscar(ActionEvent event) {
        String aux = "forn_nome ilike '%%'";
            CtrFornecedor ctrforn = new CtrFornecedor();
            ArrayList<Objeto> obsList = new ArrayList<>();
            Objeto obj;

            if(rdNome.isSelected()) //Pesquisa por nome
                aux = "forn_nome ilike '%" + txtPesquisa.getText() + "%'";
            else if(rdCNPJ.isSelected()) //Pesquisa por cnpj
                aux = "forn_cnpj ilike '%" + txtPesquisa.getText() + "%'";
            else if(rdEmail.isSelected()) //Pesquisa por email
                aux = "forn_email ilike '%" + txtPesquisa.getText() + "%'";

            list = ctrforn.buscar(aux);

            for (int i = 0; i < list.size(); i++) { //Cria um observable list para adicionar na tabela.
                obj = new Objeto((String)list.get(i)[1], (String)list.get(i)[2], (String)list.get(i)[3], (String)list.get(i)[4], (String)list.get(i)[9], (String)list.get(i)[8]);
                obsList.add(obj);
            }

            if(obsList.size() > 0) //Habilitar os botoes selecionar e  cancelar
            {
                btVoltar.setText("Cancelar");
                habilitarBotoes(true, true, true);
            }
            
            tableview.setItems(FXCollections.observableArrayList(obsList)); //Adicione observable list na tabela.
        
    }

    @FXML
    private void evtRdNome(ActionEvent event) {
        setRadioButtons(true, false, false);
    }

    @FXML
    private void evtRdCNPJ(ActionEvent event) {
        setRadioButtons(false, true, false);
    }

    @FXML
    private void evtRdEmail(ActionEvent event) {
        setRadioButtons(false, false, true);
    }
    
    public static int getFlag() {
        return flag;
    }

    public static Object[] getRetorno() {
        return retorno;
    }
    
}
