package com.example.meet.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Service
public class AlertServiceImpl implements AlertService{

    /**
     * 알림창
     */
    @Override
    public void notificationWindow(String message, HttpServletResponse response) {
            try {
                response.setContentType("text/html; charset=utf-8");
                PrintWriter w = response.getWriter();
                w.write("<script>alert('" + message + "');location.href='/';</script>");
                w.flush();
                w.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
    }
}
