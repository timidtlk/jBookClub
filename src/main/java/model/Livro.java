package model;

import java.time.LocalDate;
import java.time.Period;

public class Livro {
	private int id;
	private String titulo;
	private String autor;
	private String genero;
	private String editora;
	private String linguas;
	private String avaliacao;
	private LocalDate anoLancamento;
	private double qtdPaginasTotal;
	private double qtdPaginasLidas;
	private String login_id;

	

	public Livro(int id, String titulo, String autor, String genero, String editora, String linguas, String avaliacao,
			LocalDate anoLancamento, double qtdPaginasTotal, double qtdPaginasLidas, String login_id) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.genero = genero;
		this.editora = editora;
		this.linguas = linguas;
		this.avaliacao = avaliacao;
		this.anoLancamento = anoLancamento;
		this.qtdPaginasTotal = qtdPaginasTotal;
		this.qtdPaginasLidas = qtdPaginasLidas;
		this.login_id = login_id;
	}

	public int getIdadeLivro() {
		LocalDate now = LocalDate.now();
		
		return Period.between(anoLancamento, now).getYears();		
	}
	
	public int[] getEstimativaLeitura() {
		double tempoTotal = this.qtdPaginasTotal * 60;
		
		int s = 0, m = 0, h = 0;
		
		s += tempoTotal;
		
		m += s/60;
		s = s % 60;
		
		h += m/60;
		m = m % 60;
		
		int[] tempo = {h, m, s};
		
		return tempo;
	}
	
	public double getProgressoPcent() {
		return (double) Math.round((qtdPaginasLidas / qtdPaginasTotal) * 100.0);
	}
	
	public int getQtdLinguas() {
		return linguas.split(",").length;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public String getLinguas() {
		return linguas;
	}

	public void setLinguas(String linguas) {
		this.linguas = linguas;
	}

	public String getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(String avaliacao) {
		this.avaliacao = avaliacao;
	}

	public LocalDate getAnoLancamento() {
		return anoLancamento;
	}

	public void setAnoLancamento(LocalDate anoLancamento) {
		this.anoLancamento = anoLancamento;
	}

	public double getQtdPaginasTotal() {
		return qtdPaginasTotal;
	}

	public void setQtdPaginasTotal(double qtdPaginasTotal) {
		this.qtdPaginasTotal = qtdPaginasTotal;
	}

	public double getQtdPaginasLidas() {
		return qtdPaginasLidas;
	}

	public void setQtdPaginasLidas(double qtdPaginasLidas) {
		this.qtdPaginasLidas = qtdPaginasLidas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginId() {
		return login_id;
	}

	public void setLoginId(String login_id) {
		this.login_id = login_id;
	}
}
