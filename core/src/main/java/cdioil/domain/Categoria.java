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
     * String que identifica a categoria (descritor + DC/UN/CAT/SCAT).
     */
    String descritor;

    /**
     * Conjunto de produtos contidos nesta categoria.
     */
    Set<Produto> produtos;

    public Categoria(String nome, String descritor) {
        this.nome = nome;

        this.descritor = descritor;

        produtos = new HashSet<>();
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
        hash = 41 * hash + Objects.hashCode(this.nome);
        hash = 41 * hash + Objects.hashCode(this.descritor);
        hash = 41 * hash + Objects.hashCode(this.produtos);
        return hash;
    }

    /**
     * Compara a Categoria com outro objeto.
     *
     * @param obj Objeto a comparar
     * @return true, se os dois objetos tiverem os mesmos atributos. Caso contrário, retorna false
     */
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
        if (!Objects.equals(this.descritor, other.descritor)) {
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
