package com.g4.viewmodel;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonViewModel {

    private String id;
    private String maHD;
    private Date ngayTao;
    private String idNV;
    private int trangThai;
    private double TongTien; 
    
    
    public String trangThai() {
        if (getTrangThai() == 0) {
            return "Đã hủy";
        } else if (getTrangThai() == 1) {
            return "Chờ thanh toán";
        } else {
            return "Đã thanh toán";
        }
    }

    public Object[] toRowDataHD() {
        return new Object[]{maHD, ngayTao, idNV, trangThai()};
    }

}
