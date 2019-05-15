/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.entidades;

import com.krupique.bedusystem.utilidades.Banco;

/**
 *
 * @author Henrique K. Secchi
 */
public class ItensCompra {
    private int cod_compra;
    private int cod_prod;
    private double item_preco;
    private int qtd;

    public ItensCompra(int cod_compra, int cod_prod, double item_preco, int qtd) {
        this.cod_compra = cod_compra;
        this.cod_prod = cod_prod;
        this.item_preco = item_preco;
        this.qtd = qtd;
    }
    
    public boolean salvar()
    {
        String sql = "insert into itens_compra (prod_codigo, comp_codigo, itens_compra_preco, itens_compra_quantidade)"
                + "values ($1, $2, $3, $4)";
        sql = sql.replace("$1", "" + cod_prod);
        sql = sql.replace("$2", "" + cod_compra);
        sql = sql.replace("$3", "" + item_preco);
        sql = sql.replace("$4", "" + qtd);
        
        System.out.println("SQL: " + sql);
        
        return Banco.con.manipular(sql);
    }
    

    public int getCod_compra() {
        return cod_compra;
    }

    public void setCod_compra(int cod_compra) {
        this.cod_compra = cod_compra;
    }

    public int getCod_prod() {
        return cod_prod;
    }

    public void setCod_prod(int cod_prod) {
        this.cod_prod = cod_prod;
    }

    public double getItem_preco() {
        return item_preco;
    }

    public void setItem_preco(double item_preco) {
        this.item_preco = item_preco;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }
    
    
}
