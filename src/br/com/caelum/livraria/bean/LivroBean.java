package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Autor;

@SuppressWarnings("deprecation")
@ManagedBean
@ViewScoped
public class LivroBean {

	private Livro livro = new Livro();
	private Integer autorId;

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	private List<Autor> autores;

	public List<Autor> getAutores() {
		autores = new DAO<Autor>(Autor.class).listaTodos();
		return autores;
	}

	public Livro getLivro() {
		return livro;
	}

	public List<Autor> getAutoresDoLivroAtual() {
		return this.livro.getAutores();
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			throw new RuntimeException("Livro deve ter pelo menos um Autor.");
		}

		new DAO<Livro>(Livro.class).adiciona(this.livro);		
		this.livro.getAutores().clear();
	}

	public void gravarAutor() {
		Autor autorSelecionado = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		this.livro.adicionaAutor(autorSelecionado);
	}

}
