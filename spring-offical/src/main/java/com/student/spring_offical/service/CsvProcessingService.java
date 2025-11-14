package com.student.spring_offical.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class CsvProcessingService {
    public Resource readFileLineByLine(MultipartFile file) throws IOException, CsvException {

        // Đây là hàm mà Controller sẽ gọi
        // Đầu vào là MultipartFile, đầu ra là Resource (để user download)
            Reader fileReader = new InputStreamReader(file.getInputStream());
            //tạo đối tượng của csvReader
            CSVReader csvReader = new CSVReader(fileReader);
            //Đọc tất cả dữ liệu file
            List<String[]> allData = csvReader.readAll();
        for (String[] row : allData) {
            for(String cell : row) {
                System.out.println(cell+ "\t");
            }
            System.out.println();
        }
            csvReader.close();
        //lọc dữ liệu bị trùng
        Set<List<String>> uniquesData = new LinkedHashSet<>(); {
            for(String[] row : allData) {
                uniquesData.add(Arrays.asList(row));
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();






        return new ByteArrayResource(outputStream.toByteArray());
    }
    public void readAllFile(String file) {
        try {
            //Tạo đối tượng của lớp fileReader
            //csv file là một tham số
            FileReader fileReader = new  FileReader(file);
            //tạo đối tượng của csvReader
            CSVReader csvReader = new
                    CSVReaderBuilder(fileReader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();
            for (String[] row : allData) {
                for(String cell : row) {
                    System.out.println(cell+ "\t");
                }
                System.out.println();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
