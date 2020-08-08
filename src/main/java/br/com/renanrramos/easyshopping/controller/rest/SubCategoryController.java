/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 08/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * @author renan.ramos
 *
 */
@RestController
@CrossOrigin
@RequestMapping(path = "api/sub-categories", produces = "application/json")
@Api(tags = "SubCategories")
public class SubCategoryController {

}
