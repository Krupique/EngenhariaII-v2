
package com.krupique.bedusystem.controladoras;

import com.krupique.bedusystem.entidades.ParcelaRecebimento;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ctrParcelaRecebimento 
{
        private static ctrParcelaRecebimento con;

    private ctrParcelaRecebimento()
    {
    }

    public static ctrParcelaRecebimento instancia()
    {
        if (con == null)
        {
            con = new ctrParcelaRecebimento();
        }
        return con;
    }
    
    public final ObservableList<ParcelaRecebimento> get(int codigo)
    {
        ArrayList<ParcelaRecebimento> a = new ArrayList<>();
        a = new ParcelaRecebimento().get(codigo);
        ObservableList<ParcelaRecebimento> ob = FXCollections.observableArrayList(a);
        return ob;
    }
    
    public boolean estornar(int codigo,double valor)
    {
       return new ParcelaRecebimento().estorno(codigo,valor);
    }
    public boolean pagar(int codigo,double valor)
    {
       return new ParcelaRecebimento().pagar(codigo,valor);
    }
}
