package br.com.dfdevforge.sisfintransaction.supermarket.model.address.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.supermarket.model.address.entities.AddressEntity;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, String> {
	public AddressEntity findByIdentity(String identity);
	public List<AddressEntity> findByUserIdentityOrderByStreetAsc(Long userIdentity);
}