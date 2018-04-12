package cdioil.domain;

import java.io.Serializable;
import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

/**
 * Image class that represents the image of a product
 * <br>Value Object of Product
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
     * Byte Array with the image represented in bytes
     */
    @Lob
    @Column(name = "IMAGE")
    private byte[] image;
    /**
     * Builds a new Image with the image converted in an array of bytes
     * @param image Byte Array with the image content
     */
    public Image(byte[] image){validateImage(image);}
    /**
     * Method that verifies if a certain Image is equal to the current Image
     * @param obj Image with the image being compared with the actual one
     * @return boolean true if both images are equal, false if not
     */
    @Override
    public boolean equals(Object obj){
        if(obj==this)return true;
        if(obj==null||this.getClass()!=obj.getClass())return false;
        byte[] otherImage=((Image)obj).image;
        if(otherImage.length!=image.length)return false;
        for(int i=0;i<image.length;i++){
            if(image[i]!=otherImage[i])return false;
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
        hash = 11 * hash + Arrays.hashCode(this.image);
        return hash;
    }
    /**
     * Method that represents the textual information of an Image
     * @return String with the textual representation of the current image
     */
    @Override
    public String toString(){ return new String(image);}
    /**
     * Method that verifies if an Image is valid or not
     * <br>An IllegalArgumentException can be thrown if the image is invalid
     * @param image Byte Array with the image being validated
     */
    private void validateImage(byte[] image){
        if(image==null||image.length==0)throw new IllegalArgumentException(INVALID_IMAGE_MESSAGE);
        fillImage(image);
    }
    /**
     * Method that fills the current image with the a certain image
     * <br>Needs to be like this in order to prevent the association of array 
     * references, that breaks the immutability of the value object
     * @param image Byte Array with the image content being filled on the current Image
     */
    private void fillImage(byte[] image){
        this.image=new byte[image.length];
        for(int i=0;i<image.length;i++)this.image[i]=image[i];
    }
    /**
     * Protected constructor in order to allow JPA persistence
     */
    protected Image(){}
}
