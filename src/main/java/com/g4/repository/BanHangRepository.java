/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository;

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

}
