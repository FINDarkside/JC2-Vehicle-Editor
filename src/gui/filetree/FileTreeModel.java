package gui.filetree;

import java.io.File;
import java.util.*;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author FINDarkside
 */
public class FileTreeModel implements TreeModel {

    private EventListenerList listeners = new EventListenerList();
    private Item root;
    private HashMap<Item, ArrayList<Item>> map = new HashMap<>();

    public FileTreeModel(File root) {
        this.root = new Item(root);
        map.put(this.root, listChilds(root));
        init(root);
    }

    private void init(File root) {
        for (File f : root.listFiles()) {
            if (f.isFile() && f.getName().endsWith("eez")) {
                map.put(new Item(f), new ArrayList<Item>());
            }
            if (f.isDirectory()) {
                map.put(new Item(f), listChilds(f));
                init(f);
            }

        }
    }

    private ArrayList<Item> listChilds(File root) {
        if (root.isFile()) {
            return null;
        }
        ArrayList<Item> items = new ArrayList<>();
        File[] files = root.listFiles();

        for (File f : root.listFiles()) {
            if (!(f.isFile() && !f.getName().endsWith("eez"))) {
                items.add(new Item(f));
            }
        }
        Collections.sort(items);
        return items;
    }

    @Override
    public Object getRoot() {
        return this.root;
    }

    @Override
    public Object getChild(Object o, int i) {
        Item item = (Item) o;
        return map.get(item).get(i);
    }

    @Override
    public int getChildCount(Object o) {
        Item item = (Item) o;
        return map.get(item).size();
    }

    @Override
    public boolean isLeaf(Object o) {
        Item item = (Item) o;
        return item.getFile().isFile();
    }

    @Override
    public void valueForPathChanged(TreePath tp, Object o) {
    }

    @Override
    public int getIndexOfChild(Object o, Object o1) {
        Item item = (Item) o;
        Item item2 = (Item) o1;

        if (map.get(item) == null || map.get(item2) == null) {
            return -1;
        }

        ArrayList list = map.get(item);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(item2)) {
                return i;
            }
        }

        return -1;

    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {

    }

    @Override
    public void removeTreeModelListener(TreeModelListener tl) {

    }

}
