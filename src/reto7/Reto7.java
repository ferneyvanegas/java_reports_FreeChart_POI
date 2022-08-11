/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package reto7;

import controllers.CtlPeople;
import views.JFrameMain;

/**
 * @project Reto 7
 * @version 1.0.1
 * @author Ferney Vanegas Hernández
 * @school MinTic - Universidad de Caldas
 * @year 2022
 */
public class Reto7 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrameMain jFrameMain = new JFrameMain();
        CtlPeople ctlPeople = new CtlPeople(jFrameMain);
        jFrameMain.setVisible(true);
    }
    
}
