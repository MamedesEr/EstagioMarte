package javafxmvc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxmvc.model.domain.Cargo;

/**
 *
 * @author Mattheus
 */
public class CargoDAO {
    private Connection connection;
    String nome;
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public boolean inserir(Cargo cargo) {
        String sql = "INSERT INTO cargo(descricao)"
                +    "VALUES(?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cargo.getDescricao());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean alterar(Cargo cargo) {
        String sql = "UPDATE cargo SET descricao=?"
                + " WHERE id_cargo=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cargo.getDescricao());
            stmt.setInt(2, cargo.getIdCargo());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean remover(Cargo cargo) {
        String sql = "DELETE FROM cargo WHERE id_cargo=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cargo.getIdCargo());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public List<Cargo> listar() {
        String sql = "SELECT * FROM cargo ORDER BY descricao";
        List<Cargo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Cargo cargo = new Cargo();
                cargo.setIdCargo(resultado.getInt("id_cargo"));
                cargo.setDescricao(resultado.getString("descricao"));
                retorno.add(cargo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public Cargo buscar(Cargo cargo) {
        String sql = "SELECT * FROM cargo WHERE id_cargo=?";
        Cargo retorno = new Cargo();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cargo.getIdCargo());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                cargo.setDescricao(resultado.getString("descricao"));
                retorno = cargo;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public List<Cargo> pesquisar(String nome) {
        this.nome = nome;
        String sql = "SELECT * FROM  cargo WHERE UPPER(DESCRICAO) LIKE '%"+ nome +"%' ORDER BY descricao";
        List<Cargo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Cargo cargo = new Cargo();
                cargo.setIdCargo(resultado.getInt("id_cargo"));
                cargo.setDescricao(resultado.getString("descricao"));
                retorno.add(cargo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
