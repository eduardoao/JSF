package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.modelo.Autor;

@Named
@ViewScoped // javax.faces.view.ViewScoped
public class AutorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Autor autor = new Autor();
	private Integer autorId;

	@Inject
	private AutorDao autorDao;

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public void carregarAutorPelaId() {
		this.autor = autorDao.buscaPorId(autorId);
	}

	public String gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if (this.autor.getId() == null) {
			autorDao.adiciona(this.autor);
		} else {
			autorDao.atualiza(this.autor);
		}

		this.autor = new Autor();

		return "livro?faces-redirect=true";
	}

	public void remover(Autor autor) {
		System.out.println("Removendo autor " + autor.getNome());
		autorDao.remove(autor);
	}

	public List<Autor> getAutores() {
		return autorDao.listaTodos();
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
}
