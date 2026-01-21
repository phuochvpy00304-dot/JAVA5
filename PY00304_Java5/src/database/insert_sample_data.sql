-- =============================================
-- Script chèn dữ liệu mẫu cho Database PY00304_Java5
-- =============================================

USE PY00304_Java5;
GO

-- =============================================
-- Chèn dữ liệu mẫu cho Airlines
-- =============================================
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

-- =============================================
-- Chèn dữ liệu mẫu cho Cities
-- =============================================
INSERT INTO Cities (CityName, CityCode, Country, TimeZone) VALUES
-- Vietnam
('Ho Chi Minh City', 'SGN', 'Vietnam', 'UTC+7'),
('Hanoi', 'HAN', 'Vietnam', 'UTC+7'),
('Da Nang', 'DAD', 'Vietnam', 'UTC+7'),
('Nha Trang', 'CXR', 'Vietnam', 'UTC+7'),
('Hue', 'HUI', 'Vietnam', 'UTC+7'),
('Can Tho', 'VCA', 'Vietnam', 'UTC+7'),
-- International
('Singapore', 'SIN', 'Singapore', 'UTC+8'),
('Bangkok', 'BKK', 'Thailand', 'UTC+7'),
('Kuala Lumpur', 'KUL', 'Malaysia', 'UTC+8'),
('Hong Kong', 'HKG', 'Hong Kong', 'UTC+8'),
('Seoul', 'ICN', 'South Korea', 'UTC+9'),
('Tokyo', 'NRT', 'Japan', 'UTC+9'),
('Manila', 'MNL', 'Philippines', 'UTC+8'),
('Jakarta', 'CGK', 'Indonesia', 'UTC+7'),
('Phnom Penh', 'PNH', 'Cambodia', 'UTC+7');

-- =============================================
-- Chèn dữ liệu mẫu cho Airports
-- =============================================
INSERT INTO Airports (AirportName, AirportCode, CityID) VALUES
-- Vietnam Airports
('Tan Son Nhat International Airport', 'SGN', 1),
('Noi Bai International Airport', 'HAN', 2),
('Da Nang International Airport', 'DAD', 3),
('Cam Ranh International Airport', 'CXR', 4),
('Phu Bai International Airport', 'HUI', 5),
('Can Tho International Airport', 'VCA', 6),
-- International Airports
('Changi Airport', 'SIN', 7),
('Suvarnabhumi Airport', 'BKK', 8),
('Kuala Lumpur International Airport', 'KUL', 9),
('Hong Kong International Airport', 'HKG', 10),
('Incheon International Airport', 'ICN', 11),
('Narita International Airport', 'NRT', 12);

-- =============================================
-- Chèn dữ liệu mẫu cho Flights
-- =============================================
INSERT INTO Flights (FlightID, AirlineID, DepartureCityID, ArrivalCityID, DepartureAirportID, ArrivalAirportID, DepartureTime, ArrivalTime, FlightNumber, Status) VALUES
-- Domestic Flights Vietnam
(1001, 1, 1, 2, 1, 2, '2024-02-15 06:00:00', '2024-02-15 08:15:00', 'VN210', 'Scheduled'),
(1002, 1, 2, 1, 2, 1, '2024-02-15 09:30:00', '2024-02-15 11:45:00', 'VN211', 'Scheduled'),
(1003, 2, 1, 3, 1, 3, '2024-02-15 07:20:00', '2024-02-15 08:45:00', 'BL695', 'Scheduled'),
(1004, 3, 2, 4, 2, 4, '2024-02-15 10:15:00', '2024-02-15 11:35:00', 'VJ1543', 'Scheduled'),
(1005, 4, 1, 5, 1, 5, '2024-02-15 14:30:00', '2024-02-15 15:45:00', 'QH1065', 'Scheduled'),

-- International Flights
(2001, 1, 1, 7, 1, 7, '2024-02-15 08:45:00', '2024-02-15 11:30:00', 'VN651', 'Scheduled'),
(2002, 5, 7, 1, 7, 1, '2024-02-15 13:20:00', '2024-02-15 16:05:00', 'SQ175', 'Scheduled'),
(2003, 1, 2, 8, 2, 8, '2024-02-15 11:40:00', '2024-02-15 13:25:00', 'VN563', 'Scheduled'),
(2004, 6, 8, 2, 8, 2, '2024-02-15 15:50:00', '2024-02-15 17:35:00', 'TG564', 'Scheduled'),
(2005, 3, 1, 9, 1, 9, '2024-02-15 19:25:00', '2024-02-15 22:40:00', 'VJ852', 'Scheduled'),

