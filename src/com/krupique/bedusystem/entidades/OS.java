package com.krupique.bedusystem.entidades;

import com.krupique.bedusystem.utilidades.Banco;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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

    public OS(int codigo, int orcamento) {
        this.codigo = codigo;
        this.orcamento = new Orçamento(orcamento);
    }
    

    public OS(int orc)
    {
        this.orcamento = new Orçamento(orc);
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
    
    public ArrayList<OS> busca(int filtro)
    {
        OS os = null;
        ArrayList<OS>retorno = new ArrayList<>();
        ResultSet rs,rsAux;
        if(filtro != 0)
             rs = Banco.getCon().consultar("select * from ordem_de_servico where os_codigo = " + filtro);
        else
            rs = Banco.getCon().consultar("select * from ordem_de_servico");
        
        try
        {
            while(rs != null && rs.next())            
            {
                os = new OS(rs.getInt("os_codigo"), rs.getString("os_descricao"), rs.getDate("os_data"));
                Orçamento o = new Orçamento().busca(rs.getInt("orc_codigo"));
                os.setOrcamento(o);
                
                rsAux = Banco.getCon().consultar("select status.stat_codigo,stat_descricao,stat_os_codigo,funcionario.func_codigo "
                        + "from ordem_de_servico inner join status_os on ordem_de_servico.os_codigo = status_os.os_codigo "
                        + "AND ordem_de_servico.os_codigo = " + os.getCodigo()+ " inner join status on "
                                + "status.stat_codigo = status_os.stat_codigo inner join funcionario "
                                + "on funcionario.func_codigo = status_os.func_codigo");
                while(rsAux != null && rsAux.next())
                {
                    StatusOS s = new StatusOS(rsAux.getInt("stat_os_codigo"), new Status(rsAux.getInt("stat_codigo")).busca());
                    s.setFuncionarios(new Funcionário().get(rsAux.getInt("func_codigo")));
                    os.addStatus(s);
                }
                retorno.add(os);
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(OS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public StatusOS getMax()
    {
        StatusOS sos = new StatusOS();
        ResultSet rs = Banco.getCon().consultar("select max(stat_os_codigo) as stat_os_codigo from status_os inner join "
        + "ordem_de_servico on ordem_de_servico.os_codigo = status_os.os_codigo and ordem_de_servico.os_codigo = " + codigo);
        
        try
        {
            if(rs != null && rs.next())            
            {
                sos = new StatusOS(rs.getInt("stat_os_codigo"));
                sos.busca();
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(OS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sos;
    }

    public boolean alterar()
    {
        
        return true;
    }

    public boolean gravar()
    {
        String sql = "insert into ordem_de_servico (os_data,orc_codigo) values('$1',$2)";
        sql = sql.replace("$1", String.valueOf(LocalDate.now()));
        sql = sql.replace("$2", String.valueOf(orcamento.getCodigo()));
        
        return Banco.getCon().manipular(sql);
    }
    
    public OS buscaOrcamento(int codigo,Orçamento orc)
    {
        String sql = "select *from Ordem_de_Servico Where orc_codigo = " + codigo;
        ResultSet rs = null;
            
        rs = Banco.getCon().consultar(sql);
        try
        {
            while (rs.next())
            {
                OS os = new OS(rs.getInt("os_codigo"),rs.getString("os_descricao"),rs.getDate("os_data"));
                os.setOrcamento(orc);
                return os;
            }
        } catch (SQLException ex)
        {
        }
        return null;
    }
}
