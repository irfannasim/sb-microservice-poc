package com.infotech.poc.auth.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.infotech.poc.auth.dl.entity.BaseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

@SuppressWarnings(value = {"rawtypes"})
public class AppUtility {

    public static final int defaultDecimalPrecision = 2;
    private static int decimalPercision = defaultDecimalPrecision;
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String Time_FORMAT = "hh:mm a";

    public static Timestamp getCurrentTimeStamp() {

        return new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    public static String getDeleteStamp() {
        return "_Del_" + Calendar.getInstance().getTimeInMillis();
    }

    public static int getDecimalPercision() {
        return decimalPercision;
    }

    public static void setDecimalPercision(int decimalPercision) {
        AppUtility.decimalPercision = decimalPercision;
    }

    public static String getResourceMessage(String key) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
        return resourceBundle.getString(key);
    }

    public static BigDecimal covertToBigDecimal(String val) {
        if (isEmpty(val) && isBigDecimal(val)) {
            return new BigDecimal(val);
        }
        return BigDecimal.ZERO;
    }

    public static Long covertToLong(String val) {
        if (isEmpty(val) && isNumeric(val)) {
            return new Long(val);
        }
        return new Long("0");
    }

    public static ZonedDateTime getZonedDateTimeFromFormattedString(String dateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDateTime = LocalDate.parse(dateTime, formatter).atStartOfDay();
        return localDateTime.atZone(ZoneId.systemDefault());
    }

    public static ZonedDateTime getEndOfMonth(ZonedDateTime dateTime, String format) {
        LocalDateTime localDateTime = dateTime.toLocalDateTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        Date lastDayOfMonth = calendar.getTime();
        return lastDayOfMonth.toInstant().atZone(ZoneId.systemDefault());
    }

    public static LocalDate zonedDateTimeToLocalDate(ZonedDateTime zonedDateTime) {
        LocalDate localDate = null;
        if (!isEmpty(zonedDateTime)) {
            localDate = zonedDateTime.toLocalDate();
        }
        return localDate;
    }

    public static ZonedDateTime localDateToZonedDateTime(LocalDate localDate) {
        ZonedDateTime zonedDateTime = null;
        if (!isEmpty(localDate)) {
            zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        }
        return zonedDateTime;
    }

    public static boolean isDateLessOrEqualToToday(ZonedDateTime dateTime) {
        boolean isExpired = false;
        if (!AppUtility.isEmpty(dateTime)) {
            LocalDate today = ZonedDateTime.now().toLocalDate();
            LocalDate expirationDate = dateTime.toLocalDate();
            if (expirationDate.compareTo(today) < 1) {
                isExpired = true;
            }
        }
        return isExpired;
    }

    public static boolean isDateLessOrEqualToToday(LocalDate expirationDate) {
        boolean isExpired = false;
        if (!AppUtility.isEmpty(expirationDate)) {
            LocalDate today = ZonedDateTime.now().toLocalDate();
            if (expirationDate.compareTo(today) < 1) {
                isExpired = true;
            }
        }
        return isExpired;
    }

    public static boolean isDateGraterThanToday(ZonedDateTime dateTime) {
        boolean before = false;
        if (!AppUtility.isEmpty(dateTime)) {
            LocalDate today = ZonedDateTime.now().toLocalDate();
            LocalDate date = dateTime.toLocalDate();
            if (date.compareTo(today) > 0) {
                before = true;
            }
        }
        return before;
    }

    public static boolean criteriaIsDateGraterThanToday(ZonedDateTime dateTime) {
        boolean before = false;
        if (!AppUtility.isEmpty(dateTime)) {
            LocalDate today = ZonedDateTime.now().toLocalDate();
            LocalDate date = dateTime.toLocalDate();
            if (date.isAfter(today)) {
                before = true;
            }
        }
        return before;
    }

    public static String getCurrentYear() {
        Calendar now = Calendar.getInstance();
        return String.valueOf(now.get(Calendar.YEAR));
    }

    public static BigInteger covertToBigInteger(String val) {
        if (!isEmpty(val) && isNumeric(val)) {
            return new BigInteger(val);
        }
        return BigInteger.ZERO;
    }

    public static Double covertToDouble(String val) {
        if (!isEmpty(val) && isDouble(val)) {
            return new Double(val);
        }
        return new Double("0");
    }

    public static Double covertToDoubleCriteria(String val) {
        if (!isEmpty(val) && isDouble(val) || isNumeric(val)) {
            return new Double(val);
        }
        return new Double("0");
    }

    public static Integer covertToInteger(String val) {
        if (!isEmpty(val) && isNumeric(val)) {
            return new Integer(val);
        }
        return new Integer("0");
    }

