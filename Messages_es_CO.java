package lab5;

import java.util.ListResourceBundle;

public class Messages_es_CO extends ListResourceBundle{
	private Object[][] contents = {
			{"Color", "Colorear"},
            {"Color_green", "Verde"},
            {"Color_red", "Rojo"},
            {"Color_yellow", "Amarillo"},
            {"Color_black", "Negro"},
            {"Color_blue", "Azul"},
            {"Color_white", "Blanco"},
            {"Size", "Tamaño"},
            {"Check_both", "Cualquier"},
            {"Check_empty", "Vacio"},
            {"Check_not_empty", "No vacio"},
            {"Name", "Ingrese el nombre..."},
            {"Buttons_start", "Empezar"},
            {"Buttons_stop", "Parar"},
            {"Language", "Idioma"},
            {"Frame", "Coleccion"}

    };
    @Override
    protected Object[][] getContents() {
        return contents;
    }

}
