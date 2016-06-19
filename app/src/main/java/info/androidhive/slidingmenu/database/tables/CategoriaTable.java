package info.androidhive.slidingmenu.database.tables;

/**
 * Created by Juan on 19/06/2016.
 */
public class CategoriaTable {

    public static final String TABLE_NAME = "categoria";

    public static final String COD_CAT = "codCat";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_DESCRIPTION= "descripcion";
    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
            " ("+COD_CAT+" TEXT, " +
            CATEGORY_NAME+" TEXT, " +
            CATEGORY_DESCRIPTION +" TEXT, " +
            "PRIMARY KEY ("+COD_CAT+"))";


    public static final String INSERT_TABLE(String codCat, String category_name, String description_cat){
        return "INSERT INTO "+CategoriaTable.TABLE_NAME+" VALUES ('"+codCat+"', '"+category_name+"', '"+description_cat+"')";
    }
}
