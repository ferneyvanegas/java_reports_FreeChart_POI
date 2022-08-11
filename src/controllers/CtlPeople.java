/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;


import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

import classes.ReportByCountry;
import classes.ReportByNameContent;
import models.ModelPeople;
import views.JFrameMain;

/**
 *
 * @author fercho
 */
public class CtlPeople implements ActionListener, MouseListener{
    private ModelPeople mdPeople;
    private JFrameMain main;

    public CtlPeople(JFrameMain jFrameMain) {
        this.mdPeople = new ModelPeople();
        this.main = jFrameMain;
        this.main.jTxtContentName.addActionListener(this);
        this.main.jTabbedPane1.addMouseListener(this);
        this.main.jBtnExport.addActionListener(this);
        
        // Esconder los controles para generar y exportar informes por contenido de nombre
        this.main.jTxtContentName.setVisible(false);
        this.main.jLabel1.setVisible(false);
        this.main.jBtnExport.setVisible(false);
        
        this.listPeopleByCountry(); // Creación de la primera gráfica
        this.listPeopleByNameContent(); // Creación de la segunda gráfica
    }
    
    public void listPeopleByCountry(){
        ArrayList<ReportByCountry> report = this.mdPeople.listPeopleByCountry();
        
        // ===============================================
        // Generación de gráfica - TORTA
        // ===============================================
        // DefaultPieDataset dataset = new DefaultPieDataset();
        // for (ReportByCountry record : report) {
        //     dataset.setValue(record.getCountry(), record.getAmountOfPeopleByCountry());
        //     System.out.println("Pais: " + record.getCountry() + " | Personas: " + record.getAmountOfPeopleByCountry());
        // }
        // JFreeChart chart = ChartFactory.createPieChart("Personas por País", dataset, true, true, true);
        // ChartPanel panel = new ChartPanel(chart);
        // panel.setMouseWheelEnabled(true);
        // this.main.JPanelAmountByCountry.setLayout(new java.awt.BorderLayout());
        // this.main.JPanelAmountByCountry.add(panel, BorderLayout.CENTER); // Para centrar la página en el JPanel
        // this.main.JPanelAmountByCountry.validate();
        // ===============================================
        
        // ===============================================
        // Generación de gráfica - Dual Axis
        // ===============================================
        DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
        for (ReportByCountry record : report) {
            dataset1.setValue(record.getAmountOfPeopleByCountry(),record.getCountry(), "Cantidad Personas");
            // System.out.println("Pais: " + record.getCountry() + " | Personas: " + record.getAmountOfPeopleByCountry());
        }
        JFreeChart chart = ChartFactory.createBarChart("Personas por país", "País", null, dataset1);
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        this.main.JPanelAmountByCountry.setLayout(new java.awt.BorderLayout());
        this.main.JPanelAmountByCountry.add(panel, BorderLayout.CENTER); // Para centrar la página en el JPanel
        this.main.JPanelAmountByCountry.validate();
        // ===============================================

    }    
    
    public void listPeopleByNameContent(){
        this.main.jPanelPercentageByName.removeAll(); // Limpia el JPanel para no superponer la nueva gráfica
        ArrayList<ReportByNameContent> report = this.mdPeople.listPeopleByNameContent(this.main.jTxtContentName.getText());
        // ===============================================
        // Generación de gráfica - TORTA
        // ===============================================
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (ReportByNameContent record : report) {
            dataset.setValue(record.getCountry(), record.getPercentage());
            // System.out.println("Pais: " + record.getCountry() + " | Porcentaje: " + record.getPercentage());
        }
        JFreeChart chart = ChartFactory.createPieChart("Porcentaje en coincidencias de '"+ this.main.jTxtContentName.getText() +"' en nombre de Personas por País", dataset, true, true, true);
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        this.main.jPanelPercentageByName.setLayout(new java.awt.BorderLayout());
        this.main.jPanelPercentageByName.add(panel, BorderLayout.CENTER); // Para centrar la página en el JPanel
        this.main.jPanelPercentageByName.validate();
        // ===============================================
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == this.main.jTxtContentName){
            this.listPeopleByNameContent();
        }
        else if(ae.getSource() == this.main.jBtnExport){
            ArrayList<ReportByNameContent> report = this.mdPeople.listPeopleByNameContent(this.main.jTxtContentName.getText());
            
            // Creación del archivo
            HSSFWorkbook book = new HSSFWorkbook();
            HSSFSheet sheet  = book.createSheet();
            book.setSheetName(0, "CountrysByContentName"); // Nombre de la pestaña
            
            // Cabeceras
            String [] headers = new String []{
                "Country",
                "Percentage"
            };
            
            CellStyle headerCellStyle = book.createCellStyle();
            HSSFFont font = book.createFont();
            font.setBold(true);
            headerCellStyle.setFont(font);
            HSSFRow headersRow = sheet.createRow(0);
            
            // Procesar los encabezados para agregarlos a una fila
            for (int i = 0; i < 2; i++) {
                 String header = headers[i];
                 HSSFCell cell = headersRow.createCell(i);
                 cell.setCellStyle(headerCellStyle);
                 cell.setCellValue(header);
            }
            // Procesamiento de los datos para agregarlos como filas
            for (int i = 0; i < report.size(); i++) {
                HSSFRow row = sheet.createRow(i + 1);
                String country = report.get(i).getCountry();
                double percentage = report.get(i).getPercentage();
                row.createCell(0).setCellValue(country);
                row.createCell(1).setCellValue(percentage);
            }
            // Guardar el archivo
            try {
                // Nombre del archivo
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss"); // Fecha actual
                String fileName = "CountrysByContentName_" 
                        + this.main.jTxtContentName.getText() 
                        + "_" 
                        + dtf.format(LocalDateTime.now()).toString()
                        + ".xls";
                
                // System.out.println(fileName);
                FileOutputStream file = new FileOutputStream(fileName);
                book.write(file);
                file.close();
                JOptionPane.showMessageDialog(main, "Archivo guardado con el nombre " + fileName, "Operación Exitosa", 1);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CtlPeople.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CtlPeople.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if(me.getSource() == this.main.jTabbedPane1 || me.getSource() == this.main.jPanelPercentageByName){
            this.main.jLabel1.setVisible(!this.main.jTxtContentName.isVisible());
            this.main.jTxtContentName.setVisible(!this.main.jTxtContentName.isVisible());
            this.main.jBtnExport.setVisible(!this.main.jBtnExport.isVisible());
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
    
}
