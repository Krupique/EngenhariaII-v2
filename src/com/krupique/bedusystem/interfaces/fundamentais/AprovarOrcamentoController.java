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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import com.krupique.bedusystem.controladoras.CtrOrcamento;
import com.krupique.bedusystem.controladoras.ctrOS;
import java.util.ArrayList;
import com.krupique.bedusystem.utilidades.MaskFieldUtil;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Caique
 */
public class AprovarOrcamentoController implements Initializable
{
    private ArrayList<Objeto> orcs = new ArrayList<>();
    private ArrayList<Objeto> s = new ArrayList<>();
    private ArrayList<Objeto> produstos = new ArrayList<>();
    private ArrayList<Objeto> servicos = new ArrayList<>();
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
    @FXML
    private JFXTextField tf_cor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        MaskFieldUtil.cpfCnpjField(tf_cpf);
        MaskFieldUtil.foneField(tf_telefone);
        
        tc_codPeca.setCellValueFactory(new PropertyValueFactory<>("param1"));
        tc_nomePeca.setCellValueFactory(new PropertyValueFactory<>("param2"));
        tc_qtdPeca.setCellValueFactory(new PropertyValueFactory<>("param3"));
        tc_precoPreca.setCellValueFactory(new PropertyValueFactory<>("param4"));
        
        tc_codServico.setCellValueFactory(new PropertyValueFactory<>("param1"));
        tc_descricaoServico.setCellValueFactory(new PropertyValueFactory<>("param2"));
        tc_precoServico.setCellValueFactory(new PropertyValueFactory<>("param3"));
        
        inicializa();
    }    

    public void inicializa()
    {
        ArrayList<Object[]> list = CtrOrcamento.instancia().getInfoTabela(lv_orcamentos);
        for(int i = 0; i < list.size();i++)
        {
            Objeto obj;
            obj = new Objeto(String.valueOf(list.get(i)[0]),String.valueOf(list.get(i)[1]),
            String.valueOf(list.get(i)[2]),String.valueOf(list.get(i)[3]),String.valueOf(list.get(i)[4]),
            String.valueOf(list.get(i)[5]),String.valueOf(list.get(i)[6]),String.valueOf(list.get(i)[7]),
            String.valueOf(list.get(i)[8]),String.valueOf(list.get(i)[9]),String.valueOf(list.get(i)[10]));
            
            orcs.add(obj);
        }
        
        tf_ano.clear(); tf_cor.clear(); tf_cpf.clear(); tf_email.clear(); tf_endereco.clear();
        tf_marca.clear(); tf_modelo.clear(); tf_nomeCliente.clear(); tf_placa.clear(); tf_subtotalPeca.clear(); 
        tf_subtotalServico.clear(); tf_telefone.clear(); tf_total.clear(); 
    }
    
    @FXML
    private void clickConfirmar(ActionEvent event)
    {
        if(new Alert(Alert.AlertType.CONFIRMATION, "Aprovar Orçamento " + lv_orcamentos.getSelectionModel().getSelectedItem() + " ?", ButtonType.YES,ButtonType.NO).showAndWait().get() == ButtonType.YES)
        {
            if(new ctrOS().gravar(Integer.parseInt(lv_orcamentos.getSelectionModel().getSelectedItem())))
                new Alert(Alert.AlertType.CONFIRMATION, "Orçamento aprovado com sucesso!!", ButtonType.OK).showAndWait();
            else
                new Alert(Alert.AlertType.CONFIRMATION, "Erro na aprocação do orçamento!!", ButtonType.OK).showAndWait();
            inicializa();
        }
    }

    @FXML
    private void clickVoltar(ActionEvent event)
    {
    }

    @FXML
    private void clickList(MouseEvent event)
    {
        if(event.getClickCount() == 2 && lv_orcamentos.getSelectionModel().getSelectedIndex() >= 0)
        {
            tv_pecas.setItems(FXCollections.observableArrayList());
            tv_servicos.setItems(FXCollections.observableArrayList());
            produstos = new ArrayList<>();
            servicos = new ArrayList<>();
            
            int pos = lv_orcamentos.getSelectionModel().getSelectedIndex();
            
            tf_nomeCliente.setText(orcs.get(pos).getParam1());
            tf_cpf.setText(orcs.get(pos).getParam2());
            tf_telefone.setText(orcs.get(pos).getParam3());
            tf_email.setText(orcs.get(pos).getParam4().equals("null")?"":orcs.get(pos).getParam4());
            tf_endereco.setText(orcs.get(pos).getParam5());
            
            tf_placa.setText(orcs.get(pos).getParam6());
            tf_modelo.setText(orcs.get(pos).getParam7());
            tf_marca.setText(orcs.get(pos).getParam8());
            tf_ano.setText(orcs.get(pos).getParam9().equals("null")?"":orcs.get(pos).getParam9());
            tf_cor.setText(orcs.get(pos).getParam10());
            
            tf_total.setText("R$ " + orcs.get(pos).getParam11());
            
            
            ArrayList<Object[]> list2 = CtrOrcamento.instancia().getProdutos_Servicos(Integer.parseInt(lv_orcamentos.getSelectionModel().getSelectedItem()),0);
            for (int j = 0; j < list2.size(); j++)
                produstos.add(new Objeto(String.valueOf(list2.get(j)[0]), String.valueOf(list2.get(j)[1]), 
                        String.valueOf(list2.get(j)[2]),String.valueOf(list2.get(j)[3])));
            
            list2 = CtrOrcamento.instancia().getProdutos_Servicos(Integer.parseInt(lv_orcamentos.getSelectionModel().getSelectedItem()),1);
            for (int j = 0; j < list2.size(); j++)
            {
                servicos.add(new Objeto(String.valueOf(list2.get(j)[0]), String.valueOf(list2.get(j)[1]), 
                        String.valueOf(list2.get(j)[2])));
            }
            
            
            tv_pecas.setItems(FXCollections.observableArrayList(produstos));
            tv_servicos.setItems(FXCollections.observableArrayList(servicos));
            calcula_subTotal();
        }
    }

    private void calcula_subTotal()
    {
        double valor = 0.0;
        for (int i = 0; i < tv_pecas.getItems().size(); i++)
            valor += Double.parseDouble(tv_pecas.getItems().get(i).getParam4()) 
                    * Integer.parseInt(tv_pecas.getItems().get(i).getParam3());
        tf_subtotalPeca.setText("R$ " + valor);
        
        valor = 0.0;
        for (int i = 0; i < tv_servicos.getItems().size(); i++)
            valor += Double.parseDouble(tv_servicos.getItems().get(i).getParam3());
        tf_subtotalServico.setText("R$ " + valor);
    }
    
}
