-- =============================================
-- Script tạo View tương thích với Entity Flight
-- =============================================

USE PY00304_Java5;
GO

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