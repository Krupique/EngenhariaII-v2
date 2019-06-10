/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.controladoras;

import com.krupique.bedusystem.entidades.Caixa;
import com.krupique.bedusystem.entidades.Pagamento;
import java.time.LocalDate;

/**
 *
 * @author Henrique K. Secchi
 */
public class CtrPagamento {
    
    private Pagamento pagamento;
    
    public int pagar(Object[] obj)
    {
        String data = LocalDate.now() + "";
        Caixa caixa = new Caixa();
        int log = caixa.validar_caixa(data);
        if(log > 0){
            pagamento = new Pagamento();
            if(pagamento.pagar((int)obj[0], (int)obj[1], (int)obj[2], (double)obj[3], log))
                return 1; //retornou certo
        }
        else if(log == 0 || log == -1)
            return log;
        return -2;
    }
    
    public int validar_caixa()
    {
        String data = LocalDate.now() + "";
        Caixa caixa = new Caixa();
        int log = caixa.validar_caixa(data);
        if(log > 0)
            return 1;
        else if(log == 0 || log == -1)
            return log;
        return -2;
    }
    
    public boolean estornar(Object[] obj)
    {
        Pagamento pag = new Pagamento();
        return pag.estornar((int)obj[0], (int)obj[1], (double)obj[3]);
    }
}
