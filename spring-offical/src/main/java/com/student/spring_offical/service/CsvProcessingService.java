package com.student.spring_offical.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class CsvProcessingService {
    public Resource removeDuplicatesFromCsv(MultipartFile file) throws IOException, CsvException {

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
        Set<List<String>> uniquesData = new LinkedHashSet<>();
            for(String[] row : allData) {
                uniquesData.add(Arrays.asList(row));
            }
        //Ghi file vào bộ nhớ
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        CSVWriter csvWrite = new CSVWriter(outputStreamWriter);
        //Chuyển kiểu dữ liệu trong Set về String[] để đúng định dạng WriterNext();
        try (OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
             CSVWriter csvWriter = new CSVWriter(streamWriter)) {

            for (List<String> row : uniquesData) {
                // Chuyển lại thành mảng String[]
                // (dùng new String[0] là cách làm phổ biến)
                csvWriter.writeNext(row.toArray(new String[0]));
            }
        }


        return new ByteArrayResource(outputStream.toByteArray());
    }
  public Resource removeDuplicatesFromCsvChat(MultipartFile file) throws IOException, CsvException {
      // Đọc dữ liệu từ file CSV
      List<String[]> allData;
      try (Reader fileReader = new InputStreamReader(file.getInputStream());
           CSVReader csvReader = new CSVReader(fileReader)) {
          allData = csvReader.readAll();
      }

      // Lọc dữ liệu trùng lặp
      Set<List<String>> uniqueData = new LinkedHashSet<>();
      for (String[] row : allData) {
          uniqueData.add(Arrays.asList(row));
      }

      // Ghi dữ liệu đã lọc vào bộ nhớ
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      try (OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream, "UTF-8");
           CSVWriter csvWriter = new CSVWriter(streamWriter)) {
          for (List<String> row : uniqueData) {
              csvWriter.writeNext(row.toArray(new String[0]));
          }
      }

      return new ByteArrayResource(outputStream.toByteArray());
  }
public List<String[]> previewCsv(MultipartFile file,int maxRows) throws IOException, CsvException {
        List<String[]> preview = new ArrayList<>();
        try(Reader fileReader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
            CSVReader csvReader = new CSVReaderBuilder(fileReader).build()) {
            String[] row;
            int count =0;
            while((row = csvReader.readNext()) != null && count < maxRows) {
                preview.add(row);
                count++;
            }
        }
        return preview;
}
}
