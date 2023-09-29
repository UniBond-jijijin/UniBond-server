package com.unibond.unibond.alarm.controller;

import com.unibond.unibond.alarm.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;
}
