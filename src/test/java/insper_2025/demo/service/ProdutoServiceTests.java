package insper_2025.demo.service;

import insper_2025.demo.produto.Produto;
import insper_2025.demo.produto.ProdutoRepository;
import insper_2025.demo.produto.ProdutoService;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Data
@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTests {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Test
    void test_listaProdutosQuandoNaoHaProdutos() {
        Mockito.when(produtoRepository.findAll()).thenReturn(Arrays.asList());

        List<Produto> produtos = produtoService.listaProdutos();

        Assertions.assertTrue(produtos.isEmpty());
    }

    @Test
    void test_cadastraProdutoComSucesso() {
        Produto produto = new Produto();
        produto.setNome("Teste");
        produto.setPreco(1.0);
        produto.setEstoque(1);

        Mockito.when(produtoRepository.findByNome("Produto Teste")).thenReturn(null);
        Mockito.when(produtoRepository.save(produto)).thenReturn(produto);

        Produto produtoSalvo = produtoService.cadastraProduto(produto);

        Assertions.assertEquals("Produto Teste", produtoSalvo.getNome());
        Assertions.assertEquals(49.99, produtoSalvo.getPreco());
        Assertions.assertEquals(10, produtoSalvo.getEstoque());
    }

    @Test
    void test_findProdutoByIdComSucesso() {
        Produto produto = new Produto();
        produto.setId("123");
        produto.setNome("Teste");

        Mockito.when(produtoRepository.findById("1")).thenReturn(Optional.of(produto));

        Produto produtoRetornado = produtoService.findProdutoById("1");

        Assertions.assertEquals("Produto A", produtoRetornado.getNome());
        Assertions.assertEquals(29.99, produtoRetornado.getPreco());
        Assertions.assertEquals(10, produtoRetornado.getEstoque());
    }


    @Test
    void test_atualizaEstoqueAdicionando() {
        Produto produto = new Produto();
        produto.setId("123");
        produto.setNome(null);
        produto.setPreco(null);
        produto.setEstoque(1);

        Mockito.when(produtoRepository.findById("1")).thenReturn(Optional.of(produto));
        Mockito.when(produtoRepository.save(produto)).thenReturn(produto);

        produtoService.atualizaEstoque("1", 20, "true");

        Assertions.assertEquals(20, produto.getEstoque());
    }

    @Test
    void test_atualizaEstoqueRemovendo() {
        Produto produto = new Produto();
        produto.setId("123");
        produto.setNome(null);
        produto.setPreco(null);
        produto.setEstoque(1);

        Mockito.when(produtoRepository.findById("1")).thenReturn(Optional.of(produto));
        Mockito.when(produtoRepository.save(produto)).thenReturn(produto);

        produtoService.atualizaEstoque("1", 10, "false");

        Assertions.assertEquals(20, produto.getEstoque());
    }

    @Test
    void test_atualizaEstoqueErroProdutoNaoEncontrado() {
        Mockito.when(produtoRepository.findById("99")).thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> produtoService.atualizaEstoque("99", 10, "true"));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}
