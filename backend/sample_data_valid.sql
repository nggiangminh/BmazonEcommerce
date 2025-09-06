-- =============================================
-- ECOMMERCE BACKEND - SAMPLE DATA (VALID UUIDs)
-- =============================================
-- File chứa các câu lệnh SQL để tạo dữ liệu mẫu cho việc test
-- Database: PostgreSQL
-- =============================================

-- Xóa dữ liệu cũ (nếu có) - theo thứ tự từ bảng con đến bảng cha
DELETE FROM cart_item;
DELETE FROM cart;
DELETE FROM order_item;
DELETE FROM order_details;
DELETE FROM payment_details;
DELETE FROM wishlist;
DELETE FROM addresses;
DELETE FROM products_skus;
DELETE FROM product_attributes;
DELETE FROM products;
DELETE FROM sub_categories;
DELETE FROM categories;
DELETE FROM users;

-- =============================================
-- 1. INSERT USERS
-- =============================================
INSERT INTO users (id, avatar, first_name, last_name, username, email, password, birth_of_date, phone_number, role, created_at) VALUES
('550e8400-e29b-41d4-a716-446655440001', 'https://example.com/avatar1.jpg', 'Nguyễn', 'Văn An', 'nguyenvanan', 'nguyenvanan@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '1990-05-15', '0123456789', 'USER', NOW()),
('550e8400-e29b-41d4-a716-446655440002', 'https://example.com/avatar2.jpg', 'Trần', 'Thị Bình', 'tranthibinh', 'tranthibinh@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '1992-08-20', '0987654321', 'USER', NOW()),
('550e8400-e29b-41d4-a716-446655440003', 'https://example.com/avatar3.jpg', 'Lê', 'Minh Cường', 'leminhcuong', 'leminhcuong@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '1988-12-10', '0369852147', 'ADMIN', NOW()),
('550e8400-e29b-41d4-a716-446655440004', 'https://example.com/avatar4.jpg', 'Phạm', 'Thị Dung', 'phamthidung', 'phamthidung@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '1995-03-25', '0147258369', 'USER', NOW()),
('550e8400-e29b-41d4-a716-446655440005', 'https://example.com/avatar5.jpg', 'Hoàng', 'Văn Em', 'hoangvanem', 'hoangvanem@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '1993-07-18', '0258741963', 'USER', NOW());

-- =============================================
-- 2. INSERT CATEGORIES
-- =============================================
INSERT INTO categories (id, name, description, created_at) VALUES
('650e8400-e29b-41d4-a716-446655440001', 'Thời Trang Nam', 'Quần áo, giày dép, phụ kiện dành cho nam giới', NOW()),
('650e8400-e29b-41d4-a716-446655440002', 'Thời Trang Nữ', 'Quần áo, giày dép, phụ kiện dành cho nữ giới', NOW()),
('650e8400-e29b-41d4-a716-446655440003', 'Điện Tử', 'Điện thoại, laptop, máy tính bảng, phụ kiện điện tử', NOW()),
('650e8400-e29b-41d4-a716-446655440004', 'Gia Dụng', 'Đồ dùng nhà bếp, nội thất, trang trí nhà cửa', NOW()),
('650e8400-e29b-41d4-a716-446655440005', 'Thể Thao', 'Quần áo thể thao, dụng cụ tập luyện, giày thể thao', NOW());

-- =============================================
-- 3. INSERT SUB CATEGORIES
-- =============================================
INSERT INTO sub_categories (id, parent_id, name, description, created_at) VALUES
('750e8400-e29b-41d4-a716-446655440001', '650e8400-e29b-41d4-a716-446655440001', 'Áo Sơ Mi', 'Áo sơ mi nam các loại', NOW()),
('750e8400-e29b-41d4-a716-446655440002', '650e8400-e29b-41d4-a716-446655440001', 'Quần Jean', 'Quần jean nam các kiểu dáng', NOW()),
('750e8400-e29b-41d4-a716-446655440003', '650e8400-e29b-41d4-a716-446655440002', 'Váy', 'Váy nữ các phong cách', NOW()),
('750e8400-e29b-41d4-a716-446655440004', '650e8400-e29b-41d4-a716-446655440002', 'Áo Khoác', 'Áo khoác nữ thời trang', NOW()),
('750e8400-e29b-41d4-a716-446655440005', '650e8400-e29b-41d4-a716-446655440003', 'Điện Thoại', 'Điện thoại thông minh các hãng', NOW()),
('750e8400-e29b-41d4-a716-446655440006', '650e8400-e29b-41d4-a716-446655440003', 'Laptop', 'Laptop gaming, văn phòng', NOW());

-- =============================================
-- 4. INSERT PRODUCTS
-- =============================================
INSERT INTO products (id, name, description, summary, cover, category_id, created_at) VALUES
('850e8400-e29b-41d4-a716-446655440001', 'Áo Sơ Mi Trắng Nam', 'Áo sơ mi trắng chất liệu cotton cao cấp, form dáng chuẩn', 'Áo sơ mi trắng nam chất lượng cao', 'https://example.com/products/shirt1.jpg', 1, NOW()),
('850e8400-e29b-41d4-a716-446655440002', 'Quần Jean Xanh Nam', 'Quần jean xanh đậm kiểu dáng slim fit, chất liệu denim', 'Quần jean nam phong cách', 'https://example.com/products/jean1.jpg', 1, NOW()),
('850e8400-e29b-41d4-a716-446655440003', 'Váy Hoa Nữ', 'Váy hoa nữ phong cách vintage, chất liệu voan mềm mại', 'Váy hoa nữ thời trang', 'https://example.com/products/dress1.jpg', 2, NOW()),
('850e8400-e29b-41d4-a716-446655440004', 'iPhone 15 Pro', 'iPhone 15 Pro với chip A17 Pro, camera 48MP, màn hình 6.1 inch', 'iPhone 15 Pro mới nhất', 'https://example.com/products/iphone15.jpg', 3, NOW()),
('850e8400-e29b-41d4-a716-446655440005', 'MacBook Air M2', 'MacBook Air với chip M2, màn hình 13 inch, 8GB RAM, 256GB SSD', 'MacBook Air M2 hiệu năng cao', 'https://example.com/products/macbook.jpg', 3, NOW()),
('850e8400-e29b-41d4-a716-446655440006', 'Giày Thể Thao Nike', 'Giày thể thao Nike Air Max, đế êm ái, thiết kế thời trang', 'Giày Nike thể thao', 'https://example.com/products/nike.jpg', 5, NOW());

-- =============================================
-- 5. INSERT PRODUCT ATTRIBUTES
-- =============================================
INSERT INTO product_attributes (id, type, value, created_at) VALUES
-- Size attributes
('950e8400-e29b-41d4-a716-446655440001', 'size', 'S', NOW()),
('950e8400-e29b-41d4-a716-446655440002', 'size', 'M', NOW()),
('950e8400-e29b-41d4-a716-446655440003', 'size', 'L', NOW()),
('950e8400-e29b-41d4-a716-446655440004', 'size', 'XL', NOW()),
('950e8400-e29b-41d4-a716-446655440005', 'size', '28', NOW()),
('950e8400-e29b-41d4-a716-446655440006', 'size', '30', NOW()),
('950e8400-e29b-41d4-a716-446655440007', 'size', '32', NOW()),
('950e8400-e29b-41d4-a716-446655440008', 'size', '34', NOW()),
('950e8400-e29b-41d4-a716-446655440009', 'size', '40', NOW()),
('950e8400-e29b-41d4-a716-446655440010', 'size', '41', NOW()),
('950e8400-e29b-41d4-a716-446655440011', 'size', '42', NOW()),
('950e8400-e29b-41d4-a716-446655440012', 'size', '43', NOW()),
-- Color attributes
('950e8400-e29b-41d4-a716-446655440013', 'color', 'Trắng', NOW()),
('950e8400-e29b-41d4-a716-446655440014', 'color', 'Đen', NOW()),
('950e8400-e29b-41d4-a716-446655440015', 'color', 'Xanh Đậm', NOW()),
('950e8400-e29b-41d4-a716-446655440016', 'color', 'Xanh Nhạt', NOW()),
('950e8400-e29b-41d4-a716-446655440017', 'color', 'Đỏ', NOW()),
('950e8400-e29b-41d4-a716-446655440018', 'color', 'Hoa Nhí', NOW()),
('950e8400-e29b-41d4-a716-446655440019', 'color', 'Titan Xanh', NOW()),
('950e8400-e29b-41d4-a716-446655440020', 'color', 'Titan Tự Nhiên', NOW()),
('950e8400-e29b-41d4-a716-446655440021', 'color', 'Bạc', NOW()),
('950e8400-e29b-41d4-a716-446655440022', 'color', 'Xám', NOW());

-- =============================================
-- 6. INSERT PRODUCT SKUS
-- =============================================
INSERT INTO products_skus (id, product_id, size_attribute_id, color_attribute_id, sku, price, quantity, created_at) VALUES
-- Áo sơ mi trắng nam - các size
('a50e8400-e29b-41d4-a716-446655440001', '850e8400-e29b-41d4-a716-446655440001', '950e8400-e29b-41d4-a716-446655440001', '950e8400-e29b-41d4-a716-446655440013', 'SHIRT-WHITE-S', 299000, 50, NOW()),
('a50e8400-e29b-41d4-a716-446655440002', '850e8400-e29b-41d4-a716-446655440001', '950e8400-e29b-41d4-a716-446655440002', '950e8400-e29b-41d4-a716-446655440013', 'SHIRT-WHITE-M', 299000, 45, NOW()),
('a50e8400-e29b-41d4-a716-446655440003', '850e8400-e29b-41d4-a716-446655440001', '950e8400-e29b-41d4-a716-446655440003', '950e8400-e29b-41d4-a716-446655440013', 'SHIRT-WHITE-L', 299000, 40, NOW()),
('a50e8400-e29b-41d4-a716-446655440004', '850e8400-e29b-41d4-a716-446655440001', '950e8400-e29b-41d4-a716-446655440004', '950e8400-e29b-41d4-a716-446655440013', 'SHIRT-WHITE-XL', 299000, 35, NOW()),

-- Quần jean xanh nam - các size
('a50e8400-e29b-41d4-a716-446655440005', '850e8400-e29b-41d4-a716-446655440002', '950e8400-e29b-41d4-a716-446655440005', '950e8400-e29b-41d4-a716-446655440015', 'JEAN-BLUE-28', 599000, 30, NOW()),
('a50e8400-e29b-41d4-a716-446655440006', '850e8400-e29b-41d4-a716-446655440002', '950e8400-e29b-41d4-a716-446655440006', '950e8400-e29b-41d4-a716-446655440015', 'JEAN-BLUE-30', 599000, 35, NOW()),
('a50e8400-e29b-41d4-a716-446655440007', '850e8400-e29b-41d4-a716-446655440002', '950e8400-e29b-41d4-a716-446655440007', '950e8400-e29b-41d4-a716-446655440015', 'JEAN-BLUE-32', 599000, 25, NOW()),
('a50e8400-e29b-41d4-a716-446655440008', '850e8400-e29b-41d4-a716-446655440002', '950e8400-e29b-41d4-a716-446655440008', '950e8400-e29b-41d4-a716-446655440015', 'JEAN-BLUE-34', 599000, 20, NOW()),

-- Váy hoa nữ - các size
('a50e8400-e29b-41d4-a716-446655440009', '850e8400-e29b-41d4-a716-446655440003', '950e8400-e29b-41d4-a716-446655440001', '950e8400-e29b-41d4-a716-446655440018', 'DRESS-FLOWER-S', 399000, 20, NOW()),
('a50e8400-e29b-41d4-a716-446655440010', '850e8400-e29b-41d4-a716-446655440003', '950e8400-e29b-41d4-a716-446655440002', '950e8400-e29b-41d4-a716-446655440018', 'DRESS-FLOWER-M', 399000, 25, NOW()),
('a50e8400-e29b-41d4-a716-446655440011', '850e8400-e29b-41d4-a716-446655440003', '950e8400-e29b-41d4-a716-446655440003', '950e8400-e29b-41d4-a716-446655440018', 'DRESS-FLOWER-L', 399000, 15, NOW()),

-- iPhone 15 Pro - các màu
('a50e8400-e29b-41d4-a716-446655440012', '850e8400-e29b-41d4-a716-446655440004', NULL, '950e8400-e29b-41d4-a716-446655440019', 'IPHONE15-BLUE', 29990000, 10, NOW()),
('a50e8400-e29b-41d4-a716-446655440013', '850e8400-e29b-41d4-a716-446655440004', NULL, '950e8400-e29b-41d4-a716-446655440020', 'IPHONE15-NATURAL', 29990000, 8, NOW()),

-- MacBook Air M2 - các màu
('a50e8400-e29b-41d4-a716-446655440014', '850e8400-e29b-41d4-a716-446655440005', NULL, '950e8400-e29b-41d4-a716-446655440021', 'MACBOOK-SILVER', 25990000, 5, NOW()),
('a50e8400-e29b-41d4-a716-446655440015', '850e8400-e29b-41d4-a716-446655440005', NULL, '950e8400-e29b-41d4-a716-446655440022', 'MACBOOK-GRAY', 25990000, 7, NOW()),

-- Giày Nike - các size và màu
('a50e8400-e29b-41d4-a716-446655440016', '850e8400-e29b-41d4-a716-446655440006', '950e8400-e29b-41d4-a716-446655440009', '950e8400-e29b-41d4-a716-446655440014', 'NIKE-BLACK-40', 2490000, 15, NOW()),
('a50e8400-e29b-41d4-a716-446655440017', '850e8400-e29b-41d4-a716-446655440006', '950e8400-e29b-41d4-a716-446655440010', '950e8400-e29b-41d4-a716-446655440014', 'NIKE-BLACK-41', 2490000, 20, NOW()),
('a50e8400-e29b-41d4-a716-446655440018', '850e8400-e29b-41d4-a716-446655440006', '950e8400-e29b-41d4-a716-446655440011', '950e8400-e29b-41d4-a716-446655440013', 'NIKE-WHITE-42', 2490000, 18, NOW()),
('a50e8400-e29b-41d4-a716-446655440019', '850e8400-e29b-41d4-a716-446655440006', '950e8400-e29b-41d4-a716-446655440012', '950e8400-e29b-41d4-a716-446655440013', 'NIKE-WHITE-43', 2490000, 12, NOW());

-- =============================================
-- 7. INSERT ADDRESSES
-- =============================================
INSERT INTO addresses (id, user_id, title, address_line_1, address_line_2, country, city, postal_code, landmark, phone_number, created_at) VALUES
('b50e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440001', 'Nhà riêng', '123 Đường Lê Lợi', 'Phường Bến Nghé', 'Việt Nam', 'TP. Hồ Chí Minh', '700000', 'Gần chợ Bến Thành', '0123456789', NOW()),
('b50e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440001', 'Văn phòng', '456 Đường Nguyễn Huệ', 'Phường Bến Nghé', 'Việt Nam', 'TP. Hồ Chí Minh', '700000', 'Tòa nhà Landmark', '0123456789', NOW()),
('b50e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440002', 'Nhà riêng', '789 Đường Trần Hưng Đạo', 'Phường Cầu Kho', 'Việt Nam', 'TP. Hồ Chí Minh', '700000', 'Gần chợ Tân Bình', '0987654321', NOW()),
('b50e8400-e29b-41d4-a716-446655440004', '550e8400-e29b-41d4-a716-446655440003', 'Nhà riêng', '321 Đường Lý Tự Trọng', 'Phường Bến Thành', 'Việt Nam', 'TP. Hồ Chí Minh', '700000', 'Gần Diamond Plaza', '0369852147', NOW()),
('b50e8400-e29b-41d4-a716-446655440005', '550e8400-e29b-41d4-a716-446655440004', 'Nhà riêng', '654 Đường Đồng Khởi', 'Phường Bến Nghé', 'Việt Nam', 'TP. Hồ Chí Minh', '700000', 'Gần Nhà hát Thành phố', '0147258369', NOW());

-- =============================================
-- 8. INSERT CART
-- =============================================
INSERT INTO cart (id, user_id, total, created_at, updated_at) VALUES
('c50e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440001', 898000, NOW(), NOW()),
('c50e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440002', 1198000, NOW(), NOW()),
('c50e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440004', 2490000, NOW(), NOW());

-- =============================================
-- 9. INSERT CART ITEMS
-- =============================================
INSERT INTO cart_item (id, cart_id, product_id, products_sku_id, quantity, created_at, updated_at) VALUES
('d50e8400-e29b-41d4-a716-446655440001', 'c50e8400-e29b-41d4-a716-446655440001', '850e8400-e29b-41d4-a716-446655440001', 'a50e8400-e29b-41d4-a716-446655440001', 2, NOW(), NOW()),
('d50e8400-e29b-41d4-a716-446655440002', 'c50e8400-e29b-41d4-a716-446655440001', '850e8400-e29b-41d4-a716-446655440002', 'a50e8400-e29b-41d4-a716-446655440005', 1, NOW(), NOW()),
('d50e8400-e29b-41d4-a716-446655440003', 'c50e8400-e29b-41d4-a716-446655440002', '850e8400-e29b-41d4-a716-446655440003', 'a50e8400-e29b-41d4-a716-446655440009', 2, NOW(), NOW()),
('d50e8400-e29b-41d4-a716-446655440004', 'c50e8400-e29b-41d4-a716-446655440002', '850e8400-e29b-41d4-a716-446655440003', 'a50e8400-e29b-41d4-a716-446655440010', 1, NOW(), NOW()),
('d50e8400-e29b-41d4-a716-446655440005', 'c50e8400-e29b-41d4-a716-446655440003', '850e8400-e29b-41d4-a716-446655440006', 'a50e8400-e29b-41d4-a716-446655440016', 1, NOW(), NOW());

-- =============================================
-- 10. INSERT WISHLIST
-- =============================================
INSERT INTO wishlist (id, user_id, product_id, created_at) VALUES
('e50e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440001', '850e8400-e29b-41d4-a716-446655440004', NOW()),
('e50e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440001', '850e8400-e29b-41d4-a716-446655440005', NOW()),
('e50e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440002', '850e8400-e29b-41d4-a716-446655440003', NOW()),
('e50e8400-e29b-41d4-a716-446655440004', '550e8400-e29b-41d4-a716-446655440002', '850e8400-e29b-41d4-a716-446655440006', NOW()),
('e50e8400-e29b-41d4-a716-446655440005', '550e8400-e29b-41d4-a716-446655440004', '850e8400-e29b-41d4-a716-446655440001', NOW());

-- =============================================
-- 11. INSERT PAYMENT DETAILS
-- =============================================
INSERT INTO payment_details (id, amount, provider, status, created_at, updated_at) VALUES
('f50e8400-e29b-41d4-a716-446655440001', 898000, 'VNPAY', 'COMPLETED', NOW(), NOW()),
('f50e8400-e29b-41d4-a716-446655440002', 1198000, 'MOMO', 'COMPLETED', NOW(), NOW()),
('f50e8400-e29b-41d4-a716-446655440003', 2490000, 'BANK_TRANSFER', 'PENDING', NOW(), NOW());

-- =============================================
-- 12. INSERT ORDER DETAILS
-- =============================================
INSERT INTO order_details (id, user_id, payment_id, total, created_at, updated_at) VALUES
('1a0e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440001', 'f50e8400-e29b-41d4-a716-446655440001', 898000, NOW(), NOW()),
('1a0e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440002', 'f50e8400-e29b-41d4-a716-446655440002', 1198000, NOW(), NOW()),
('1a0e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440004', 'f50e8400-e29b-41d4-a716-446655440003', 2490000, NOW(), NOW());

-- =============================================
-- 13. INSERT ORDER ITEMS
-- =============================================
INSERT INTO order_item (id, order_id, product_id, products_sku_id, quantity, created_at, updated_at) VALUES
('2b0e8400-e29b-41d4-a716-446655440001', '1a0e8400-e29b-41d4-a716-446655440001', '850e8400-e29b-41d4-a716-446655440001', 'a50e8400-e29b-41d4-a716-446655440001', 2, NOW(), NOW()),
('2b0e8400-e29b-41d4-a716-446655440002', '1a0e8400-e29b-41d4-a716-446655440001', '850e8400-e29b-41d4-a716-446655440002', 'a50e8400-e29b-41d4-a716-446655440005', 1, NOW(), NOW()),
('2b0e8400-e29b-41d4-a716-446655440003', '1a0e8400-e29b-41d4-a716-446655440002', '850e8400-e29b-41d4-a716-446655440003', 'a50e8400-e29b-41d4-a716-446655440009', 2, NOW(), NOW()),
('2b0e8400-e29b-41d4-a716-446655440004', '1a0e8400-e29b-41d4-a716-446655440002', '850e8400-e29b-41d4-a716-446655440003', 'a50e8400-e29b-41d4-a716-446655440010', 1, NOW(), NOW()),
('2b0e8400-e29b-41d4-a716-446655440005', '1a0e8400-e29b-41d4-a716-446655440003', '850e8400-e29b-41d4-a716-446655440006', 'a50e8400-e29b-41d4-a716-446655440016', 1, NOW(), NOW());

-- =============================================
-- THÔNG TIN BỔ SUNG
-- =============================================
-- Password mặc định cho tất cả user: "password123" (đã được hash bằng BCrypt)
-- 
-- Dữ liệu bao gồm:
-- - 5 users (4 USER, 1 ADMIN)
-- - 5 categories với 6 sub-categories
-- - 6 products với 22 product attributes và 19 product SKUs
-- - 5 addresses cho users
-- - 3 carts với 5 cart items
-- - 5 wishlist items
-- - 3 payment details với các trạng thái khác nhau
-- - 3 orders với 5 order items
--
-- Các UUID được tạo theo pattern để dễ nhận biết (CHỈ CHỨA 0-9 và a-f):
-- - Users: 550e8400-e29b-41d4-a716-446655440xxx
-- - Categories: 650e8400-e29b-41d4-a716-446655440xxx
-- - Sub-categories: 750e8400-e29b-41d4-a716-446655440xxx
-- - Products: 850e8400-e29b-41d4-a716-446655440xxx
-- - Product Attributes: 950e8400-e29b-41d4-a716-446655440xxx
-- - Product SKUs: a50e8400-e29b-41d4-a716-446655440xxx
-- - Addresses: b50e8400-e29b-41d4-a716-446655440xxx
-- - Cart: c50e8400-e29b-41d4-a716-446655440xxx
-- - Cart Items: d50e8400-e29b-41d4-a716-446655440xxx
-- - Wishlist: e50e8400-e29b-41d4-a716-446655440xxx
-- - Payment Details: f50e8400-e29b-41d4-a716-446655440xxx
-- - Order Details: 1a0e8400-e29b-41d4-a716-446655440xxx
-- - Order Items: 2b0e8400-e29b-41d4-a716-446655440xxx
-- =============================================
