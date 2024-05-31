package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "attach")
@Entity
public class AttachEntity {
    @Id
    private String id;
    @Column
    private String filename;
    @Column
    private String extension;
    @Column
    private String path;
    @Column
    private Long size;
    @Column
    @CreationTimestamp
    private LocalDateTime createdDate;
}
