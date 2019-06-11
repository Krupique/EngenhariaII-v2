/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.interfaces.basicas;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.controladoras.ctrFuncionarios;
import com.krupique.bedusystem.controladoras.ctrUsuario;
import com.krupique.bedusystem.utilidades.CorSistema;
import com.krupique.bedusystem.utilidades.Objeto;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Henrique K. Secchi
 */
public class CadUsuarioController implements Initializable
{
    private final ToggleGroup group = new ToggleGroup();
    private ArrayList<String> nomes = new ArrayList<>();
    private int acao;
    @FXML
    private HBox paneBotoes;
    @FXML
    private JFXButton btNovo;
    @FXML
    private JFXButton btSalvar;
    @FXML
    private JFXButton btAlterar;
    @FXML
    private JFXButton btExcluir;
    @FXML
    private JFXButton btCancelar;
    @FXML
    private JFXButton btBuscar;
    @FXML
    private JFXButton btVoltar;
    @FXML
    private BorderPane paneprincipal;
    @FXML
    private JFXTextField tf_nome;
    @FXML
    private JFXTextField tf_login;
    @FXML
    private TableView<Objeto> tv_usuarios;
    @FXML
    private TableColumn<Object, String> tc_nome;
    @FXML
    private TableColumn<Object, String> tc_login;
    @FXML
    private JFXTextField tf_busca;
    @FXML
    private JFXRadioButton rb_nome;
    @FXML
    private JFXRadioButton rb_login;
    @FXML
    private JFXRadioButton rb_nivel;
    @FXML
    private JFXComboBox<String> cb_nivel;
    @FXML
    private TableColumn<Object, String> tc_nivel;
    @FXML
    private JFXPasswordField pf_senha;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        inicializaEstilo();
        nomes = new ctrFuncionarios().buscaTodosSemAdmin();
        
        TextFields.bindAutoCompletion(tf_nome, nomes);

        rb_nome.setSelected(true);
        rb_nome.setToggleGroup(group);
        rb_login.setToggleGroup(group);
        rb_nivel.setToggleGroup(group);

        ObservableList<String> data = FXCollections.observableArrayList("Alto", "Medio", "Baixo");
        cb_nivel.setItems(data);
        cb_nivel.getSelectionModel().select(0);

        tc_login.setCellValueFactory(new PropertyValueFactory<>("param1"));
        tc_nivel.setCellValueFactory(new PropertyValueFactory<>("param2"));
        tc_nome.setCellValueFactory(new PropertyValueFactory<>("param3"));
        
