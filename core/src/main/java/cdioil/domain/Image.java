package cdioil.domain;

import cdioil.framework.domain.ddd.ValueObject;
import java.io.Serializable;
import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

/**
 * Image class that represents the productImage of a product
 <br>Value Object of Product
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
@Embeddable
public class Image implements Serializable,ValueObject {
    /**
     * Constant that represents the error message that occurs whenever the creation 
     * of an Image is invalid
     */
    private static final String INVALID_IMAGE_MESSAGE="Imagem Inválida!";
    /**
     * Byte Array with the productImage represented in bytes
     */
    @Lob
    @Column(name = "IMAGE")
    private byte[] productImage;
    /**
     * Builds a new Image with the productImage converted in an array of bytes
     * @param image Byte Array with the productImage content
     */
    public Image(byte[] image){validateImage(image);}
    /**
     * Protected constructor in order to allow JPA persistence
     */
    protected Image(){}
    /**
     * Method that verifies if a certain Image is equal to the current Image
     * @param obj Image with the productImage being compared with the actual one
     * @return boolean true if both images are equal, false if not
     */
    @Override
    public boolean equals(Object obj){
        if(obj==this){
            return true;
        }
        if(obj==null||this.getClass()!=obj.getClass()){
            return false;
        }
        byte[] otherImage=((Image)obj).productImage;
        if(otherImage.length!=productImage.length){
            return false;
        }
        for(int i=0;i<productImage.length;i++){
            if(productImage[i]!=otherImage[i]){
                return false;
            }
        }
        return true;
    }
    /**
     * Image Hashcode
     * @return Integer with the current Image hashcode
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Arrays.hashCode(this.productImage);
        return hash;
    }
    /**
     * Method that represents the textual information of an Image
     * @return String with the textual representation of the current productImage
     */
    @Override
    public String toString(){ return new String(productImage);}
    /**
     * Method that verifies if an Image is valid or not
     * <br>An IllegalArgumentException can be thrown if the productImage is invalid
     * @param image Byte Array with the productImage being validated
     */
    private void validateImage(byte[] image){
        if(image==null||image.length==0){
            throw new IllegalArgumentException(INVALID_IMAGE_MESSAGE);
        }
        fillImage(image);
    }
    /**
     * Method that fills the current productImage with the a certain productImage
 <br>Needs to be like this in order to prevent the association of array 
     * references, that breaks the immutability of the value object
     * @param image Byte Array with the productImage content being filled on the current Image
     */
    private void fillImage(byte[] image){
        this.productImage=new byte[image.length];
        for(int i=0;i<image.length;i++){
            this.productImage[i]=image[i];
        }
    }
}
