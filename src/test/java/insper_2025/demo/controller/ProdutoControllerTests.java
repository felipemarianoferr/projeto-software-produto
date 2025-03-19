package insper_2025.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import insper_2025.demo.produto.Produto;
import insper_2025.demo.produto.ProdutoController;
import insper_2025.demo.produto.ProdutoService;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
@Data
@ExtendWith(MockitoExtension.class)
public class ProdutoControllerTests {

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoService produtoService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(produtoController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void test_GetProdutos() throws Exception {
        List<Produto> produtos = Arrays.asList(
                new Produto(), new Produto()
        );

        Mockito.when(produtoService.listaProdutos()).thenReturn(produtos);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(produtos)));
    }

    @Test
    void test_GetProdutoById() throws Exception {
        Produto produto = new Produto();
        produto.setId("123");
        produto.setNome("Produto A");

        Mockito.when(produtoService.findProdutoById("1")).thenReturn(produto);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(produto)));
    }

    @Test
    void test_PostProduto() throws Exception {
        Produto produto = new Produto();
        produto.setNome("Produto A");
        produto.setPreco(29.99);
        produto.setEstoque(10);

        Mockito.when(produtoService.cadastraProduto(produto)).thenReturn(produto);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/produto")
                                .content(objectMapper.writeValueAsString(produto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(produto)));
    }

    @Test
    void test_PutAtualizaEstoque() throws Exception {
        Mockito.doNothing().when(produtoService).atualizaEstoque("1", 20, "true");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/produto/estoque/1/20/true"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Estoque Atualizado"));
    }
}
