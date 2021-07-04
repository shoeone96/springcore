
package com.sparta.springcore.security;

import com.sparta.springcore.model.User;
import com.sparta.springcore.model.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {       // userdetails interface를 구현하고 객체로 담고있다

    private final User user;        // Db model에 선언된 user를 가져오는 과정

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getPassword() {       // user에서 조회한 password를 가지고
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private static final String ROLE_PREFIX = "ROLE_";      // 사용자와 관리자 role을 구분할 때 사용 > ROLE_USER / ROLE_ADMIN

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRole userRole = user.getRole();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(ROLE_PREFIX + userRole.toString());   // 여기서 객체를 만들어 Security에 전달
        Collection<GrantedAuthority> authorities = new ArrayList<>();       // 사용자의 권한을 여러 개 줄 수도 있어서 list로 준다
        authorities.add(authority);

        return authorities;
    }
    // 대상의 Role을 가지고 나와서 SpringSecurity에 인식시켜주는 과정
}
