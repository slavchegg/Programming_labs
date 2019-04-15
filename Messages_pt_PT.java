package lab5;

import java.util.ListResourceBundle;

public class Messages_pt_PT extends ListResourceBundle{
	private Object[][] contents = {
			{"Color", "Cor"},
            {"Color_green", "Verde"},
            {"Color_red", "Vermelho"},
            {"Color_yellow", "Amarelo"},
            {"Color_black", "Preto"},
            {"Color_blue", "Azul"},
            {"Color_white", "Branco"},
            {"Size", "Tamanho"},
            {"Check_both", "Ambos"},
            {"Check_empty", "Vazio"},
            {"Check_not_empty", "Não vazio"},
            {"Name", "Entre no nome..."},
            {"Buttons_start", "O começo"},
            {"Buttons_stop", "Pare"},
            {"Language", "Idioma"},
            {"Frame", "A coleção"}

    };
    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
