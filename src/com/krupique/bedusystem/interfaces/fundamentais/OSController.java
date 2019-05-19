/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.interfaces.fundamentais;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.controladoras.ctrFuncionarios;
import com.krupique.bedusystem.controladoras.ctrOS;
import com.krupique.bedusystem.controladoras.ctrStatus;
import com.krupique.bedusystem.controladoras.ctrStatusOS;
import com.krupique.bedusystem.utilidades.Objeto;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Henrique K. Secchi
 */
public class OSController implements Initializable
{
    private ArrayList<String> nomes = new ArrayList<>();
    private ArrayList<String> status = new ArrayList<>();
    private final Tooltip t = new Tooltip();
    public static int cod,acao;
    
    @FXML
    private JFXTextField tf_codigo;
    @FXML
    private JFXTextField tf_funcionario;
    @FXML
    private JFXComboBox<String> cb_status;
    @FXML
    private JFXTextArea ta_descricao;
    @FXML
    private DatePicker dp_data;
    @FXML
    private TableView<Objeto> tv_historico;
    @FXML
    private TableColumn<Object, String> tc_statusHistorico;
    @FXML
    private TableColumn<Object, String> tc_dataHistorico;
    @FXML
    private TableColumn<Object, String> tc_funcionarioHistorico;
    @FXML
    private TableView<Objeto> tv_os;
    @FXML
    private TableColumn<Object, String> tc_os;
    @FXML
    private TableColumn<Object, String> tc_cliente;
    @FXML
    private TableColumn<Object, String> tc_veiculo;
    @FXML
    private TableColumn<Object, String> tc_status;
    @FXML
    private TableColumn<Object, String> tc_data;
    @FXML
    private TableColumn<Object, String> tc_descricao;
    @FXML
    private JFXButton btIniciar;
    @FXML
    private JFXButton btAlterar;
    @FXML
    private JFXButton btFinalizar;
    @FXML
    private JFXButton btCancelar;
    @FXML
    private JFXButton btHelp;
    @FXML
    private JFXButton btConfirmar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        t.setText("Não achou o status? Clique aqui para adiciona-lo");
        btHelp.setTooltip(t);
        
        tc_data.setCellValueFactory(new PropertyValueFactory<>("param2"));
        tc_descricao.setCellValueFactory(new PropertyValueFactory<>("param3"));
        tc_cliente.setCellValueFactory(new PropertyValueFactory<>("param5"));
        tc_status.setCellValueFactory(new PropertyValueFactory<>("param7"));
        tc_veiculo.setCellValueFactory(new PropertyValueFactory<>("param6"));
        tc_os.setCellValueFactory(new PropertyValueFactory<>("param1"));
        tc_dataHistorico.setCellValueFactory(new PropertyValueFactory<>("param2"));
        tc_funcionarioHistorico.setCellValueFactory(new PropertyValueFactory<>("param3"));
        tc_statusHistorico.setCellValueFactory(new PropertyValueFactory<>("param1"));
        tf_codigo.setDisable(true);
        