    public static String formatDoubleToStringWithPrecision(Double d) {
        String str = "###,###,###.####";
        if (AppUtility.getDecimalPercision() == 2) {
            str = "###,###,###.##";
        } else if (AppUtility.getDecimalPercision() == 3) {
            str = "###,###,###.###";
        }
        DecimalFormat df = new DecimalFormat(str);
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(d);
    }

    public static String formatDoubleToStringWithoutCommasWithPrecision(Double d) {
        String str = "#########.####";
        if (AppUtility.getDecimalPercision() == 2) {
            str = "#########.##";
        } else if (AppUtility.getDecimalPercision() == 3) {
            str = "#########.###";
        }
        DecimalFormat df = new DecimalFormat(str);
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(d);
    }

    public static String formatBigDecimalToStringWithoutCommasWithPrecision(BigDecimal d) {
        String str = "%1$." + AppUtility.getDecimalPercision() + "f";
        return String.format(str, d);
    }

    public static String formatDoubleToString(Double d) {
        return String.format("%1$,.4f", d);
    }

    public static String formatDoubleToStringWithoutCommas(Double d) {
        return String.format("%1$.4f", d);
    }

    public static String formatBigDecimalToString(BigDecimal d) {
        return String.format("%1$,.4f", d);
    }

    public static String formatIntegerToString(Integer d) {
        return String.format("%,d", d);
    }

    public static String formatLongToString(Long d) {
        return String.format("%,d", d);
    }

    public static String formatBigIntegerToString(BigInteger d) {
        return String.format("%,d", d);
    }

    public static String formatDateWithShortMonth(ZonedDateTime zonedDateTime) {
        if (!isEmpty(zonedDateTime)) {
            return zonedDateTime.getDayOfMonth() + "-" + zonedDateTime.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + "-" + zonedDateTime.getYear();
        } else {
            return "";
        }
    }

