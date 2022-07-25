package com.example.algamoney.api.event.listener;

import com.example.algamoney.api.event.RecursoCriadorEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class RecursoCriadorListener implements ApplicationListener<RecursoCriadorEvent> {

    @Override
    public void onApplicationEvent(RecursoCriadorEvent recursoCriadorEvent){
        HttpServletResponse response = recursoCriadorEvent.getResponse();
        Long id = recursoCriadorEvent.getId();

        adicionarHeaderLocation(response, id);
    }

    private void adicionarHeaderLocation(HttpServletResponse response, Long id) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
        response.setHeader("Location", uri.toASCIIString());//aqui escreve a location da uri com o codigo criado
    }

}
