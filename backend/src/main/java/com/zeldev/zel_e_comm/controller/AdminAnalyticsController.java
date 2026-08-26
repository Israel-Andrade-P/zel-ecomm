package com.zeldev.zel_e_comm.controller;

import com.zeldev.zel_e_comm.dto.response.AnalyticsOverview;
import com.zeldev.zel_e_comm.service.AdminAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminAnalyticsController {
    private final AdminAnalyticsService adminAnalyticsService;

    @GetMapping("/overview")
    public AnalyticsOverview getOverview() {
        return adminAnalyticsService.getOverview();
    }
}
