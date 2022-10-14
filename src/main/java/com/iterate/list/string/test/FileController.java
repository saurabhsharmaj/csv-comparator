package com.iterate.list.string.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@RestController
public class FileController {

	@RequestMapping(value = "/v1.0/appopsservice/cmdb/fileupload1", method = RequestMethod.POST)
	@ResponseBody
	public String createUpdateFeedback(@RequestParam("file") MultipartFile file) {
		JsonObject response=new JsonObject();
        if (file.isEmpty()) {
        	response.addProperty("message", "Please select a CSV file to upload.");
        	response.addProperty("status", false);
        } else {

            // parse CSV file to create a list of `User` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                // create csv bean reader
                CsvToBean<CMDBInventoryVO> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(CMDBInventoryVO.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                // convert `CsvToBean` object to list of users
                List<CMDBInventoryVO> users = csvToBean.parse();

                // TODO: save users in DB?

                // save users list on model
               // response.addProperty("users", users);
                response.addProperty("status", true);

            } catch (Exception ex) {
            	response.addProperty("message", "An error occurred while processing the CSV file.");
            	response.addProperty("status", false);
            }
        }

        return response.toString();
	}
}
