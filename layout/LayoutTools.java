package studio.dexter.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.HashMap;

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
        View view = inflater.inflate(customLayout, null);
        return getCustomAlertDialog(ctx, view);
    }

    public static AlertDialog.Builder getCustomAlertDialog(Context ctx, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(view);
        builder.create();
        return builder;
    }

    public static HashMap<LayoutFlag, Integer> getLayoutSize(final View view) {
        final HashMap<LayoutFlag, Integer> map = new HashMap<LayoutFlag, Integer>();
        ViewTreeObserver obs = view.getViewTreeObserver();
        obs.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                map.put(LayoutFlag.HEIGHT, view.getHeight());
                map.put(LayoutFlag.WIDTH, view.getWidth());
                view.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
            }
        });
        return map;
    }

    public static HashMap<LayoutFlag, Float> getLayoutPosition(final View view) {
        final HashMap<LayoutFlag, Float> map = new HashMap<LayoutFlag, Float>();
        ViewTreeObserver obs = view.getViewTreeObserver();
        obs.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                map.put(LayoutFlag.X, view.getX());
                map.put(LayoutFlag.Y, view.getY());
                view.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
            }
        });
        return map;
    }

    public enum LayoutFlag {
        WIDTH, HEIGHT, X, Y
    }

}
