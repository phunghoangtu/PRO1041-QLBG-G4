/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g4.repository.impl;

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
    
    public ChatLieu getByTenChatLieu(String ten){
        ChatLieu banmau = new ChatLieu();
        for(ChatLieu cl: getChatLieu()){
            if(ten.equalsIgnoreCase(cl.getTenchatlieu())){
                banmau =  cl;
                break;
            }
        }
        return banmau;
    }
    
    public void themChat(String tenMau) {
        try {
            Connection conn = JdbcHelper.openDbConnection();
            String caulenh = "insert into ChatLieuGiay(TenChatLieu, TrangThai)"
                                        + "values(?,1)";

            PreparedStatement stm = conn.prepareStatement(caulenh);

            stm.setString(1, tenMau);

            stm.executeQuery();

            stm.close();
            conn.close();
        } catch (Exception e) {
        }
    }

    public void suaChat(String tenMau, int IDmau) {
        try {
            Connection conn = JdbcHelper.openDbConnection();
            String caulenh = "update ChatLieuGiay set TenChatLieu = ? where Id = ?";

            PreparedStatement stm = conn.prepareStatement(caulenh);

            stm.setString(1, tenMau);
            stm.setInt(2, IDmau);

            stm.executeQuery();

            stm.close();
            conn.close();
        } catch (Exception e) {
        }
    }
    
    public void xoaChat(int IDmau) {
        try {
            Connection conn = JdbcHelper.openDbConnection();
            String caulenh = "update ChatLieuGiay set TrangThai = 0 where Id = ?";

            PreparedStatement stm = conn.prepareStatement(caulenh);

            stm.setInt(1, IDmau);

            stm.executeQuery();

            stm.close();
            conn.close();
        } catch (Exception e) {
        }
    }
}
