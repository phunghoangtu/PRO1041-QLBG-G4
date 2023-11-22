/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

import com.g4.entity.NhanVien;
import com.g4.repository.G4Repository;
import com.g4.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ddawng
 */
public class NhanVienRepository extends G4Repository<NhanVien, String> {

    String insert_sql = "Insert into NhanVien(Manv, MatKhau, Hoten, vaiTro)values(?,?,?,?)";
    String update_sql = "Update NhanVien set MatKhau = ?, Hoten = ?, VaiTro = ? Where MaNV = ?";
    String delete_sql = "Delete from NhanVien Where MaNV = ?";
    String select_all_sql = "select * from NhanVien";
    String select_by_id_sql = "Select * from NhanVien Where MaNV = ?";

    @Override
    public void insert(NhanVien entity) {
        JdbcHelper.update(insert_sql, entity.getId(), entity.getMatKhau(), entity.getTenNV(), entity.isVaiTro());
    }

    @Override
    public void update(NhanVien entity) {
        JdbcHelper.update(update_sql, entity.getId(), entity.getMatKhau(), entity.getTenNV(), entity.isVaiTro());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(delete_sql, id);
    }

    @Override
    public List<NhanVien> selectAll() {
        return selectBySql(select_all_sql);
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = selectBySql(select_by_id_sql, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while(rs.next()){
                NhanVien entity = new NhanVien();
                entity.setId(rs.getString("Id"));
                entity.setTenNV(rs.getString("TenNhanVien"));
                entity.setDiaChi(rs.getString("DiaChi"));
                entity.setEmail(rs.getString("Email"));
                entity.setGioiTinh(rs.getInt("GioiTinh"));
                entity.setNgaySinh(rs.getDate("NgaySinh"));
                entity.setSdt(rs.getString("SoDienThoai"));
                entity.setNgayTao(rs.getDate("NgayTao"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setVaiTro(rs.getBoolean("VaiTro"));
                list.add(entity);
            }
        } catch (Exception e) {
        }
        return list;
    }

}
