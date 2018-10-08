package Models;

public class TrackableDataModel {



    int id;
    String title;
    String description;
    String link;
    String category;

    public TrackableDataModel(int id, String title, String description, String link, String category) {
        this.id = id;
        this.title = cleanString(title);
        this.description = cleanString(description);
        this.link = cleanString(link);
        this.category = cleanString(category);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String cleanString(String s){
        return s.replaceAll("^\"|\"$", "");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void printData()
    {
        System.out.println(id + " " + title+ " " + description+ " " + link + " " + category);
    }
}
