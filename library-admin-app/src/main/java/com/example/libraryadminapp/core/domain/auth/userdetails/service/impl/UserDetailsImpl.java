package com.example.libraryadminapp.core.domain.auth.userdetails.service.impl;

import com.example.libraryadminapp.core.domain.student.entity.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

@Getter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String mobileNumber;

    @JsonIgnore
    private String password;


    public UserDetailsImpl(Long id, String username, String mobileNumber, String password) {

        this.id = id;
        this.username = username;
        this.mobileNumber = mobileNumber;
        this.password = password;
    }

    public static UserDetailsImpl build(Student student) {

        return new UserDetailsImpl(
                student.getId(),
                student.getStudentName(),
                student.getMobileNumber(),
                student.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}

