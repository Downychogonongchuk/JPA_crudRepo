package com.icia.jpaproject.controller;

import com.icia.jpaproject.dto.JpaDto;
import com.icia.jpaproject.service.JpaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class JpaController {
    @Autowired
    private JpaService jServ;

    @GetMapping("/")
    public  String home(Model model){

        log.info("home()");
        String view = jServ.getList(model);
        return view;
    }

    @GetMapping("wrtiteForm")
    public String writeForm(){
    log.info("writeForm()");
    return "writeForm";
    }

    @PostMapping("dataProc")
    public String dataProc(JpaDto jdto,
                           RedirectAttributes rttr){
        log.info("dataProc()");
        String view = jServ.insertData(jdto, rttr);
        return view;
    }

    @GetMapping("search")
    public String search(@RequestParam("type") String type,
                         @RequestParam("keyword") String keyword,
                         Model model){
        log.info("search()");
        String view = jServ.getData(type, keyword, model);
        return view;
    }

}//class end





























