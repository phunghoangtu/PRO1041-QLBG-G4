/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

import com.g4.entity.NhanVien;
import com.g4.repository.G4Repository;
import java.util.List;

/**
 *
 * @author Ddawng
 */
public class GiaoCaRepository extends G4Repository<GiaoCaRepository, Integer> {

    String select_all_sql = "SELECT dbo.GiaoCa.Id, dbo.NhanVien.TenNhanVien, dbo.CaLam.NgayGiaoCa, dbo.CaLam.GioBatDau, dbo.CaLam.GioKetThuc\n"
            + "FROM     dbo.CaLam INNER JOIN\n"
            + "                  dbo.GiaoCa ON dbo.CaLam.Id = dbo.GiaoCa.IdCaLam INNER JOIN\n"
            + "                  dbo.NhanVien ON dbo.GiaoCa.IdNhanVien = dbo.NhanVien.Id ";
    
    String delete_sql = "DELECT FROM GIAOCA where Id = ?"; 
    

    @Override
    public void insert(GiaoCaRepository entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(GiaoCaRepository entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<GiaoCaRepository> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public GiaoCaRepository selectById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<GiaoCaRepository> selectBySql(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
