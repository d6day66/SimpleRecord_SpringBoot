package cn.jackbin.SimpleRecord.utils;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class BusinessLicenseOCR {
    public static void main(String[] args) {
        // 创建Tesseract实例
        Tesseract tesseract = new Tesseract();

        try {
            // 设置OCR语言为中文
            tesseract.setLanguage("chi_sim");

            // 读取待识别的营业执照图片文件
            File imageFile = new File("test1.jpg");

            // 执行OCR操作并输出识别结果
            String result = tesseract.doOCR(imageFile);
            System.out.println(result);

            // 从OCR输出中提取公司名称和注册号码信息
            String companyName = result.split("名称")[1].split("\n")[0].trim();
            String registrationNumber = result.split("注册号码")[1].split("\n")[0].trim();

            // 输出公司名称和注册号码信息
            System.out.println("公司名称：" + companyName);
            System.out.println("注册号码：" + registrationNumber);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}
