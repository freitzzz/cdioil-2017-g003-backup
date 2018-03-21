package cdioil.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes Unitários relativamente à classe Imagem
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class ImagemTest {
    
    public ImagemTest() {
    }
    @Test
    public void testConstrutor(){
        assertNotNull("A condição deve acertar pois os argumentos são válidos",createImagem(new byte[1]));
        assertNull("A condição deve acertar pois os argumentos são inválidos",createImagem(null));
        assertNull("A condição deve acertar pois os argumentos são inválidos",createImagem(new byte[0]));
    }
    /**
     * Test of equals method, of class Imagem.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        String conteudoImagemX="Imagem X";
        String conteudoImagemY="Imagem Y";
        String conteudoImagemZ="Imagem X";
        String conteudoImagemXX="Imagem XX";
        Imagem imagemX=createImagem(conteudoImagemX.getBytes());
        Imagem imagemY=createImagem(conteudoImagemY.getBytes());
        Imagem imagemZ=createImagem(conteudoImagemZ.getBytes());
        Imagem imagemXX=createImagem(conteudoImagemXX.getBytes());
        assertEquals("A condição deve acertar pois as Imagens são instâncias iguais",imagemX,imagemX);
        assertNotEquals("A condição deve falhar pois as imagens sao diferentes",imagemX,imagemY);
        assertEquals("A condição deve acertar pois as Imagens são iguais",imagemX,imagemZ);
        assertNotEquals("A condição deve falhar pois uma das imagens é null",imagemX,null);
        assertNotEquals("A condição deve falhar pois as classes sao diferentes",imagemX,"");
        assertNotEquals("A condição deve falhar pois as Imagens são diferentes",imagemX,imagemXX);
    }

    /**
     * Test of hashCode method, of class Imagem.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Imagem instance = new Imagem("11001".getBytes());
        int expResult = 75389159;
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Imagem.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String conteudoImagem="11001";
        Imagem instance = new Imagem(conteudoImagem.getBytes());
        String expResult = conteudoImagem;
        String result = instance.toString();
        assertEquals("A condição deve acertar pois o conteudo as Strings são iguais",expResult, result);
    }
    /**
     * Métoodo que cria uma instância de Imagem
     * @param imagem Array de Bytes com o contéudo da imagem
     * @return Imagem com o a imagem criada, ou null caso os parametros sejam invalidos
     */
    private Imagem createImagem(byte[] imagem){try{return new Imagem(imagem);}catch(IllegalArgumentException e){return null;}}
    
}
