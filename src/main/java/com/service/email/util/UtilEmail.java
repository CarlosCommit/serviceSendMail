package com.service.email.util;

import org.springframework.stereotype.Component;

@Component
public class UtilEmail {


    public String getPlantilla(String mensaje) {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html>");
        htmlBuilder.append("<body>");
        htmlBuilder.append(getStyle());
        htmlBuilder.append("<h1 style=\"font-size: 70px; text-align: center;\">").append("Bienvenido a ********* ]").append(mensaje).append("!</h1>");
        htmlBuilder.append("</body>");
        htmlBuilder.append("</html>");

        return htmlBuilder.toString();
    }


 private String getStyle()
 {
     StringBuilder cssBuilder = new StringBuilder();
     cssBuilder.append("<style>");
     cssBuilder.append(".titulo-bienvenida{font-size:70px; text-align: center;}");
     cssBuilder.append("</style>");
    return cssBuilder.toString();
 }

}
