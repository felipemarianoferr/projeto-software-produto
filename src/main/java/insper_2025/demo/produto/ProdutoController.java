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

    @PutMapping("/estoque/{id}/{quantidade}")
    public String atualizaEstoque(@PathVariable String id, @PathVariable Integer quantidade, @RequestParam boolean x) {
        produtoService.atualizaEstoque(id, quantidade, x);
        return "Estoque Atualizado";
    }

    @PostMapping
    public Produto saveProduto(@RequestBody Produto produto) {
        return produtoService.cadastraProduto(produto);
    }


}
