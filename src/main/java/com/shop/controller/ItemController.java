package com.shop.controller;

import com.shop.dto.ItemFormDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model){
        //상품 등록 페이지에 접근할 수 있게 하도록 하기 위해 코드 추가.
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "/item/itemForm";
    }
}
