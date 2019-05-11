package com.krupique.bedusystem.entidades;

import com.krupique.bedusystem.utilidades.Banco;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OS
{
    private int codigo;
    private String descricao;
    private Date data;
    private ArrayList<StatusOS> status;
    private Orçamento orcamento;
    
    public OS()
    {
        this(0,"",null,new ArrayList<StatusOS>(),null);
    }

    public OS(int codigo, String descricao, Date data)
    {
        this.codigo = codigo;
        this.descricao = descricao;
        this.data = data;
        this.status = new ArrayList<>();
    }

    public OS(int codigo, String descricao, Date data, ArrayList<StatusOS> status, Orçamento orcamento)
    {
        this.codigo = codigo;
        this.descricao = descricao;
        this.data = data;
        this.status = status;
        this.orcamento = orcamento;
    }

    
    
    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    public String getDescricao()
    {
        return descricao;
    }

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }

    public Orçamento getOrcamento()
    {
        return orcamento;
    }

    public void setOrcamento(Orçamento orcamento)
    {
        this.orcamento = orcamento;
    }

    public ArrayList<StatusOS> getStatus()
    {
        return status;
    }

    public void setStatus(ArrayList<StatusOS> status)
    {
        this.status = status;
    }
    
    public void addStatus(StatusOS s)
    {
        this.status.add(s);
    }

    public Date getData()
    {
        return data;
    }

    public void setData(Date data)
    {
        this.data = data;
    }
    
    public OS busca(int filtro)
    {
        OS os = null;
        ResultSet rs = Banco.getCon().consultar("select * from ordem_de_servico where os_codigo = " + filtro);
        
        try
        {
            if(rs != null && rs.next())            
            {
                os = new OS(rs.getInt("os_codigo"), rs.getString("os_descricao"), rs.getDate("os_data"));
                Orçamento o = new Orçamento().busca(rs.getInt("orc_codigo"));
                os.setOrcamento(o);
                
                rs = Banco.getCon().consultar("select status.stat_codigo,stat_descricao,stat_os_codigo from ordem_de_servico "
                        + "inner join status_os on ordem_de_servico.os_codigo = status_os.os_codigo "
                        + "AND ordem_de_servico.os_codigo = " + os.getCodigo()+ "inner join status on "
                        + "status.stat_codigo = status_os.stat_codigo");
                while(rs != null && rs.next())
                {
                    StatusOS s = new StatusOS(rs.getInt("stat_os_codigo"), new Status(rs.getInt("stat_codigo")).busca(), os);
                    s.setFuncionarios(s.buscaFuncionarios());
                    os.addStatus(s);
                }
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(OS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return os;
    }
}
