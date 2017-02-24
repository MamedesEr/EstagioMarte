package javafxmvc.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxmvc.model.domain.Departamento;

/**
 *
 * @author Mattheus
 */
public class DepartamentoDAO {
    private Connection connection;
    String nome;
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Departamento departamento) {
        String sql = "INSERT INTO departamento(nome, sigla, chefia, telefone, email, observacao) "
                + "VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, departamento.getNome());
            stmt.setString(2, departamento.getSigla());
            stmt.setString(3, departamento.getChefia());
            stmt.setString(4, departamento.getTelefone());
            stmt.setString(5, departamento.getEmail());
            stmt.setString(6, departamento.getObservacao());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean alterar(Departamento departamento) {
        String sql = "UPDATE departamento SET nome=?, sigla=?, chefia=?, telefone=?, email=?, observacao=? "
                + "WHERE id_departamento=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, departamento.getNome());
            stmt.setString(2, departamento.getSigla());
            stmt.setString(3, departamento.getChefia());
            stmt.setString(4, departamento.getTelefone());
            stmt.setString(5, departamento.getEmail());
            stmt.setString(5, departamento.getObservacao());
            stmt.setInt(7, departamento.getIdDepartamento());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean remover(Departamento departamento) {
        String sql = "DELETE FROM departamento WHERE id_departamento=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, departamento.getIdDepartamento());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public List<Departamento> listar() {
        String sql = "SELECT * FROM departamento ORDER BY nome";
        List<Departamento> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Departamento departamento = new Departamento();
                departamento.setIdDepartamento(resultado.getInt("id_departamento"));
                departamento.setNome(resultado.getString("nome"));
                departamento.setSigla(resultado.getString("sigla"));
                departamento.setChefia(resultado.getString("chefia"));
                departamento.setTelefone(resultado.getString("telefone"));
                departamento.setEmail(resultado.getString("email"));
                departamento.setObservacao(resultado.getString("observacao"));
                retorno.add(departamento);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public Departamento buscar(Departamento departamento) {
        String sql = "SELECT * FROM departamento WHERE id_departamento=?";
        Departamento retorno = new Departamento();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, departamento.getIdDepartamento());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                departamento.setNome(resultado.getString("nome"));
                departamento.setSigla(resultado.getString("sigla"));
                departamento.setChefia(resultado.getString("chefia"));
                departamento.setTelefone(resultado.getString("telefone"));
                departamento.setEmail(resultado.getString("email"));
                departamento.setObservacao(resultado.getString("observacao"));
                retorno = departamento;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public List<Departamento> pesquisar(String nome) {
        this.nome = nome;
        String sql = "SELECT * FROM  departamento WHERE UPPER(NOME) LIKE '%"+ nome +"%' ORDER BY nome";
        List<Departamento> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Departamento departamento = new Departamento();
                departamento.setIdDepartamento(resultado.getInt("id_departamento"));
                departamento.setNome(resultado.getString("nome"));
                departamento.setSigla(resultado.getString("sigla"));
                departamento.setChefia(resultado.getString("chefia"));
                departamento.setTelefone(resultado.getString("telefone"));
                departamento.setEmail(resultado.getString("email"));
                departamento.setObservacao(resultado.getString("observacao"));
                retorno.add(departamento);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
