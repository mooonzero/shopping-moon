package com.shop.controller;

import com.shop.dto.ItemFormDto;
import com.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model){
        //상품 등록 페이지에 접근할 수 있게 하도록 하기 위해 코드 추가.
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "/item/itemForm";
    }

    @PostMapping(value = "/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model,
                          @RequestParam("itemImgFile")List<MultipartFile> itemImgFileList){

        //bindingResult : validate 값을 받는 class에서 객체값을 검증하는 방식
        //상품 등록 시 필수 값이 없다면, 다시 상품 등록 페이지로 전환해줌.
       if(bindingResult.hasErrors()){
           model.addAttribute("errorMessage", "상품 등록 시 필요한 필수 값이 없습니다.");
            return "item/itemForm";
        }

       //상품 등록 시 첫 번째 이미지가 없을때 에러메시지 && 상품 등록 페이지 전환
       if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
           model.addAttribute("errorMessage", "첫 번째 상품 이미지는 필수 입력 값입니다.");
           return "item/itemForm";
       }

       try {
           //상품 저장 로직 호출, 매개변수는 상품 정보(itemFormDto),상품 이미지 정보(itemImgFileList)
           itemService.saveItem(itemFormDto, itemImgFileList);
       }catch(Exception e){
           model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
           return "item/itemForm";
       }
        //상품이 정상적으로 등록되면 main page로 이동
        return "redirect:/";
    }

    //상품 수정 페이지 진입입
   @GetMapping(value = "admin/item/{itemId}")
    public String ItemDtl(@PathVariable("itemId") Long itemId, Model model){

        try {
            //조회한 상품 데이터를 모델에 담아서 뷰로 전달.
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDto", itemFormDto);
        }catch (EntityNotFoundException e){
            //상품 엔티티가 존재하지 않을 경우 에러메시지를 담아서 상품 등록 페이지로 이동.
            model.addAttribute("errorMessage", "존자해지 않는 상품입니다.");
            model.addAttribute("itemFormDto", new ItemFormDto());
            return "item/itemForm";
        }

        return "item/itemForm";
   }

   @PostMapping(value = "/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList,Model model){

            if (bindingResult.hasGlobalErrors()){
                return "item/itemForm";
            }
            if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
                model.addAttribute("errorMessage", "첫 번째 상품 이미지는 필수 입력값입니당");
                return "item/itemForm";
            }

            try {
                itemService.updateItem(itemFormDto, itemImgFileList);
            }catch (Exception e){
                 model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
                 return "item/itemForm";
            }

            return "redirect:/";
   }
    //url에 페이지 번호가 없는 경우와 있는 경우를 함께 매핑
    //optional, isPresent() error.. 해결하기
//   @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
//    public String itemManage(ItemSearchDto itemSearchDto,
//                             @PathVariable("page") Optional<Integer> page, Model model){
//       // PageRequest.of 메소드를 통해 pageable 객체 생성
//       //첫번째 param : 조회할 페이지 번호
//       //두번째 param : 한 번에 가지고 올 데이터 수
//       //isPresent()를 통해  페이지 번호가 있으면 해당 페이지 조회하도록 세팅, 없으면 0페이지 조회
//       Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
//       Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);
//                    model.addAttribute("items", items);
//                    // 페이지 전환 시 기존 검색 조건을 유지한 채 이동할 수 있도록 view에 다시 전달
//                    model.addAttribute("itemSearchDto", itemSearchDto);
//                    model.addAttribute("maxPage", 5);
//                    return "item/itemMng";
//   }
}
