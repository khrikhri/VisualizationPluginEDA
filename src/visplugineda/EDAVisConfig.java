package visplugineda;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mo.organization.Configuration;
import mo.visualization.Playable;
import mo.visualization.VisualizableConfiguration;

public class EDAVisConfig implements VisualizableConfiguration {

        
    private String id;
    EDAPlayer player;
    List<File> files;
    private final String[] creators = {"capturepluginbitalino.BitalinoRecorder2",
    "capturepluginbitalino.BitalinoRecorder4",
    "capturepluginbitalino.BitalinoRecorder5",
    "capturepluginbitalino.BitalinoRecorder6"};
    

    public EDAVisConfig() {
        files = new ArrayList<>();
    }
    
   @Override
    public String getId() {
        return id;
    }

    @Override
    public File toFile(File parent) {
        File f = new File(parent, "eda-visualization_"+id+".xml");
        try {
            f.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(EDAVisConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    }

    @Override
    public Configuration fromFile(File file) {
        String fileName = file.getName();

        if (fileName.contains("_") && fileName.contains(".")) {
            String name = fileName.substring(fileName.indexOf("_")+1, fileName.lastIndexOf("."));
            EDAVisConfig config = new EDAVisConfig();
            config.id = name;
            return config;
        }
        return null;
    }

    @Override
    public List<String> getCompatibleCreators() {
       return Arrays.asList(creators);
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    private void ensurePlayerCreated() {
        if (player == null && !files.isEmpty()) {
            player = new EDAPlayer(files.get(0));
        }
    }

    @Override
    public void addFile(File file) {
        if ( !files.contains(file) ) {
            this.files.add(file);
        }
    }

    public Playable getPlayer() {
        ensurePlayerCreated();
        return player;
    }
    
    @Override
    public void removeFile(File file) {
        File toRemove = null;
        
        for (File f : files) {
            if (f.equals(file)) {
                toRemove = f;
            }
        }
        
        if (toRemove != null) {
            files.remove(toRemove);
        }
    }

    
}
