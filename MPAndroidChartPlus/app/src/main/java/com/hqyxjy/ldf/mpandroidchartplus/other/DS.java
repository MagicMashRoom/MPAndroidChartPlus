package com.hqyxjy.ldf.mpandroidchartplus.other;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hqyxjy.ldf.mpandroidchartplus.BuildConfig;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Description:
 * DataSafer: 安全的数据转换器，用于各种数据转换情景，比如把服务器返回的数据转换成非空的数据模型
 * 1. 在debug状态下，出现空值或者转换异常，会直接崩溃
 * 2. 在release状态下，出现空值或者转换异常，不会直接崩溃，而会做两个事情：
 * 1) 使用默认值作为转换结果返回，默认值可以在调用的时候指定，也可以使用本工具类中设置的缺省值
 * 2) 进行错误报告（打印log，把错误发布到bugly）
 * 3. 另外，如果针对某些可以为空的数据进行转换，那么传入nullable为true这个参数，这样不论是在debug还是
 * release状态，出现空值都会直接转换为默认值，不会崩溃也不会进行错误报告
 * <p>
 * Attention:
 * 1. 通过修改源文件的一个开关，可以让在debug状态下，不直接崩溃，而是打印log和发布bugly
 * 2. 针对某些可以为空的数据进行转换时，转换异常还是会崩溃或进行错误报告
 * <p>
 * Created by Zhengyu.Xiong ; On 2017-02-21.
 */

public class DS {
    /*config--------------------------------------------------------------------------------------*/
    // debug模式下的保护开关，true为开启保护
    // 保护在debug的情况下，出现空值或者转换异常，像release一样去处理（不会崩溃）
    // 在使用mock数据的时候可能需要它
    private static final boolean DEBUG_CRASH_PROTECTOR = true;
    /*--------------------------------------------------------------------------------------config*/

    private static final boolean IS_DEBUG = BuildConfig.DEBUG;

    private static final int INT_DEFAULT = 0;
    private static final long LONG_DEFAULT = 0L;
    private static final double DOUBLE_DEFAULT = 0.0;
    private static final float FLOAT_DEFAULT = 0.0f;
    private static final String STRING_DEFAULT = "";

    private static final String TAG_DS = "DataSafer";
    private static final String NULL_DATA_TIPS = "Null Data";
    private static final String ERROR_DATA_TIPS = "Error Data";

    /*--------------------------------------------------------------------------------------------*/

    /**
     * String 类型转换方法
     *
     * @param sourceString 转换的源字符串
     * @param defaultValue 自定义的默认值，转换失败后如果不允许崩溃，就把这个作为转换结果
     * @param nullable     这个是否允许转换的源字符串为空
     */
    @NonNull
    public static String toString(String sourceString, String defaultValue, boolean nullable) {
        if (isStringNotEmpty(sourceString, nullable)) {
            return sourceString;
        } else {
            return defaultValue;
        }
    }

    @NonNull
    public static String toString(String sourceString) {
        return toString(sourceString, STRING_DEFAULT, false);
    }

    @NonNull
    public static String toString(String sourceString, String defaultValue) {
        return toString(sourceString, defaultValue, false);
    }

    @NonNull
    public static String toString(String sourceString, boolean nullable) {
        return toString(sourceString, STRING_DEFAULT, nullable);
    }

    /**
     * int 类型转换方法
     *
     * @param sourceString 转换的源字符串
     * @param defaultValue 自定义的默认值，转换失败后如果不允许崩溃，就把这个作为转换结果
     * @param nullable     这个是否允许转换的源字符串为空
     */
    public static int toInt(String sourceString, int defaultValue, boolean nullable) {
        int result = defaultValue;

        if (isStringEmpty(sourceString, nullable)) return result;

        if (isAllowCrash()) {
            result = Integer.parseInt(sourceString);
        } else {
            try {
                result = Integer.parseInt(sourceString);
            } catch (Exception e) {
                reportError(ERROR_DATA_TIPS);
            }
        }

        return result;
    }

