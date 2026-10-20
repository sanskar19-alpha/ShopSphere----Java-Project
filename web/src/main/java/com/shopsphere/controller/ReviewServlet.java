package com.shopsphere.controller;

import com.shopsphere.service.ReviewService;
import com.shopsphere.service.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/review")
public class ReviewServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = intParam(req, "productId", -1);
        try {
            new ReviewService().submit(currentUser(req).getUserId(), productId,
                    intParam(req, "rating", 0), req.getParameter("reviewText"));
            flash(req, "success", "Thanks! Your review has been saved.");
        } catch (ServiceException e) {
            flash(req, "danger", e.getMessage());
        }
        redirect(req, resp, "/product?id=" + productId);
    }
}
