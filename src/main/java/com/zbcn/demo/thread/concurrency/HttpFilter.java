package com.zbcn.demo.thread.concurrency;

import com.zbcn.demo.thread.concurrency.threadLocal.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Description:
 * @Auther: zbcn
 * @Date: 2/27/19 17:59
 */
@Slf4j
public class HttpFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        //Object user = httpServletRequest.getSession().getAttribute("user");
        log.info("do filter {};  {}",Thread.currentThread().getName(),httpServletRequest.getServletMapping());
        RequestHolder.add(Thread.currentThread().getId());
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
