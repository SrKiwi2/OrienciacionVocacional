package com.usic.usic.controller.Test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PreTestController {
    
    
    @GetMapping("/pre_test")
    public String pre_test() {
        return "test/pre-test/pre-test";
    }
    
}