        inicializa();
    }
    
    private void inicializa()
    {
        nomes = new ctrFuncionarios().buscaTodos();
        TextFields.bindAutoCompletion(tf_funcionario, nomes);
        
        ObservableList<String> data = FXCollections.observableArrayList(new ctrStatus().buscaTodos());
        cb_status.setItems(data);
        cb_status.getSelectionModel().select(0);
        
        altera_campos(true,true, true, true, true);
        cod = -1;
        acao = 0;
        limpa();
        procura(0);
        btFinalizar.setText("Finalizar");
        btIniciar.setText("Iniciar");
    }
    
    public void limpa()
    {
        tf_funcionario.clear(); cb_status.getSelectionModel().select(0); ta_descricao.clear(); dp_data.setValue(LocalDate.now());
    }
    
    public void altera_campos(boolean b1,boolean b2,boolean b3,boolean b4,boolean b5)
    {
        tf_funcionario.setDisable(b1);
        ta_descricao.setDisable(b1);
        cb_status.setDisable(b1);
        dp_data.setDisable(b1);
        btConfirmar.setDisable(b2);
        btIniciar.setDisable(b3);
        btAlterar.setDisable(b4);
        btFinalizar.setDisable(b5);
    }
    
    private void procura(int filtro)
    {
        ArrayList<Object[]> list;
        ArrayList<Objeto> obsList = new ArrayList<>();
        Objeto obj;
        
        list = new ctrOS().procurar(filtro);
        for(int i = 0; i < list.size();i++)
        {
            obj = new Objeto(String.valueOf(list.get(i)[0]),String.valueOf(list.get(i)[1]),(String)list.get(i)[2],
                    String.valueOf(list.get(i)[3]),(String)list.get(i)[4],(String)list.get(i)[5],(String)list.get(i)[6]);
            obsList.add(obj);
        }
        tv_os.setItems(FXCollections.observableArrayList(obsList));
    }
    
    private void recuperaHistorico(int filtro)
    {
        ArrayList<Object[]> list;
        ArrayList<Objeto> obsList = new ArrayList<>();
        Objeto obj;
        int i;
        
        list = new ctrStatusOS().procurar(filtro);
        for(i = 0; i < list.size();i++)
        {
            obj = new Objeto(String.valueOf(list.get(i)[0]),String.valueOf(list.get(i)[1]),(String)list.get(i)[2]);
            obsList.add(obj);
        }
        tf_funcionario.setText((String)list.get(i-1)[2]);
        tv_historico.setItems(FXCollections.observableArrayList(obsList));
    }
    
    private String valida_campos()
    {
        String erro = "";
        if(!nomes.contains(tf_funcionario.getText()))
            erro = "Funcionário não cadastrado";
        else if(dp_data.getValue().compareTo(LocalDate.now()) > 0)
            erro = "Data inválida";
        return erro;
    }

    @FXML
    private void clickIniciar(ActionEvent event)
    {
        altera_campos(false, false, true, true, true);
        cb_status.getSelectionModel().select("iniciado");
        acao = 1;
    }

    @FXML
    private void clickAlterar(ActionEvent event)
    {
        acao = 2;
        altera_campos(false, false, true, true, true);
    }

    @FXML
    private void clickFinalizar(ActionEvent event)
    {
        acao = 3;
        altera_campos(true, false, true, true, true);
    }

    @FXML
    private void clickCancelar(ActionEvent event)
    {
        inicializa();
    }

    @FXML
    private void clickHelp(ActionEvent event)
    {
    }

    @FXML
    private void clickTabela(MouseEvent event)
    {
        if(event.getClickCount() == 2 && tv_os.getSelectionModel().getSelectedItem() != null)
        {
            
            if(tv_os.getSelectionModel().getSelectedItem().getParam7().equals("não iniciado"))
                altera_campos(true,true, false, true, true);
            else
                altera_campos(true,true, true, false, false);
            
            cod = Integer.parseInt(tv_os.getSelectionModel().getSelectedItem().getParam1());
            tf_codigo.setText(String.valueOf(cod));
            cb_status.getSelectionModel().select(tv_os.getSelectionModel().getSelectedItem().getParam7());
            ta_descricao.setText(tv_os.getSelectionModel().getSelectedItem().getParam3());
            if(tv_os.getSelectionModel().getSelectedItem().getParam2() != null)
                dp_data.setValue(LocalDate.parse(tv_os.getSelectionModel().getSelectedItem().getParam2()));
            recuperaHistorico(cod);
            
            if(cb_status.getSelectionModel().getSelectedItem().equals("finalizado"))
            {
                btFinalizar.setText("Fechar OS");
                altera_campos(true,true, true, true, false);
            }
            else
                btFinalizar.setText("Finalizar");
        }
    }

    @FXML
    private void clickConfirmar(ActionEvent event)
    {
        boolean flag = true;
        String op = cb_status.getSelectionModel().getSelectedItem();
        if(acao == 1 && (op.equals("não iniciado") || op.equals("finalizado")))
            flag = false;
        if(acao == 2 && op.equals("não iniciado"))
            flag = false;
        if(acao == 2 && op.equals("finalizado"))
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Alterar a Ordem de Serviço com Status finalizado irá finalizar a manutenção. Deseja Continuar?");

            ButtonType btn_yes = new ButtonType("Yes");
            ButtonType btn_no = new ButtonType("No");

            alert.getButtonTypes().setAll(btn_yes,btn_no);

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == btn_no)
                flag  = false;
        }
        
        Alert alert;
        if(flag && new ctrOS().alterar(cb_status, tf_funcionario, ta_descricao, dp_data, cod))
        {
            alert = new Alert(Alert.AlertType.INFORMATION, "OS alterada com sucesso", ButtonType.NO);
            clickCancelar(event);
        }
        else
            alert = new Alert(Alert.AlertType.ERROR, "Erro na alteração da OS", ButtonType.OK);
        alert.showAndWait();
    }

}
