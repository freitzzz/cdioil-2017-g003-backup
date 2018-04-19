/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import cdioil.framework.domain.ddd.ValueObject;
import javax.persistence.Entity;

/**
 * Rpresnets the code SKU (European Article Number)
 *
 * @author Ana Guerra (1161191)
 */
@Entity(name = "SKU")
public class SKU extends Code<String> implements ValueObject {

    /**
     * Protected constructor for JPA.
     */
    protected SKU() {

    }
    /**
     * Constructs a new instance with a given code.
     *
     * @param code code to be a attribute
     */
    public SKU(String code) {
        this.code = code;
    }
}
