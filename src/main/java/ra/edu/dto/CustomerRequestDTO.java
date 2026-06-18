package ra.edu.dto;

import lombok.Data;

@Data
public class CustomerRequestDTO {
    private String fullName;
    private String email;
    private String password;
}
