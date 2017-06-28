package com.elecom.crawler.common.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

/**
 * 
 * @author chunhui.li
 */
public class SecurityFilter implements Filter {

    protected Logger logger = Logger.getLogger(this.getClass());
    private static Set<String> GreenUrlSet = new HashSet<String>();

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpSession session = request.getSession();
//
//        // uri取得
//        String uri = request.getRequestURI();
//        
//        // セッションからユーザはなかった場合、ログイン画面遷移
//        if (session.getAttribute(Application.SESSION_KEY_LOGINUSER) == null) {
//            
//            Cookie[] cookies = request.getCookies();
//            if (GreenUrlSet.contains(uri)) {
//                chain.doFilter(req, res);
//                return;
//            }
//            // ある場合、権限の判断を行う
//        } else {
//            
//        }
//        
//
//        if (request != null && session != null) {
//
//            // 画面表示権限チェック
//        }
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        GreenUrlSet.add("/");
        GreenUrlSet.add("/index");
        GreenUrlSet.add("/login");
    }

}
