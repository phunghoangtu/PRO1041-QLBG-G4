/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository;

import com.g4.entity.KichCoGiay;
import com.g4.entity.MauSac;
import com.g4.utils.JdbcHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class KichCoRepository {
    public List<KichCoGiay> getKichCo(){
        try {
            Connection conn = JdbcHelper.openDbConnection();
            List<KichCoGiay> listmau = new ArrayList<>();
            String caulenh = "select * from KichCoGiay where TrangThai like 1";
        
            PreparedStatement stm = conn.prepareStatement(caulenh);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {                
                KichCoGiay layMau  = new KichCoGiay();
                layMau.setId(rs.getInt("Id"));
                layMau.setKichco(rs.getString("KichCo"));
                layMau.setTrangthai(1);
                
                listmau.add(layMau);
            }
            rs.close();
            stm.close();
            conn.close();
            return listmau;
        } catch (Exception e) {
            return null;
        }
    }
    
    public KichCoGiay getByID(int id){
        KichCoGiay banmau = new KichCoGiay();
        for(KichCoGiay cl: getKichCo()){
            if(id==cl.getId()){
                banmau =  cl;
                break;
            }
        }
        return banmau;
    }
    
    public void themKich(String tenMau) {
        try {
            Connection conn = JdbcHelper.openDbConnection();
            String caulenh = "insert into KichCoGiay(KichCo, TrangThai)"
                                        + "values(?,1)";

            PreparedStatement stm = conn.prepareStatement(caulenh);

            stm.setString(1, tenMau);

            stm.executeQuery();

            stm.close();
            conn.close();
        } catch (Exception e) {
        }
    }

    public void suaKich(String tenMau, int IDmau) {
        try {
            Connection conn = JdbcHelper.openDbConnection();
            String caulenh = "update KichCoGiay set KichCo = ? where Id = ?";

            PreparedStatement stm = conn.prepareStatement(caulenh);

            stm.setString(1, tenMau);
            stm.setInt(2, IDmau);

            stm.executeQuery();

            stm.close();
            conn.close();
        } catch (Exception e) {
        }
    }
    
    public void xoaKich(int IDmau) {
        try {
            Connection conn = JdbcHelper.openDbConnection();
            String caulenh = "update KichCoGiay set TrangThai = 0 where Id = ?";

            PreparedStatement stm = conn.prepareStatement(caulenh);

            stm.setInt(1, IDmau);

            stm.executeQuery();

            stm.close();
            conn.close();
        } catch (Exception e) {
        }
    }
}
