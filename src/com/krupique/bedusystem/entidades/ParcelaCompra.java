/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.entidades;

import com.krupique.bedusystem.utilidades.Banco;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Henrique K. Secchi
 */
public class ParcelaCompra {
    private int cod_parcela;
    private int status_parcela;
    private LocalDate data_vencimento;
    private int numero_parcela;
    private LocalDate data_pagamento;
    private double valor_pago;
    private int cod_compra;
    private double vlr_parcela;

    public ParcelaCompra() {
    }
    
    public ParcelaCompra(int cod_parcela, int status_parcela, LocalDate data_vencimento, int numero_parcela, LocalDate data_pagamento, double valor_pago, int cod_compra) {
        this.cod_parcela = cod_parcela;
        this.status_parcela = status_parcela;
        this.data_vencimento = data_vencimento;
        this.numero_parcela = numero_parcela;
        this.data_pagamento = data_pagamento;
        this.valor_pago = valor_pago;
        this.cod_compra = cod_compra;
    }

    public ParcelaCompra(int cod_parcela, int status_parcela, LocalDate data_vencimento, int numero_parcela, int cod_compra, double vlrParcela) {
        this.cod_parcela = cod_parcela;
        this.status_parcela = status_parcela;
        this.data_vencimento = data_vencimento;
        this.numero_parcela = numero_parcela;
        this.cod_compra = cod_compra;
        this.vlr_parcela = vlrParcela;
    }

    public ParcelaCompra(int cod) {
        this.cod_compra = cod;
    }
    
    
    
    public boolean salvar()
    {
        String sql = "insert into parcela_compra (parc_compra_codigo, parc_compra_status, parc_compra_dtvencimento, parc_compra_numero, parc_compra_compra_cod, parc_compra_vlrparcela)"
                + "values ($1, $2, '$3', $4, $5, $6)";
        
        sql = sql.replace("$1", "" + cod_parcela);
        sql = sql.replace("$2", "" + status_parcela);
        sql = sql.replace("$3", "" + data_vencimento);
        sql = sql.replace("$4", "" + numero_parcela);
        sql = sql.replace("$5", "" + cod_compra);
        sql = sql.replace("$6", "" + vlr_parcela);
        
        System.out.println("SQL: " + sql);
        
        return Banco.con.manipular(sql);
    }
    
    public ArrayList<Object[]> buscar(int cod)
    {
        String sql = "select * from parcela_compra where parc_compra_compra_cod = " + cod;
        System.out.println(sql);
        ResultSet rs;
        ArrayList<Object[]> list = new ArrayList<>();
        Object[] obj;
        
        try
        {
            rs = Banco.con.consultar(sql);
            while(rs.next())
            {
                obj = new Object[5];
                obj[0] = rs.getInt("parc_compra_codigo");
                obj[1] = rs.getInt("parc_compra_status");
                obj[2] = rs.getString("parc_compra_dtvencimento");
                obj[3] = rs.getString("parc_compra_dtpagamento");
                obj[4] = rs.getDouble("parc_compra_vlrparcela");
                
                list.add(obj);
            }
        }catch(Exception er)
        {
            System.out.println("Erro: " + er.getMessage());
        }
        return list;
    }
    
    public boolean excluir()
    {
        String str = "delete from parcela_compra where parc_compra_compra_cod = " + cod_compra;
        return Banco.con.manipular(str);
    }

    public int getCod_parcela() {
        return cod_parcela;
    }

    public void setCod_parcela(int cod_parcela) {
        this.cod_parcela = cod_parcela;
    }

    public int getStatus_parcela() {
        return status_parcela;
    }

    public void setStatus_parcela(int status_parcela) {
        this.status_parcela = status_parcela;
    }

    public LocalDate getData_vencimento() {
        return data_vencimento;
    }

    public void setData_vencimento(LocalDate data_vencimento) {
        this.data_vencimento = data_vencimento;
    }

    public int getNumero_parcela() {
        return numero_parcela;
    }

    public void setNumero_parcela(int numero_parcela) {
        this.numero_parcela = numero_parcela;
    }

    public LocalDate getData_pagamento() {
        return data_pagamento;
    }

    public void setData_pagamento(LocalDate data_pagamento) {
        this.data_pagamento = data_pagamento;
    }

    public double getValor_pago() {
        return valor_pago;
    }

    public void setValor_pago(double valor_pago) {
        this.valor_pago = valor_pago;
    }

    public int getCod_compra() {
        return cod_compra;
    }

    public void setCod_compra(int cod_compra) {
        this.cod_compra = cod_compra;
    }
    
    
    
}
