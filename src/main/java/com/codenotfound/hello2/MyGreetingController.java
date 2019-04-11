package com.codenotfound.hello2;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyGreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public MyGreeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new MyGreeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
}