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

	public boolean inserirLivro(LivroDTO livro) {
		Connection conexao = null;
		PreparedStatement statement = null;
		int resultado;
		
		try {
			conexao = dataSource.getConnection();
			
			String sql = "INSERT INTO `livro`"
					+ "(`titulo`, `autor`, `genero`, `editora`, `linguas`, `avaliacao`, `anoLancamento`, `qtdPaginasTotal`, `qtdPaginasLidas`) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
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
			
			resultado = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			resultado = 0;
		} finally {
			fecharConexao(conexao, statement, null);
		}
		
		return resultado == 1;
	}
	
	public boolean modificarLivro(LivroDTO livro) {
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
	
	public ArrayList<LivroDTO> consultarLivros() {
		ArrayList<LivroDTO> listaLivro = new ArrayList<>();
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT * FROM livro";
			statement = conexao.prepareStatement(sql);
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
				
				LivroDTO tarefa = new LivroDTO(id, titulo, autor, genero, editora, linguas, avaliacao, anoLancamento, qtdPaginasTotal, qtdPaginasLidas);
				listaLivro.add(tarefa);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao(conexao, statement, resultado);
		}
		return listaLivro;
	}
	
	public LivroDTO procurarLivro(int id) {
		LivroDTO livro = null;
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT * FROM livro WHERE id=?";
			statement = conexao.prepareStatement(sql);
			statement.setInt(1, id);
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
				
				livro = new LivroDTO(id, titulo, autor, genero, editora, linguas, avaliacao, anoLancamento, qtdPaginasTotal, qtdPaginasLidas);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao(conexao, statement, resultado);
		}
		return livro;
	}
	
	public boolean excluirLivro(int id) {
		Connection conexao = null;
		PreparedStatement statement = null;
		int resultado;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "DELETE FROM livro WHERE id = ?";
			statement = conexao.prepareStatement(sql);
			statement.setInt(1, id);
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
