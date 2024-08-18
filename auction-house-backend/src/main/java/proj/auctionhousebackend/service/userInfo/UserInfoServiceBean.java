package proj.auctionhousebackend.service.userInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import proj.auctionhousebackend.dto.CollectionResponseDTO;
import proj.auctionhousebackend.dto.PageRequestDTO;
import proj.auctionhousebackend.dto.userInfo.UserInfoResponseDTO;
import proj.auctionhousebackend.exception.ExceptionCode;
import proj.auctionhousebackend.exception.NotFoundException;
import proj.auctionhousebackend.mapper.UserInfoMapper;
import proj.auctionhousebackend.model.UserInfoEntity;
import proj.auctionhousebackend.repository.UserInfoRepository;

import java.util.List;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
public class UserInfoServiceBean implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserInfoMapper userInfoMapper;
    private final String applicationName;

    @Override
    public UserInfoResponseDTO findById(UUID id) {
        log.info("Finding user by ID: {}", id);
        return userInfoRepository.findById(id)
                .map(userInfoMapper::userInfoEntityToUserInfoResponseDTO)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR001_USER_NOT_FOUND.getMessage(),
                        id
                )));
    }

    @Override
    public List<UserInfoResponseDTO> findAll() {

        log.info("Getting all users for application {}", applicationName);

        List<UserInfoEntity> userInfoEntityList = userInfoRepository.findAll();

        return userInfoMapper.userInfoEntityListToUserInfoResponseDTOList(userInfoEntityList);
    }

    @Override
    public CollectionResponseDTO<UserInfoResponseDTO> findAllPaged(PageRequestDTO page) {
        log.info("Getting all users with pagination");
        Page<UserInfoEntity> userInfoEntityList = userInfoRepository.findAll(PageRequest.of(
                page.getPageNumber(),
                page.getPageSize()
        ));
        List<UserInfoResponseDTO> userInfos = userInfoMapper.userInfoEntityListToUserInfoResponseDTOList(userInfoEntityList.getContent());

        return new CollectionResponseDTO<>(userInfos, userInfoEntityList.getTotalElements());
    }

    @Override
    public List<UserInfoResponseDTO> findAllSorted(String sortBy) {
        log.info("Finding all users sorted by: {}", sortBy);
        List<UserInfoEntity> userInfoEntityList = userInfoRepository.findAll(Sort.by(sortBy).descending());

        return userInfoMapper.userInfoEntityListToUserInfoResponseDTOList(userInfoEntityList);
    }

    @Override
    public UserInfoResponseDTO findByEmail(String email) {
        log.info("Finding user by email: {}", email);
        return userInfoRepository.findByEmail(email)
                .map(userInfoMapper::userInfoEntityToUserInfoResponseDTO)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR002_EMAIL_NOT_FOUND.getMessage(),
                        email
                )));
    }
}