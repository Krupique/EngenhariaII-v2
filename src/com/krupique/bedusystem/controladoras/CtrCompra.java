/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.controladoras;

import com.krupique.bedusystem.entidades.Compra;
import com.krupique.bedusystem.entidades.ItensCompra;
import com.krupique.bedusystem.entidades.ParcelaCompra;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 *
 * @author Henrique K. Secchi
 */
public class CtrCompra {
    
    public boolean salvar(Object[] obj_compra, Object[] obj_parcela, ArrayList<Object[]> obj_itens){
        
        //Inserir compra
        Compra compra = new Compra((int)obj_compra[0], (int)obj_compra[1], (int)obj_compra[2], (int)obj_compra[3], (double)obj_compra[4], (double)obj_compra[5], (LocalDate)obj_compra[6]);
        if(compra.salvar())
        {
            int cod = compra.getSequence();
            compra.setCod_compra(cod);
            ItensCompra itens;
            
            //Inserir itensCompra
            try
            {
                for (int i = 0; i < obj_itens.size(); i++) {
                    itens = new ItensCompra(cod, (int)obj_itens.get(i)[0], (double)obj_itens.get(i)[2], (int)obj_itens.get(i)[3]);
                    itens.salvar();
                }
            }catch(Exception er){
                System.out.println("Erro gravar itensCompra\nErro: " + er.getMessage());
                return false;
            }
            
            //Inserir parcelasCompra
            try
            {
                ParcelaCompra parc;
                LocalDate data = (LocalDate)obj_parcela[2];
                int qtdParcelas = (int)obj_compra[3];
                for (int i = 0; i < qtdParcelas; i++) {
                    parc = new ParcelaCompra(i + 1, (int)obj_parcela[1], data, i + 1, cod);
                    parc.salvar();
                    data = data.getMonthValue() == 12 ? 
                            LocalDate.of(data.getYear() + 1, 1, data.getDayOfMonth()):
                            LocalDate.of(data.getYear(), data.getMonthValue() + 1, data.getDayOfMonth());

            }
            }catch(Exception er){
                System.out.println("Erro gravar parcelasCompra\nErro: " + er.getMessage());
                return false;
            }
            
            return true;
        }
        return false;
        
    }
    
    public ArrayList<Object[]> buscar(String str)
    {
        Compra compra = new Compra();
        ParcelaCompra parcela_compra = new ParcelaCompra();
        
        ArrayList<Object[]> list_prods = compra.buscar(str);
        ArrayList<Object[]> list_parcelas;
        
        for (int i = 0; i < list_prods.size(); i++) {
            list_parcelas = new ArrayList<>();
            list_parcelas = parcela_compra.buscar((int)list_prods.get(i)[0]);
            for (int j = 0; j < list_parcelas.size(); j++) {
                if(list_parcelas.get(j)[3] == null)
                    list_parcelas.get(j)[3] = "nulo";
                if(list_parcelas.get(j)[4] == null)
                    list_parcelas.get(j)[4] = 1;
            }
            list_prods.get(i)[6] = list_parcelas;
        }
        
        return list_prods;
    }
}
