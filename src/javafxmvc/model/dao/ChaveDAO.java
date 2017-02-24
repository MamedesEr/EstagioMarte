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

/**
 *
 * @author Mattheus
 */
public class ChaveDAO {
    private Connection connection;
    String nome;
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public boolean inserir(Chave chave) {
        String sql = "INSERT INTO chave(identificador, descricao)"
                +    "VALUES(?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, chave.getDescricao());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ChaveDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean alterar(Chave chave) {
        String sql = "UPDATE chave SET identificador=?, descricao=?"
                + " WHERE id_cargo=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, chave.getIdentificador());
            stmt.setString(2, chave.getDescricao());
            stmt.setInt(3, chave.getIdChave());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ChaveDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean remover(Chave chave) {
        String sql = "DELETE FROM chave WHERE id_chave=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, chave.getIdChave());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ChaveDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public List<Chave> listar() {
        String sql = "SELECT * FROM chave ORDER BY identificador";
        List<Chave> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Chave chave = new Chave();
                chave.setIdChave(resultado.getInt("id_chave"));
                chave.setIdentificador(resultado.getString("identificador"));
                chave.setDescricao(resultado.getString("descricao"));
                retorno.add(chave);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChaveDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public Chave buscar(Chave chave) {
        String sql = "SELECT * FROM chave WHERE id_chave=?";
        Chave retorno = new Chave();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, chave.getIdChave());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                chave.setIdentificador(resultado.getString("identificador"));
                chave.setDescricao(resultado.getString("descricao"));
                retorno = chave;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChaveDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public List<Chave> pesquisar(String nome) {
        this.nome = nome;
        String sql = "SELECT * FROM  chave WHERE UPPER(IDENTIFICADOR) LIKE '%"+ nome +"%' ORDER BY identificador";
        List<Chave> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Chave chave = new Chave();
                chave.setIdChave(resultado.getInt("id_chave"));
                chave.setIdentificador(resultado.getString("identificador"));
                chave.setDescricao(resultado.getString("descricao"));
                retorno.add(chave);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChaveDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}