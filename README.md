# Jesse

Jesse stands for **J**ava **E**nterprise **S**erver **S**ide **E**vents. It is a framework built using JEE APIs to provide server-side event capability in a JEE web application.

# Motivation

"But JAX-RS already has Sse", I hear you say, and well, you are right, but it has a few caveats that I couldnt get around and didnt fit in with an application I was building.

 You see, the thing is that JAX-RS Sse depends a lot on context injection(and by depends a lot I mean doesnt work without it), which is a headache I prefer to avoid if i can. It was also really difficult to use with external threads(which i needed to implement some specific, periodically ocurring database operations that users needed to be notified about). So I decided to build my own Sse Framework, based on JavaEE APIs. 

# Getting Started

## Adding It to your project

Im not on maven central yet, for now download and build yourself and add the jar and JavaEE api version 7 to your project

## Setup
You need to add some entries into your web.xml 

```xml

    <servlet>
        <servlet-name>{Servlet Name}</servlet-name>
        <servlet-class>me.busr.jesse.JesseServlet</servlet-class >
        <load-on-startup>1</load-on-startup>
        <async-supported>true</async-supported>
    </servlet>
    <servlet-mapping>
        <servlet-name>{Servlet Name}</servlet-name>
        <url-pattern>{URI mapping}</url-pattern>
    </servlet-mapping>

```

If you would like to use your own implementation of the SseSessionManager, then you need to add the following init params to your servlet defenition

```xml
    <init-param >
        <param-name>me.busr.jesse.session.manager</param-name >
        <param-value>{Class name of session manager}</param-value >
    </init-param >

```

For example,this is a complete configuration with a custom session manager

```xml
    <servlet >
        <servlet-name> EventStream Endpoint </servlet-name>
        <servlet-class>me.busr.jesse.JesseServlet</servlet-class >
        <init-param>
            <param-name >me.busr.jesse.session.manager</param-name >
            <param-value>me.busr.core.sse.BusrSessionManager</param-value >
        </init-param>
        <load-on-startup>1</load-on-startup>
        <async-supported>true</async-supported>
    </servlet>
    <servlet-mapping>
        <servlet-name> EventStream Endpoint </servlet-name>
        <url-pattern>/event/*</url-pattern>
    </servlet-mapping>
```

And Thats it! you are now ready to go

# Useage
## Sending Sse Events

```java

 DefaultSessionEvent.broadcastAll(new SseEventBuilder()
                            .event("test")
                            .id(33)
                            .retry(500)
                            .data("WOOT AN EVENT")
                            .build())

```

Lets break this code down 

``` DefaultSessionManager ``` is the default implementation of the session manager. It stores all active sessions in a list, and you can broadcast events to groups of sessions, individual sessions, or all sessions. 


``` SseEventBuilder ``` is a utility class to build a new ``` SseEvent ```. ```event("test")``` sets the type of the event to "test", ```, ``` id(33) ``` sets the event id to "33",  retry(500) ``` sets the retry interval to 500ms, ``` data("WOOT AN EVENT") ``` sends "WOOT AN EVENT" as the event data, and ``` build() ``` creates the ``` SseEvent ``` based on the previous functions. The resulting event would be

```
id: 33
event: test
retry: 500
data: WOOT AN EVENT

```

## Custom Session Managers

a custom session manager can be created by extending the ``` SseSessionManager ``` class.
By default, the sessions are not stored anywhere, so it is up to you to store them in whatever way you see fit.

The following methods are mandatory to implement

``` onOpen(SseSession sseSession) throws WebApplicationExceptoion ``` : This method is called when a new session is opened. ``` WebApplicationException ``` will terminate this session with the corrseponding Http Response code.

``` onClose(SseSession sseSession) throws WebApplicationExceptoion ``` : This method is called when a session is being closed. ``` WebApplicationException ``` will terminate this session with the corrseponding Http Response code.

``` onError(SseSession sseSession)``` : This method is called if there is a transport error(The session has been closed for example, or some internal server error). I reccomend you use this to remove the session from your sessions list/map/whatever you are storing the sessions in.

# Building

Nothing fancy, just clone and build using the pom.xml file. I havent gotten around to putting in test classes yet though.