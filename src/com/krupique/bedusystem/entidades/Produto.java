package com.krupique.bedusystem.entidades;

import com.krupique.bedusystem.utilidades.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Produto
{
    private int codigo;
    private String nome;
    private int quantidade;
    private Classificacao classificacao;
    private double preco;

    public Produto(){}

    public Produto(int codigo, String nome, double preco, int quantidade, Classificacao classificacao)
    {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.classificacao = classificacao;
    }
    
    public Produto(int codigo, int quantidade)
    {
        this.codigo = codigo;
        this.quantidade = quantidade;
    }

    public Produto(int cod) {
        this.codigo = cod;
    }
    
    public boolean salvar(int flag)
    {
        String sql;
        if(flag == 1)
            sql = "update produto set prod_nome = '$1', prod_preco = $2, prod_quantidade = $3, class_codigo = $4 where prod_codigo = " + codigo;
        else //if(flag == 0)
            sql = "insert into produto (prod_codigo, prod_nome, prod_preco, prod_quantidade, class_codigo) "
                    + "values(nextval('seq_produto'), '$1', $2, $3, $4)";
        
        sql = sql.replace("$1", nome);
        sql = sql.replace("$2", ""+preco);
        sql = sql.replace("$3", ""+quantidade);
        sql = sql.replace("$4", ""+classificacao.getCod());
        System.out.println("" + sql);
        
        return Banco.con.manipular(sql);
    }
    
    public ArrayList<Produto> buscar (String str)
    {
        String sql = "select * from produto inner join classificacao on produto.class_codigo = classificacao.class_codigo where " + str;
        System.out.println(sql);
        ResultSet rs;
        ArrayList<Produto> list = new ArrayList<>();
        
        try
        {
            rs = Banco.con.consultar(sql);
            while(rs.next())
                list.add(new Produto(rs.getInt("prod_codigo"), rs.getString("prod_nome"), rs.getDouble("prod_preco"), rs.getInt("prod_quantidade"), new Classificacao(rs.getInt("class_codigo"), rs.getString("class_descricao"))));
        }catch(Exception er)
        {
            System.out.println("Erro: " + er.getMessage());
        }
        return list;
    }
    
    public boolean excluir()
    {
        String str = "delete from produto where prod_codigo = " + codigo;
        return Banco.con.manipular(str);
    }
    
    public boolean atualiza_estoque()
    {
        String sql = "select prod_quantidade from produto where prod_codigo = " + codigo;
        
        ResultSet rs;
        int quant = 0;
        
        try
        {
            rs = Banco.con.consultar(sql);
            if(rs.next())
                quant = rs.getInt("prod_quantidade");
        }catch(Exception er){
            System.out.println("Erro: " + er.getMessage());
        }
        
        quant += this.quantidade;
        sql = "update produto set prod_quantidade = " + quant + " where prod_codigo = " + codigo;
        return Banco.con.manipular(sql);
    }

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public int getQuantidade()
    {
        return quantidade;
    }

    public void setQuantidade(int quantidade)
    {
        this.quantidade = quantidade;
    }

    public Classificacao getClassificacao()
    {
        return classificacao;
    }

    public void setClassificacao(Classificacao classificacao)
    {
        this.classificacao = classificacao;
    }

    public double getPreco()
    {
        return preco;
    }

    public void setPreco(double preco)
    {
        this.preco = preco;
    }
    
}
