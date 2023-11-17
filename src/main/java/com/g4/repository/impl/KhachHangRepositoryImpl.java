
package com.g4.repository.impl;

import com.g4.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepositoryImpl extends JpaRepository<KhachHang, String>{
    
}
