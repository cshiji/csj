package com.cshiji.superviki.base.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;


//import org.apache.commons.id.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 字符串处理类
 */
public class StringUtil {
	static Log logger = LogFactory.getLog(StringUtil.class);
	
	/**
	 * 获取32位随机数唯一ID
	 * @return
	 */
	public static String getUUIDKey(){
		//return new String(Hex.encodeHex(org.apache.commons.id.uuid.UUID.randomUUID().getRawBytes()));
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}
	
	/**
	 * 判断字符串是否为Null或空串
	 * @param str	要判断的字符串
	 * @param bool	是否不判断"NULL"字符串（true,不判断）
	 * @return boolean
	 */
	public static boolean isEmpty(String str,boolean bool) {
		if(bool){
			return (str == null || str.length() == 0);
		}else{
			return (str == null || str.length() == 0 || str.trim().equalsIgnoreCase("NULL"));
		}
	}
	

	/**
	 * 判断字符串是否为Null或空串或"NULL"字符串
	 * @param str	要判断的字符串
	 * @return boolean
	 */
	public static boolean isEmpty(String str) {
		return isEmpty(str,false);
	}
	
	/**
	 * 判断字符串是否全部非空
	 * @param str[]	要判断的字符串
	 * @return boolean
	 */
	public static boolean isAllNotEmpty(String... str) {
		if (str == null || str.length == 0) {
			return false;
		}
		for (String s : str) {
			if(isEmpty(s)){//有一个为空，则为false
				return false;
			}
		}
		
		return true;
	}	

	/**
	 * 判断字符串是否为空
	 * @param str	要判断的字符串
	 * @return boolean
	 */
	public static boolean isNull(String str) {
		return str == null;
	}


	
    /**
     * 压缩字符串的左边空格
     * @param value ：源串
     * @return String
     */
    public static String trimLeft(String value) {
        if (value == null)
            return "";
        String result = value;
        char ch[] = result.toCharArray();
        int index = -1;
        for (int i = 0; i < ch.length; i++) {
            if (Character.isWhitespace(ch[i])) {
                index = i;
            } else {
                break;
            }
        }
        if (index != -1) {
            result = result.substring(index + 1);
        }
        return result;
    }

    /**
     * 压缩字符串的右边空格
     * @param value ：源串
     * @return String 
     */
    public static String trimRight(String value) {
        if (value == null)
            return "";
        String result = value;
        char ch[] = result.toCharArray();
        int endIndex = -1;
        for (int i = ch.length - 1; i > -1; i--) {
            if (Character.isWhitespace(ch[i])) {
                endIndex = i;
            } else {
                break;
            }
        }
        if (endIndex != -1) {
            result = result.substring(0, endIndex);
        }
        return result;
    }

    /**
	 * 将输入的BigDecimal对象转换成保留两位小数的字符串。
	 * @param inputData
	 * @return
	 */
	public static String getDoubleValueString(BigDecimal inputData)
	{
		if (inputData == null)
		{
			return "";
		}
		DecimalFormat formatter = new DecimalFormat("0.00");
		return formatter.format(inputData.doubleValue());
	}

