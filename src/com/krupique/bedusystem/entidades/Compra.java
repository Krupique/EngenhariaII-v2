package com.krupique.bedusystem.entidades;

import com.krupique.bedusystem.utilidades.Banco;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

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
        sql = sql.replace("$5", "" + valor_total);
        sql = sql.replace("$6", "" + juros);
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
    
    public ArrayList<Object[]> buscar(String str)
    {
        String temp =   "select distinct compra.comp_codigo, funcionario.func_nome, fornecedor.forn_nome, compra.comp_qtd_parcelas, compra.comp_valor_total, compra.comp_data_compra\n" +
                        "from compra inner join funcionario on compra.func_codigo = funcionario.func_codigo\n" +
                        "inner join fornecedor on compra.forn_cod = fornecedor.forn_cod\n" +
                        "inner join itens_compra on compra.comp_codigo = itens_compra.comp_codigo\n" +
                        "inner join produto on itens_compra.prod_codigo = produto.prod_codigo\n" +
                        "where " + str;
        System.out.println("Temp: " + temp);
        
        return buscar_no_banco(temp);
    }
    
    private ArrayList<Object[]> buscar_no_banco(String sql)
    {
        System.out.println(sql);
        ResultSet rs;
        ArrayList<Object[]> list = new ArrayList<>();
        Object[] obj;
        
        try
        {
            rs = Banco.con.consultar(sql);
            while(rs.next())
            {
                obj = new Object[7];
                obj[0] = rs.getInt("comp_codigo");
                obj[1] = rs.getString("func_nome");
                obj[2] = rs.getString("forn_nome");
                obj[3] = rs.getInt("comp_qtd_parcelas");
                obj[4] = rs.getDouble("comp_valor_total");
                obj[5] = rs.getString("comp_data_compra");
                obj[6] = null; //Parcelas
                
                list.add(obj);
            }
        }catch(Exception er)
        {
            System.out.println("Erro: " + er.getMessage());
        }
        return list;
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
