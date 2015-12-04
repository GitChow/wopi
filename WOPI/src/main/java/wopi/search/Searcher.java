package wopi.search;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import wopi.model.WANOrderDetails;

@ManagedBean(name = "searcher")
@SessionScoped
public class Searcher {
	public String input;
	private List<WANOrderDetails> results = new ArrayList<WANOrderDetails>();

	private List<WANOrderDetails> allData = new ArrayList<WANOrderDetails>();

	public String column = "Connection ID";

	private String oldPath = "D:\\WAN Order Tool\\Data\\open_orders.xlsx";

	public void search() throws IOException, ParseException {
		FacesContext context = FacesContext.getCurrentInstance();

		this.results.clear();
		if (this.input.isEmpty()) {
			context.addMessage(null, new FacesMessage("Notice", "Please make sure input is not empty"));
			return;
		}

		loadAllDataFromExcel();

		List<String> failedOnes = new ArrayList<String>();
		String s = this.input.trim().toLowerCase();
		boolean flagFound = false;
		for (WANOrderDetails item : this.allData) {

			if (item.rowData.toLowerCase().indexOf(s) >= 0) {
				flagFound = true;
				this.results.add(item);
			}
		}
		if (!flagFound) {
			failedOnes.add(s);
		}

		if (failedOnes.size() > 0) {
			String FailedOnesStr = failedOnes.toString();
			context.addMessage(null, new FacesMessage("Notice", "No records found:   " + FailedOnesStr.substring(1, FailedOnesStr.length() - 1)));
		}
	}

	public void inputStringOptimize() {
		String[] inputArray = this.input.split(",");
		String inputArrayAfterTrim = "";
		for (String inputArrayItem : inputArray) {
			inputArrayAfterTrim = inputArrayAfterTrim + "," + inputArrayItem.trim();
		}
		this.input = inputArrayAfterTrim.substring(1);
	}

	public void loadAllDataFromExcel() throws IOException, ParseException {
		this.allData.clear();
		Sheet sheet = null;
		FileInputStream file = new FileInputStream(new File(this.oldPath));
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		sheet = workbook.getSheetAt(0);

		Row headerRow = sheet.getRow(1);// get name row

		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();

			WANOrderDetails item = new WANOrderDetails();
			Iterator<Cell> cellIterator = row.cellIterator();
			String everyRowdata = "";
			String area = "";
			while (cellIterator.hasNext()) {
				Cell cell = (Cell) cellIterator.next();
				int columnNum = cell.getColumnIndex();
				if (headerRow.getCell(columnNum) != null) {
					String header = headerRow.getCell(columnNum).toString().replaceAll("[\n\r]", "");
					if (header.equalsIgnoreCase("Connection ID")) {

						String value = "";
						if (cell.getCellType() == 0) {
							int number = (int) cell.getNumericCellValue();
							value = String.valueOf(number);
						}

						if (!value.equalsIgnoreCase("Connection ID")) {
							item.setNumber(value);
							everyRowdata += value;
						}

					}
					if (header.equalsIgnoreCase("Order Status")) {
						if (!cell.toString().trim().equalsIgnoreCase("Order Status")) {
							item.setStatus(cell.toString().trim());
							everyRowdata += cell.toString().trim();
						}
					}
					if (header.equalsIgnoreCase("Network")) {
						if (!cell.toString().trim().equalsIgnoreCase("Network")) {
							area = cell.toString().trim();
						}
					}
					if (header.equalsIgnoreCase("Company Name")) {
						if (cell.getCellType() == 2) {
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						}
						if (!cell.toString().equalsIgnoreCase("Company Name")) {
							item.setCompany(cell.toString());
							everyRowdata += cell.toString();
						}
					}
					if (header.equalsIgnoreCase("BCN Coordinator")) {
						if (cell.getCellType() == 2) {
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						}
						if (!cell.toString().equalsIgnoreCase("BCN Coordinator")) {
							item.setContact(cell.toString());
							everyRowdata += cell.toString();
						}
					}

					if (header.equalsIgnoreCase("Request from Customer")) {

						if (!cell.toString().trim().replaceAll("[\n\r]", "").equalsIgnoreCase("Request from Customer")) {
							// item.setDateCustomerRequest(format.parse(cell.toString().trim()));
							item.setDateCustomerRequest(cell.toString().trim());
							everyRowdata += cell.toString().trim();
						}

					}
					if (header.equalsIgnoreCase("Price info request to carrier")) {
						if (!cell.toString().trim().replaceAll("[\n\r]", "").equalsIgnoreCase("Price info request to carrier")) {
							// item.setDatePriceRequestToCarrier(format.parse(cell.toString().trim()));
							item.setDatePriceRequestToCarrier(cell.toString().trim());
							everyRowdata += cell.toString().trim();
						}
					}

					if (header.equalsIgnoreCase("Planned installation date")) {
						if (!cell.toString().trim().replaceAll("[\n\r]", "").equalsIgnoreCase("Planned installation date")) {
							// item.setDatePriceRequestToCarrier(format.parse(cell.toString().trim()));

							item.setPlannedInstallationDate(cell.toString().trim());
							everyRowdata += cell.toString().trim();
						}
					}

					if (header.equalsIgnoreCase("Country Code")) {
						if (cell.getCellType() == 2) {
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						}
						if (!cell.toString().trim().replaceAll("[\n\r]", "").equalsIgnoreCase("Country Code")) {
							item.setCountryCode(cell.toString());

							everyRowdata += cell.toString().trim();
						}
					}

					if (header.equalsIgnoreCase("Location Code")) {
						if (cell.getCellType() == 2) {
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						}
						if (!cell.toString().trim().replaceAll("[\n\r]", "").equalsIgnoreCase("Location Code")) {
							item.setLocatioCode(cell.toString());

							everyRowdata += cell.toString().trim();
						}
					}

				}
			}
			item.setRowData(everyRowdata);
			item.setPicture(area + item.getStatus());
			if ((item.number != null) && (item.number != "")) {
				this.allData.add(item);
			}
		}
	}

	public List<WANOrderDetails> getAllData() {
		return this.allData;
	}

	public void setAllData(List<WANOrderDetails> allData) {
		this.allData = allData;
	}

	public List<WANOrderDetails> getResults() {
		return this.results;
	}

	public void setResults(List<WANOrderDetails> results) {
		this.results = results;
	}

	public String getInput() {
		return this.input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getOldPath() {
		return oldPath;
	}

	public void setOldPath(String oldPath) {
		this.oldPath = oldPath;
	}

	public static int getWorkingDaysBetweenTwoDates(Date startDate, Date endDate) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);

		int workDays = 0;

		// Return 0 if start and end are the same
		if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
			return 0;
		}

		if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
			startCal.setTime(endDate);
			endCal.setTime(startDate);
		}

		do {
			// excluding start date
			startCal.add(Calendar.DAY_OF_MONTH, 1);
			if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				++workDays;
			}
		} while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); // excluding

		return workDays;
	}

}
