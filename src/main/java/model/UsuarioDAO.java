package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.sql.DataSource;

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
			
			String sql = "INSERT INTO `usuario`(`id`, `login`, `password`, `email`, `cpf`, `telefone`) VALUES (?,?,?,?,?,?)";
			
			statement = conexao.prepareStatement(sql);
			statement.setString(1, UUID.randomUUID().toString());
			statement.setString(2, usuario.login());
			statement.setString(3, usuario.password());
			statement.setString(4, usuario.email());
			statement.setString(5, usuario.cpf());
			statement.setString(6, usuario.telefone());
			
			resultado = (statement.executeUpdate() == 1) ? "Cadastro foi realizado com sucesso!" : "Ocorreu um erro";
		} catch (SQLException e) {
			e.printStackTrace();
			resultado = "Ocorreu um erro";
		} finally {
			fecharConexao(conexao, statement, null);
		}
		
		return resultado;
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
