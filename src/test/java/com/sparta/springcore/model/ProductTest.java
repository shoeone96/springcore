package com.sparta.springcore.model;

import com.sparta.springcore.dto.ProductRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    @DisplayName("정상 케이스")      // 어떤 테스트가 어떤 결과를 가져올지 확인하기 위해 설정
    void createProduct_Normal() {
        // given        // 주어진 환경 > 이런 상황에서(관심상품이 만들어진다고 가)
        Long userId = 100L;
        String title = "오리온 꼬북칩 초코츄러스맛 160g";
        String image = "https://shopping-phinf.pstatic.net/main_2416122/24161228524.20200915151118.jpg";
        String link = "https://search.shopping.naver.com/gate.nhn?id=24161228524";
        int lprice = 2350;
        ProductRequestDto requestDto = new ProductRequestDto(title, image, link, lprice);       // 객체 생성 시 필요한 매개변수
        // when
        Product product = new Product(requestDto, userId);          // 다음과 같은 결과값(객체 생성)
        // then
        assertNull(product.getId());
        assertEquals(userId, product.getUserId());      // 앞에 값은 예상되는 값, 두 번째 값은 동일해야 하는 기대값
        assertEquals(title, product.getTitle());
        assertEquals(image, product.getImage());
        assertEquals(link, product.getLink());
        assertEquals(lprice, product.getLprice());
        assertEquals(0, product.getMyprice());
    }
    // test가 중간에 에러가 나면 이후의 동작을 시행하지 않는다
}
