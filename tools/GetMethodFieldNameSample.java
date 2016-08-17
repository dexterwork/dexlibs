package bundles;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by SkykingAndroid on 2016/8/17.
 */
public class GetMethodFieldNameSample {


//    private void getMethodSample() {

//        for (Method method : someObject.getClass().getDeclaredMethods()) {
//            if (Modifier.isPublic(method.getModifiers())
//                    && method.getParameterTypes().length == 0
//                    && method.getReturnType() != void.class
//                    && (method.getName().startsWith("get") || method.getName().startsWith("is"))
//                    ) {
//                Object value = method.invoke(someObject);
//                if (value != null) {
//                    System.out.println(method.getName() + "=" + value);
//                }
//            }
//        }
//    }

    //取得自己及父類別所有 public 的變數名稱及值
    private HashMap<String, String> getFieldSample() {
        HashMap<String, String> bundleMap = new HashMap<>();
        for (Field field : this.getClass().getFields()) {
            field.setAccessible(true);
            Object obj = null;
            try {
                obj = field.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            String value = "";
            if (obj != null) value = obj.toString();
            if (field.getName().contains("$")) continue;
            bundleMap.put(field.getName(), value);
        }
        return bundleMap;
    }
}
