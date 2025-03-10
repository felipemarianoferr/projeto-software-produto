package insper_2025.demo.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    public Produto cadastraProduto(Produto produto){
        Produto produtoDB = produtoRepository.findByNome(produto.getNome());
        if (produtoDB == null){
            return produtoRepository.save(produto);
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public Produto findProdutoById(String id){
        Produto produtoDB = produtoRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return produtoDB;
    }

    public void atualizaEstoque(String id, Integer quantidade, boolean x){
        Produto produtoDB = findProdutoById(id);
        if (x){
            int novoEstoque = produtoDB.getEstoque() + quantidade;
            produtoDB.setEstoque(novoEstoque);
        }else {
            int novoEstoque = produtoDB.getEstoque() - quantidade;
            produtoDB.setEstoque(novoEstoque);
        }

    }

    public List<Produto> listaProdutos(){
        return produtoRepository.findAll();
    }
}
