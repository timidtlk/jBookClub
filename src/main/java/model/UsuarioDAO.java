package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.sql.DataSource;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class UsuarioDAO {
    private DataSource dataSource;
	
	public UsuarioDAO(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

    public String cadastrarUsuario(Usuario usuario) {
        Connection conexao = null;
		PreparedStatement statement = null;
		String resultado;
		
		try {
			conexao = dataSource.getConnection();
			
			String sql = "INSERT INTO `usuario`(`id`, `login`, `password`, `email`, `cpf`, `telefone`, `nome`) VALUES (?,?,?,?,?,?,?)";
			
			statement = conexao.prepareStatement(sql);
			statement.setString(1, UUID.randomUUID().toString());
			statement.setString(2, usuario.login());
			statement.setString(3, usuario.password());
			statement.setString(4, usuario.email());
			statement.setString(5, usuario.cpf());
			statement.setString(6, usuario.telefone());
			statement.setString(7, usuario.nome());
			
			resultado = (statement.executeUpdate() == 1) ? "Cadastro foi realizado com sucesso!" : "Ocorreu um erro";
		} catch (SQLException e) {
			e.printStackTrace();
			resultado = "Ocorreu um erro";
		} finally {
			fecharConexao(conexao, statement, null);
		}
		
		return resultado;
    }

	public Usuario existeUsuario(String login, String email) {
		Usuario usuario = null;
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT login, email FROM usuario WHERE login = ? OR email = ?";
			statement = conexao.prepareStatement(sql);
			statement.setString(1,  login);
			statement.setString(2,  email);
			resultado = statement.executeQuery();
			
			if(resultado.next()) {
				usuario = new Usuario(login, email);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao(conexao, statement, resultado);
		}
		return usuario;
	}

	public boolean excluirUsuario(String id) {
		Connection conexao = null;
		PreparedStatement statement = null;
		int resultado = 0;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "DELETE FROM `usuario` WHERE id = ?";
			statement = conexao.prepareStatement(sql);
			statement.setString(1,  id);
			resultado = statement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao(conexao, statement, null);
		}
		return (resultado == 1) ? true : false;
	}

	public Usuario fazerLogin(String login, String senha) {
		Usuario usuario = null;
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT * FROM usuario WHERE login = ?";
			statement = conexao.prepareStatement(sql);
			statement.setString(1,  login);
			resultado = statement.executeQuery();
			
			if(resultado.next()) {
				if (BCrypt.verifyer().verify(senha.toCharArray(), resultado.getString("password").toCharArray()).verified) {
					String id = resultado.getString("id");
					String nome = resultado.getString("nome");
					String email = resultado.getString("email");
					String cpf = resultado.getString("cpf");
					String telefone = resultado.getString("telefone");
					String password = resultado.getString("password");

					usuario = new Usuario(id, nome, login, email, cpf, telefone, password);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao(conexao, statement, resultado);
		}
		return usuario;
	}

    private void fecharConexao(Connection conexao, PreparedStatement statement, ResultSet resultado) {
		try {
			if (conexao != null)
				conexao.close();
			if (statement != null)
				statement.close();
			if (resultado != null)
				resultado.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
