package org.zerock.nado.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.nado.dto.MemberDTO;
import org.zerock.nado.entity.Member;
import org.zerock.nado.repository.MemberRepository;

@Service                    /** 저는 서비스에요 */
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void createMember(MemberDTO memberDTO){
        String id = memberDTO.getId();
        String pass = memberDTO.getPass();
        String name = memberDTO.getName();
        String tel = memberDTO.getTel();

        /** DB에 이미 동일한 ID를 가진 회원이 존재하는가? */
        Boolean isExist = memberRepository.existsByid(id);

        if(isExist){
            return;
        }

        Member data = new Member();

        data.setId(id);
        data.setPass(bCryptPasswordEncoder.encode(pass));
        data.setName(name);
        data.setTel(tel);
        data.setRole("ROLE_ADMIN");

        memberRepository.save(data);
    }

    /*
    @Override
    public Member join(MemberDTO memberDTO){

        Member member = Member.builder()
                .id(memberDTO.getId())
                .pass(memberDTO.getPass())
                .name(memberDTO.getName())
                .tel(memberDTO.getTel())
                .build();

        return memberRepository.save(member);
    }
    */
}
