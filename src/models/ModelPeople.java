/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import classes.People;
import classes.ReportByCountry;
import classes.ReportByNameContent;

/**
 *
 * @author fercho
 */
public class ModelPeople {
    DbData dbData;

    public ModelPeople() {
        this.dbData = new DbData();
    }
    
    public ArrayList listPeopleByCountry(){
        ArrayList<ReportByCountry> report = new ArrayList<ReportByCountry>();
        try (Connection conn = DriverManager.getConnection(this.dbData.getUrl(), 
                                                            this.dbData.getUser(), 
                                                            this.dbData.getPass())){
            String query = "SELECT country, COUNT(country) AS cantidad FROM Tb_People GROUP BY country";
            PreparedStatement ps = conn.prepareStatement(query);
            
            ResultSet result  = ps.executeQuery();
            
            while(result.next()){
                ReportByCountry rep = new ReportByCountry();
                String countryName = result.getString(1);
                int amount = result.getInt(2);
                rep.setCountry(countryName);
                rep.setAmountOfPeopleByCountry(amount);
                // System.out.println("Pais: " + countryName + " | Personas: " + amount);
                report.add(rep);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return report;
    }
    
    public ArrayList listPeopleByNameContent(String nameContent){
        ArrayList<ReportByNameContent> report = new ArrayList<ReportByNameContent>();
        try (Connection conn = DriverManager.getConnection(this.dbData.getUrl(), 
                                                            this.dbData.getUser(), 
                                                            this.dbData.getPass())){
            String query = "SELECT country, ROUND(((COUNT(name)*100)/(SELECT COUNT(*) FROM Tb_People)),2) as Porcentaje \n" +
                           "FROM Tb_People \n" +
                           "WHERE name LIKE ? \n" +
                           "GROUP BY country;";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, "%" + nameContent + "%");
            
            ResultSet result  = ps.executeQuery();
            
            while(result.next()){
                ReportByNameContent rep = new ReportByNameContent();
                String countryName = result.getString(1);
                double percentage = result.getDouble(2);
                rep.setCountry(countryName);
                rep.setPercentage(percentage);
                // System.out.println("País: " + countryName + " | Porcentaje: " + percentage);
                report.add(rep);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return report;
    }
    
}
