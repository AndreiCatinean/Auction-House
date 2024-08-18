package proj.auctionhousebackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import proj.auctionhousebackend.dto.userInfo.UserInfoResponseDTO;
import proj.auctionhousebackend.model.UserInfoEntity;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserInfoMapper {

    UserInfoResponseDTO userInfoEntityToUserInfoResponseDTO(UserInfoEntity userInfoEntity);

    List<UserInfoResponseDTO> userInfoEntityListToUserInfoResponseDTOList(List<UserInfoEntity> userInfoEntityList);

    UserInfoEntity UserInfoResponseDTOTouserInfoEntity(UserInfoResponseDTO chefRequestDTO);
}