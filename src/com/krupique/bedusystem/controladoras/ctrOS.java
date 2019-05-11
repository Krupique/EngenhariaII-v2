/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.controladoras;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.entidades.OS;
import java.util.ArrayList;

/**
 *
 * @author Caique
 */
public class ctrOS
{
    public boolean alterar(JFXComboBox cb, JFXTextField funcionario, JFXTextArea desc, JFXDatePicker data,int cod)
    {
        /*OS o= new OS(cod, desc.getText(), Date.valueOf(data.getValue()), new Funcionário().get_nome(funcionario.getText()), 
                new Status().buscaStatus(cb.getSelectionModel().getSelectedItem().toString()));
        return o.alterar();*/
        return true;
    }
    
    public ArrayList<Object[]> procurar()
    {
        ArrayList<Object[]> usuarios = new ArrayList<>();
        ArrayList<OS> aux = new ArrayList<>();
        Object[] obj;
        Object[] objAux = null;
        Object[] objAux2 = null;
        int tipo;
        
        OS o = new OS().busca(1);
        
        obj = new Object[7 + o.getStatus().size()];
            
        obj[0] = o.getCodigo();
        obj[1] = o.getData();
        obj[2] = o.getDescricao();
        obj[3] = o.getOrcamento().getCodigo();
        obj[4] = o.getOrcamento().getCliente().getNome();
        obj[5] = o.getOrcamento().getVeiculo().getVei_placa();
        
        for (int i = 0; i < o.getStatus().size(); i++)
        {
            objAux = new Object[2 + o.getStatus().size()];
            
            objAux[0] = o.getStatus().get(i).getCodigo();
            objAux[1] = o.getStatus().get(i).getStatus().getCodigo();
            
            for (int j = 0; j < o.getStatus().get(i).getFuncionarios().size(); j++)
            {
                objAux2 = new Object[2];
                
                objAux2[0] = o.getStatus().get(i).getFuncionarios().get(j).getCodigo();
                objAux2[1] = o.getStatus().get(i).getFuncionarios().get(j).getNome();
                objAux[j + 2] = objAux2;
            }
            obj[6 + i] = objAux;
        }
        
        
        return usuarios;
    }
}
