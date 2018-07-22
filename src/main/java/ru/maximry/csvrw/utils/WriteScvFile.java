package ru.maximry.csvrw.utils;

import ru.maximry.csvrw.annitation.CsvCell;
import ru.maximry.csvrw.annitation.CsvColumnDelemiter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;

public class WriteScvFile {


    private void prepereFile(Path filepath) {
        if(Files.exists(filepath)){
            try {
                Files.delete(filepath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Files.createFile(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String path, List<Object> objects) {
        Path filepath = Paths.get(path);
        this.prepereFile(filepath);

        for (Object object: objects){
            String delemiter = object.getClass().getAnnotation(CsvColumnDelemiter.class).delem();
            String[] line = new String[object.getClass().getDeclaredFields().length];

             for (Field field: object.getClass().getDeclaredFields()){
                 try {
                     line[field.getAnnotation(CsvCell.class).num()] = field.get(object).toString();
                 } catch (IllegalAccessException e) {
                     e.printStackTrace();
                 }
             }

            try {
                Files.write(filepath, Collections.singleton(String.join(delemiter, line)), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}
