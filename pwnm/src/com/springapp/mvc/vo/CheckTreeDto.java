package com.springapp.mvc.vo;


import java.util.List;

public class CheckTreeDto {

    private static CheckTreeDto instance;

    private TreeStyle checkbox;

    private List<String> plugins;

    private TreeCode core;

    private CheckTreeDto() {
    }

    public static CheckTreeDto getInstance(){
        if(instance == null){
            instance = new CheckTreeDto();
        }

        return instance;
    }

    public TreeStyle getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(TreeStyle checkbox) {
        this.checkbox = checkbox;
    }

    public List<String> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<String> plugins) {
        this.plugins = plugins;
    }

    public TreeCode getCore() {
        return core;
    }

    public void setCore(TreeCode core) {
        this.core = core;
    }
}
