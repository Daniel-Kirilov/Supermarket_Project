package com.endava.supermarket.repository;

import com.endava.supermarket.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

    boolean existsByName(String name);

}
