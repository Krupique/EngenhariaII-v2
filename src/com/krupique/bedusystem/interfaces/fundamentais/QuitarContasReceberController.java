
package com.krupique.bedusystem.interfaces.fundamentais;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void evtPagar(ActionEvent event) {
    }

    @FXML
    private void evtRemover(ActionEvent event) {
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
    }

    @FXML
    private void evtBuscar(ActionEvent event) {
    }

    @FXML
    private void evtVoltar(ActionEvent event) {
    }
    
}
