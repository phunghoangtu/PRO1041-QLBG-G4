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
public class HoaDonChiTiet {

    private String idHD;
    private String idSP;
    private int soLuong;
    private Double donGia;

}
