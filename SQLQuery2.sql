-- =============================================
-- Script tạo Database SQL Server cho dự án PY00304_Java5
-- Đã chuẩn hóa + FIX lỗi CREATE VIEW / PROCEDURE
-- =============================================

/* =========================
   TẠO DATABASE
   ========================= */
USE master;
GO

IF EXISTS (SELECT name FROM sys.databases WHERE name = 'PY00304_Java5')
BEGIN
    ALTER DATABASE PY00304_Java5 SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE PY00304_Java5;
END
GO

CREATE DATABASE PY00304_Java5;
GO

USE PY00304_Java5;
GO

/* =========================
   TẠO CÁC BẢNG (3NF)
   ========================= */

-- Airlines
CREATE TABLE Airlines (
    AirlineID INT IDENTITY(1,1) PRIMARY KEY,
    AirlineName NVARCHAR(100) NOT NULL UNIQUE,
    AirlineCode NVARCHAR(10) NOT NULL UNIQUE,
    Country NVARCHAR(50),
    CreatedDate DATETIME2 DEFAULT GETDATE(),
    IsActive BIT DEFAULT 1
);

-- Cities
CREATE TABLE Cities (
    CityID INT IDENTITY(1,1) PRIMARY KEY,
    CityName NVARCHAR(100) NOT NULL,
    CityCode NVARCHAR(10) NOT NULL UNIQUE,
    Country NVARCHAR(50) NOT NULL,
    TimeZone NVARCHAR(50),
    CreatedDate DATETIME2 DEFAULT GETDATE(),
    IsActive BIT DEFAULT 1
);

-- Airports
CREATE TABLE Airports (
    AirportID INT IDENTITY(1,1) PRIMARY KEY,
    AirportName NVARCHAR(200) NOT NULL,
    AirportCode NVARCHAR(10) NOT NULL UNIQUE,
    CityID INT NOT NULL,
    CreatedDate DATETIME2 DEFAULT GETDATE(),
    IsActive BIT DEFAULT 1,
    CONSTRAINT FK_Airports_Cities FOREIGN KEY (CityID) REFERENCES Cities(CityID)
);

-- Flights
CREATE TABLE Flights (
    FlightID INT PRIMARY KEY,
    AirlineID INT NOT NULL,
    DepartureCityID INT NOT NULL,
    ArrivalCityID INT NOT NULL,
    DepartureAirportID INT NULL,
    ArrivalAirportID INT NULL,
    DepartureTime DATETIME2 NOT NULL,
    ArrivalTime DATETIME2 NOT NULL,
    FlightNumber NVARCHAR(20),
    Status NVARCHAR(20) DEFAULT 'Scheduled',
    CreatedDate DATETIME2 DEFAULT GETDATE(),
    UpdatedDate DATETIME2 DEFAULT GETDATE(),

    CONSTRAINT FK_Flights_Airlines FOREIGN KEY (AirlineID) REFERENCES Airlines(AirlineID),
    CONSTRAINT FK_Flights_DepCity FOREIGN KEY (DepartureCityID) REFERENCES Cities(CityID),
    CONSTRAINT FK_Flights_ArrCity FOREIGN KEY (ArrivalCityID) REFERENCES Cities(CityID),
    CONSTRAINT FK_Flights_DepAirport FOREIGN KEY (DepartureAirportID) REFERENCES Airports(AirportID),
    CONSTRAINT FK_Flights_ArrAirport FOREIGN KEY (ArrivalAirportID) REFERENCES Airports(AirportID),

    CONSTRAINT CK_Flight_Time CHECK (ArrivalTime > DepartureTime),
    CONSTRAINT CK_Flight_City CHECK (DepartureCityID <> ArrivalCityID)
);
GO

/* =========================
   CREATE VIEW
   ========================= */
GO
CREATE VIEW FlightView
AS
SELECT 
    f.FlightID,
    a.AirlineName AS Airline,
    dc.CityName AS DepartureCity,
    ac.CityName AS ArrivalCity,
    f.DepartureTime,
    f.ArrivalTime,
    f.FlightNumber,
    f.Status
FROM Flights f
JOIN Airlines a ON f.AirlineID = a.AirlineID
JOIN Cities dc ON f.DepartureCityID = dc.CityID
JOIN Cities ac ON f.ArrivalCityID = ac.CityID;
GO

/* =========================
   CREATE INDEXES
   ========================= */
