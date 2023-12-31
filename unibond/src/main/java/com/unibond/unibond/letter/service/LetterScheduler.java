package com.unibond.unibond.letter.service;

import com.unibond.unibond.letter.repository.LetterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LetterScheduler {
    private final LetterRepository letterRepository;

    @Transactional
    @Scheduled(cron = "0 0,30 * * * *")
    public void sendLetter() {
        letterRepository.bulkSendLetter(LocalDateTime.now().minusHours(1L));
    }
}
