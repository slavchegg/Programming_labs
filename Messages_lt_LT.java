package lab5;

import java.util.ListResourceBundle;

public class Messages_lt_LT extends ListResourceBundle{
	private Object[][] contents = {
			{"Color", "Spalva"},
            {"Color_green", "Žalia"},
            {"Color_red", "Raudona"},
            {"Color_yellow", "Geltona"},
            {"Color_black", "Juoda"},
            {"Color_blue", "Mėlyna"},
            {"Color_white", "Balta"},
            {"Size", "Dydis"},
            {"Check_both", "Bet koks"},
            {"Check_empty", "Tuščia"},
            {"Check_not_empty", "Ne tuščias"},
            {"Name", "Įveskite pavadinimą..."},
            {"Buttons_start", "Pradėti"},
            {"Buttons_stop", "Sustoti"},
            {"Language", "Kalba"},
            {"Frame", "Kolekcija"}

    };
    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
