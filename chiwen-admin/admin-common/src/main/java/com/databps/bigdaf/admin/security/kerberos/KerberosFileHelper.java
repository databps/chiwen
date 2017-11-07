package com.databps.bigdaf.admin.security.kerberos;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by shibingxin on 2017/7/18.
 */

public class KerberosFileHelper {

	public static void buildKrb5Conf(Map<String, String> replaceMap, String sourcePath, String targetPath) throws IOException {
		BufferedReader bufferedReader = Files.newReader(new File(sourcePath), Charsets.UTF_8);
		String temp;
		StringBuffer sb = new StringBuffer();
		while ((temp = bufferedReader.readLine()) != null) {
			sb.append(temp);
			sb.append(System.getProperty("line.separator"));
		}
		String tempString = sb.toString();
		Iterator<Map.Entry<String, String>> it = replaceMap.entrySet().iterator();  
		while (it.hasNext()) {  
		   Map.Entry<String, String> entry = it.next();
			tempString = tempString.replace("{{"+entry.getKey()+"}}", entry.getValue());
		}  
		createFile(targetPath);
		File file = new File(targetPath);
		Files.write(tempString, file, Charsets.UTF_8);
	}


	public static void createFile(String destFileName) throws IOException {
		File file = new File(destFileName);
		if (file.exists()) {
			return;
		}
		if (destFileName.endsWith(File.separator)) {
			return;
		}
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				return;
			}
		}
		// 创建目标文件
		file.createNewFile();
		return;
	}

}