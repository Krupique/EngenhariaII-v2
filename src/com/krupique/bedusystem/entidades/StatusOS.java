/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.entidades;

import java.sql.ResultSet;
import com.krupique.bedusystem.utilidades.Banco;
import com.krupique.bedusystem.entidades.Funcionário;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Caique
 */
public class StatusOS
{
    private int codigo;
    private Status status;
    private Funcionário funcionarios;
    private Date data;
    private String descrição;

    public StatusOS()
    {
        this(0,null,new Funcionário(),null);
    }
    
    public StatusOS(int codigo)
    {
        this(codigo,null,new Funcionário(),null);
    }

    public StatusOS(int codigo, Status status)
    {
        this.codigo = codigo;
        this.status = status;
        funcionarios = new Funcionário();
        this.data = null;
    }

    public StatusOS(Status status, Funcionário funcionarios)
    {
        this.status = status;
        this.funcionarios = funcionarios;
        this.codigo = 0;
        this.data = null;
    }

    public StatusOS(int codigo, Status status,Funcionário funcionarios,Date data)
    {
        this.codigo = codigo;
        this.status = status;
        this.funcionarios = funcionarios;
        this.data = data;
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

    public Funcionário getFuncionarios()
    {
        return funcionarios;
    }

    public void setFuncionarios(Funcionário funcionarios)
    {
        this.funcionarios = funcionarios;
    }

    public Date getData()
    {
        return data;
    }

    public void setData(Date data)
    {
        this.data = data;
    }

    public String getDescrição()
    {
        return descrição;
    }

    public void setDescrição(String descrição)
    {
        this.descrição = descrição;
    }
    
    public void busca()
    {
        ResultSet rs = Banco.getCon().consultar("select * from status_os where stat_os_codigo = " + codigo);
        
        try
        {
            if(rs != null && rs.next())            
            {
                setStatus(new Status(rs.getInt("stat_codigo")).busca());
                setFuncionarios(new Funcionário(rs.getInt("func_codigo")).get());
                setData(rs.getDate("stat_os_data"));
                setDescrição(rs.getString("stat_os_descricao"));
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(StatusOS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<StatusOS> get(int filtro)
    {
        ArrayList<StatusOS> historico = new ArrayList<>();
        ResultSet rs = Banco.getCon().consultar("select * from status_os where os_codigo = " + filtro);
        
        try
        {
            while(rs != null && rs.next())            
            {
                StatusOS s = new StatusOS(rs.getInt("stat_os_codigo"));
                s.setStatus(new Status(rs.getInt("stat_codigo")).busca());
                s.setFuncionarios(new Funcionário(rs.getInt("func_codigo")).get());
                s.setData(rs.getDate("stat_os_data"));
                s.setDescrição(rs.getString("stat_os_descricao"));
                historico.add(s);
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(StatusOS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return historico;
    }

    public boolean gravar(int os,int status, Date data, String text, int funcionario)
    {
        if(text == null)
            text = "";
        String sql = "insert into status_os(stat_codigo,os_codigo,stat_os_data,stat_os_descricao,"
                + "func_codigo)  values($1,$2,'$3','$4',$5)";
        sql = sql.replace("$1", String.valueOf(status));
        sql = sql.replace("$2", String.valueOf(os));
        sql = sql.replace("$3", String.valueOf(data));
        sql = sql.replace("$4", text);
        sql = sql.replace("$5", String.valueOf(funcionario));
        
        return Banco.getCon().manipular(sql);
    }
}
