package com.likelion.domain.entity;

import com.likelion.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String username;

    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String nickname;

    @Builder
    public User(String email, String username, String password, UserRole role, String nickname) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.nickname = nickname;
    }

    public void update(String password) {
        this.password = password;
    }

    /**
     * Security
     */
    @Override // 권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override // 사용자 id를 반환
    public String getUsername() {
        return email;
    }

    @Override // 패스워드 반환
    public String getPassword() {
        return password;
    }

    @Override // 계쩡 만료 여부
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // 계정 잠금 여부
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override // 패스워드 만료 여부
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override // 계정 사용 가능 여부
    public boolean isEnabled() {
        return true;
    }
}
