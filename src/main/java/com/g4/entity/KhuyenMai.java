/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author khuong duc
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KhuyenMai {

    private String id;

    private String tenKM;

    private String ngaybatDau;

    private String ngayketThuc;

    private String moTa;

    private Double muctramGiam;

    private boolean kieugiamGia;

    public String giamGiaTT() {
        if (getMuctramGiam() == 0.95) {
            return "5%";
        } else if (getMuctramGiam() == 0.9) {
            return "10%";
        } else if (getMuctramGiam() == 0.85) {
            return "15%";
        } else if (getMuctramGiam() == 0.8) {
            return "20%";
        } else if (getMuctramGiam() == 0.75) {
            return "25%";
        } else if (getMuctramGiam() == 0.7) {
            return "30%";
        } else if (getMuctramGiam() == 0.65) {
            return "35%";
        } else if (getMuctramGiam() == 0.6) {
            return "40%";
        } else if (getMuctramGiam() == 0.55) {
            return "45%";
        } else if (getMuctramGiam() == 0.5) {
            return "50%";
        } else if (getMuctramGiam() == 0.5) {
            return "55%";
        } else if (getMuctramGiam() == 0.5) {
            return "60%";
        } else if (getMuctramGiam() == 0.5) {
            return "65%";
        } else if (getMuctramGiam() == 0.5) {
            return "70%";
        } else if (getMuctramGiam() == 0.5) {
            return "76%";
        } else if (getMuctramGiam() == 0.5) {
            return "80%";
        } else if (getMuctramGiam() == 0.5) {
            return "85%";
        } else if (getMuctramGiam() == 0.5) {
            return "90%";
        } else if (getMuctramGiam() == 0.5) {
            return "95%";
        } else {
            return "0%";
        }
    }

    public String giamGiaTT1() {

        if (getMuctramGiam() == 5000) {
            return "5,000";
        } else if (getMuctramGiam() == 10000) {
            return "10,000";
        } else if (getMuctramGiam() == 15000) {
            return "15,000";
        } else if (getMuctramGiam() == 20000) {
            return "20,000";
        } else if (getMuctramGiam() == 25000) {
            return "25,000";
        } else if (getMuctramGiam() == 30000) {
            return "30,000";
        } else if (getMuctramGiam() == 35000) {
            return "35,000";
        } else if (getMuctramGiam() == 40000) {
            return "40,000";
        } else if (getMuctramGiam() == 45000) {
            return "45,000";
        } else if (getMuctramGiam() == 50000) {
            return "50,000";
        } else {
            return "0";
        }
    }

    @Override
    public String toString() {
        return this.tenKM;
    }

    public KhuyenMai(Double muctramGiam) {
        this.muctramGiam = muctramGiam;
    }

    public Object[] toRowDataTrangThai() {
        return new Object[]{giamGiaTT()};
    }

}
