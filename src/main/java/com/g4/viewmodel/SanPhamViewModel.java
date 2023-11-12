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
public class SanPhamViewModel {

    private String id;
    private String maSP;
    private String tenSP;
    private String idKT;
    private String iDMS;
    private String idDG;
    private String idTH;
    private int soLuong;
    private Double giaBan; 
    DecimalFormat fomat = new DecimalFormat("###,###,###");

    public Object[] todataRowSanPham() {
        String formattedGiaBan = fomat.format(giaBan) + " VND";
        return new Object[]{maSP, tenSP, idKT, iDMS, idDG, idTH, soLuong, formattedGiaBan};
    }

}
