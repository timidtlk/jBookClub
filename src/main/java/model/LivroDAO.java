package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.sql.DataSource;

public class LivroDAO {
	private DataSource dataSource;
	
	public LivroDAO(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	public boolean inserirLivro(Livro livro) {
		Connection conexao = null;
		PreparedStatement statement = null;
		int resultado;
		
		try {
			conexao = dataSource.getConnection();
			
			String sql = "INSERT INTO `livro`"
					+ "(`titulo`, `autor`, `genero`, `editora`, `linguas`, `avaliacao`, `anoLancamento`, `qtdPaginasTotal`, `qtdPaginasLidas`, `login_id`) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			statement = conexao.prepareStatement(sql);
			statement.setString(1, livro.getTitulo());
			statement.setString(2, livro.getAutor());
			statement.setString(3, livro.getGenero());
			statement.setString(4, livro.getEditora());
			statement.setString(5, livro.getLinguas());
			statement.setString(6, livro.getAvaliacao());
			statement.setDate(7, Date.valueOf(livro.getAnoLancamento()));
			statement.setDouble(8, livro.getQtdPaginasTotal());
			statement.setDouble(9, livro.getQtdPaginasLidas());
			statement.setString(10, livro.getLoginId());
			
			resultado = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			resultado = 0;
		} finally {
			fecharConexao(conexao, statement, null);
		}
		
		return resultado == 1;
	}
	
	public boolean modificarLivro(Livro livro) {
		Connection conexao = null;
		PreparedStatement statement = null;
		int resultado;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "UPDATE `livro` SET `titulo`=?,`autor`=?,`genero`=?,`editora`=?,`linguas`=?,`avaliacao`=?,`anoLancamento`=?,`qtdPaginasTotal`=?,`qtdPaginasLidas`=? WHERE id=?";
			statement = conexao.prepareStatement(sql);
			statement.setString(1, livro.getTitulo());
			statement.setString(2, livro.getAutor());
			statement.setString(3, livro.getGenero());
			statement.setString(4, livro.getEditora());
			statement.setString(5, livro.getLinguas());
			statement.setString(6, livro.getAvaliacao());
			statement.setDate(7, Date.valueOf(livro.getAnoLancamento()));
			statement.setDouble(8, livro.getQtdPaginasTotal());
			statement.setDouble(9, livro.getQtdPaginasLidas());
			statement.setInt(10, livro.getId());
			resultado = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			resultado = 0;
		} finally {
			fecharConexao(conexao, statement, null);
		}
		
		return resultado == 1;
	}
	
	public ArrayList<Livro> consultarLivros(String login_id) {
		ArrayList<Livro> listaLivro = new ArrayList<>();
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT * FROM livro WHERE login_id = ?";
			statement = conexao.prepareStatement(sql);
			statement.setString(1, login_id);
			resultado = statement.executeQuery();
			
			while (resultado.next()) {
				int id = resultado.getInt("id");
				String titulo = resultado.getString("titulo");
				String autor = resultado.getString("autor");
				String genero = resultado.getString("genero");
				String editora = resultado.getString("editora");
				String linguas = resultado.getString("linguas");
				String avaliacao = resultado.getString("avaliacao");
				LocalDate anoLancamento = resultado.getDate("anoLancamento").toLocalDate();
				double qtdPaginasTotal = resultado.getDouble("qtdPaginasTotal");
				double qtdPaginasLidas = resultado.getDouble("qtdPaginasLidas");
				
				Livro tarefa = new Livro(id, titulo, autor, genero, editora, linguas, avaliacao, anoLancamento, qtdPaginasTotal, qtdPaginasLidas, login_id);
				listaLivro.add(tarefa);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao(conexao, statement, resultado);
		}
		return listaLivro;
	}
	
	public Livro procurarLivro(int id, String login_id) {
		Livro livro = null;
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT * FROM livro WHERE id=? AND login_id = ?";
			statement = conexao.prepareStatement(sql);
			statement.setInt(1, id);
			statement.setString(2, login_id);
			resultado = statement.executeQuery();
			
			while (resultado.next()) {

				String titulo = resultado.getString("titulo");
				String autor = resultado.getString("autor");
				String genero = resultado.getString("genero");
				String editora = resultado.getString("editora");
				String linguas = resultado.getString("linguas");
				String avaliacao = resultado.getString("avaliacao");
				LocalDate anoLancamento = resultado.getDate("anoLancamento").toLocalDate();
				double qtdPaginasTotal = resultado.getDouble("qtdPaginasTotal");
				double qtdPaginasLidas = resultado.getDouble("qtdPaginasLidas");
				
				livro = new Livro(id, titulo, autor, genero, editora, linguas, avaliacao, anoLancamento, qtdPaginasTotal, qtdPaginasLidas, login_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao(conexao, statement, resultado);
		}
		return livro;
	}
	
	public boolean excluirLivro(int id, String login_id) {
		Connection conexao = null;
		PreparedStatement statement = null;
		int resultado;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "DELETE FROM livro WHERE id = ? AND login_id = ?";
			statement = conexao.prepareStatement(sql);
			statement.setInt(1, id);
			statement.setString(2, login_id);
			resultado = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			resultado = 0;
		} finally {
			fecharConexao(conexao, statement, null);
		}
		
		return resultado == 1;
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
