package se.ifmo.lab08.client.resourcebundles.enums;

import se.ifmo.lab08.client.controller.MainFormController;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public enum AvailableLocales {
    //    ENGLISH(new Locale("en"), "english", ZoneId.of("US/Central")),
    RUSSIAN(new Locale("ru"), "russian", ZoneId.of("Europe/Moscow")),
    ESTONIA(new Locale("lt"), "lithuanian", ZoneId.of("Europe/Vilnius")),
    FINNISH(new Locale("fi"), "finnish", ZoneId.of("Europe/Helsinki")),
    SPANISH(new Locale("es", "MX"), "spanish", ZoneId.of("Europe/Madrid"));

    private Locale locale;

    private DateTimeFormatter dateTimeFormatter;

    private final String bundleObjectName;

    private final ZoneId zoneId;

    AvailableLocales(Locale locale, String bundleObjectName, ZoneId zoneId) {
        this.locale = locale;
        this.bundleObjectName = bundleObjectName;
        this.zoneId = zoneId;
        this.dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).localizedBy(locale);
    }

    public Locale getLocale() {
        return locale;
    }

    public ZoneId getZoneID() {
        return zoneId;
    }

    @Override
    public String toString() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resourcebundle.locale.AvailableLocales", MainFormController.getCurrentLocale().get().getLocale());
        return resourceBundle.getString(bundleObjectName);
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }
}
