package com.krupique.bedusystem.entidades;

import java.sql.Date;
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

    public void setParcela(ParcelaRecebimento parcela)
    {
        this.parcela = parcela;
    }
    
}
