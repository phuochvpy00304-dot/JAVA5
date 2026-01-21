-- =============================================
-- Script tạo Database SQL Server cho dự án PY00304_Java5
-- =============================================

-- Tạo database
USE master;
GO

-- Xóa database nếu đã tồn tại
IF EXISTS (SELECT name FROM sys.databases WHERE name = 'PY00304_Java5')
BEGIN
    ALTER DATABASE PY00304_Java5 SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE PY00304_Java5;
END
GO

-- Tạo database mới
CREATE DATABASE PY00304_Java5
ON 
( NAME = 'PY00304_Java5_Data',
  FILENAME = 'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\PY00304_Java5.mdf',
  SIZE = 100MB,
  MAXSIZE = 1GB,
  FILEGROWTH = 10MB )
LOG ON 
( NAME = 'PY00304_Java5_Log',
  FILENAME = 'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\PY00304_Java5.ldf',
  SIZE = 10MB,
  MAXSIZE = 100MB,
  FILEGROWTH = 1MB );
GO

-- Sử dụng database vừa tạo
USE PY00304_Java5;
GO

-- =============================================
-- Tạo các bảng chuẩn hóa
-- =============================================

-- Bảng Airlines (Hãng hàng không)
CREATE TABLE Airlines (
    AirlineID INT IDENTITY(1,1) PRIMARY KEY,
    AirlineName NVARCHAR(100) NOT NULL UNIQUE,
    AirlineCode NVARCHAR(10) NOT NULL UNIQUE,
    Country NVARCHAR(50),
    CreatedDate DATETIME2 DEFAULT GETDATE(),
    IsActive BIT DEFAULT 1
);

-- Bảng Cities (Thành phố)
CREATE TABLE Cities (
    CityID INT IDENTITY(1,1) PRIMARY KEY,
    CityName NVARCHAR(100) NOT NULL,
    CityCode NVARCHAR(10) NOT NULL UNIQUE,
    Country NVARCHAR(50) NOT NULL,
    TimeZone NVARCHAR(50),
    CreatedDate DATETIME2 DEFAULT GETDATE(),
    IsActive BIT DEFAULT 1
);

-- Bảng Airports (Sân bay)
CREATE TABLE Airports (
    AirportID INT IDENTITY(1,1) PRIMARY KEY,
    AirportName NVARCHAR(200) NOT NULL,
    AirportCode NVARCHAR(10) NOT NULL UNIQUE,
    CityID INT NOT NULL,
    CreatedDate DATETIME2 DEFAULT GETDATE(),
    IsActive BIT DEFAULT 1,
    FOREIGN KEY (CityID) REFERENCES Cities(CityID)
);

-- Bảng Flights (Chuyến bay) - Bảng chính tương thích với Entity
CREATE TABLE Flights (
    FlightID INT PRIMARY KEY,
    AirlineID INT NOT NULL,
    DepartureCityID INT NOT NULL,
    ArrivalCityID INT NOT NULL,
    DepartureAirportID INT,
    ArrivalAirportID INT,
    DepartureTime DATETIME2 NOT NULL,
    ArrivalTime DATETIME2 NOT NULL,
    FlightNumber NVARCHAR(20),
    Status NVARCHAR(20) DEFAULT 'Scheduled',
    CreatedDate DATETIME2 DEFAULT GETDATE(),
    UpdatedDate DATETIME2 DEFAULT GETDATE(),
    
    -- Foreign Keys
    FOREIGN KEY (AirlineID) REFERENCES Airlines(AirlineID),
    FOREIGN KEY (DepartureCityID) REFERENCES Cities(CityID),
    FOREIGN KEY (ArrivalCityID) REFERENCES Cities(CityID),
    FOREIGN KEY (DepartureAirportID) REFERENCES Airports(AirportID),
    FOREIGN KEY (ArrivalAirportID) REFERENCES Airports(AirportID),
    
    -- Constraints
    CONSTRAINT CK_Flight_Times CHECK (ArrivalTime > DepartureTime),
    CONSTRAINT CK_Flight_Cities CHECK (DepartureCityID != ArrivalCityID)
);

