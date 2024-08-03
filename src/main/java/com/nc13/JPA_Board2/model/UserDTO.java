package com.nc13.JPA_Board2.model;

import com.nc13.JPA_Board2.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor  //기본 생성자를 자동으로 만들어줌
@AllArgsConstructor // 이 필드를 매개변수로 하는 생성자 생성?
@ToString //객체가 가지고 있는 필드를 출력할때
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String nickname;

    public static UserDTO toUserDTO(UserEntity userEntity) {
        UserDTO userDTO=new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setNickname(userEntity.getNickname());
        return userDTO;
    }
}
