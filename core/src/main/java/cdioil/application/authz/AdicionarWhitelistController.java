/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.authz;

import cdioil.domain.authz.Whitelist;
import cdioil.persistence.impl.WhitelistRepositoryImpl;

/**
 * Controller responsável pela US103.
 *
 * @author António Sousa [1161371]
 */
public class AdicionarWhitelistController {

    WhitelistRepositoryImpl repo;

    /**
     * Instancia o controller.
     */
    public AdicionarWhitelistController() {
        repo = new WhitelistRepositoryImpl();
    }

    /**
     * Cria um dominio de email e adiciona-o ao repositorio.
     *
     * @param dominio dominio de email
     */
    public void adicionarDominioAutorizado(String dominio) {

        Whitelist whitelist = new Whitelist(dominio);

        repo.add(whitelist);
    }

}
