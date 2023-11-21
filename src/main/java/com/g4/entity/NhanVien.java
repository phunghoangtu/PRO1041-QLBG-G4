package com.g4.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NhanVien {

    private String id;
    private String tenNV;
    private Date ngaySinh;
    private Date ngayTao;
    
    private String diaChi;
    private String email;
    private String sdt;
    private String matKhau;
    private int trangThai;
    private boolean vaiTro;
    
}
