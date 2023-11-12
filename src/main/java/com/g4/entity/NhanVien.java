package com.g4.entity;

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
    private String email;
    private String sdt;
    private String matKhau;
    private boolean vaiTro;
    
}
