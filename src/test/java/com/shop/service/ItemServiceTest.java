package com.shop.service;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

    //MockMultipartFile 클래스를 이용하여 가짜 MultipartFileList를 만들어서 반환해주는 method
    List<MultipartFile> createMultiplePartFiles() throws Exception{

        List<MultipartFile> multipartFileList = new ArrayList<>();

        for(int i=0;i<5;i++){
            String path = "C:/spring_study/item/";
            String imageName = "image" + i + ".jpg";
            MockMultipartFile multipartFile =
                                        new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1,2,3,4});
            multipartFileList.add(multipartFile);

        }
        return multipartFileList;
    }

    @Test
    @DisplayName("상품 등록 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void saveItem() throws Exception{
        //상품 등록 화면에서 입력 받는 상품 데이터를 세팅
       ItemFormDto itemFormDto = new ItemFormDto();

       itemFormDto.setItemNm("테스트 상품");
       itemFormDto.setItemSellStatus(ItemSellStatus.SELL);
       itemFormDto.setItemDetail("~~~상품 등록 테스트를 위한 상품~~~");
       itemFormDto.setPrice(10000);
       itemFormDto.setStockNumber(100);

       List<MultipartFile> multipartFileList = createMultiplePartFiles();
       Long itemId = itemService.saveItem(itemFormDto, multipartFileList);

       List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
       // findById 해서 찾은 값이 없으면 orElseThrow로 넘어가는데 거기서  ::new를 몰겠음 ..
       Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

       assertEquals(itemFormDto.getItemNm(), item.getItemNm());
       assertEquals(itemFormDto.getItemSellStatus(), item.getItemSellStatus());
       assertEquals(itemFormDto.getItemDetail(), item.getItemDetail());
       assertEquals(itemFormDto.getPrice(), item.getPrice());
       assertEquals(itemFormDto.getStockNumber(), item.getStockNumber());
       //상품 이미지는, 사진 하나만 필수 입력(나머지는 선택)이므로 첫 번째 사진만 파일이름이 같은지 확인해줌.
       assertEquals(multipartFileList.get(0).getOriginalFilename(), itemImgList.get(0).getOriImgName());

    }
}
