package metube.domain.dtos.create;

public class TubeCreateDto {
    private String name;
    private String description;
    private String uploader;
    private String youtubeLink;

    public TubeCreateDto() {
    }

    public TubeCreateDto(String name, String description, String uploader, String youtubeLink) {
        this.name = name;
        this.description = description;
        this.uploader = uploader;
        this.youtubeLink = youtubeLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }
}
