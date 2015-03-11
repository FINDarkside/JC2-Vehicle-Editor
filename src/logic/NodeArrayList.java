package logic;

import java.util.ArrayList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Jesse
 */
public class NodeArrayList extends ArrayList<Node> implements NodeList {

    @Override
    public Node item(int index) {
        return this.get(index);
    }

    @Override
    public int getLength() {
        return this.size();
    }
    

}
