/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.controladoras;

import com.krupique.bedusystem.entidades.Classificacao;
import java.util.ArrayList;

/**
 *
 * @author Henrique K. Secchi
 */
public class CtrClassificacao {
    private Classificacao classif;
    
    public ArrayList<Object[]> buscar()
    {
        ArrayList<Object[]> list = new ArrayList<>();
        ArrayList<Classificacao> listAux;
        Object[] obj;
        
        classif = new Classificacao();
        listAux = classif.buscar();
        for (int i = 0; i < listAux.size(); i++) {
            obj = new Object[2];
            obj[0] = listAux.get(i).getCod();
            obj[1] = listAux.get(i).getDescricao();
            
            list.add(obj);
        }
        
        return list;
    }
}
