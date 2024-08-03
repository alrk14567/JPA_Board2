package com.nc13.JPA_Board2.service;

import com.nc13.JPA_Board2.entity.UserEntity;
import com.nc13.JPA_Board2.model.UserDTO;
import com.nc13.JPA_Board2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void register(UserDTO userDTO) {
        // 1. dto -> entity 변환
        // 2. repository의 save메서드 호출
        UserEntity userEntity= UserEntity.toUserEntity(userDTO);
        userRepository.save(userEntity);//등록할떄?? 자동으로 inser쿼리를 만들어서 DB에 저장시켜줌
        //repository의 register 메서드 호출 (조건. entity객체를 넘겨줘야 함)

    }

    public UserDTO login(UserDTO userDTO) {
        /*
            1. 회원이 입력한 이메일로 DB에서 조회를 함
            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
        */
        Optional<UserEntity> byUsername= userRepository.findByUsername(userDTO.getUsername());
        if(byUsername.isPresent()) {
            // 조회 결과가 있다( 해당 이메일을 가진 회원 정보가 있다.)
            UserEntity userEntity=byUsername.get();
            if (userEntity.getPassword().equals(userDTO.getPassword())) {
                //비밀 번호 일치
                // 엔티티 -> dto 변환 후 리턴
                UserDTO dto = UserDTO.toUserDTO(userEntity);
                return dto;
            } else {
                //비밀번호 불일치
                return null;
            }
        }else {
            // 조회 결과가 없다(해당 이메일을 가진 회원이 없다.)
            return null;
        }
    }

    public List<UserDTO> selectAll() {
        List<UserEntity> memberEntityList= userRepository.findAll();
        List<UserDTO> memberList= new ArrayList<>();
        for (UserEntity userEntity: memberEntityList) {
            memberList.add(UserDTO.toUserDTO(userEntity));
        }
        return memberList;
    }

    public UserDTO selectOne(Long id) {
        Optional<UserEntity> userEntity= userRepository.findById(id);  // 하나를 조회할때는 옵셔널로 감싸서 조회
        if (userEntity.isPresent()){
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            return memberDTO;

          return UserDTO.toUserDTO(userEntity.get()); // 왜 userEntity.get으로 해주냐?? 옵셔널로 감싸져 있는 엔티티의 경우 옵셔널을 한번 없애줘야하기 때문에 겟으로 값읋 전달한다.
        } else {
            return null;
        }
    }

    public UserDTO updateForm(String myUsername) {
        Optional<UserEntity> byUsername = userRepository.findByUsername(myUsername);
        if(byUsername.isPresent()) {
            return UserDTO.toUserDTO(byUsername.get());
        } else {
            return null;
        }
    }

    public void update(UserDTO userDTO) {
        userRepository.save(UserEntity.toUpdateUserEntity(userDTO));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public String usernameCheck(String memberUsername) {
        Optional<UserEntity> byUserUsername = userRepository.findByUsername(memberUsername);
        if(byUserUsername.isPresent()) {
            // 결과가 있으면 사용x
            return null;
        } else{
            //없으면 사용 o
            return "ok";
        }
    }
}
