package com.krupique.bedusystem.entidades;

import com.krupique.bedusystem.utilidades.Banco;
import java.sql.ResultSet;
import java.time.LocalDate;

public class Compra extends Movimento 
{
	private int cod_compra;
	private int cod_fornecedor;
	private int cod_funcionario;
	private int parcelas;
	private double juros;
	private double valor_total;
        private LocalDate data_compra;

    public Compra()
    {
    }

    public Compra(int cod_compra, int cod_fornecedor, int cod_funcionario, int parcelas, double juros, double valor_total, LocalDate data_compra) {
        this.cod_compra = cod_compra;
        this.cod_fornecedor = cod_fornecedor;
        this.cod_funcionario = cod_funcionario;
        this.parcelas = parcelas;
        this.juros = juros;
        this.valor_total = valor_total;
        this.data_compra = data_compra;
    }

    public boolean salvar()
    {
        String sql = "insert into compra (comp_codigo, forn_cod, func_codigo, comp_qtd_parcelas, "
                + "comp_valor_total, comp_juros, comp_data_compra)"
                + "values (nextval('seq_compra'), $2, $3, $4, $5, $6, '$7')";
        
        sql = sql.replace("$2", "" + cod_fornecedor);
        sql = sql.replace("$3", "" + cod_funcionario);
        sql = sql.replace("$4", "" + parcelas);
        sql = sql.replace("$5", "" + juros);
        sql = sql.replace("$6", "" + valor_total);
        sql = sql.replace("$7", "" + data_compra);
        System.out.println("SQL: " + sql);
        
        return Banco.con.manipular(sql);
    }
    
    public int getSequence()
    {
        //select last_value from seq_compra
        String sql = "select last_value from seq_compra";
        ResultSet rs;
        int cod = -1;
        
        try
        {
            rs = Banco.con.consultar(sql);
            if(rs.next())
                cod = rs.getInt("last_value");
            
            
        } catch(Exception er){
            System.out.println("Erro: " + er.getMessage());
        }
        return cod;
    }
    
    public int getCod_compra() {
        return cod_compra;
    }

    public void setCod_compra(int cod_compra) {
        this.cod_compra = cod_compra;
    }

    public int getCod_fornecedor() {
        return cod_fornecedor;
    }

    public void setCod_fornecedor(int cod_fornecedor) {
        this.cod_fornecedor = cod_fornecedor;
    }

    public int getCod_funcionario() {
        return cod_funcionario;
    }

    public void setCod_funcionario(int cod_funcionario) {
        this.cod_funcionario = cod_funcionario;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public double getJuros() {
        return juros;
    }

    public void setJuros(double juros) {
        this.juros = juros;
    }

    public double getValor_total() {
        return valor_total;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
    }

    public LocalDate getData_compra() {
        return data_compra;
    }

    public void setData_compra(LocalDate data_compra) {
        this.data_compra = data_compra;
    }

    
    
}
