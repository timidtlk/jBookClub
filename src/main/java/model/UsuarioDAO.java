package model;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import at.favre.lib.crypto.bcrypt.BCrypt;
import entity.Usuario;

public class UsuarioDAO {
    private EntityManagerFactory emf;
	
	public UsuarioDAO(EntityManagerFactory emf) {
		super();
		this.emf = emf;
	}

    public String cadastrarUsuario(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
		String resultado = "Ocorreu um erro";
		usuario.setId(UUID.randomUUID().toString());
		
		try {
			em.getTransaction().begin();
			em.persist(usuario);
			em.getTransaction().commit();
			resultado = "Cadastro foi realizado com sucesso!";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		return resultado;
    }

	public boolean existeUsuario(String login, String email) {
		EntityManager em = emf.createEntityManager();
		boolean achou = false;
		
		try {
			Query query = em.createQuery("from " + Usuario.class + " where login = :l and email = :e");
			query.setParameter("l", login);
			query.setParameter("e", email);
			achou = (query.getResultList().size() > 0) ? true : false;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return achou;
	}

	public boolean excluirUsuario(String id) {
		EntityManager em = emf.createEntityManager();
		boolean status = false;

		try {
			Usuario usuario = em.find(Usuario.class, id);
			em.getTransaction().begin();
			em.remove(usuario);
			em.getTransaction().commit();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return status;
	}

	public Usuario fazerLogin(String login, String senha) {
		Usuario usuario = null;
		EntityManager em = emf.createEntityManager();
		
		try {
			Query query = em.createQuery("from" + Usuario.class + " where login = :l");
			query.setParameter("l", login);
			usuario = (Usuario) query.getSingleResult();
			
			if (!BCrypt.verifyer().verify(senha.toCharArray(), usuario.getPassword().toCharArray()).verified) {
				usuario = null;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return usuario;
	}
}
