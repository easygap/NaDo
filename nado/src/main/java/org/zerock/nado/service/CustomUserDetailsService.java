package org.zerock.nado.service;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.nado.dto.CustomUserDetails;
import org.zerock.nado.entity.Member;
import org.zerock.nado.repository.MemberRepository;

/** 로그인 검증 로직 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        //DB에서 조회
        Member userData = memberRepository.findByid(id);

        if (userData != null) {

            // UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new CustomUserDetails(userData);
        }

        /** 사용자가 존재하지 않으면 null 반환 */
        return null;
    }
}