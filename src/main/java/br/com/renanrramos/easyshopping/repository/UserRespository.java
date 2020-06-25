package br.com.renanrramos.easyshopping.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.renanrramos.easyshopping.model.User;

public interface UserRespository extends CrudRepository<User, Long>{

}
