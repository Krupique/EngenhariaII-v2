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
        String sql = "select *from recebimento";
        ResultSet rs = null;
        ArrayList<Recebimento> a = new ArrayList<>();

        rs = Banco.getCon().consultar(sql);
        try
        {
            while (rs.next())
            {
                
                //    private int codigo;String descricao;Date data;double valor;Funcionário funcionario;
                //Caixa caixa; ParcelaRecebimento parcela;
                a.add(new Recebimento(rs.getInt("rec_codigo"), rs.getString("rec_descricao"),
                        rs.getDate("rec_data"),rs.getDouble("rec_valor"),new Funcionário().get(rs.getInt("func_codigo")),
                        new Caixa(rs.getInt("caixa_codigo")),new Cliente(rs.getInt("cli_cod"))));
                        
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
        String sql = "select *from recebimento where recebimento.cli_cod = "+c.getCodigo();
        ResultSet rs = null;
        ArrayList<Recebimento> a = new ArrayList<>();

        rs = Banco.getCon().consultar(sql);
        try
        {
            while (rs.next())
            {
                
                //    private int codigo;String descricao;Date data;double valor;Funcionário funcionario;
                //Caixa caixa; ParcelaRecebimento parcela;
                a.add(new Recebimento(rs.getInt("rec_codigo"), rs.getString("rec_descricao"),
                        rs.getDate("rec_data"),rs.getDouble("rec_valor"),new Funcionário().get(rs.getInt("func_codigo")),
                        new Caixa(rs.getInt("caixa_codigo")),c));
                        
            }
        } catch (SQLException ex)
        {
            a = null;
        }
        return a;
    }
        public ArrayList<Recebimento> getDia(LocalDate data)
    {
        System.out.println(data.toString().replaceAll("-", "/"));
        String sql = "select *from recebimento where recebimento.rec_data = '"+data.toString().replaceAll("-", "/")+"'";
        ResultSet rs = null;
        ArrayList<Recebimento> a = new ArrayList<>();

        rs = Banco.getCon().consultar(sql);
        try
        {
            while (rs.next())
            {
                
                //    private int codigo;String descricao;Date data;double valor;Funcionário funcionario;
                //Caixa caixa; ParcelaRecebimento parcela;
                a.add(new Recebimento(rs.getInt("rec_codigo"), rs.getString("rec_descricao"),
                        rs.getDate("rec_data"),rs.getDouble("rec_valor"),new Funcionário().get(rs.getInt("func_codigo")),
                        new Caixa(rs.getInt("caixa_codigo")),new Cliente(rs.getInt("cli_cod"))));
                        
            }
        } catch (SQLException ex)
        {
            a = null;
        }
        return a;
    }
        
            public ArrayList<Recebimento> getAte(LocalDate data)
    {
        String sql = "select *from recebimento where recebimento.rec_data BETWEEN '"+LocalDate.now()+"'"+" AND '"+data.toString().replaceAll("-", "/")+"'" ;
        ResultSet rs = null;
        ArrayList<Recebimento> a = new ArrayList<>();

        rs = Banco.getCon().consultar(sql);
        try
        {
            while (rs.next())
            {
                
                //    private int codigo;String descricao;Date data;double valor;Funcionário funcionario;
                //Caixa caixa; ParcelaRecebimento parcela;
                a.add(new Recebimento(rs.getInt("rec_codigo"), rs.getString("rec_descricao"),
                        rs.getDate("rec_data"),rs.getDouble("rec_valor"),new Funcionário().get(rs.getInt("func_codigo")),
                        new Caixa(rs.getInt("caixa_codigo")),new Cliente(rs.getInt("cli_cod"))));
                        
            }
        } catch (SQLException ex)
        {
            a = null;
        }
        return a;
    }
    
}
