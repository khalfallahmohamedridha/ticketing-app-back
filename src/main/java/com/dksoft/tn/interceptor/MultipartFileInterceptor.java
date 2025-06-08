package com.dksoft.tn.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;

@Component
public class MultipartFileInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request instanceof MultipartHttpServletRequest multipartRequest) {
            MultipartFile[] images = multipartRequest.getFiles("images").toArray(new MultipartFile[0]);
            List<String> imageUrls = new ArrayList<>();

            // Assuming the event DTO is passed as a JSON string in the "event" part
            String eventJson = multipartRequest.getParameter("event");
            if (eventJson != null) {
                // Note: In a real application, you would parse the JSON into EventDto
                // For simplicity, we assume the EventDto will be processed later and just collect image URLs
                request.setAttribute("images", images);
                request.setAttribute("imageUrls", imageUrls);
            }
        }
        return true;
    }
}