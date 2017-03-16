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
import javafxmvc.model.domain.Departamento;
import javafxmvc.model.domain.Usuario;

/**
 *
 * @author Mattheus
 */
public class UsuarioDAO {
    
    private Connection connection;
    String nome;
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public boolean inserir(Usuario usuario) {
        String sql = "INSERT INTO usuario(login, senha, nome, cpf, telefone, email, id_departamento, id_cargo)"
                +    "VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getNome());
            stmt.setString(4, usuario.getCpf());
            stmt.setString(5, usuario.getTelefone());
            stmt.setString(6, usuario.getEmail());
            stmt.setInt(7, usuario.getDepartamento().getIdDepartamento());
            stmt.setInt(8, usuario.getCargo().getIdCargo());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean alterar(Usuario usuario) {
        String sql = "UPDATE Usuario SET login=?, senha=?, nome=?, cpf=?, telefone=?, email=?, id_departamento=?, "
                + "id_cargo=? WHERE id_usuario=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getNome());
            stmt.setString(4, usuario.getCpf());
            stmt.setString(5, usuario.getTelefone());
            stmt.setString(6, usuario.getEmail());
            stmt.setInt(7, usuario.getDepartamento().getIdDepartamento());
            stmt.setInt(8, usuario.getCargo().getIdCargo());
            stmt.setInt(9, usuario.getIdUsuario());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean remover(Usuario usuario) {
        String sql = "DELETE FROM usuario WHERE id_usuario=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, usuario.getIdUsuario());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public List<Usuario> listar() {
        String sql = "SELECT * FROM usuario";
        List<Usuario> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Usuario usuario = new Usuario();
                Departamento departamento = new Departamento();
                Cargo cargo = new Cargo();
                usuario.setIdUsuario(resultado.getInt("id_usuario"));
                usuario.setLogin(resultado.getString("login"));
                usuario.setSenha(resultado.getString("senha"));
                usuario.setNome(resultado.getString("nome"));
                usuario.setCpf(resultado.getString("cpf"));
                usuario.setTelefone(resultado.getString("telefone"));
                usuario.setEmail(resultado.getString("email"));
                departamento.setIdDepartamento(resultado.getInt("id_departamento"));
                cargo.setIdCargo(resultado.getInt("id_cargo"));
                
                //Obtendo os dados completos de Departamento associada ao Usuario
                DepartamentoDAO departamentoDAO = new DepartamentoDAO();
                departamentoDAO.setConnection(connection);
                departamento = departamentoDAO.buscar(departamento);         
                
                //Obtendo os dados completos de cargo associada ao Usuario
                CargoDAO cargoDAO = new CargoDAO();
                cargoDAO.setConnection(connection);
                cargo = cargoDAO.buscar(cargo);
                
                usuario.setDepartamento(departamento);
                usuario.setCargo(cargo);
                retorno.add(usuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public Usuario buscar(Usuario usuario) {
        String sql = "SELECT * FROM usuario WHERE id_usuario=?";
        Usuario retorno = new Usuario();
        Departamento departamento = new Departamento();
        Cargo cargo = new Cargo();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, usuario.getIdUsuario());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                usuario.setIdUsuario(resultado.getInt("id_usuario"));
                usuario.setLogin(resultado.getString("login"));
                usuario.setSenha(resultado.getString("senha"));
                usuario.setNome(resultado.getString("nome"));
                usuario.setCpf(resultado.getString("cpf"));
                usuario.setTelefone(resultado.getString("telefone"));
                usuario.setEmail(resultado.getString("email"));
                departamento.setIdDepartamento(resultado.getInt("id_departamento"));
                cargo.setIdCargo(resultado.getInt("id_cargo"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
