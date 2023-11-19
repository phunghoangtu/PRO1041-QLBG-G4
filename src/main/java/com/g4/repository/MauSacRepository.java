/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository;

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
public class MauSacRepository {
    public List<MauSac> getMau(){
        try {
            Connection conn = JdbcHelper.openDbConnection();
            List<MauSac> listmau = new ArrayList<>();
            String caulenh = "select * from MauSac where TrangThai like 1";
        
            PreparedStatement stm = conn.prepareStatement(caulenh);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {                
                MauSac layMau  = new MauSac();
                layMau.setId(rs.getInt("Id"));
                layMau.setTenmausac(rs.getString("TenMauSac"));
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
    
    public MauSac getByID(int id){
        MauSac banmau = new MauSac();
        for(MauSac cl: getMau()){
            if(id==cl.getId()){
                banmau =  cl;
                break;
            }
        }
        return banmau;
    }
    
}
