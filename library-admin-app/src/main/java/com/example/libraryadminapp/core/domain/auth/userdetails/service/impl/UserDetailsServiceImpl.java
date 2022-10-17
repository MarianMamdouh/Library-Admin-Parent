package com.example.libraryadminapp.core.domain.auth.userdetails.service.impl;

import com.example.libraryadminapp.core.domain.student.entity.Student;
import com.example.libraryadminapp.core.domain.student.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final StudentRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String studentName) throws UsernameNotFoundException {
        final Student student = userRepository.findByStudentName(studentName)
                .orElseThrow(() -> new UsernameNotFoundException("Student " + studentName + " Not Found"));

        return UserDetailsImpl.build(student);
    }

}