-- More flights for testing
(3001, 2, 3, 1, 3, 1, '2024-02-16 06:30:00', '2024-02-16 07:55:00', 'BL696', 'Scheduled'),
(3002, 4, 2, 3, 2, 3, '2024-02-16 12:15:00', '2024-02-16 13:35:00', 'QH1205', 'Scheduled'),
(3003, 1, 1, 10, 1, 10, '2024-02-16 23:30:00', '2024-02-17 06:45:00', 'VN731', 'Scheduled'),
(3004, 8, 10, 1, 10, 1, '2024-02-16 14:20:00', '2024-02-16 21:35:00', 'CX766', 'Scheduled'),
(3005, 3, 4, 1, 4, 1, '2024-02-16 16:40:00', '2024-02-16 18:00:00', 'VJ1544', 'Scheduled'),

-- Weekend flights
(4001, 1, 1, 11, 1, 11, '2024-02-17 10:30:00', '2024-02-17 16:45:00', 'VN451', 'Scheduled'),
(4002, 9, 11, 1, 11, 1, '2024-02-17 18:20:00', '2024-02-18 00:35:00', 'KE682', 'Scheduled'),
(4003, 2, 2, 7, 2, 7, '2024-02-17 07:45:00', '2024-02-17 10:30:00', 'BL3521', 'Scheduled'),
(4004, 1, 1, 12, 1, 12, '2024-02-17 22:15:00', '2024-02-18 06:30:00', 'VN301', 'Scheduled'),
(4005, 10, 12, 1, 12, 1, '2024-02-18 08:50:00', '2024-02-18 17:05:00', 'JL751', 'Scheduled');

-- =============================================
-- Tạo thêm dữ liệu test cho tìm kiếm
-- =============================================
INSERT INTO Flights (FlightID, AirlineID, DepartureCityID, ArrivalCityID, DepartureAirportID, ArrivalAirportID, DepartureTime, ArrivalTime, FlightNumber, Status) VALUES
-- Thêm nhiều chuyến bay Vietnam Airlines để test search
(5001, 1, 2, 3, 2, 3, '2024-02-20 08:00:00', '2024-02-20 09:20:00', 'VN1543', 'Scheduled'),
(5002, 1, 3, 4, 3, 4, '2024-02-20 10:30:00', '2024-02-20 11:50:00', 'VN1645', 'Scheduled'),
(5003, 1, 4, 5, 4, 5, '2024-02-20 13:15:00', '2024-02-20 14:25:00', 'VN1747', 'Scheduled'),
(5004, 1, 5, 6, 5, 6, '2024-02-20 16:40:00', '2024-02-20 17:35:00', 'VN1849', 'Scheduled'),

-- Thêm chuyến bay VietJet để test
(6001, 3, 1, 2, 1, 2, '2024-02-21 05:30:00', '2024-02-21 07:45:00', 'VJ100', 'Scheduled'),
(6002, 3, 2, 1, 2, 1, '2024-02-21 20:15:00', '2024-02-21 22:30:00', 'VJ101', 'Scheduled'),
(6003, 3, 1, 3, 1, 3, '2024-02-21 11:20:00', '2024-02-21 12:45:00', 'VJ200', 'Scheduled'),
(6004, 3, 3, 1, 3, 1, '2024-02-21 18:50:00', '2024-02-21 20:15:00', 'VJ201', 'Scheduled');

PRINT 'Dữ liệu mẫu đã được chèn thành công!';
PRINT 'Tổng số Airlines: ' + CAST((SELECT COUNT(*) FROM Airlines) AS VARCHAR(10));
PRINT 'Tổng số Cities: ' + CAST((SELECT COUNT(*) FROM Cities) AS VARCHAR(10));
PRINT 'Tổng số Airports: ' + CAST((SELECT COUNT(*) FROM Airports) AS VARCHAR(10));
PRINT 'Tổng số Flights: ' + CAST((SELECT COUNT(*) FROM Flights) AS VARCHAR(10));