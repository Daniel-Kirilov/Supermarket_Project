package com.endava.supermarket.repository;

import com.endava.supermarket.model.Supermarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuperMarketRepository extends JpaRepository<Supermarket, String> {

    Optional<Supermarket> findById(String id);

    boolean existsByAddress(String address);

    Supermarket getSupermarketByNameAndAddress(String name, String address);
}
