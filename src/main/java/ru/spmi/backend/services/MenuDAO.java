package ru.spmi.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spmi.backend.dto.MenuDTO;
import ru.spmi.backend.repositories.MenuRepository;

import java.util.ArrayList;
import java.util.Map;

@Service
public class MenuDAO {
    @Autowired
    private MenuRepository menuRepository;

    public Map getMenuFromByRole(long roles) {
        ArrayList<MenuDTO> menuList = new ArrayList<>();
        var bdFuncResponse = menuRepository.menuFunc(roles);
//        bdFuncResponse.forEach(x -> menuList.add(new MenuDTO(x.getMenu_role())));
//        System.out.println(bdFuncResponse);

        return bdFuncResponse;
    }



}
