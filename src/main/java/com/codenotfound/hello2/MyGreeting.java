package com.codenotfound.hello2;

public class MyGreeting {
    private final long id;
    private final String content;

    public MyGreeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
