package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Autor;

@SuppressWarnings("deprecation")
@ManagedBean
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();

	private List<Livro> livros;

	private Integer autorId;

	private Integer livroId;

	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}
	
    public void carregaPelaId() {
        this.livro = new DAO<Livro>(Livro.class).buscaPorId(this.livroId);
    }

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Autor> autores = new ArrayList<Autor>();

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public Livro getLivro() {
		return livro;
	}

	public List<Livro> getLivros() {
		livros = new DAO<Livro>(Livro.class).listaTodos();
		return livros;
	}

	public List<Autor> getAutores() {
		autores = new DAO<Autor>(Autor.class).listaTodos();
		return autores;
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public void gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
		System.out.println("Escrito por: " + autor.getNome());
	}

	public void gravar() {

		if (livro.getAutores().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}

		if (this.livro.getId() == null) {
			System.out.println("Gravando livro " + this.livro.getTitulo());
			new DAO<Livro>(Livro.class).adiciona(livro);
		} else {
			System.out.println("Atualizando livro " + this.livro.getTitulo());
			new DAO<Livro>(Livro.class).atualiza(this.livro);
		}

		this.livro = new Livro();
	}

	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {

		String valor = value.toString();
		if (!valor.startsWith("9")) {
			throw new ValidatorException(new FacesMessage("ISBN deveria começar com 9"));
		}

	}

	public String formAutor() {
		System.out.println("Chamando o formulário do Autor");
		return "autor?faces-redirect=true";
	}

	public void remover(Livro livro) {
		System.out.println("Removendo livro " + livro.getTitulo());
		new DAO<Livro>(Livro.class).remove(livro);
		this.livros.remove(livro);
	}

	public void carregar(Livro livro) {
		System.out.println("Carregando o livro: " + livro.getTitulo());
		this.livro = livro;
	}

	// Remove o autor associado a um determinado livro
	public void removerAutorDoLivro(Autor autor) {
		System.out.println("Removendo autor: " + autor.getNome() + "do livro: " + livro.getTitulo());
		this.livro.getAutores().remove(autor);
	}
	
	public void novoLivro() {
		this.livro = new Livro();	
	}

}
