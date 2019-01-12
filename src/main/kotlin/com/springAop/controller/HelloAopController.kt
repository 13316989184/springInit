package com.springAop.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloAopController {

    @GetMapping("hello")
    fun hello(): String {
        return "hahah"
    }

}