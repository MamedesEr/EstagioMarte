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
import javafxmvc.model.domain.Emprestimo;
import javafxmvc.model.domain.Pessoa;

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

    public boolean inserir(Pessoa pessoa) 
    {
        String sql = "INSERT INTO pessoa(nome, cpf, telefone, email) "
                + "VALUES(?,?,?,?)";
        try
        {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getCpf());
            stmt.setString(3, pessoa.getTelefone());
            stmt.setString(4, pessoa.getEmail());
            stmt.execute();
            return true;
        } catch (SQLException ex) 
        {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean alterar(Pessoa pessoa)
    {
        String sql = "UPDATE pessoaSET nome=?, cpf=?, telefone=?, email=? "
                + "WHERE id_pessoa=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getCpf());
            stmt.setString(3, pessoa.getTelefone());
            stmt.setString(4, pessoa.getEmail());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean remover(Pessoa pessoa) 
    {
        String sql = "DELETE FROM pessoa WHERE id_pessoa=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, pessoa.getIdPessoa());
            stmt.execute();
            return true;
        } catch (SQLException ex)
        {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public List<Pessoa> listar() 
    {
        String sql = "SELECT * FROM Pessoa ORDER BY nome";
        List<Pessoa> retorno = new ArrayList<>();
        try 
        {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) 
            {   
                Pessoa pessoa = new Pessoa();
                pessoa.setIdPessoa      (resultado.getInt("id_pessoa"));
                pessoa.setNome          (resultado.getString("nome"));
                pessoa.setCpf           (resultado.getString("cpf"));
                pessoa.setTelefone      (resultado.getString("telefone"));
                pessoa.setEmail         (resultado.getString("email"));
                retorno.add(pessoa);
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public  Pessoa buscar(Pessoa pessoa) {
        String sql = "SELECT * FROM pessoa WHERE id_pessoa=?";
        Pessoa retorno = new Pessoa();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, pessoa.getIdPessoa());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) 
            {
                pessoa.setNome      (resultado.getString("nome"));
                pessoa.setCpf       (resultado.getString("cpf"));
                pessoa.setTelefone  (resultado.getString("telefone"));
                pessoa.setEmail     (resultado.getString("email"));
                retorno = pessoa;
            }
        } catch (SQLException ex) 
        {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public List<Pessoa> pesquisar(String nome)
    {
        this.nome = nome;
        String sql = "SELECT * FROM  pessoa WHERE UPPER(NOME) LIKE '%"+ nome +"%' ORDER BY nome";
        List<Pessoa> retorno = new ArrayList<>();
        try 
        {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next())
            {
                Pessoa pessoa               = new Pessoa();
                Emprestimo emprestimo       = new Emprestimo();
                Cargo cargo                 = new Cargo();
                Departamento departamento   = new Departamento();
                
                pessoa.setIdPessoa  (resultado.getInt("id_pessoa"));
                pessoa.setNome      (resultado.getString("nome"));
                pessoa.setCpf       (resultado.getString("cpf"));
                pessoa.setTelefone  (resultado.getString("telefone"));
                pessoa.setEmail     (resultado.getString("email"));
                
                EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
                emprestimoDAO.setConnection(connection);
                emprestimo = emprestimoDAO.buscar(emprestimo);
                
                CargoDAO cargoDAO = new CargoDAO();
                cargoDAO.setConnection(connection);
                cargo = cargoDAO.buscar(cargo);
                
                DepartamentoDAO departamentoDAO = new DepartamentoDAO();
                departamentoDAO.setConnection(connection);
                departamento = departamentoDAO.buscar(departamento);
                retorno.add(pessoa);
            }
        } catch (SQLException ex) 
        {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
}


