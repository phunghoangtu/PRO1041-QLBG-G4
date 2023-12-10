CREATE DATABASE QLBG_DUAN1_JAVA
go
USE QLBG_DUAN1_JAVA
GO

CREATE TABLE NhanVien (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    TenNhanVien NVARCHAR(30) DEFAULT NULL,
    GioiTinh INT DEFAULT NULL,
    NgaySinh DATE DEFAULT NULL,
	SoDienThoai VARCHAR(30) DEFAULT NULL,
    DiaChi NVARCHAR(100) DEFAULT NULL,
    Email VARCHAR(50) DEFAULT NULL,
	NgayTao DATETIME2 DEFAULT GETDATE(), 
	MatKhau VARCHAR(64) DEFAULT NULL,
	VaiTro BIT DEFAULT 1, -- 1 nhân viên | 0 quản lý
    TrangThai INT DEFAULT 1,
);
CREATE TABLE CaLam (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    NgayGiaoCa DATE DEFAULT GETDATE(),
	CaTrongNgay INT DEFAULT NULL,
	GioBatDau VARCHAR(15) DEFAULT NULL,
	GioKetThuc VARCHAR(15) DEFAULT NULL,
);
CREATE TABLE GiaoCa (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    IdNhanVien INT DEFAULT NULL,
    IdCaLam INT DEFAULT NULL
    FOREIGN KEY (IdNhanVien) REFERENCES NhanVien(Id),
	FOREIGN KEY (IdCaLam) REFERENCES CaLam(Id)
);
CREATE TABLE KhachHang (
    Id INT IDENTITY(1,1) PRIMARY KEY,
	MaKhachHang VARCHAR(20) UNIQUE,
    TenKhachHang NVARCHAR(30) DEFAULT NULL,
    SoDienThoai VARCHAR(30) DEFAULT NULL,   
    NgayTao DATETIME2 DEFAULT GETDATE(),
    TrangThai INT DEFAULT 1
);
CREATE TABLE KhuyenMai(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	TenKhuyenMai NVARCHAR(30) DEFAULT NULL,
	MoTa TEXT DEFAULT NULL,
	KieuGiamGia BIT, -- 1 % | 0 VND
	MucGiamGia Float DEFAULT NULL,
	NgayBatDau DATE DEFAULT NULL,
	NgayKetThuc DATE DEFAULT NULL,
	TrangThai INT DEFAULT 1
);
CREATE TABLE HoaDon (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    IdNhanVien INT DEFAULT NULL,
	IdKhachHang INT DEFAULT NULL,
	IdKhuyenMai INT DEFAULT NULL,
    MaHD VARCHAR(20) UNIQUE,
    NgayTao DATETIME2 DEFAULT GETDATE(), 
	NgayThanhToan DATETIME2 DEFAULT NULL, 
	TongTien DECIMAL(20,0) DEFAULT NULL,
    GhiChu TEXT DEFAULT NULL,
	ChietKhau DECIMAL(5,2) DEFAULT NULL,
	HinhThucThanhToan BIT DEFAULT 1, -- 1 tiền mặt | 0 chuyển khoản
    TrangThai INT DEFAULT 1, -- 0 Hủy | 1 chưa thanh toán | 2 đã thanh toán
    FOREIGN KEY (IdNhanVien) REFERENCES NhanVien(Id), 
	FOREIGN KEY (IdKhachHang) REFERENCES KhachHang(Id),
	FOREIGN KEY (IdKhuyenMai) REFERENCES KhuyenMai(Id), 
);

CREATE TABLE ThuongHieu (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    TenThuongHieu NVARCHAR(30) DEFAULT NULL,
    TrangThai INT DEFAULT 1 
);

CREATE TABLE ChatLieuGiay (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    TenChatLieu NVARCHAR(30) DEFAULT NULL,
    TrangThai INT DEFAULT 1
);

CREATE TABLE MauSac (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    TenMauSac NVARCHAR(30) DEFAULT NULL,
    TrangThai INT DEFAULT 1
);

CREATE TABLE KichCoGiay (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    KichCo NVARCHAR(30) DEFAULT NULL,
    TrangThai INT DEFAULT 1
);

CREATE TABLE SanPham (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    IdKichCoGiay INT DEFAULT NULL,
    IdMauSac INT DEFAULT NULL,
    IdChatLieuGiay INT DEFAULT NULL,
    IdThuongHieu INT DEFAULT NULL,
    MaSanPham VARCHAR(20) UNIQUE,
    TenSanPham NVARCHAR(30) DEFAULT NULL,
    GiaBan DECIMAL(20,0) DEFAULT 0,  
    SoLuong INT DEFAULT 0,
    MoTa NVARCHAR(50) DEFAULT NULL,
    TrangThai INT DEFAULT 1,
    FOREIGN KEY (IdKichCoGiay) REFERENCES KichCoGiay(Id),
    FOREIGN KEY (IdMauSac) REFERENCES MauSac(Id),
    FOREIGN KEY (IdChatLieuGiay) REFERENCES ChatLieuGiay(Id),
    FOREIGN KEY (IdThuongHieu) REFERENCES ThuongHieu(Id)
);

CREATE TABLE HoaDonChiTiet (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    IdHoaDon INT DEFAULT NULL,
	IdSanPham INT DEFAULT NULL,
	SoLuong INT DEFAULT 0,
	DonGia DECIMAL(20,0) DEFAULT 0,
	FOREIGN KEY (IdSanPham) REFERENCES SanPham(Id),
	FOREIGN KEY (IdHoaDon) REFERENCES HoaDon(Id),
);

