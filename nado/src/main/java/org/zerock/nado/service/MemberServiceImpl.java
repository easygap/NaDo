package org.zerock.nado.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.nado.dto.MemberDTO;
import org.zerock.nado.entity.Member;
import org.zerock.nado.repository.MemberRepository;

@Service                    /** 저는 서비스에요 */
@RequiredArgsConstructor    /** 밑에 MemberRepository의 생성자를 쓰지 않기 위해서 */
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

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
}
