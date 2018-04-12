package cdioil.framework.domain.ddd;

import cdioil.framework.domain.Identifiable;

/**
 * Interface that represents the Aggregate Root pattern
 * <br>Based on professor's Paulo Gandra Sousa Aggregate Root implementation 
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @param <I> Generic-Type that represents the identifiable of the class
 * <br>(TO-DO: Extend Domain Entity that extends Identifiable) 
 */
public interface AggregateRoot<I> extends Identifiable<I>{}
