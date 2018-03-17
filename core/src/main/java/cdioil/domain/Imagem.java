package cdioil.domain;

import java.io.Serializable;
import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

/**
 * Classe Imagem que representa uma imagem de um produto
 * <br>Value Object de Produto
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
@Embeddable
public class Imagem implements Serializable {
    /**
     * Constante que representa a mensagem de erro a quando a criação de uma imagem 
     * inválida
     */
    private static final String MENSAGEM_IMAGEM_INVALIDA="Imagem Inválida!";
    /**
     * Byte Array com a imagem em formato de bytes
     */
    @Lob
    @Column(name = "IMAGEM")
    private byte[] image;
    /**
     * Cria uma nova instância de Imagem com a imagem convertida num array de bytes
     * @param image Array de bytes com o conteudo da imagem
     */
    public Imagem(byte[] image){verificarImagem(image);}
    /**
     * Método que verifica se uma determinada Imagem é igual à Imagem atual
     * @param obj Imagem com a imagem a ser comparada
     * @return boolean true caso as duas imagens sejam iguais,false caso contrario
     */
    @Override
    public boolean equals(Object obj){
        if(obj==this)return true;
        if(obj==null||this.getClass()!=obj.getClass())return false;
        byte[] otherImagem=((Imagem)obj).image;
        if(otherImagem.length!=image.length)return false;
        for(int i=0;i<image.length;i++){
            if(image[i]!=otherImagem[i])return false;
        }
        return true;
    }
    /**
     * Hashcode da Imagem
     * @return Integer com o hash code da Imagem atual
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Arrays.hashCode(this.image);
        return hash;
    }
    /**
     * Método que representa a informação textual de uma Imagem
     * @return String com a informação textual da Imagem atual
     */
    @Override
    public String toString(){ return new String(image);}
    /**
     * Verifica se uma determinada
     * @param imagem Array de bytes coma imagem a ser preenchida na Imagem atual
     */
    private void verificarImagem(byte[] imagem){
        if(imagem==null||imagem.length==0)throw new IllegalArgumentException(MENSAGEM_IMAGEM_INVALIDA);
        fillImage(imagem);
    }
    /**
     * Preenche a Imagem atual com uma outra imagem, evitando assim a associação de 
     * referencias, que quebra a imutabilidade do objecto
     * @param image Array de bytes coma imagem a ser preenchida na Imagem atual
     */
    private void fillImage(byte[] image){
        this.image=new byte[image.length];
        for(int i=0;i<image.length;i++)this.image[i]=image[i];
    }
    /**
     * Construtor protegido de modo a permitir a persistencia do objecto com o JPA
     */
    protected Imagem(){}
}