        inicializa();
    }
    
    private void inicializa()
    {
        acao = 0;
        limpa();
        altera_campos(true, true, false, true, true);
        evtBuscar(new ActionEvent());
    }
    
    private void altera_campos(boolean b1, boolean b2,boolean b3,boolean b4,boolean b5)
    {
        tf_nome.setDisable(b1);
        cb_nivel.setDisable(b1);
        tf_login.setDisable(b1);
        pf_senha.setDisable(b1);
        btSalvar.setDisable(b2);
        btNovo.setDisable(b3);
        btAlterar.setDisable(b4);
        btExcluir.setDisable(b5);
    }
    
    private void limpa()
    {
        tf_busca.clear();
        tf_login.clear();
        tf_nome.clear();
        pf_senha.clear();
        btAlterar.setText("Alterar");
        btNovo.setText("Novo");
    }
    
    private String valida_campos()
    {
        String erro = "";
        
        if(!tf_nome.getText().equals(""))
        {
            if(!tf_login.getText().equals(""))
            {
                if(pf_senha.getText().equals(""))
                    erro = "Campo Senha obrigatório";
            }    
            else
                erro = "Campo Login obrigatório";
        }  
        else
            erro = "Campo Nome obrigatório";
        return erro;
    }

    //################################# PARTE DE ESTILO DA TELA #################################//
    private void inicializaEstilo()
    {
        String cor = CorSistema.getCorHex();
        btNovo.setStyle("-fx-background-color: " + cor);
        btSalvar.setStyle("-fx-background-color: " + cor);
        btAlterar.setStyle("-fx-background-color: " + cor);
        btExcluir.setStyle("-fx-background-color: " + cor);
        btCancelar.setStyle("-fx-background-color: " + cor);
        btBuscar.setStyle("-fx-background-color: " + cor);
        btVoltar.setStyle("-fx-background-color: " + cor);
    }

    //################################# PARTE DE FUNCIONALIDADES DA TELA #################################//
    @FXML
    private void evtNovo(ActionEvent event)
    {
        acao = 1;
        altera_campos(false, false, true, true, true);
    }

    @FXML
    private void evtSalvar(ActionEvent event)
    {
        if(acao == 1)//novo
        {
            Alert alert;
            String res = valida_campos();
            if(res.equals(""))
            {
                boolean ativo = new ctrFuncionarios().getAtivo(tf_nome.getText());
                if(nomes.contains(tf_nome.getText()) && ativo)
                {
                    ArrayList<Object[]> list = new ctrUsuario().procurar(rb_nome, tf_nome);
                    if(list.isEmpty())
                    {
                            list = new ctrUsuario().procurar(rb_login, tf_login);
                            if(list.isEmpty())
                            {
                                    if(new ctrUsuario().gravar(tf_nome, tf_login, pf_senha, cb_nivel))
                                    {
                                        alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setContentText("Usuario adicionado com suceso");
                                        evtCancelar(event);
                                        inicializa();
                                    }
                                    else
                                        alert = new Alert(Alert.AlertType.ERROR, "Erro na adição do usuário", ButtonType.NO);
                            }
                            else
                                alert = new Alert(Alert.AlertType.ERROR, "Login já existente", ButtonType.NO);                
                    }
                    else
                        alert = new Alert(Alert.AlertType.ERROR, "Já existe um usuário vinculado ao funcionário", ButtonType.NO);
                }
                else
                    alert = new Alert(Alert.AlertType.ERROR, "Funcionário inexistente ou inativo", ButtonType.OK);
            }
            else
                alert = new Alert(Alert.AlertType.ERROR, res, ButtonType.NO);
            alert.showAndWait();
        }
        else if(acao == 2)//alterar
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Deseja alterar " + tf_nome.getText() + "?");
            alert.setTitle("Alteração");

            ButtonType btn_yes = new ButtonType("Yes");
            ButtonType btn_no = new ButtonType("No");

            alert.getButtonTypes().setAll(btn_yes,btn_no);

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == btn_yes)
            {
                String res = valida_campos();
                if(res.equals("") &&  new ctrUsuario().alterar(tf_nome, tf_login, pf_senha, cb_nivel))
                {
                    alert = new Alert(Alert.AlertType.INFORMATION,"Alteração concluida com sucesso",ButtonType.OK);
                    evtCancelar(event);
                    inicializa();
                }
                else if(!res.equals(""))
                    alert = new Alert(Alert.AlertType.ERROR,res,ButtonType.OK);
                else
                {
                    boolean repetiu = false;
                    for (int i = 0; i < tv_usuarios.getItems().size() && !repetiu; i++)
                        repetiu = tv_usuarios.getItems().get(i).getParam1().equals(tf_login.getText());
                    
                    if(repetiu)
                        res = "Login ja existente, por favor escolha outro";
                    else
                        res = "Erro na alteração, por favor revise os campos";
                    alert = new Alert(Alert.AlertType.ERROR,res,ButtonType.OK);
                }
                    
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void evtAlterar(ActionEvent event)
    {
        acao = 2;
        altera_campos(false, false, true, true, true);
    }

    @FXML
    private void evtExcluir(ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Deseja remover " + tf_nome.getText() + "?");
        alert.setTitle("Remoção");
        
        ButtonType btn_yes = new ButtonType("Yes");
        ButtonType btn_no = new ButtonType("No");
        
        alert.getButtonTypes().setAll(btn_yes,btn_no);
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == btn_yes)
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            if(new ctrUsuario().remover(tf_login))
            {
                alert.setContentText("Exclusão concluida com sucesso");
                evtCancelar(event);
                inicializa();
            }
            else
                alert.setContentText("Erro na exclusão do usuário");
            alert.showAndWait();
        }      
    }

    @FXML
    private void evtCancelar(ActionEvent event)
    {
        inicializa();
    }

    @FXML
    private void evtBuscar(ActionEvent event)
    {
        ArrayList<Object[]> list;
        ArrayList<Objeto> obsList = new ArrayList<>();
        Objeto obj;
        //ObservableList<Usuario> usuarios;
        if(rb_login.isSelected())
            list = new ctrUsuario().procurar(rb_login, tf_busca);
        else if(rb_nivel.isSelected())
        {
            tf_busca.setText(tf_busca.getText().substring(0, 1).toUpperCase() + tf_busca.getText().substring(1));
            list = new ctrUsuario().procurar(rb_nivel, tf_busca);
        }    
        else
            list = new ctrUsuario().procurar(rb_nome, tf_busca);
        
        for (int i = 0; i < list.size(); i++) 
        { 
            obj = new Objeto((String)list.get(i)[0], String.valueOf(list.get(i)[1]), (String)list.get(i)[2]);
            obsList.add(obj);    
        }
       
        tv_usuarios.setItems(FXCollections.observableArrayList(obsList));
    }

    @FXML
    private void evtVoltar(ActionEvent event)
    {
    }

    @FXML
    private void clickTabela(MouseEvent event)
    {
        if(event.getClickCount() == 2 && tv_usuarios.getSelectionModel().getSelectedItem() != null)
        {
            altera_campos(true, true, true, false, false);
            tf_login.setText(tv_usuarios.getSelectionModel().getSelectedItem().getParam1());
            tf_nome.setText(tv_usuarios.getSelectionModel().getSelectedItem().getParam3());
            switch(tv_usuarios.getSelectionModel().getSelectedItem().getParam2())
            {
                case "0":
                    cb_nivel.getSelectionModel().select(0);
                    break;
                case "1":
                    cb_nivel.getSelectionModel().select(1);
                    break;
                case "2":
                    cb_nivel.getSelectionModel().select(2);
                    break;
            }
            pf_senha.setText(new ctrUsuario().getSenha(tf_nome));
        }
    }

}
