package com.example.bookingclinic.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.user.dto.UpdateAddressDTO;
import com.example.bookingclinic.user.entity.UPatient;
import com.example.bookingclinic.user.service.UPatientService;

@RestController
@RequestMapping("api/v1")
public class UPatientController {
    private UPatientService patientService;

    public UPatientController(UPatientService patientService){
        this.patientService = patientService;
    }

    @GetMapping("/patient/{id}") // xem thôn tin cá nhân của bệnh nhân
    public UPatient patientDrtail(@PathVariable String id){
        return patientService.getPatientById(id);
    }

    @PutMapping("/patient/{id}") // chỉnh sửa thông tin cá nhân của bệnh nhân
    public UPatient editPatient(@PathVariable String id, @RequestBody UPatient newPatient){
        return patientService.editPatient(id,newPatient);
    }

    @PutMapping("/patientQQ/{id}") // chỉnh sửa thông tin quê quán của bệnh nhân
    public String editQueQuan(@PathVariable String id, @RequestBody UpdateAddressDTO qq){
        return patientService.updateQueQuan(id, qq);
    }
}
