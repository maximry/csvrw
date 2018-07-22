package ru.maximry.csvrw.utils;

import ru.maximry.csvrw.annitation.CsvCell;
import ru.maximry.csvrw.annitation.CsvModel;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class WriteScvFile {


    public WriteScvFile prepereFile(Path filepath) {
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
        return this;
    }

    public void write(String path, Object object){
        String delemiter = this.getDelimiter(object);
        String[] line = new String[object.getClass().getDeclaredFields().length];

        for (Field field: object.getClass().getDeclaredFields()){
            try {
                line[field.getAnnotation(CsvCell.class).num()] = field.get(object).toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        this.writeToFile(path,line,delemiter);
    }

    public void write(String path, List<Object> objects) {
        this.prepereFile(Paths.get(path));
        if(objects.get(0).getClass().getAnnotation(CsvModel.class).header()){
           this.writeHeader(path, objects.get(0));
        }
        for (Object object: objects){
            this.write(path, object);
        }
    }

    private String getDelimiter(Object object){
        return object.getClass().getAnnotation(CsvModel.class).delem();
    }

    private void writeHeader(String path, Object object){
        String[] line = new String[object.getClass().getDeclaredFields().length];
        for(Field field : object.getClass().getDeclaredFields()){
            line[field.getAnnotation(CsvCell.class).num()] = field.getName();
        }
        writeToFile(path, line,this.getDelimiter(object));
    }

    private void writeToFile(String path, String[] line, String delemiter){
        try {
            Files.write(Paths.get(path), Collections.singleton(String.join(delemiter, line)), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
