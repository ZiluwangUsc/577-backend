package com.tripwise.backend.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

    // set Cookie
    public static void addCookie(
            HttpServletResponse response,
            String name,
            String value,
            int maxAge,
            String domain,
            String path,
            boolean secure,
            boolean httpOnly) {

        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setSecure(secure);
        cookie.setHttpOnly(httpOnly); 
        cookie.setAttribute("SameSite", "None"); // CORS

        response.addCookie(cookie);
    }

    // get cookie value
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // delete Cookie
    public static void deleteCookie(
            HttpServletResponse response,
            String name,
            String domain,
            String path) {

        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0); // expire immediately
        cookie.setDomain(domain);
        cookie.setPath(path);
        response.addCookie(cookie);
    }
}