-- =============================================
-- Tạo View để tương thích với Entity Flight
-- =============================================
CREATE VIEW FlightView AS
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
INNER JOIN Airlines a ON f.AirlineID = a.AirlineID
INNER JOIN Cities dc ON f.DepartureCityID = dc.CityID
INNER JOIN Cities ac ON f.ArrivalCityID = ac.CityID;
GO

-- =============================================
-- Tạo Indexes để tối ưu hiệu suất
-- =============================================
CREATE INDEX IX_Flights_DepartureTime ON Flights(DepartureTime);
CREATE INDEX IX_Flights_ArrivalTime ON Flights(ArrivalTime);
CREATE INDEX IX_Flights_Airline ON Flights(AirlineID);
CREATE INDEX IX_Flights_DepartureCity ON Flights(DepartureCityID);
CREATE INDEX IX_Flights_ArrivalCity ON Flights(ArrivalCityID);
CREATE INDEX IX_Airlines_Name ON Airlines(AirlineName);
CREATE INDEX IX_Cities_Name ON Cities(CityName);

-- =============================================
-- Tạo Stored Procedures
-- =============================================

-- Procedure tìm kiếm chuyến bay theo hãng
CREATE PROCEDURE sp_SearchFlightsByAirline
    @AirlineName NVARCHAR(100)
AS
BEGIN
    SELECT * FROM FlightView 
    WHERE Airline LIKE '%' + @AirlineName + '%'
    ORDER BY DepartureTime;
END
GO

-- Procedure thêm chuyến bay mới
CREATE PROCEDURE sp_AddFlight
    @FlightID INT,
    @AirlineName NVARCHAR(100),
    @DepartureCity NVARCHAR(100),
    @ArrivalCity NVARCHAR(100),
    @DepartureTime DATETIME2,
    @ArrivalTime DATETIME2,
    @FlightNumber NVARCHAR(20) = NULL
AS
BEGIN
    DECLARE @AirlineID INT, @DepartureCityID INT, @ArrivalCityID INT;
    
    -- Lấy hoặc tạo AirlineID
    SELECT @AirlineID = AirlineID FROM Airlines WHERE AirlineName = @AirlineName;
    IF @AirlineID IS NULL
    BEGIN
        INSERT INTO Airlines (AirlineName, AirlineCode) VALUES (@AirlineName, LEFT(@AirlineName, 3));
        SET @AirlineID = SCOPE_IDENTITY();
    END
    
    -- Lấy hoặc tạo DepartureCityID
    SELECT @DepartureCityID = CityID FROM Cities WHERE CityName = @DepartureCity;
    IF @DepartureCityID IS NULL
    BEGIN
        INSERT INTO Cities (CityName, CityCode, Country) VALUES (@DepartureCity, LEFT(@DepartureCity, 3), 'Unknown');
        SET @DepartureCityID = SCOPE_IDENTITY();
    END
    
    -- Lấy hoặc tạo ArrivalCityID
    SELECT @ArrivalCityID = CityID FROM Cities WHERE CityName = @ArrivalCity;
    IF @ArrivalCityID IS NULL
    BEGIN
        INSERT INTO Cities (CityName, CityCode, Country) VALUES (@ArrivalCity, LEFT(@ArrivalCity, 3), 'Unknown');
        SET @ArrivalCityID = SCOPE_IDENTITY();
    END
    
    -- Thêm chuyến bay
    INSERT INTO Flights (FlightID, AirlineID, DepartureCityID, ArrivalCityID, DepartureTime, ArrivalTime, FlightNumber)
    VALUES (@FlightID, @AirlineID, @DepartureCityID, @ArrivalCityID, @DepartureTime, @ArrivalTime, @FlightNumber);
END
GO

PRINT 'Database PY00304_Java5 đã được tạo thành công với cấu trúc chuẩn hóa!';