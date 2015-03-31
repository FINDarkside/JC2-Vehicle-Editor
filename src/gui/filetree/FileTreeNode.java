package gui.filetree;

import java.io.File;
import java.util.*;
import javax.swing.tree.TreeNode;
import logic.dictionaries.Vehicles;

/**
 *
 * @author FINDarkside
 */
public abstract class FileTreeNode implements Comparable<FileTreeNode>, TreeNode {

    private static List<String> order = new ArrayList<>(Arrays.asList("Cars", "Bikes", "Planes", "Helicopters", "Boats"));

    protected final List<FileTreeNode> childs = new ArrayList<>();
    protected FileTreeNode parent;


    public abstract File getFile();

    @Override
    public String toString() {
        String fileName = getFile().getName();
        if (!getFile().isFile()) {
            return fileName;
        }
        String s = Vehicles.getName(fileName.substring(0, fileName.lastIndexOf('.')));
        return s != null ? s : fileName;
    }

    @Override
    public int compareTo(FileTreeNode node) {
        File f2 = node.getFile();

        if (getFile().isDirectory() && !f2.isDirectory()) {
            return -1;
        }
        if (!getFile().isDirectory() && f2.isDirectory()) {
            return 1;
        }
        if (getFile().isDirectory() && f2.isDirectory()) {
            return order.indexOf(this.toString()) - order.indexOf(node.toString());
        }
        return getFile().getName().compareTo(f2.getName());
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return childs.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return childs.size();
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return childs.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return getFile().isFile();
    }

    @Override
    public Enumeration children() {
        return Collections.enumeration(childs);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        FileTreeNode node = (FileTreeNode) o;
        return getFile().equals(node.getFile());

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(getFile());
        return hash;
    }
}
