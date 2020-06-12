# Vicente - Framework Java

[![](https://avatars2.githubusercontent.com/u/1757453?s=460&v=4)](https://github.com/munifgebara/vicente)

 * Framework para desenvolvimento de aplicações Java
 * Políticas de ternancy integradas
 * Facilitadores para busca objeto-relacional
 * Criação e gerenciamento de recursos, serviços e repositórios
 
 [![](https://raw.githubusercontent.com/munifgebara/munifgebara.github.io/master/images/d.png)](https://github.com/munifgebara/vicente)
## Ferramentas disponíveis no Framework 
### ** VQuery - Consulta O.O **
VQuery é um mecanismo que permite a busca de dados objeto relacional
  - As entidades a ser pesquisado o texto
  - Os campos
  - O texto

#### O que utilizar

Atualmente existe uma rota que permite fazer consultas OO. É possível enviar um objeto query nas requisições de busca do tipo GET, entretanto, lembre-se que parâmetros tem tamanho limitado:

* [vquery] (Rota: ../minha-api/vquery) -  Rota que permite fazer uma requisição do tipo POST para buscas.
* Exemplo de Objeto JSON do tipo VQuery:
```json
     {
        "subQuerys": [
          {
            "subQuerys": [],
            "joins": [],
            "criteria": {
              "comparisonOperator": "EQUAL",
              "fieldFunction": "%s",
              "valueFunction": "",
              "field": "nome",
              "value": "Willian"
            },
            "logicalOperator": "SIMPLE"
          },
          {
            "subQuerys": [],
            "joins": [],
            "criteria": {
              "comparisonOperator": "CONTAINS",
              "fieldFunction": "%s",
              "valueFunction": "",
              "field": "nome",
              "value": "Munif"
            },
            "logicalOperator": "SIMPLE"
          }
        ],
        "joins": [],
        "logicalOperator": "OR"
      }
```

### Exemplo

Para utilização do VQuery:

```java
@Service
public class PessoaService extends BaseService<Pessoa>{
    
    public PessoaService(VicRepository<Pessoa> repository) {
        super(repository);
    }
    
     @Transactional
     public void findByQuery2In() {
         VicQuery q = new VQuery(new Criteria('nome', ComparisonOperator.EQUAL, 'Willian'))
                                   .or(new Criteria('nome', ComparisonOperator.CONTAINS, 'Munif'));
          List<Pessoa> findAll = this.findByHql(q);
          System.out.println(findAll);
     }
}
```

See [Documentação Core](https://munifgebara.github.io/assets/java-docs/coredocs/index.html)

License
----

LGPL-3.0


**Free Software, Hell Yeah!**
