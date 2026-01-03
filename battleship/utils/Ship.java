package battleship.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ship {
    final List<Field> fields = new ArrayList<>();

    public Ship (Field initialField) {
        fields.add(initialField);
    }
    public Ship (Field[] initialFields) {
        fields.addAll(Arrays.asList(initialFields));
    }

    public void  addField(Field field) {
        fields.add(field);
    }
    public List<Field> getFields() { return List.copyOf(fields); }
    public int getSize() {
        return  fields.size();
    }
    public boolean isSunk() {
        for (Field field : fields) {
            if (!field.shot) return false;
        }
        return true;
    }
}
