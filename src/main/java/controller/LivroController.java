package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entity.Livro;
import entity.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.LivroDAO;

@WebServlet("/LivroController")
public class LivroController extends HttpServlet {
	
	private static final long serialVersionUID = 820928843794152937L;
	
	private LivroDAO lDAO;
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoLivros");
	
	@Override
	public void init() throws ServletException{
		lDAO = new LivroDAO(emf);
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
		
		Livro livro = new Livro("id", titulo, autor, genero, editora, linguas, avaliacao, anoLancamento, qtdPgsTotal, qtdPgsLidas, usuario);
		
		boolean status = lDAO.inserirLivro(livro);
		
		request.setAttribute("status", status);
		request.setAttribute("operacao", "inseri");
		
		if (status) {
			Cookie cookieTitulo = new Cookie("titulo-"+usuario.getLogin(), titulo);
			Cookie cookieAutor = new Cookie("autor-"+usuario.getLogin(), autor);
			Cookie cookieAvaliacao = new Cookie("avaliacao-"+usuario.getLogin(), avaliacao);
			
			cookieTitulo.setMaxAge(60*60*24*365);
			cookieAutor.setMaxAge(60*60*24*365);
			cookieAvaliacao.setMaxAge(60*60*24*365);

			response.addCookie(cookieTitulo);
			response.addCookie(cookieAutor);
			response.addCookie(cookieAvaliacao);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/status.jsp");
		dispatcher.forward(request, response);
	}
	
	private void atualizarLivro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
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
		
		Livro livro = new Livro(id, titulo, autor, genero, editora, linguas, avaliacao, anoLancamento, qtdPgsTotal, qtdPgsLidas, usuario);
		
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

		livros = lDAO.consultarLivros(usuario);
		
		request.setAttribute("lista", livros);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/lista.jsp");
		dispatcher.forward(request, response);
	}

	private void excluirLivro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean status = lDAO.excluirLivro(request.getParameter("id"));
		
		request.setAttribute("status", status);
		request.setAttribute("operacao", "exclui");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/status.jsp");
		dispatcher.forward(request, response);
	}
	
	private void modificarLivro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Livro livro = lDAO.procurarLivro(request.getParameter("id"));
		
		request.setAttribute("livro", livro);
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		request.setAttribute("ano", formatter.format(livro.getAnoLancamento()));
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/edit.jsp");
		dispatcher.forward(request, response);
	}
}
