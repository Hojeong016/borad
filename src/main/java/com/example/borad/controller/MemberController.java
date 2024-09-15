package com.example.borad.controller;

import com.example.borad.dto.MemberForm;
import com.example.borad.entity.Member;
import com.example.borad.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    //회원가입 페이지
    @GetMapping("/joinView")
    public String join() {
        return "/member/join";
    }

    @PostMapping("/joinForm")
    public String joinForm(MemberForm memberForm) {
        //엔티티로 변경 후 디비에 저장하기
        Member member = memberForm.toEntity();
        log.info("member : {}", member);
        memberRepository.save(member);
        return "redirect:/members/"+ member.getId();
    }

    //회원 조회
    @GetMapping("/members/{id}")
    public String showMemer(@PathVariable Long id, Model model){
        //아이디 찾아서 디비
        Member member = memberRepository.findById(id).orElse(null);
        // 엔티티 담아서
        model.addAttribute("member", member);
        // 모델 전달
        return "/member/memberShow";
    }

    @GetMapping("/members")
    public String showAllMembers(Model model){
        //전체 정보 디비에서 리스트로 담아오기
        List<Member> members = memberRepository.findAll();
        //모델로 전달하기
        model.addAttribute("members", members);
        return "/member/memberIndex";
    }

    @GetMapping("/members/{id}/edit")
    public String editForm(@PathVariable Long id, Model model){
        Member member = memberRepository.findById(id).orElse(null);
        model.addAttribute("memberUp", member);
        return "/member/editForm";
    }

    @PostMapping("/members/update")
    public String updateForm(MemberForm memberForm){
        Member member = memberForm.toEntity();

        Member save = memberRepository.findById(member.getId()).orElse(null);

        if(save != null){
            memberRepository.save(member);
        }else {throw new NullPointerException("멤버를 찾을 수 없습니다.");}

        return "redirect:/members/"+ member.getId();
    }
}
