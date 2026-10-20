package com.shopsphere.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Syllabus demo: Internationalization (i18n) with Locale, ResourceBundle, NumberFormat and DateFormat.
 * The JSP pages do the same thing through JSTL (fmt:setLocale, fmt:setBundle, fmt:message, fmt:formatNumber).
 *
 * Run:  java -cp web/src/main/resources:web/target/classes com.shopsphere.util.I18nDemo
 */
public class I18nDemo {
    public static void main(String[] args) {
        Locale[] locales = {Locale.of("en", "IN"), Locale.of("hi", "IN"), Locale.US, Locale.GERMANY};
        double amount = 1234567.89;
        Date now = new Date();
        for (Locale locale : locales) {
            // ResourceBundle picks messages_<language>.properties; falls back to messages.properties
            ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
            System.out.println("=== " + locale.getDisplayName(Locale.ENGLISH) + " (" + locale + ") ===");
            System.out.println("nav.cart       : " + bundle.getString("nav.cart"));
            System.out.println("checkout.place : " + bundle.getString("checkout.place"));
            System.out.println("number         : " + NumberFormat.getInstance(locale).format(amount));
            System.out.println("currency       : " + NumberFormat.getCurrencyInstance(locale).format(amount));
            System.out.println("percent        : " + NumberFormat.getPercentInstance(locale).format(0.256));
            System.out.println("date           : " + DateFormat.getDateInstance(DateFormat.LONG, locale).format(now));
            System.out.println();
        }
    }
}
