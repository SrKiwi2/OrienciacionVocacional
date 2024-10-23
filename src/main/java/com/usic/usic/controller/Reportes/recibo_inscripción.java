package com.usic.usic.controller.Reportes;

import java.util.List;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.itextpdf.text.Chunk;
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
import com.usic.usic.model.Entity.Carrera;
import com.usic.usic.model.Entity.Colegio;
import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.Facultad;
import com.usic.usic.model.Service.ICarreraService;
import com.usic.usic.model.Service.IColegioService;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IFacultadService;

@Controller
public class recibo_inscripción {

    @Autowired
    private IEstudianteService estudianteService;

    @Autowired
    private IFacultadService facultadService;

    @Autowired
    private ICarreraService carreraService;


    @GetMapping("/recibo/pdf/{idEstudiante}")
    public ResponseEntity<byte[]> reporteChasidePdf(@PathVariable Long idEstudiante) {
        try{

            Estudiante estudiante = estudianteService.findById(idEstudiante);
            Colegio colegio = estudianteService.findColegioByIdEstudiante(idEstudiante);
            Facultad facultad = facultadService.findById(1L);
            Carrera carrera = carreraService.findById(1L);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document(PageSize.LETTER);;
            PdfWriter.getInstance(document, out);

            document.open();

            Image logoIzquierdo = Image.getInstance("src/main/resources/static/assets/images/logos/UAPLOGO.png");
            Image logoDerecho = Image.getInstance("src/main/resources/static/assets/images/logos/Logo cpeyfp.png");
            logoIzquierdo.scaleToFit(65, 65);
            logoDerecho.scaleToFit(65, 65);

            PdfPTable headerTable = new PdfPTable(3);
            headerTable.setWidthPercentage(90);

            float[] columnWidths = {20f, 60f, 20f};
            headerTable.setWidths(columnWidths);

            PdfPCell cellLogoIzquierdo = new PdfPCell(logoIzquierdo);
            cellLogoIzquierdo.setBorder(PdfPCell.NO_BORDER);
            cellLogoIzquierdo.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellLogoIzquierdo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerTable.addCell(cellLogoIzquierdo);

            Paragraph titleAndSubtitle = new Paragraph();
            titleAndSubtitle.add(new Paragraph("UNIVERSIDAD AMAZONICA DE PANDO", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
            titleAndSubtitle.add(new Chunk("\n"));
            titleAndSubtitle.add(new Chunk("\n"));
            titleAndSubtitle.add(new Paragraph("VICE-RECTORADO", new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC)));
            titleAndSubtitle.add(new Chunk("\n"));
            titleAndSubtitle.add(new Chunk("\n"));
            titleAndSubtitle.add(new Paragraph("La preservación de la Amazonía es parte de la vida, del proyecto y desarrollo de la bella tierra pandina.", new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC)));
            
            PdfPCell cellTituloSubtitulo = new PdfPCell(titleAndSubtitle);
            cellTituloSubtitulo.setBorder(PdfPCell.NO_BORDER);
            cellTituloSubtitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTituloSubtitulo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerTable.addCell(cellTituloSubtitulo);

            PdfPCell cellLogoDerecho = new PdfPCell(logoDerecho);
            cellLogoDerecho.setBorder(PdfPCell.NO_BORDER);
            cellLogoDerecho.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellLogoDerecho.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerTable.addCell(cellLogoDerecho);

            // Agregar línea horizontal
            PdfPCell lineCell = new PdfPCell();
            lineCell.setBorder(PdfPCell.NO_BORDER);
            lineCell.setColspan(3);
            lineCell.setPaddingTop(2f);

            Chunk line = new Chunk(new String(new char[72]).replace('\0', '_'));
            line.setUnderline(1f, -1f);
            lineCell.addElement(line);
            
            headerTable.addCell(lineCell);

            document.add(headerTable);
            
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph titulo = new Paragraph("RECIBO 225/2024", boldFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
            Font contentFont = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(85);

            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            float[] columnWidths2 = {22f, 36f, 10f, 32f};
            table.setWidths(columnWidths2);

           PdfPCell cell;

            cell = new PdfPCell(new Phrase("Id Estudiante", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPadding(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(estudiante.getIdEstudiante()), contentFont));
            cell.setPadding(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Area", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPadding(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(facultad.getFacultad(), contentFont));
            cell.setPadding(5);
            table.addCell(cell);

            // Segunda fila
            cell = new PdfPCell(new Phrase("Nombres", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPadding(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(estudiante.getPersona().getNombre() + " " + estudiante.getPersona().getPaterno() + " " + estudiante.getPersona().getMaterno(), contentFont));
            cell.setPadding(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Carrera", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPadding(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(carrera.getCarrera(), contentFont));
            cell.setPadding(5);
            table.addCell(cell);

            // Tercera fila
            cell = new PdfPCell(new Phrase("Gestión", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPadding(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(java.time.LocalDate.now().getYear()), contentFont));
            cell.setPadding(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Periodo", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPadding(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("2", contentFont));
            cell.setPadding(5);
            table.addCell(cell);

            // Cuarta fila
            cell = new PdfPCell(new Phrase("Nro. Celular", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPadding(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("74754979", contentFont));
            cell.setPadding(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Fecha", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPadding(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(java.time.LocalDate.now().toString(), contentFont));
            cell.setPadding(5);
            table.addCell(cell);

            // Quinta fila
            cell = new PdfPCell(new Phrase("Cédula de Identidad", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPadding(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(estudiante.getPersona().getCi(), contentFont));
            cell.setPadding(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Colegio", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPadding(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(colegio.getNombreColegio(), contentFont));
            cell.setPadding(5);
            table.addCell(cell);

            document.add(table);

            PdfPTable secondTable = new PdfPTable(6);
            secondTable.setWidthPercentage(85);
            secondTable.setSpacingBefore(10f);
            secondTable.setSpacingAfter(10f);

            float[] columnWidthsSecondTable = {5f, 4f, 55f, 8f, 13f, 15f};
            secondTable.setWidths(columnWidthsSecondTable);

            Font boldHeaderFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);

            PdfPCell nroHeaderCell = new PdfPCell(new Phrase("Nro", boldHeaderFont));
            nroHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            nroHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            secondTable.addCell(nroHeaderCell);

            PdfPCell questionMarkHeaderCell = new PdfPCell(new Phrase("?", boldHeaderFont));
            questionMarkHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            questionMarkHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            secondTable.addCell(questionMarkHeaderCell);

            PdfPCell cursoHeaderCell = new PdfPCell(new Phrase("CURSO", boldHeaderFont));
            cursoHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cursoHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            secondTable.addCell(cursoHeaderCell);

            PdfPCell costoHeaderCell = new PdfPCell(new Phrase("COSTO", boldHeaderFont));
            costoHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            costoHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            secondTable.addCell(costoHeaderCell);

            PdfPCell descuentoHeaderCell = new PdfPCell(new Phrase("DESCUENTO", boldHeaderFont));
            descuentoHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            descuentoHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            secondTable.addCell(descuentoHeaderCell);

            PdfPCell observacionHeaderCell = new PdfPCell(new Phrase("OBSERVACIÓN", boldHeaderFont));
            observacionHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            observacionHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            secondTable.addCell(observacionHeaderCell);

            for (int i = 0; i < 1; i++) {
                PdfPCell nroCell = new PdfPCell(new Phrase(String.valueOf(i + 1), contentFont));
                secondTable.addCell(nroCell);
            
                PdfPCell questionMarkCell = new PdfPCell(new Phrase("- ", contentFont));
                secondTable.addCell(questionMarkCell);
            
                PdfPCell cursoCell = new PdfPCell(new Phrase("Programa de Admisión Estudiantil con Orientación Vocacional (nacionales)", contentFont));
                secondTable.addCell(cursoCell);
            
                PdfPCell costoCell = new PdfPCell(new Phrase("300 Bs.", contentFont));
                secondTable.addCell(costoCell);
            
                PdfPCell descuentoCell = new PdfPCell(new Phrase("0 Bs.", contentFont));
                secondTable.addCell(descuentoCell);
            
                PdfPCell observacionCell = new PdfPCell(new Phrase("", contentFont));
                secondTable.addCell(observacionCell);
            }

            document.add(secondTable);

            PdfPTable firmasTable = new PdfPTable(2);
            firmasTable.setWidthPercentage(70);
            firmasTable.setSpacingBefore(5f);

            Chunk firmaYareline = new Chunk(new String(new char[20]).replace('\0', '_'));
            Chunk firmaEstudiante = new Chunk(new String(new char[20]).replace('\0', '_'));

            PdfPCell yarelineCell = new PdfPCell();
            yarelineCell.setBorder(PdfPCell.NO_BORDER);
            yarelineCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            yarelineCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            yarelineCell.addElement(new Paragraph(firmaYareline));
            yarelineCell.addElement(new Paragraph("YARELINE JAIDI PEDRAZA OLIVER", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            yarelineCell.addElement(new Paragraph("Cajero/a", new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL)));

            PdfPCell estudianteCell = new PdfPCell();
            estudianteCell.setBorder(PdfPCell.NO_BORDER);
            estudianteCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            estudianteCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            estudianteCell.addElement(new Paragraph(firmaEstudiante));
            estudianteCell.addElement(new Paragraph(estudiante.getPersona().getNombre() + " " + estudiante.getPersona().getPaterno() + " " + estudiante.getPersona().getMaterno(), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            estudianteCell.addElement(new Paragraph("Estudiante", new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL)));

            firmasTable.addCell(yarelineCell);
            firmasTable.addCell(estudianteCell);

            document.add(firmasTable);

            BaseFont scriptFont = BaseFont.createFont("src/main/resources/static/assets/fonts/White Dahlia.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font handwrittenFont = new Font(scriptFont, 20, Font.ITALIC);

            Paragraph finalText = new Paragraph("Escribiendo una nueva Historia con vos", handwrittenFont);
            finalText.setAlignment(Element.ALIGN_CENTER);
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
