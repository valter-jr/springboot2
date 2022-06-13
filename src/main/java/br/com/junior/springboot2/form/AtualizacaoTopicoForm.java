package br.com.junior.springboot2.form;

import br.com.junior.springboot2.model.Topico;
import br.com.junior.springboot2.repository.TopicoRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AtualizacaoTopicoForm {
    @NotNull
    @NotEmpty
    @Length(min = 5, max = 20)
    private String titulo;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 200)
    private String mensagem;

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

    public Topico atualizar(Long id, TopicoRepository topicoRepository) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);
        return topico;
    }
}