CREATE INDEX IX_Flights_DepartureTime ON Flights(DepartureTime);
CREATE INDEX IX_Flights_ArrivalTime ON Flights(ArrivalTime);
CREATE INDEX IX_Flights_AirlineID ON Flights(AirlineID);
CREATE INDEX IX_Flights_DepCity ON Flights(DepartureCityID);
CREATE INDEX IX_Flights_ArrCity ON Flights(ArrivalCityID);
CREATE INDEX IX_Airlines_Name ON Airlines(AirlineName);
CREATE INDEX IX_Cities_Name ON Cities(CityName);
GO

/* =========================
   STORED PROCEDURES
   ========================= */

-- Tìm chuyến bay theo hãng
GO
CREATE OR ALTER PROCEDURE sp_SearchFlightsByAirline
    @AirlineName NVARCHAR(100)
AS
BEGIN
    SELECT *
    FROM FlightView
    WHERE Airline LIKE '%' + @AirlineName + '%'
    ORDER BY DepartureTime;
END
GO

-- Thêm chuyến bay
GO
CREATE OR ALTER PROCEDURE sp_AddFlight
    @FlightID INT,
    @AirlineName NVARCHAR(100),
    @DepartureCity NVARCHAR(100),
    @ArrivalCity NVARCHAR(100),
    @DepartureTime DATETIME2,
    @ArrivalTime DATETIME2,
    @FlightNumber NVARCHAR(20) = NULL
AS
BEGIN
    DECLARE 
        @AirlineID INT,
        @DepartureCityID INT,
        @ArrivalCityID INT;

    -- Airline
    SELECT @AirlineID = AirlineID FROM Airlines WHERE AirlineName = @AirlineName;
    IF @AirlineID IS NULL
    BEGIN
        INSERT INTO Airlines (AirlineName, AirlineCode)
        VALUES (@AirlineName, LEFT(@AirlineName, 3));
        SET @AirlineID = SCOPE_IDENTITY();
    END

    -- Departure City
    SELECT @DepartureCityID = CityID FROM Cities WHERE CityName = @DepartureCity;
    IF @DepartureCityID IS NULL
    BEGIN
        INSERT INTO Cities (CityName, CityCode, Country)
        VALUES (@DepartureCity, LEFT(@DepartureCity, 3), 'Unknown');
        SET @DepartureCityID = SCOPE_IDENTITY();
    END

    -- Arrival City
    SELECT @ArrivalCityID = CityID FROM Cities WHERE CityName = @ArrivalCity;
    IF @ArrivalCityID IS NULL
    BEGIN
        INSERT INTO Cities (CityName, CityCode, Country)
        VALUES (@ArrivalCity, LEFT(@ArrivalCity, 3), 'Unknown');
        SET @ArrivalCityID = SCOPE_IDENTITY();
    END

    -- Insert Flight
    INSERT INTO Flights
        (FlightID, AirlineID, DepartureCityID, ArrivalCityID,
         DepartureTime, ArrivalTime, FlightNumber)
    VALUES
        (@FlightID, @AirlineID, @DepartureCityID, @ArrivalCityID,
         @DepartureTime, @ArrivalTime, @FlightNumber);
END
GO

PRINT N'Database PY00304_Java5 đã được tạo thành công – chạy không lỗi 🎉';


-- nhập dữ liệu
/* =============================================
   INSERT DATA: AIRLINES
   ============================================= */
INSERT INTO Airlines (AirlineName, AirlineCode, Country) VALUES
('Vietnam Airlines', 'VN', 'Vietnam'),
('Jetstar Pacific', 'BL', 'Vietnam'),
('VietJet Air', 'VJ', 'Vietnam'),
('Bamboo Airways', 'QH', 'Vietnam'),
('Singapore Airlines', 'SQ', 'Singapore'),
('Thai Airways', 'TG', 'Thailand'),
('Malaysia Airlines', 'MH', 'Malaysia'),
('Cathay Pacific', 'CX', 'Hong Kong'),
('Korean Air', 'KE', 'South Korea'),
('Japan Airlines', 'JL', 'Japan');
GO

/* =============================================
   INSERT DATA: CITIES
   ============================================= */
