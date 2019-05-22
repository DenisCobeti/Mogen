package model.constants;

/**
 *
 * @author Denis C
 */
public enum Sumo {
    
    PROGRAMM("sumo");
    
    private String opcion;

    private Sumo(String opcion) {
        this.opcion = opcion;
    }

    @Override
    public String toString() {
        return opcion;
    }
    
    
}
