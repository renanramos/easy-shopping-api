/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 04/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.renanrramos.easyshopping.model.User;

/**
 * @author renan.ramos
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findTopUserByEmail(String email);

	Optional<List<User>> findUserByEmail(String email);

	Optional<User> findUserByTokenId(String tokenId);
}
