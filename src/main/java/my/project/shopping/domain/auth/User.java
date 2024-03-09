package my.project.shopping.domain.auth;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
@Table(schema = "public")
public class User implements Serializable{

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uid;


    @Column(unique = true)
    private String userId;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String role = "ROLE_USER";  // default role

    @Column
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @CreationTimestamp
    @Column
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedDate;

    @Builder
    public User(String userId, String phone, String name, String password, String email, String role, SocialType socialType, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.userId = userId;
        this.phone = phone;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.socialType = socialType;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", socialType=" + socialType +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
