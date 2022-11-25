@XmlJavaTypeAdapter(type = LocalDate.class,value = LocalDateAdapter.class)

package hu.webuni.studentregister202210.soap;


import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;