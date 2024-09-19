/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 04/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.repository;

import java.util.List;
import java.util.Optional;

import br.com.renanrramos.easyshopping.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author renan.ramos
 *
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

	Optional<UserEntity> findTopUserByEmail(String email);

	Optional<List<UserEntity>> findUserByEmail(String email);

	Optional<UserEntity> findUserByTokenId(String tokenId);
}
