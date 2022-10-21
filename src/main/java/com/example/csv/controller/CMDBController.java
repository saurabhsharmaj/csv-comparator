package com.example.csv.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.csv.entity.CMDBEntity;
import com.example.csv.entity.LocalEntity;
import com.example.csv.entity.ResonseVO;
import com.example.csv.repository.CMDBRepository;
import com.example.csv.repository.LocalRepository;
import com.example.csv.util.ExcelHelper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RestController
public class CMDBController {

	@Autowired
	CMDBRepository cMDBRepository;
	
	@Autowired
	LocalRepository localRepository;

	@Autowired
	ExcelHelper excelHelper;

	@RequestMapping(value = "/v1.0/appopsservice/cmdb/fileupload", method = RequestMethod.POST)
	public String createUpdateFeedback(@RequestParam("file") MultipartFile []files) throws Exception {
		JsonObject json = new JsonObject();
		ResonseVO res = new ResonseVO();
		List<CMDBEntity> clist = new ArrayList<CMDBEntity>();
		boolean flag =false;
		for (MultipartFile mfile : files) {
			//File file = new File("C:\\Users\\saursharma\\Downloads\\csv\\temp\\test.xlsx");
		
			if(flag==false) {
				clist = excelHelper.excelToTutorials(mfile.getInputStream(),flag);
				for (CMDBEntity cmdbEntity : clist) {
					if(cmdbEntity.getName().equalsIgnoreCase(null))
						continue;
					cMDBRepository.save(cmdbEntity);
				}
				flag=true;
			} else {
				List<LocalEntity> LocalList = excelHelper.excelToTutorials(mfile.getInputStream(),flag);
				for (LocalEntity local : LocalList) {
					if(local.getName().equalsIgnoreCase(null))
						continue;
					localRepository.save(local);
				}
				flag=false;
			}

			List<CMDBEntity>cmdbList=cMDBRepository.findAllDiffCMDBEntities();
			
			List<LocalEntity>localList=localRepository.findAllDiffLocalEntities();
			res.setCmdbList(cmdbList);
			res.setLocalList(localList);
		}
		return new Gson().toJson(res).toString();
	}
}