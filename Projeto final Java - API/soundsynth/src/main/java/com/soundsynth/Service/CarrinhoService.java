package com.soundsynth.Service;

import com.soundsynth.Model.Carrinho;
import com.soundsynth.Model.ItemCarrinho;
import com.soundsynth.Model.Produto;
import com.soundsynth.Repository.CarrinhoRepository;
import com.soundsynth.Repository.ItemCarrinhoRepository;
import com.soundsynth.Repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemCarrinhoRepository itemCarrinhoRepository;

    public CarrinhoService(CarrinhoRepository carrinhoRepository, ProdutoRepository produtoRepository, ItemCarrinhoRepository itemCarrinhoRepository) {
        this.carrinhoRepository = carrinhoRepository;
        this.produtoRepository = produtoRepository;
        this.itemCarrinhoRepository = itemCarrinhoRepository;
    }

    public Carrinho criarNovoCarrinho() {
        Carrinho novoCarrinho = new Carrinho();
        // Inicializar o carrinho, se necessário
        return carrinhoRepository.save(novoCarrinho);
    }

    public Carrinho adicionarProduto(Long carrinhoId, Long produtoId, int quantidade) {
        // Verifica se o carrinho existe
        Optional<Carrinho> carrinhoOptional = carrinhoRepository.findById(carrinhoId);
        if (!carrinhoOptional.isPresent()) {
            // Se não encontrado, pode lançar uma exceção genérica ou retornar nulo
            throw new IllegalArgumentException("Carrinho não encontrado");
        }

        Carrinho carrinho = carrinhoOptional.get();

        // Verifica se o produto existe
        Optional<Produto> produtoOptional = produtoRepository.findById(produtoId);
        if (!produtoOptional.isPresent()) {
            // Se não encontrado, pode lançar uma exceção genérica ou retornar nulo
            throw new IllegalArgumentException("Produto não encontrado");
        }

        Produto produto = produtoOptional.get();

        // Verifica se o produto já está no carrinho
        Optional<ItemCarrinho> itemExistente = carrinho.getItens().stream()
                .filter(item -> item.getProduto().getId().equals(produtoId))
                .findFirst();

        if (itemExistente.isPresent()) {
            // Se o produto já estiver no carrinho, atualiza a quantidade
            ItemCarrinho item = itemExistente.get();
            item.setQuantidade(item.getQuantidade() + quantidade);
        } else {
            // Se o produto não estiver no carrinho, adiciona como novo item
            ItemCarrinho novoItem = new ItemCarrinho();
            novoItem.setProduto(produto);
            novoItem.setQuantidade(quantidade);
            novoItem.setCarrinho(carrinho);
            carrinho.getItens().add(novoItem);
        }

        return carrinhoRepository.save(carrinho);
    }

    public Carrinho removerItem(Long carrinhoId, Long itemId) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        ItemCarrinho item = itemCarrinhoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        carrinho.removerItem(item);
        itemCarrinhoRepository.delete(item);

        return carrinhoRepository.save(carrinho);
    }

    public Carrinho atualizarQuantidade(Long carrinhoId, Long itemId, int quantidade) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        ItemCarrinho item = itemCarrinhoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        item.setQuantidade(quantidade);

        return carrinhoRepository.save(carrinho);
    }

    public BigDecimal calcularSubtotal(Long carrinhoId) {
        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findById(carrinhoId);

        if (optionalCarrinho.isPresent()) {
            Carrinho carrinho = optionalCarrinho.get();

            // Calcula o subtotal
            return carrinho.getItens().stream()
                    .map(item -> {
                        BigDecimal precoUnitario = item.getProduto().getPrecoAVista(); // Use o preço à vista
                        return precoUnitario.multiply(new BigDecimal(item.getQuantidade()));
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            // Retorna null se o carrinho não for encontrado
            return null;
        }
    }
    public Carrinho obterCarrinhoPorId(Long carrinhoId) {
        return carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));
    }
}

