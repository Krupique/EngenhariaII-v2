package com.krupique.bedusystem.entidades;

import com.krupique.bedusystem.utilidades.Banco;

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
    
        public boolean atualizar(int codigo,double valor)
    {
        String sql = "UPDATE caixa SET caixa_valor = caixa_valor + $1 WHERE caixa_codigo = " + codigo;
        sql = sql.replace("$1", String.valueOf(valor));
        return Banco.getCon().manipular(sql);
    }
}
