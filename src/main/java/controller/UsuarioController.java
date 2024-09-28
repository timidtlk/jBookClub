package controller;

import java.io.IOException;

import javax.sql.DataSource;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Usuario;
import model.UsuarioDAO;

@WebServlet("/UsuarioController")
public class UsuarioController extends HttpServlet {
    
    private static final long serialVersionUID = 820928843794152937L;
	
	private UsuarioDAO uDAO;
	@Resource(name="bancoLivros")
	private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        uDAO = new UsuarioDAO(dataSource);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacao = request.getParameter("operacao");
        operacao = operacao.toLowerCase();

        switch (operacao) {
            case "entrar":
                fazerLogin(request, response);
                break;
            case "cadastrar":
                cadastrarUsuario(request, response);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacao = request.getParameter("operacao");
        operacao = operacao.toLowerCase();

        switch (operacao) {
            case "sair":
                fazerLogout(request, response);
                break;
            case "excluir":
                excluirUsuario(request, response);
                break;
            default:
                break;
        }
    }

    private void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String cpf = request.getParameter("cpf");
        String telefone = request.getParameter("telefone");
        String password = BCrypt.withDefaults().hashToString(6, request.getParameter("password").toCharArray());

        Usuario verifica = uDAO.existeUsuario(login, email);
        if (verifica != null) {
            request.setAttribute("erro", "Já existe um usuário com o mesmo login ou e-mail. Por favor, insira outros");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastrarUsuario.jsp");
            dispatcher.forward(request, response);
        } else {
            Usuario usuario = new Usuario("id", nome, login, email, cpf, telefone, password);

            String cadastro = uDAO.cadastrarUsuario(usuario);
    
            if (cadastro.equals("Cadastro foi realizado com sucesso!")) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("erro", "Houve um erro durante o cadastro. Por favor, tente mais tarde");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastrarUsuario.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    private void fazerLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        Usuario verifica = uDAO.existeUsuario(login, "");
        if (verifica == null) {
            request.setAttribute("erro", "Não existe um usuário com esse login. Cadastre-se no site");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }
        verifica = null;

        Usuario usuario = uDAO.fazerLogin(login, password);

        if (usuario != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/infoLivros.html");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("erro", "A senha inserida está incorreta");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void fazerLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		session.invalidate();
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
		dispatcher.forward(request, response);
	}

    private void excluirUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");

        uDAO.excluirUsuario(usuario.id());
		
        request.setAttribute("erro", "Usuário excluído com sucesso!");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
	}
}
