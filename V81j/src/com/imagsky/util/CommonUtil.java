package com.imagsky.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

/**
 * some very general utility method that cant be put into other XXXUtil class
 *
 */
public class CommonUtil {

    private CommonUtil() {
    } // this is a helper class
    private static final String dateFormatString = "dd-MM-yyyy HH:mm";
    public static final String dateFormatChineseString = "yyyy年M月dd日";
    public static final String dateOnlyFormatString = "dd-MM-yyyy";
    private static final String[] monthNames = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public static StringBuffer printMapWithNewLine(Map map) {
        StringBuffer strBuffer = new StringBuffer();

        if (map != null) {
            strBuffer.append('[');
            strBuffer.append('\n');

            if (!map.isEmpty()) {
                Iterator keyIter = map.keySet().iterator();
                while (keyIter.hasNext()) {
                    Object nextKey = keyIter.next();

                    strBuffer.append(nextKey);
                    strBuffer.append('=');
                    strBuffer.append(map.get(nextKey));
                    strBuffer.append('\n');
                }
            }

            strBuffer.append(']');
            strBuffer.append('\n');
        } else {
            strBuffer.append(map);
        }

        return strBuffer;
    }

    public static String trimIfNotNull(String str) {
        if (str != null) {
            return str.trim();
        } else {
            return null;
        }
    }

