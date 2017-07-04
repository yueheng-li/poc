package com.auto.myte.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.auto.myte.beans.Message;
import com.auto.myte.common.message.SpringMessageResourceMessages;
import com.auto.myte.config.PropertiesConfig;
import com.auto.myte.entity.ReceiptInfo;
import com.auto.myte.service.ReceiptInfoService;
import com.auto.myte.utils.AZureOcrUtils;
import com.auto.myte.utils.CommonUtils;
import com.auto.myte.utils.DateUtils;
import com.auto.myte.utils.ParseJsonUtils;

@Controller
@RequestMapping("/")
public class IndexController {
	private static Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private PropertiesConfig propertiesConfig;

	@Autowired
	private SpringMessageResourceMessages messageSource;

	@Autowired
	private ReceiptInfoService service;

	/**
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/")
	public String home(Model model) throws Exception {
		
		model.addAttribute("submitDate", DateUtils.getMyteEndDateTime());
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.info(userDetails.getUsername());
		return "list";
	}
	
	/**
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/admin")
	public String admin(Model model) throws Exception {
		return "admin";
	}

	/**
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("submitDate", DateUtils.getMyteEndDateTime());
		return "list";
	}

	/**
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/detail")
	public String index(@RequestParam(value = "id", required = false) String id, Model model) throws Exception {
		logger.info("id = " + id);
		model.addAttribute("id", id);
		return "detail";
	}

	/**
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/uploadfile")
	public String uploadFile(Model model) throws Exception {
		model.addAttribute("submitDate", DateUtils.getMyteEndDateTime());
		return "list";
	}

	/**
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/uploadImage", method = RequestMethod.GET)
	public String uploadImageGet(Model model) throws Exception {
		model.addAttribute("submitDate", DateUtils.getMyteEndDateTime());
		return "list";
	}

	/**
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/uploadImageMulti", method = RequestMethod.GET)
	public String uploadImageMultiGet(Model model) throws Exception {
		model.addAttribute("submitDate", DateUtils.getMyteEndDateTime());
		return "list";
	}

	@RequestMapping(value = "/uploadImageMulti", method = RequestMethod.POST)
	public String uploadImageMulti(HttpServletRequest request, @RequestParam("myFile") MultipartFile[] file,@RequestParam("submitDate") String submitDate,
			Model model) throws Exception {
		model.addAttribute("submitDate", submitDate);

		StringBuilder sbInfo = new StringBuilder();
		StringBuilder sbError = new StringBuilder();
		for (MultipartFile multipartFile : file) {
			Message message = this.uploadFIle(multipartFile);

			if (message != null) {

				if (StringUtils.isNotBlank(message.getError())) {
					model.addAttribute("messageImage", message.getError());
					sbError.append(message.getError());
					// sbError.append("&lt;br/&gt;");
				}
				if (StringUtils.isNotBlank(message.getInfo())) {
					model.addAttribute("messageInfo", message.getInfo());
					;
					sbInfo.append(message.getInfo());
					// sbInfo.append("&lt;br/&gt;");
				}
			}
		}
		model.addAttribute("messageImage", sbError.toString());
		model.addAttribute("messageInfo", sbInfo.toString());
		;
		return "list";
	}

	/**
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public String uploadImage(HttpServletRequest request, @RequestParam("myFile") MultipartFile file, Model model)
			throws Exception {
		model.addAttribute("submitDate", DateUtils.getMyteEndDateTime());
		Message message = this.uploadFIle(file);
		if (StringUtils.isNotBlank(message.getError())) {
			model.addAttribute("messageImage", message.getError());
		}
		if (StringUtils.isNotBlank(message.getInfo())) {
			model.addAttribute("messageInfo", message.getInfo());
		}
		return "list";
	}

	private Message uploadFIle(MultipartFile file) throws IOException {
		Message message = null;
		if (!file.isEmpty()) {
			message = new Message();
			String fileName = file.getOriginalFilename();
			if (CommonUtils.isImage(fileName)) {
				// Now do something with file...
				FileCopyUtils.copy(file.getBytes(), new File(propertiesConfig.getFilepath() + fileName));

				JSONObject entity = AZureOcrUtils.getOcrRespone(file.getInputStream());
				List<String> textList = ParseJsonUtils.parseJson(entity);

				// call ocr api
				ReceiptInfo receipt = new ReceiptInfo();
				StringBuilder status = new StringBuilder();

				String taxi = ParseJsonUtils.pareseText(textList, CommonUtils.TAXI_REGEX);
				if (StringUtils.isNotBlank(taxi)) {
					// category
					receipt.setCategory_id("1");
					// Receipt Name
					receipt.setName("Taxi");
				} else {
					receipt.setCategory_id("2");
					// Receipt Name
					receipt.setName("MealsEntertainment");
				}
				if ("Β-Τ".equals(taxi)) {
					receipt.setLocation("cn");
					String cnAmount = ParseJsonUtils.parserCnAmount(textList);
					receipt.setAmount(cnAmount);
				} else {
					receipt.setLocation("ja");

					String price = ParseJsonUtils.pareseText(textList, CommonUtils.PRICE_REGEX);
					if (StringUtils.isBlank(taxi)) {
						price = ParseJsonUtils.pareseTextByMeals(textList, CommonUtils.PRICE_REGEX1);
					}
					if (StringUtils.isNotBlank(price)) {

						// 金額
						receipt.setAmount(CommonUtils.getAmount(price));
					} else {
						status.append("金額認識処理異常。\\n");
						receipt.setAmount("0");
					}
				}
				String date = ParseJsonUtils.pareseText(textList, CommonUtils.DATE_REGEX);
				if (StringUtils.isNotBlank(date)) {
					// 日付
					receipt.setDate(CommonUtils.convertYyyyMmdd(date));
				} else {
					status.append("日付認識処理異常。認識日を使っている。");
					receipt.setDate(DateUtils.getToday());
				}

				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
				// 登録者
				receipt.setEid(userDetails.getUsername());
				// Image URL
				receipt.setImage_name(fileName);
				if (StringUtils.isBlank(status.toString())) {
					status.append("正常処理された。");
				}
				receipt.setStatus(status.toString());
				receipt.setImage_url(propertiesConfig.getImageUrl() + fileName);
				receipt.setSubmit_date(DateUtils.getMyteEndDateTime());
				int insertFlag = service.insertReceiptInfo(receipt);
				if (insertFlag > 0) {
					message.setInfo(fileName + " upload is ok.");// model.addAttribute("messageImage",
																	// "upload
																	// is ok.");
				} else {
					message.setError(fileName + " upload is ng.");

				}
			} else {
				message.setError("please upload image file." + fileName + " is not image type");
			}
		}
		return message;
	}
	/**
	 * Handle request to download an Excel document
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			File file = ResourceUtils.getFile("classpath:template.xlsx");
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
			Sheet sheet = wb.getSheetAt(0);
			List<ReceiptInfo> receiptInfoList = service.getAllReceiptByEid(userDetails.getUsername());
			int i = 1;
			int row = 2;
			for (ReceiptInfo receiptInfo : receiptInfoList) {
				// No.
				sheet.getRow(row).getCell(1).setCellValue(i);
				// Receipt type
				sheet.getRow(row).getCell(2).setCellValue(receiptInfo.getCategory_name());
				// Country of Expense
				sheet.getRow(row).getCell(3).setCellValue("ja");
				// CNY
				sheet.getRow(row).getCell(4).setCellValue("Ja");
				// Amount
				sheet.getRow(row).getCell(5).setCellValue(receiptInfo.getAmount());
				// On
				sheet.getRow(row).getCell(6).setCellValue(receiptInfo.getDate());
				// Reason
				String reason = "";
				if ("1".equals(receiptInfo.getCategory_id())) {
					reason = "Client Site/Other<-> Other/Client Site";
				} else if ("2".equals(receiptInfo.getCategory_id())) {
					reason = "Oviertime Meal Allowance";
				}
				sheet.getRow(row).getCell(7).setCellValue(reason);
				i++;
				row++;
			}

			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);

		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String((userDetails.getUsername() + ".xlsx").getBytes(), "iso-8859-1"));

		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}

}
