
package com.krupique.bedusystem.controladoras;

import com.jfoenix.controls.JFXTextField;
import com.krupique.bedusystem.entidades.ParcelaRecebimento;
import com.krupique.bedusystem.utilidades.Objeto;
import java.sql.Date;
import java.util.ArrayList;

public class ctrRecebimento
{
    public boolean gravar(ArrayList<Objeto>parcelas,JFXTextField cliente)
    {
        ParcelaRecebimento parcela;
        boolean flag = true;
        int i;
        for (i = 0; i < parcelas.size() && flag; i++)
        {
            parcela = new ParcelaRecebimento(Integer.parseInt(parcelas.get(i).getParam1()),
                    Date.valueOf(parcelas.get(i).getParam3()), Double.valueOf(parcelas.get(i).getParam2().replace(",", ".")));
            parcela.setCliente(CtrCliente.instancia().getCodigo(cliente.getText()));
            flag = flag && parcela.gravar();
        }
        if(!flag)
            for (; i > 0; i--)
            {
                parcela = new ParcelaRecebimento(Integer.parseInt(parcelas.get(i).getParam1()));
                parcela.remover();
            }
        return flag;
    }
}
