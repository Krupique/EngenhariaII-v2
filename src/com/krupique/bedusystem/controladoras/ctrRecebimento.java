package com.krupique.bedusystem.controladoras;

import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.entidades.ParcelaRecebimento;
import com.krupique.bedusystem.entidades.Recebimento;
import com.krupique.bedusystem.utilidades.Objeto;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ctrRecebimento
{

    private static ctrRecebimento con;

    public ctrRecebimento()
    {
    }

    public static ctrRecebimento instancia()
    {
        if (con == null)
        {
            con = new ctrRecebimento();
        }
        return con;
    }

    public final boolean gravar(ArrayList<Objeto> parcelas, JFXTextField cliente)
    {
        ParcelaRecebimento parcela;
        boolean flag = true;
        int i;
        for (i = 0; i < parcelas.size() && flag; i++)
        {
            parcela = new ParcelaRecebimento(Integer.parseInt(parcelas.get(i).getParam1()),
                    Date.valueOf(parcelas.get(i).getParam3()), Double.valueOf(parcelas.get(i).getParam2().replace(",", ".")));
            if(LocalDate.parse(parcelas.get(i).getParam3()).compareTo(LocalDate.now()) == 0)
                parcela.setPagamento(Date.valueOf(parcelas.get(i).getParam3()));
            parcela.setCliente(CtrCliente.instancia().getCodigo(cliente.getText()));
            flag = flag && parcela.gravar();
        }
        if (!flag)
        {
            for (; i > 0; i--)
            {
                parcela = new ParcelaRecebimento(Integer.parseInt(parcelas.get(i).getParam1()));
                parcela.remover();
            }
        }
        return flag;
    }

    public final ObservableList<Recebimento> getTodos()
    {
        ArrayList<Recebimento> a = new ArrayList<>();
        a = new Recebimento().get();
        ObservableList<Recebimento> ob = FXCollections.observableArrayList(a);
        return ob;
    }

    public final ObservableList<Recebimento> getCliente(String nome)
    {
        ArrayList<Recebimento> a = new ArrayList<>();
        a = new Recebimento().getCliente(nome);
        ObservableList<Recebimento> ob = FXCollections.observableArrayList(a);
        return ob;
    }

    public final ObservableList<Recebimento> getDia(LocalDate data)
    {
        ArrayList<Recebimento> a = new ArrayList<>();
        a = new Recebimento().getDia(data);
        ObservableList<Recebimento> ob = FXCollections.observableArrayList(a);
        return ob;
    }

    public final ObservableList<Recebimento> getAte(LocalDate datainicial,LocalDate datafinal)
    {
        ArrayList<Recebimento> a = new ArrayList<>();
        a = new Recebimento().getAte(datainicial,datafinal);
        ObservableList<Recebimento> ob = FXCollections.observableArrayList(a);
        return ob;
    }
}
