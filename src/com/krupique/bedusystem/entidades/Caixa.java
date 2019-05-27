package com.krupique.bedusystem.entidades;

public class Caixa 
{
    private int codigo;
    private double valor;

    public Caixa()
    {
    }

    public Caixa(int codigo, double valor)
    {
        this.codigo = codigo;
        this.valor = valor;
    }

    public Caixa(int codigo) {
        this.codigo = codigo;
    }



    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    public double getValor()
    {
        return valor;
    }

    public void setValor(double valor)
    {
        this.valor = valor;
    }
}
