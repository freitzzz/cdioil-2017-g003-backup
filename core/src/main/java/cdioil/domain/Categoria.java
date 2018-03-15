package cdioil.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Representa uma categoria de produtos existente na Estrutura Mercadologica.
 */
public class Categoria {

    /**
     * Nome da categoria.
     */
    String nome;

    /**
     * Inteiro que identifica a categoria.
     */
    int descritor;

    /**
     * Conjunto de produtos contidos nesta categoria.
     */
    Set<Produto> produtos;

    public Categoria(String nome, int descritor) {

        this.nome = nome;

        this.descritor = descritor;

        produtos = new HashSet<>();

    }

    /**
     * Adiciona um produto ao conjunto de produtos desta categoria
     *
     * @param p produto a adicionar
     * @return true - se o produto tiver sido adicionado com sucesso<p>
     * false - caso contrário
     */
    public boolean adicionarProduto(Produto p) {
        return produtos.add(p);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.nome);
        hash = 79 * hash + this.descritor;
        hash = 79 * hash + Objects.hashCode(this.produtos);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Categoria other = (Categoria) obj;
        if (this.descritor != other.descritor) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.produtos, other.produtos)) {
            return false;
        }
        return true;
    }

}
