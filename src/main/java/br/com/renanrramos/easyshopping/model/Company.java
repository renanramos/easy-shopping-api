/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * @author renan.ramos
 *
 */
@Entity
@PrimaryKeyJoinColumn(name="id")
public class Company extends User {

	private static final long serialVersionUID = 1302828903094520601L;

	public Company() {
		
	}
	
}
