package com.soundsynth.Repository;

import com.soundsynth.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findAllById(Long id);
    List<Produto> findAllByCategoria(String categoria);
    List<Produto> findByNomeContainingIgnoreCase(String nome);
}