    public static int toInt(String sourceString) {
        return toInt(sourceString, INT_DEFAULT, false);
    }

    public static int toInt(String sourceString, int defaultValue) {
        return toInt(sourceString, defaultValue, false);
    }

    public static int toInt(String sourceString, boolean nullable) {
        return toInt(sourceString, INT_DEFAULT, nullable);
    }

    /**
     * long 类型转换方法
     *
     * @param sourceString 转换的源字符串
     * @param defaultValue 自定义的默认值，转换失败后如果不允许崩溃，就把这个作为转换结果
     * @param nullable     这个是否允许转换的源字符串为空
     */
    public static long toLong(String sourceString, long defaultValue, boolean nullable) {
        long result = defaultValue;

        if (isStringEmpty(sourceString, nullable)) return result;

        if (isAllowCrash()) {
            result = Long.parseLong(sourceString);
        } else {
            try {
                result = Long.parseLong(sourceString);
            } catch (Exception e) {
                reportError(ERROR_DATA_TIPS);
            }
        }

        return result;
    }

    public static long toLong(String sourceString) {
        return toLong(sourceString, LONG_DEFAULT, false);
    }

    public static long toLong(String sourceString, int defaultValue) {
        return toLong(sourceString, defaultValue, false);
    }

    public static long toLong(String sourceString, boolean nullable) {
        return toLong(sourceString, LONG_DEFAULT, nullable);
    }

    /**
     * double 类型转换方法
     *
     * @param sourceString 转换的源字符串
     * @param defaultValue 自定义的默认值，转换失败后如果不允许崩溃，就把这个作为转换结果
     * @param nullable     这个是否允许转换的源字符串为空
     */
    public static double toDouble(String sourceString, double defaultValue, boolean nullable) {
        double result = defaultValue;

        if (isStringEmpty(sourceString, nullable)) return result;

        if (isAllowCrash()) {
            result = Double.parseDouble(sourceString);
        } else {
            try {
                result = Double.parseDouble(sourceString);
            } catch (Exception e) {
                reportError(ERROR_DATA_TIPS);
            }
        }

        return result;
    }

    public static double toDouble(String sourceString) {
        return toDouble(sourceString, DOUBLE_DEFAULT, false);
    }

    public static double toDouble(String sourceString, double defaultValue) {
        return toDouble(sourceString, defaultValue, false);
    }

    public static double toDouble(String sourceString, boolean nullable) {
        return toDouble(sourceString, DOUBLE_DEFAULT, nullable);
    }

    /**
     * float 类型转换方法
     *
     * @param sourceString 转换的源字符串
     * @param defaultValue 自定义的默认值，转换失败后如果不允许崩溃，就把这个作为转换结果
     * @param nullable     这个是否允许转换的源字符串为空
     */
    public static float toFloat(String sourceString, float defaultValue, boolean nullable) {
        float result = defaultValue;

        if (isStringEmpty(sourceString, nullable)) return result;

        if (isAllowCrash()) {
            result = Float.parseFloat(sourceString);
        } else {
            try {
                result = Float.parseFloat(sourceString);
            } catch (Exception e) {
                reportError(ERROR_DATA_TIPS);
            }
        }
        return result;
    }

    public static float toFloat(String sourceString) {
        return toFloat(sourceString, FLOAT_DEFAULT, false);
    }

    public static float toFloat(String sourceString, float defaultValue) {
        return toFloat(sourceString, defaultValue, false);
    }

    public static float toFloat(String sourceString, boolean nullable) {
        return toFloat(sourceString, FLOAT_DEFAULT, nullable);
    }

    /*-------------------------------------------------*/

    public static boolean isNotNull(Object object, boolean nullable) {
        if (object != null) return true;
        dealNullData(nullable);
        return false;
    }

    public static boolean isNotNull(Object object) {
        return isNotNull(object, false);
    }

    public static boolean isNull(Object object) {
        return !isNotNull(object);
    }

    public static boolean isNull(Object object, boolean nullable) {
        return !isNotNull(object, nullable);
    }

