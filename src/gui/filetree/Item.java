package gui.filetree;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import logic.dictionaries.VehicleNames;

/**
 *
 * @author FINDarkside
 */
public class Item implements Comparable<Item> {

    private List<String> order =new ArrayList<>(Arrays.asList("Cars", "Bikes", "Planes", "Helicopters", "Boats"));
    private File f;

    public Item(File f) {
        this.f = f;
    }

    public File getFile() {
        return this.f;
    }

    @Override
    public String toString() {
        if (f.isDirectory()) {
            return f.getName();
        }
        String s = VehicleNames.getName(f.getName().substring(0, f.getName().lastIndexOf('.')));
        return s != null ? s : f.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != Item.class) {
            return false;
        }
        Item i = (Item) o;

        return i.getFile().getAbsolutePath().equals(this.f.getAbsolutePath());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.f);
        return hash;
    }

    @Override
    public int compareTo(Item item) {
        if (this.f.isDirectory() && !item.getFile().isDirectory()) {
            return -1;
        }
        if (!this.f.isDirectory() && item.getFile().isDirectory()) {
            return 1;
        }

        if (this.f.isDirectory() && item.getFile().isDirectory()) {
            return order.indexOf(this.toString()) - order.indexOf(item.toString());
        }

        return this.toString().compareTo(item.toString());
    }
}
