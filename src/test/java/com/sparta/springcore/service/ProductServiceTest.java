package com.sparta.springcore.service;

import com.sparta.springcore.dto.ProductMypriceRequestDto;
import com.sparta.springcore.dto.ProductRequestDto;
import com.sparta.springcore.model.Product;
import com.sparta.springcore.repository.ProductRepository;
import com.sparta.springcore.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductServiceTest {
    @Mock
    ProductRepository productRepository;        // mock을 사용하여 굳이 MockRepository를 생성하지 않더라도 실제 productRepositroy를 통해서 Mock 형식의 객체를 가져올 수 있다

    @Test
    @DisplayName("updateProduct() 에 의해 관심 가격이 3만원으로 변경되는지 확인")
    void updateProduct_Normal() {
        // given
        Long productId = 100L;
        int myprice = 30000;

        ProductMypriceRequestDto requestMyPriceDto = new ProductMypriceRequestDto(
                myprice
        );

        Long userId = 12345L;
        ProductRequestDto requestProductDto = new ProductRequestDto(
                "오리온 꼬북칩 초코츄러스맛 160g",
                "https://shopping-phinf.pstatic.net/main_2416122/24161228524.20200915151118.jpg",
                "https://search.shopping.naver.com/gate.nhn?id=24161228524",
                2350
        );

        Product product = new Product(requestProductDto, userId);

        ProductService productService = new ProductService(productRepository);

        // when                 // productservice의 과정에서 잘 실현되어 들어오는가를 확인
        Product result = productService.updateProduct(productId, requestMyPriceDto);

        // then
        assertEquals(myprice, result.getMyprice());
    }

}
