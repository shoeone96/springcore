package com.sparta.springcore.service;

import com.sparta.springcore.dto.ProductMypriceRequestDto;
import com.sparta.springcore.dto.ProductRequestDto;
import com.sparta.springcore.model.Product;
import com.sparta.springcore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {
    // 멤버 변수 선언
    private final ProductRepository productRepository;
    private static final int MIN_PRICE = 100;

    // 생성자: ProductService() 가 생성될 때 호출됨
    @Autowired
    public ProductService(ProductRepository productRepository) {
        // 멤버 변수 생성
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(Long userId) {
        return productRepository.findAllByUserId(userId);
    }

    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
    public Product createProduct(ProductRequestDto requestDto, Long userId) {       // 상품 정보 말고도 userId도 같이 전달
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Product product = new Product(requestDto, userId);          // 새 객체 생성 시 전달한다
        productRepository.save(product);
        return product;
    }

    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
    public Product updateProduct(Long id, ProductMypriceRequestDto requestDto) {
        Product product = productRepository.findById(id).orElseThrow(           // test 주체자가 어떤 상황에 따라 어떤 결과가 나온다 설정해주지 않으면 자동 반영이 안됨
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );

        // 변경될 관심 가격이 유효한지 확인합니다.
        int myPrice = requestDto.getMyprice();
        if (myPrice < MIN_PRICE) {
            throw new IllegalArgumentException("유효하지 않은 관심 가격입니다. 최소 " + MIN_PRICE + " 원 이상으로 설정해 주세요.");
        }

        product.updateMyPrice(myPrice);
        return product;
    }
    // 모든 상품 조회 (관리자용)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}

//package com.sparta.springcore.service;
//
//import com.sparta.springcore.dto.ProductMypriceRequestDto;
//import com.sparta.springcore.dto.ProductRequestDto;
//import com.sparta.springcore.model.Product;
//import com.sparta.springcore.repository.ProductRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.sql.SQLException;
//import java.util.List;
//
//@RequiredArgsConstructor
//@Service
//public class ProductService {
//    // 멤버 변수 선언
//    private final ProductRepository productRepository;
//
//    //    public List<Product> getProducts() throws SQLException {
////        ProductRepository productRepository = new ProductRepository();
////        return productRepository.getProducts();
////    }
//
//    public List<Product> getProducts() throws SQLException {
//        // 멤버 변수 사용
//        return productRepository.getProducts();
//    }
//
////    public Product createProduct(ProductRequestDto requestDto) throws SQLException {
////        ProductRepository productRepository = new ProductRepository();
////        // 요청받은 DTO 로 DB에 저장할 객체 만들기
////        Product product = new Product(requestDto);
////        productRepository.createProduct(product);
////        return product;
////    }
//    public Product createProduct(ProductRequestDto requestDto) throws SQLException {
//        // 요청받은 DTO 로 DB에 저장할 객체 만들기
//        Product product = new Product(requestDto);
//        productRepository.createProduct(product);
//        return product;
//    }
//
////    public Product updateProduct(Long id, ProductMypriceRequestDto requestDto) throws SQLException {
////        ProductRepository productRepository = new ProductRepository();
////        Product product = productRepository.getProduct(id);
////        if (product == null) {
////            throw new NullPointerException("해당 아이디가 존재하지 않습니다.");
////        }
////        int myPrice = requestDto.getMyprice();
////        productRepository.updateProductMyPrice(id, myPrice);
////        return product;
////    }
//    public Product updateProduct(Long id, ProductMypriceRequestDto requestDto) throws SQLException {
//        Product product = productRepository.getProduct(id);
//        if (product == null) {
//            throw new NullPointerException("해당 아이디가 존재하지 않습니다.");
//        }
//        int myPrice = requestDto.getMyprice();
//        productRepository.updateProductMyPrice(id, myPrice);
//        return product;
//    }
//}
//
////package com.sparta.springcore;
////
////import java.sql.SQLException;
////import java.util.List;
////
////public class ProductService {
////
//
////
//
////
//
////}