package project.financial.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    //응답 성공여부
    private boolean success;

    //응답 코드번호
    private int code;

    //응답 메시지
    private String msg;
}
