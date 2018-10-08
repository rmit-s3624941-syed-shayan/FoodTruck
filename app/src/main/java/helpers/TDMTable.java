package helpers;

public class TDMTable {

    private static final String TABLE_NAME = "TrackableDataModel_testing1";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_Desc = "description";
    private static final String COLUMN_link = "link";
    private static final String COLUMN_category = "category";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCateg() {
        return categ;
    }

    public void setCateg(String categ) {
        this.categ = categ;
    }

    private int id;
    private String title;
    private String desc;
    private String link;
    private String categ;

    public static String getCreateTable() {
        return CREATE_TABLE;
    }

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                    "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_Desc + " TEXT,"
                    + COLUMN_link + " TEXT,"
                    + COLUMN_category + " TEXT"
                    +
                    ")";


    public TDMTable(int id, String title, String desc, String link, String categ) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.link = link;
        this.categ = categ;
    }

    public TDMTable() { }

}