/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

/**
 * @author Pedro Portela
 * @author Ana Guerra (1161191)
 */
public interface TemplateWriter {
    /**
     * Exports statistics about a template to a file of any format.
     *
     * @return true, if the stats are successfully exported. Otherwise, returns false
     */
    public abstract boolean write();
    
}
