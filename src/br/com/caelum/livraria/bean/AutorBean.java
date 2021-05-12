package br.com.caelum.livraria.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;

@SuppressWarnings("deprecation")
@ManagedBean
@ViewScoped
public class AutorBean {

	private Autor autor = new Autor();
	private List<Autor> autores = new ArrayList<Autor>(); 
	
	private Integer autorId;

	public Integer getAutorId() {
	    return autorId;
	}

	public void setAutorId(Integer autorId) {
	    this.autorId = autorId;
	}

	public void carregarAutorPelaId() {
		if (this.autorId == null) return;
	    this.autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
	}

	public List<Autor> getAutores() {
		autores = new DAO<Autor>(Autor.class).listaTodos();
		return autores;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	public Autor getAutor() {
		return autor;
	}

	public void gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());
		
		if (this.autor.getId() == null) {
			System.out.println("Gravando autor " + this.autor.getNome());			
			new DAO<Autor>(Autor.class).adiciona(this.autor);
		} else {
			System.out.println("Atualizando autor " + this.autor.getNome());
			new DAO<Autor>(Autor.class).atualiza(this.autor);
		}
		

		this.autor = new Autor();
	}
	
	public String formLivro() {
		System.out.println("Chamando o formulário do Livro");
		return "livro?faces-redirect=true";
	}
	
	public void removerAutor(Autor autor) {
		System.out.println("Removendo autor da tabela de autores!");
		
		new DAO<Autor>(Autor.class).remove(autor);
	}
	
	public void carregar(Autor autor) {
		System.out.println("Carregando o autor: " + autor.getNome());
		this.autor = autor;
	}
	
	
}
