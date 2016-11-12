package com.cshiji.superviki.base.util;

import java.math.BigDecimal;  


/**
 * <p>decribing : 金额元分之间转换</p>
 */
public class AmountUtils {  
	
	private AmountUtils() {
    }
      
    /**金额为分的格式 */  
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";  
      
    /**  
     * 将分为单位的转换为元并返回金额格式的字符串 （除100） 
     * @param amount 
     * @return 
     * @throws Exception  
     */  
    public static String changeF2Y(Long amount) throws Exception{  
        if(!amount.toString().matches(CURRENCY_FEN_REGEX)) {  
            throw new Exception("金额格式有误");  
        }  
          
        int flag = 0;  
        String amString = amount.toString();  
        if(amString.charAt(0)=='-'){  
            flag = 1;  
            amString = amString.substring(1);  
        }  
        StringBuffer result = new StringBuffer();  
        if(amString.length()==1){  
            result.append("0.0").append(amString);  
        }else if(amString.length() == 2){  
            result.append("0.").append(amString);  
        }else{  
            String intString = amString.substring(0,amString.length()-2);  
            for(int i=1; i<=intString.length();i++){  
                if( (i-1)%3 == 0 && i !=1){  
                    result.append(",");  
                }  
                result.append(intString.substring(intString.length()-i,intString.length()-i+1));  
            }  
            result.reverse().append(".").append(amString.substring(amString.length()-2));  
        }  
        if(flag == 1){  
            return "-"+result.toString();  
        }else{  
            return result.toString();  
        }  
    }  
      
    /** 
     * 将分为单位的转换为元 （除100） 
     * @param amount 
     * @return 
     * @throws Exception  
     */  
    public static String changeF2Y(String amount) throws Exception{  
        if(!amount.matches(CURRENCY_FEN_REGEX)) {  
            throw new Exception("金额格式有误");  
        }  
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();  
    }  
      
    /**  
     * 将元为单位的转换为分 （乘100） 
     * @param amount 
     * @return 
     */  
    public static String changeY2F(Long amount){  
        return BigDecimal.valueOf(amount).multiply(new BigDecimal(100)).toString();  
    }  
      
    /**  
     * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额 
     *  
     * @param amount 
     * @return 
     */  
    public static String changeY2F(String amount){  
        String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额  
        int index = currency.indexOf(".");  
        int length = currency.length();  
        Long amLong = 0l;  
        if(index == -1){  
            amLong = Long.valueOf(currency+"00");  
        }else if(length - index >= 3){  
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));  
        }else if(length - index == 2){  
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);  
        }else{  
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");  
        }  
        return amLong.toString();  
    }  
    
	/**
	 * 金额转换大写（精确到分，最大单位千亿）
	 * @param value
	 * @return
	 */
	public static String changeToBig(BigDecimal value) {
		char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
		char[] vunit = { '万', '亿' }; // 段名表示
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
		long midVal = (long) (value.setScale(3, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).longValue()); // 转化成整形
		String valStr = String.valueOf(midVal); // 转化成字符串
		int strl = valStr.length() > 1 ? 2 : 1;
		String head = "";
		String rail = "";
		if(strl == 1){
			head = valStr.substring(0, valStr.length() - strl); // 取整数部分
			//金额小于一毛补零
			rail = "0" + valStr; // 取小数部分
		}else{
			head = valStr.substring(0,valStr.length()-2);
		    rail = valStr.substring(valStr.length()-2);                     
		}
		String prefix = ""; // 整数部分转化的结果
		String suffix = ""; // 小数部分转化的结果
		// 处理小数点后面的数
		if (rail.equals("00")) { // 如果小数部分为0
			suffix = "整";
		} else {
			suffix = digit[rail.charAt(0) - '0'] + "角"
					+ digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
		}
		// 处理小数点前面的数
		char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
		char zero = '0'; // 标志'0'表示出现过0
		byte zeroSerNum = 0; // 连续出现0的次数
		for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
			int idx = (chDig.length - i - 1) % 4; // 取段内位置
			int vidx = (chDig.length - i - 1) / 4; // 取段位置
			if (chDig[i] == '0') { // 如果当前字符是0
				zeroSerNum++; // 连续0次数递增
				if (zero == '0') { // 标志
					zero = digit[0];
				} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix += vunit[vidx - 1];
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0; // 连续0次数清零
			if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
				prefix += zero;
				zero = '0';
			}
			prefix += digit[chDig[i] - '0']; // 转化该数字表示
			if (idx > 0)
				prefix += hunit[idx - 1];
			if (idx == 0 && vidx > 0) {
				prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
			}
		}

		if (prefix.length() > 0)
			prefix += '圆'; // 如果整数部分存在,则有圆的字样
		return prefix + suffix; // 返回正确表示
	}
    
      
    public static void main(String[] args) {  
//        try {  
//            System.out.println("结果："+changeF2Y("-000a00"));  
//        } catch(Exception e){  
//            System.out.println("----------->>>"+e.getMessage());  
////          return e.getErrorCode();  
//        }   
      System.out.println("结果："+changeY2F("1.00000000001E10"));  
          
        System.out.println(AmountUtils.changeY2F("1.33"));  
        try {
			System.out.println(AmountUtils.changeF2Y("1322"));
		} catch (Exception e) {
			e.printStackTrace();
		}
//        System.out.println(Long.parseLong(AmountUtils.changeY2F("1000000000000000")));  
//        System.out.println(Integer.parseInt(AmountUtils.changeY2F("10000000")));  
//        System.out.println(Integer.MIN_VALUE);  
//        long a = 0;  
//        System.out.println(a);  
//          System.err.println(changeToBig(new BigDecimal(173275943000.13)));
    }  
}  
