package com.krupique.bedusystem.entidades;

import com.krupique.bedusystem.utilidades.Banco;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;

public class Pagamento
{
    private int codigo;
    private String descricao;
    private Date data;
    private double valor;
    private Funcionário funcionario;
    private Caixa caixa;

    public Pagamento()
    {
    }
    

    public Pagamento(int codigo, Date data, double valor, Funcionário funcionario, Caixa caixa)
    {
        this.codigo = codigo;
        this.data = data;
        this.valor = valor;
        this.funcionario = funcionario;
        this.caixa = caixa;
        this.descricao = "";
    }

    public Pagamento(int codigo, String descricao, Date data, double valor, Funcionário funcionario, Caixa caixa)
    {
        this.codigo = codigo;
        this.descricao = descricao;
        this.data = data;
        this.valor = valor;
        this.funcionario = funcionario;
        this.caixa = caixa;
    }

    public boolean pagar(int cod_parcela, int cod_compra, int cod_func, double valor, int cod_caixa) {
        
        String sql = "insert into Pagamento (pag_codigo, pag_data, pag_valor, func_codigo, caixa_codigo, comp_codigo, parc_codigo)"
                + "values (nextval('seq_pagamento'), '$1', $2, $3, $4, $5, $6)";
        
        
        sql = sql.replace("$1", "" + LocalDate.now());
        sql = sql.replace("$2", "" + valor);
        sql = sql.replace("$3", "" + cod_func);
        sql = sql.replace("$4", "" + cod_caixa);
        sql = sql.replace("$5", "" + cod_compra);
        sql = sql.replace("$6", "" + cod_parcela);
        
        if(Banco.con.manipular(sql))
        {
            //Atualizar caixa;
            //Setar parcela com status paga
            if(atualizarCaixa(cod_caixa, valor))
                return setarParcela(cod_parcela, cod_compra, valor, 1);
            
        }
        return false;
    }
    
    public boolean estornar(int cod_parcela, int cod_compra, double valor)
    {
        String sql = "select caixa_codigo from pagamento where comp_codigo = %1 and parc_codigo = %2";
        sql = sql.replace("%1", "" + cod_compra);
        sql = sql.replace("%2", "" + cod_parcela);
        int cod_caixa = pegarCodCaixa(sql);
        
        if(atualizarCaixa(cod_caixa, -valor)){
            return setarParcela(cod_parcela, cod_compra, valor, 0);
        }
        return false;
    }
    
    public int pegarCodCaixa(String sql){
        ResultSet rs;
        int cod = -1;
        try
        {
            rs = Banco.con.consultar(sql);
            if(rs.next())
                cod = rs.getInt("caixa_codigo");
        }catch(Exception er){
            System.out.println("Erro: " + er.getMessage());
        }
        return cod;
    }
    
    private boolean setarParcela(int cod_parcela, int cod_compra, double valor, int stat)
    {
        String sql = "update parcela_compra set parc_compra_status = $2, parc_compra_dtpagamento = $1 where parc_compra_codigo = " + cod_parcela +
                    "and parc_compra_compra_cod = " + cod_compra;
        if(stat == 1)
            sql = sql.replace("$1", "'" + LocalDate.now() + "'");
        else
            sql = sql.replace("$1", "NULL");
        
        sql = sql.replace("$2", "" + stat);
        return Banco.con.manipular(sql);
    }
    
    private boolean atualizarCaixa(int cod_caixa, double valor) {
        String sql = "select * from caixa where caixa_codigo = " + cod_caixa;
        ResultSet rs;
        double vl_temp = 0;
        try
        {
            rs = Banco.con.consultar(sql);
            if(rs.next())
                vl_temp = rs.getDouble("caixa_valorfecha");
            
            valor = vl_temp - valor;
            sql = "update caixa set caixa_valorfecha = " + valor + " where caixa_codigo = " + cod_caixa;
            return Banco.con.manipular(sql);
        }catch(Exception er){
            System.out.println("Erro: " + er.getMessage());
        }
        return false;
    }
    
    public boolean pagar()
    {
        return true;
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
}
