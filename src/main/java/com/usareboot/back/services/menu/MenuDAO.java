package com.usareboot.back.services.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.usareboot.back.repositories.menu.MenuRepository;

@Service
public class MenuDAO {
    @Autowired
    private MenuRepository menuRepository;

//    public Map getMenuFromByRole(long roles) {
//        ArrayList<MenuDTO> menuList = new ArrayList<>();
//        var bdFuncResponse = menuRepository.menuFunc(roles);
////        bdFuncResponse.forEach(x -> menuList.add(new MenuDTO(x.getMenu_role())));
////        System.out.println(bdFuncResponse);
//
//        return bdFuncResponse;
//    }



}
