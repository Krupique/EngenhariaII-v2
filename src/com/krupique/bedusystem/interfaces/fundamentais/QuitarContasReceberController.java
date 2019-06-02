
package com.krupique.bedusystem.interfaces.fundamentais;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.controladoras.ctrParcelaRecebimento;
import com.krupique.bedusystem.controladoras.ctrRecebimento;
import com.krupique.bedusystem.entidades.Cliente;
import com.krupique.bedusystem.entidades.Funcionário;
import com.krupique.bedusystem.entidades.ParcelaRecebimento;
import com.krupique.bedusystem.entidades.Recebimento;
import static com.krupique.bedusystem.interfaces.fundamentais.TelaGenOrcamentoController.stage;
import com.krupique.bedusystem.utilidades.MaskFieldUtil;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class QuitarContasReceberController implements Initializable {

    @FXML
    private BorderPane paneprincipal;
    @FXML
    private JFXButton btPagar;
    @FXML
    private JFXButton brRemover;
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
    private TableColumn<Recebimento, Funcionário> colfunc;
    @FXML
    private TableView<ParcelaRecebimento> tab_par;
    @FXML
    private TableColumn<ParcelaRecebimento, Integer> colcodigop;
    @FXML
    private TableColumn<ParcelaRecebimento, Integer> colnum;
    @FXML
    private TableColumn<ParcelaRecebimento, Date> colvenc;
    @FXML
    private TableColumn<ParcelaRecebimento, Date> colpagamento;
    @FXML
    private TableColumn<ParcelaRecebimento, Cliente> colcliente;
    @FXML
    private TableColumn<ParcelaRecebimento, Double> colvalor;
    @FXML
    private TableColumn<Recebimento, Double> coltotal;
    @FXML
    private ToggleGroup Group;
    @FXML
    private JFXRadioButton rb_cliente;
    @FXML
    private JFXRadioButton rb_dia;
    @FXML
    private JFXRadioButton rb_atedia;
    @FXML
    private JFXDatePicker dtFinal;
    @FXML
    private Label lbinicio;
    @FXML
    private Label lbfinal;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        colcodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        coldescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        coldata.setCellValueFactory(new PropertyValueFactory<>("data"));
        coltotal.setCellValueFactory(new PropertyValueFactory<Recebimento, Double>("valor"));
        colfunc.setCellValueFactory(new PropertyValueFactory<Recebimento, Funcionário>("funcionario"));
        
        colcodigop.setCellValueFactory(new PropertyValueFactory<ParcelaRecebimento, Integer>("codigo"));
        colnum.setCellValueFactory(new PropertyValueFactory<ParcelaRecebimento, Integer>("numero"));
        colvenc.setCellValueFactory((new PropertyValueFactory<ParcelaRecebimento, Date>("vencimento")));
        colvalor.setCellValueFactory(new PropertyValueFactory<ParcelaRecebimento, Double>("valor"));
        colpagamento.setCellValueFactory(new PropertyValueFactory<ParcelaRecebimento, Date>("pagamento"));
        colcliente.setCellValueFactory(new PropertyValueFactory<ParcelaRecebimento, Cliente>("cliente"));
        lbfinal.setText("Data Final:");
        lbinicio.setText("Data Incial:");
        CarregaTabelaRecebimento("");
        rb_dia.setSelected(true);
        btPagar.setDisable(true);
        brRemover.setDisable(true);
        dtBusca.setValue(LocalDate.now());
        dtFinal.setVisible(false);
        
    }  
    
    private void CarregaTabelaRecebimento(String string)
    {
        btPagar.setDisable(true);
        brRemover.setDisable(true);
        tab_reb.getItems().clear();
        tab_par.getItems().clear();
        tab_reb.setItems(ctrRecebimento.instancia().getTodos());
    }
    private void CarregaTabelaParcela(int codigo)
    {

        tab_par.getItems().clear();
        tab_par.setItems(ctrParcelaRecebimento.instancia().get(codigo));
    }


    @FXML
    private void evtPagar(ActionEvent event) 
    {
        String res ="";
        double valorpg;
        TextField tx = new TextField();
         TextInputDialog a = new TextInputDialog(); 
         a.setTitle("Pagamento");
         a.setHeaderText("Digite o valor que deseja pagar!!!");  
         a.setContentText("Valor:");
         tx = a.getEditor();
         MaskFieldUtil.monetaryField(tx);
         Optional <String> resultado =  a.showAndWait();
    
         if(resultado.isPresent())
         {
            res = resultado.get();
            res = res.replace(".", "");
            res = res.replace(",",".");

            valorpg = Double.parseDouble(res);
             System.out.println(valorpg);
         }
    }

    @FXML
    private void evtRemover(ActionEvent event) 
    {
        
    }


    @FXML
    private void evtBuscar(ActionEvent event) 
    {
        if(rb_cliente.isSelected())
        {
            tab_reb.getItems().clear();
            if(!txtBusca.getText().isEmpty())
            tab_reb.setItems(ctrRecebimento.instancia().getCliente(txtBusca.getText()));
            else
            CarregaTabelaRecebimento("");
            
        }
        else if(rb_dia.isSelected())
        {
            tab_reb.getItems().clear();
            tab_reb.setItems(ctrRecebimento.instancia().getDia(dtBusca.getValue()));
        }
        else if(rb_atedia.isSelected())
        {
            tab_reb.getItems().clear();
            if(!Period.between(dtBusca.getValue(), dtFinal.getValue()).isNegative())
                tab_reb.setItems(ctrRecebimento.instancia().getAte(dtBusca.getValue(),dtFinal.getValue()));
           else
            {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Data Inicial não pode ser maior do que a Final!!!");
            a.show();
            dtBusca.setValue(LocalDate.now().plusDays(1));
            }
               
        }
    }

    @FXML
    private void evtVoltar(ActionEvent event) throws IOException 
    {

            Stage stage = (Stage)paneprincipal.getScene().getWindow();
            stage.setResizable(false);

            Parent root = FXMLLoader.load(getClass().getResource("/com/krupique/bedusystem/inicio/TelaDashboard.fxml"));
            paneprincipal.getChildren().clear();
            paneprincipal.getChildren().add(root);
    }

    @FXML
    private void evtMudaData(ActionEvent event) 
    {


    }

    @FXML
    private void evtClickRecebimento(MouseEvent event) 
    {
        CarregaTabelaParcela(tab_reb.getSelectionModel().getSelectedItem().getCodigo());


    }

    @FXML
    private void evtCliente(ActionEvent event) 
    {
        dtBusca.setVisible(false);
        txtBusca.setVisible(true);
        dtFinal.setVisible(false);
        lbinicio.setVisible(false);
        lbfinal.setVisible(false);
    }

    @FXML
    private void evtDia(ActionEvent event) 
    {
        dtBusca.setVisible(true);
        txtBusca.setVisible(false);
        dtFinal.setVisible(false);
        lbinicio.setVisible(false);
        lbfinal.setVisible(false);
    }

    @FXML
    private void evtPeriodo(ActionEvent event) 
    {
        dtBusca.setVisible(true);
        txtBusca.setVisible(false);
        dtFinal.setVisible(true);
        lbinicio.setVisible(true);
        lbfinal.setVisible(true);
        dtFinal.setValue(LocalDate.now().plusDays(1));
        
    }

    @FXML
    private void evtClickParcela(MouseEvent event)
    {
        btPagar.setDisable(false);
        brRemover.setDisable(false);
    }
    
}
