package hu.webuni.studentregister202210.soap;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    private static final String CUSTOM_FORMAT_STRING = "yyyy-MM-dd";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CUSTOM_FORMAT_STRING);
    @Override
    public String marshal(LocalDate v) {
        return new SimpleDateFormat(CUSTOM_FORMAT_STRING).format(v);
    }

    @Override
    public LocalDate unmarshal(String v)  {
        return LocalDate.parse(v,formatter);
    }

}