    public static boolean isNullOrEmpty(String str) {
        if (str == null) {
            return true;
        } else {
            if (str.length() == 0) {
                return true;
            } else {
                if (str.trim().length() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public static boolean isNullOrZeroLength(String str) {
        if (str == null) {
            return true;
        } else {
            if (str.length() == 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static StringBuffer null2EmptyBuffer(Object obj) {
        if (obj == null) {
            return new StringBuffer();
        } else {
            return (new StringBuffer()).append(obj);
        }
    }

    public static String null2Empty(Object obj) {
        if (obj == null) {
            return "";
        } else {
            if (obj instanceof String) {
                return (String) obj;
            } else {
                return String.valueOf(obj);
            }
        }
    }

    public static StringBuffer null2Empty(StringBuffer strBuffer) {
        if (strBuffer == null) {
            return new StringBuffer();
        } else {
            return strBuffer;
        }
    }

    public static String null2Empty(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

    public static int null2Zero(Integer num) {
        if (num == null) {
            return 0;
        } else {
            return num.intValue();
        }
    }

    public static String leftpadding(String str, String paddingStr, int length) {
        if (str.length() >= length) {
            return str;
        } else {
            StringBuffer sb = new StringBuffer(str);
            while (sb.toString().length() < length) {
                sb.insert(0, paddingStr);
            }
            return sb.toString();
        }
    }

    public static String padding(String str, String paddingStr, int length) {
        if (str.length() >= length) {
            return str;
        } else {
            StringBuffer sb = new StringBuffer(str);
            while (sb.toString().length() < length) {
                sb.append(paddingStr);
            }
            return sb.toString();
        }
    }

    /**
     *
     * @param dateToAdd
     * @param calUnit Calendar.MONTH, Calendar.DAY_OF_YEAR, Calendar.YEAR, ...,
     * etc
     * @param amountToAdd
     */
    public static Date dateAdd(java.util.Date dateToAdd, int calUnit, int amountToAdd) {
        if (dateToAdd == null) {
            dateToAdd = new java.util.Date();
        }
        if (dateToAdd != null) {
            Calendar calObj = Calendar.getInstance();
            calObj.setTimeInMillis(dateToAdd.getTime());

            calObj.add(calUnit, amountToAdd);

            dateToAdd.setTime(calObj.getTimeInMillis());
        } // end if (dateToAdd != null)
        return dateToAdd;
    }
    //getDateDiff(Calendar.<unit>, d1, d2);
    private static final double DAY_MILLIS = 1000 * 60 * 60 * 24.0015;
    private static final double HOUR_MILLIS = DAY_MILLIS / 24.0015;
    private static final double MIN_MILLIS = HOUR_MILLIS / 60;
    private static final double WEEK_MILLIS = DAY_MILLIS * 7;
    private static final double MONTH_MILLIS = DAY_MILLIS * 30.43675;
    private static final double YEAR_MILLIS = WEEK_MILLIS * 52.2;
    private static final String[] timediff_unit_zh = new String[]{"年", "日", "小時", "分鐘"};
    private static final String[] timediff_unit_en = new String[]{"Year(s)", "Day(s)", "Hour(s)", "Min(s)"};

    public static String getDateDiffFullString(String lang, java.util.Date d1, java.util.Date d2, int showLevel) {
        String[] units = null;
        if (d1 == null || d2 == null) {
            return "";
        }

        if ("zh".equalsIgnoreCase(lang)) {
            units = timediff_unit_zh;
        } else if ("en".equalsIgnoreCase(lang)) {
            units = timediff_unit_en;
        } else {
            units = timediff_unit_zh;//default
        }
        if (showLevel < 1 || showLevel > 4) {
            showLevel = 2;
        }

        StringBuffer sb = new StringBuffer();
        java.util.Date workingDate = new java.util.Date(d1.getTime());
        if (getDateDiff(Calendar.YEAR, workingDate, d2) > 0 && showLevel > 0) {
            sb.append(getDateDiff(Calendar.YEAR, workingDate, d2) + units[0]);
            workingDate = dateAdd(workingDate, Calendar.YEAR, getDateDiff(Calendar.YEAR, workingDate, d2));
            showLevel--;
        }
        if (getDateDiff(Calendar.DAY_OF_MONTH, workingDate, d2) > 0 && showLevel > 0) {
            sb.append(getDateDiff(Calendar.DAY_OF_MONTH, workingDate, d2) + units[1]);
            workingDate = dateAdd(workingDate, Calendar.DAY_OF_MONTH, getDateDiff(Calendar.DAY_OF_MONTH, workingDate, d2));
            showLevel--;
        }
        if (getEstDiff(Calendar.HOUR_OF_DAY, workingDate, d2) > 0 && showLevel > 0) {
            sb.append(getEstDiff(Calendar.HOUR_OF_DAY, workingDate, d2) + units[2]);
            workingDate = dateAdd(workingDate, Calendar.HOUR_OF_DAY, getEstDiff(Calendar.HOUR_OF_DAY, workingDate, d2));
            showLevel--;
        }
        if (getEstDiff(Calendar.MINUTE, workingDate, d2) > 0 && showLevel > 0) {
            sb.append(getEstDiff(Calendar.MINUTE, workingDate, d2) + units[3]);
            workingDate = dateAdd(workingDate, Calendar.MINUTE, getEstDiff(Calendar.MINUTE, workingDate, d2));
            showLevel--;
        }
        return sb.toString();
    }

    public static int getDateDiff(int calUnit, java.util.Date d1, java.util.Date d2) {
        // swap if d1 later than d2
        boolean neg = false;
        if (d1.after(d2)) {
            java.util.Date temp = d1;
            d1 = d2;
            d2 = temp;
            neg = true;
        }

        // estimate the diff.  d1 is now guaranteed <= d2
        int estimate = (int) getEstDiff(calUnit, d1, d2);

        // convert the Dates to GregorianCalendars
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);

        // add 2 units less than the estimate to 1st date,
        //  then serially add units till we exceed 2nd date
        c1.add(calUnit, (int) estimate - 2);
        for (int i = estimate - 1;; i++) {
            c1.add(calUnit, 1);
            if (c1.after(c2)) {
                return neg ? 1 - i : i - 1;
            }
        }
    }

    private static int getEstDiff(int calUnit, java.util.Date d1, java.util.Date d2) {
        long diff = d2.getTime() - d1.getTime();
        switch (calUnit) {
            case Calendar.DAY_OF_WEEK_IN_MONTH:
            case Calendar.DAY_OF_MONTH:
                //      case Calendar.DATE :      // codes to same int as DAY_OF_MONTH
                return (int) (diff / DAY_MILLIS + .5);
            case Calendar.WEEK_OF_YEAR:
                return (int) (diff / WEEK_MILLIS + .5);
            case Calendar.MONTH:
                return (int) (diff / MONTH_MILLIS + .5);
            case Calendar.YEAR:
                return (int) (diff / YEAR_MILLIS + .5);
            case Calendar.HOUR_OF_DAY:
                return (int) (diff / HOUR_MILLIS + .5);
            case Calendar.MINUTE:
                return (int) (diff / MIN_MILLIS + .5);
            default:
                return 0;
        } /*
         * endswitch
         */
    }

    public static void trimCalendar(java.util.Calendar calObj) {
        if (calObj != null) {
            calObj.set(Calendar.HOUR_OF_DAY, 0);
            calObj.set(Calendar.MINUTE, 0);
            calObj.set(Calendar.SECOND, 0);
            calObj.set(Calendar.MILLISECOND, 0);
        }
    }

    public static void trimDate(java.util.Date dataObj) {
        if (dataObj != null) {
            Calendar calObj = Calendar.getInstance();
            calObj.setTimeInMillis(dataObj.getTime());

            calObj.set(Calendar.HOUR_OF_DAY, 0);
            calObj.set(Calendar.MINUTE, 0);
            calObj.set(Calendar.SECOND, 0);
            calObj.set(Calendar.MILLISECOND, 0);

            dataObj.setTime(calObj.getTimeInMillis());
        }
    }

    public static void trimStringList(List strList) {
        if (strList != null) {
            for (int i = 0; i < strList.size(); i++) {
                String nextStr = (String) strList.get(i);
                strList.set(i, CommonUtil.trimIfNotNull(nextStr));
            }
        }
    }

    public static String[] trimStringArray(String[] strArray) {
        String[] trimmedStrArray = null;

        if (strArray != null) {
            trimmedStrArray = new String[strArray.length];

            for (int i = 0; i < strArray.length; i++) {
                trimmedStrArray[i] = CommonUtil.trimIfNotNull(strArray[i]);
            }
        }

        return trimmedStrArray;
    }

    public static boolean isNullOrEmpty(Collection c) {
        if (c == null) {
            return true;
        }
        return (c.size() == 0);
    }

    public static String formatDate(java.util.Date inDate, String dateFormatstr) {
        if (inDate == null) {
            return "";
        } else {
            SimpleDateFormat dateFormat =
                    new SimpleDateFormat(dateFormatstr);
            return dateFormat.format(inDate);
        }
    }

    public static String formatDate(java.util.Date inDate) {
        return formatDate(inDate, dateFormatString);
    }

    /**
     * **
     * Input java.util.Date and return string array as {"dd","mm","yyyy"}
     *
     * @param inDate
     * @return
     */
    public static String[] formatDateAsTokenDDMMYYYY(java.util.Date inDate) {
        if (inDate == null) {
            return null;
        }
        String[] tokens = CommonUtil.stringTokenize(formatDate(inDate, dateOnlyFormatString), "-");
        return tokens;
    }

    public static String formatDateFromDDMMYYYY(String ddmmyyyy) {
        if (ddmmyyyy != null) {
            int monthStr = new Integer(ddmmyyyy.substring(2, 4)).intValue();
            return ddmmyyyy.substring(0, 2) + " " + monthNames[monthStr - 1] + " " + ddmmyyyy.substring(4);
        }
        return "";
    }

    /**
     * *
     * Accept input string as 31/1/2010 or 1-1-2010 First portion must be day
     * part
     *
     * @param ddmmyyyy
     * @param delimiter
     * @return
     */
    public static java.util.Date StringDDMMYYYY2Date(String ddmmyyyy, String delimiter) {
        if (ddmmyyyy == null) {
            return null;
        }
        String[] tokens = CommonUtil.stringTokenize(ddmmyyyy, delimiter);
        if (tokens == null || tokens.length != 3) {
            return null;
        }
        String day = (tokens[0].length() == 1) ? "0" + tokens[0] : tokens[0];
        String month = (tokens[1].length() == 1) ? "0" + tokens[1] : tokens[1];
        if (tokens[2].length() != 4) {
            return null;
        }
        return StringDDMMYYYY2Date(day + month + tokens[2]);
    }

    public static java.util.Date StringDDMMYYYY2Date(String ddmmyyyy) {
        Calendar calObj = Calendar.getInstance();
        calObj.set(Calendar.YEAR, new Integer(ddmmyyyy.substring(4)).intValue());
        calObj.set(Calendar.MONTH, new Integer(ddmmyyyy.substring(2, 4)).intValue() - 1);
        calObj.set(Calendar.DAY_OF_MONTH, new Integer(ddmmyyyy.substring(0, 2)).intValue());
        calObj.set(Calendar.HOUR_OF_DAY, 0);
        calObj.set(Calendar.MINUTE, 0);
        calObj.set(Calendar.SECOND, 0);
        calObj.set(Calendar.MILLISECOND, 0);
        return new java.util.Date(calObj.getTimeInMillis());
    }

    public static java.util.Date StringDDMMYYHHmm2Date(String ddmmyyyyhhmm) {
        if (CommonUtil.isNullOrEmpty(ddmmyyyyhhmm)) {
            return null;
        }
        ddmmyyyyhhmm = ddmmyyyyhhmm.replaceAll("\\.", "").replaceAll("/", "").replaceAll(" ", "").replaceAll(":", "");
        Calendar calObj = Calendar.getInstance();
        try {
            calObj.set(Calendar.YEAR, new Integer(ddmmyyyyhhmm.substring(4, 8)).intValue());
            calObj.set(Calendar.MONTH, new Integer(ddmmyyyyhhmm.substring(2, 4)).intValue() - 1);
            calObj.set(Calendar.DAY_OF_MONTH, new Integer(ddmmyyyyhhmm.substring(0, 2)).intValue());
            calObj.set(Calendar.HOUR_OF_DAY, new Integer(ddmmyyyyhhmm.substring(8, 10)).intValue());
            calObj.set(Calendar.MINUTE, new Integer(ddmmyyyyhhmm.substring(10)).intValue());
            calObj.set(Calendar.SECOND, 0);
            calObj.set(Calendar.MILLISECOND, 0);
            return new java.util.Date(calObj.getTimeInMillis());
        } catch (Exception e) {
            System.out.println("StringDDMMYYHHmm2Date: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    public static boolean isBefore(Date date1, Date date2) {
        Calendar calObj = Calendar.getInstance();
        Calendar calObj2 = Calendar.getInstance();
        calObj.setTime(date1);
        calObj2.setTime(date2);
        return calObj.before(calObj2);
    }

    public static boolean isBeforeAdd(Date date1, Date date2, int calUnit, int amountToAdd) {
        dateAdd(date1, calUnit, amountToAdd);
        return isBefore(date1, date2);
    }

    public static Integer parseInteger(String intStr) {
        if (intStr == null) {
            return null;
        }

        try {
            return new Integer(intStr);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }
    private static String rxInteger = "[0-9]";
    private static String rxNumber = "[0-9.\\-]";
    private static String rxLoginName = "^0-9A-Za-z@.\\_\\-";
    //private static String rxLetter = "^A-Za-z";
    private static String rxLetter = "[a-zA-Z]";
    private static String rxLetterNumeric = "[\\w]";

    public static boolean isValidInteger(String inStr) {
        if (inStr == null) {
            return false;
        }
        Pattern numPattern = Pattern.compile(rxInteger);
        Matcher numMatcher = numPattern.matcher(inStr.trim());
        return numMatcher.find();
    }

    public static boolean isValidNumber(String inStr) {
        if (inStr == null) {
            return false;
        }
        Pattern numPattern = Pattern.compile(rxNumber);
        Matcher numMatcher = numPattern.matcher(inStr.trim());
        return numMatcher.find();
    }

    public static boolean isValidLoginName(String inStr) {
        if (inStr == null) {
            return false;
        }
        Pattern numPattern = Pattern.compile(rxLoginName);
        Matcher numMatcher = numPattern.matcher(inStr.trim());
        return numMatcher.find();
    }

    public static boolean isLetter(String inStr) {
        if (inStr == null) {
            return false;
        }
        Pattern letPattern = Pattern.compile(rxLetter);
        Matcher letMatcher = letPattern.matcher(inStr.trim());
        return letMatcher.find();
    }

    public static boolean isLetterNumeric(String inStr) {
        if (inStr == null) {
            return false;
        }
        Pattern letPattern = Pattern.compile(rxLetterNumeric);
        Matcher letMatcher = letPattern.matcher(inStr.trim());
        return letMatcher.find();
    }

    public static String[] stringTokenize(String inStr, String delimiter) {
        StringTokenizer st = new StringTokenizer(inStr, delimiter);
        int count = st.countTokens();
        if (count == 0) {
            return null;
        }
        String[] result = new String[count];
        int x = 0;
        while (st.hasMoreTokens()) {
            result[x] = st.nextToken();
            x++;
        }
        return result;
    }

    public static String[] stringTokenize(String inStr) {
        return CommonUtil.stringTokenize(inStr, " \t\n\r\f"); // all writespace
    }

    public static String findValueWithStringTokenizer(String inStr, String key, String delimiter1, String delimiter2) {
        String[] tokens = stringTokenize(inStr, delimiter1);
        for (int x = 0; x < tokens.length; x++) {
            if (tokens[x].startsWith(key + delimiter2)) {
                String[] token2 = stringTokenize(tokens[x], delimiter2);
                return token2[1];
            }
        }
        return null;
    }

    public static String numericFormatWithComma(double inNum, boolean hasDecimal) {
        NumberFormat formatter;
        String inStr = new Double(inNum).toString();
        if (inStr.indexOf(".") >= 0 || hasDecimal) {
            formatter = new DecimalFormat("#,###,###,###.##");
        } else {
            formatter = new DecimalFormat("#,###,###,###");
        }
        return formatter.format(inNum);
    }

    public static String numericFormatWithComma(double inNum) {
        return numericFormatWithComma(inNum, false);
    }

    /**
     * no IndexOutOfBound even if the maxSize too large
     *
     * @param fullList
     * @param maxSize, negative integer = no maxSize
     * @return null if
     * <code>fullList</code> is null, otherwise return new list
     */
    public static List subList(List fullList, int maxSize) {
        List subList = null;

        if (fullList != null) {
            int endIndexExclusive = maxSize;
            if (maxSize < 0) {
                endIndexExclusive = fullList.size(); // -ve integer = no maxSize
            } else {
                if (fullList.size() < maxSize) {
                    endIndexExclusive = fullList.size();
                }
            }

            subList = fullList.subList(0, endIndexExclusive);
        }

        return subList;
    }

    public static String toString(Object[] arrayObj) {
        return CommonUtil.toStringBuffer(arrayObj).toString();
    }

    public static StringBuffer toStringBuffer(Object[] arrayObj) {
        StringBuffer strBuffer = new StringBuffer();

        if (arrayObj != null) {

            strBuffer.append('[');

            for (int i = 0; i < arrayObj.length; i++) {
                if (i != 0) {
                    strBuffer.append(',');
                    strBuffer.append(' ');
                }

                strBuffer.append(arrayObj[i]);
            }

            strBuffer.append(']');
        } else {
            strBuffer.append((String) null);
        }

        return strBuffer;
    }

    public static String list2String(List a, String deli) {
        StringBuffer sb = new StringBuffer();
        if (a == null) {
            return "";
        } else {
            Collections.sort(a);
            for (int x = 0; x < a.size(); x++) {
                if (x > 0) {
                    sb.append(deli);
                }
                sb.append(a.get(x));
            }
        }
        return sb.toString();
    }

    public static String array2String(Object[] array, String delimitor) {
        StringBuffer strBuffer = new StringBuffer();
        if (array == null || array.length == 0) {

            return "";
        } else {

            for (int i = 0; i < array.length; i++) {
                if (i > 0) {
                    strBuffer.append(delimitor);
                }

                strBuffer.append(array[i]);
            }
        }
        return strBuffer.toString();
    }

    public static void toUpperCase(String[] strArray) {
        if (strArray != null) {
            for (int i = 0; i < strArray.length; i++) {
                if (strArray[i] != null) {
                    strArray[i] = strArray[i].toUpperCase();
                }
            }
        }
    }

    public static int indexOf(Object[] array, Object value) {
        if (array == null) {
            return -1;
        } else {
            for (int i = 0; i < array.length; i++) {
                Object nextVal = array[i];

                if (value == null) {
                    if (nextVal == null) {
                        return i;
                    }
                } else {
                    if (value.equals(nextVal)) {
                        return i;
                    }
                }
            }

            return -1;
        }
    }

    public static boolean isJavaIdentifier(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        if (Character.isJavaIdentifierStart(str.charAt(0))) {
            for (int i = 1; i < str.length(); i++) {
                if (Character.isJavaIdentifierPart(str.charAt(i))) {
                    // do nothing
                } else {
                    return false;
                }
            } // end for

            return true;
        } else {
            return false;
        }
    }

    public static String createStringFromCLOB(Clob message) throws SQLException, IOException {
        StringBuffer temp = new StringBuffer("");
        String str = "";
        BufferedReader clobData;

        if (null != message) {
            clobData = new BufferedReader(message.getCharacterStream());
            while ((str = clobData.readLine()) != null) {
                temp.append(str);
            }
        }
        return temp.toString();
    }

    public static String createSQL_in(String[] values) {
        StringBuffer sb = new StringBuffer();
        if (values == null) {
            //V5Logger.debug("input array is null");
            return "";
        } else {
            //V5Logger.debug("input array is not null: length = "+ values.length);
            for (int x = 0; x < values.length; x++) {
                if (!sb.toString().equals("")) {
                    sb.append(",");
                }
                sb.append("'" + values[x] + "'");
            }
        }
        return sb.toString();
    }

    public static String createSQL_in(ArrayList values) {
        StringBuffer sb = new StringBuffer();
        if (values == null) {
            return "";
        } else {
            for (int x = 0; x < values.size(); x++) {
                if (!sb.toString().equals("")) {
                    sb.append(",");
                }
                sb.append("'" + values.get(x) + "'");
            }
        }
        return sb.toString();
    }

    public static Date toDateFromSQLDateTime(String dateTimeStr) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").parse(dateTimeStr);
            return date;
        } catch (Exception e) {
            //V5CMALogger.error("Error in toDateFromSQLDateTime", e);
            return null;
        }
    }

    public static HttpServletRequest addAsRequestAttribute(HttpServletRequest request, Map<String, Object> aMap) {
        if (request == null) {
            return null;
        }
        if (aMap != null) {
            Iterator<String> it = aMap.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                request.setAttribute(key, aMap.get(key));
            }
        }
        return request;
    }

    public static boolean isValidEmailAddress(String aEmailAddress) {
        if (aEmailAddress == null) {
            return false;
        }
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(aEmailAddress);
            if (!hasNameAndDomain(aEmailAddress)) {
                result = false;
            }
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    private static boolean hasNameAndDomain(String aEmailAddress) {
        String[] tokens = aEmailAddress.split("@");
        return tokens.length == 2
                && !CommonUtil.isNullOrEmpty(tokens[0])
                && !CommonUtil.isNullOrEmpty(tokens[1]);
    }

    public static String isChecked(String in, String checkboxV) {
        return (CommonUtil.null2Empty(in).equalsIgnoreCase(checkboxV)) ? " checked" : "";
    }

    public static String isSelected(String in, String checkboxV) {
        return (CommonUtil.null2Empty(in).equalsIgnoreCase(checkboxV)) ? " selected" : "";
    }

    public static String escapeJavascriptTag(String s) {
        return s.replaceAll("(?is)<script.*?>.*?</script.*?>", "") // Remove all <script> tags.
                .replaceAll("(?is)<.*?javascript:.*?>.*?</.*?>", "") // Remove tags with javascript: call.
                .replaceAll("(?is)<.*?\\s+on.*?>.*?</.*?>", ""); // Remove tags with on* attributes.
    }

    public static String escapeHTMLTag(String content) {
        return content.replaceAll("\\<.*?\\>", "");
    }

    public static String escape(String s) {
        return escapeHTMLTag(escapeJavascriptTag(null2Empty(s)));
    }

    public static String inputParamConversion(String s) {
        return escapeJavascriptTag(s);
    }

    
    public static String getHttpServerHost(HttpServletRequest req, boolean isHTTPS) {
        StringBuffer sb = new StringBuffer();
        if (isHTTPS) {
            sb.append("https://");
        } else {
            sb.append("http://");
        }
        if(req.getServerPort()==80 || req.getServerPort()==443){
            sb.append(req.getServerName());
        } else {
            sb.append(req.getServerName()+":"+req.getServerPort());
        }
        
        return sb.toString();
    }
    
    public static String getHttpServerHostWithPort(HttpServletRequest req) {
        StringBuffer sb = new StringBuffer();
        if (req.isSecure() && V6Util.isSSLOn()) {
            sb.append("https://");
        } else {
            sb.append("http://");
        }
        sb.append(req.getServerName());
        if (req.getServerPort() != 80 & req.getServerPort() != 433) {
            sb.append(":" + req.getServerPort());
        }
        return sb.toString();
    }

    public static String getHttpProtocal(HttpServletRequest req) {
        if (req.isSecure() && V6Util.isSSLOn()) {
            return "https://";
        } else {
            return "http://";
        }
    }

    public static String getSecureFullRequestURL(HttpServletRequest req) {
        if (!V6Util.isSSLOn()) {
            return req.getAttribute("fullRequestURL").toString();
        } else {
            return req.getAttribute("fullRequestURL").toString().replaceAll("http:", "https:");
        }
    }

    public static String subStringWithDots(String inStr, int length) {
        String dots = "...";
        if (inStr == null) {
            return null;
        }
        if (inStr.length() > length) {
            return inStr.substring(0, length) + dots;
        } else {
            return inStr;
        }
    }

    public static java.util.Date isValidDate(String inDate) {
        return isValidDate(inDate, dateOnlyFormatString);
    }

    public static java.util.Date isValidDate(String inDate, String dateFormatStr) {
        if (inDate == null) {
            return null;
        }
        //set the format to use as a constructor argument
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        if (inDate.trim().length() != dateFormat.toPattern().length()) {
            return null;
        }
        dateFormat.setLenient(false);
        try {
            //parse the inDate parameter
            return dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return null;
        }
    }

    /**
     * **
     *
     * @param inString - Original String to be marked
     * @param keepFirstCharacters Keep first N character of the string
     * @param maskUntilCharacter used for email mask, pass "@" to keep the
     * domain address of the email, else null or empty
     * @param replacewith marked to be
     * @param trunc use together with maskUntilCharacter, true to truncate
     * string after the maskUntilCharacter, false to keep
     * @return eg. waxxxxxxxx@yahoo.com.hk
     */
    public static String maskEmail(String inString, int keepFirstCharacters, String replacewith, boolean returnDomain) {
        if (CommonUtil.isNullOrEmpty(inString)) {
            return null;
        }
        if (keepFirstCharacters <= 0) {
            keepFirstCharacters = 1; //default keep the first character
        }
        if (keepFirstCharacters > inString.length()) {
            return inString; //no change if wrong keepFirstCharacter passin
        }
        if (inString.indexOf("@") < 0) {
            return inString; //not a email
        }
        if (CommonUtil.isNullOrEmpty(replacewith)) {
            replacewith = "x"; //default replace to x
        }
        StringBuffer sb = new StringBuffer(inString.substring(0, keepFirstCharacters));
        int indexofAtSign = inString.indexOf("@");
        for (int x = keepFirstCharacters; x < indexofAtSign; x++) {
            sb.append(replacewith);
        }
        if (returnDomain) {
            sb.append(inString.substring(indexofAtSign));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
    }
}
