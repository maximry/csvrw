package ru.maximry.csvrw.example;

import ru.maximry.csvrw.annitation.CsvCell;
import ru.maximry.csvrw.annitation.CsvModel;

@CsvModel(delem = ";", header = true)
public class Model {

    @CsvCell(name = "name", num = 0)
    public String name;

    @CsvCell(name = "value", num = 1)
    public String value;

    public String getRecord(){
        return name + " = " + value;
    }

}
