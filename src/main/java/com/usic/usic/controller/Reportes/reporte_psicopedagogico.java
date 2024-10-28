package com.usic.usic.controller.Reportes;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.usic.usic.model.Entity.Estudiante;
import com.usic.usic.model.Entity.Persona;
import com.usic.usic.model.Entity.ResultadoIA;
import com.usic.usic.model.Entity.TipoTest;
import com.usic.usic.model.Service.ICarreraService;
import com.usic.usic.model.Service.IColegioService;
import com.usic.usic.model.Service.IEstudianteService;
import com.usic.usic.model.Service.IFacultadService;
import com.usic.usic.model.Service.IGeneroService;
import com.usic.usic.model.Service.IResultadoIaService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class reporte_psicopedagogico {

    @Autowired
    private IEstudianteService estudianteService;

    @Autowired
    private IGeneroService generoService;

    @Autowired
    private IColegioService colegioService;

    @Autowired
    private IResultadoIaService resultadoIaService;

    @Autowired
    private IFacultadService facultadService;

    @Autowired
    private ICarreraService carreraService;
    
    @GetMapping("/estudianteEvaluacion/{idEstudiante}")
    public ResponseEntity<Estudiante> obtenerEstudiante(@PathVariable Long idEstudiante, Model model) {
        Estudiante estudiante = estudianteService.findById(idEstudiante);
        model.addAttribute("estudiante", estudiante);
        return ResponseEntity.ok(estudiante);
    }

    @GetMapping("/datosEstudianteSeguimiento/{idEstudiante}")
    public String datosEstudianteSeguimiento(@PathVariable("idEstudiante") Long idEstudiante, Model model, HttpServletRequest request) {
        Persona persona = (Persona) request.getSession().getAttribute("persona");
        String PersonaUsuario = persona.getNombre() + ' ' + persona.getPaterno() + ' ' + persona.getMaterno();
        Estudiante estudiante = estudianteService.findById(idEstudiante);

        List<ResultadoIA> resultados = resultadoIaService.findByEstudiante(estudiante);

        for (ResultadoIA resultado : resultados) {
            if (resultado.getResultado() != null) {
                String[] partesResultado = resultado.getResultado().split("/");
                resultado.setResultado(String.join("</p><p>", partesResultado));
            }
        }

        Set<TipoTest> tiposTest = resultados.stream().map(ResultadoIA::getTipoTest).collect(Collectors.toSet());

        Date fechaNacimientoDate = estudiante.getPersona().getFecha();
        LocalDate fechaNacimiento = fechaNacimientoDate.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
        LocalDate fechaActual = LocalDate.now();
        int edad = Period.between(fechaNacimiento, fechaActual).getYears();

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("genero", generoService.findAll());
        model.addAttribute("edad", edad);
        model.addAttribute("colegio", colegioService.findAll());
        model.addAttribute("usuario", PersonaUsuario);
        model.addAttribute("fechaActual", fechaActual);
        model.addAttribute("resultados", resultados);
        model.addAttribute("tiposTest", tiposTest);
        model.addAttribute("facultades", facultadService.findAll());
        model.addAttribute("carreras", carreraService.findAll());

        return "Administracion/psicopedagoga/form_seguiminto";
    }

    @GetMapping("/psicopedagogico/pdf/{idEstudiante}")
    public ResponseEntity<byte[]> psicopedagogico(@PathVariable Long idEstudiante, HttpServletRequest request) {
        Estudiante estudiante = estudianteService.findById(idEstudiante);
        Persona persona = (Persona) request.getSession().getAttribute("persona");
        String PersonaUsuario = persona.getNombre() + ' ' + persona.getPaterno() + ' ' + persona.getMaterno();
        LocalDate fechaActual = LocalDate.now();
        try{

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);;
            PdfWriter.getInstance(document, out);

            document.open();

            Image logoIzquierdo = Image.getInstance("src/main/resources/static/assets/images/logos/DIRECCION ACADEMICA LOGO_Mesa de trabajo 1.png");
            Image logoDerecho = Image.getInstance("src/main/resources/static/assets/images/logos/Logo Gabinete Psicopedagógico.png");
            logoIzquierdo.scaleToFit(65, 65);
            logoDerecho.scaleToFit(65, 65);

            PdfPTable headerTable = new PdfPTable(3);
            headerTable.setWidthPercentage(100);

            float[] columnWidths = {20f, 60f, 20f};
            headerTable.setWidths(columnWidths);

            PdfPCell cellLogoIzquierdo = new PdfPCell(logoIzquierdo);
            cellLogoIzquierdo.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLogoIzquierdo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerTable.addCell(cellLogoIzquierdo);

            Paragraph titleAndSubtitle = new Paragraph();
            titleAndSubtitle.add(new Paragraph("INFORME PSICOPEDAGÓGCO", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
            titleAndSubtitle.add(new Chunk("\n"));
            titleAndSubtitle.add(new Chunk("\n"));
            titleAndSubtitle.add(new Paragraph("PROGRAMA DE ORIENTACIÓN VOCACIONAL Y PROFESIONAL", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            
            PdfPCell cellTituloSubtitulo = new PdfPCell(titleAndSubtitle);
            cellTituloSubtitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTituloSubtitulo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerTable.addCell(cellTituloSubtitulo);

            PdfPCell cellLogoDerecho = new PdfPCell(logoDerecho);
            cellLogoDerecho.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLogoDerecho.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerTable.addCell(cellLogoDerecho);

            document.add(headerTable);

            document.add(new Paragraph(" "));

            PdfPTable Table1 = new PdfPTable(1);
            Table1.setWidthPercentage(100);

            Paragraph footerText = new Paragraph("El vicerrectorado, Dirección Académica, Centro de Proyectos Especiales y Formación Permanente a través del Gabinete Psicopedagógico hace la entrega de los resultados de Orientación vocacional y Profesional a estudiantes que culminaron el Primer Módulo del Programa de Admisión y Permanencia Estudiantil en la UAP.", 
            new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL));

            PdfPCell footerCell = new PdfPCell(footerText);
            footerCell.setPaddingTop(10f);
            footerCell.setPaddingBottom(10f);
            footerCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            footerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            Table1.addCell(footerCell);
            document.add(Table1);

            PdfPTable table2 = new PdfPTable(3);
            table2.setWidthPercentage(100);

            float[] columnWidths2 = {33f, 33f, 33f};
            table2.setWidths(columnWidths2);

            PdfPCell cellHeader = new PdfPCell(new Phrase("I. DATOS PERSONALES", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD)));
            cellHeader.setColspan(3);
            cellHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
            table2.addCell(cellHeader);

            String[] etiquetas = { "NOMBRES Y APELLIDOS:", "SEXO:", "EDAD:", "GRADO DE INSTRUCCIÓN:", 
                       "UNIDAD EDUCATIVA:", "EVALUADOR:", "FECHA DE ENTREGA:" };
            
            String[] datos = {
                estudiante.getPersona().getNombre() + " " + estudiante.getPersona().getNombre(), 
                estudiante.getPersona().getGenero().getNombreGenero(),
                String.valueOf(estudiante.getPersona().getFecha()), 
                estudiante.getGrado(), 
                estudiante.getColegio().getNombreColegio(),
                PersonaUsuario,
                "23-10-2024"
            };

            for (int i = 0; i < etiquetas.length; i++) {
                PdfPCell celdaEtiqueta = new PdfPCell(new Phrase(etiquetas[i], new Font(Font.FontFamily.HELVETICA, 8)));
                celdaEtiqueta.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celdaEtiqueta.setBorderWidth(1);  // Bordes visibles
                table2.addCell(celdaEtiqueta);
            
                PdfPCell celdaDato = new PdfPCell(new Phrase(datos[i], new Font(Font.FontFamily.HELVETICA, 8)));
                celdaDato.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celdaDato.setBorderWidth(1);  // Bordes visibles
                table2.addCell(celdaDato);
            }

            PdfPCell celdaCombinada = new PdfPCell(new Phrase("")); // Celda vacía o con contenido
            celdaCombinada.setRowspan(etiquetas.length); // Combina las filas de la tercera columna
            celdaCombinada.setBorderWidth(1); // Bordes visibles
            celdaCombinada.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celdaCombinada.setHorizontalAlignment(Element.ALIGN_CENTER);
            table2.addCell(celdaCombinada);

            document.add(table2);

            PdfPTable firmasTable = new PdfPTable(3);
            firmasTable.setWidthPercentage(80);
            firmasTable.setSpacingBefore(5f);

            Chunk firmaYareline = new Chunk(new String(new char[20]).replace('\0', '_'));
            Chunk DirectorAcademico = new Chunk(new String(new char[20]).replace('\0', '_'));
            Chunk firmaEstudiante = new Chunk(new String(new char[20]).replace('\0', '_'));

            PdfPCell yarelineCell = new PdfPCell();
            yarelineCell.setBorder(PdfPCell.NO_BORDER);
            yarelineCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            yarelineCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            yarelineCell.addElement(new Paragraph(firmaYareline));
            yarelineCell.addElement(new Paragraph("Lic. Ygnacio Mendoza Nilaca", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            yarelineCell.addElement(new Paragraph("TEC. GAB. PSICOPEDAGÓGICO", new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL)));

            PdfPCell DirectorAcademicoCell = new PdfPCell();
            DirectorAcademicoCell.setBorder(PdfPCell.NO_BORDER);
            DirectorAcademicoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            DirectorAcademicoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            DirectorAcademicoCell.addElement(new Paragraph(DirectorAcademico));
            DirectorAcademicoCell.addElement(new Paragraph("Dr. Humberto Fernández Calle Ph. D.", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            DirectorAcademicoCell.addElement(new Paragraph("DIRECTOR ACADEMICO a.i. UAP", new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL)));

            PdfPCell estudianteCell = new PdfPCell();
            estudianteCell.setBorder(PdfPCell.NO_BORDER);
            estudianteCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            estudianteCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            estudianteCell.addElement(new Paragraph(firmaEstudiante));
            estudianteCell.addElement(new Paragraph("M.Sc. Oscar Felipe Melgar Saucedo", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            estudianteCell.addElement(new Paragraph("VICERRECTOR UAP", new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL)));

            firmasTable.addCell(yarelineCell);
            firmasTable.addCell(DirectorAcademicoCell);
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
