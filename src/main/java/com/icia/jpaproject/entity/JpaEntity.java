package com.icia.jpaproject.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "jpatbl")
@Data
public class JpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;//primary key 변수. 자동 증가

    @Column(name = "str_data", nullable = false, length = 50)
    private String strData;

    @Column(name = "int_data")
    private int intData;

    @Column(name = "reg_date")
    @CreationTimestamp // default current_timestamp: 작성 시간 저장
    private Timestamp regDate;
}
