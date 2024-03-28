package com.icia.jpaproject.service;

import com.icia.jpaproject.dto.JpaDto;
import com.icia.jpaproject.entity.JpaEntity;
import com.icia.jpaproject.repository.JpaDataRepository;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class JpaService {
        @Autowired
    private JpaDataRepository jRepo;

        private ModelMapper mapper = new ModelMapper();

    public String getList(Model model) {
        log.info("getList()");
        //DB에서 데이터 목록 가져오기
        List<JpaEntity> jList = jRepo.findAll();
        //select * from jpatbl   // 뭐가 자동? 이 쿼리문이 왜 자동 생성?

        //Entity > Dto로 매핑  entity가 임시로 가져와서 dto에 넘겨줌
        List<JpaDto> jdList = mapper.map(jList,
                new TypeToken<List<JpaDto>>(){}.getType());

        model.addAttribute("jdList", jdList);

        return "index";
    }

    public String insertData(JpaDto jdto,
                                RedirectAttributes rttr) {
        log.info("insertData()");
        String view = null;
        String msg = null;

        //Dto > Entity로 변환
        JpaEntity jEntity = mapper.map(jdto, JpaEntity.class);

        try{
            //id 컬럼 값이 없는 상태로 save > insert
            //id 컬럼 값이 있는 상태로 save > update

            jRepo.save(jEntity); // entity를 기준으로 저장해주는 save 메소드
            view = "redirect:/";
            msg = "저장 성공";
        }catch(Exception e){
            e.printStackTrace();
            view = "redirect:writeForm";
            msg = "저장 실패";
        }
        rttr.addFlashAttribute("msg", msg);

        return view;
    }

    public String getData(String type,
                          String keyword, Model model) {
        // 다목적 메소드. 번호 검색(no) , 문자열 검색(string) , 숫자 검색(int) , 날짜 검색(date)
        log.info("getData()");
        List<JpaEntity> jpaEntityList = null;
        List<JpaDto> jpaDtoList = null;
        JpaEntity jpaEntity = null;
        JpaDto jpaDto = null;
        
        //type 에 따라서 구분
        switch (type){
            case "no":
                long keyValue = Long.parseLong(keyword);
                //keyValue(code)가 없으면 null //findById 리턴값은 Optional
                // jpaEntity = jRepo.findById(keyValue).get(); 반드시 값이 있을 때
                jpaEntity = jRepo.findById(keyValue).orElse(null);
                if(jpaEntity != null){
                    jpaDto = mapper.map(jpaEntity, JpaDto.class);
                    //화면(html)에서 목록(list)로 받기 때문에
                    // 목록으로 만들어서 보낸다.
                    jpaDtoList = new ArrayList<>();
                    jpaDtoList.add(jpaDto);
                }
                break;

        }
        model.addAttribute("jdList", jpaDtoList);

        return "index";
    }



}// class end


































