package com.unibond.unibond.report.controller;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponse;
import com.unibond.unibond.report.dto.ReportReqDto;
import com.unibond.unibond.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
public class ReportController {
    private final ReportService reportService;

    @PostMapping("")
    public BaseResponse<?> makeReport(@RequestBody ReportReqDto reqDto,
                                      @RequestHeader("Authorization") Long loginId) {
        try {
            return new BaseResponse<>(reportService.makeReport(reqDto));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
