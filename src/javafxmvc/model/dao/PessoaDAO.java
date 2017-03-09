/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmvc.model.dao;

import java.sql.Connection;

/**
 *
 * @author DPPG
 */
public class PessoaDAO 
{
     private Connection connection;
    String nome;
    
    public Connection getConnection()
    {
        return connection;
    }

    public void setConnection(Connection connection)
    {
        this.connection = connection;
    }
}
