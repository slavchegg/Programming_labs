package lab5;

import java.util.ListResourceBundle;

public class Messages_ru_RU extends ListResourceBundle{
	private Object[][] contents = {
			{"Color", "����"},
            {"Color_green", "������"},
            {"Color_red", "�������"},
            {"Color_yellow", "Ƹ����"},
            {"Color_black", "׸����"},
            {"Color_blue", "�����"},
            {"Color_white", "�����"},
            {"Size", "������"},
            {"Check_both", "�����"},
            {"Check_empty", "������"},
            {"Check_not_empty", "�� ������"},
            {"Name", "������� ���..."},
            {"Buttons_start", "�����"},
            {"Buttons_stop", "����"},
            {"Language", "����"},
            {"Frame", "���������"}
    };
	@Override
	protected Object[][] getContents() {
		// TODO Auto-generated method stub
		return contents;
	}

}
