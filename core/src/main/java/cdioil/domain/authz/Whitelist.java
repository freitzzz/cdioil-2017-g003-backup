package cdioil.domain.authz;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Classe Whitelist que representa o dominio e subdominio de um endereço de email
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
@Entity
public class Whitelist implements Serializable {
    /**
     * Código de serialização
     */
    private static final long serialVersionUID=5L;
    /**
     * Long com o identificador da identidade na base de dados
     */
    @Id
    @GeneratedValue
    private long id;
    /**
     * String que representa o dominio princial de um endereço de email
     */
    private String dominio;
    /**
     * String que representa o subdominio de um endereço de email
     */
    private String subDominio;
    /**
     * Constrói uma nova instância de Dominio de um determinado email
     * @param dominio String com o dominio que contém um certo dominio
     */
    public Whitelist(String dominio){/*Ainda precisa de ser discutido*/}
    /**
     * Método que verifica se dois Dominios são iguais
     * @param obj Whitelist com o dominio de um email a ser comparado com o Whitelist atual
     * @return boolean true se os dominios são iguais, false caso contrário
     */
    @Override
    public boolean equals(Object obj){
        if(this==obj)return true;
        if(obj==null||this.getClass()!=obj.getClass())return false;
        return dominio.equalsIgnoreCase(((Whitelist)obj).dominio)
                &&subDominio.equalsIgnoreCase(((Whitelist)obj).subDominio);
    }
    /**
     * Hashcode do Whitelist
     * @return Integer com o hashcode do dominio atual
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.dominio);
        hash = 29 * hash + Objects.hashCode(this.subDominio);
        return hash;
    }
    /**
     * Método que representa a informação textual de um Whitelist
     * @return String coma informação textual do Whitelist atual
     */
    @Override
    public String toString(){return "Dominio: "+dominio+"\nSubdominio: "+subDominio;}
    /**
     * Construtor protegido de modo a permitir a persitencia com o JPA
     */
    protected Whitelist(){}
}
