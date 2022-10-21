package com.example.csv.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import com.example.csv.entity.CMDBEntity;
import com.example.csv.entity.LocalEntity;

@Configuration
public class ExcelHelper {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Name", "IP Address", "Status", "Operational status", "Environment", "Created",
			"Most recent discovery", "Application" };
	static String SHEET = "CRM VMs";

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static List excelToTutorials(InputStream is, boolean flag) {
		try {
			Workbook workbook = new XSSFWorkbook(is);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> rows = sheet.iterator();

			if (flag == false) {
				List<CMDBEntity> tutorials = new ArrayList<CMDBEntity>();

				int rowNumber = 0;
				while (rows.hasNext()) {
					Row currentRow = rows.next();

					// skip header
					if (rowNumber == 0) {
						rowNumber++;
						continue;
					}

					Iterator<Cell> cellsInRow = currentRow.iterator();

					CMDBEntity tutorial = new CMDBEntity();

					int cellIdx = 0;
					// {0=ch3lxcrmaspc02, 1=192.168.122.1, 2=Installed, 3=Operational, 4=PACEDRUAT,
					// 5=02-Sep-2022, 6=10-Oct-2022, 7=Siebel CRM}
					// Name IP Address Status Operational status Environment Created Most recent
					// discovery Application

					while (cellsInRow.hasNext()) {
						Cell currentCell = cellsInRow.next();

						switch (cellIdx) {

						case 0:
							tutorial.setName(extractValues(currentCell));
							break;

						case 1:
							tutorial.setIpAddress(currentCell.getRichStringCellValue().getString());
							break;

						case 2:
							tutorial.setStatus(extractValues(currentCell));
							break;

						case 3:
							tutorial.setOperationalStatus(extractValues(currentCell));
							break;
						case 4:
							tutorial.setEnvironment(extractValues(currentCell));
							break;
						case 5:
							tutorial.setCreated(extractDateValues(currentCell));
							break;
						case 6:
							tutorial.setMostRecentDiscovery(extractDateValues(currentCell));
							break;
						case 7:
							tutorial.setApplication(extractValues(currentCell));
							break;
						default:
							break;
						}

						cellIdx++;
					}

					tutorials.add(tutorial);
				}

				workbook.close();

				return tutorials;
			} else {
				List<LocalEntity> locallist = new ArrayList<LocalEntity>();

				int rowNumber = 0;
				while (rows.hasNext()) {
					Row currentRow = rows.next();

					// skip header
					if (rowNumber == 0) {
						rowNumber++;
						continue;
					}

					Iterator<Cell> cellsInRow = currentRow.iterator();

					LocalEntity tutorial = new LocalEntity();

					int cellIdx = 0;
					while (cellsInRow.hasNext()) {
						Cell currentCell = cellsInRow.next();

						switch (cellIdx) {

						case 0:
							tutorial.setName(extractValues(currentCell));
							break;

						case 1:
							tutorial.setIpAddress(currentCell.getRichStringCellValue().getString());
							break;

						case 2:
							tutorial.setStatus(extractValues(currentCell));
							break;

						case 3:
							tutorial.setOperationalStatus(extractValues(currentCell));
							break;
						case 4:
							tutorial.setEnvironment(extractValues(currentCell));
							break;
						case 5:
							tutorial.setCreated(extractDateValues(currentCell));
							break;
						case 6:
							tutorial.setMostRecentDiscovery(extractDateValues(currentCell));
							break;
						case 7:
							tutorial.setApplication(extractValues(currentCell));
							break;
						default:
							break;
						}

						cellIdx++;
					}

					locallist.add(tutorial);
				}

				workbook.close();

				return locallist;
			}

		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}

	private static String extractValues(Cell currentCell) {
		try {
			return currentCell.getStringCellValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	private static Date extractDateValues(Cell currentCell) {
		try {
			return currentCell.getDateCellValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}