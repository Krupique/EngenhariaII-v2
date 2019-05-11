/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.krupique.bedusystem.utilidades.Banco;

/**
 *
 * @author Caique
 */
public class StatusOS
{
    private int codigo;
    private Status status;
    private OS os;
    private ArrayList<Funcionário> funcionarios;

    public StatusOS()
    {
        this(0,null,null,new ArrayList<Funcionário>());
    }

    public StatusOS(int codigo, Status status, OS os)
    {
        this.codigo = codigo;
        this.status = status;
        this.os = os;
        funcionarios = new ArrayList<>();
    }

    public StatusOS(Status status, OS os, ArrayList<Funcionário> funcionarios)
    {
        this.status = status;
        this.os = os;
        this.funcionarios = funcionarios;
        this.codigo = 0;
    }

    public StatusOS(int codigo, Status status, OS os, ArrayList<Funcionário> funcionarios)
    {
        this.codigo = codigo;
        this.status = status;
        this.os = os;
        this.funcionarios = funcionarios;
    } 

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public OS getOs()
    {
        return os;
    }

    public void setOs(OS os)
    {
        this.os = os;
    }

    public ArrayList<Funcionário> getFuncionarios()
    {
        return funcionarios;
    }

    public void setFuncionarios(ArrayList<Funcionário> funcionarios)
    {
        this.funcionarios = funcionarios;
    }
    
    public void addFuncionario(Funcionário f)
    {
        if(!this.funcionarios.contains(f))
            this.funcionarios.add(f);
    }
    
    public ArrayList<Funcionário> buscaFuncionarios()
    {
        ArrayList<Funcionário> func = new ArrayList<>();
        ResultSet rs = Banco.getCon().consultar("select * from funcionarios_stat_os where stat_os_codigo = " + codigo
                + " AND stat_codigo = " + status.getCodigo() + " AND os_codigo = " + os.getCodigo());
        
        try
        {
            while(rs != null && rs.next())            
            {
                Funcionário f = new Funcionário(rs.getInt("func_codigo")).get();
                func.add(f);
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(StatusOS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return func;
    }
}
