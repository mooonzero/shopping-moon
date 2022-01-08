package com.shop.controller;

import com.shop.dto.ItemDto;
import com.shop.entity.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafExController {

    @GetMapping(value = "/ex01")
    public String thymeleafExample01(Model model){
        model.addAttribute("data","타임리프 예제");
        return "thymeleafEx/thymeleafEx01";
    }

    //view에 전달하기 위해 ItemDto 객체 생성을 해서 model에 데이터를 담음
    @GetMapping(value = "/ex02")
    public String thymeleafExample02(Model model){
        ItemDto itemDto = new ItemDto();
        itemDto.setItemDetail("상품 상세 설명");
        itemDto.setItemNm("테스트 상품1");
        itemDto.setPrice(10000);
        itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto",itemDto);
        return "thymeleafEx/thymeleafEx02";

    }

    @GetMapping(value = "/ex03")
    public String thymeleafExample03(Model model){

        List<ItemDto> itemDtoList = new ArrayList<>();

        //반복문을 통해 화면에서 출력할 10개의 itemDto 객체를 만들어서 itemDtoList에 넣어줍니다.
        for(int i=1;i<=10;i++){

            ItemDto itemDto = new ItemDto();
            itemDto.setItemDetail("상품 상세 설명"+i);
            itemDto.setItemNm("테스트 상품"+i);
            itemDto.setPrice(1000*i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }

        //화면에서 출력할 itemDtoList를 model에 담아서 View에 전달합니다.
        model.addAttribute("itemDtoList",itemDtoList);
        return "thymeleafEx/thymeleafEx03";
    }

    @GetMapping(value = "/ex04")
    public String thymeleafExample04(Model model){
        List<ItemDto> itemDtoList = new ArrayList<>();

        for(int i=1;i<=10;i++){

            ItemDto itemDto = new ItemDto();
            itemDto.setItemDetail("상품 상세 설명"+i);
            itemDto.setItemNm("테스트 상품"+i);
            itemDto.setPrice(1000*i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }
        model.addAttribute("itemDtoList",itemDtoList);
        return "thymeleafEx/thymeleafEx04";
    }
    //th:href를 이용한 링크 처리용 컨트롤러
    @GetMapping(value = "/ex05")
    public String thymeleafExample05(){
        return  "thymeleafEx/thymeleafEx05";
    }

    //th:href를 이용한 param 데이터 전달용 컨트롤러
    @GetMapping(value = "/ex06")
    public String thymeleafExample06(String param1, String param2, Model model){
        model.addAttribute("param1",param1);
        model.addAttribute("param2",param2);

        return "thymeleafEx/thymeleafEx06";
    }

    //thymeleaf 페이지 레이아웃 예제
    @GetMapping(value = "/ex07")
    public String thymeleafExample07(){
        return  "thymeleafEx/thymeleafEx07";
    }


}
