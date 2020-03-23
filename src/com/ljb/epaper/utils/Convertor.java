package com.ljb.epaper.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;






public class Convertor {
	public static boolean PDFtoHTML(String pdfPath, String HTMLPath) throws IOException, InterruptedException {
        final String METHOD_NAME = "runCMD";
        String[] cmd = {"D:\\Program Files\\docpub\\docpub32.exe", "-f","html",pdfPath, "-o", HTMLPath};
        
        Process p = Runtime.getRuntime().exec(cmd);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String readLine = br.readLine();
            StringBuilder builder = new StringBuilder();
            while (readLine != null) {
                readLine = br.readLine();
                builder.append(readLine);
            }
            Log.log(METHOD_NAME + "#readLine: " + builder.toString());

            p.waitFor();
            int i = p.exitValue();
            Log.log(METHOD_NAME + "#exitValue = " + i);
            if (i == 0) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
        	Log.log(METHOD_NAME + "#ErrMsg=" + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

	public static void main(String[] args) {

		try {
			Log.log(""+Convertor.PDFtoHTML("F:\\ServerData\\PDF\\2020-03-21\\20200321a01_pdf.pdf", "F:\\ServerData\\HTML\\2020-03-21\\"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
