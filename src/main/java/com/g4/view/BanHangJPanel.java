package com.g4.view;

import com.g4.entity.HoaDon;
import com.g4.entity.HoaDonChiTiet;
import com.g4.entity.SanPham;
import com.g4.main.Main;
import com.g4.repository.BanHangRepository;
import com.g4.utils.Auth;
import com.g4.utils.MsgBox;
import com.g4.viewmodel.GioHangViewModel;
import com.g4.viewmodel.HoaDonViewModel;
import com.g4.viewmodel.SanPhamViewModel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class BanHangJPanel extends javax.swing.JPanel {

    private BanHangRepository bhs = new BanHangRepository();

    DecimalFormat fomat = new DecimalFormat("###,###,### VND");

    private DefaultTableModel tblModelHoaDon = new DefaultTableModel();
    private DefaultTableModel tblModelGioHang = new DefaultTableModel();
    private DefaultTableModel tblModelSanPham = new DefaultTableModel();

    private DefaultComboBoxModel cbbModelHTTT = new DefaultComboBoxModel();

    private List<GioHangViewModel> listGH = new ArrayList<>();
    private List<SanPhamViewModel> listSP = new ArrayList<>();
    private List<HoaDonViewModel> listHD = new ArrayList<>();

    public BanHangJPanel() {
        initComponents();
        init();
    }

    void init() {
        listHD = bhs.getALLHD();
        loadHoaDon(listHD);
        listSP = bhs.getAllSP();
        loadSanPham(listSP);

        if (demTrangThai() > 4) {
            btnTaoHoaDon.setEnabled(false);
        }
    }

    public int demTrangThai() {
        int a = 0;
        for (HoaDonViewModel x : listHD) {
            if (x.getTrangThai() == 1) {
                x.getTrangThai();
                a++;
            }
        }
        return a;
    }

    public void loadGioHang(List<GioHangViewModel> listGioHangS) {
        tblModelGioHang = (DefaultTableModel) tbGioHang.getModel();
        tblModelGioHang.setRowCount(0);
        for (GioHangViewModel gh : listGioHangS) {
            tblModelGioHang.addRow(gh.todataRow());
        }
    }

    public void loadSanPham(List<SanPhamViewModel> listSanPhams) {
        tblModelSanPham = (DefaultTableModel) tbSanPham.getModel();
        tblModelSanPham.setRowCount(0);
        for (SanPhamViewModel sp : listSanPhams) {
            tblModelSanPham.addRow(sp.todataRowSanPham());
        }
    }

    public void loadHoaDon(List<HoaDonViewModel> listHoaDons) {
        tblModelHoaDon = (DefaultTableModel) tbHoaDon.getModel();
        tblModelHoaDon.setRowCount(0);
        for (HoaDonViewModel hd : listHoaDons) {
            tblModelHoaDon.addRow(hd.toRowDataHD());
        }
    }

    void insertHoaDon() {
        // Tạo hóa đơn
        if (demTrangThai() > 3) {
            btnTaoHoaDon.setEnabled(false);
        }
        // Dùng cả random + listSize để không bị trùng
        Random random = new Random();
        int x = random.nextInt(10);
        int i = listHD.size();
        i++;
        long millis = System.currentTimeMillis();
        String maHD = "HD" + x + i;
        HoaDonViewModel hd = new HoaDonViewModel();
        String id = Auth.user.getId();
        hd.setIdNV(id);
        hd.setIdKH(bhs.findByIDKH(lblMaKH.getText()));
        hd.setMaHD(maHD);
        hd.setTrangThai(1);
        //Lưu hóa đơn tạo vào bảng hóa đơn
        bhs.addHoaDon(hd);
        //Hóa đơn chờ
        listHD = bhs.getALLHD();
        loadHoaDon(listHD);
    }

    void fillInsertSanPhamGH() {
        GioHangViewModel gh = new GioHangViewModel();
        int rowHD = tbHoaDon.getSelectedRow();
        int row = tbSanPham.getSelectedRow();
        SanPhamViewModel sp = listSP.get(row);
        if (rowHD < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn muốn thêm sản phẩm");
        } else {
            String soLuong = JOptionPane.showInputDialog("Mời bạn nhập số lượng: ");

            if (soLuong != null) {
                if (!soLuong.matches("[0-9]+")) {
                    JOptionPane.showMessageDialog(this, "Nhập đúng định dạng");
                } else if (Integer.valueOf(soLuong) > sp.getSoLuong()) {
                    JOptionPane.showMessageDialog(this, "Số lượng vượt quá -.-");
                } else {
                    // Thêm sản phẩm vào giỏ hàng
                    HoaDonViewModel hd = listHD.get(rowHD);
                    gh.setSoLuong(Integer.valueOf(soLuong));
                    gh.setMaSP(sp.getMaSP());
                    gh.setTenSP(sp.getTenSP());
                    gh.setDonGia(sp.getGiaBan());
                    boolean trung = false;
                    for (GioHangViewModel x : listGH) {
                        if (x.getMaSP().contains(sp.getMaSP())) {
                            trung = true;
                        }
                    }
                    if (trung) {
                        JOptionPane.showMessageDialog(this, "Sản phẩm đã có trong giỏ hàng, để thêm số lượng vui lòng chọn chức năng cập nhật");
                    } else {
                        // Thêm sản phẩm vào list giỏ hàng
                        listGH.add(gh);

                        sp.setSoLuong(sp.getSoLuong() - Integer.valueOf(soLuong));
                        loadSanPham(listSP);

                        String idHD = hd.getId();
                        String idCtsp = sp.getId();
                        int soLuong1 = Integer.valueOf(soLuong);
                        Double donGia = sp.getGiaBan();

                        // add giỏ hàng vào HDCT
                        HoaDonChiTiet hdct = new HoaDonChiTiet(idHD, idCtsp, soLuong1, donGia);
                        JOptionPane.showMessageDialog(this, bhs.addHDCT(hdct));
                        listGH = bhs.getGioHang(idHD);
                        loadGioHang(listGH);

                        //Cập nhật số lượng trong bảng Sản phẩm CT
                        SanPham sp1 = new SanPham(sp.getSoLuong());
                        bhs.updateSoLuong(sp1, idCtsp);

                        //Fill thành tiền, thanh toán, giảm giá
                        double thanhTien = 0;
                        double thanhToan = 0;
                        double giamGia = 0;

                        for (GioHangViewModel gha : listGH) {
                            thanhTien += gha.getSoLuong() * gh.getDonGia();
                        }
                        lblThanhTien.setText(String.valueOf(fomat.format(thanhTien)));
                        lblThanhToan.setText(String.valueOf(fomat.format(thanhToan = thanhTien)));
                    }
                }
            }
        }
    }

    private void fillDataHD(int index) {
        //fill data hoa don
        HoaDonViewModel hd = listHD.get(index);
        lblMaHD.setText(hd.getMaHD());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
        lblMaKH.setText(hd.getIdKH());
        lblTenKhachHangNhanh.setText(hd.getTenKhachHang());
        lblNgayTao.setText(sdf.format(hd.getNgayTao()));

    }

    void fillHoaDonCho() {
        int index = tbHoaDon.getSelectedRow();
        HoaDonViewModel hd = listHD.get(index);
        String idHD = hd.getId();
        listGH = bhs.getGioHang(idHD);
        loadGioHang(listGH);
//        if (hd.getTrangThai() == 1) {
//            btnThanhToan.setEnabled(true);
//        } else {
//            btnThanhToan.setEnabled(false);
//        }
//        if (hd.getTrangThai() == 0 || hd.getTrangThai() == 3) {
//            btnHuyHoaDon.setEnabled(false);
//        } else {
//            btnHuyHoaDon.setEnabled(true);
//        }
        double thanhTien = 0;
        double thanhToan = 0;
        double giamGia = 0;

        for (GioHangViewModel gh : listGH) {
            thanhTien += gh.getSoLuong() * gh.getDonGia();
        }
        
        lblThanhTien.setText(String.valueOf(fomat.format(thanhTien)));
        lblThanhToan.setText(String.valueOf(fomat.format(thanhToan = thanhTien)));
        fillDataHD(index);

        txtTienKhachDua.setText("0");
        lblTienThua.setText("0");

        btnThanhToan.setEnabled(true);
        btnHuyHoaDon.setEnabled(true);
        btnLamMoi.setEnabled(true);
    }

    void updateSanPhamGioHang() {
        int indexHD = tbHoaDon.getSelectedRow();
        int indexGH = tbGioHang.getSelectedRow();
        if (indexGH < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần cập nhật số lượng");

        } else {
            String soLuongMoi = JOptionPane.showInputDialog("Mời nhập số lượng cần cập nhật: ");
            if (soLuongMoi != null) {
                if (!soLuongMoi.matches("[0-9]+")) {
                    JOptionPane.showMessageDialog(this, "Nhập đúng định dạng");
                } else {
                    GioHangViewModel gh = listGH.get(indexGH);
                    HoaDonViewModel hd = listHD.get(indexHD);
                    String idCTSP = gh.getIdSP();
                    String id = gh.getId();
                    String idHD = hd.getId();
                    int soLuongCu = gh.getSoLuong();
                    int soLuongCapNhat = 0;
                    if (Integer.valueOf(soLuongMoi) < soLuongCu) {
                        soLuongCapNhat = soLuongCu - Integer.valueOf(soLuongMoi);
                        SanPham ctsp = new SanPham(soLuongCapNhat);
                        bhs.capNhatSoLuong(ctsp, idCTSP);
                    } else {
                        soLuongCapNhat = Integer.valueOf(soLuongMoi) - soLuongCu;
                        SanPham ctsp = new SanPham(soLuongCapNhat);
                        bhs.capNhatSoLuong2(ctsp, idCTSP);
                    }
                    gh.setSoLuong(Integer.valueOf(soLuongMoi));
                    bhs.updateSoLuongHDCT(gh, id);
                    listGH = bhs.getGioHang(idHD);
                    loadGioHang(listGH);
                    listSP = bhs.getAllSP();
                    loadSanPham(listSP);
                    double thanhTien = 0;
                    double thanhToan = 0;
                    double giamGia = 0;
                  
                    for (GioHangViewModel gha : listGH) {
                        thanhTien += gha.getSoLuong() * gha.getDonGia();
                    }
         
                    lblThanhTien.setText(String.valueOf(fomat.format(thanhTien)));
                    lblThanhToan.setText(String.valueOf(fomat.format(thanhToan = thanhTien)));

                }
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbHoaDon = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbSanPham = new javax.swing.JTable();
        txtTimSP = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbGioHang = new javax.swing.JTable();
        btnXoaSanPham = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblMaKH = new javax.swing.JLabel();
        lblTenKhachHangNhanh = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblTenTienThua1 = new javax.swing.JLabel();
        lblTenTienKhachDua = new javax.swing.JLabel();
        lblTenTienThua = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lblMaHD = new javax.swing.JLabel();
        lblNgayTao = new javax.swing.JLabel();
        lblThanhTien = new javax.swing.JLabel();
        cbbKhuyemMai = new javax.swing.JComboBox<>();
        lblThanhToan = new javax.swing.JLabel();
        cbbHTTT = new javax.swing.JComboBox<>();
        txtTienKhachDua = new javax.swing.JTextField();
        btnTaoHoaDon = new javax.swing.JButton();
        lblTienThua = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        btnThanhToan = new javax.swing.JButton();
        btnHuyHoaDon = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn chờ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tbHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HĐ", "Ngày tạo", "Nhân viên tạo", "Khách hàng", "Tình trạng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbHoaDon);
        if (tbHoaDon.getColumnModel().getColumnCount() > 0) {
            tbHoaDon.getColumnModel().getColumn(0).setMinWidth(80);
            tbHoaDon.getColumnModel().getColumn(0).setMaxWidth(80);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tbSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên sản phẩm", "Kích thước", "Màu sắc", "Đế giày", "Thương hiệu", "Số lượng", "Giá bán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbSanPham);
        if (tbSanPham.getColumnModel().getColumnCount() > 0) {
            tbSanPham.getColumnModel().getColumn(0).setMinWidth(80);
            tbSanPham.getColumnModel().getColumn(0).setMaxWidth(80);
            tbSanPham.getColumnModel().getColumn(6).setMinWidth(60);
            tbSanPham.getColumnModel().getColumn(6).setMaxWidth(60);
        }

        jLabel2.setText("Tìm kiếm sản phẩm:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimSP, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane3)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTimSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Giỏ hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tbGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên sản phẩm", "Số lượng", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbGioHang);
        if (tbGioHang.getColumnModel().getColumnCount() > 0) {
            tbGioHang.getColumnModel().getColumn(0).setMinWidth(80);
            tbGioHang.getColumnModel().getColumn(0).setMaxWidth(80);
            tbGioHang.getColumnModel().getColumn(2).setMinWidth(60);
            tbGioHang.getColumnModel().getColumn(2).setMaxWidth(60);
            tbGioHang.getColumnModel().getColumn(3).setMinWidth(100);
            tbGioHang.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        btnXoaSanPham.setBackground(new java.awt.Color(255, 255, 102));
        btnXoaSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaSanPham.setText("Xóa sản phẩm");
        btnXoaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSanPhamActionPerformed(evt);
            }
        });

        btnCapNhat.setBackground(new java.awt.Color(255, 255, 102));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaSanPham, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(btnXoaSanPham)
                .addGap(18, 18, 18)
                .addComponent(btnCapNhat)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel1.setText("Mã khách hàng");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel3.setText("Tên khách hàng");

        lblMaKH.setText("KH00");

        lblTenKhachHangNhanh.setText("Khách vãng lai");

        jButton1.setText("Chọn");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1))
                .addGap(73, 73, 73)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenKhachHangNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(lblMaKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(98, 98, 98)))
                .addGap(27, 27, 27)
                .addComponent(jButton1)
                .addGap(17, 17, 17))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lblMaKH))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lblTenKhachHangNhanh)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Mã hóa đơn:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Ngày tạo");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Tổng tiền");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Giảm giá:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Thanh toán");

        lblTenTienThua1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTenTienThua1.setText("Hình thức thanh toán");

        lblTenTienKhachDua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTenTienKhachDua.setText("Tiền khách đưa");

        lblTenTienThua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTenTienThua.setText("Tiền thừa");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setText("Ghi chú:");

        lblMaHD.setText("HD++");

        lblNgayTao.setText("date");

        lblThanhTien.setForeground(new java.awt.Color(255, 0, 0));
        lblThanhTien.setText("0");

        cbbKhuyemMai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0" }));

        lblThanhToan.setForeground(new java.awt.Color(255, 0, 0));
        lblThanhToan.setText("0");

        cbbHTTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", "Chuyển khoản" }));
        cbbHTTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbHTTTActionPerformed(evt);
            }
        });

        txtTienKhachDua.setForeground(new java.awt.Color(255, 0, 0));

        btnTaoHoaDon.setBackground(new java.awt.Color(255, 255, 102));
        btnTaoHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTaoHoaDon.setText("Tạo hóa đơn");
        btnTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonActionPerformed(evt);
            }
        });

        lblTienThua.setForeground(new java.awt.Color(255, 0, 0));
        lblTienThua.setText("0");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane4.setViewportView(txtGhiChu);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(lblTenTienThua1)
                    .addComponent(lblTenTienKhachDua)
                    .addComponent(lblTenTienThua)
                    .addComponent(jLabel24))
                .addGap(36, 36, 36)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbKhuyemMai, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNgayTao)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(lblMaHD)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTaoHoaDon))
                        .addComponent(lblThanhTien, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblThanhToan, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cbbHTTT, javax.swing.GroupLayout.Alignment.LEADING, 0, 150, Short.MAX_VALUE)
                            .addComponent(txtTienKhachDua, javax.swing.GroupLayout.Alignment.LEADING))
                        .addComponent(lblTienThua, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblMaHD)
                    .addComponent(btnTaoHoaDon))
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblNgayTao))
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblThanhTien))
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbbKhuyemMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblThanhToan))
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenTienThua1)
                    .addComponent(cbbHTTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenTienKhachDua)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenTienThua)
                    .addComponent(lblTienThua))
                .addGap(32, 32, 32)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnThanhToan.setBackground(new java.awt.Color(255, 255, 102));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnHuyHoaDon.setBackground(new java.awt.Color(255, 255, 102));
        btnHuyHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHuyHoaDon.setText("Hủy hóa đơn");
        btnHuyHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyHoaDonActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(255, 255, 102));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(btnThanhToan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHuyHoaDon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLamMoi))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThanhToan)
                    .addComponent(btnHuyHoaDon)
                    .addComponent(btnLamMoi))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonActionPerformed
        insertHoaDon();
    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void tbSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSanPhamMouseClicked
        fillInsertSanPhamGH();
    }//GEN-LAST:event_tbSanPhamMouseClicked

    private void tbHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHoaDonMouseClicked
        fillHoaDonCho();
    }//GEN-LAST:event_tbHoaDonMouseClicked

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        updateSanPhamGioHang();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSanPhamActionPerformed

    }//GEN-LAST:event_btnXoaSanPhamActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed

    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void btnHuyHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyHoaDonActionPerformed

    }//GEN-LAST:event_btnHuyHoaDonActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed

    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Main m = new Main();
        m.openKhachHangNhanh();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbbHTTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbHTTTActionPerformed
       if (cbbHTTT.getSelectedItem().equals("Tiền mặt")) {
            
        }
        
    }//GEN-LAST:event_cbbHTTTActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnHuyHoaDon;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnXoaSanPham;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbHTTT;
    private javax.swing.JComboBox<String> cbbKhuyemMai;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblMaHD;
    private javax.swing.JLabel lblMaKH;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JLabel lblTenKhachHangNhanh;
    private javax.swing.JLabel lblTenTienKhachDua;
    private javax.swing.JLabel lblTenTienThua;
    private javax.swing.JLabel lblTenTienThua1;
    private javax.swing.JLabel lblThanhTien;
    private javax.swing.JLabel lblThanhToan;
    private javax.swing.JLabel lblTienThua;
    private javax.swing.JTable tbGioHang;
    private javax.swing.JTable tbHoaDon;
    private javax.swing.JTable tbSanPham;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTimSP;
    // End of variables declaration//GEN-END:variables
}
