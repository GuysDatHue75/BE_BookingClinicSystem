package com.example.bookingclinic.user.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.user.entity.Advisory;
import com.example.bookingclinic.user.repository.AdvisoryRepository;
import com.example.bookingclinic.user.service.AdvisoryService;

@RestController
@RequestMapping("/api/v1")
public class AdvisoryController {
    private AdvisoryService advisoryService;
    private AdvisoryRepository advisoryRepository;

    public AdvisoryController(AdvisoryService advisoryService,AdvisoryRepository advisoryRepository){
        this.advisoryService = advisoryService;
        this.advisoryRepository = advisoryRepository;
    }

    @PostMapping("/advisory")// gửi câu hỏi tư vấn
    public Advisory sendQuessionToClinic(@RequestBody Advisory advisory){
        return advisoryService.sendQuessionToClinic(advisory);
    }
    // @GetMapping("/advisorys")// xem câu hỏi và câu trả lời
    // public List<Advisory> getAllAnswerAndQuessionByClinic(){
    //     return advisoryService.getAllAnswerAndQuessionByClinic();
    // }

    @GetMapping("/advisorys")// xem câu hỏi và câu trả lời có phân trang
    public Page<Advisory> getAdvisoryByPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,@RequestParam String city){
        Pageable pageable = PageRequest.of(page,size);
        Page<Advisory> result = advisoryRepository.findByTrangThaiTraLoiAndClinic_TinhThanhPhoContainingIgnoreCase(true,pageable,city);

        return result;
    }
}
