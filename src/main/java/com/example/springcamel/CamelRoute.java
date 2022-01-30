package com.example.springcamel;

import lombok.Data;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class CamelRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true");

        rest()
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .get("/hello")
                .to("direct:hello");

        from("direct:hello")
                .process(e -> {}).id("p1")
                .process(e -> {}).id("p2")
                .transform()
                .constant(new Message("Hello"));
    }
}

@Data
class Message {
    private final String message;
}
