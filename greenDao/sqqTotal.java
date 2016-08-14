package com.sqq.database;

import java.io.File;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class sqqTotal {
	
	public static final String GREEN_DAO_CODE_PATH = "../LearnGreen/src-sqqTotal";
	 
	public static void main(String[] args) throws Exception{
		Schema schema = new Schema(1,"com.sqq.databaseDao");
		
		Entity forum = schema.addEntity("User");
        forum.addIdProperty().autoincrement().primaryKey();
        forum.addLongProperty("userId").notNull();
        forum.addStringProperty("password").notNull();
        forum.addIntProperty("age");
        forum.addIntProperty("sex");
        forum.addStringProperty("nickName");
        forum.addStringProperty("picUrl");
        forum.addStringProperty("telNumber");
        
        File f = new File(GREEN_DAO_CODE_PATH);
        if (!f.exists()) {
            f.mkdirs();
        }
        
        new DaoGenerator().generateAll(schema, f.getAbsolutePath());
	}
}
