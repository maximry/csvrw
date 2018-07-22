package ru.maximry.csvrw;

import ru.maximry.csvrw.example.Model;
import ru.maximry.csvrw.utils.ReadScvFile;
import ru.maximry.csvrw.utils.WriteScvFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static ReadScvFile readScvFile = new ReadScvFile();
    static WriteScvFile writeScvFile = new WriteScvFile();


    public static void main(String[] args) throws NoSuchFieldException, InstantiationException, IllegalAccessException, IOException {
        List<Model> modelList = (List<Model>)(List<?>) readScvFile.read("1.csv", Model.class);
        System.out.println(modelList.get(1).getRecord());

        List<Model> list = new ArrayList<>();
        Model model = new Model();
        model.value = "123";
        model.name = "4321";

        Model model1 = new Model();
        model1.value = "a123";
        model1.name = "a4321";

        list.add(model);
        list.add(model1);
        writeScvFile.write("2.csv", (List<Object>)(List<?>) list);

    }

}
