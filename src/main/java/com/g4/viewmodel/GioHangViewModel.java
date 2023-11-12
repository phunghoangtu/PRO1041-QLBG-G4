package com.g4.viewmodel;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GioHangViewModel {

    private String id;
    private String idSP;
    private String maSP;
    private String tenSP;
    private int soLuong;
    private Double donGia;
    DecimalFormat fomat = new DecimalFormat("###,###,###");

    public Object[] todataRow() {
        String formattedDonGia = fomat.format(donGia) + " VND";
        return new Object[]{maSP, tenSP, soLuong, formattedDonGia};
    }
    
    

    public GioHangViewModel(String idSP, String maSP, String tenSP, int soLuong, Double donGia) {
        this.idSP = idSP;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public GioHangViewModel(String maSP, String tenSP, int soLuong, Double donGia) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }
}
