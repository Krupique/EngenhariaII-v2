package com.krupique.bedusystem.utilidades;
//Padrão Singleton
public class Banco {
    static public Conexao con;

    private Banco(){}
    
    public static boolean conectar()
    {   
        con = new Conexao();
        return con.conectar("jdbc:postgresql://localhost/","BeduSystem","postgres","postgres123");//conexão Henrique
    }
    
    public static Conexao getCon() {
        return con;
    }

    public static void setCon(Conexao con) {
        Banco.con = con;
    }
}
