package com.soundsynth.Controller;


import com.soundsynth.Model.Produto;
import com.soundsynth.Repository.ItemCarrinhoRepository;
import com.soundsynth.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "http://localhost:8090")
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
    public Produto buscarPorId(@PathVariable("id") Long id) {
        return produtoRepository.findById(id).orElse(null);
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
    @PostMapping("/adicionar")
    public String adicionarProduto(@RequestParam("nome") String nome,
                                   @RequestParam("categoria") String categoria,
                                   @RequestParam("precoAVista") BigDecimal precoAVista,
                                   @RequestParam("precoAPrazo") BigDecimal precoAPrazo,
                                   @RequestParam("promocao") String promocao,
                                   @RequestParam("descricao") String descricao,
                                   @RequestParam("especificacoes") String especificacoes,
                                   @RequestParam("marca") String marca,
                                   @RequestParam("imagem") MultipartFile imagem,
                                   Model model) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setCategoria(categoria);
        produto.setPrecoAVista(precoAVista);
        produto.setPrecoAPrazo(precoAPrazo);
        produto.setPromocao(promocao);
        produto.setDescricao(descricao);
        produto.setEspecificacoes(especificacoes);
        produto.setMarca(marca);

        // Verifica se o arquivo de imagem não está vazio
        if (!imagem.isEmpty()) {
            try {
                // Salva o arquivo no diretório especificado
                String caminhoArquivo = salvarImagem(imagem);

                // Define o caminho da imagem no banco de dados
                produto.setImagem(caminhoArquivo);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Salva o produto no banco de dados
        produtoRepository.save(produto);
        return "redirect:/produtos/listar";
    }

    private String salvarImagem(MultipartFile imagem) throws IOException {
        // Diretório onde a imagem será salva
        String diretorio = "C:/Users/jluca/OneDrive/Área de Trabalho/Projeto Final - SoundSynth/Front/ThymeLeaf/ThymeLeaf/src/main/resources/static/Assets/produtos/";

        // Nome do arquivo original
        String nomeArquivo = imagem.getOriginalFilename();

        // Caminho completo do arquivo
        Path caminho = Paths.get(diretorio + nomeArquivo);

        // Copia o arquivo para o diretório
        Files.copy(imagem.getInputStream(), caminho, StandardCopyOption.REPLACE_EXISTING);

        // Retorna o caminho do arquivo relativo para ser salvo no banco de dados
        return "/Assets/produtos/" + nomeArquivo;
    }
    @Autowired
    ItemCarrinhoRepository itemCarrinhoRepository;
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirProduto(@PathVariable Long id) {
        try {
            // Primeiro, remova os itens associados ao produto
            itemCarrinhoRepository.deleteByProdutoId(id);

            // Agora, remova o produto
            produtoRepository.deleteById(id);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Adicione um log para verificar o erro
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Editar produto já existente por ID
    @PutMapping("/alterar/{id}")
    public ResponseEntity<String> atualizarProduto(
            @PathVariable Long id,
            @RequestPart("produto") Produto novoProduto,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem) {

        Optional<Produto> produtoExistente = produtoRepository.findById(id);

        if (produtoExistente.isPresent()) {
            Produto produto = produtoExistente.get();

            // Atualizando apenas campos que não são nulos
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
            if (novoProduto.getEspecificacoes() != null) {
                produto.setEspecificacoes(novoProduto.getEspecificacoes());
            }
            if (novoProduto.getMarca() != null) {
                produto.setMarca(novoProduto.getMarca());
            }

            // Tratamento para imagem
            if (imagem != null && !imagem.isEmpty()) {
                try {
                    // Salva a imagem no diretório e atualiza o caminho
                    String caminhoImagem = salvarImagem(imagem);
                    produto.setImagem(caminhoImagem);
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Erro ao processar a imagem");
                }
            }

            if (novoProduto.getPromocao() != null) {
                produto.setPromocao(novoProduto.getPromocao());
            }

            // Salvar o produto atualizado
            produtoRepository.save(produto);

            return ResponseEntity.ok("Produto atualizado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
    }
}
