package com.usic.usic.controller.Reportes;

import java.util.List;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.ResultadoIA;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IResultadoIaService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class resultados_chaside {
    
    @Autowired
    private IEstudianteService estudianteService;

    @Autowired
    private IResultadoIaService resultadoIaService;

    @GetMapping("/reporte/pdf/{idEstudiante}")
    public ResponseEntity<byte[]> reporteChasidePdf(@PathVariable Long idEstudiante) {
        try{

            Estudiante estudiante = estudianteService.findById(idEstudiante);
            List<ResultadoIA> resultados = resultadoIaService.findByEstudiante(estudiante);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document(PageSize.LETTER);;
            PdfWriter.getInstance(document, out);

            document.open();

            Image logoIzquierdo = Image.getInstance("src/main/resources/static/assets/images/logos/uap.png");
            Image logoDerecho = Image.getInstance("src/main/resources/static/assets/images/logos/logoUsic.png");
            logoIzquierdo.scaleToFit(80, 80);
            logoDerecho.scaleToFit(80, 80);

            PdfPTable headerTable = new PdfPTable(3);
            headerTable.setWidthPercentage(100);

            float[] columnWidths = {20f, 60f, 20f};
            headerTable.setWidths(columnWidths);

            PdfPCell cellLogoIzquierdo = new PdfPCell(logoIzquierdo);
            cellLogoIzquierdo.setBorder(PdfPCell.NO_BORDER);
            cellLogoIzquierdo.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerTable.addCell(cellLogoIzquierdo);

            Paragraph titleAndSubtitle = new Paragraph();
            titleAndSubtitle.setAlignment(Element.ALIGN_CENTER);
            titleAndSubtitle.add(new Paragraph(" UNIVERSIDAD AMAZONICA DE PANDO", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
            titleAndSubtitle.add(new Paragraph("           Centro de proyectos especiales y formación permanente", new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC)));

            PdfPCell cellTituloSubtitulo = new PdfPCell();
            cellTituloSubtitulo.setBorder(PdfPCell.NO_BORDER);
            cellTituloSubtitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTituloSubtitulo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellTituloSubtitulo.addElement(titleAndSubtitle);
            headerTable.addCell(cellTituloSubtitulo);

            PdfPCell cellLogoDerecho = new PdfPCell(logoDerecho);
            cellLogoDerecho.setBorder(PdfPCell.NO_BORDER);
            cellLogoDerecho.setHorizontalAlignment(Element.ALIGN_RIGHT);
            headerTable.addCell(cellLogoDerecho);

            document.add(headerTable);
            
            document.add(new Paragraph(" "));
            
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph titulo = new Paragraph("RESULTADO DEL TEST VOCACIONAL - CHASIDE", boldFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph(" "));

            Paragraph mensaje = new Paragraph("Hola " + estudiante.getPersona().getNombre() + " " + 
                    estudiante.getPersona().getPaterno() + " " + 
                    estudiante.getPersona().getMaterno() + 
                    ", estos son los resultados de tu test de orientación vocacional.", 
                    new Font(Font.FontFamily.HELVETICA, 12));
            mensaje.setAlignment(Element.ALIGN_CENTER);
            document.add(mensaje);

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(85);
            table.setSpacingBefore(10f);

            float[] columnWidthss = {0.5f, 2f};
            table.setWidths(columnWidthss);

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font contentFont = new Font(Font.FontFamily.HELVETICA, 11);

            BaseColor interesesColor = new BaseColor(255, 0, 0);
            BaseColor aptitudesColor = new BaseColor(0, 0, 255);
            BaseColor areasColor = new BaseColor(0, 128, 0);

            for (ResultadoIA resultado : resultados) {
                String[] partes = resultado.getResultado().split("/");
    
                if (partes.length >= 3) {
                    PdfPCell interesesHeaderCell = new PdfPCell(new Phrase("Intereses: ", new Font(headerFont.getFamily(), headerFont.getSize(), headerFont.getStyle(), interesesColor)));
                    interesesHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    interesesHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(interesesHeaderCell);

                    PdfPCell interesesContentCell = new PdfPCell(new Phrase(partes[0], contentFont));
                    interesesContentCell.setPadding(5);
                    interesesContentCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    interesesContentCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    table.addCell(interesesContentCell);
    
                    PdfPCell aptitudesHeaderCell = new PdfPCell(new Phrase("Aptitudes: ", new Font(headerFont.getFamily(), headerFont.getSize(), headerFont.getStyle(), aptitudesColor)));
                    aptitudesHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    aptitudesHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(aptitudesHeaderCell);

                    PdfPCell aptitudesContentCell = new PdfPCell(new Phrase(partes[1], contentFont));
                    aptitudesContentCell.setPadding(5);
                    aptitudesContentCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    aptitudesContentCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    table.addCell(aptitudesContentCell);
    
                    PdfPCell areasHeaderCell = new PdfPCell(new Phrase("Áreas: ", new Font(headerFont.getFamily(), headerFont.getSize(), headerFont.getStyle(), areasColor)));
                    areasHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    areasHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(areasHeaderCell);

                    String areasTexto = partes[2].replaceAll("\\*", "").trim();

                    PdfPCell areasContentCell = new PdfPCell(new Phrase(areasTexto, contentFont));
                    areasContentCell.setPadding(5);
                    areasContentCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    areasContentCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    table.addCell(areasContentCell);
                } else {
                    PdfPCell incompleteCell = new PdfPCell(new Paragraph("Resultado incompleto", headerFont));
                    incompleteCell.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(incompleteCell);
                    table.addCell(incompleteCell);
                    table.addCell(incompleteCell);
                }
            }
            document.add(table);

            BaseFont scriptFont = BaseFont.createFont("src/main/resources/static/assets/fonts/White Dahlia.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font handwrittenFont = new Font(scriptFont, 28, Font.ITALIC);

            // Crear el texto en cursiva
            Paragraph finalText = new Paragraph("Escribiendo una nueva Historia con vos", handwrittenFont);
            finalText.setAlignment(Element.ALIGN_CENTER); // Centrar el texto

            // Añadir un espacio antes del texto
            document.add(new Paragraph(" "));
            document.add(finalText);

            document.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.inline().filename("Resultado_CHASIDE.pdf").build());
            headers.set("X-Frame-Options", "");

            return ResponseEntity.ok().headers(headers).body(out.toByteArray());
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
