package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import javax.sql.DataSource;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.LivroDAO;
import model.LivroDTO;

@WebServlet("/Livro")
public class LivroController extends HttpServlet {
	
	private static final long serialVersionUID = 820928843794152937L;
	
	private LivroDAO lDAO;
	@Resource(name="bancoLivros")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException{
		lDAO = new LivroDAO(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String operacao = request.getParameter("operacao");
    	operacao = operacao.toLowerCase();
		
		switch (operacao) {
			case "listar":
				listarLivro(request, response);
				break;
			case "remover":
				excluirLivro(request, response);
				break;
			case "editar":
				modificarLivro(request, response);
				break;
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String operacao = request.getParameter("operacao");
    	operacao = operacao.toLowerCase();
		
		switch (operacao) {
			case "adicionar":
				cadastrarLivro(request, response);
				break;
			case "atualizar":
				atualizarLivro(request, response);
				break;
		}
	}
	
	private void cadastrarLivro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String titulo = request.getParameter("titulo");
		String autor = request.getParameter("autor");
		String genero = request.getParameter("genero");
		String editora = request.getParameter("editora");
		String linguas = request.getParameter("linguas");
		String avaliacao = request.getParameter("avaliacao");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		LocalDate anoLancamento = LocalDate.now();
		
		try {
			anoLancamento = formatter.parse(request.getParameter("anoLancamento")).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		} catch (ParseException e) {
			e.printStackTrace();
		};
		
		double qtdPgsTotal = Double.parseDouble(request.getParameter("qtdPgsTotal"));
		double qtdPgsLidas = Double.parseDouble(request.getParameter("qtdPgsLidas"));
		
		LivroDTO livro = new LivroDTO(0, titulo, autor, genero, editora, linguas, avaliacao, anoLancamento, qtdPgsTotal, qtdPgsLidas);
		
		boolean status = lDAO.inserirLivro(livro);
		
		request.setAttribute("status", status);
		request.setAttribute("operacao", "inseri");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/status.jsp");
		dispatcher.forward(request, response);
	}
	
	private void atualizarLivro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String titulo = request.getParameter("titulo");
		String autor = request.getParameter("autor");
		String genero = request.getParameter("genero");
		String editora = request.getParameter("editora");
		String linguas = request.getParameter("linguas");
		String avaliacao = request.getParameter("avaliacao");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		LocalDate anoLancamento = LocalDate.now();
		
		try {
			anoLancamento = formatter.parse(request.getParameter("anoLancamento")).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		} catch (ParseException e) {
			e.printStackTrace();
		};
		
		double qtdPgsTotal = Double.parseDouble(request.getParameter("qtdPgsTotal"));
		double qtdPgsLidas = Double.parseDouble(request.getParameter("qtdPgsLidas"));
		
		LivroDTO livro = new LivroDTO(id, titulo, autor, genero, editora, linguas, avaliacao, anoLancamento, qtdPgsTotal, qtdPgsLidas);
		
		boolean status = lDAO.modificarLivro(livro);
		
		request.setAttribute("status", status);
		request.setAttribute("operacao", "atualiza");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/status.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listarLivro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<LivroDTO> livros = new ArrayList<>();
		
		livros = lDAO.consultarLivros();
		
		request.setAttribute("lista", livros);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/lista.jsp");
		dispatcher.forward(request, response);
	}

	private void excluirLivro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean status = lDAO.excluirLivro(Integer.parseInt(request.getParameter("id")));
		
		request.setAttribute("status", status);
		request.setAttribute("operacao", "exclui");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/status.jsp");
		dispatcher.forward(request, response);
	}
	
	private void modificarLivro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivroDTO livro = lDAO.procurarLivro(Integer.parseInt(request.getParameter("id")));
		
		request.setAttribute("livro", livro);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/edit.jsp");
		dispatcher.forward(request, response);
	}
}
