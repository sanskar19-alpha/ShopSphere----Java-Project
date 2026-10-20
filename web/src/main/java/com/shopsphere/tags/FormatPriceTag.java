package com.shopsphere.tags;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Classic Java tag handler (JSP tag extension) built on SimpleTagSupport.
 * Usage:  &lt;sp:price amount="${product.price}"/&gt;  prints a locale aware rupee amount.
 */
public class FormatPriceTag extends SimpleTagSupport {
    private double amount;

    public void setAmount(double amount) { this.amount = amount; }

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pc = (PageContext) getJspContext();
        Object lang = pc.findAttribute("lang");                       // set by LocaleFilter in the session
        Locale locale = "hi".equals(lang) ? Locale.of("hi", "IN") : Locale.of("en", "IN");
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(amount == Math.floor(amount) ? 0 : 2);
        getJspContext().getOut().write(nf.format(amount).replace('\u00a0', ' '));
    }
}
