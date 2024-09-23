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

    }

    private void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String cpf = request.getParameter("cpf");
        String telefone = request.getParameter("telefone");
        String password = BCrypt.withDefaults().hashToString(6, request.getParameter("password").toCharArray());

        Usuario usuario = new Usuario("id", nome, login, email, cpf, telefone, password);

        String cadastro = uDAO.cadastrarUsuario(usuario);

        if (cadastro.equals("Cadastro foi realizado com sucesso!")) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastrarUsuario.html");
            dispatcher.forward(request, response);
        }
    }
    
}
