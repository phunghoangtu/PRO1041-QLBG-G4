/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

import com.g4.entity.SanPham;
import com.g4.utils.JdbcHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class SanPhamRepository {
    public List<SanPham> getConBan(String kwd) throws SQLException{
        Connection conn = JdbcHelper.openDbConnection();
        List<SanPham> conban = new ArrayList<>();
        String caulenh = "select * from SanPham where TrangThai like 1 and TenSanPham like ? or TrangThai like 1 and MaSanPham like ?";
        
        PreparedStatement stm = conn.prepareStatement(caulenh);
        stm.setString(1, "%"+kwd+"%");
        stm.setString(2, "%"+kwd+"%");
        ResultSet rs = stm.executeQuery();
       
            
        while (rs.next()) {                
            SanPham mau = new SanPham();

            mau.setId(rs.getInt("Id"));

            mau.setIdkichcogiay(rs.getInt("IdKichCoGiay"));
            mau.setIdmausac(rs.getInt("IdMauSac"));
            mau.setIdchatlieugiay(rs.getInt("IdChatLieuGiay"));
            mau.setIdthuonghieu(rs.getInt("IdThuongHieu"));

            mau.setMasanpham(rs.getString("MaSanPham"));
            mau.setTensanpham(rs.getString("TenSanPham"));
            mau.setGiaban(rs.getDouble("GiaBan"));
            mau.setSoluong(rs.getInt("SoLuong"));
            mau.setMota(rs.getString("MoTa"));
            mau.setTrangthai(1);

            conban.add(mau);
        }
        rs.close();
        stm.close();
        conn.close();
        return conban;
    }
    
    public List<SanPham> getDungBan() throws SQLException{
        Connection conn = JdbcHelper.openDbConnection();
        List<SanPham> conban = new ArrayList<>();
        String caulenh = "select * from SanPham where TrangThai like 0";
        
        PreparedStatement stm = conn.prepareStatement(caulenh);
        ResultSet rs = stm.executeQuery();
       
        while (rs.next()) {                
            SanPham mau = new SanPham();

            mau.setId(rs.getInt("Id"));

            mau.setIdkichcogiay(rs.getInt("IdKichCoGiay"));
            mau.setIdmausac(rs.getInt("IdMauSac"));
            mau.setIdchatlieugiay(rs.getInt("IdChatLieuGiay"));
            mau.setIdthuonghieu(rs.getInt("IdThuongHieu"));

            mau.setMasanpham(rs.getString("MaSanPham"));
            mau.setTensanpham(rs.getString("TenSanPham"));
            mau.setGiaban(rs.getDouble("GiaBan"));
            mau.setSoluong(rs.getInt("SoLuong"));
            mau.setMota(rs.getString("MoTa"));
            mau.setTrangthai(0);

            conban.add(mau);
        }
        rs.close();
        stm.close();
        conn.close();
        return conban;
    }

    public void luu(SanPham spmoi) throws SQLException {
        try {
            Connection conn = JdbcHelper.openDbConnection();
        String caulenh = "insert into SanPham(IdKichCoGiay, IdMauSac, IdChatLieuGiay, IdThuongHieu, MaSanPham, TenSanPham, GiaBan, SoLuong, MoTa, TrangThai)"
                                    + "values(?,?,?,?,?,?,?,?,?,?)";
        
        PreparedStatement stm = conn.prepareStatement(caulenh);
        
        stm.setInt(1, spmoi.getIdkichcogiay());
        stm.setInt(2, spmoi.getIdmausac());
        stm.setInt(3, spmoi.getIdchatlieugiay());
        stm.setInt(4, spmoi.getIdthuonghieu());
        
        stm.setString(5, spmoi.getMasanpham());
        stm.setString(6, spmoi.getTensanpham());
        stm.setDouble(7, spmoi.getGiaban());
        stm.setInt(8, spmoi.getSoluong());
        stm.setString(9, spmoi.getMota());
        stm.setInt(10, 1);
        
        stm.executeQuery();
        
        stm.close();
        conn.close();
        } catch (Exception e) {
        }
    }

    public void sua(SanPham spSua) throws SQLException {
        try {
            Connection conn = JdbcHelper.openDbConnection();
        String caulenh = "update SanPham set IdKichCoGiay=?, IdMauSac=?, IdChatLieuGiay=?, IdThuongHieu=?, MaSanPham=?, TenSanPham=?, GiaBan=?, SoLuong=?, MoTa=?, TrangThai=? where Id = ?";
        
        PreparedStatement stm = conn.prepareStatement(caulenh);
        
        stm.setInt(1, spSua.getIdkichcogiay());
        stm.setInt(2, spSua.getIdmausac());
        stm.setInt(3, spSua.getIdchatlieugiay());
        stm.setInt(4, spSua.getIdthuonghieu());
        
        stm.setString(5, spSua.getMasanpham());
        stm.setString(6, spSua.getTensanpham());
        stm.setDouble(7, spSua.getGiaban());
        stm.setInt(8, spSua.getSoluong());
        stm.setString(9, spSua.getMota());
        stm.setInt(10, 1);
        
        stm.setInt(11, spSua.getId());
        
        stm.executeQuery();
        
        stm.close();
        conn.close();
        } catch (Exception e) {
        }
    }

    public void xoa(int parseInt) throws SQLException {
        try {
            Connection conn = JdbcHelper.openDbConnection();
        String caulenh = "update SanPham set TrangThai = 0 where Id = ?";
        
        PreparedStatement stm = conn.prepareStatement(caulenh);
        stm.setInt(1, parseInt);
        
        stm.executeQuery();
        
        stm.close();
        conn.close();
        } catch (Exception e) {
        }
    }
}
