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
public class KhachHang {

    private String id;
    private String maKH;
    private String tenKH;
    private String sdt;
    private Date ngayTao;

}
