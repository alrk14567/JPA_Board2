package com.nc13.JPA_Board2.entity;

import com.nc13.JPA_Board2.model.UserDTO;
import com.nc13.JPA_Board2.service.UserService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "user_table")
public class UserEntity {
    @Id //pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto _increment
    private  Long id;

    @Column(unique = true) // 유니크 제약조건
    private String username;

    @Column
    private String password;

    @Column
    private String nickname;

    // dto -> 엔티티로 변환
    public static UserEntity toUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setNickname(userDTO.getNickname());
        return userEntity;
    }

    public static UserEntity toUpdateUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setNickname(userDTO.getNickname());

        return userEntity;
    }

}