    public static String formatDate(ZonedDateTime zonedDateTime) {
        if (!isEmpty(zonedDateTime)) {
            return zonedDateTime.getDayOfMonth() + "-" + zonedDateTime.getMonthValue() + "-" + zonedDateTime.getYear();
        } else {
            return "";
        }
    }

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof Long) {
            if ((Long) object <= 0) {
                return true;
            }
        }
        if (object instanceof Double) {
            if ((Double) object <= 0) {
                return true;
            }
        }
        if (object instanceof BaseEntity) {
            if (((BaseEntity) object).getId() == null) {
                return true;
            }
        }
        if (object instanceof Optional<?> && !((Optional<?>) object).isPresent()) {
            return true;
        }
        if (object instanceof String) {
            String objString = object.toString();
            return objString.trim().length() <= 0 || objString.trim().equalsIgnoreCase("null");
        } else if (object instanceof StringBuilder) {
            StringBuilder stringBuilder = (StringBuilder) object;
            return stringBuilder.toString().trim().length() <= 0;
            // Check for List and Sets
        } else if (object instanceof Collection<?>) {
            return ((Collection) object).isEmpty();
        } else if (object instanceof Map<?, ?>) {
            return ((Map) object).isEmpty();
        } else if (object instanceof Boolean[]) {
            return ((Boolean[]) object).length <= 0;
        }
        return false;
    }

    public static boolean isEmptyCriteria(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof BaseEntity) {
            if (((BaseEntity) object).getId() == null) {
                return true;
            }
        }
        if (object instanceof Optional<?> && !((Optional<?>) object).isPresent()) {
            return true;
        }
        if (object instanceof String) {
            String objString = object.toString();
            return objString.trim().length() <= 0 || objString.trim().equalsIgnoreCase("null");
        } else if (object instanceof StringBuilder) {
            StringBuilder stringBuilder = (StringBuilder) object;
            return stringBuilder.toString().trim().length() <= 0;
            // Check for List and Sets
        } else if (object instanceof Collection<?>) {
            return ((Collection) object).isEmpty();
        } else if (object instanceof Map<?, ?>) {
            return ((Map) object).isEmpty();
        } else if (object instanceof Boolean[]) {
            return ((Boolean[]) object).length <= 0;
        }
        return false;
    }

    public static boolean isNumeric(String value) {
        try {
            new BigInteger(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isBigDecimal(String value) {
        try {
            new BigDecimal(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isDouble(String value) {
        try {
            new Double(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @SuppressWarnings({"deprecation", "unused"})
    public static boolean isDate(String value) {
        try {
            Date date = new Date(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String encryptPlainPasswordToBcrypt(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public static String encryptStringToBase64(String password) {
        byte[] decodedBytes = Base64.getEncoder().encode(password.getBytes());
        return new String(decodedBytes);
    }

    public static String decryptBase64ToPlainText(String encodedPassword) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword);
        return new String(decodedBytes);
    }

    public static ZonedDateTime getEndOfDay(ZonedDateTime regTo) {
        return regTo.with(LocalTime.of(23, 59, 59));
    }

    public static ZonedDateTime getStartOfDay(ZonedDateTime zonedDateTime) {
        return zonedDateTime.truncatedTo(ChronoUnit.DAYS);
    }

    public static boolean isFutureOrOpenDate(ZonedDateTime zonedDateTime) {
        boolean isFutureDate = false;
        if (AppUtility.isEmpty(zonedDateTime)
                || AppUtility.getEndOfDay(ZonedDateTime.now())
                .isBefore(zonedDateTime)) {
            // if validity is in future then create new...
            isFutureDate = true;
        }
        return isFutureDate;
    }

    public static ZonedDateTime addMinsToZonedDateTime(ZonedDateTime zonedDateTime, Long mins) {
        return zonedDateTime.plusMinutes(mins);
    }

    public static Date convertDateToDefaultTimeZone(Date date) throws Exception {
        TimeZone zone = TimeZone.getTimeZone("GMT");
        Calendar first = Calendar.getInstance(zone);
        first.setTimeInMillis(date.getTime());

        Calendar output = Calendar.getInstance();
        output.set(Calendar.YEAR, first.get(Calendar.YEAR));
        output.set(Calendar.MONTH, first.get(Calendar.MONTH));
        output.set(Calendar.DAY_OF_MONTH, first.get(Calendar.DAY_OF_MONTH));
        output.set(Calendar.HOUR_OF_DAY, first.get(Calendar.HOUR_OF_DAY));
        output.set(Calendar.MINUTE, first.get(Calendar.MINUTE));
        output.set(Calendar.SECOND, first.get(Calendar.SECOND));

        output.set(Calendar.SECOND, first.get(Calendar.SECOND));
        output.set(Calendar.MILLISECOND, first.get(Calendar.MILLISECOND));

        return output.getTime();
    }

    public static LocalDate convertStringDateToLocalDate(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        return LocalDate.parse(date, formatter);
    }

    public static String encodeUTF(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String generateRandomUniqString() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    /**
     * Private Methods
     *
     * @param x
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private static byte[] computeHash(String x) throws Exception {
        java.security.MessageDigest d = null;
        d = java.security.MessageDigest.getInstance("SHA-1");
        d.reset();
        d.update(x.getBytes());
        return d.digest();
    }

    @SuppressWarnings("unused")
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }


    public static String formatDataAndTime(ZonedDateTime zonedDateTime) {
        return zonedDateTime.format(DateTimeFormatter.ofPattern(Time_FORMAT)) + " " + zonedDateTime.getDayOfMonth() + "-" + zonedDateTime.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + "-" + zonedDateTime.getYear();
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        final Set<Object> seen = new HashSet<>();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static String spliceSystemGeneratedRefNum(String refNum) {
        String splicedRefNum = "";
        if (!AppUtility.isEmpty(refNum)) {
            String[] refNumArray = refNum.split("\\|");
            if (refNumArray.length == 4) {
                splicedRefNum = refNumArray[2] + "-" + refNumArray[3];
            }
        }
        return splicedRefNum;
    }

    public static String fullSystemGeneratedRefNum(String refNum, String officeCode) {
        String fullRefNum = "";
        String[] refNumArray = refNum.split("\\|");
        if (refNumArray.length != 4) {
            if (!AppUtility.isEmpty(refNum) && !AppUtility.isEmpty(officeCode)) {
                refNum = refNum.replace("-", "|");
                Integer year = Calendar.getInstance().get(Calendar.YEAR);
                refNum = year + "|" + officeCode + "|" + refNum;
                fullRefNum = refNum;
            }
        } else {
            fullRefNum = refNum;
        }
        return fullRefNum;
    }

    public static String pojoToString(Object obj) throws JsonProcessingException {
        if (!AppUtility.isEmpty(obj)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            mapper.setDateFormat(df);

            return mapper.writeValueAsString(obj);
        }
        return null;
    }

    public static String convertDateToUFDateFormat(ZonedDateTime start, ZonedDateTime end) {
        String str = "";
        if (!AppUtility.isEmpty(start) && !AppUtility.isEmpty(end)) {
            long diff = end.toInstant().toEpochMilli() - start.toInstant().toEpochMilli();

            if ((diff / 1000) <= 60) {
                str = diff + " Seconds";
            } else if ((diff / 60000) <= 60) {
                str = (diff / 60000) + " Minutes";
            } else if ((diff / 3600000) <= 24) {
                str = (diff / 3600000) + " Hours";
            } else if ((diff / 3600000) > 24) {
                double daysDiff = diff / 3600000.0 / 24.0;
                long days = (long) daysDiff;
                long hours = (long) ((daysDiff - days) * 24);
                str = days + " Days & " + hours + " Hours";
            }
        }
        return str;
    }
}
