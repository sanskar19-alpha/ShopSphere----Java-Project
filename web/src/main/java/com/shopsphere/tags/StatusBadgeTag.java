package com.shopsphere.tags;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;

/** Tag handler: &lt;sp:statusBadge status="SHIPPED"/&gt; prints a coloured Bootstrap badge. */
public class StatusBadgeTag extends SimpleTagSupport {
    private String status;

    public void setStatus(String status) { this.status = status; }

    @Override
    public void doTag() throws JspException, IOException {
        String s = status == null ? "" : status.toUpperCase();
        String colour = switch (s) {
            case "PLACED", "PENDING" -> "secondary";
            case "PACKED" -> "info";
            case "SHIPPED" -> "primary";
            case "DELIVERED", "PAID" -> "success";
            case "CANCELLED", "REFUNDED" -> "danger";
            default -> "dark";
        };
        String safe = s.replaceAll("[^A-Z_]", "");
        getJspContext().getOut().write("<span class=\"badge text-bg-" + colour + "\">" + safe + "</span>");
    }
}
