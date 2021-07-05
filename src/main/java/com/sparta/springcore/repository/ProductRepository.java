package com.sparta.springcore.repository;

import com.sparta.springcore.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByUserId(Long userId, Pageable pageable);      // page로 구현되어 있는 부분 pageable을 넣어 구현
}