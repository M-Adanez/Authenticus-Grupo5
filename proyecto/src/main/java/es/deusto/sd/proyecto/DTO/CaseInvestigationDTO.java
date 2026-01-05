package es.deusto.sd.proyecto.DTO;

import java.util.Date;
import java.util.List;

import es.deusto.sd.proyecto.Entity.AnalysisType;

public class CaseInvestigationDTO {

    private long userId;
    private String name;
    private AnalysisType type;
    private Date date;
    private List<String> imageList;

    public CaseInvestigationDTO() {}

    public CaseInvestigationDTO(String name, AnalysisType type, Date date, List<String> imageList) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.imageList = imageList;
    }

    public String getName() { return name; }
    public AnalysisType getType() { return type; }
    public Date getDate() { return date; }
    public List<String> getImageList() { return imageList; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
