package ru.maximry.csvrw.utils;

import ru.maximry.csvrw.annitation.CsvCell;
import ru.maximry.csvrw.annitation.CsvModel;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadScvFile {

    private List<String> readFile(String path){
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }


    public ArrayList<Object> read(String path, Class clazz) {
        ArrayList<Object> resultList = new ArrayList<>();
        String delemiter = null;
        int ai = 0;
        try {
            Object obj = clazz.newInstance();
            delemiter = obj.getClass().getAnnotation(CsvModel.class).delem();
            if(obj.getClass().getAnnotation(CsvModel.class).header())
                ai = 1;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }


        List<String> lines = this.readFile(path);

        for (int i = ai;i<lines.size();i++){
            Object object = null;
            try {
                object = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            String[] spl = lines.get(i).split(delemiter);
            for (Field field: clazz.getDeclaredFields()){
                field.setAccessible(true);
                try {
                    field.set(object, spl[field.getAnnotation(CsvCell.class).num()]);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            resultList.add(object);
        }
        return resultList;
    }


}
