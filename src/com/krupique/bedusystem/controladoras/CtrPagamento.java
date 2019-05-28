/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.controladoras;

import com.krupique.bedusystem.entidades.Pagamento;

/**
 *
 * @author Henrique K. Secchi
 */
public class CtrPagamento {
    
    private Pagamento pagamento;
    
    public boolean pagar(Object[] obj)
    {
        pagamento = new Pagamento();
        return pagamento.pagar((int)obj[0], (int)obj[1], (int)obj[2], (double)obj[3]);
    }
}
