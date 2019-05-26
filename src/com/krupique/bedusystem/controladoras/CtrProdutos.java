/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.controladoras;

import com.krupique.bedusystem.entidades.Classificacao;
import com.krupique.bedusystem.entidades.Produto;
import java.util.ArrayList;

/**
 *
 * @author Henrique K. Secchi
 */
public class CtrProdutos {
    Produto prod;
    
    public boolean salvar(Object[] obj, int flag)
    {
        prod = new Produto((int)obj[0], (String)obj[1], (double)obj[2], (int)obj[3], new Classificacao((int)obj[4], (String)obj[5]));
        return prod.salvar(flag);
    }
    
    public ArrayList<Object[]> buscar(String str)
    {
        Object[] obj;
        prod = new Produto();
        ArrayList<Object[]> list = new ArrayList<>();
        ArrayList<Produto> listAux = prod.buscar(str);
        
        for (int i = 0; i < listAux.size(); i++) {
            obj = new Object[6];
            obj[0] = listAux.get(i).getCodigo();
            obj[1] = listAux.get(i).getNome();
            obj[2] = listAux.get(i).getPreco();
            obj[3] = listAux.get(i).getQuantidade();
            obj[4] = listAux.get(i).getClassificacao().getCod();
            obj[5] = listAux.get(i).getClassificacao().getDescricao();
            
            list.add(obj);
        }
        
        return list;
    }
    
    public ArrayList<Object> buscarArmando(String s)
    {
        return new Produto().get(s);
    }
    
    public boolean excluir(int cod)
    {
        prod = new Produto(cod);
        return prod.excluir();
    }
}
