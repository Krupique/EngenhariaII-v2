/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.utilidades;

import javafx.scene.paint.Color;

/**
 *
 * @author Henrique K. Secchi
 */
public class CorSistema {
    private static String corHex = "#347E65";
    private static String corSec = "#204f3f";

    public static String getCorHex() {
        return corHex;
    }

    public static String getCorSec() {
        return corSec;
    }

    public static void setCorHex(String corHex) {
        double percent = 0.7;
        CorSistema.corHex = corHex;
        Color aux = new Color(1, 1, 1, 1);
        try{
            
            aux = hex2Rgb(corHex);
        }catch(Exception er){
            System.out.println("Erro: " + er.getMessage());
        }
        double r, g, b;
        r = aux.getRed(); g = aux.getGreen(); b = aux.getBlue();
        r = r * percent; g = g * percent; b = b * percent;
        
        aux = new Color(r, g, b, 1);
        corSec = toRGBCode(aux);
    }
    
    public static String toRGBCode( Color color ){
        return String.format( "#%02X%02X%02X",
            (int)( color.getRed() * 255 ),
            (int)( color.getGreen() * 255 ),
            (int)( color.getBlue() * 255 ) );
    }
    
    public static Color hex2Rgb(String colorStr){
        
        double r = (double)Integer.valueOf( colorStr.substring( 1, 3 ), 16 ) / 255;
        double g = (double)Integer.valueOf( colorStr.substring( 3, 5 ), 16 ) / 255;
        double b = (double)Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) / 255;
        
        return new Color(r, g, b, 1);
    }
   
}
