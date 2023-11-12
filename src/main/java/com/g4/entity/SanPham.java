package com.g4.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SanPham {

    private String id;
    private String maSP;
    private String tenSP;
    private String idKT;
    private String iDMS;
    private String idCL;
    private String idTH;
    private int soLuong;
    private Double giaBan;
    private Double giamGia;

    public SanPham(int soLuong) {
        this.soLuong = soLuong;
    }

}
