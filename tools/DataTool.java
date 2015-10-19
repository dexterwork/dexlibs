package com.awant.lion.tools;

import android.database.Cursor;

import com.awant.lion.data_provider.AppDataContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by dexter on 2015/10/2.
 */
public class DataTool {
    


     public static boolean matchesEmail(String email) {
        return email.matches("^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$") ? true : false;
    }
}
