package com.soundsynth.Repository;

import com.soundsynth.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findAllById(Long id);
    @Query("SELECT p FROM Produto p WHERE LOWER(p.categoria) LIKE LOWER(CONCAT('%', :categoria, '%'))")
    List<Produto> findByCategoriaContainingIgnoreCase(@Param("categoria") String categoria);
    List<Produto> findByNomeContainingIgnoreCase(String nome);
}
