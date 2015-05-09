package name.walnut.kanjian.app.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 联系人utils
 */
public class ContactsUtils {

    /**
     * 获取通讯录所有联系人
     * @param context
     * @return
     */
    public static Map<String, String> getAllContacts(Context context) {
        return getContacts(context, null);
    }

    /**
     * 根据phone查找联系人
     * @param context
     * @param phone
     * @return
     */
    public static Map<String, String> getContactsByPhone(Context context, String phone) {
        String[] phones = {phone};
        return getContactsByPhone(context, phones);
    }

    /**
     * 根据phones查找联系人
     * @param context
     * @param phones
     * @return
     */
    public static Map<String, String> getContactsByPhone(Context context, String[] phones) {
        return getContacts(context, phones);
    }

    /**
     * 获取通讯录联系人
     * @param context
     * @param phones 筛选的手机号，null表示获取全部
     * @return
     */
    private static Map<String, String> getContacts(Context context, String[] phones) {

        Map<String, String> resultMap = new HashMap<>();

        String[] projection = {
                ContactsContract.PhoneLookup.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
        };

        Cursor cursor = null;

        if (phones == null) {
            // 获取全部联系人
            cursor = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    projection, // Which columns to return.
                    null,
                    null, // WHERE clause value substitution
                    null); // Sort order.
        } else {
            cursor = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    projection, // Which columns to return.
                    ContactsContract.CommonDataKinds.Phone.NUMBER + " IN (" + makePlaceholders(phones.length) + ")", // WHERE clause.
                    phones, // WHERE clause value substitution
                    null); // Sort order.
        }


        if (cursor == null) {
            return resultMap;
        }
        for( int i = 0; i < cursor.getCount(); i++ ){
            cursor.moveToPosition(i);

            // 取得联系人名字
            String name = cursor.getString(cursor.getColumnIndex(projection[0]));
            String phone = cursor.getString(cursor.getColumnIndex(projection[1]));

            resultMap.put(phone, name);
        }
        cursor.close();
        return resultMap;
    }

    private static String makePlaceholders(int len) {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }

}
