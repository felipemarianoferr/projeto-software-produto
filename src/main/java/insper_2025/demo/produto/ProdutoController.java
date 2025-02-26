package insper_2025.demo.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @GetMapping
    public List<Produto> getProdutos() {
        return produtoService.listaProdutos();
    }

    @GetMapping("/estoque/{id}")
    public String atualizaEstoque(@PathVariable String id, @PathVariable Integer quantidade) {
        produtoService.atualizaEstoque(id, quantidade);
        return "Estoque Atualizado";
    }

    @PostMapping
    public Produto saveProduto(@RequestBody Produto produto) {
        return produtoService.cadastraProduto(produto);
    }

    @PutMapping("/produtos/{id}/estoque")
    public ResponseEntity<Void> atualizaEstoquePut(
            @PathVariable String id,
            @RequestParam Integer quantidade) {
        produtoService.atualizaEstoque(id, quantidade);
        return ResponseEntity.noContent().build();
    }


}
