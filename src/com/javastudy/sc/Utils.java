package com.javastudy.sc;

/**
 * 实用工具类
 * Created by OyDn on 2016/1/1/001.
 */
public class Utils {
    /**
     * 判断一个字符是否为null或空内容
     * @param str 字符串
     * @return 返回真假
     */
    public boolean isBlank(String str) {
        //如果字符串为null，或者字符串长度小于0，直接返回为真
        if (str == null || str.length() <= 0) {
            return true;
        } else {
            //如果字符长度大于0，遍历字符串每一个字符
            for (int i = 0; i < str.length(); i++) {
                char item = str.charAt(i);
                //如果其中有一个字符不是空格，则返回为假
                if (item != ' ') {
                    return false;
                }
            }
            return true;//如果全是空格，则返回为真
        }
    }

    /**
     * 判断字符串是否为boolean
     * @param str 字符串
     * @return 返回真假
     */
    public boolean isBoolean(String str) {
        int firstIndex = 0;//字符串第一个非空格下标
        boolean getFirst = false;
        int lastIndex = str.length() - 1;//字符串第最后一个非空格下标
        boolean getLast = false;
        String booleanString = "";//定义一个长度为0的booleanString字符串
        //如果str是空字符串或者空格，直接返回为假
        if (this.isBlank(str)) {
            return false;
        } else {
            //遍历str每一个字符
            for (int i = 0; i < str.length(); i++) {
                char item = str.charAt(i);
                //如果还没有取得第一个非空下标才去获取，否则不用
                if (!getFirst){
                    if (item != ' ') {
                        firstIndex = i;//得到第一个非空格下标
                        getFirst = true;
                    }
                }
                if (!getLast) {
                    String s = "";//定义一个长度为0的字符串s
                    for (int j = i; j < str.length(); j++) {
                        s += str.charAt(j);//把str字符串中，下标在i之后的全字符放入s中
                    }
                    if (this.isBlank(s)) {
                        lastIndex = i - 1;//如果s为空，或者全为空格，那么lastIndex的下标就该是i之前一位
                        getLast = true;
                    }
                }
            }
            for (int i = firstIndex; i <= lastIndex; i ++) {
                booleanString += str.charAt(i);//把str字符串中间非空格的内容赋值给booleanString
            }
            //如果str非空格部分内容是true或者yes或者false或者no
            if (booleanString.equals("true") || booleanString.equals("yes") || booleanString.equals("false") || booleanString.equals("no")) {
                return true;//返回为真
            } else {
                return false;//若不是以上个内容，则返回为假
            }
        }
    }

    /**
     * 判断是否是数字，包含正负数
     * @param str 字符串
     * @return 返回真假
     */
    public boolean isDigit(String str) {
        //如果是空字符串，直接返回为假
        String firstIndex = String.valueOf(str.charAt(0));//获取字符串第一个字符
        if (this.isBlank(str)) {
            return false;
        }else if (firstIndex.equals("-")){
            //遍历str除了负号的每一个字符
            for (int i = 1; i < str.length(); i++) {
                char c = str.charAt(i);
                //判断每一个字符是不是数字
                if (!isNumber(c)) {
                    return false;
                }
            }
            return true;//除开以上其他情况都返回为真
        }else {
            //遍历str每一个字符
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                //判断每一个字符是不是数字
                if (!isNumber(c)) {
                    return false;
                }
            }
            return true;//除开以上其他情况都返回为真
        }
    }

    /**
     * 判断是否是浮点数
     * @param str
     * @return 返回真假
     */
    public boolean isDouble(String str) {
        int minusIndex = str.lastIndexOf("-");//定义最后一个负号的位置
        int index = str.indexOf(".");//定义index为str中小数点的下标位置,如果没有小数点，则index会返回为-1
        String frontNumber = "";//定义小数点前面的字符串长度为0
        String lastNumber = "";//定义小数点后面的字符串长度为0
        //如果str为空字符串或者没有小数点或者负号在小数点后面，直接返回为假
        if (this.isBlank(str) || index == -1 || minusIndex > index) {
            return false;
        } else {
            //把小数点前面的内容赋值给frontNumber
            for (int i = 0; i < index; i++) {
                frontNumber += str.charAt(i);
            }
            //把小数点后面的内容赋值给lastNumber
            for (int i = index + 1; i < str.length(); i++) {
                lastNumber += str.charAt(i);
            }
        }
        //如果小数点前后都是数字，则返回为真，否则返回为假
        if (this.isDigit(frontNumber) && this.isDigit(lastNumber)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否是正确的电子邮件格式(选作)
     * @param str
     * @return 返回真假
     */
    public boolean isEmail(String str){
        int atIndex = str.indexOf("@");//获得@的下标
        int pointIndex = str.indexOf(".");//获得小数点的下标
        String lastString = "";//定义小数点之后的字符串
        //如果是空字符串，或者没有小数点，或者没有@,或者@在小数点后面，直接返回为假
        if(this.isBlank(str) || atIndex == -1 || pointIndex == -1 || atIndex > pointIndex) {
            return false;
        }else {
            //遍历@之前的每一字符
            for (int i = 0; i < atIndex; i ++) {
                char c = str.charAt(i);
                //只要有一个字符不是字母或者下划线，就返回为假
                if (!(isLetter(c) || (c == '_') || isNumber(c))) {
                    return false;
                }
            }
            for (int i = atIndex + 1; i < pointIndex; i++) {
                char c = str.charAt(i);
                //只要不是字母或者数字，就返回为假
                if (!(isLetter(c) || isNumber(c))) {
                    return false;
                }
            }
            //把小数点后面的字符串都赋值给lastString
            for (int i = pointIndex +1; i < str.length(); i++) {
                lastString += str.charAt(i);
            }
            //如果小数点后面是com（不分大小写），则格式已经完全正确，返回为真,否则为假
            if (lastString.equalsIgnoreCase("com") || lastString.equalsIgnoreCase("com.cn") || lastString.equalsIgnoreCase("cn")){
                return true;
            }else{
                return false;
            }
        }
    }

    /**
     * 判断字符是不是字母
     * @param c 字符
     * @return 返回真假
     */
    public boolean isLetter (char c) {
        if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否为合法用户名
     * @param str 用户名
     * @return 返回真假
     */
    public boolean isAccount(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!isNumber(c) && !isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符是不是数字
     * @param c 字符
     * @return 返回真假
     */
    public boolean isNumber(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        }
        return false;
    }

    /**
     *
     * @param str 字符串
     * @return 返回字符串是否为身份证号
     */
    public boolean isIdCard(String str) {
        //判断身份证是否为15或者18位
        if (str.length() == 18 || str.length() == 15) {
            //如果位数正确且都是数字，返回为真
            if (isDigit(str)) {
                return true;
                //如果是18位，且前面为数字，最后一个是x，也返回真
            } else if ((str.length() == 18)&&(isDigit(str.substring(0,16)))&&str.substring(17).equalsIgnoreCase("x")) {
                return true;
            } else {
                //其余情况返回为假
                return false;
            }
        }
        else {
            //位数不正确直接返回为假
            return false;
        }
    }

    /**
     * 判断是不是手机号码
     * @param str 输入字符串
     * @return 如果是返回为真，否则返回为假
     */
    public boolean isPhoneNum(String str) {
        //输入的是11为数字
        if (isDigit(str) && str.length() == 11) {
            return true;
        } else {
            return false;
        }
    }
}
