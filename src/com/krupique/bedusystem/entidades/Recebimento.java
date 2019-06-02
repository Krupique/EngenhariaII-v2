package com.krupique.bedusystem.entidades;

import com.krupique.bedusystem.utilidades.Banco;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Recebimento
{
    private int codigo;
    private String descricao;
    private Date data;
    private double valor;
    private Funcionário funcionario;
    private Caixa caixa;
    private ParcelaRecebimento parcela;
    private Cliente cliente;

    public Recebimento()
    {
    }

    
    public Recebimento(int codigo, Date data, double valor, Funcionário funcionario, Caixa caixa)
    {
        this.codigo = codigo;
        this.data = data;
        this.valor = valor;
        this.funcionario = funcionario;
        this.caixa = caixa;
        this.descricao = "";
        this.parcela = new ParcelaRecebimento();
    }

    public Recebimento(int codigo, String descricao, Date data, double valor, Funcionário funcionario, Caixa caixa, Cliente cliente) 
    {
        this.codigo = codigo;
        this.descricao = descricao;
        this.data = data;
        this.valor = valor;
        this.funcionario = funcionario;
        this.caixa = caixa;
        this.cliente = cliente;
    }
    

    public Recebimento(int codigo, String descricao, Date data, double valor, Funcionário funcionario, Caixa caixa)
    {
        this.codigo = codigo;
        this.descricao = descricao;
        this.data = data;
        this.valor = valor;
        this.funcionario = funcionario;
        this.caixa = caixa;
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

    public Date getData()
    {
        return data;
    }

    public void setData(Date data)
    {
        this.data = data;
    }

    public double getValor()
    {
        return valor;
    }

    public void setValor(double valor)
    {
        this.valor = valor;
    }

    public Funcionário getFuncionario()
    {
        return funcionario;
    }

    public void setFuncionario(Funcionário funcionario)
    {
        this.funcionario = funcionario;
    }

    public Caixa getCaixa()
    {
        return caixa;
    }

    public void setCaixa(Caixa caixa)
    {
        this.caixa = caixa;
    }

    public ParcelaRecebimento getParcela()
    {
        return parcela;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    

    public void setParcela(ParcelaRecebimento parcela)
    {
        this.parcela = parcela;
    }
    
    
        public ArrayList<Recebimento> get()
    {
        String sql = "select *from Ordem_de_Servico";
        ResultSet rs = null;
        Orçamento orc ;
        int i = 0;
        ArrayList<Recebimento> a = new ArrayList<>();
        ArrayList<OS> os = new ArrayList<>();

        rs = Banco.getCon().consultar(sql);
        try
        {
            while (rs.next())
            {
                os.add(new OS().busca(rs.getInt("os_codigo")).get(0));
            }
        //    private int codigo;String descricao;Date data;double valor;Funcionário funcionario;
        //Caixa caixa; ParcelaRecebimento parcela;
        while(i < os.size())
        {
        orc = new Orçamento().busca(os.get(i).getOrcamento().getCodigo());
        a.add(new Recebimento(os.get(i).getCodigo(), os.get(i).getDescricao(),
        os.get(i).getData(),orc.getValorTotal(),orc.getFuncionario(),
        new Caixa(1),orc.getCliente()));
        i++;
        }
  
        } catch (SQLException ex)
        {
            a = null;
        }
        return a;
    }
        
    public ArrayList<Recebimento> getCliente(String nome)
    {
        Cliente c = new Cliente().getCliente(nome);
        String sql = "select *from Orcamento where Orcamento.cli_cod = "+c.getCodigo();
        ResultSet rs = null;
        OS os ;
        int i = 0;
        ArrayList<Recebimento> a = new ArrayList<>();
        ArrayList<Orçamento> orc = new ArrayList<>();

        rs = Banco.getCon().consultar(sql);
        try
        {
            while (rs.next())
            {
                orc.add(new Orçamento(rs.getInt("orc_codigo"), rs.getDate("dt_orcamento"), rs.getDate("dt_validade"), rs.getDouble("valor_tot"), rs.getString("orc_descricao"), rs.getInt("func_codigo"), rs.getInt("cli_cod")));      
            }
            
          while(i<orc.size())
          {
            os = new OS().buscaOrcamento(orc.get(i).getCodigo(),orc.get(i));
            a.add(new Recebimento(os.getCodigo(), os.getDescricao(),
            os.getData(),orc.get(i).getValorTotal(),orc.get(i).getFuncionario(),
            new Caixa(1),orc.get(i).getCliente()));
            i++;
          }
        } catch (SQLException ex)
        {
            a = null;
        }
        return a;
    }
        public ArrayList<Recebimento> getDia(LocalDate data)
    {
        String sql = "select *from Ordem_de_Servico where Ordem_de_Servico.os_data = '"+data.toString().replaceAll("-", "/")+"'";
        ResultSet rs = null;
        Orçamento orc ;
        int i = 0;
        ArrayList<Recebimento> a = new ArrayList<>();
        ArrayList<OS> os = new ArrayList<>();

        rs = Banco.getCon().consultar(sql);
        try
        {
            while (rs.next())
            {
                os.add(new OS().busca(rs.getInt("os_codigo")).get(0));
            }
            
         while (i< os.size())
         {
            //    private int codigo;String descricao;Date data;double valor;Funcionário funcionario;
            //Caixa caixa; ParcelaRecebimento parcela;
        orc = new Orçamento().busca(os.get(i).getOrcamento().getCodigo());
        a.add(new Recebimento(os.get(i).getCodigo(), os.get(i).getDescricao(),
        os.get(i).getData(),orc.getValorTotal(),orc.getFuncionario(),
        new Caixa(1),orc.getCliente()));
        i++;
         }
        } catch (SQLException ex)
        {
            a = null;
        }
        return a;
    }
        
    public ArrayList<Recebimento> getAte(LocalDate datainicial ,LocalDate datafinal)
    {
        String sql = "select *from Ordem_de_Servico where Ordem_de_Servico.os_data BETWEEN '"+datainicial+"'"+" AND '"+datafinal+"'" ;
      ResultSet rs = null;
        Orçamento orc ;
        int i = 0;
        ArrayList<Recebimento> a = new ArrayList<>();
        ArrayList<OS> os = new ArrayList<>();

        rs = Banco.getCon().consultar(sql);
        try
        {
            while (rs.next())
            {
                os.add(new OS().busca(rs.getInt("os_codigo")).get(0));
            }

        while(i < os.size())
        {
        orc = new Orçamento().busca(os.get(i).getOrcamento().getCodigo());
        a.add(new Recebimento(os.get(i).getCodigo(), os.get(i).getDescricao(),
        os.get(i).getData(),orc.getValorTotal(),orc.getFuncionario(),
        new Caixa(1),orc.getCliente()));
        i++;
        }
        } catch (SQLException ex)
        {
            a = null;
        }
        return a;
    }
    
}
