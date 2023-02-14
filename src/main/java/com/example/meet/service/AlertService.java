package com.example.meet.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Service
public interface AlertService {
    /**
     * 알림창
     */
    void notificationWindow(String message, HttpServletResponse response);
}
