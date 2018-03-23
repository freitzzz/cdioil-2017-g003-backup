package cdioil.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Classe que representa a Estrutura Mercadologica que agrega as categorias de
 * produtos.
 *
 * @author António Sousa [1161371]
 */
@Entity
public class EstruturaMercadologica implements Serializable {

    /**
     * Código de serialização.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_ESTRUTURA", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Primeiro node da estrutura mercadologica.
     */
    @OneToOne(cascade = {CascadeType.PERSIST})
    private Node raiz;
    /**
     * Tamanho da estrutura mercadologica (numero de nodes).
     */
    private int tamanho;

    /**
     * Construtor publico para incializar a Estrutura Mercadologica.
     */
    public EstruturaMercadologica() {

        raiz = new Node(null, new Category("Todos os Produtos", "-1UB"));
        tamanho = 1;
    }

    public Node getRaiz() {
        return raiz;
    }

    /**
     * Adiciona uma nova categoria principal à estrutura mercadologica.
     *
     * @param c nova categoria
     * @return true - se for possivel adicionar a categoria<p>
     * false - caso contrário
     */
    public boolean adicionarCategoriaRaiz(Category c) {
        if (c == null) {
            throw new IllegalArgumentException("O argumento não pode ser null");
        }

        return adicionarCategoria(raiz.getElemento(), c);
    }

    /**
     * Adiciona uma nova Category à Estrutura Mercadologica.
     *
     * @param pai a categoria acima da nova categoria
     * @param c a nova categoria
     * @return true - se for possivel adicionar a categoria<p>
     * false - caso contrário
     */
    public boolean adicionarCategoria(Category pai, Category c) {
        if (pai == null || c == null) {
            throw new IllegalArgumentException("O argumentos não podem ser null");
        }

        //Ler: "Se o pai estiver na estrutura, mas o filho nao"
        Node nodePai = procuraNode(raiz, pai);

        if (nodePai != null) {

            Node nodeFilho = procuraNode(nodePai, c);

            if (nodeFilho == null) {
                tamanho++;
                return nodePai.addFilho(new Node(nodePai, c));
            }
        }
        return false;
    }

    /**
     * Remove a Category da Estrutura Mercadologica.
     *
     * @param c categoria a remover
     */
    public void removerCategoria(Category c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Método recursivo usado para para procurar uma dada Category na Estrutura
     * Mercadologica.
     *
     * @param node node a partir do qual se pretende iniciar a procura
     * @param c Category a procurar
     * @return o node em que a categoria se encontra, null caso não seja
     * encontrada
     */
    private Node procuraNode(Node node, Category c) {
        if (node == null) {
            return null;
        }

        if (node.getElemento().equals(c)) {
            return node;
        }

        List<Node> filhos = node.getFilhos();

        for (Node n : filhos) {
            Node filho = procuraNode(n, c);

            //retornar apenas filhos que não sejam null
            if (filho != null) {
                return filho;
            }
        }

        return null;
    }

    /**
     * Método para verificar se um Node é uma folha da Estrutura
     * Mercadologica.<p>
     * Uma Category folha é uma categoria que não alberga sub-categorias,
     * podendo conter produtos.
     *
     * @param node node a verificar se é folha
     * @return true se o node não tiver nodes filhos, false caso tenha
     */
    private boolean isLeaf(Node node) {
        return node.getFilhos().isEmpty();
    }

    /**
     * Devolve uma coleção de todos os nodes da Estrutura Mercadologica que
     * sejam folhas.
     *
     * @return
     */
    public Iterable<Category> getFolhas() {

        List<Category> folhas = new LinkedList<>();

        procuraFolhas(folhas, raiz);

        return folhas;
    }

    /**
     * Pesquisa recursivamente por todos os nodes que nao tenham filhos e
     * adiciona-os a lista.
     *
     * @param folhas lista de nodes que não tenham nodes filhos
     * @param node node atual
     */
    private void procuraFolhas(List<Category> folhas, Node node) {

        List<Node> filhos = node.getFilhos();

        if (isLeaf(node)) {
            folhas.add(node.getElemento());
            return;
        }

        for (Node filho : filhos) {

            procuraFolhas(folhas, filho);
        }
    }

    /**
     * Adiciona um Product a Category pretendida, podendo apenas adicionar-se a
     * categorias que sejam folhas.
     *
     * @param p produto que se pretende adicionar
     * @param c categoria a qual se pretende adicionar o produto
     * @return true - se a categoria for uma folha<p>
     */
    public boolean adicionarProduto(Product p, Category c) {

        if (p == null || c == null) {
            throw new IllegalArgumentException("Os argumentos não podem ser null");
        }

        Node node = procuraNode(raiz, c);

        if (isLeaf(node)) {
            //adicionar à categoria dentro da estrutura e não à parametrizada
            return node.getElemento().addProduct(p);
        }

        return false;
    }

    /**
     * Determina o tamanho da Estrutura Mercadologica (número de nodes)
     *
     * @return o tamanho
     */
    public int tamanho() {
        return tamanho;
    }

    /**
     * Verifica se duas Categorias estao ligadas.
     *
     * @param pai categoria pai
     * @param filho categoria filho
     * @return true - se o pai estiver ligado e o filho ligado ao pai<p>
     * false - caso uma das ligacoes nao se verifique ou se uma das categorias
     * nao se encontrar na estrutura mercadologica
     */
    public boolean verificaLigados(Category pai, Category filho) {

        if (pai == null || filho == null) {
            throw new IllegalArgumentException("Os argumentos não podem ser null");
        }

        Node nodePai = procuraNode(raiz, pai);

        Node nodeFilho = procuraNode(raiz, filho);

        if (nodePai == null || nodeFilho == null) {
            return false;
        }

        boolean eFilho = false;

        for (Node n : nodePai.getFilhos()) {
            if (n == nodeFilho) {
                eFilho = true;
                break;
            }
        }

        return nodeFilho.getPai() == nodePai && eFilho;
    }

}
