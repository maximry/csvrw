package ru.maximry.csvrw.utils;

import ru.maximry.csvrw.annitation.CsvCell;
import ru.maximry.csvrw.annitation.CsvColumnDelemiter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadScvFile {


    public ArrayList<Object> read(String path, Class clazz) {
        ArrayList<Object> list = new ArrayList<>();
        String delemiter = null;
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line: lines){
            Object object = null;
            try {
                object = clazz.newInstance();
                delemiter = object.getClass().getAnnotation(CsvColumnDelemiter.class).delem();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            String[] spl = line.split(delemiter);
            for (Field field: clazz.getDeclaredFields()){
                field.setAccessible(true);
                try {
                    field.set(object, spl[field.getAnnotation(CsvCell.class).num()]);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            list.add(object);
        }
        return list;
    }


}
