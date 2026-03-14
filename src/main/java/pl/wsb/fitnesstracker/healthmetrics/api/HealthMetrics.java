package pl.wsb.fitnesstracker.healthmetrics.api;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "health_metrics")
@Getter
@NoArgsConstructor
@ToString
public class HealthMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "weight", nullable = false)
    private Float weight;

    @Column(name = "height", nullable = false)
    private Float height;

    @Column(name = "heartRate", nullable = false)
    private Integer heartRate;

    public HealthMetrics(
            final Long userId,
            final LocalDate date,
            final Float weight,
            final Float height,
            final Integer heartRate
    ) {
        this.userId = userId;
        this.date = date;
        this.weight = weight;
        this.height = height;
        this.heartRate = heartRate;
    }
}
