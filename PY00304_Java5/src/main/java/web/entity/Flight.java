package web.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "FlightView") // 🔥 MAP VIEW – QUAN TRỌNG
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    @Id
    @Column(name = "FlightID")
    private Integer flightId;

    @Column(name = "Airline")
    private String airline;

    @Column(name = "DepartureCity")
    private String departureCity;

    @Column(name = "ArrivalCity")
    private String arrivalCity;

    @Column(name = "DepartureTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime departureTime;

    @Column(name = "ArrivalTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime arrivalTime;
}
