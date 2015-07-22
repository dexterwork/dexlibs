package studio.dexter.tools;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Dexter on 2015/7/22.
 */
public class FormatKing {

    /**
     * set format (parameters) as "#,###,###,###" or "#,###,###,###.00"
     * return likes "1,234,567,890" or "1,234,567,890.00"
     * return likes "NT$1,004,234.00" when format (parameters) is null.
     *
     * @param currency
     * @return
     */
    public static String currencyFormat(double currency, String format) {
        if (TextUtils.isEmpty(format)) {
            return NumberFormat.getCurrencyInstance().format(currency);
        }
        return new DecimalFormat(format).format(currency);
    }

}
