package com.krupique.bedusystem.entidades;

import com.krupique.bedusystem.utilidades.Banco;
import java.sql.ResultSet;

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
        
    public int validar_caixa(String data_pagamento)
    {
        ResultSet rs;
        String data_abert = null;
        String data_fecha = null;
        String sql = "select caixa_codigo, caixa_dtabertura, caixa_dtfecha from caixa where"
                + " caixa_dtabertura = '" + data_pagamento + "'";
        int cod_caixa = 0;
        
        try
        {
            rs = Banco.con.consultar(sql);
            if (rs.next()) {
                cod_caixa = rs.getInt("caixa_codigo");
                data_abert = rs.getString("caixa_dtabertura");
                data_fecha = rs.getString("caixa_dtfecha");
            }
        }catch(Exception er)
        {
            System.out.println("Erro Caixa: " + er.getMessage());
        }
        
        
        if(data_abert == null) //Caixa nao foi aberto ainda
            return 0;
        else if(data_fecha != null) //Caixa ja foi fechado
            return -1;
        else //Caixa aberto mas nao foi fechado ainda
            return cod_caixa;
    }
}
