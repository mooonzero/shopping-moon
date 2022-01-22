package com.shop.service;

import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {
    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void savedItemImg(ItemImg itemImg, MultipartFile itemImgFile)throws Exception{
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        //사용자가 상품의 이미지를 등록한 경우(stringUtils가 empty가 아니라면
        if (!StringUtils.isEmpty(oriImgName)){
            //이미지 이름와 경로 설정.
            imgName = fileService.uploadFile(itemImgLocation, oriImgName,
                        itemImgFile.getBytes());
            imgUrl = "/images/item/" + imgName;
        }

        //상품 이미지 정보 저장
        //oriImgName : 업로드했던 상품 이미지 파일의 원래 이름
        //imgName : 실제 로컬에 저장된 상품 이미지 파일의 이름
        // imgUrl : 업로드 결과 로컬에 저장된 상품 이미지 파일을 불러오는 경우
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);
    }
}
