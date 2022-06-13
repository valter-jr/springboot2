package br.com.junior.springboot2.repository;

import br.com.junior.springboot2.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    List<Topico> findByCurso_Nome(String nomeCurso);

    @Query("SELECT t FROM Topico t WHERE t.curso.nome = :atributo")
    List<Topico> queryEspecifica(@Param("atributo") String atributo);

}
