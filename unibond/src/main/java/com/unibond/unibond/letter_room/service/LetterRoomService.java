package com.unibond.unibond.letter_room.service;

import com.unibond.unibond.letter_room.repository.LetterRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterRoomService {
    private final LetterRoomRepository letterRoomRepository;
}
