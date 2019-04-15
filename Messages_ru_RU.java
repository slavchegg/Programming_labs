package lab5;

import java.util.ListResourceBundle;

public class Messages_ru_RU extends ListResourceBundle{
	private Object[][] contents = {
			{"Color", "Цвет"},
            {"Color_green", "Зелёный"},
            {"Color_red", "Красный"},
            {"Color_yellow", "Жёлтый"},
            {"Color_black", "Чёрный"},
            {"Color_blue", "Синий"},
            {"Color_white", "Белый"},
            {"Size", "Размер"},
            {"Check_both", "Любой"},
            {"Check_empty", "Пустой"},
            {"Check_not_empty", "Не пустой"},
            {"Name", "Введите имя..."},
            {"Buttons_start", "Старт"},
            {"Buttons_stop", "Стоп"},
            {"Language", "Язык"},
            {"Frame", "Коллекция"}
    };
	@Override
	protected Object[][] getContents() {
		// TODO Auto-generated method stub
		return contents;
	}

}
