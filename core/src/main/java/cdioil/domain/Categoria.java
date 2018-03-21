package cdioil.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Representa uma categoria de produtos existente na Estrutura Mercadologica.
 */
@Entity
public class Categoria implements Serializable {

    /**
     * Código de serialização.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_CATEGORIA", nullable = false, updatable = false)
    private Long id;
    /**
     * Nome da categoria.
     */
    private String nome;

    /**
     * String que identifica a categoria (descritor + DC/UN/CAT/SCAT).
     */
    @Column(unique = true)
    private String descritor;

    /**
     * Conjunto de produtos contidos nesta categoria.
     */
    @OneToMany
    private Set<Produto> produtos = new HashSet<>();

    /**
     * Construtor protegido apenas para uso do JPA.
     */
    protected Categoria() {
    }

    /**
     * Constrói uma instância com um dado nome e descritor.
     *
     * @param nome
     * @param descritor
     */
    public Categoria(String nome, String descritor) {
        this.nome = nome;
        this.descritor = descritor;
    }

    /**
     * Adiciona um produto ao conjunto de produtos desta Categoria.
     *
     * @param p produto a adicionar
     * @return true - se o produto tiver sido adicionado com sucesso<p>
     * false - caso contrário
     */
    public boolean adicionarProduto(Produto p) {
        return produtos.add(p);
    }

    /**
     * Descreve a Categoria através do seu nome e descritor.
     *
     * @return a descrição textual da Categoria.
     */
    @Override
    public String toString() {
        return String.format("Nome: %s\nDescritor: %s\n", nome, descritor);
    }

    /**
     * Gera um índice a partir do dos atributos da Categoria.
     *
     * @return o valor de hash gerado
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.descritor);
        return hash;
    }

    /**
     * Compara a Categoria com outro objeto.
     *
     * @param obj Objeto a comparar
     * @return true, se os dois objetos tiverem os mesmos atributos. Caso
     * contrário, retorna false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Categoria other = (Categoria) obj;
        if (!Objects.equals(this.descritor, other.descritor)) {
            return false;
        }
        return true;
    }
}
