package com.endava.wallet.controller;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WalletController {
    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
