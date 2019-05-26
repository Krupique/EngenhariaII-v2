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
import com.krupique.bedusystem.controladoras.CtrCliente;
import com.krupique.bedusystem.utilidades.MaskFieldUtil;
import com.krupique.bedusystem.utilidades.Objeto;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import com.krupique.bedusystem.controladoras.ctrRecebimento;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author Caique
 */
public class GerarContasReceberController implements Initializable
{
    private final ToggleGroup group = new ToggleGroup();
    public static Objeto os;
    public static double valor;
    @FXML
    private JFXTextField tf_valor;
    @FXML
    private JFXRadioButton rb_avista;
    @FXML
    private JFXRadioButton rb_parcelado;
    @FXML
    private JFXComboBox<String> cb_parcelas;
    @FXML
    private TableView<Objeto> tv_pagmento;
    @FXML
    private TableColumn<Object, String> tc_parcela;
    @FXML
    private TableColumn<Object, String> tc_valor;
    @FXML
    private TableColumn<Object, String> tc_data;
    @FXML
    private JFXDatePicker dp_data;
    @FXML
    private JFXTextField tf_cliente;
    @FXML
    private JFXButton bt_confirmar;
    @FXML
    private JFXButton bt_cancelar;
    @FXML
    private BorderPane paneprincipal;
    @FXML
    private JFXTextField tf_veiculo;
    @FXML
    private JFXTextField tf_telefone;
    @FXML
    private JFXTextField tf_email;
    @FXML
    private JFXTextField tf_endereco;
    @FXML
    private JFXTextField tf_cep;
    @FXML
    private JFXTextField tf_cpf;
    @FXML
    private AnchorPane info_cliente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        tc_parcela.setCellValueFactory(new PropertyValueFactory<>("param1"));
        tc_valor.setCellValueFactory(new PropertyValueFactory<>("param2_1"));
        tc_data.setCellValueFactory(new PropertyValueFactory<>("param3"));
        
        ObservableList<String> data = FXCollections.observableArrayList("1x", "2x", "3x","4x", "5x", "6x",
                "7x", "8x", "9x","10x", "11x", "12x");
        cb_parcelas.setItems(data);
        
        rb_avista.setToggleGroup(group);
        rb_parcelado.setToggleGroup(group);
        
        inicializa();
    }  
    
    
    private void inicializa()
    {
        MaskFieldUtil.monetaryField(tf_valor);
        MaskFieldUtil.cepField(tf_cep);
        MaskFieldUtil.cpfField(tf_cpf);
        tf_cliente.setText(os.getParam5());
        tf_veiculo.setText(os.getParam6());
        String aux = String.valueOf(valor);
        if(aux.substring(aux.indexOf(".")).length() == 2)
            valor *= 10;
        tf_valor.setText(String.valueOf(valor));
        CtrCliente o = CtrCliente.instancia();
        o.get(tf_cliente, tf_cpf, tf_telefone, tf_email, tf_endereco, tf_cep);
        info_cliente.setDisable(true);
    }

    @FXML
    private void clickFormaPagamento(ActionEvent event)
    {
        if(dp_data.getValue() != null)
        {
            if(rb_avista.isSelected())
            {
                cb_parcelas.setDisable(true);
                Objeto o = new Objeto(String.valueOf(1), String.valueOf(tf_valor.getText()), 
                        String.valueOf(dp_data.getValue()));
                tv_pagmento.setItems(FXCollections.observableArrayList(o));
            }
            else if(rb_parcelado.isSelected())
            {
                cb_parcelas.setDisable(false);
                if(cb_parcelas.getSelectionModel().getSelectedIndex() > 0)
                {
                    ObservableList<Objeto> forma = FXCollections.observableArrayList();
                    int meses = 0;
                    int q = Integer.parseInt(cb_parcelas.getSelectionModel().getSelectedItem().replace("x", ""));
                    double valor  = Double.parseDouble(tf_valor.getText().replace(",", ".")),parcela = valor/q;
                    DecimalFormat df2 = new DecimalFormat("#.##");
                    df2.setRoundingMode(RoundingMode.DOWN);
                    double vparcela = Double.parseDouble(df2.format(parcela).replace(",", ".")),corrente = 0.0;
                    LocalDate data = dp_data.getValue();
                    for (int i = 0; i < q; i++)
                    {
                        if(i + 1 < q)
                            forma.add(new Objeto(String.valueOf(i + 1), String.valueOf(vparcela), 
                            String.valueOf(data)));
                        else
                            forma.add(new Objeto(String.valueOf(i + 1), String.valueOf(df2.format(valor-corrente)), 
                            String.valueOf(data)));
                        corrente+=vparcela;
                        data = data.plusMonths(1);
                    }
                    tv_pagmento.setItems(FXCollections.observableArrayList(forma));
                }
            }
        }
    }


    @FXML
    private void clickConfirmar(ActionEvent event)
    {
        Alert alert;
        if(tv_pagmento.getItems().size() > 0)
        {
            if(new ctrRecebimento().gravar(new ArrayList<>(tv_pagmento.getItems()),tf_cliente))
                alert = new Alert(Alert.AlertType.INFORMATION, "Parcela(s) inserida(s) com sucesso", ButtonType.OK);
            else
                alert = new Alert(Alert.AlertType.ERROR, "Erro na inserção de parcelas", ButtonType.OK);
        }
        else
            alert = new Alert(Alert.AlertType.ERROR, "Por favor, escolha uma data e uma forma de pagamento", ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    private void clickCancelar(ActionEvent event)
    {
    }
    
}
