package model;

import java.util.ArrayList;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import entity.Livro;
import entity.Usuario;

public class LivroDAO {
	private EntityManagerFactory emf;
	
	public LivroDAO(EntityManagerFactory emf) {
		super();
		this.emf = emf;
	}

	public boolean inserirLivro(Livro livro) {
		EntityManager em = emf.createEntityManager();
		boolean status = false;
		livro.setId(UUID.randomUUID().toString());

		try {
			em.getTransaction().begin();
			em.persist(livro);
			em.getTransaction().commit();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return status;
	}
	
	public boolean modificarLivro(Livro livro) {
		EntityManager em = emf.createEntityManager();
		boolean status = false;

		try {
			em.getTransaction().begin();
			em.merge(livro);
			em.getTransaction().commit();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return status;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Livro> consultarLivros(Usuario user) {
		EntityManager em = emf.createEntityManager();
		ArrayList<Livro> listaLivro = new ArrayList<>();
		
		try {
			Query query = em.createQuery("from " + Usuario.class + " where user = :u");
			query.setParameter("u", user);
			listaLivro = (ArrayList<Livro>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return listaLivro;
	}
	
	public Livro procurarLivro(int id) {
		EntityManager em = emf.createEntityManager();
		Livro livro = null;
		
		try {
			livro = em.find(Livro.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return livro;
	}
	
	public boolean excluirLivro(int id) {
		EntityManager em = emf.createEntityManager();
		boolean status = false;

		try {
			Livro livro = em.find(Livro.class, id);
			em.getTransaction().begin();
			em.remove(livro);
			em.getTransaction().commit();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return status;
	}
}
