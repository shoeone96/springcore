package com.sparta.springcore.mockobject;

import com.sparta.springcore.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockProductRepository {

    private List<Product> products = new ArrayList<>();
    private Long id = 1L;

    // 회원 ID 로 등록된 모든 상품 조회
    public List<Product> findAllByUsername(Long userId) {
        List<Product> userProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getId().equals(userId)) {
                userProducts.add(product);
            }
        }

        return userProducts;
    }

    // 모든 상품 조회 (관리자용)
    public List<Product> findAll() {
        return products;
    }

    // 상품 저장        // db에서 하는 것처럼 저장하고 증가하는 모습을 보여줌, 실제로는 저장되지 않지만 저장된 것 처럼 만들어줌
    public Product save(Product product) {
        product.setId(id);
        ++id;
        products.add(product);
        return product;
    }

    public Optional<Product> findById(Long id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return Optional.of(product);
            }
        }

        return Optional.empty();
    }
}