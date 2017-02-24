package javafxmvc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        String sql = "INSERT INTO usuario(login, senha, nome, cpf, telefone, email)"
                +    "VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getNome());
            stmt.setString(4, usuario.getCpf());
            stmt.setString(5, usuario.getTelefone());
            stmt.setString(6, usuario.getEmail());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
