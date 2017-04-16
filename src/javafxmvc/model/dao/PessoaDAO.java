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
        String sql = "INSERT INTO pessoa(nome, cpf, telefone, email, id_departamento, id_cargo) "
                + "VALUES(?,?,?,?,?,?)";
        try
        {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getCpf());
            stmt.setString(3, pessoa.getTelefone());
            stmt.setString(4, pessoa.getEmail());
            stmt.setInt(5, pessoa.getDepartamento().getIdDepartamento());
            stmt.setInt(6, pessoa.getCargo().getIdCargo());
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
        String sql = "UPDATE pessoa SET nome=?, cpf=?, telefone=?, email=?, id_departamento=?, id_cargo=?"
                + "WHERE id_pessoa=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getCpf());
            stmt.setString(3, pessoa.getTelefone());
            stmt.setString(4, pessoa.getEmail());
            stmt.setInt(5, pessoa.getDepartamento().getIdDepartamento());
            stmt.setInt(6, pessoa.getCargo().getIdCargo());
            stmt.setInt(7, pessoa.getIdPessoa());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
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
                Departamento departamento = new Departamento();
                Cargo cargo = new Cargo();
                
                pessoa.setIdPessoa      (resultado.getInt("id_pessoa"));
                pessoa.setNome          (resultado.getString("nome"));
                pessoa.setCpf           (resultado.getString("cpf"));
                pessoa.setTelefone      (resultado.getString("telefone"));
                pessoa.setEmail         (resultado.getString("email"));
                departamento.setIdDepartamento(resultado.getInt("id_departamento"));
                cargo.setIdCargo(resultado.getInt("id_cargo"));
                
                //Obtendo os dados completos de Departamento associada a Pessoa
                DepartamentoDAO departamentoDAO = new DepartamentoDAO();
                departamentoDAO.setConnection(connection);                
                departamento = departamentoDAO.buscar(departamento);
                
                //Obtendo os dados completos de cargo associada a Pessoa
                CargoDAO cargoDAO = new CargoDAO();
                cargoDAO.setConnection(connection);                
                cargo = cargoDAO.buscar(cargo);
                
                pessoa.setDepartamento(departamento);
                pessoa.setCargo(cargo);
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
                Departamento departamento = new Departamento();
                Cargo cargo = new Cargo();
                pessoa.setNome      (resultado.getString("nome"));
                pessoa.setCpf       (resultado.getString("cpf"));
                pessoa.setTelefone  (resultado.getString("telefone"));
                pessoa.setEmail     (resultado.getString("email"));
                departamento.setIdDepartamento(resultado.getInt("id_departamento"));
                cargo.setIdCargo(resultado.getInt("id_cargo"));
                pessoa.setDepartamento(departamento);
                pessoa.setCargo(cargo);
                retorno = pessoa;
            }
        } catch (SQLException ex) 
        {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
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
                Pessoa pessoa = new Pessoa();
                Departamento departamento = new Departamento();
                Cargo cargo = new Cargo();
                
                pessoa.setIdPessoa      (resultado.getInt("id_pessoa"));
                pessoa.setNome          (resultado.getString("nome"));
                pessoa.setCpf           (resultado.getString("cpf"));
                pessoa.setTelefone      (resultado.getString("telefone"));
                pessoa.setEmail         (resultado.getString("email"));
                departamento.setIdDepartamento(resultado.getInt("id_departamento"));
                cargo.setIdCargo(resultado.getInt("id_cargo"));
                
                //Obtendo os dados completos de Departamento associada a Pessoa
                DepartamentoDAO departamentoDAO = new DepartamentoDAO();
                departamentoDAO.setConnection(connection);                
                departamento = departamentoDAO.buscar(departamento);
                
                //Obtendo os dados completos de cargo associada a Pessoa
                CargoDAO cargoDAO = new CargoDAO();
                cargoDAO.setConnection(connection);                
                cargo = cargoDAO.buscar(cargo);
                
                pessoa.setDepartamento(departamento);
                pessoa.setCargo(cargo);
                retorno.add(pessoa);
            }
        } catch (SQLException ex) 
        {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
}


