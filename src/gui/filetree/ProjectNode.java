package gui.filetree;

import java.io.File;
import logic.Project;

/**
 *
 * @author Jesse
 */
public class ProjectNode extends FileTreeNode {
    
    private final Project project;
    
    public ProjectNode(Project project,FileTreeNode parent){
        this.parent = parent;
        this.project = project;
    }

    @Override
    public File getFile() {
        return project.getEez();
    }

}

