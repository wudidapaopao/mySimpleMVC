# mySimpleMVC

一个未完善的MVC框架

一个简易的MVC框架，前端通过一个单独的DispatchServlet统一分发请求，将请求分发到对应的请求处理器。
实现了基于注解和xml配置的ioc容器。
实现了基于注解的JDK和CGLib的链式动态代理的AOP，并基于此实现了事务处理。
支持json和jsp两种视图渲染。

next... 更完善的参数绑定，servlet api的解耦，more...