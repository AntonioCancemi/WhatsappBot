package com.whatsbot.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InfoServiceTest {

    @Autowired
    private InfoService infoService;

    @Test
    void openHoursNotEmpty() {
        assertThat(infoService.getOpenHours()).isNotEmpty();
    }
}
