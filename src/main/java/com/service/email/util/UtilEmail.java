package com.service.email.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public String getPlantillaFromHtml(String mensaje) {
        try {
            String filePath = "static/PlantillaNegocio.html";
            ClassPathResource resource = new ClassPathResource(filePath);
            byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
            String contenidoHtml = new String(bytes, StandardCharsets.UTF_8);

            // Reemplazar el marcador de posición con el valor de mensaje
            contenidoHtml = contenidoHtml.replace("${mensaje}", mensaje);

            return contenidoHtml;
        } catch (Exception e) {
            // Manejar excepciones si ocurre algún problema al leer el archivo
            e.printStackTrace();
            return ""; // O una cadena de error
        }
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
