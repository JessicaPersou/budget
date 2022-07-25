package com.example.algamoney.api.event;

import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

public class RecursoCriadorEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;

    private HttpServletResponse response;
    private Long id;

    public RecursoCriadorEvent(Object source, HttpServletResponse response, Long id){
        super(id);
        this.response = response;
        this.id = id;
    }

    public HttpServletResponse getResponse(){
        return response;
    }
    public Long getId(){
        return id;
    }
}
