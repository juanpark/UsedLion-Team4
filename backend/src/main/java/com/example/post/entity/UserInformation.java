package com.example.post.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // 실제 DB 컬럼명과 매핑
    private Integer userId;
    private String email;
    private String password;
    private String username;
    private String nickname;
    private String provider;
    private String provider_id;
    private String role;
    private LocalDateTime created_at;

}