INSERT INTO Cities (CityName, CityCode, Country, TimeZone) VALUES
('Ho Chi Minh City', 'SGN', 'Vietnam', 'UTC+7'),
('Hanoi', 'HAN', 'Vietnam', 'UTC+7'),
('Da Nang', 'DAD', 'Vietnam', 'UTC+7'),
('Nha Trang', 'CXR', 'Vietnam', 'UTC+7'),
('Hue', 'HUI', 'Vietnam', 'UTC+7'),
('Can Tho', 'VCA', 'Vietnam', 'UTC+7'),
('Singapore', 'SIN', 'Singapore', 'UTC+8'),
('Bangkok', 'BKK', 'Thailand', 'UTC+7'),
('Kuala Lumpur', 'KUL', 'Malaysia', 'UTC+8'),
('Hong Kong', 'HKG', 'Hong Kong', 'UTC+8'),
('Seoul', 'ICN', 'South Korea', 'UTC+9'),
('Tokyo', 'NRT', 'Japan', 'UTC+9'),
('Manila', 'MNL', 'Philippines', 'UTC+8'),
('Jakarta', 'CGK', 'Indonesia', 'UTC+7'),
('Phnom Penh', 'PNH', 'Cambodia', 'UTC+7');
GO

/* =============================================
   INSERT DATA: AIRPORTS
   ============================================= */
INSERT INTO Airports (AirportName, AirportCode, CityID) VALUES
('Tan Son Nhat International Airport', 'SGN', 1),
('Noi Bai International Airport', 'HAN', 2),
('Da Nang International Airport', 'DAD', 3),
('Cam Ranh International Airport', 'CXR', 4),
('Phu Bai International Airport', 'HUI', 5),
('Can Tho International Airport', 'VCA', 6),
('Changi Airport', 'SIN', 7),
('Suvarnabhumi Airport', 'BKK', 8),
('Kuala Lumpur International Airport', 'KUL', 9),
('Hong Kong International Airport', 'HKG', 10),
('Incheon International Airport', 'ICN', 11),
('Narita International Airport', 'NRT', 12);
GO

/* =============================================
   INSERT DATA: FLIGHTS
   ============================================= */
INSERT INTO Flights
(FlightID, AirlineID, DepartureCityID, ArrivalCityID,
 DepartureAirportID, ArrivalAirportID,
 DepartureTime, ArrivalTime, FlightNumber, Status)
VALUES
(1001,1,1,2,1,2,'2024-02-15 06:00','2024-02-15 08:15','VN210','Scheduled'),
(1002,1,2,1,2,1,'2024-02-15 09:30','2024-02-15 11:45','VN211','Scheduled'),
(1003,2,1,3,1,3,'2024-02-15 07:20','2024-02-15 08:45','BL695','Scheduled'),
(1004,3,2,4,2,4,'2024-02-15 10:15','2024-02-15 11:35','VJ1543','Scheduled'),
(1005,4,1,5,1,5,'2024-02-15 14:30','2024-02-15 15:45','QH1065','Scheduled'),

(2001,1,1,7,1,7,'2024-02-15 08:45','2024-02-15 11:30','VN651','Scheduled'),
(2002,5,7,1,7,1,'2024-02-15 13:20','2024-02-15 16:05','SQ175','Scheduled'),
(2003,1,2,8,2,8,'2024-02-15 11:40','2024-02-15 13:25','VN563','Scheduled'),
(2004,6,8,2,8,2,'2024-02-15 15:50','2024-02-15 17:35','TG564','Scheduled'),
(2005,3,1,9,1,9,'2024-02-15 19:25','2024-02-15 22:40','VJ852','Scheduled'),

(3001,2,3,1,3,1,'2024-02-16 06:30','2024-02-16 07:55','BL696','Scheduled'),
(3002,4,2,3,2,3,'2024-02-16 12:15','2024-02-16 13:35','QH1205','Scheduled'),
(3003,1,1,10,1,10,'2024-02-16 23:30','2024-02-17 06:45','VN731','Scheduled'),
(3004,8,10,1,10,1,'2024-02-16 14:20','2024-02-16 21:35','CX766','Scheduled'),
(3005,3,4,1,4,1,'2024-02-16 16:40','2024-02-16 18:00','VJ1544','Scheduled'),

(4001,1,1,11,1,11,'2024-02-17 10:30','2024-02-17 16:45','VN451','Scheduled'),
(4002,9,11,1,11,1,'2024-02-17 18:20','2024-02-18 00:35','KE682','Scheduled'),
(4003,2,2,7,2,7,'2024-02-17 07:45','2024-02-17 10:30','BL3521','Scheduled'),
(4004,1,1,12,1,12,'2024-02-17 22:15','2024-02-18 06:30','VN301','Scheduled'),
(4005,10,12,1,12,1,'2024-02-18 08:50','2024-02-18 17:05','JL751','Scheduled');
GO