    public static boolean isEmpty(String string) {
        return isStringEmpty(string, false);
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    public static boolean isEmpty(String string, boolean nullable) {
        return isStringEmpty(string, nullable);
    }

    public static boolean isNotEmpty(String string, boolean nullable) {
        return !isStringEmpty(string, nullable);
    }

    public static boolean isEmpty(List list) {
        return !isNotEmpty(list);
    }

    public static boolean isNotEmpty(List list) {
        return isNotEmpty(list, false);
    }

    public static boolean isEmpty(List list, boolean nullable) {
        return !isNotEmpty(list, nullable);
    }

    public static boolean isNotEmpty(List list, boolean nullable) {
        if (list != null && (!list.isEmpty())) return true;
        dealNullData(nullable);
        return false;
    }

    /*--------------------------------------------------------------------------------------------*/

    @NonNull
    public static ArrayList<String> toStringList(String sourceString) {
        return toStringList(sourceString, false);
    }

    /**
     * 把用英文逗号分隔的string转换成一个ArrayList,可能结果size = 0
     */
    @NonNull
    public static ArrayList<String> toStringList(String sourceString, boolean nullable) {
        return toStringList(sourceString, nullable, ",");
    }

    public static ArrayList<String> toStringList(String sourceString, String divider) {
       return toStringList(sourceString, false, divider);
    }

    public static ArrayList<String> toStringList(String sourceString, boolean nullable, String divider) {
        ArrayList<String> allQuestionIds = new ArrayList<>();
        if (isStringEmpty(sourceString, nullable)) return allQuestionIds;

        StringTokenizer st = new StringTokenizer(sourceString, divider);
        while (st.hasMoreTokens()) {
            allQuestionIds.add(st.nextToken());
        }

        return allQuestionIds;
    }

    @NonNull
    public static String listToString(final List<String> list) {
        return listToString(list, false, ",");
    }

    @NonNull
    public static String listToString(final List<String> list, boolean nullable) {
        return listToString(list, nullable, ",");
    }

    @NonNull
    public static String listToString(final List<String> list, String divider) {
        return listToString(list, false, divider);
    }

    /**
     * 把一个List<String>转换成用任意字符分隔的string
     * ，,
     */
    @NonNull
    public static String listToString(final List<String> list, boolean nullable, String divider) {
        String result = "";
        if (isEmpty(list, nullable)) return result;

        for (String s : list) {
            result = result + s + divider;
        }
        result = result.substring(0, result.length() - divider.length());

        return result;
    }

    /*--------------------------------------------------------------------------------------------*/
    //                                      Base Method
    /*--------------------------------------------------------------------------------------------*/

    private static boolean isStringNotEmpty(String sourceString, boolean nullable) {
        if (!isBaseStringEmpty(sourceString)) return true;
        dealNullData(nullable);
        return false;
    }

    private static boolean isStringEmpty(String sourceString, boolean nullable) {
        return !isStringNotEmpty(sourceString, nullable);
    }

    private static boolean isBaseStringEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }

    private static void dealNullData(boolean nullable) {
        if (nullable) return;

        if (isAllowCrash()) {
            crashByNullData();
        } else {
            reportError(NULL_DATA_TIPS);
        }
    }

    /*-------------------------------------------------*/

    private static void crashByNullData() {
        Assert.assertTrue(NULL_DATA_TIPS, false);
    }

    private static boolean isAllowCrash() {
        return IS_DEBUG && (!DEBUG_CRASH_PROTECTOR);
    }

    private static void reportError(String message) {
        log(message);
        postToBugly(message);
    }

    private static void log(String message) {
//        FLog.showThread().exclude(DS.class).tag(TAG_DS).e().print(message);
    }

    private static void postToBugly(String type) {
        String isDebug = IS_DEBUG ? "debug" : "release";
//        TagThrowableUtil.pushTagExceptionMessage(37572, TAG_DS + ":" + type + " (" + isDebug + ")"); // 老师端
//        TagThrowableUtil.pushTagExceptionMessage(41671, TAG_DS + ":" + type + " (" + isDebug + ")"); // 学生端
    }
}
