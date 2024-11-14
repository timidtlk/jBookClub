package entity;

import java.time.LocalDate;
import java.time.Period;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livro {
	@Id
	private String id;
	private String titulo;
	private String autor;
	private String genero;
	private String editora;
	private String linguas;
	private String avaliacao;
	private LocalDate anoLancamento;
	private double qtdPaginasTotal;
	private double qtdPaginasLidas;
	@ManyToOne(optional = false, cascade = {CascadeType.REMOVE})
	private Usuario user;

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
}