/* =============================================
   THỐNG KÊ – FIX Msg 1046
   ============================================= */
DECLARE
    @TotalAirlines INT,
    @TotalCities INT,
    @TotalAirports INT,
    @TotalFlights INT;

SELECT @TotalAirlines = COUNT(*) FROM Airlines;
SELECT @TotalCities = COUNT(*) FROM Cities;
SELECT @TotalAirports = COUNT(*) FROM Airports;
SELECT @TotalFlights = COUNT(*) FROM Flights;

PRINT N'Dữ liệu mẫu đã được chèn thành công!';
PRINT N'Tổng số Airlines: ' + CAST(@TotalAirlines AS NVARCHAR(10));
PRINT N'Tổng số Cities: ' + CAST(@TotalCities AS NVARCHAR(10));
PRINT N'Tổng số Airports: ' + CAST(@TotalAirports AS NVARCHAR(10));
PRINT N'Tổng số Flights: ' + CAST(@TotalFlights AS NVARCHAR(10));
GO
---
-- Xóa view cũ nếu tồn tại
IF OBJECT_ID('dbo.FlightCompatibilityView', 'V') IS NOT NULL
    DROP VIEW dbo.FlightCompatibilityView;
GO

-- =============================================
-- Tạo View tương thích hoàn toàn với Entity Flight
-- Mapping các cột để JPA có thể đọc được
-- =============================================
CREATE VIEW FlightCompatibilityView AS
SELECT 
    f.FlightID AS FlightID,
    a.AirlineName AS Airline,
    dc.CityName AS DepartureCity,
    ac.CityName AS ArrivalCity,
    f.DepartureTime AS DepartureTime,
    f.ArrivalTime AS ArrivalTime
FROM Flights f
INNER JOIN Airlines a ON f.AirlineID = a.AirlineID
INNER JOIN Cities dc ON f.DepartureCityID = dc.CityID
INNER JOIN Cities ac ON f.ArrivalCityID = ac.CityID
WHERE a.IsActive = 1 AND dc.IsActive = 1 AND ac.IsActive = 1;
GO

-- =============================================
-- Tạo Stored Procedure để JPA có thể gọi
-- =============================================

-- Procedure lấy tất cả flights
CREATE OR ALTER PROCEDURE sp_GetAllFlights
AS
BEGIN
    SELECT * FROM FlightCompatibilityView
    ORDER BY DepartureTime;
END
GO

-- Procedure tìm kiếm theo airline (tương thích với FlightDAO.findByAirlineContainingIgnoreCase)
CREATE OR ALTER PROCEDURE sp_FindFlightsByAirline
    @AirlineName NVARCHAR(100)
AS
BEGIN
    SELECT * FROM FlightCompatibilityView 
    WHERE UPPER(Airline) LIKE '%' + UPPER(@AirlineName) + '%'
    ORDER BY DepartureTime;
END
GO

-- Procedure lấy flight theo ID
CREATE OR ALTER PROCEDURE sp_GetFlightById
    @FlightID INT
AS
BEGIN
    SELECT * FROM FlightCompatibilityView 
    WHERE FlightID = @FlightID;
END
GO

-- =============================================
-- Tạo Function để hỗ trợ tìm kiếm
-- =============================================

-- Function đếm số chuyến bay theo hãng
CREATE OR ALTER FUNCTION fn_CountFlightsByAirline(@AirlineName NVARCHAR(100))
RETURNS INT
AS
BEGIN
    DECLARE @Count INT;
    SELECT @Count = COUNT(*) 
    FROM FlightCompatibilityView 
    WHERE UPPER(Airline) LIKE '%' + UPPER(@AirlineName) + '%';
    RETURN @Count;
END
GO

-- =============================================
-- Test các View và Procedures
-- =============================================

-- Test View
PRINT 'Testing FlightCompatibilityView:';
SELECT TOP 5 * FROM FlightCompatibilityView;

-- Test Procedure tìm kiếm
PRINT 'Testing search by airline (Vietnam):';
EXEC sp_FindFlightsByAirline 'Vietnam';

-- Test Function đếm
PRINT 'Count flights by VietJet: ' + CAST(dbo.fn_CountFlightsByAirline('VietJet') AS VARCHAR(10));

PRINT 'View và Procedures tương thích đã được tạo thành công!';