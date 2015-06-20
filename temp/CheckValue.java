import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;

public class CheckValue {

	private final String HASH_KEY = "jfjrX5aj0NmIrUs6IHCkVI9ty9VcgkDz";
	private final String HASH_IV = "zW0w2bQ2ZcKq7dwP";

	public static final String AMT = "Amt";
	public static final String MERCHANT_ID = "MerchantID";
	public static final String MERCHANT_ORDER_NO = "MerchantOrderNo";
	public static final String TIME_STAMP = "TimeStamp";
	public static final String VERSION = "Version";


	public CheckValue() {

	}

	public void showCheckValue(String time,String amt,String order) {
		HashMap<String, String> params2 = new HashMap<String, String>();
		params2.put(TIME_STAMP, time);
		params2.put(AMT, amt);// 價格
		params2.put(MERCHANT_ID, "3679911");// 商店代號
		params2.put(MERCHANT_ORDER_NO, order);// 訂單單號
		params2.put(VERSION, "1.1");

		System.out.println(getCheckValue(params2));
	}

	public String getCheckValue(HashMap<String, String> params) {
		String[] keys = new String[] { AMT, MERCHANT_ID, MERCHANT_ORDER_NO,
				TIME_STAMP, VERSION };
		Arrays.sort(keys);
		String str = "HashKey=" + HASH_KEY + "&";
		for (String s : keys) {
			str += s + "=" + params.get(s) + "&";
		}
		str += "HashIV=" + HASH_IV;
		return toHex256(str);
	}

	private String toHex256(String value) {
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA-256");
			sha.update(value.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return byte2hex(sha.digest());
	}

	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

}
