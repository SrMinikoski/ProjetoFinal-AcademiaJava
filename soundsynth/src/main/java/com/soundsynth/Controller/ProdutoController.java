package com.soundsynth.Controller;


import com.soundsynth.Model.Produto;
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
