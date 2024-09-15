package com.example.borad.dto;

import com.example.borad.entity.Member;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberForm {

    private long id;
    private String email;
    private String password;

    public Member toEntity(){
        return new Member(id, email, password);
    }
}
