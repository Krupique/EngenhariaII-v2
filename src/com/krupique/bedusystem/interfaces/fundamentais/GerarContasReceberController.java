package com.krupique.bedusystem.interfaces.fundamentais;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.controladoras.CtrCliente;
import com.krupique.bedusystem.controladoras.ctrOS;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
    @FXML
    private JFXTextField tf_desconto;
    @FXML
    private JFXTextField tf_numeroParcela;
    @FXML
    private JFXTextField tf_valorParcela;
    @FXML
    private JFXButton btAdd;
    @FXML
    private JFXTextField tf_valorRestante;
    @FXML
    private JFXButton bt_limpar;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        tc_parcela.setCellValueFactory(new PropertyValueFactory<>("param1"));
        tc_valor.setCellValueFactory(new PropertyValueFactory<>("param2_1"));
        tc_data.setCellValueFactory(new PropertyValueFactory<>("param3"));

        ObservableList<String> data = FXCollections.observableArrayList("1x","2x", "3x", "4x", "5x", "6x",
                "7x", "8x", "9x", "10x", "11x", "12x");
        cb_parcelas.setItems(data);

        rb_avista.setToggleGroup(group);
        rb_parcelado.setToggleGroup(group);

        inicializa();
    }

    private void inicializa()
    {
        MaskFieldUtil.monetaryField(tf_valor);
        MaskFieldUtil.monetaryField(tf_valorParcela);
        MaskFieldUtil.monetaryField(tf_valorRestante);
        MaskFieldUtil.cepField(tf_cep);
        MaskFieldUtil.cpfField(tf_cpf);
        MaskFieldUtil.numericField(tf_desconto);
        MaskFieldUtil.numericField(tf_numeroParcela);

        String aux = String.valueOf(valor);
        if (aux.substring(aux.indexOf(".")).length() == 2)
        {
            valor *= 10;
        }
        tf_cliente.setText(os.getParam5());
        tf_veiculo.setText(os.getParam6());
        tf_valor.setText(String.valueOf(valor));
        tf_valorRestante.setText(String.valueOf(valor));
        tf_desconto.setText("" + 5);
        dp_data.setValue(LocalDate.now());

        CtrCliente o = CtrCliente.instancia();
        o.get2(tf_cpf, tf_cliente, tf_telefone, tf_email, tf_endereco, tf_cep);
        info_cliente.setDisable(true);
        
        inicializa_tabela();
    }
    
    public void inicializa_tabela()
    {
        tf_valorRestante.setText(tf_valor.getText());
        rb_avista.setSelected(false);
        rb_parcelado.setSelected(false);
        tf_numeroParcela.setText("1");
        tv_pagmento.setItems(FXCollections.observableArrayList());
        cb_parcelas.getSelectionModel().select(-1);
    }

    @FXML
    private void clickFormaPagamento(ActionEvent event)
    {
        if (dp_data.getValue() != null && dp_data.getValue().compareTo(LocalDate.now()) >= 0)
        {
            if (rb_avista.isSelected())
            {
                inicializa_tabela();
                rb_avista.setSelected(true);
                String v = tf_valor.getText();
                double valor = Double.parseDouble(v.replace(",", ".")),desconto = 1.0;
                cb_parcelas.setDisable(true);
                
                if (Integer.parseInt(tf_desconto.getText()) > 0)
                    desconto = 1.00 - (Double.parseDouble(tf_desconto.getText()) / 100);
                
                DecimalFormat df2 = new DecimalFormat("#.##");
                df2.setRoundingMode(RoundingMode.DOWN);
                valor = Double.parseDouble(df2.format(valor * desconto).replace(",", "."));
                
                Objeto o = new Objeto(String.valueOf(1), String.valueOf(valor),
                        String.valueOf(dp_data.getValue()));
                
                tv_pagmento.setItems(FXCollections.observableArrayList(o));
                tf_valorRestante.setText("0");
            }
            else if (rb_parcelado.isSelected())
            {
                cb_parcelas.setDisable(false);
                if (cb_parcelas.getSelectionModel().getSelectedIndex() >= 0 && !tf_valorRestante.equals("0"))
                {
                    if(tf_valorRestante.getText().equals("0"))
                        clickLimpar(new ActionEvent());
                    ObservableList<Objeto> forma = tv_pagmento.getItems().size() > 0? tv_pagmento.getItems() : FXCollections.observableArrayList();
                    int meses = 0,i,q,max;    
                    
                    i = tv_pagmento.getItems().size() == 0? 0:
                        Integer.parseInt(tv_pagmento.getItems().get(tv_pagmento.getItems().size() - 1).getParam1());
                    q = Integer.parseInt(cb_parcelas.getSelectionModel().getSelectedItem().replace("x", ""));
                    max = q + i;
                    double valor = Double.parseDouble(tf_valorRestante.getText().replace(",", ".")), parcela = valor / q;
                    DecimalFormat df2 = new DecimalFormat("#.##");
                    df2.setRoundingMode(RoundingMode.DOWN);
                    double vparcela = Double.parseDouble(df2.format(parcela).replace(",", ".")), corrente = 0.0;
                    LocalDate data = tv_pagmento.getItems().size() == 0? dp_data.getValue() :
                        LocalDate.parse(tv_pagmento.getItems().get(tv_pagmento.getItems().size() - 1).getParam3()).plusMonths(1);
                    for (; i < max; i++)
                    {
                        if (i + 1 < max)
                        {
                            forma.add(new Objeto(String.valueOf(i + 1), String.valueOf(vparcela),
                                    String.valueOf(data)));
                        }
                        else
                        {
                            valor -= corrente;
                            df2.setRoundingMode(RoundingMode.DOWN);
                            String aux = String.valueOf(Double.parseDouble(df2.format(valor).replace(",",".")));
                            forma.add(new Objeto(String.valueOf(i + 1), aux, String.valueOf(data)));
                        }

                        corrente += vparcela;
                        data = data.plusMonths(1);
                    }
                    tf_valorRestante.setText("0");
                    tv_pagmento.setItems(FXCollections.observableArrayList(forma));
                }
            }
            else if(rb_avista.isSelected() && tv_pagmento.getItems().size() > 0)
                new Alert(Alert.AlertType.ERROR, "Pagamento Parcelado selecionado, limpe as parcelas para selecionar a forma de pagamento à vista", ButtonType.OK).showAndWait();
        }
        else if (dp_data.getValue().compareTo(LocalDate.now()) < 0)
        {
            new Alert(Alert.AlertType.ERROR, "Data inválida", ButtonType.OK).showAndWait();
        }
    }

    @FXML
    private void clickConfirmar(ActionEvent event)
    {
        Alert alert;
        if (tv_pagmento.getItems().size() > 0)
        {
            if (new ctrRecebimento().gravar(new ArrayList<>(tv_pagmento.getItems()), tf_cliente))
            {
                if (new ctrOS().alterar("fechado", "", "", dp_data, Integer.parseInt(os.getParam1())))
                {
                    alert = new Alert(Alert.AlertType.INFORMATION, "Parcela(s) inserida(s) com sucesso", ButtonType.OK);
                }
                else
                {
                    alert = new Alert(Alert.AlertType.ERROR, "Erro na alteração da ordem de serviço", ButtonType.OK);
                }
            }
            else
            {
                alert = new Alert(Alert.AlertType.ERROR, "Erro na inserção de parcelas", ButtonType.OK);
            }
        }
        else
        {
            alert = new Alert(Alert.AlertType.ERROR, "Por favor, escolha uma data e uma forma de pagamento", ButtonType.OK);
        }
        alert.showAndWait();
    }

    @FXML
    private void clickCancelar(ActionEvent event)
    {
    }

    @FXML
    private void btAdd(ActionEvent event)
    {
        Alert alert = null;
        if (!tf_numeroParcela.getText().equals(""))
        {
            if (!tf_valorParcela.getText().equals(""))
            {
                if (Double.parseDouble(tf_valorParcela.getText().replace(".", "").replace(",", "."))
                        <= Double.parseDouble(tf_valorRestante.getText().replace(",", ".")))
                {
                    ObservableList<Objeto> parcelas = tv_pagmento.getItems();
                    int indice = parcelas == null || parcelas.size() > 0 ? Integer.parseInt(parcelas.get(parcelas.size() - 1).getParam1()) : 0;

                    if (indice + 1 == Integer.parseInt(tf_numeroParcela.getText()))
                    {
                        LocalDate data = dp_data.getValue();
                        parcelas.add(new Objeto(tf_numeroParcela.getText(), tf_valorParcela.getText(), String.valueOf(data)));
                        double d = ((Double.parseDouble(tf_valorRestante.getText().replace(",", "."))
                                - Double.parseDouble(tf_valorParcela.getText().replace(",", ".")))) * 10;
                        tf_valorRestante.setText(String.valueOf(d).replace(".", ""));
                        tf_numeroParcela.setText(""+(Integer.parseInt(tf_numeroParcela.getText()) + 1));
                        tf_valorParcela.clear();
                        tf_valorParcela.requestFocus();
                    
                    }
                    else
                    {
                        alert = new Alert(Alert.AlertType.ERROR, "Por favor, insira a parcela " + (indice + 1), ButtonType.OK);
                    }
                }
                else
                {
                    alert = new Alert(Alert.AlertType.ERROR, "Por favor, digite um valor válido(menor que o valor restante) para a parcela", ButtonType.OK);
                }
            }
            else
            {
                alert = new Alert(Alert.AlertType.ERROR, "Por favor, digite um valor para a parcela", ButtonType.OK);
            }
        }
        else
        {
            alert = new Alert(Alert.AlertType.ERROR, "Por favor, digite um número para parcela", ButtonType.OK);
        }
        alert.showAndWait();
    }

    @FXML
    private void alterarDesconto(KeyEvent event)
    {
        if(event.getCode() == KeyCode.BACK_SPACE && tf_desconto.getText().equals("") || Integer.parseInt(tf_desconto.getText()) < 0)
            tf_desconto.setText("0");
        if (Integer.parseInt(tf_desconto.getText()) > 100)
            tf_desconto.setText("100");
        clickFormaPagamento(new ActionEvent());
    }

    public String soma()
    {
        double soma = 0.0;
        for (int i = 0; i < tv_pagmento.getItems().size(); i++)
            soma += Double.parseDouble(tv_pagmento.getItems().get(i).getParam2_1().replace(",", "."));
        return String.valueOf(Double.parseDouble(tf_valor.getText().replace(",", ".")) - soma);
    }

    @FXML
    private void clickLimpar(ActionEvent event)
    {
        inicializa_tabela();
    }
}
