package com.unibond.unibond.letter.controller;

import com.unibond.unibond.letter.service.LetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LetterController {
    private final LetterService letterService;
}
