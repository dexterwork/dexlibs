package studio.dexter.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import studio.dexter.ohmoney.R;

/**
 * Created by 旺比優05 on 2015/7/13.
 */
public class LayoutTools {

    public static LayoutInflater getInflater(Context context) {
        return (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public static AlertDialog.Builder getCustomAlertDialog(Context ctx, int customLayout) {
        LayoutInflater inflater = LayoutTools.getInflater(ctx);
        View view=inflater.inflate(customLayout,null);
        return getCustomAlertDialog(ctx,view);
    }
    public static AlertDialog.Builder getCustomAlertDialog(Context ctx,View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(view);
        builder.create();
        return builder;
    }

}
