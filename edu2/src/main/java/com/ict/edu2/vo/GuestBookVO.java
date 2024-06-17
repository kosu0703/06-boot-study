package com.ict.edu2.vo;

import lombok.Data;

@Data
public class GuestBookVO {
    private String idx, name, subject, content, email, pwd, regdate;
}
