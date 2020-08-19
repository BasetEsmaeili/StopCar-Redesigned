package ir.esmaeili.stopcar.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import androidx.core.os.ConfigurationCompat;
import androidx.preference.PreferenceManager;

import java.util.Locale;

public class LocaleWrapper {

    private static Locale locale;

    public static Context onAttach(Context context) {
        String lang = PreferenceManager.getDefaultSharedPreferences(context).getString(Constants.KEY_LOCALE, "1");
        if (lang.equals("0")) {
            locale = new Locale("en-us");
        } else if (lang.equals("1")) {
            locale = new Locale("fa");
        } else {
            locale = ConfigurationCompat.getLocales(context.getResources().getConfiguration()).get(0);
        }
        return setLocale(context, locale);
    }

    private static Context setLocale(Context context, Locale language) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        }
        return updateResourcesLegacy(context, language);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, Locale locale) {
        Locale.setDefault(locale);
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private static Context updateResourcesLegacy(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        Locale.setDefault(locale);
        configuration.setLayoutDirection(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }


    public static Locale getCurrentLocale() {
        return locale;
    }
}
