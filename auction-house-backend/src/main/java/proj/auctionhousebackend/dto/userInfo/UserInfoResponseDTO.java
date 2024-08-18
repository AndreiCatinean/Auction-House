package proj.auctionhousebackend.dto.userInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import proj.auctionhousebackend.model.Role;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseDTO {

    private UUID id;
    private String username;
    private String email;
    private String phoneNumber;
    private Role role;
}
