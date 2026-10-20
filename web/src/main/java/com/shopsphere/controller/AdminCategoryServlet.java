package com.shopsphere.controller;

import com.shopsphere.model.Category;
import com.shopsphere.service.ProductService;
import com.shopsphere.service.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/categories")
public class AdminCategoryServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private final ProductService service = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("categories", service.categories(false));
            int edit = intParam(req, "edit", 0);
            if (edit > 0) {
                for (Category c : service.categories(false)) if (c.getCategoryId() == edit) req.setAttribute("form", c);
            }
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
        }
        render(req, resp, "admin/categories.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if ("delete".equals(req.getParameter("action"))) {
                service.deleteCategory(intParam(req, "categoryId", -1));
                flash(req, "info", "Category deleted.");
            } else {
                Category c = new Category();
                c.setCategoryId(intParam(req, "categoryId", 0));
                c.setName(req.getParameter("name"));
                c.setDescription(req.getParameter("description"));
                c.setStatus(req.getParameter("status") != null);
                service.saveCategory(c);
                flash(req, "success", "Category saved.");
            }
        } catch (ServiceException e) {
            flash(req, "danger", e.getMessage());
        }
        redirect(req, resp, "/admin/categories");
    }
}
