package gui.filetree;

import java.io.File;
import java.util.*;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Jesse
 */
public class FileTreeModel implements TreeModel {

    public final static String projects = "Current projects";

    protected FileTreeNode root;
    protected FileTreeNode openedProjects;
    protected FileTreeNode defaultVehicles;
    protected EventListenerList listenerList = new EventListenerList();

    public FileTreeModel(File defaultRoot) {
        root = new FileTreeNode(new File("Root"), null);
        defaultVehicles = new FileTreeNode(defaultRoot, root);
        openedProjects = new FileTreeNode(new File(projects), root);

        this.root.childs.add(openedProjects);
        this.root.childs.add(defaultVehicles);

        init(defaultVehicles);
    }

    private void init(FileTreeNode root) {
        if (!root.getFile().isDirectory()) {
            return;
        }
        for (File f : root.getFile().listFiles()) {
            FileTreeNode child = new FileTreeNode(f, root);
            root.childs.add(child);
            init(child);
        }
        Collections.sort(root.childs);
    }

    public void addProject(File f) {
        FileTreeNode node = new FileTreeNode(f, openedProjects);
        openedProjects.childs.add(node);
        int[] arr = {openedProjects.childs.size() - 1};
        nodesWereInserted(openedProjects, arr);
    }

    public void closeProject(File f) {
        FileTreeNode[] node = {new FileTreeNode(f, openedProjects)};
        int[] index = {openedProjects.getIndex(node[0])};
        openedProjects.childs.remove(index[0]);
        nodesWereRemoved(openedProjects, index, node);
    }

  

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public Object getChild(Object parent, int index) {
        FileTreeNode node = (FileTreeNode) parent;
        return node.getChildAt(index);
    }

    @Override
    public int getChildCount(Object parent) {
        FileTreeNode node = (FileTreeNode) parent;
        return node.getChildCount();
    }

    @Override
    public boolean isLeaf(Object o) {
        FileTreeNode node = (FileTreeNode) o;
        return node.isLeaf();
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        FileTreeNode p = (FileTreeNode) parent;
        FileTreeNode c = (FileTreeNode) child;
        return p.getIndex(c);
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        listenerList.add(TreeModelListener.class, l);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        listenerList.remove(TreeModelListener.class, l);
    }

    protected TreeNode[] getPathToRoot(TreeNode aNode, int depth) {
        TreeNode[] retNodes;

        depth++;
        if (aNode == root) {
            retNodes = new TreeNode[depth];
        } else {
            retNodes = getPathToRoot(aNode.getParent(), depth);
        }
        retNodes[retNodes.length - depth] = aNode;
        return retNodes;
    }

    public void nodesWereInserted(TreeNode node, int[] childIndices) {

        int cCount = childIndices.length;
        Object[] newChildren = new Object[cCount];

        for (int counter = 0; counter < cCount; counter++) {
            newChildren[counter] = node.getChildAt(childIndices[counter]);
        }
        fireTreeNodesInserted(this, getPathToRoot(node, 0), childIndices, newChildren);

    }

    public void nodesWereRemoved(TreeNode node, int[] childIndices, Object[] removedChildren) {
        if (node != null && childIndices != null) {
            fireTreeNodesRemoved(this, getPathToRoot(node, 0), childIndices, removedChildren);
        }
    }

    protected void fireTreeNodesInserted(Object source, Object[] path, int[] childIndices, Object[] children) {
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TreeModelListener.class) {
                if (e == null) {
                    e = new TreeModelEvent(source, path, childIndices, children);
                }
                ((TreeModelListener) listeners[i + 1]).treeNodesInserted(e);
            }
        }
    }

    protected void fireTreeNodesRemoved(Object source, Object[] path, int[] childIndices, Object[] children) {

        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TreeModelListener.class) {
                if (e == null) {
                    e = new TreeModelEvent(source, path,
                            childIndices, children);
                }
                ((TreeModelListener) listeners[i + 1]).treeNodesRemoved(e);
            }
        }
    }
}
