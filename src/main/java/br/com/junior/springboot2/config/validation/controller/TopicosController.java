package br.com.junior.springboot2.config.validation.controller;

import br.com.junior.springboot2.config.validation.controller.dto.DetalhesDoTopicoDto;
import br.com.junior.springboot2.config.validation.controller.dto.TopicoDto;
import br.com.junior.springboot2.form.AtualizacaoTopicoForm;
import br.com.junior.springboot2.form.TopicoForm;
import br.com.junior.springboot2.model.Topico;
import br.com.junior.springboot2.repository.CursoRepository;
import br.com.junior.springboot2.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<TopicoDto> lista(String nomeCurso) {
        if (nomeCurso == null) {
            List<Topico> topicos = topicoRepository.findAll();
            return TopicoDto.toTopicoDto(topicos);
        } else {
            List<Topico> topicos = topicoRepository.findByCurso_Nome(nomeCurso);
            return TopicoDto.toTopicoDto(topicos);
        }
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        return topico.map(t -> ResponseEntity.ok(new DetalhesDoTopicoDto(t))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
        Topico topico = form.toTopico(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @PutMapping("/{id}")
    @Transactional //Commita a transação no fim do método
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            Topico topico = form.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDto(topico));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover (@PathVariable Long id) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
