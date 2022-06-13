package br.com.junior.springboot2.form;

import br.com.junior.springboot2.model.Curso;
import br.com.junior.springboot2.model.Topico;
import br.com.junior.springboot2.repository.CursoRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TopicoForm {

    @NotNull
    @NotEmpty
    @Length(min = 5, max = 20)
    private String titulo;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 200)
    private String mensagem;

    @NotNull
    @NotEmpty
    private String nomeCurso;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public Topico toTopico(CursoRepository cursoRepository) {
        Curso curso = cursoRepository.findByNome(nomeCurso);
        return new Topico(titulo, mensagem, curso);
    }
}
