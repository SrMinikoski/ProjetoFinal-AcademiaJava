package com.soundsynth.Repository;

import com.soundsynth.Model.ItemCarrinho;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ItemCarrinho ic WHERE ic.produto.id = :produtoId")
    void deleteByProdutoId(@Param("produtoId") Long produtoId);
}