	/**
	 * 内码转化
	 * @param days
	 * @return
	 * @throws Exception 
	 */
	public static String code8859toutf8(String inStr) throws Exception
	{

		String retStr;
		if (inStr == null || inStr.trim().equals(""))
			return inStr;
		try
		{
			retStr = new String(inStr.getBytes("ISO-8859-1"), "utf-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			throw new Exception("不支持的代码转化");
		}
		return retStr;
	}

	/**
		* 内码转化
		* @param days
		* @return
	 * @throws Exception 
		*/
	public static String codeGBKto8859(String inStr) throws Exception
	{

		String retStr;
		if (inStr == null || inStr.trim().equals(""))
			return inStr;
		try
		{
			retStr = new String(inStr.getBytes("GBK"), "ISO-8859-1");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			throw new Exception("不支持的代码转化");
		}
		return retStr;
	}
	/**
	 * 内码转化
	 * @param days
	 * @return
	 */
	public static String code8859toGBK(String inStr) throws Exception
	{

		String retStr;
		if (inStr == null || inStr.trim().equals(""))
			return inStr;
		try
		{
			retStr = new String(inStr.getBytes("ISO-8859-1"), "GBK");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			throw new Exception("不支持的代码转化");
		}
		return retStr;
	}

	/**
	 * 判断 字符串messageContent 是否包含子串 delim
	 * @param messageContent
	 * @param delim
	 * @return  true :包含  false :不包含
	 */
	public static boolean hasMoreItem(StringBuffer messageContent, String delim)
	{
		if (messageContent == null)
			return false;
		if (delim == null)
			return false;
		if (messageContent.equals(""))
			return false;
		if (delim.equals(""))
			return false;

		String s = new String(messageContent);
		if (s.indexOf(delim) >= 0)
			return true;
		else
			return false;
	}
	
	/**
	 * 去掉字符串的尾部空格，如果输入的字符串对象为空，则返回“”
	 * @param strSrc
	 * @return
	 */
	public static String safeTrim(String strSrc)
	{
		if (strSrc == null)
		{
			return "";

		}
		else
		{
			return strSrc.trim();
		}
	}
	
	/**
	 * 如果传入字符串为null或""或"null"或"NULL"时返回默认串
	 * @param str		要处理字符串
	 * @param defValue	默认返回串
	 * @return
	 */
	public static String getString(String str, String defValue) {
		if (str == null || "".equals(str) || "null".equals(str)
				|| "NULL".equals(str))
			return defValue;
		else
			return str;
	}
	
	/**
	 * 如果传入字符串为null或""或"null"或"NULL"时返回""
	 * @param str	要处理字符串
	 * @return
	 */
	public static String getString(String str) {
		return getString(str,"");
	}
	
	/**
	 * 如果传入字符串为null或""或"null"或"NULL"时返回默认串
	 * @param str
	 * @param defValue	默认返回串
	 * @return
	 */
	public static String getString(Object str, String defValue) {
		return getString(String.valueOf(str), defValue);
	}
	
	/**
	 * 如果传入字符串为null或""或"null"或"NULL"时返回""
	 * @param obj
	 * @return
	 */
	public static String getString(Object obj) {
		return getString(obj, "");
	}
	
	/**
	 * 判断字符串是否为GBK编码
	 * @param string	输入串
	 * @return
	 */
	public static boolean isGBK(String string) {
		byte[] bytes = null;
		try {
			bytes = string.replace('?', 'a').getBytes("ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			return false;
		}
		for (int i = 0; i < bytes.length; ++i)
			if (bytes[i] == 63)
				return true;

		return false;
	}
	
	/**
	 * 字符串转为GBK
	 * @param string
	 * @return
	 */
	public static String toGBK(String string) {
		if (string == null)
			return "";
		if (!(isGBK(string))) {
			String str = "";
			try {
				str = new String(string.getBytes("ISO-8859-1"), "GBK");
				return str;
			} catch (UnsupportedEncodingException e) {
				return string;
			}
		}
		return string;
	}
	
	
	/**
	 * 此方法将给出的字符串source使用delim划分为单词数组。
	 * @param source 需要进行划分的原字符串
	 * @param delim  单词的分隔字符串
	 * @return 划分以后的数组，如果source为null的时候返回以source为唯一元素的数组，
	 *         如果delim为null则使用逗号作为分隔字符串。
	 */
	public static String[] split(String source, String delim) {
		String[] wordLists;
		if (source == null) {
			wordLists = new String[1];
			wordLists[0] = source;
			return wordLists;
		}
		if (delim == null) {
			delim = ",";
		}
		StringTokenizer st = new StringTokenizer(source, delim);
		int total = st.countTokens();
		wordLists = new String[total];
		for (int i = 0; i < total; i++) {
			wordLists[i] = st.nextToken();
		}
		return wordLists;
	}
	
	/**
	 * 将字符串转换为整型数
	 * @param str
	 * @param defaultValue	出现异常时的默认值
	 * @return
	 */
	public static int getInt(String str, int defaultValue) {
		int returnInt = defaultValue;

		try {
			returnInt = Integer.parseInt(str);
		} catch (Exception e) {
			returnInt = defaultValue;
		}
		return returnInt;
	}
	
	/**
	 * json字符串转map
	 * @param json	{key:value,key2:value2,key3:value3....}
	 * @return	Map<String, String>
	 */
	public static Map<String, String> jsonStrToMap(String json){
		Map<String, String> map = new HashMap<String, String>();
		if(json.startsWith("{") && json.endsWith("}")){
			json = json.substring(1,json.length()-1);
		}
		String[] array = json.split(",");
		String[] arrayIn;
		for (String str : array) {
			arrayIn = str.split(":");
			if(arrayIn.length != 2){
				logger.error(str+"结构不正确就为xxx:yyy");
			}else{
				map.put(arrayIn[0].trim(), arrayIn[1].trim());
			}
		}
		return map;
	}
	
	/**
	 * List转string数组
	 * @param element
	 * @return
	 */
    public static String[] listToStrArray(List element) {

        if (element == null || element.size() == 0) return null;

        Iterator it = element.iterator();
        String[] strArray = new String[element.size()];
        int i = 0;

        while (it.hasNext()) {
            strArray[i] = String.valueOf(it.next());
            i++;
        }
        return strArray;
    }	
    
    /**
     * string加分割符转List
     * @param str
     * @param separator
     * @return
     */
    public static List strToList(String str, String separator) {

        if (str == null || str.equals("")) return null;
        if (separator == null) separator = "";

        String[] strArr = str.split(separator);
        int size = strArr.length;
        List list = new ArrayList();

        for (int i = 0; i < size; i++) {
            list.add(strArr[i]);
        }
        return list;
    }    
    
    /**
     * 回车换行\r\n替换为<br/>
     * @param str
     * @return
     */
    public static String replaceCrlfToBr(String str){
    	if(StringUtil.isEmpty(str))return str;
    	return str.replaceAll("\\r\\n", "<br/>").replaceAll("\\n", "<br/>").replaceAll("\r", "<br/>");
    }
    
    /**
     * js的escape java实现
     * @param src	源字符串
     * @return
     */
    public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

    /**
     * js的unescape java实现
     * @param src	源字符串
     * @return
     */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src
							.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src
							.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	
	/**
	 * 判断字符串是否包含中文
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		return str.getBytes().length != str.length();
	}
	
	/**
	 * 
	 * 日期格式化yyyy-MM-dd
	 * @Title: formatDate
	 * @param @param date
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	public static String formatDate(Date date){
		return formatDate(date, "yyyy-MM-dd");
	}
	/**
	 * 
	 * 日期格式化
	 * @Title: formatDate
	 * @param @param date
	 * @param @param format 格式化字符串
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	public static String formatDate(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 
	 * 字符串转日期yyyy-MM-dd格式
	 * @Title: parseDate
	 * @param @param date
	 * @param @return
	 * @param @throws ParseException    设定文件
	 * @return Date    返回类型
	 * @throws
	 */
	public static Date parseDate(String date) throws ParseException{
		return parseDate(date, "yyyy-MM-dd");
	}
	
	/**
	 * 
	 * 字符串转日期
	 * @Title: parseDate
	 * @param @param date
	 * @param @param format
	 * @param @return
	 * @param @throws ParseException    设定文件
	 * @return Date    返回类型
	 * @throws
	 */
	public static Date parseDate(String date, String format) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}
	
	/**
	 * 保留两位小数 BigDecimal转字符串
	 * @param b
	 * @return
	 */
	public static String formateBigDecimal(BigDecimal b){
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");  
		return df.format(b);
	}
	
	public static String getPath(String no, String pPath){
		if(StringUtil.isEmpty(pPath)){
			return no;
		} else {
			return pPath + "," + no;
		}
	}
	
	
}
