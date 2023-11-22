/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

import com.g4.entity.MauSac;
import com.g4.entity.ThuongHieu;
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
public class ThuongHieuRepository {
    public List<ThuongHieu> getThuongHieu(){
        try {
            Connection conn = JdbcHelper.openDbConnection();
            List<ThuongHieu> listmau = new ArrayList<>();
            String caulenh = "select * from ThuongHieu where TrangThai like 1";
        
            PreparedStatement stm = conn.prepareStatement(caulenh);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {                
                ThuongHieu layMau  = new ThuongHieu();
                layMau.setId(rs.getInt("Id"));
                layMau.setTenthuonghieu(rs.getString("TenThuongHieu"));
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
    
    public ThuongHieu getByID(int id){
        ThuongHieu banmau = new ThuongHieu();
        for(ThuongHieu cl: getThuongHieu()){
            if(id==cl.getId()){
                banmau =  cl;
                break;
            }
        }
        return banmau;
    }
    
    public ThuongHieu getByThuongHieu(String ten){
        ThuongHieu banmau = new ThuongHieu();
        for(ThuongHieu cl: getThuongHieu()){
            if(ten.equalsIgnoreCase(cl.getTenthuonghieu())){
                banmau =  cl;
                break;
            }
        }
        return banmau;
    }
    
    public void themHieu(String tenMau) {
        try {
            Connection conn = JdbcHelper.openDbConnection();
            String caulenh = "insert into ThuongHieu(TenThuongHieu, TrangThai)"
                                        + "values(?,1)";

            PreparedStatement stm = conn.prepareStatement(caulenh);

            stm.setString(1, tenMau);

            stm.executeQuery();

            stm.close();
            conn.close();
        } catch (Exception e) {
        }
    }

    public void suaHieu(String tenMau, int IDmau) {
        try {
            Connection conn = JdbcHelper.openDbConnection();
            String caulenh = "update ThuongHieu set TenThuongHieu = ? where Id = ?";

            PreparedStatement stm = conn.prepareStatement(caulenh);

            stm.setString(1, tenMau);
            stm.setInt(2, IDmau);

            stm.executeQuery();

            stm.close();
            conn.close();
        } catch (Exception e) {
        }
    }
    
    public void xoaHieu(int IDmau) {
        try {
            Connection conn = JdbcHelper.openDbConnection();
            String caulenh = "update ThuongHieu set TrangThai = 0 where Id = ?";

            PreparedStatement stm = conn.prepareStatement(caulenh);

            stm.setInt(1, IDmau);

            stm.executeQuery();

            stm.close();
            conn.close();
        } catch (Exception e) {
        }
    }
}
