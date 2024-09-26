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
import jakarta.servlet.http.HttpSession;
import model.LivroDAO;
import model.Usuario;
import model.Livro;

@WebServlet("/LivroController")
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

		HttpSession session = request.getSession();

		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		double qtdPgsTotal = Double.parseDouble(request.getParameter("qtdPgsTotal"));
		double qtdPgsLidas = Double.parseDouble(request.getParameter("qtdPgsLidas"));
		
		Livro livro = new Livro(0, titulo, autor, genero, editora, linguas, avaliacao, anoLancamento, qtdPgsTotal, qtdPgsLidas, usuario.id());
		
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

		HttpSession session = request.getSession();

		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		Livro livro = new Livro(id, titulo, autor, genero, editora, linguas, avaliacao, anoLancamento, qtdPgsTotal, qtdPgsLidas, usuario.id());
		
		boolean status = lDAO.modificarLivro(livro);
		
		request.setAttribute("status", status);
		request.setAttribute("operacao", "atualiza");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/status.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listarLivro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Livro> livros = new ArrayList<>();
		
		HttpSession session = request.getSession();

		Usuario usuario = (Usuario) session.getAttribute("usuario");

		livros = lDAO.consultarLivros(usuario.id());
		
		request.setAttribute("lista", livros);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/lista.jsp");
		dispatcher.forward(request, response);
	}

	private void excluirLivro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		boolean status = lDAO.excluirLivro(Integer.parseInt(request.getParameter("id")), usuario.id());
		
		request.setAttribute("status", status);
		request.setAttribute("operacao", "exclui");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/status.jsp");
		dispatcher.forward(request, response);
	}
	
	private void modificarLivro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		Livro livro = lDAO.procurarLivro(Integer.parseInt(request.getParameter("id")), usuario.id());
		
		request.setAttribute("livro", livro);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/edit.jsp");
		dispatcher.forward(request, response);
	}
}
