/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

import com.g4.entity.NhanVien;
import com.g4.repository.INhanVienRepository;
import com.g4.utils.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Ddawng
 */
public class NhanVienRepository implements INhanVienRepository{

    @Override
    public List<NhanVien> getAllNV() {
        List<NhanVien> list = new ArrayList<>();
        try (Session session = HibernateUtil.getFACTORY().openSession()){
            String hql = "Select nv from nhanvien nv";
            Query query = session.createQuery(hql);
            list = query.getResultList();
            
        } catch (Exception e) {
        }
        
        return list;
    }
    
    
    
    
}
