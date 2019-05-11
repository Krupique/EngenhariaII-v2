/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.controladoras;

import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.entidades.Status;
import java.util.ArrayList;

/**
 *
 * @author Caique
 */
public class ctrStatus
{
    public boolean gravar(JFXTextField status)
    {
        Status s = new Status();
        s.setDescricao(status.getText());
        return s.gravar();
    }
    
    public boolean alterar(JFXTextField status,int cod)
    {
        Status s = new Status(cod,status.getText());
        return s.alterar();
    }
    
    public boolean remover(int cod)
    {
        Status s = new Status(cod);
        return s.alterar();
    }
    
    public ArrayList<String> buscaTodos()
    {
        Status s = new Status();
        return s.buscaTodosDescricao();
    }
    
    public ArrayList<Object[]> buscaTodosS()
    {
        ArrayList<Object[]> status = new ArrayList<>();
        ArrayList<Status> aux = new ArrayList<>();
        Object[] obj;
        aux = new Status().buscaTodos();
        for (int i = 0; i < aux.size(); i++)
        {
            obj = new Object[2];
            
            obj[0] = aux.get(i).getCodigo();
            obj[1] = aux.get(i).getDescricao();
            status.add(obj);
        }
        return status;
    }
}
