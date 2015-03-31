package gui.filetree;

import java.io.File;

/**
 *
 * @author Jesse
 */
public class FileNode extends FileTreeNode{
    private File file;

    public FileNode(File f, FileTreeNode parent){
        this.parent = parent;
        file = f;
    }
    
    @Override
    public File getFile() {
        return file;
    }
    
   
}
