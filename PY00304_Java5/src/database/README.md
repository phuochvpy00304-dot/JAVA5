# Database SQL Server cho dự án PY00304_Java5

## Tổng quan
Database này được thiết kế chuẩn hóa để hỗ trợ ứng dụng Spring Boot quản lý chuyến bay.

## Cấu trúc Database

### 1. Bảng chính (Normalized Tables)
- **Airlines**: Quản lý thông tin hãng hàng không
- **Cities**: Quản lý thông tin thành phố
- **Airports**: Quản lý thông tin sân bay
- **Flights**: Bảng chuyến bay chính (tương thích với Entity)

### 2. View tương thích
- **FlightCompatibilityView**: View mapping để tương thích với Entity Flight

### 3. Stored Procedures
- `sp_GetAllFlights`: Lấy tất cả chuyến bay
- `sp_FindFlightsByAirline`: Tìm kiếm theo hãng hàng không
- `sp_GetFlightById`: Lấy chuyến bay theo ID
- `sp_AddFlight`: Thêm chuyến bay mới

## Cách sử dụng

### Bước 1: Tạo Database
```sql
-- Chạy file create_database.sql trong SQL Server Management Studio
sqlcmd -S localhost -U sa -P 123 -i create_database.sql
```

### Bước 2: Chèn dữ liệu mẫu
```sql
-- Chạy file insert_sample_data.sql
sqlcmd -S localhost -U sa -P 123 -i insert_sample_data.sql
```

### Bước 3: Tạo View tương thích
```sql
-- Chạy file compatibility_view.sql
sqlcmd -S localhost -U sa -P 123 -i compatibility_view.sql
```

## Kết nối từ Spring Boot

Cấu hình trong `application.properties` đã sẵn sàng:
```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=PY00304_Java5;encrypt=false
spring.datasource.username=sa
spring.datasource.password=123
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
```

## Dữ liệu mẫu

Database bao gồm:
- 10 hãng hàng không (Vietnam Airlines, VietJet, Jetstar, v.v.)
- 15 thành phố (trong nước và quốc tế)
- 12 sân bay chính
- 25+ chuyến bay mẫu

## Tính năng nâng cao

### Indexes được tối ưu
- Index trên thời gian khởi hành/đến
- Index trên hãng hàng không
- Index trên thành phố

### Constraints
- Kiểm tra thời gian đến > thời gian khởi hành
- Kiểm tra thành phố khởi hành ≠ thành phố đến
- Unique constraints trên mã hãng, mã thành phố

### Audit Trail
- Tự động ghi nhận thời gian tạo/cập nhật
- Trạng thái active/inactive cho soft delete

## Troubleshooting

### Lỗi kết nối
1. Kiểm tra SQL Server đã chạy
2. Kiểm tra SQL Server Authentication được bật
3. Kiểm tra user 'sa' có quyền truy cập

### Lỗi Entity mapping
- Sử dụng FlightCompatibilityView thay vì bảng Flights trực tiếp
- Đảm bảo tên cột trong View khớp với Entity

## Mở rộng

Để thêm tính năng mới:
1. Thêm bảng mới vào normalized structure
2. Cập nhật View compatibility
3. Tạo stored procedures tương ứng
4. Cập nhật Entity nếu cần