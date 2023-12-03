/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

import com.g4.repository.G4Repository;
import com.g4.viewmodel.HoaDonViewModel;
import com.g4.entity.HoaDon;
import com.g4.utils.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author khuong duc
 */
public class HoaDonRepository extends G4Repository<HoaDon, String>{
    String select_all_sql = "select * from HoaDon";
    String select_by_id_sql = "Select * from HoaDon Where id = ?";
    
    public List<HoaDon> selectAll() {
        return selectBySql(select_all_sql);
    }

    public HoaDon selectById(String id) {
        List<HoaDon> list = selectBySql(select_by_id_sql, id);
        if(list.isEmpty()){
        return null;
        }
        return list.get(0);
    }

    public List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while(rs.next()){
                HoaDon entity = new HoaDon();
                entity.setId(rs.getString("Id"));
                entity.setIdNV(rs.getInt("IdNhanVien"));
                entity.setMaHD(rs.getString("MaHD"));
                entity.setNgayTao(rs.getDate("NgayTao"));
                entity.setTongTien(rs.getDouble("TongTien"));
                entity.setNgayThanhToan(rs.getDate("NgayThanhToan"));
                entity.setGhiChu(rs.getString("GhiChu"));
                entity.setTrangThai(rs.getInt("TrangThai"));
                entity.setHTTT(rs.getInt("HinhThucThanhToan"));

                list.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void insert(HoaDon entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(HoaDon entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
