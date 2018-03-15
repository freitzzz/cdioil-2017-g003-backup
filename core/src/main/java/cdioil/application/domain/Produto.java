/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author António Sousa [1161371]
 */
@Entity
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDPRODUTO")
    private Long id;

    /**
     * Nome do produto.
     */
    @Column(name = "NOME")
    private String nome;

    /**
     * Construtor protegido apenas para uso de JPA.
     */
    protected Produto() {

    }

    public Produto(String nome) {
        this.nome = nome;
    }

}
