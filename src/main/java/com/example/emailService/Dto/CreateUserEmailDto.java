package com.example.emailService.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUserEmailDto {
        String from;
        String to;
        String body;
        String subject;
}
