/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository;

import com.g4.entity.ChatLieu;
import com.g4.entity.MauSac;
import com.g4.utils.JdbcHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class ChatLieuReporitory {
    public List<ChatLieu> getChatLieu(){
        try {
            Connection conn = JdbcHelper.openDbConnection();
            List<ChatLieu> listmau = new ArrayList<>();
            String caulenh = "select * from ChatLieuGiay where TrangThai like 1";
        
            PreparedStatement stm = conn.prepareStatement(caulenh);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {                
                ChatLieu layMau  = new ChatLieu();
                layMau.setId(rs.getInt("Id"));
                layMau.setTenchatlieu(rs.getString("TenChatLieu"));
                layMau.setTrangthai(1);
                
                listmau.add(layMau);
            }
            rs.close();
            stm.close();
            conn.close();
            return listmau;
        } catch (Exception e) {
            return null;
        }
    }
    
    public ChatLieu getByID(int id){
        ChatLieu banmau = new ChatLieu();
        for(ChatLieu cl: getChatLieu()){
            if(id==cl.getId()){
                banmau =  cl;
                break;
            }
        }
        return banmau;
    }
}
