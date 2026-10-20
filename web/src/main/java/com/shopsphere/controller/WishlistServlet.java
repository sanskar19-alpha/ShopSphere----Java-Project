package com.shopsphere.controller;

import com.shopsphere.model.User;
import com.shopsphere.service.CartService;
import com.shopsphere.service.ServiceException;
import com.shopsphere.service.WishlistService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/wishlist")
public class WishlistServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private final WishlistService wishlist = new WishlistService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("items", wishlist.list(currentUser(req).getUserId()));
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
        }
        render(req, resp, "wishlist.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = currentUser(req);
        int productId = intParam(req, "productId", -1);
        String action = req.getParameter("action");
        String back = safeNext(req.getParameter("back"), "/wishlist");
        try {
            if ("add".equals(action)) {
                wishlist.add(u.getUserId(), productId);
                flash(req, "success", "Saved to your wishlist.");
            } else if ("remove".equals(action)) {
                wishlist.remove(u.getUserId(), productId);
                flash(req, "info", "Removed from wishlist.");
            } else if ("toCart".equals(action)) {
                new CartService().add(u.getUserId(), productId, 1);
                wishlist.remove(u.getUserId(), productId);
                refreshCartCount(req);
                flash(req, "success", "Moved to your cart.");
            }
        } catch (ServiceException e) {
            flash(req, "danger", e.getMessage());
        }
        redirect(req, resp, back);
    }
}
