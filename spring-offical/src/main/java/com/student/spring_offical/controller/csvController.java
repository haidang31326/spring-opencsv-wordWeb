package com.student.spring_offical.controller;

import com.opencsv.exceptions.CsvException;
import com.student.spring_offical.service.CsvProcessingService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/csv")
public class csvController {
    private final CsvProcessingService csvService;

    public csvController(CsvProcessingService csvService) {
        this.csvService = csvService;
    }

    @PostMapping("/remove-dulicate")
    public ResponseEntity<Resource> removeDulicate(@RequestParam("file") MultipartFile file) {
        try {
            //Gọi service
            Resource processedFile = csvService.removeDuplicatesFromCsv(file);
            //Tạo Header để thông báo file download
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=result.csv");
            //Trả về kết quả trạng thái
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(processedFile);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null); // Trả lỗi về cho user
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
}
