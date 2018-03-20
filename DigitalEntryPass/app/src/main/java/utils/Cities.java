package utils;

/**
 * Created by meg2tron on 16/3/18.
 */

public class Cities {
    public Cities() {
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;
    public Cities(String name, String image) {
        this.name = name;
        this.image = image;
    }


}
