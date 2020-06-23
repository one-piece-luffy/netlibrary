package com.example.netlibrary;

public interface HttpStrategy {
    public void init();
    public String Get(String url);
    public String Post(String url);
}
