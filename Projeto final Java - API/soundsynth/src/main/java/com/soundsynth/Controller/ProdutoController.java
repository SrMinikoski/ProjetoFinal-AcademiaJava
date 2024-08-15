package com.soundsynth.Controller;


import com.soundsynth.Model.Produto;
import com.soundsynth.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    //Listar todos os produtos sem filtro
    @GetMapping("/listar")
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }
    //Listar o produto de determinado ID
    @GetMapping("/buscarid/{id}")
    public List<Produto> buscarid(@PathVariable("id") Long id) {
        return produtoRepository.findAllById(id);
    }
    //Listar todos os produtos de determinada categoria
    @GetMapping("/buscarcategoria/{categoria}")
    public List<Produto> buscarCategoria(@PathVariable("categoria") String categoria) {
        return produtoRepository.findByCategoriaContainingIgnoreCase(categoria);
    }
    //Buscar todos os produtos de acordo com a pesquisa de nome
    @GetMapping("/buscarnome/{nome}")
    public List<Produto> buscarNome(@PathVariable("nome") String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }
    //Adicionar novo produto
    @PostMapping("/adicionar")
    public Produto adicionar(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }


    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<HttpStatus> excluir(@PathVariable Long id) {
        produtoRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Editar produto já existente por ID
    @PutMapping("/alterar/{id}")
    public String atualizarProduto(@PathVariable Long id, @RequestBody Produto novoProduto) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);

        if (produtoExistente.isPresent()) {
            Produto produto = produtoExistente.get();

            if (novoProduto.getNome() != null) {
                produto.setNome(novoProduto.getNome());
            }
            if (novoProduto.getDescricao() != null) {
                produto.setDescricao(novoProduto.getDescricao());
            }
            if (novoProduto.getPrecoAPrazo() != null) {
                produto.setPrecoAPrazo(novoProduto.getPrecoAPrazo());
            }
            if (novoProduto.getPrecoAVista() != null) {
                produto.setPrecoAVista(novoProduto.getPrecoAVista());
            }
            if (novoProduto.getCategoria() != null) {
                produto.setCategoria(novoProduto.getCategoria());
            }
            if (novoProduto.getEspecificacoes() != null){
                produto.setEspecificacoes(novoProduto.getEspecificacoes());
            }
            if (novoProduto.getMarca() != null) {
                produto.setMarca(novoProduto.getMarca());
            }
            if (novoProduto.getImagem() != null) {
                produto.setImagem(novoProduto.getImagem());
            }
            if (novoProduto.getPromocao() != null) {
                produto.setPromocao(novoProduto.getPromocao());
            }

            // Salve o produto atualizado
            produtoRepository.save(produto);

            // Log para confirmar o salvamento


            return "Produto atualizado com sucesso";
        } else {
            return "Produto não encontrado";
        }




    }
}
