/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.interfaces.fundamentais;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.utilidades.Objeto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Caique
 */
public class AprovarOrcamentoController implements Initializable
{

    @FXML
    private BorderPane paneprincipal;
    @FXML
    private JFXTextField tf_nomeCliente;
    @FXML
    private JFXTextField tf_cpf;
    @FXML
    private JFXTextField tf_telefone;
    @FXML
    private JFXTextField tf_email;
    @FXML
    private JFXTextField tf_endereco;
    @FXML
    private JFXTextField tf_placa;
    @FXML
    private JFXTextField tf_modelo;
    @FXML
    private JFXTextField tf_marca;
    @FXML
    private JFXTextField tf_ano;
    @FXML
    private TableView<Objeto> tv_pecas;
    @FXML
    private TableColumn<Object, String> tc_codPeca;
    @FXML
    private TableColumn<Object, String> tc_nomePeca;
    @FXML
    private TableColumn<Object, String> tc_qtdPeca;
    @FXML
    private TableColumn<Object, String> tc_precoPreca;
    @FXML
    private JFXTextField tf_subtotalPeca;
    @FXML
    private TableView<Objeto> tv_servicos;
    @FXML
    private TableColumn<Object, String> tc_codServico;
    @FXML
    private TableColumn<Object, String> tc_descricaoServico;
    @FXML
    private TableColumn<Object, String> tc_precoServico;
    @FXML
    private JFXTextField tf_subtotalServico;
    @FXML
    private JFXTextField tf_total;
    @FXML
    private JFXButton btConfirmar;
    @FXML
    private JFXButton btVoltar;
    @FXML
    private ListView<String> lv_orcamentos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void clickConfirmar(ActionEvent event)
    {
    }

    @FXML
    private void clickVoltar(ActionEvent event)
    {
    }

    @FXML
    private void clickList(MouseEvent event)
    {
    }
    
}
