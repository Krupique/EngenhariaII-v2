/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.utilidades.estilo;

import javafx.scene.layout.Pane;

/**
 *
 * @author Henrique K. Secchi
 */
public class Estilo {
    
    public static void setEstiloPane(Pane folder, Pane pane, String corFolder, String corPane)
    {
        folder.setStyle("-fx-background-color:" + corFolder + ";"
                + " -fx-background-radius:7px;"
                + " -fx-border-color:d1d1d1;"
                + " -fx-border-radius:5px;"
                + " -fx-border-width:2px;");
        
        pane.setStyle("-fx-background-color:" + corPane + "; "
                + " -fx-background-radius:7px;"
                + " -fx-border-color:d1d1d1;"
                + " -fx-border-radius:5px;");
    }
}
