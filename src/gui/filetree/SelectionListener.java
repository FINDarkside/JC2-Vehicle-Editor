
package gui.filetree;

import java.io.File;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import logic.Logic;

/**
 *
 * @author FINDarkside
 */
public class SelectionListener implements TreeSelectionListener{
    JTree tree;
    private Logic logic;
    
    public SelectionListener(JTree tree,Logic logic){
        this.tree = tree;
        this.logic = logic;
    }

    @Override
    public void valueChanged(TreeSelectionEvent tse) {
            if(tree.getLastSelectedPathComponent().getClass() != Item.class){
                return;
            }
        
            Item item = (Item) tree.getLastSelectedPathComponent();
            if(item == null){
                return;
            }
            
            File f = item.getFile();
            if(f.isFile()){
                logic.loadFile(f);
            }
                
            
            

    }
    
}
