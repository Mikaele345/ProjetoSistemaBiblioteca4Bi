package br.edu.ifpb.biblioteca.entity;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data do empréstimo é obrigatória")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "data_emprestimo", nullable = false)
    private String dataEmprestimo;

    @NotNull(message = "A data de devolução prevista é obrigatória")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "data_devolucao_prevista", nullable = false)
    private String dataDevolucaoPrevista;

    @NotNull(message = "A data de devolução real é obrigatória")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "data_devolucao_real", nullable = false)
    private String dataDevolucaoReal;
    private String status;
    private String livro;

   @ManyToOne(
			cascade = {CascadeType.REFRESH, CascadeType.MERGE,CascadeType.PERSIST},
			fetch = FetchType.EAGER,
			optional = false)
	private Usuario usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(String dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public String getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(String dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public String getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }

    public void setDataDevolucaoReal(String dataDevolucaoReal) {
        this.dataDevolucaoReal = dataDevolucaoReal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLivro() {
        return livro;
    }

    public void setLivro(String livro) {
        this.livro = livro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
}
