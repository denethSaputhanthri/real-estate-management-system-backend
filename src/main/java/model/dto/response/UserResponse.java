package model.dto.response;

import com.realestate.system.enums.UserRole;
import com.realestate.system.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponse {
    private Long id;

    private String name;

    private String email;

    private String phone;

    private UserRole role;

    private UserStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
