package com.g4.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SanPham {
    private int id;//Id INT IDENTITY(1,1) PRIMARY KEY,
    private int idkichcogiay;//IdKichCoGiay INT DEFAULT NULL,
    private int idmausac;//IdMauSac INT DEFAULT NULL,
    private int idchatlieugiay;//IdChatLieuGiay INT DEFAULT NULL,
    private int idthuonghieu;//IdThuongHieu INT DEFAULT NULL,
    private String masanpham;//MaSanPham VARCHAR(20) UNIQUE,
    private String tensanpham;//TenSanPham NVARCHAR(30) DEFAULT NULL,
    private double giaban;//GiaBan DECIMAL(20,0) DEFAULT 0,  
    private int soluong;//SoLuong INT DEFAULT 0,
    private String mota;//MoTa NVARCHAR(50) DEFAULT NULL,
    private int trangthai;//TrangThai INT DEFAULT 1,

}
