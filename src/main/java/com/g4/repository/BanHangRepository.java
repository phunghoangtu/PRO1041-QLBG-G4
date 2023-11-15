/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository;

import com.g4.utils.JdbcHelper;
import com.g4.viewmodel.GioHangViewModel;
import com.g4.viewmodel.HoaDonViewModel;
import com.g4.viewmodel.SanPhamViewModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tuphp
 */
public class BanHangRepository {

    String slect_hd_chua_thanh_toan_bh = "SELECT hd.Id, hd.MaHD, hd.NgayTao, nv.TenNhanVien,kh.TenKhachHang, hd.TrangThai\n"
            + "FROM dbo.HoaDon hd        \n"
            + "            INNER JOIN dbo.NhanVien nv ON hd.IdNhanVien = nv.Id\n"
            + "			INNER JOIN dbo.KhachHang kh ON hd.IdKhachHang = kh.Id\n"
            + "WHERE hd.TrangThai = 1\n"
            + "ORDER BY hd.MaHD DESC";
    String slect_all_sp_bh = "SELECT SP.Id, SP.MaSanPham, SP.TenSanPham, KT.KichCo, MS.TenMauSac, CLG.TenChatLieu, TH.TenThuongHieu, SP.SoLuong, SP.GiaBan\n"
            + "FROM dbo.SanPham SP\n"
            + "			INNER JOIN dbo.KichCoGiay KT ON SP.IdKichCoGiay = KT.Id\n"
            + "			INNER JOIN dbo.MauSac MS ON SP.IdMauSac = MS.Id\n"
            + "			INNER JOIN dbo.ChatLieuGiay CLG ON SP.IdChatLieuGiay = CLG.Id\n"
            + "			INNER JOIN dbo.ThuongHieu TH ON SP.IdThuongHieu = TH.Id";
    String select_gh_bh = "SELECT HDCT.Id, SP.Id, SP.MaSanPham, SP.TenSanPham, HDCT.SoLuong, SP.GiaBan\n"
            + "FROM dbo.SanPham SP\n"
            + "	INNER JOIN dbo.HoaDonChiTiet HDCT ON SP.Id = HDCT.IdSanPham\n"
            + "WHERE HDCT.IdHoaDon LIKE ?";
    String insert_hdct = "INSERT INTO HoaDonChiTiet (IdHD,IdSP,SoLuong,DonGia) VALUES (?,?,?,?)";
    String update_soluong_sp_by_id = "UPDATE SanPham SET SoLuong = ? WHERE ID = ?";
    String delete_hdct_by_idHoaDon = "Delete from HoaDonChiTiet where IdHD = ?";
    String delete_hd_by_id = "Delete from HoaDon where Id = ?";
    String update_NVKH = "UPDATE HoaDon SET IdNV = ? WHERE MaHD = ?";
    String update_thanh_toan = "UPDATE HoaDon SET NgayThanhToan = GETDATE() ,TongTien = ? ,TrangThai = ? WHERE MaHD = ?";
    String delete_giohang = "Delete from HoaDonChiTiet where Id = ?";
    String capNhatSoLuong2 = "Update SanPham Set SoLuong = SoLuong - ? where Id = ?";
    String capNhatSoLuong = "Update SanPham Set SoLuong = SoLuong + ? where Id = ?";
    String updateSoLuong = "UPDATE SanPham SET SoLuong = ? WHERE ID = ?";
    String updateSoLuongHDCT = "UPDATE HoaDonChiTiet SET SoLuong = ? WHERE Id = ?";
    String insert_hoadon = "Insert into HoaDon(IdNV,IdKH, MaHD) values (?,?,?);";

    String findByIdKH_ma = "SELECT Id FROM KhachHang WHERE MaKH = ?";
    String select_KHN = "select * from KhachHang";

    
    
    public List<GioHangViewModel> getGioHang(String id) {
        return this.selectBySqlGH(select_gh_bh, "%" + id + "%");
    }

    public List<HoaDonViewModel> getALLHD() {
        return selectBySqlHD(slect_hd_chua_thanh_toan_bh);
    }

    public List<SanPhamViewModel> getAllSP() {
        return selectBySqlSP(slect_all_sp_bh);
    }

    public List<HoaDonViewModel> selectBySqlHD(String sql, Object... args) {
        List<HoaDonViewModel> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                HoaDonViewModel entity = new HoaDonViewModel();
                entity.setId(rs.getString(1));
                entity.setMaHD(rs.getString(2));
                entity.setNgayTao(rs.getDate(3));
                entity.setIdNV(rs.getString(4));
                entity.setIdKH(rs.getString(5));
                entity.setTrangThai(rs.getInt(6));
                list.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<SanPhamViewModel> selectBySqlSP(String sql, Object... args) {
        List<SanPhamViewModel> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                SanPhamViewModel entity = new SanPhamViewModel();
                entity.setId(rs.getString(1));
                entity.setMaSP(rs.getString(2));
                entity.setTenSP(rs.getString(3));
                entity.setIdKT(rs.getString(4));
                entity.setIDMS(rs.getString(5));
                entity.setIdDG(rs.getString(6));
                entity.setIdTH(rs.getString(7));
                entity.setSoLuong(rs.getInt(8));
                entity.setGiaBan(rs.getDouble(9));
                list.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<GioHangViewModel> selectBySqlGH(String sql, Object... args) {
        List<GioHangViewModel> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                GioHangViewModel entity = new GioHangViewModel();
                entity.setId(rs.getString(1));
                entity.setIdSP(rs.getString(2));
                entity.setMaSP(rs.getString(3));
                entity.setTenSP(rs.getString(4));
                entity.setSoLuong(rs.getInt(5));
                entity.setDonGia(rs.getDouble(6));
                list.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
