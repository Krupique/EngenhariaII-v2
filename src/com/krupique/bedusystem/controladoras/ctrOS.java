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
import com.krupique.bedusystem.entidades.Funcionário;
import com.krupique.bedusystem.entidades.OS;
import com.krupique.bedusystem.entidades.Orçamento;
import com.krupique.bedusystem.entidades.Status;
import com.krupique.bedusystem.entidades.StatusOS;
import java.sql.Date;
import java.util.ArrayList;
import javafx.scene.control.DatePicker;

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
    
    public boolean alterar(JFXComboBox cb, JFXTextField funcionario, JFXTextArea desc, DatePicker data,int cod)
    {
        StatusOS s = new StatusOS();
        Status sta = new Status().buscaStatus(cb.getSelectionModel().getSelectedItem().toString());
        return s.gravar(cod,sta.getCodigo(),Date.valueOf(data.getValue()),desc.getText(),
                new Funcionário().get_nome(funcionario.getText()).getCodigo());
    }
    
    public ArrayList<Object[]> procurar(int filtro)
    {
        ArrayList<Object[]> usuarios = new ArrayList<>();
        ArrayList<OS> aux = new ArrayList<>();
        ArrayList<StatusOS> historico = new ArrayList<>();
        Object[] obj,object,object2;
        Status s;
        
        aux = new OS().busca(filtro);
        
        for (int i = 0; i < aux.size(); i++)
        {
            obj = new Object[9];

            obj[0] = aux.get(i).getCodigo();
            obj[1] = aux.get(i).getData();
            obj[2] = aux.get(i).getDescricao();
            obj[3] = aux.get(i).getOrcamento().getCodigo();
            obj[4] = aux.get(i).getOrcamento().getCliente().getNome();
            obj[5] = aux.get(i).getOrcamento().getVeiculo().getVei_placa();
            s = aux.get(i).getMax().getStatus();
            if(s != null)
            {
                obj[6] = aux.get(i).getMax().getStatus().getDescricao();
                obj[7] = aux.get(i).getMax().getFuncionarios().getNome();
            }
            else
            {
                obj[6] = "";
                obj[7] = "";
            }
            obj[8] = aux.get(i).getOrcamento().getValorTotal();
            usuarios.add(obj);
        }
        return usuarios;
    }
}
