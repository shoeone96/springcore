package com.sparta.springcore.controller;

import com.sparta.springcore.model.Product;
import com.sparta.springcore.dto.ProductMypriceRequestDto;
import com.sparta.springcore.dto.ProductRequestDto;
import com.sparta.springcore.security.UserDetailsImpl;
import com.sparta.springcore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
 // final로 선언된 멤버 변수를 자동으로 생성합니다.
@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class ProductController {
    // 멤버 변수 선언
    private final ProductService productService;

    // 생성자: ProductController() 가 생성될 때 호출됨
    @Autowired
    public ProductController(ProductService productService) {
        // 멤버 변수 생성
//        productService = new ProductService();    // 가위를 새로 생성할 필요 없이 이미 만들어진 가위를 가져올거야 이 자식아
        this.productService = productService;       // 여기서 필요한 product service는 앞서 Bean에 저장되어 있는 productService 객체를 가져온다
    }
// 등록된 전체 상품 목록 조회
//    @GetMapping("/api/products")
//    public List<Product> getProducts() throws SQLException {
//        ProductService productService = new ProductService();
//        List<Product> products = productService.getProducts();
//        // 응답 보내기
//        return products;
//    }

    // 등록된 전체 상품 목록 조회
    @GetMapping("/api/products")
    public List<Product> getProducts(@AuthenticationPrincipal UserDetailsImpl userDetails) {        // 로그인 된 사용자의 정보 가져오기
        Long userId = userDetails.getUser().getId();            // 로그인 된 사용자의 id 값 가져오기
        return productService.getProducts(userId);              // id 값에 해당하는 product 가져오기
        // 응답 보내기
    }

    // 신규 상품 등록
    @PostMapping("/api/products")
    public Product createProduct(@RequestBody ProductRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 사용자의 로그인 정보를 자동으로 가져오는 부분까지 완료
        // 로그인 료어 있는 ID
        Long userId = userDetails.getUser().getId();        // 가져온 사용자의 정보 중에서도 user의 Id값을 가져온다
        Product product = productService.createProduct(requestDto, userId);        // 관심 상품의 정보 뿐만 아니라 user의 Id 정보까지 같이 보낸다
        return product;
    }
    //    // 신규 상품 등록
//    @PostMapping("/api/products")
//    public Product createProduct(@RequestBody ProductRequestDto requestDto) throws SQLException {
//        ProductService productService = new ProductService();
//        Product product = productService.createProduct(requestDto);
//        // 응답 보내기
//        return product;
//    }
//

    // 설정 가격 변경
    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) throws SQLException {
        Product product = productService.updateProduct(id, requestDto);
        return product.getId();
    }
    //    // 설정 가격 변경
//    @PutMapping("/api/products/{id}")
//    public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) throws SQLException {
//        ProductService productService = new ProductService();
//        Product product = productService.updateProduct(id, requestDto);
//        return product.getId();
//    }
//}
     // (관리자용) 등록된 모든 상품 목록 조회
     @Secured("ROLE_ADMIN") // 마찬가지로 어드민 role을 가진 자만 사용 가능
     @GetMapping("/api/admin/products")
     public List<Product> getAllProducts() {
         return productService.getAllProducts();
     }
}

//package com.sparta.springcore;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.sql.SQLException;
//import java.util.List;
//
//@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다.
//@RestController // JSON으로 데이터를 주고받음을 선언합니다.
//public class ProductController {

//

