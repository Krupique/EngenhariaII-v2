/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krupique.bedusystem.controladoras;

import com.krupique.bedusystem.entidades.StatusOS;
import java.util.ArrayList;

/**
 *
 * @author Caique
 */
public class ctrStatusOS
{

    public ArrayList<Object[]> procurar(int filtro)
    {
        ArrayList<Object[]> usuarios = new ArrayList<>();
        ArrayList<StatusOS> aux = new ArrayList<>();
        Object[] obj,object,object2;
        StatusOS s;
        
        aux = new StatusOS().get(filtro);
        
        for (int i = 0; i < aux.size(); i++)
        {
            obj = new Object[3];

            obj[0] = aux.get(i).getStatus().getDescricao();
            obj[1] = aux.get(i).getData();
            obj[2] = aux.get(i).getFuncionarios().getNome();
            usuarios.add(obj);
        }
        return usuarios;
    }
    
}
