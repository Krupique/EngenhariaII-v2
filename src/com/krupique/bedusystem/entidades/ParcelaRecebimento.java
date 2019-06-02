/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.entidades;

import com.krupique.bedusystem.utilidades.Banco;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Caique
 */
public class ParcelaRecebimento
{

    private int codigo;
    private int status;
    private Date vencimento;
    private Date pagamento;
    private int numero;
    private double valor_pago;
    private double valor;
    private Cliente cliente;
    private int OScodigo;

    public ParcelaRecebimento()
    {
    }

    
    public ParcelaRecebimento(int codigo, int status, Date vencimento, Date pagamento, int numero, double valor_pago, double valor, Cliente cliente)
    {
        this.codigo = codigo;
        this.status = status;
        this.vencimento = vencimento;
        this.pagamento = pagamento;
        this.numero = numero;
        this.valor_pago = valor_pago;
        this.valor = valor;
        this.cliente = cliente;
    }

    public ParcelaRecebimento(int codigo, Date vencimento, Date pagamento, double valor, int OScodigo) {
        this.codigo = codigo;
        this.vencimento = vencimento;
        this.pagamento = pagamento;
        this.valor = valor;
        this.OScodigo = OScodigo;
    }

    public ParcelaRecebimento(int codigo, Date vencimento, Date pagamento, int numero, double valor) {
        this.codigo = codigo;
        this.vencimento = vencimento;
        this.pagamento = pagamento;
        this.numero = numero;
        this.valor = valor;
    }
    

    public ParcelaRecebimento(int codigo)
    {
        this.codigo = codigo;
    }

    public ParcelaRecebimento(int codigo, int status, Date vencimento, Date pagamento, int numero, double valor_pago, double valor)
    {
        this.codigo = codigo;
        this.status = status;
        this.vencimento = vencimento;
        this.pagamento = pagamento;
        this.numero = numero;
        this.valor_pago = valor_pago;
        this.valor = valor;
    }

    public ParcelaRecebimento(int numero, Date vencimento, double valor)
    {
        this.numero = numero;
        this.vencimento = vencimento;
        this.valor = valor;
    }

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public Date getVencimento()
    {
        return vencimento;
    }

    public void setVencimento(Date vencimento)
    {
        this.vencimento = vencimento;
    }

    public Date getPagamento()
    {
        return pagamento;
    }

    public void setPagamento(Date pagamento)
    {
        this.pagamento = pagamento;
    }

    public int getNumero()
    {
        return numero;
    }

    public void setNumero(int numero)
    {
        this.numero = numero;
    }

    public double getValor_pago()
    {
        return valor_pago;
    }

    public void setValor_pago(double valor_pago)
    {
        this.valor_pago = valor_pago;
    }

    public double getValor()
    {
        return valor;
    }

    public void setValor(double valor)
    {
        this.valor = valor;
    }

    public Cliente getCliente()
    {
        return cliente;
    }

    public void setCliente(Cliente cliente)
    {
        this.cliente = cliente;
    }

    public int getOScodigo() {
        return OScodigo;
    }

    public void setOScodigo(int OScodigo) {
        this.OScodigo = OScodigo;
    }
    
    

    public boolean gravar()
    {
        String sql = "insert into parcela_recebimento(parc_receb_status,parc_receb_dtvencimento,parc_receb_numero,"
                + "parc_receb_vlrpago,parc_receb_valor,cli_cod) values ($2,'$3',$4,$5,$6,$1)";

        sql = sql.replace("$2", String.valueOf(status));
        sql = sql.replace("$3", String.valueOf(vencimento));
        sql = sql.replace("$4", String.valueOf(numero));
        sql = sql.replace("$5", String.valueOf(valor_pago));
        sql = sql.replace("$6", String.valueOf(valor));
        sql = sql.replace("$1", String.valueOf(cliente.getCodigo()));

        return Banco.getCon().manipular(sql);
    }

    public boolean remover()
    {
        String sql = "delete from parcela_recebimento where parc_receb_codigo = " + codigo;

        return Banco.getCon().manipular(sql);
    }

    public ArrayList<ParcelaRecebimento> get(int codigo)
    {
        String sql = "select *from parcela_recebimento where parcela_recebimento.os_codigo =" + codigo +"order by parc_receb_dtVencimento";
        ResultSet rs = null;
        ArrayList<ParcelaRecebimento> a = new ArrayList<>();

        rs = Banco.getCon().consultar(sql);
        try
        {
            while (rs.next())
            {

                // int codigo;int status; Date vencimento;Date pagamento;int numero;
                //double valor_pago;double valor;
                a.add(new ParcelaRecebimento(rs.getInt("parc_receb_codigo"),
                        rs.getDate("parc_receb_dtVencimento"), rs.getDate("parc_receb_dtPagamento"),
                        rs.getInt("parc_receb_numero"),rs.getDouble("parc_receb_valor")));

            }
        } catch (SQLException ex)
        {
            a = null;
        }
        return a;
    }

}
