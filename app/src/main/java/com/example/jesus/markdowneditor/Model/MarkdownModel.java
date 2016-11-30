package com.example.jesus.markdowneditor.Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * Created by jesus on 29/11/16.
 */

public class MarkdownModel {

    private static List<MarkdownModel> modelList = new ArrayList<>();
    private static List<Long> ids = new ArrayList<>();
    private static List<MarkdownModel> deletedList = new ArrayList<>();
    private static MarkdownModel upsertModel = null;

    private Long id;
    private String title;
    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static int getSize(){
        return modelList.size();
    }

    public static MarkdownModel getbyid(int i){
        return modelList.get(i);
    }

    public static void upsert(MarkdownModel m){
        upsertModel = m;
    }

    public static void store(List<MarkdownModel> l){
        for (MarkdownModel m: l) {
            Long id = m.getId();
            if (!ids.contains(id) && id != null){
                ids.add(id);
                modelList.add(m);
            }
        }
    }

    public static void delete(List<MarkdownModel> list){
        for (MarkdownModel m : list) {
            modelList.remove(m);
            deletedList.add(m);
        }
    }

    public static MarkdownModel getToUpsert(){
        MarkdownModel actual = upsertModel;
        upsertModel = null;
        return actual;
    }

    public static List<MarkdownModel> getToDelete(){
        List<MarkdownModel> actual = deletedList;
        deletedList = new ArrayList<>();
        return actual;
    }

}
