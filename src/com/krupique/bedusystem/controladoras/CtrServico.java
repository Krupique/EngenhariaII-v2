
package com.krupique.bedusystem.controladoras;


import com.krupique.bedusystem.entidades.Servico;
import java.sql.SQLException;
import java.util.ArrayList;

public class CtrServico
{

    public static ArrayList<Object> getAll(String filtro)
    {
        try
        {
            return Servico.get(filtro);
        } catch (SQLException ex)
        {
            return null;
        }
    }
}
