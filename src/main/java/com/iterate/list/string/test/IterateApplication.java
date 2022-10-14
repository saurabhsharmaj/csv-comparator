package com.iterate.list.string.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;
import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import com.google.gson.JsonObject;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@SpringBootApplication
@Controller
public class IterateApplication {

	public static void main(String[] args) {
		SpringApplication.run(IterateApplication.class, args);
	}

	@RequestMapping({"/","/home"})
	public String check(Model model) {		
		return "index";
	}
	
	@RequestMapping(value = "/v1.0/appopsservice/cmdb/fileupload", method = RequestMethod.POST)
	public String createUpdateFeedback(@RequestParam("file") MultipartFile[] files,Model model) {
		JsonObject response=new JsonObject();
		CMDBComparision cmdb = new CMDBComparision();
		boolean flag=false;
		List<CMDBInventoryVO> inventory = new ArrayList<CMDBInventoryVO>();
		for (MultipartFile file : files) {
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
					if(flag==false) {
						cmdb.setLeft(csvToBean.parse());
						flag=true;
					}else {
						cmdb.setRight(csvToBean.parse());
					}
					response.addProperty("status", true);
				} catch (Exception ex) {
					response.addProperty("message", "An error occurred while processing the CSV file.");
					response.addProperty("status", false);
				}
			}
		}
		
		List<DiffRow> diff = diff(cmdb.getLeft().stream().map(c->c.getHostname()).collect(Collectors.toList()),cmdb.getRight().stream().map(c->c.getHostname()).collect(Collectors.toList()));
		model.addAttribute("diff", diff);
		model.addAttribute("inventory", cmdb);
        
        return "index";
	}
	
	private List<DiffRow> diff(List<String> original,List<String> revised) {
		
		DiffRowGenerator generator = DiffRowGenerator.create().showInlineDiffs(true).inlineDiffByWord(true)
				.oldTag(f -> "~").newTag(f -> "**").build();
		List<DiffRow> rows = generator.generateDiffRows(original,revised);
		return rows;
//		System.out.println("|original|new|");
//		System.out.println("|-----------------------------|---------|");
//		for (DiffRow row : rows) {
//			System.out.println(row.getTag()+"|" + row.getOldLine() + "|" + row.getNewLine() + "|");
//		}
	}
	
}
