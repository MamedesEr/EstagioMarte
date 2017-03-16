/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmvc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxmvc.model.domain.Chave;
import javafxmvc.model.domain.Emprestimo;
import javafxmvc.model.domain.Pessoa;
import javafxmvc.model.domain.Usuario;

/**
 *
 * @author DPPG
 */
public class EmprestimoDAO 
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
    
    public boolean inserir(Emprestimo emprestimo)
    {
        String sql = "INSERT INTO emprestimo(dt_emprestimo, hr_emprestimo, descricao, dt_devolucao,"
                + " hr_devolucao, dt_prev_entrega hr_prev_entrega)VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate    (1,emprestimo.getDtEmprestimo());
            stmt.setTime    (2,emprestimo.getHrEmprestimo());
            stmt.setString  (3,emprestimo.getDescricao());
            stmt.setDate    (4, emprestimo.getDtDevolucao());
            stmt.setTime    (5, emprestimo.getHrDevolucao());
            stmt.setDate    (6, emprestimo.getDtPrevisaoEntrega());
            stmt.setTime    (7, emprestimo.getHrPrevisaoEntrega());
            stmt.execute    ();
            return true;
        } catch (SQLException ex)
        {
            Logger.getLogger(EmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean alterar(Emprestimo emprestimo)   
    {
        String sql = "UPDATE emprestimo SET descricao=?"
                + " WHERE id_emprestimo=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString  (1, emprestimo.getDescricao());
            stmt.setInt     (2, emprestimo.getIdEmprestimo());
            stmt.setDate    (3,emprestimo.getDtEmprestimo() );
            stmt.setTime    (4,emprestimo.getHrEmprestimo());
            stmt.setString  (5,emprestimo.getDescricao());
            stmt.setDate    (6, emprestimo.getDtDevolucao());
            stmt.setTime    (7, emprestimo.getHrDevolucao());
            stmt.setDate    (8, emprestimo.getDtPrevisaoEntrega());
            stmt.setTime    (9, emprestimo.getHrPrevisaoEntrega());
            
            stmt.execute();
            return true;
        } catch (SQLException ex)   
        {
            Logger.getLogger(EmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean remover(Emprestimo emprestimo)
    {
        String sql = "DELETE FROM emprestimo WHERE id_emprestimo =?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, emprestimo.getIdEmprestimo());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public List<Emprestimo> listar()
    {
        String sql = "SELECT * FROM emprestimo ORDER BY descricao";
        List<Emprestimo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Usuario usuario = new Usuario();
                Chave chave = new Chave();
                Pessoa pessoa = new Pessoa();
                Emprestimo emprestimo = new Emprestimo();
                
                emprestimo.setIdEmprestimo(resultado.getInt("id_emprestimo"));
                emprestimo.setDescricao(resultado.getString("descricao"));
                emprestimo.setDtDevolucao(resultado.getDate("dt_devolucao"));
                emprestimo.setHrDevolucao(resultado.getTime("hr_devolucao"));
                emprestimo.setDtEmprestimo(resultado.getDate("dt_emprestimo"));
                emprestimo.setHrEmprestimo(resultado.getTime("hr_emprestimo"));
                emprestimo.setDtPrevisaoEntrega(resultado.getDate("dt_prev_entrega"));
                emprestimo.setHrPrevisaoEntrega(resultado.getTime("hr_prev_devolucao"));
                
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuarioDAO.setConnection(connection);                
                usuario = usuarioDAO.buscar(usuario);
                
                PessoaDAO pessoaDAO = new PessoaDAO();
                pessoaDAO.setConnection(connection);                
                pessoa = pessoaDAO.buscar(pessoa);
                
                ChaveDAO chaveDao = new ChaveDAO();
                chaveDao.setConnection(connection);
                chave = chaveDao.buscar(chave);
                
                emprestimo.setUsuario(usuario);
                emprestimo.setPessoa(pessoa);
                emprestimo.setChave(chave);
                retorno.add(emprestimo);             
                
                               
               }
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public Emprestimo buscar(Emprestimo emprestimo) 
    {
        String sql = "SELECT * FROM emprestimo WHERE id_emprestimo=?";
        Emprestimo retorno = new Emprestimo();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, emprestimo.getIdEmprestimo());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                emprestimo.setDescricao(resultado.getString("descricao"));
                emprestimo.setDtDevolucao(resultado.getDate("dt_devolucao"));
                emprestimo.setHrDevolucao(resultado.getTime("hr_devolucao"));
                emprestimo.setDtEmprestimo(resultado.getDate("dt_emprestimo"));
                emprestimo.setHrEmprestimo(resultado.getTime("hr_emprestimo"));
                emprestimo.setDtPrevisaoEntrega(resultado.getDate("hr_prev_entrega"));
                emprestimo.setHrPrevisaoEntrega(resultado.getTime("hr_prev_devolucao"));
                
                retorno = emprestimo;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public List<Emprestimo> pesquisar(String nome)
    {
        this.nome = nome;
        String sql = "SELECT * FROM  emprestimo WHERE UPPER(DESCRICAO) LIKE '%"+ nome +"%' ORDER BY descricao";
        List<Emprestimo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setIdEmprestimo      (resultado.getInt("id_emprestimo"));
                emprestimo.setDescricao         (resultado.getString("descricao"));
                emprestimo.setDtEmprestimo      (resultado.getDate("dt_emprestimo"));
                emprestimo.setHrEmprestimo      (resultado.getTime("hr_emprestimo"));
                emprestimo.setDtDevolucao       (resultado.getDate("dt_devolucao"));
                emprestimo.setHrDevolucao       (resultado.getTime("hr_devolucao"));
                emprestimo.setDtPrevisaoEntrega (resultado.getDate("dt_prev_entrega"));
                emprestimo.setHrPrevisaoEntrega (resultado.getTime("hr_prev_entrega"));
                retorno.add(emprestimo);
            }
        } catch (SQLException ex) 
        {
            Logger.getLogger(EmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
