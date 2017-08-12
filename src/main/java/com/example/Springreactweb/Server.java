package com.example.Springreactweb;

import com.example.Springreactweb.handler.PersonHandler;
import com.example.Springreactweb.repo.PersonRepository;
import com.example.Springreactweb.repo.PersonRepositoryImpl;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.http.server.reactive.ServletHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.ipc.netty.http.server.HttpServer;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

/**
 * Created by amarendra on 12/08/17.
 */
public class Server {

    public static final String HOST = "localhost";

    public static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        Server server = new Server();

        //HttpHandler httpHandler = server.standalone();

        HttpHandler httpHandler = server.applicationContext();

        //server.startTomcatServer(httpHandler);

        server.startReactorServer(httpHandler);

        System.out.println("Hit Enter to Stop");

        System.in.read();
    }

    public void startReactorServer(HttpHandler httpHandler) throws InterruptedException{
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer httpServer = HttpServer.create(HOST, PORT);
        httpServer.newHandler(adapter).block();
    }

    public void startTomcatServer(HttpHandler httpHandler) throws LifecycleException {
        Tomcat tomcatServer = new Tomcat();
        tomcatServer.setHostname(HOST);
        tomcatServer.setPort(PORT);
        Context context = tomcatServer.addContext("", System.getProperty("java.io.tmpdir"));
        ServletHttpHandlerAdapter handlerAdapter = new ServletHttpHandlerAdapter(httpHandler);
        Tomcat.addServlet(context,"httpHandlerServlet",handlerAdapter);
        context.addServletMapping("/","httpHandlerServlet");
        tomcatServer.start();
    }

    public HttpHandler standalone(){
        PersonRepository personRepository = new PersonRepositoryImpl();
        PersonHandler personHandler = new PersonHandler(personRepository);
        RouterFunction<?> allRoutes =
                nest(path("/person"),
                nest(accept(MediaType.APPLICATION_JSON),
                        route(method(GET), personHandler::allPerson)
                                .and(route(method(POST)
                                        .and(contentType(MediaType.APPLICATION_JSON)), personHandler::savePerson))
                                .and(route(RequestPredicates.GET("/{id}"), personHandler::getPerson)))) ;
        return toHttpHandler(allRoutes);
    }

    public HttpHandler applicationContext() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(RoutingConfiguration.class);
        return WebHttpHandlerBuilder.applicationContext(applicationContext).build();
    }
}
