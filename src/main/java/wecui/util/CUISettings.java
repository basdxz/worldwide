package wecui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Stores and reads WorldEditCUI settings
 * 
 * TODO: Use yaml instead
 * 
 * @author yetanotherx
 */
public class CUISettings extends Properties {

    private static final long serialVersionUID = 1L;
    protected File propertiesFile;

    public CUISettings(File propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    public void load() throws IOException {
        if (this.propertiesFile == null) {
            throw new RuntimeException("Internal error: Properties file is not set.");
        } else {
            this.saveDefaultUnlessExists();
        }

        FileInputStream istream = new FileInputStream(this.propertiesFile);
        this.load(istream);
    }

    public void save() throws IOException {
        if (this.propertiesFile == null) {
            throw new RuntimeException("Internal error: Properties file is not set.");
        } else {
            this.saveDefaultUnlessExists();
        }

        FileOutputStream ostream = new FileOutputStream(this.propertiesFile);
        this.store(ostream, "WorldEditCUI properties file");
    }

    protected void saveDefaultUnlessExists() throws IOException {
        if (this.propertiesFile == null) {
            throw new RuntimeException("Internal error: Properties file is not set.");
        } else {
            if (!this.propertiesFile.exists()) {
                this.setProperty("debugMode", "false");

                this.propertiesFile.createNewFile();
                FileOutputStream ostream = new FileOutputStream(this.propertiesFile);
                this.store(ostream, "WorldEditCUI properties file");
            }
        }


    }
}
