package org.zerock.nado.service;

import org.zerock.nado.dto.MemberDTO;
import org.zerock.nado.entity.Member;

public interface MemberService {
    void createMember(MemberDTO memberDTO);
    // Member join(MemberDTO memberDTO);
}
