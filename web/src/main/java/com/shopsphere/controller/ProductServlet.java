package com.shopsphere.controller;

import com.shopsphere.model.Product;
import com.shopsphere.model.User;
import com.shopsphere.service.ProductService;
import com.shopsphere.service.ReviewService;
import com.shopsphere.service.ServiceException;
import com.shopsphere.util.CookieUtil;
import com.shopsphere.util.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** /products = listing with search, filter and sort;  /product?id=N = product details. */
@WebServlet({"/products", "/product"})
public class ProductServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private final ProductService products = new ProductService();
    private final ReviewService reviews = new ReviewService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if ("/product".equals(req.getServletPath())) details(req, resp); else list(req, resp);
        } catch (ServiceException e) {
            flash(req, "danger", e.getMessage());
            redirect(req, resp, "/products");
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, ServletException, IOException {
        String q = req.getParameter("q");
        Integer category = Validator.toInteger(req.getParameter("category"));
        Double min = Validator.toDouble(req.getParameter("min"));
        Double max = Validator.toDouble(req.getParameter("max"));
        String sort = req.getParameter("sort");

        req.setAttribute("products", products.search(q, category, min, max, sort));
        req.setAttribute("categories", products.categories(true));
        req.setAttribute("q", q == null ? "" : q.trim());
        req.setAttribute("selectedCategory", category);
        req.setAttribute("min", req.getParameter("min"));
        req.setAttribute("max", req.getParameter("max"));
        req.setAttribute("sort", sort == null ? "newest" : sort);
        render(req, resp, "products.jsp");
    }

    private void details(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, ServletException, IOException {
        int id = intParam(req, "id", -1);
        Product p = products.getActive(id);

        List<Product> related = products.search(null, p.getCategoryId(), null, null, "rating").stream()
                .filter(r -> r.getProductId() != id).limit(4).collect(Collectors.toList());
        req.setAttribute("product", p);
        req.setAttribute("reviews", products.reviews(id));
        req.setAttribute("related", related);

        User u = currentUser(req);
        req.setAttribute("canReview", u != null && reviews.canReview(u.getUserId(), id));

        rememberViewed(req, resp, id);
        render(req, resp, "product-details.jsp");
    }

    /** Cookie "recentlyViewed" = "12-7-3" (most recent first, max 5 ids). Non-sensitive data only. */
    private void rememberViewed(HttpServletRequest req, HttpServletResponse resp, int id) {
        List<String> ids = new ArrayList<>();
        ids.add(String.valueOf(id));
        String old = CookieUtil.get(req, "recentlyViewed");
        if (old != null) {
            for (String s : old.split("-")) {
                if (s.matches("\\d{1,9}") && !s.equals(String.valueOf(id)) && ids.size() < 5) ids.add(s);
            }
        }
        CookieUtil.set(req, resp, "recentlyViewed", String.join("-", ids), 60 * 60 * 24 * 7);
    }
}
