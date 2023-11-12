package com.g4.repository;

import com.g4.entity.HoaDon;
import com.g4.entity.HoaDonChiTiet;
import com.g4.entity.SanPham;
import com.g4.utils.JdbcHelper;
import com.g4.viewmodel.GioHangViewModel;
import com.g4.viewmodel.HoaDonViewModel;
import com.g4.viewmodel.SanPhamViewModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BanHangRepository {

    String slect_hd_chua_thanh_toan = "SELECT hd.Id, hd.MaHD, hd.NgayTao, dbo.NhanVien.TenNV, hd.TrangThai\n"
            + "            FROM dbo.HoaDon hd\n"
            + "            	INNER JOIN dbo.NhanVien ON hd.IdNV = dbo.NhanVien.Id\n"
            + "            WHERE hd.TrangThai = 1\n"
            + "           ORDER BY hd.MaHD DESC";
    String slect_all_sp = "SELECT SP.Id, SP.MaSP, SP.TenSP, KT.GiaTri, MS.Ten, DG.Ten, TH.Ten, SP.SoLuong, SP.GiaBan\n"
            + "                    FROM dbo.SanPham SP\n"
            + "INNER JOIN dbo.KichThuoc KT ON SP.IdKT = KT.Id\n"
            + "INNER JOIN dbo.MauSac MS ON SP.IdMS = MS.Id\n"
            + "INNER JOIN dbo.DeGiay DG ON SP.IdDG = DG.Id\n"
            + "INNER JOIN dbo.ThuongHieu TH ON SP.IdTH = TH.Id";
    String select_GH = "SELECT HDCT.Id, SP.Id, SP.MaSP, SP.TenSP, HDCT.SoLuong, SP.GiaBan\n"
            + "FROM dbo.SanPham SP\n"
            + "	INNER JOIN dbo.HoaDonChiTiet HDCT ON SP.Id = HDCT.IdSP\n"
            + "WHERE HDCT.IdHD LIKE ?";

    String getBiggestMa = "SELECT MAX(hd.MaHD) FROM HoaDon hd";
    String insert_hd = "";
    String insert_hdct = "INSERT INTO dbo.HoaDonChiTiet (IdHD,IdSP,SoLuong,DonGia) VALUES (?,?,?,?)";
    String update_soluong_SP = "UPDATE SanPham SET SoLuong = ? WHERE ID = ?";
    String findByIdKH_ma = "SELECT Id FROM KhachHang WHERE MaKH = ?";

        public String deleteHDCT(String idHD) {
        String query = "Delete from HoaDonChiTiet where IdHD = ?";
        try ( Connection con = JdbcHelper.openDbConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, idHD);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Ôi hỏng";
    }

    public String deleteHD(String idHD) {
        String query = "Delete from HoaDon where Id = ?";
        try ( Connection con = JdbcHelper.openDbConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, idHD);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Ôi hỏng";
    }
    
    public String updateNVKH(HoaDon hdUpdate, String ma) {
        String query = "UPDATE [dbo].[HoaDon] SET IdNV = ? WHERE MaHD = ?";
        try ( Connection con = JdbcHelper.openDbConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(2, ma);
            ps.setObject(1, hdUpdate.getIdNV());
            if (ps.executeUpdate() > 0) {
                return "Thay đổi thành công";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Thay đổi thất bại";
    }

    public String updateTrangThai(HoaDonViewModel hd, String ma) {
        String query = "UPDATE [dbo].[HoaDon]\n"
                + "   SET \n"
                + "      ,[NgayThanhToan] = GETDATE()\n"
                + "      ,[TongTien] = ?\n"
                + "      ,[TrangThai] = ?\n"
                + " WHERE Ma = ?";
        try ( Connection con = JdbcHelper.openDbConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(2, hd.getTongTien());
            ps.setObject(3, hd.getTrangThai());
            ps.setObject(4, ma);

            if (ps.executeUpdate() > 0) {
                return "Thành công";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Thất bại";
    }

    public String deleteGioHang(String id) {
        String query = "Delete from HoaDonChiTiet where Id = ?";
        try ( Connection con = JdbcHelper.openDbConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, id);

            if (ps.executeUpdate() > 0) {
                return "Xóa sản phẩm thành công";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Để xác nhận vui lòng chọn lại sản phẩm cần xóa";
    }

    public String capNhatSoLuong2(SanPham ctsp, String id) {
        String query = "Update SanPham Set SoLuong = SoLuong - ? where Id = ?";
        try ( Connection con = JdbcHelper.openDbConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, ctsp.getSoLuong());
            ps.setObject(2, id);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public String capNhatSoLuong(SanPham ctsp, String id) {
        String query = "Update SanPham Set SoLuong = SoLuong + ? where Id = ?";
        try ( Connection con = JdbcHelper.openDbConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, ctsp.getSoLuong());
            ps.setObject(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public String updateSoLuong(SanPham ctsp, String id) {
        String query = "UPDATE [dbo].[SanPham] SET [SoLuong] = ? WHERE ID = ?";
        try ( Connection con = JdbcHelper.openDbConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, ctsp.getSoLuong());
            ps.setObject(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public String updateSoLuongHDCT(GioHangViewModel gh, String id) {
        String query = "UPDATE [dbo].[HoaDonChiTiet]\n"
                + "   SET \n"
                + "      [SoLuong] = ?\n"
                + " WHERE Id = ?";
        try ( Connection con = JdbcHelper.openDbConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, gh.getSoLuong());
            ps.setObject(2, id);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public String addHDCT(HoaDonChiTiet hdct) {
        try ( Connection con = JdbcHelper.openDbConnection();  PreparedStatement ps = con.prepareStatement(insert_hdct)) {

            ps.setObject(1, hdct.getIdHD());
            ps.setObject(2, hdct.getIdSP());
            ps.setObject(3, hdct.getSoLuong());
            ps.setObject(4, hdct.getDonGia());
            if (ps.executeUpdate() > 0) {
                return "Thêm sản phẩm thành công";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Thêm sản phẩm thất bại";

    }

    public String saveHoaDon(HoaDonViewModel hd) {
        String query = "Insert into HoaDon(IdNV, MaHD, NgayTao) values (?,?,GETDATE());";
        try ( Connection con = JdbcHelper.openDbConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, hd.getIdNV());
            ps.setObject(2, hd.getMaHD());        
            if (ps.executeUpdate() > 0) {
                return "Thêm hóa đơn thành công";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Thêm hóa đơn thất bại";
    }

    

    public List<GioHangViewModel> getGioHang(String id) {
        return this.selectBySqlGH(select_GH, "%" + id + "%");
    }

    public List<HoaDonViewModel> getALLHD() {
        return selectBySqlHD(slect_hd_chua_thanh_toan);
    }

    public List<SanPhamViewModel> getAllSP() {
        return selectBySqlSP(slect_all_sp);
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
                entity.setTrangThai(rs.getInt(5));
                list.add(entity);
            }
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
