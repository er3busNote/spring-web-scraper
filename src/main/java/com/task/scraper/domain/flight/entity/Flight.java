package com.task.scraper.domain.flight.entity;

import com.task.scraper.domain.flight.dto.FlightRequest;
import com.task.scraper.domain.flight.type.FlightStatus;
import com.task.scraper.global.common.utils.DateUtil;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "BIGINT(20) COMMENT '스크랩 결과 ID'")
    private Long id;

    @Column(name = "FLIGHT_NO", nullable = false, columnDefinition = "VARCHAR(20) COMMENT '항공편 번호'")
    private String flightNumber;

    @Column(name = "AIRLINE", nullable = false, columnDefinition = "VARCHAR(50) COMMENT '항공사'")
    private String airline;

    @Column(name = "DEPARTURE", nullable = false, columnDefinition = "VARCHAR(30) COMMENT '출발지'")
    private String departure;

    @Column(name = "DEST", nullable = false, columnDefinition = "VARCHAR(30) COMMENT '목적지'")
    private String destination;

    @Column(name = "DEPARTURE_TIME", nullable = false, columnDefinition = "VARCHAR(20) COMMENT '출발 시간'")
    private String departureTime;

    @Column(name = "ARRIVAL_TIME", nullable = false, columnDefinition = "VARCHAR(20) COMMENT '도착 시간'")
    private String arrivalTime;

    @Column(name = "FLIGHT_DATE", nullable = false, columnDefinition = "DATE COMMENT '비행 날짜'")
    private LocalDateTime flightDate;

    @Column(name = "PRICE", nullable = true, columnDefinition = "DECIMAL(10,2) COMMENT '가격'")
    private Double price;

    @Column(name = "CURRENCY", nullable = true, columnDefinition = "VARCHAR(10) COMMENT '통화'")
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, columnDefinition = "VARCHAR(20) COMMENT '상태 (성공, 실패)'")
    private FlightStatus status;

    @Column(name = "REMARK", nullable = true, columnDefinition = "VARCHAR(255) COMMENT '비고 (에러메시지 등)'")
    private String remark;

    @Builder.Default
    @Column(name = "SCRAPED_AT", updatable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT current_timestamp() COMMENT '스크래핑 시작일시'")
    @CreationTimestamp  // INSERT 시 자동으로 값을 채워줌
    private LocalDateTime scrapedAt = LocalDateTime.now();

    public Flight(FlightRequest orderRequest, String airline, boolean available) {
        this.airline = airline;
        this.flightNumber = airline;
        this.departure = orderRequest.getOrigin();
        this.destination = orderRequest.getDest();
        this.departureTime = DateUtil.getCurrentDate();
        this.arrivalTime = DateUtil.getCurrentDate();
        this.flightDate = LocalDateTime.now();
        this.price = 0.0;
        this.currency = "KR";
        this.status = FlightStatus.IN_PROGRESS;
    }
}
