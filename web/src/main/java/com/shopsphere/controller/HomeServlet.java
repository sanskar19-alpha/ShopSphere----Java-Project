package com.shopsphere.controller;

import com.shopsphere.model.Product;
import com.shopsphere.service.ProductService;
import com.shopsphere.service.ServiceException;
import com.shopsphere.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Home page: featured products, categories and the "recently viewed" list from a cookie. */
@WebServlet("/home")
public class HomeServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private final ProductService products = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("featured", products.featured(8));
            req.setAttribute("categories", products.categories(true));
            req.setAttribute("recent", recentlyViewed(req));
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
        }
        render(req, resp, "index.jsp");
    }

    private List<Product> recentlyViewed(HttpServletRequest req) {
        List<Product> list = new ArrayList<>();
        String cookie = CookieUtil.get(req, "recentlyViewed");
        if (cookie == null || cookie.isBlank()) return list;
        for (String part : cookie.split("-")) {
            try { list.add(products.getActive(Integer.parseInt(part))); } catch (Exception ignored) { /* skip stale ids */ }
            if (list.size() == 4) break;
        }
        return list;
    }
}
