package com.krupique.bedusystem.entidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.krupique.bedusystem.utilidades.Banco;
import java.sql.Date;
import java.util.ArrayList;

public class Orçamento
{
    private int codigo;
    private String descricao;
    private Funcionário funcionario;
    private Cliente cliente;
    private Veiculo veiculo;
    private double valorTotal;
    private Date dataOrcamento;
    private Date dataValidade;
    private ArrayList<ItemOrcamentoProduto> produtosOrcamento;
    private ArrayList<ItemOrcamentoServico> servicosOrcamento;

    public Orçamento()
    {
    }
    
    

    public Orçamento(int codigo)
    {
        this.codigo = codigo;
        this.produtosOrcamento = new ArrayList<>();
        this.servicosOrcamento = new ArrayList<>();
        ResultSet rs = Banco.getCon().consultar("select prod_nome,produto.prod_codigo,prod_orc_preco,"
        + "prod_orc_quantidade from orcamento inner join produtos_orcamento on "
        + "orcamento.orc_codigo = produtos_orcamento.orc_codigo and orcamento.orc_codigo = " + codigo
        + "inner join produto on produtos_orcamento.prod_codigo = produto.prod_codigo");
        
        try
        {
            while(rs != null && rs.next())            
            {
                produtosOrcamento.add(
                    new ItemOrcamentoProduto(new Produto(rs.getInt("prod_codigo"), rs.getString("prod_nome")),
                            rs.getDouble("prod_orc_preco"), rs.getInt("prod_orc_quantidade")));
            }
            
            rs = Banco.getCon().consultar("select servicos.serv_codigo,serv_cod_preco "
            + "from orcamento inner join servicos_orcamento on orcamento.orc_codigo = servicos_orcamento.orc_codigo"
            + " and orcamento.orc_codigo = " + codigo + "inner join servicos "
            + "on servicos.serv_codigo = servicos_orcamento.serv_codigo");
            while(rs != null && rs.next())            
            {
                servicosOrcamento.add(
                        new ItemOrcamentoServico(new Servico(rs.getInt("serv_codigo")), 
                                rs.getDouble("serv_cod_preco"), 0));
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Orçamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Orçamento(int codigo, Cliente cliente, Veiculo veiculo, double valorTotal)
    {
        this.codigo = codigo;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.valorTotal = valorTotal;
    }
    
    public Orçamento(String descricao, Funcionário funcionario, Cliente cliente)
    {
        this.descricao = descricao;
        this.funcionario = funcionario;
        this.cliente = cliente;
    }

    public Orçamento(int codigo, Funcionário funcionario, Cliente cliente)
    {
        this.codigo = codigo;
        this.funcionario = funcionario;
        this.cliente = cliente;
        this.descricao = "";
    }

    public Orçamento(int codigo, String descricao, Funcionário funcionario, Cliente cliente)
    {
        this.codigo = codigo;
        this.descricao = descricao;
        this.funcionario = funcionario;
        this.cliente = cliente;
    }

    public Orçamento(int codigo, String descricao, Funcionário funcionario, Cliente cliente, Veiculo veiculo)
    {
        this.codigo = codigo;
        this.descricao = descricao;
        this.funcionario = funcionario;
        this.cliente = cliente;
        this.veiculo = veiculo;
    }

    public Orçamento(int codigo,Date dataOrcamento,Date dataValidade,double valorTotal, String descricao, int funcionario, int cliente) 
    {
        this.codigo = codigo;
        this.descricao = descricao;
        this.funcionario = new Funcionário().get(funcionario);
        this.cliente = new Cliente(cliente);
        this.valorTotal = valorTotal;
        this.dataOrcamento = dataOrcamento;
        this.dataValidade = dataValidade;
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

    public Funcionário getFuncionario()
    {
        return funcionario;
    }

    public void setFuncionario(Funcionário funcionario)
    {
        this.funcionario = funcionario;
    }

    public Cliente getCliente()
    {
        return cliente;
    }

    public void setCliente(Cliente cliente)
    {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo()
    {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo)
    {
        this.veiculo = veiculo;
    }

    public double getValorTotal()
    {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal)
    {
        this.valorTotal = valorTotal;
    }

    public Date getDataOrcamento()
    {
        return dataOrcamento;
    }

    public void setDataOrcamento(Date dataOrcamento)
    {
        this.dataOrcamento = dataOrcamento;
    }

    public Date getDataValidade()
    {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade)
    {
        this.dataValidade = dataValidade;
    }

    public ArrayList<ItemOrcamentoProduto> getProdutosOrcamento()
    {
        return produtosOrcamento;
    }

    public void setProdutosOrcamento(ArrayList<ItemOrcamentoProduto> produtosOrcamento)
    {
        this.produtosOrcamento = produtosOrcamento;
    }

    public ArrayList<ItemOrcamentoServico> getServicosOrcamento()
    {
        return servicosOrcamento;
    }

    public void setServicosOrcamento(ArrayList<ItemOrcamentoServico> servicosOrcamento)
    {
        this.servicosOrcamento = servicosOrcamento;
    }
    
    public Orçamento busca(int filtro)
    {
        Orçamento o = null;
        ResultSet rs = Banco.getCon().consultar("select * from orcamento where orc_codigo = " + filtro);
        
        try
        {
            if(rs != null && rs.next())            
            {
                Funcionário f = new Funcionário(rs.getInt("func_codigo"));
                Veiculo v = new Veiculo(rs.getString("vei_placa"));
                Cliente c = new Cliente(rs.getInt("cli_cod"));
                
                f = f.get();
                v.busca();
                c.busca();
                
                o = new Orçamento(filtro, rs.getString("orc_descricao"), f, c, v);
                o.setValorTotal(rs.getDouble("valor_tot"));
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(Orçamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o;
    }
    
    public Orçamento buscaOrc_Os(int filtro)
    {
        Orçamento o = null;
        ResultSet rs = Banco.getCon().consultar("select * from orcamento inner join ordem_de_servico on "
                + "orcamento.orc_codigo = ordem_de_servico.orc_codigo and os_codigo = " + filtro);
        
        try
        {
            if(rs != null && rs.next())            
            {
                Funcionário f = new Funcionário(rs.getInt("func_codigo"));
                Veiculo v = new Veiculo(rs.getString("vei_placa"));
                Cliente c = new Cliente(rs.getInt("cli_cod"));
                
                f = f.get();
                v.busca();
                c.busca();
                
                o = new Orçamento(rs.getInt("orc_codigo"), rs.getString("orc_descricao"), f, c, v);
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(Orçamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o;
    }
    
    public ArrayList<Orçamento> getAvancado()
    {
        /*select * from orcamento where orc_codigo not in (select orc_codigo from ordem_de_servico)*/
        ArrayList<Orçamento> orcamentos = new ArrayList<>();
        String sql = "select valor_tot,orc_codigo,orcamento.vei_placa,cli_nome,cli_cpf,cli_telefone,cli_email,"
        + "cli_endereco,vei_modelo,vei_marca,vei_ano,vei_cor,cliente.cli_cod from (select * from orcamento "
        + "where orc_codigo not in (select orc_codigo from ordem_de_servico)) as orcamento  inner join cliente "
        + "on orcamento.cli_cod = cliente.cli_cod inner join veiculo on orcamento.vei_placa = veiculo.vei_placa";
        ResultSet rs = Banco.getCon().consultar(sql);
        
        try
        {
            while(rs != null && rs.next())            
            {
                ArrayList<ItemOrcamentoProduto> produtos = new ArrayList<>();
                ArrayList<ItemOrcamentoServico> servicos = new ArrayList<>();
                Cliente c = new Cliente(rs.getInt("cli_cod"));
                Veiculo v = new Veiculo(rs.getString("vei_placa"));
                v.busca();
                Orçamento o = new Orçamento(rs.getInt("orc_codigo"), c, v, rs.getDouble("valor_tot"));
                
                String sql2 = "select prod_orc_preco,prod_orc_quantidade,produto.prod_codigo,prod_nome from" +
                "orcamento inner join produtos_orcamento on orcamento.orc_codigo = produtos_orcamento.orc_codigo "
                + "inner join produto on produto.prod_codigo = produtos_orcamento.prod_codigo";
                ResultSet rsP = Banco.getCon().consultar(sql2);
                while(rsP != null && rsP.next())
                {
                    Produto p = new Produto(rs.getInt("prod_cod"),rs.getString("prod_nome"));
                    produtos.add(new ItemOrcamentoProduto(p, rs.getDouble("prod_orc_preco"), rs.getInt("prod_orc_quantidade")));
                }
                o.setProdutosOrcamento(produtos);
                
                sql2 = "select serv_cod_preco,servicos.serv_codigo,serv_descricao from orcamento "
                + "inner join servicos_orcamento on orcamento.orc_codigo = servicos_orcamento.orc_codigo "
                + "inner join servicos on servicos.serv_codigo = servicos_orcamento.serv_codigo";
                ResultSet rsS = Banco.getCon().consultar(sql2);
                while(rsP != null && rsP.next())
                {
                    Serviço s = new Serviço(rs.getInt("serv_codigo"),rs.getString("serv_descricao"));
                    servicos.add(new ItemOrcamentoServico(s, rs.getDouble("serv_cod_preco")));
                }
                o.setServicosOrcamento(servicos);
                
                orcamentos.add(o);
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Orcamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orcamentos;
    }
}
