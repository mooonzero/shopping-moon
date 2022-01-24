package com.shop.service;
//상품 등록 class

import com.shop.dto.ItemFormDto;
import com.shop.dto.ItemImgDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{

        //상품 등록

        //상품 등록 폼으로부터 입력 받은 데이터를 이용하여 item 객체를 생성
        Item item = itemFormDto.createItem();
        //상품 데이터를 저장
        itemRepository.save(item);

        //이미지 등록
        for(int i=0; i< itemImgFileList.size();i++){
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            //첫 번째 이미지를 대표이미지로 등록해주는 if,else문
            if(i == 0){
                itemImg.setRepimgYn("Y");
            }else{
                itemImg.setRepimgYn("N");
            }
            //상품의 이미지 정보를 저장
            itemImgService.savedItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }

    //상품 데이터를 읽어오는 트랜잭션을 읽기 전용으로 설정 -> 성능 향상
    @Transactional(readOnly = true)
    public  ItemFormDto getItemDtl(Long itemId){

        //해당상품 이미지 조회
        List<ItemImg> itemImgList =
                itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();

        //조회한 ItemImg entity를  ItemImgDto 객체로 만들어서 리스트에 추가
        for (ItemImg itemImg : itemImgList){
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }
        //상품 아이디를 통해 상품 엔티티 조회
        //존재하지 않을 때 EntityNotFoundException 발생 시킴.
        // ::new :
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }
}
