package com.dksoft.tn.repository;

import com.dksoft.tn.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}