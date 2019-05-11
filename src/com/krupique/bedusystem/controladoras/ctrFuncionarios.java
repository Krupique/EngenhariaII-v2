/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.controladoras;

import java.util.ArrayList;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.entidades.Funcionário;
import java.sql.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Caique
 */
public class ctrFuncionarios
{
    public boolean gravar(JFXTextField nome,JFXTextField funcao, JFXTextField telefone, JFXTextField celular, 
            JFXTextField email,JFXRadioButton sexo,JFXTextField rua, JFXTextField bairro, JFXTextField cidade, 
            JFXDatePicker admissao)
    {
        
        Funcionário f = new Funcionário(0, nome.getText(), funcao.getText(), telefone.getText(), celular.getText(), 
                email.getText(), rua.getText(), bairro.getText(), cidade.getText(), Date.valueOf(admissao.getValue()), sexo.getText().equals("Masculino"));
        return f.gravar();
    }
    
    public boolean alterar(JFXTextField nome,JFXTextField funcao, JFXTextField telefone, JFXTextField celular, 
            JFXTextField email,JFXRadioButton sexo,JFXTextField rua, JFXTextField bairro, JFXTextField cidade, 
            JFXDatePicker admissao,int codigo)
    {
        if(telefone.getText() == null)
            telefone.setText("");
        if(celular.getText() == null)
            celular.setText("");
        if(email.getText() == null)
            email.setText("");
        Funcionário f = new Funcionário(codigo, nome.getText(), funcao.getText(), telefone.getText(), celular.getText(), 
                email.getText(), rua.getText(), bairro.getText(), cidade.getText(), Date.valueOf(admissao.getValue()), sexo.getText().equals("Masculino"));
        return f.alterar();
    }
    
    public boolean remover(int codigo)
    {
        Funcionário f = new Funcionário();
        f.setCodigo(codigo);
        return f.remover();
    }
    
    public ArrayList<String> buscaTodos()
    {
        Funcionário f = new Funcionário();
        return f.getNomes();
    }
    
    public ArrayList<Object[]> procurarTodos(JFXTextField filtro,JFXRadioButton rb,JFXDatePicker dp)
    {
        ArrayList<Object[]> usuarios = new ArrayList<>();
        ArrayList<Funcionário> aux = new ArrayList<>();
        Object[] obj;
        if(dp.getValue() != null)
            aux = new Funcionário().getAll(dp.getValue().toString(), "func_dtentrada");
        else if(rb.getText().equals("Nome"))
            aux = new Funcionário().getAll(filtro.getText());
        else if(rb.getText().equals("Função"))
            aux = new Funcionário().getAll(filtro.getText(), "func_funcao");
        
        for(int i = 0; i < aux.size(); i++)
        {
            obj = new Object[13];
            
            obj[0] = aux.get(i).getCodigo();
            obj[1] = aux.get(i).getNome();
            obj[2] = aux.get(i).getFuncao();
            obj[3] = aux.get(i).getTelefone();
            obj[4] = aux.get(i).getCelular();
            obj[5] = aux.get(i).getEmail();
            obj[6] = aux.get(i).getRua();
            obj[7] = aux.get(i).getBairro();
            obj[8] = aux.get(i).getCidade();
            obj[9] = aux.get(i).getEntrada();
            obj[10] = aux.get(i).getSexo();
            obj[11] = aux.get(i).isAtivo();
            obj[12] = aux.get(i).getRua() + "," + aux.get(i).getBairro() + "," + aux.get(i).getCidade();
            usuarios.add(obj);
        }
        
        return usuarios;
    }
    
    public String busca(String filtro)
    {
        Funcionário f = new Funcionário();
        return f.get(filtro).getNome();
    }
    
    public String busca(int filtro)
    {
        Funcionário f = new Funcionário();
        return f.get(filtro).getNome();
    }
    
    public boolean getAtivo(String filtro)
    {
        Funcionário f = new Funcionário();
        return f.get(filtro).isAtivo();
    }
}
