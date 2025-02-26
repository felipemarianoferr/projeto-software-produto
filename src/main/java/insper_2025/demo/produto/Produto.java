package insper_2025.demo.produto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Produto {
    @Id
    private String  id;
    private String nome;
    private Double preco;
    private Integer estoque;

}
