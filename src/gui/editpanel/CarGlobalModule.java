package gui.editpanel;

import java.util.List;

/**
 *
 * @author FINDarkside
 */
public class CarGlobalModule extends EditPanel {

    public CarGlobalModule(List<String> text) {
        text = text;

        for(int i = 3;i<3+14;i++){
            createTextField(text.get(i));
        }

    }

}
