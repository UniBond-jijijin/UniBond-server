package com.unibond.unibond.block.controller;

import com.unibond.unibond.block.service.BlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blocks")
public class BlockController {
    private final BlockService blockService;
}
