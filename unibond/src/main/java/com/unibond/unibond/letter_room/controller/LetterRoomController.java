package com.unibond.unibond.letter_room.controller;

import com.unibond.unibond.letter_room.service.LetterRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LetterRoomController {
    private final LetterRoomService letterRoomService;
}
