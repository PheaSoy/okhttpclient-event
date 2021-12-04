package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class MyRouter extends RouteBuilder {
    @Autowired
    DataSource dataSource;

    @Autowired
    private Environment env;

    @Override
    public void configure() throws Exception {
//
//        from("timer://MoveNewCustomersEveryHour?period=2000")
//                .setBody(constant("select * from customer where create_time > (sysdate-1/24)"))
//                .to("jdbc:testdb")
//                .split(body())
//                //.process(new MyCustomerProcessor()) //filter/transform results as needed
//                .setBody(simple("insert into processed_customer values('${body[ID]}','${body[NAME]}')"))
//                .to("jdbc:testdb");
//
//        from("timer:foo").
//                setBody(constant("hello world")).
//                to("log:bar");
//        restConfiguration().component("servlet");

        restConfiguration()
                .contextPath("/app")
                //.apiContextPath("/api-doc")
                //.apiProperty("api.title", "REST API for processing Order")
                //.apiProperty("api.version", "1.0")
               // .apiProperty("cors", "true")
                //.apiContextRouteId("doc-api")
                .port(env.getProperty("server.port", "8080"))
                .bindingMode(RestBindingMode.json);

        rest("/books")
                .get("/find")
                .consumes("application/json")
                .outType(Book.class)
                .produces(MediaType.APPLICATION_JSON_VALUE).route()
//                .to("sql:{{sql.selectById}}")
                .setBody(constant("SELECT ID, NAME, AUTHOR FROM BOOK WHERE ID = :#id?outputType=SelectOne&outputClass=com.example.demo.Book"))
                .to("jdbc:dataSource")
                .log("--- select a book ${body} ---");
    }
}
