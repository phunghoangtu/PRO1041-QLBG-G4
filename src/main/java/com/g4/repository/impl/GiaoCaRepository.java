/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

import com.g4.entity.GiaoCa;
import com.g4.entity.NhanVien;
import com.g4.repository.G4Repository;
import com.g4.utils.JdbcHelper;
import java.util.List;

/**
 *
 * @author Ddawng
 */
public class GiaoCaRepository extends G4Repository<GiaoCa, Integer> {

    String select_all_sql = "SELECT dbo.GiaoCa.Id, dbo.NhanVien.TenNhanVien, dbo.CaLam.NgayGiaoCa, dbo.CaLam.GioBatDau, dbo.CaLam.GioKetThuc\n"
            + "FROM     dbo.CaLam INNER JOIN\n"
            + "                  dbo.GiaoCa ON dbo.CaLam.Id = dbo.GiaoCa.IdCaLam INNER JOIN\n"
            + "                  dbo.NhanVien ON dbo.GiaoCa.IdNhanVien = dbo.NhanVien.Id ";
    
    String delete_sql = "DELECT FROM GIAOCA where Id = ?";
    String insert_sql = "insert into GiaoCa (IdNhanVien, IdCaLam) values (?,?) ";

    @Override
    public void insert(GiaoCa entity) {
        JdbcHelper.update(insert_sql, entity.getIdNV(), entity.getIdCL());
    }

    @Override
    public void update(GiaoCa entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<GiaoCa> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public GiaoCa selectById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<GiaoCa> selectBySql(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    

    

}
