package com.tians.filter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
拦截器是什么？
   拦截器是指通过统一拦截从浏览器发往服务器的请求来完成功能的增强
   使用场景：解决请求的共性问题（如：乱码问题、权限验证问题等）
拦截器的基本工作原理
   springMVC可以通过配置过滤器来解决乱码问题
   拦截器的工作原理和过滤器非常相似
拦截器的实现
   编写拦截器类实现HandlerInterceptor接口
   将拦截器注册进SpringMVC框架中
   配置拦截器的拦截规则
拦截器的其他实现规则
   拦截器的类还可以通过实现WebRequestInterceptor接口来编写
   向SpringMVC框架注册的写法不变
   弊端：preHandle方法没有返回值，不能终止请求
拦截器和过滤器的区别
   拦截器Filter依赖于Servlet容器，基于回调函数，过滤范围大
   拦截器Interceptor依赖于框架容器，基于反射机制，只过滤请求
总结
    拦截器可以处理Web应用中请求的一些通用性问题
    共性问题在拦截器中处理，可以减少重复代码，便于维护
* */
public class MyHandlerInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return false;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