CREATE TABLE NhaCungCap (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    TenNhaCungCap NVARCHAR(30) DEFAULT NULL,
    DiaChi NVARCHAR(100) DEFAULT NULL,
    SoDienThoai VARCHAR(30) DEFAULT NULL,
    Email VARCHAR(50) DEFAULT NULL,
    TrangThai INT DEFAULT 1
);
CREATE TABLE NhapHang (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    IdNhaCungCap INT DEFAULT NULL,
    IdNhanVien INT DEFAULT NULL,
    NgayNhap DATETIME2 DEFAULT GETDATE(),
    TrangThai INT DEFAULT 1,
    FOREIGN KEY (IdNhaCungCap) REFERENCES NhaCungCap(Id),
    FOREIGN KEY (IdNhanVien) REFERENCES NhanVien(Id)
);
CREATE TABLE NhapHangChiTiet (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    IdNhapHang INT DEFAULT NULL,
    IdSanPham INT DEFAULT NULL,
    SoLuong INT DEFAULT 0,
    GiaNhap DECIMAL(20,0) DEFAULT 0,
    ThanhTien DECIMAL(20,0) DEFAULT 0,
    TrangThai INT DEFAULT 1,
    FOREIGN KEY (IdNhapHang) REFERENCES NhapHang(Id),
    FOREIGN KEY (IdSanPham) REFERENCES SanPham(Id)
);


-- INSERT dữ liệu

	INSERT INTO ThuongHieu(TenThuongHieu) VALUES ('Brand A'),('Brand B'),('Brand C'),('Brand D'),('Brand E');
	INSERT INTO ChatLieuGiay(TenChatLieu) VALUES ('Type A'),('Type B'),('Type C'),('Type D'),('Type E');
	INSERT INTO MauSac (TenMauSac) VALUES ('Color A'),('Color B'),('Color C'),('Color D'),('Color E');
	INSERT INTO KichCoGiay (KichCo) VALUES ('36'),('37'),('38'),('39'),('40');

	INSERT INTO SanPham (IdKichCoGiay, IdMauSac, IdChatLieuGiay, IdThuongHieu, MaSanPham, TenSanPham, GiaBan, SoLuong, MoTa) VALUES
				('1', '1', '1', '1', 'SP001', 'Product 1', 100000, 50, 'Product 1 description'),
				('2', '2', '2', '2', 'SP002', 'Product 2', 150000, 30, 'Product 2 description'),  
				('3', '3', '3', '3', 'SP005', 'Product 5', 300000, 10, 'Product 5 description');

	INSERT INTO NhanVien (TenNhanVien, GioiTinh, NgaySinh, SoDienThoai, DiaChi, Email, MatKhau, VaiTro) VALUES
    ('Employee 1', 1, '1990-01-01', '123456789', 'Address 1', 'sa', '123456', 0),
	('Employee 4', 1, '1990-01-01', '123456789', 'Address 1', 'sa1', '123456', 1),
    ('Employee 2', 0, '1995-02-02', '987654321', 'Address 2', 'employee2@example.com', HASHBYTES('SHA2_256', 'password2'), 1),
    ('Employee 3', 1, '1992-03-03', '456789123', 'Address 3', 'employee3@example.com', HASHBYTES('SHA2_256', 'password3'), 0);


	INSERT INTO CaLam (NgayGiaoCa, CaTrongNgay, GioBatDau, GioKetThuc) VALUES  (GETDATE(), 1, '08:00', '12:00'), (GETDATE(), 2, '12:00', '16:00'), (GETDATE(), 3, '16:00', '22:00');

	INSERT INTO GiaoCa (IdNhanVien, IdCaLam) VALUES    ('1', '1'), ('2', '2'), ('3', '3');

	INSERT INTO NhaCungCap (TenNhaCungCap, DiaChi, SoDienThoai, Email) VALUES
    ('Supplier 1', 'Address 1', '123456789', 'supplier1@example.com'),
    ('Supplier 2', 'Address 2', '987654321', 'supplier2@example.com'),
    ('Supplier 3', 'Address 3', '456789123', 'supplier3@example.com'),
    ('Supplier 4', 'Address 4', '321654987', 'supplier4@example.com'),
    ('Supplier 5', 'Address 5', '789123456', 'supplier5@example.com');

	INSERT INTO NhapHang (IdNhaCungCap, IdNhanVien, NgayNhap) VALUES  ('1', '1', GETDATE()), ('2', '2', GETDATE()), ('3', '3', GETDATE());

	INSERT INTO NhapHangChiTiet (IdNhapHang, IdSanPham, SoLuong, GiaNhap, ThanhTien) VALUES
    ('1', '1', 10, 1000, 10000),
    ('1', '1', 5, 500, 2500),
    ('1', '2', 8, 800, 6400);

	INSERT INTO KhuyenMai (TenKhuyenMai, MoTa, MucGiamGia,KieuGiamGia, NgayBatDau, NgayKetThuc) VALUES
    (N'Lễ giáng sinh', N'Khuyến mãi 3', 0.95 , 1 , '2023-03-01', '2023-03-31');

	INSERT INTO KhachHang(MaKhachHang,TenKhachHang,SoDienThoai) VALUES
    ('KH00', N'Khách vãng lai','12321456'),
    ('KH01',  N'Khách hàng 1','3123123214'),
    ('KH02', N'Khách hàng 2','1231232114');
	

