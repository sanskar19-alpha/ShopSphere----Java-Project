package com.shopsphere.controller;

import com.shopsphere.model.Product;
import com.shopsphere.service.ProductService;
import com.shopsphere.service.ServiceException;
import com.shopsphere.util.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/** Product CRUD for administrators. GET ?edit=ID shows the edit form; "new" shows an empty one. */
@WebServlet("/admin/products")
public class AdminProductServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private final ProductService service = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("products", service.adminList());
            req.setAttribute("categories", service.categories(false));
            String edit = req.getParameter("edit");
            if ("new".equals(edit)) {
                Product p = new Product();
                p.setStatus(true);
                req.setAttribute("form", p);
            } else if (edit != null) {
                req.setAttribute("form", service.adminGet(Validator.toInt(edit, -1)));
            }
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
        }
        render(req, resp, "admin/products.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("deactivate".equals(action)) {
                service.deactivate(intParam(req, "productId", -1));
                flash(req, "info", "Product removed from the store (history kept).");
            } else {
                Product p = new Product();
                p.setProductId(intParam(req, "productId", 0));
                p.setCategoryId(intParam(req, "categoryId", 0));
                p.setName(req.getParameter("name"));
                p.setBrand(req.getParameter("brand"));
                p.setDescription(req.getParameter("description"));
                Double price = Validator.toDouble(req.getParameter("price"));
                Double discount = Validator.toDouble(req.getParameter("discount"));
                p.setPrice(price == null ? 0 : price);
                p.setDiscount(discount == null ? 0 : discount);
                p.setStock(intParam(req, "stock", 0));
                p.setImageUrl(req.getParameter("imageUrl"));
                p.setStatus(req.getParameter("status") != null);
                service.save(p);
                flash(req, "success", "Product saved.");
            }
        } catch (ServiceException e) {
            flash(req, "danger", e.getMessage());
            redirect(req, resp, "/admin/products" + (req.getParameter("productId") != null && !"0".equals(req.getParameter("productId"))
                    ? "?edit=" + intParam(req, "productId", 0) : "?edit=new"));
            return;
        }
        redirect(req, resp, "/admin/products");
    }
}
