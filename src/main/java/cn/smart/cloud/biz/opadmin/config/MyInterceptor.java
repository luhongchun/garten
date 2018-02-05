package cn.smart.cloud.biz.opadmin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class MyInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        logger.info("------preHandle------");
        HttpSession session = request.getSession(true);
        if(session.getAttribute("user") == null){
            logger.warn("No find user in session, redirect to login page !");
            request.setAttribute("lastRequestUrl", request.getRequestURL());
            response.sendRedirect(request.getContextPath()+"/admin/login");
            return false;
        }else{
            session.setAttribute("user", session.getAttribute("user"));
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o,
                           ModelAndView modelAndView) throws Exception {
        logger.info("------postHandle------");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o,
                                Exception e) throws Exception {
        logger.info("------afterHandle------");

    }
}
