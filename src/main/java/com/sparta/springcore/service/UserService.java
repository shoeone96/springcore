package com.sparta.springcore.service;

import com.sparta.springcore.dto.SignupRequestDto;
import com.sparta.springcore.model.User;
import com.sparta.springcore.model.UserRole;
import com.sparta.springcore.repository.UserRepository;
import com.sparta.springcore.security.UserDetailsImpl;
import com.sparta.springcore.security.kakao.KakaoOAuth2;
import com.sparta.springcore.security.kakao.KakaoUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    // 카카오 로그인을 위해 필요한 선언
    private final KakaoOAuth2 kakaoOAuth2;
    //    private final AuthenticationManager authenticationManager;
    //관리자 승인을 위한 토큰
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    //    @Autowired      // 회원 가입을 위해 중복 검사시 기존에 있는 id 인지 db 검사를 해야 하기에 검색하는 부분
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, KakaoOAuth2 kakaoOAuth2, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.kakaoOAuth2 = kakaoOAuth2;
//        this.authenticationManager = authenticationManager;
    }

    public void registerUser(SignupRequestDto requestDto) {     // 등록된 request dto를 확인하고
        String username = requestDto.getUsername();
//        String password = requestDto.getPassword();
        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);     // 그 하나를 찾아낸다면 중복된 id가 존재합니다
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }
        // 패스워드 인코딩
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();       // 가입 요청할 때 받은 email, id, pw를 모두 받게 된다

        // 사용자 ROLE 확인
        UserRole role = UserRole.USER;                                  // 처음에는 user를 주나
        if (requestDto.isAdmin()) {                                      // 만약 어드민 요청을 한다면
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {      // 토큰 일치 여부 확인 후 결정
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRole.ADMIN;
        }

        User user = new User(username, password, email, role);      // entity 생성을 위해 필요한 부분
        userRepository.save(user);
    }

    // 카카오로그인을 위한 정보처리
    public void kakaoLogin(String authorizedCode) {
        // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
        KakaoUserInfo userInfo = kakaoOAuth2.getUserInfo(authorizedCode);
        Long kakaoId = userInfo.getId();
        String nickname = userInfo.getNickname();
        String email = userInfo.getEmail();

        // 우리 DB 에서 회원 Id 와 패스워드
        // 회원 Id = 카카오 nickname
//        String username = nickname;
//        // 패스워드 = 카카오 Id + ADMIN TOKEN
//        String password = kakaoId + ADMIN_TOKEN;        // 카카오 로그인 시 비밀번호 별도의 처리를 하지 않기 때문에 공통되지 않은 값으로 설정

        // DB 에 중복된 Kakao Id 가 있는지 확인  > 한번이라도 로그인을 했으면 저장이 되었을 것이기 때문에 확인 절차 필요
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);

        // 카카오 정보로 회원가입
        if (kakaoUser == null) {
//            // 패스워드 인코딩
//            String encodedPassword = passwordEncoder.encode(password);
//            // ROLE = 사용자
//            UserRole role = UserRole.USER;
//                // 카카오 이메일과 동일한 이메일 회원이 있는 경우
//                // 카카오 Id 를 회원정보에 저장
//            kakaoUser = new User(nickname, encodedPassword, email, role, kakaoId);
//            userRepository.save(kakaoUser);
//        }

            // 카카오 이메일과 동일한 이메일을 가진 회원이 있는지 확인
            User sameEmailUser = userRepository.findByEmail(email).orElse(null);
            if (sameEmailUser != null) {
                kakaoUser = sameEmailUser;
                // 카카오 이메일과 동일한 이메일 회원이 있는 경우
                // 카카오 Id 를 회원정보에 저장
                kakaoUser.setKakaoId(kakaoId);
                userRepository.save(kakaoUser);

            } else {
                // 카카오 정보로 회원가입
                // username = 카카오 nickname
                String username = nickname;
                // password = 카카오 Id + ADMIN TOKEN
                String password = kakaoId + ADMIN_TOKEN;
                // 패스워드 인코딩
                String encodedPassword = passwordEncoder.encode(password);
                // ROLE = 사용자
                UserRole role = UserRole.USER;

                kakaoUser = new User(username, encodedPassword, email, role, kakaoId);
                userRepository.save(kakaoUser);
            }
        }


//        // 로그인 처리
//        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(username, password);
//        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 강제 로그인 처리
        UserDetailsImpl userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
}